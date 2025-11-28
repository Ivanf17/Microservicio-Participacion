package Persistencia;

import Modelo.*;
import java.util.List;

public interface IParticipacionDAO {

    boolean crearPostulacion(PostulacionDTO dto);

    List<Postulacion> listarPostulacionesVoluntario(int idVoluntario);

    boolean registrarParticipacion(ParticipacionDTO dto);

    List<Participacion> historialParticipacion(int idVoluntario);

    boolean generarCertificado(CertificadoDTO dto);

    List<Certificado> listarCertificados(int idVoluntario);
}
