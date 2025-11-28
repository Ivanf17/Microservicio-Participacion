package Modelo;

public class ParticipacionDTO {

    public int idVoluntario;
    public int idProyecto;
    public int horas;
    public String fechaInicio;
    public String fechaFin;

    public ParticipacionDTO(int idVoluntario, int idProyecto, int horas, String fechaInicio, String fechaFin) {
        this.idVoluntario = idVoluntario;
        this.idProyecto = idProyecto;
        this.horas = horas;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
}
