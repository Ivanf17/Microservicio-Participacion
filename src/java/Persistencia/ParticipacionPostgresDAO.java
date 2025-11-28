package Persistencia;

import Config.PostgreSQLConnection;
import Modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipacionPostgresDAO implements IParticipacionDAO {

    @Override
    public boolean crearPostulacion(PostulacionDTO dto) {
        String sql = "INSERT INTO postulacion(id_voluntario, id_proyecto) VALUES (?, ?)";

        try (Connection conn = PostgreSQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.idVoluntario);
            ps.setInt(2, dto.idProyecto);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error crear postulacion: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Postulacion> listarPostulacionesVoluntario(int idVoluntario) {
        List<Postulacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM postulacion WHERE id_voluntario = ?";

        try (Connection conn = PostgreSQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVoluntario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Postulacion p = new Postulacion();
                p.setId(rs.getInt("id"));
                p.setIdVoluntario(rs.getInt("id_voluntario"));
                p.setIdProyecto(rs.getInt("id_proyecto"));
                p.setFechaPostulacion(rs.getString("fecha_postulacion"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error listar postulaciones: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean registrarParticipacion(ParticipacionDTO dto) {
        String sql = """
                     INSERT INTO participacion(id_voluntario, id_proyecto, horas, fecha_inicio, fecha_fin)
                     VALUES (?, ?, ?, ?, ?)
                     """;

        try (Connection conn = PostgreSQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.idVoluntario);
            ps.setInt(2, dto.idProyecto);
            ps.setInt(3, dto.horas);
            ps.setString(4, dto.fechaInicio);
            ps.setString(5, dto.fechaFin);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error registrar participación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Participacion> historialParticipacion(int idVoluntario) {
        List<Participacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM participacion WHERE id_voluntario = ?";

        try (Connection conn = PostgreSQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVoluntario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Participacion p = new Participacion();
                p.setId(rs.getInt("id"));
                p.setIdVoluntario(rs.getInt("id_voluntario"));
                p.setIdProyecto(rs.getInt("id_proyecto"));
                p.setHoras(rs.getInt("horas"));
                p.setFechaInicio(rs.getString("fecha_inicio"));
                p.setFechaFin(rs.getString("fecha_fin"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error historial participación: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean generarCertificado(CertificadoDTO dto) {
        String sql = "INSERT INTO certificado(id_voluntario, id_proyecto) VALUES (?, ?)";

        try (Connection conn = PostgreSQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.idVoluntario);
            ps.setInt(2, dto.idProyecto);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error generar certificado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Certificado> listarCertificados(int idVoluntario) {
        List<Certificado> lista = new ArrayList<>();
        String sql = "SELECT * FROM certificado WHERE id_voluntario = ?";

        try (Connection conn = PostgreSQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVoluntario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Certificado c = new Certificado();
                c.setId(rs.getInt("id"));
                c.setIdVoluntario(rs.getInt("id_voluntario"));
                c.setIdProyecto(rs.getInt("id_proyecto"));
                c.setFechaEmision(rs.getString("fecha_emision"));
                c.setUrlPdf(rs.getString("url_pdf"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error listar certificados: " + e.getMessage());
        }

        return lista;
    }
}
