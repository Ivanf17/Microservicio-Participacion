package Presentacion;

import Modelo.CertificadoDTO;
import Modelo.ParticipacionDTO;
import Modelo.PostulacionDTO;
import Servicio.ParticipacionServicio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ParticipacionControl")
public class ParticipacionControl extends HttpServlet {

    private final ParticipacionServicio servicio = new ParticipacionServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.getWriter().println("Microservicio Participación funcionando correctamente");
            return;
        }

        switch (accion) {

            case "listarPostulaciones": {
                int idVol = Integer.parseInt(request.getParameter("idVoluntario"));

                var lista = servicio.listarPostulaciones(idVol);

                if (lista.isEmpty()) {
                    response.getWriter().println("No hay postulaciones registradas para ese voluntario.");
                } else {
                    for (var p : lista) {
                        response.getWriter().println(
                                "ID: " + p.getId()
                                + ", Proyecto: " + p.getIdProyecto()
                                + ", Fecha: " + p.getFechaPostulacion()
                                + ", Estado: " + p.getEstado()
                        );
                    }
                }
                break;
            }

            case "listarCertificados": {
                int idVol = Integer.parseInt(request.getParameter("idVoluntario"));

                List<Modelo.Certificado> lista = servicio.listarCertificados(idVol);

                if (lista.isEmpty()) {
                    response.getWriter().println("No hay certificados para este voluntario.");
                } else {
                    for (var c : lista) {
                        response.getWriter().println(
                                "Certificado ID: " + c.getId()
                                + ", Proyecto: " + c.getIdProyecto()
                                + ", Fecha emisión: " + c.getFechaEmision()
                                + ", URL PDF: " + c.getUrlPdf()
                        );
                    }
                }
                break;
            }

            default:
                response.getWriter().println("Acción GET no reconocida.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.getWriter().println("Acción POST no enviada.");
            return;
        }

        switch (accion) {

            case "postular": {
                int idVol = Integer.parseInt(request.getParameter("idVoluntario"));
                int idProy = Integer.parseInt(request.getParameter("idProyecto"));

                boolean ok = servicio.postular(new PostulacionDTO(idVol, idProy));

                response.getWriter().println(ok ? "Postulación registrada." : "Error al registrar postulación.");
                break;
            }

            case "registrarParticipacion": {
                int idVol = Integer.parseInt(request.getParameter("idVoluntario"));
                int idProy = Integer.parseInt(request.getParameter("idProyecto"));
                int horas = Integer.parseInt(request.getParameter("horas"));
                String inicio = request.getParameter("fechaInicio");
                String fin = request.getParameter("fechaFin");

                boolean ok = servicio.registrarParticipacion(
                        new ParticipacionDTO(idVol, idProy, horas, inicio, fin)
                );

                response.getWriter().println(ok ? "Participación registrada." : "Error al registrar participación.");
                break;
            }

            case "generarCertificado": {
                int idVol = Integer.parseInt(request.getParameter("idVoluntario"));
                int idProy = Integer.parseInt(request.getParameter("idProyecto"));

                boolean ok = servicio.generarCertificado(new CertificadoDTO(idVol, idProy));

                response.getWriter().println(ok ? "Certificado generado." : "Error al generar certificado.");
                break;
            }

            default:
                response.getWriter().println("Acción POST no reconocida.");
        }
    }
}


