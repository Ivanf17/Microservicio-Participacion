package Modelo;

public class Postulacion {

    private int id;
    private int idVoluntario;
    private int idProyecto;
    private String fechaPostulacion;
    private String estado;

    public Postulacion() {
    }

    public Postulacion(int id, int idVoluntario, int idProyecto, String fechaPostulacion, String estado) {
        this.id = id;
        this.idVoluntario = idVoluntario;
        this.idProyecto = idProyecto;
        this.fechaPostulacion = fechaPostulacion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(int idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(String fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}


