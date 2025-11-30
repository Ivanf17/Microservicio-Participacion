package Servicio;

import Modelo.*;
import Persistencia.*;
import java.util.List;

public class ParticipacionServicio {

    IParticipacionDAO dao = new ParticipacionMongoDAO();

    public boolean postular(PostulacionDTO dto) {
        return dao.crearPostulacion(dto);
    }

    public List<Postulacion> listarPostulaciones(int idVoluntario) {
        return dao.listarPostulacionesVoluntario(idVoluntario);
    }

    public boolean registrarParticipacion(ParticipacionDTO dto) {
        return dao.registrarParticipacion(dto);
    }

    public List<Participacion> historial(int idVoluntario) {
        return dao.historialParticipacion(idVoluntario);
    }

    public boolean generarCertificado(CertificadoDTO dto) {
        return dao.generarCertificado(dto);
    }

    public List<Certificado> listarCertificados(int idVoluntario) {
        return dao.listarCertificados(idVoluntario);
    }
}

