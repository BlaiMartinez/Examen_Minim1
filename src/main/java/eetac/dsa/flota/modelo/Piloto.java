package eetac.dsa.flota.modelo;

public class Piloto {
    private final String id;
    private final String nombre;
    private final String apellidos;
    private final double horasVuelo;

    public Piloto(String id, String nombre, String apellidos, double horasVuelo) {
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

    @Override
    public String toString() {
        return "Piloto " + nombre + " " + apellidos + " (id=" + id + ", horasVuelo=" + horasVuelo + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piloto)) return false;
        return id.equals(((Piloto) o).id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
