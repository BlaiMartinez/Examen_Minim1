package eetac.dsa.flota.web;

import eetac.dsa.flota.api.GestorFlotaFacade;
import eetac.dsa.flota.api.IGestorFlota;
import eetac.dsa.flota.dto.DronDTO;
import eetac.dsa.flota.dto.PilotoDTO;
import eetac.dsa.flota.dto.PlanDeVueloDTO;
import eetac.dsa.flota.dto.PosicionDTO;
import eetac.dsa.flota.excepcion.GestorException;
import eetac.dsa.flota.modelo.Posicion;
import eetac.dsa.flota.modelo.Dron;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** Servicio REST con las (4) operaciones del enunciado. */
@RestController
@RequestMapping("/api")
public class GestorFlotaController {

    private final IGestorFlota gestor = GestorFlotaFacade.getInstance();

    @GetMapping("/drones")
    public ResponseEntity<List<DronDTO>> listarDronesPorHoras() {
        List<DronDTO> resultado = new ArrayList<>();
        for (Dron dron : gestor.listarDronesPorHoras()) {
            resultado.add(new DronDTO(dron.getId(), dron.getNombre(), dron.getFabricante(),
                    dron.getModelo(), dron.getHorasVuelo()));
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/drones")
    public ResponseEntity<DronDTO> añadirDron(@RequestBody DronDTO peticion) throws GestorException {
        gestor.añadirDron(peticion.getId(), peticion.getNombre(), peticion.getFabricante(),
                peticion.getModelo(), peticion.getHorasVuelo());
        return ResponseEntity.status(HttpStatus.CREATED).body(peticion);
    }

    @PostMapping("/pilotos")
    public ResponseEntity<PilotoDTO> añadirPiloto(@RequestBody PilotoDTO peticion) throws GestorException {
        gestor.añadirPiloto(peticion.getId(), peticion.getNombre(), peticion.getApellidos(),
                peticion.getHorasVuelo());
        return ResponseEntity.status(HttpStatus.CREATED).body(peticion);
    }

    @PostMapping("/almacen/{idDron}")
    public ResponseEntity<String> guardarDronEnAlmacen(@PathVariable String idDron) throws GestorException {
        gestor.guardarDronEnAlmacen(idDron);
        return ResponseEntity.ok("Dron " + idDron + " guardado en el almacén");
    }

    @PostMapping("/planes")
    public ResponseEntity<PlanDeVueloDTO> crearPlanDeVuelo(@RequestBody PlanDeVueloDTO peticion) throws GestorException {
        List<Posicion> posiciones = new ArrayList<>();
        if (peticion.getPosiciones() != null) {
            for (PosicionDTO posicionDTO : peticion.getPosiciones()) {
                posiciones.add(new Posicion(posicionDTO.getLatitud(), posicionDTO.getLongitud()));
            }
        }
        gestor.crearPlanDeVuelo(peticion.getIdDron(), peticion.getIdPiloto(), peticion.getFechaHora(),
                peticion.getDuracionHoras(), posiciones);
        return ResponseEntity.status(HttpStatus.CREATED).body(peticion);
    }
}
