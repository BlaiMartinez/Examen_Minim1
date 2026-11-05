package eetac.dsa.flota.api;

import eetac.dsa.flota.excepcion.GestorConflictException;
import eetac.dsa.flota.excepcion.GestorException;
import eetac.dsa.flota.excepcion.GestorNotFoundException;
import eetac.dsa.flota.modelo.Dron;
import eetac.dsa.flota.modelo.Piloto;
import eetac.dsa.flota.modelo.PlanDeVuelo;
import eetac.dsa.flota.modelo.Posicion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestorFlotaFacadeTest {

    private final IGestorFlota gestor = GestorFlotaFacade.getInstance();

    @Test
    void añadirDronYPiloto() throws GestorException {
        gestor.añadirDron("T-D1", "Rayo", "DJI", "Mavic3", 12.5);
        gestor.añadirPiloto("T-P1", "Ana", "Lopez", 30);

        assertThrows(GestorConflictException.class,
                () -> gestor.añadirDron("T-D1", "Otro", "Parrot", "Anafi", 1));
        assertThrows(GestorConflictException.class,
                () -> gestor.añadirPiloto("T-P1", "Luis", "Garcia", 5));
    }

    @Test
    void listadosOrdenadosPorHoras() throws GestorException {
        gestor.añadirDron("L-D1", "A", "F1", "M1", 101);
        gestor.añadirDron("L-D2", "B", "F2", "M2", 103);
        gestor.añadirPiloto("L-P1", "Pepe", "Solo", 201);
        gestor.añadirPiloto("L-P2", "Rosa", "Campos", 203);

        List<Dron> drones = gestor.listarDronesPorHoras();
        assertTrue(posicionDe(drones, "L-D2") < posicionDe(drones, "L-D1"));

        List<Piloto> pilotos = gestor.listarPilotosPorHoras();
        assertTrue(posicionDe(pilotos, "L-P2") < posicionDe(pilotos, "L-P1"));
    }

    private int posicionDe(List<? extends Object> lista, String id) {
        for (int i = 0; i < lista.size(); i++) {
            Object o = lista.get(i);
            String idObjeto = (o instanceof Dron) ? ((Dron) o).getId() : ((Piloto) o).getId();
            if (idObjeto.equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Test
    void almacenMantenimiento() throws GestorException {
        gestor.añadirDron("A-D1", "Trueno", "Parrot", "Anafi", 20);
        gestor.añadirPiloto("A-P1", "Marc", "Puig", 40);

        gestor.guardarDronEnAlmacen("A-D1");

        assertThrows(GestorConflictException.class, () -> gestor.crearPlanDeVuelo(
                "A-D1", "A-P1", LocalDateTime.parse("2026-12-02T10:00"), 2,
                List.of(new Posicion(41.27, 2.08))));

        Dron reparado = gestor.repararProximoDronEnAlmacen();
        assertEquals("A-D1", reparado.getId());

        assertThrows(GestorNotFoundException.class, () -> gestor.repararProximoDronEnAlmacen());

        gestor.crearPlanDeVuelo("A-D1", "A-P1", LocalDateTime.parse("2026-12-02T10:00"), 2,
                List.of(new Posicion(41.27, 2.08)));
    }

    @Test
    void planesDeVuelo() throws GestorException {
        gestor.añadirDron("F-D1", "Relampago", "DJI", "Mavic2", 55);
        gestor.añadirDron("F-D2", "Viento", "Parrot", "Anafi", 60);
        gestor.añadirPiloto("F-P1", "Nuria", "Vila", 80);

        LocalDateTime inicio1 = LocalDateTime.parse("2026-12-01T10:00");
        LocalDateTime inicio2 = LocalDateTime.parse("2026-12-01T13:00");
        List<Posicion> posiciones = List.of(new Posicion(41.27, 2.08), new Posicion(41.30, 2.10));

        gestor.crearPlanDeVuelo("F-D1", "F-P1", inicio1, 2, posiciones);

        assertThrows(GestorConflictException.class, () -> gestor.crearPlanDeVuelo(
                "F-D1", "F-P1", inicio1, 2, posiciones));

        assertThrows(GestorConflictException.class, () -> gestor.crearPlanDeVuelo(
                "F-D2", "F-P1", inicio1, 2, posiciones));

        gestor.crearPlanDeVuelo("F-D2", "F-P1", inicio2, 2, posiciones);

        assertThrows(GestorNotFoundException.class, () -> gestor.crearPlanDeVuelo(
                "F-XX", "F-P1", inicio1, 2, posiciones));
        assertThrows(GestorNotFoundException.class, () -> gestor.crearPlanDeVuelo(
                "F-D1", "F-YY", inicio1, 2, posiciones));

        assertEquals(2, gestor.listarPlanesDePiloto("F-P1").size());
        List<PlanDeVuelo> planesDron1 = gestor.listarPlanesDeDron("F-D1");
        assertEquals(1, planesDron1.size());
        assertEquals("F-D1", planesDron1.get(0).getIdDron());
        assertEquals("F-P1", planesDron1.get(0).getIdPiloto());
    }
}
