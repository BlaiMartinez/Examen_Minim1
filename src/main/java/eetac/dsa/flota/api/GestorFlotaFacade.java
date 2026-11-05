package eetac.dsa.flota.api;

import eetac.dsa.flota.excepcion.GestorConflictException;
import eetac.dsa.flota.excepcion.GestorException;
import eetac.dsa.flota.excepcion.GestorNotFoundException;
import eetac.dsa.flota.modelo.Dron;
import eetac.dsa.flota.modelo.Piloto;
import eetac.dsa.flota.modelo.PlanDeVuelo;
import eetac.dsa.flota.modelo.Posicion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Fachada SINGLETON que implementa la especificación de IGestorFlota con trazas Log4J2. */
public class GestorFlotaFacade implements IGestorFlota {

    private static final Logger LOG = LogManager.getLogger(GestorFlotaFacade.class);

    private static final GestorFlotaFacade INSTANCE = new GestorFlotaFacade();

    public static GestorFlotaFacade getInstance() {
        return INSTANCE;
    }

    private GestorFlotaFacade() {
    }

    private final Map<String, Dron> drones = new HashMap<>();
    private final Map<String, Piloto> pilotos = new HashMap<>();
    private final List<PlanDeVuelo> planesDeVuelo = new ArrayList<>();

    /** Los drones se guardan en el fondo: el más cercano a la puerta es el último en entrar (LIFO). */
    private final Deque<Dron> almacen = new ArrayDeque<>();

    @Override
    public void añadirDron(String id, String nombre, String fabricante, String modelo, double horasVuelo) throws GestorException {
        LOG.info("START añadirDron: id={}, nombre={}, fabricante={}, modelo={}, horasVuelo={}",
                id, nombre, fabricante, modelo, horasVuelo);
        if (drones.containsKey(id)) {
            GestorConflictException e = new GestorConflictException("Error: ya existe un dron con el identificador " + id);
            LOG.error("ERROR añadirDron: {}", e.getMessage());
            throw e;
        }
        drones.put(id, new Dron(id, nombre, fabricante, modelo, horasVuelo));
        LOG.info("END añadirDron: dron {} dado de alta correctamente", id);
    }

    @Override
    public void añadirPiloto(String id, String nombre, String apellidos, double horasVuelo) throws GestorException {
        LOG.info("START añadirPiloto: id={}, nombre={}, apellidos={}, horasVuelo={}",
                id, nombre, apellidos, horasVuelo);
        if (pilotos.containsKey(id)) {
            GestorConflictException e = new GestorConflictException("Error: ya existe un piloto con el identificador " + id);
            LOG.error("ERROR añadirPiloto: {}", e.getMessage());
            throw e;
        }
        pilotos.put(id, new Piloto(id, nombre, apellidos, horasVuelo));
        LOG.info("END añadirPiloto: piloto {} dado de alta correctamente", id);
    }

    @Override
    public List<Dron> listarDronesPorHoras() {
        LOG.info("START listarDronesPorHoras: totalDrones={}", drones.size());
        List<Dron> lista = new ArrayList<>(drones.values());
        lista.sort(Comparator.comparing(Dron::getHorasVuelo).reversed());
        LOG.info("END listarDronesPorHoras: {} drones ordenados", lista.size());
        return lista;
    }

    @Override
    public List<Piloto> listarPilotosPorHoras() {
        LOG.info("START listarPilotosPorHoras: totalPilotos={}", pilotos.size());
        List<Piloto> lista = new ArrayList<>(pilotos.values());
        lista.sort(Comparator.comparing(Piloto::getHorasVuelo).reversed());
        LOG.info("END listarPilotosPorHoras: {} pilotos ordenados", lista.size());
        return lista;
    }

    @Override
    public void guardarDronEnAlmacen(String idDron) throws GestorException {
        LOG.info("START guardarDronEnAlmacen: idDron={}", idDron);
        Dron dron = drones.get(idDron);
        if (dron == null) {
            GestorNotFoundException e = new GestorNotFoundException("Error: no existe ningún dron con el identificador " + idDron);
            LOG.error("ERROR guardarDronEnAlmacen: {}", e.getMessage());
            throw e;
        }
        if (almacen.contains(dron)) {
            GestorConflictException e = new GestorConflictException("Error: el dron " + idDron + " ya está en el almacén");
            LOG.error("ERROR guardarDronEnAlmacen: {}", e.getMessage());
            throw e;
        }
        almacen.push(dron);
        LOG.info("END guardarDronEnAlmacen: dron {} guardado en el almacén", idDron);
    }

