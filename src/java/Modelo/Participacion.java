package Modelo;

public class Participacion {

    private int id;
    private int idVoluntario;
    private int idProyecto;
    private int horas;
    private String fechaInicio;
    private String fechaFin;

    public Participacion() {
    }

    public Participacion(int id, int idVoluntario, int idProyecto, int horas, String fechaInicio, String fechaFin) {
        this.id = id;
        this.idVoluntario = idVoluntario;
        this.idProyecto = idProyecto;
        this.horas = horas;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
