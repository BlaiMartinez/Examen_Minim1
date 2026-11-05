package eetac.dsa.flota.dto;

public class DronDTO {
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private double horasVuelo;

    public DronDTO() {
    }

    public DronDTO(String id, String nombre, String fabricante, String modelo, double horasVuelo) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = horasVuelo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public double getHorasVuelo() {
        return horasVuelo;
    }
}
