package eetac.dsa.flota.api;

import eetac.dsa.flota.excepcion.GestorException;
import eetac.dsa.flota.modelo.Dron;
import eetac.dsa.flota.modelo.Piloto;
import eetac.dsa.flota.modelo.PlanDeVuelo;
import eetac.dsa.flota.modelo.Posicion;

import java.time.LocalDateTime;
import java.util.List;

public interface IGestorFlota {

    void añadirDron(String id, String nombre, String fabricante, String modelo, double horasVuelo) throws GestorException;

    void añadirPiloto(String id, String nombre, String apellidos, double horasVuelo) throws GestorException;

    List<Dron> listarDronesPorHoras();

    List<Piloto> listarPilotosPorHoras();

    void guardarDronEnAlmacen(String idDron) throws GestorException;

    Dron repararProximoDronEnAlmacen() throws GestorException;

    void crearPlanDeVuelo(String idDron, String idPiloto, LocalDateTime fechaHora,
                          double duracionHoras, List<Posicion> posiciones) throws GestorException;

    List<PlanDeVuelo> listarPlanesDePiloto(String idPiloto) throws GestorException;

    List<PlanDeVuelo> listarPlanesDeDron(String idDron) throws GestorException;
}