    @Override
    public Dron repararProximoDronEnAlmacen() throws GestorException {
        LOG.info("START repararProximoDronEnAlmacen: dronesEnAlmacen={}", almacen.size());
        if (almacen.isEmpty()) {
            GestorNotFoundException e = new GestorNotFoundException("Error: el almacén no tiene ningún dron");
            LOG.fatal("FATAL repararProximoDronEnAlmacen: se ha solicitado mantenimiento sin drones en el almacén");
            throw e;
        }
        Dron dron = almacen.pop();
        LOG.info("END repararProximoDronEnAlmacen: mantenimiento realizado sobre {}. Dron disponible para ser utilizado", dron);
        return dron;
    }

    @Override
    public void crearPlanDeVuelo(String idDron, String idPiloto, LocalDateTime fechaHora,
                                 double duracionHoras, List<Posicion> posiciones) throws GestorException {
        LOG.info("START crearPlanDeVuelo: idDron={}, idPiloto={}, fechaHora={}, duracionHoras={}, numPosiciones={}",
                idDron, idPiloto, fechaHora, duracionHoras, posiciones == null ? 0 : posiciones.size());

        Dron dron = drones.get(idDron);
        if (dron == null) {
            GestorNotFoundException e = new GestorNotFoundException("Error: no existe ningún dron con el identificador " + idDron);
            LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
            throw e;
        }
        Piloto piloto = pilotos.get(idPiloto);
        if (piloto == null) {
            GestorNotFoundException e = new GestorNotFoundException("Error: no existe ningún piloto con el identificador " + idPiloto);
            LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
            throw e;
        }
        if (duracionHoras <= 0) {
            GestorConflictException e = new GestorConflictException("Error: la duración estimada debe ser mayor que 0");
            LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
            throw e;
        }
        if (posiciones == null || posiciones.isEmpty()) {
            GestorConflictException e = new GestorConflictException("Error: el plan de vuelo debe incluir al menos una posición");
            LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
            throw e;
        }
        if (almacen.contains(dron)) {
            GestorConflictException e = new GestorConflictException(
                    "Error: el dron " + idDron + " está en el almacén para tareas de mantenimiento y no está operativo");
            LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
            throw e;
        }

        LocalDateTime fechaFin = fechaHora.plusMinutes(Math.round(duracionHoras * 60));
        for (PlanDeVuelo plan : planesDeVuelo) {
            if (plan.getIdDron().equals(idDron) && plan.seSolapaCon(fechaHora, fechaFin)) {
                GestorConflictException e = new GestorConflictException(
                        "Error: el dron " + idDron + " ya tiene un plan de vuelo asignado dentro de ese intervalo de tiempo");
                LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
                throw e;
            }
            if (plan.getIdPiloto().equals(idPiloto) && plan.seSolapaCon(fechaHora, fechaFin)) {
                GestorConflictException e = new GestorConflictException(
                        "Error: el piloto " + idPiloto + " ya tiene un plan de vuelo asignado dentro de ese intervalo de tiempo");
                LOG.error("ERROR crearPlanDeVuelo: {}", e.getMessage());
                throw e;
            }
        }

        planesDeVuelo.add(new PlanDeVuelo(idDron, idPiloto, fechaHora, duracionHoras, posiciones));
        LOG.info("END crearPlanDeVuelo: plan creado correctamente para el dron {} y el piloto {}", idDron, idPiloto);
    }

    @Override
    public List<PlanDeVuelo> listarPlanesDePiloto(String idPiloto) throws GestorException {
        LOG.info("START listarPlanesDePiloto: idPiloto={}", idPiloto);
        if (!pilotos.containsKey(idPiloto)) {
            GestorNotFoundException e = new GestorNotFoundException("Error: no existe ningún piloto con el identificador " + idPiloto);
            LOG.error("ERROR listarPlanesDePiloto: {}", e.getMessage());
            throw e;
        }
        List<PlanDeVuelo> lista = new ArrayList<>();
        for (PlanDeVuelo plan : planesDeVuelo) {
            if (plan.getIdPiloto().equals(idPiloto)) {
                lista.add(plan);
            }
        }
        LOG.info("END listarPlanesDePiloto: {} planes para el piloto {}", lista.size(), idPiloto);
        return lista;
    }

    @Override
    public List<PlanDeVuelo> listarPlanesDeDron(String idDron) throws GestorException {
        LOG.info("START listarPlanesDeDron: idDron={}", idDron);
        if (!drones.containsKey(idDron)) {
            GestorNotFoundException e = new GestorNotFoundException("Error: no existe ningún dron con el identificador " + idDron);
            LOG.error("ERROR listarPlanesDeDron: {}", e.getMessage());
            throw e;
        }
        List<PlanDeVuelo> lista = new ArrayList<>();
        for (PlanDeVuelo plan : planesDeVuelo) {
            if (plan.getIdDron().equals(idDron)) {
                lista.add(plan);
            }
        }
        LOG.info("END listarPlanesDeDron: {} planes para el dron {}", lista.size(), idDron);
        return lista;
    }
}
