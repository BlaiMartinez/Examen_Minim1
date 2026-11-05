package eetac.dsa.flota.dto;

public class PilotoDTO {
    private String id;
    private String nombre;
    private String apellidos;
    private double horasVuelo;

    public PilotoDTO() {
    }

    public PilotoDTO(String id, String nombre, String apellidos, double horasVuelo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.horasVuelo = horasVuelo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public double getHorasVuelo() {
        return horasVuelo;
    }
}
