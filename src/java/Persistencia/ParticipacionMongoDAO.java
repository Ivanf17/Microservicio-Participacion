/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

/**
 *
 * @author flori
 */

import Config.MongoDBConnection;
import Modelo.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ParticipacionMongoDAO implements IParticipacionDAO {
    
    private final MongoDatabase database;
    private final MongoCollection<Document> postulacionesCollection;
    private final MongoCollection<Document> participacionesCollection;
    private final MongoCollection<Document> certificadosCollection;
    
    public ParticipacionMongoDAO() {
        this.database = MongoDBConnection.getInstance().getDatabase();
        this.postulacionesCollection = database.getCollection("postulaciones");
        this.participacionesCollection = database.getCollection("participaciones");
        this.certificadosCollection = database.getCollection("certificados");
    }
    
    @Override
    public boolean crearPostulacion(PostulacionDTO dto) {
        try {
            Document doc = new Document()
                .append("idVoluntario", dto.idVoluntario)
                .append("idProyecto", dto.idProyecto)
                .append("fechaPostulacion", new Date())
                .append("estado", "Pendiente");
            
            postulacionesCollection.insertOne(doc);
            System.out.println("✓ [MongoDB] Postulación creada");
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error crear postulación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Postulacion> listarPostulacionesVoluntario(int idVoluntario) {
        List<Postulacion> lista = new ArrayList<>();
        
        try {
            postulacionesCollection
                .find(Filters.eq("idVoluntario", idVoluntario))
                .sort(Sorts.descending("fechaPostulacion"))
                .forEach(doc -> {
                    Postulacion p = new Postulacion();
                    p.setId(doc.getObjectId("_id").toString().hashCode());
                    p.setIdVoluntario(doc.getInteger("idVoluntario"));
                    p.setIdProyecto(doc.getInteger("idProyecto"));
                    p.setFechaPostulacion(formatearFecha(doc.getDate("fechaPostulacion")));
                    p.setEstado(doc.getString("estado"));
                    lista.add(p);
                });
            
            System.out.println("✓ [MongoDB] " + lista.size() + " postulaciones encontradas");
            
        } catch (Exception e) {
            System.err.println("✗ Error listar postulaciones: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    @Override
    public boolean registrarParticipacion(ParticipacionDTO dto) {
        try {
            Document doc = new Document()
                .append("idVoluntario", dto.idVoluntario)
                .append("idProyecto", dto.idProyecto)
                .append("horas", dto.horas)
                .append("fechaInicio", parsearFecha(dto.fechaInicio))
                .append("fechaFin", parsearFecha(dto.fechaFin));
            
            participacionesCollection.insertOne(doc);
            System.out.println("✓ [MongoDB] Participación registrada");
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error registrar participación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Participacion> historialParticipacion(int idVoluntario) {
        List<Participacion> lista = new ArrayList<>();
        
        try {
            participacionesCollection
                .find(Filters.eq("idVoluntario", idVoluntario))
                .sort(Sorts.descending("fechaInicio"))
                .forEach(doc -> {
                    Participacion p = new Participacion();
                    p.setId(doc.getObjectId("_id").toString().hashCode());
                    p.setIdVoluntario(doc.getInteger("idVoluntario"));
                    p.setIdProyecto(doc.getInteger("idProyecto"));
                    p.setHoras(doc.getInteger("horas"));
                    p.setFechaInicio(formatearFecha(doc.getDate("fechaInicio")));
                    p.setFechaFin(formatearFecha(doc.getDate("fechaFin")));
                    lista.add(p);
                });
            
            System.out.println("✓ [MongoDB] " + lista.size() + " participaciones encontradas");
            
        } catch (Exception e) {
            System.err.println("✗ Error historial participación: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    @Override
    public boolean generarCertificado(CertificadoDTO dto) {
        try {
            // Verificar si ya existe un certificado para este voluntario y proyecto
            Document existente = certificadosCollection.find(
                Filters.and(
                    Filters.eq("idVoluntario", dto.idVoluntario),
                    Filters.eq("idProyecto", dto.idProyecto)
                )
            ).first();
            
            if (existente != null) {
                System.out.println("⚠ [MongoDB] Certificado ya existe");
                return false;
            }
            
            // Generar URL del PDF
            String urlPdf = "/certificados/cert_" + dto.idVoluntario + "_" + dto.idProyecto + ".pdf";
            
            Document doc = new Document()
                .append("idVoluntario", dto.idVoluntario)
                .append("idProyecto", dto.idProyecto)
                .append("fechaEmision", new Date())
                .append("urlPdf", urlPdf);
            
            certificadosCollection.insertOne(doc);
            System.out.println("✓ [MongoDB] Certificado generado: " + urlPdf);
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error generar certificado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Certificado> listarCertificados(int idVoluntario) {
        List<Certificado> lista = new ArrayList<>();
        
        try {
            certificadosCollection
                .find(Filters.eq("idVoluntario", idVoluntario))
                .sort(Sorts.descending("fechaEmision"))
                .forEach(doc -> {
                    Certificado c = new Certificado();
                    c.setId(doc.getObjectId("_id").toString().hashCode());
                    c.setIdVoluntario(doc.getInteger("idVoluntario"));
                    c.setIdProyecto(doc.getInteger("idProyecto"));
                    c.setFechaEmision(formatearFecha(doc.getDate("fechaEmision")));
                    c.setUrlPdf(doc.getString("urlPdf"));
                    lista.add(c);
                });
            
            System.out.println("✓ [MongoDB] " + lista.size() + " certificados encontrados");
            
        } catch (Exception e) {
            System.err.println("✗ Error listar certificados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Convierte Date a String formato yyyy-MM-dd
     */
    private String formatearFecha(Date fecha) {
        if (fecha == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }
    
    /**
     * Convierte String a Date
     */
    private Date parsearFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(fechaStr);
        } catch (Exception e) {
            System.err.println("✗ Error parsear fecha: " + fechaStr);
            return null;
        }
    }
    
    /**
     * Obtiene estadísticas de participación de un voluntario
     */
    public Document obtenerEstadisticas(int idVoluntario) {
        try {
            long totalPostulaciones = postulacionesCollection.countDocuments(
                Filters.eq("idVoluntario", idVoluntario)
            );
            
            long postulacionesAceptadas = postulacionesCollection.countDocuments(
                Filters.and(
                    Filters.eq("idVoluntario", idVoluntario),
                    Filters.eq("estado", "Aceptada")
                )
            );
            
            long totalParticipaciones = participacionesCollection.countDocuments(
                Filters.eq("idVoluntario", idVoluntario)
            );
            
            long totalCertificados = certificadosCollection.countDocuments(
                Filters.eq("idVoluntario", idVoluntario)
            );
            
            // Calcular total de horas
            int totalHoras = 0;
            for (Document doc : participacionesCollection.find(Filters.eq("idVoluntario", idVoluntario))) {
                totalHoras += doc.getInteger("horas", 0);
            }
            
            return new Document()
                .append("totalPostulaciones", totalPostulaciones)
                .append("postulacionesAceptadas", postulacionesAceptadas)
                .append("totalParticipaciones", totalParticipaciones)
                .append("totalHoras", totalHoras)
                .append("totalCertificados", totalCertificados);
                
        } catch (Exception e) {
            System.err.println("✗ Error obtener estadísticas: " + e.getMessage());
            return new Document();
        }
    }
    
    /**
     * Actualiza el estado de una postulación
     */
    public boolean actualizarEstadoPostulacion(int idVoluntario, int idProyecto, String nuevoEstado) {
        try {
            Document filtro = new Document()
                .append("idVoluntario", idVoluntario)
                .append("idProyecto", idProyecto);
            
            Document actualizacion = new Document("$set", 
                new Document("estado", nuevoEstado)
            );
            
            long modificados = postulacionesCollection.updateOne(filtro, actualizacion).getModifiedCount();
            
            if (modificados > 0) {
                System.out.println("✓ [MongoDB] Estado actualizado a: " + nuevoEstado);
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("✗ Error actualizar estado: " + e.getMessage());
            return false;
        }
    }
}
