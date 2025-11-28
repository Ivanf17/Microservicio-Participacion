package Modelo;

public class Certificado {

    private int id;
    private int idVoluntario;
    private int idProyecto;
    private String fechaEmision;
    private String urlPdf;

    public Certificado() {
    }

    public Certificado(int id, int idVoluntario, int idProyecto, String fechaEmision, String urlPdf) {
        this.id = id;
        this.idVoluntario = idVoluntario;
        this.idProyecto = idProyecto;
        this.fechaEmision = fechaEmision;
        this.urlPdf = urlPdf;
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

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }
}
