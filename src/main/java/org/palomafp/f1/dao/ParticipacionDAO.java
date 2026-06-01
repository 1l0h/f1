package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.palomafp.f1.model.Participacion;

public class ParticipacionDAO {

	private String url;
	private String usuario;
	private String contrasena;
	private PilotosDAO pilotosDAO;
	private GranPremioDAO granPremioDAO;

	public ParticipacionDAO(String url, String usuario, String contrasena) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
		this.granPremioDAO = new GranPremioDAO(url, usuario, contrasena);
	}
	
	private Connection obtenerConexion() throws Exception {
		if (url == null || usuario == null || contrasena == null) {
			return null;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las participaciones (desde BD o MOCK)
	 */
	public ArrayList<Participacion> obtenerTodasParticipaciones() {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numeroPiloto, idGp, tiempoCarrera, posicion FROM participaciones";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numeroPiloto = rs.getInt("numeroPiloto");
					String tiempoCarrera = rs.getString("tiempoCarrera");
					int posicion = rs.getInt("posicion");
					
					participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(numeroPiloto), tiempoCarrera, posicion));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!participaciones.isEmpty()) {
					return participaciones;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al conectar con BD: " + e.getMessage());
		}
		
		// Fallback a mock
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:28:45", 1));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "01:29:15", 2));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:29:45", 3));

		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:51:32", 1));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:52:10", 2));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:52:45", 3));

		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:27:35", 1));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(63), "02:27:50", 2));
		participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "02:28:15", 3));

		return participaciones;
	}

	/**
	 * Obtiene participaciones de un piloto (desde BD o MOCK)
	 */
	public ArrayList<Participacion> obtenerParticipacionesPiloto(int numeroPiloto) {
		ArrayList<Participacion> resultado = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numeroPiloto, idGp, tiempoCarrera, posicion FROM participaciones WHERE numeroPiloto = " + numeroPiloto;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					String tiempoCarrera = rs.getString("tiempoCarrera");
					int posicion = rs.getInt("posicion");
					
					resultado.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(numeroPiloto), tiempoCarrera, posicion));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!resultado.isEmpty()) {
					return resultado;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al obtener participaciones del piloto: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<Participacion> todas = obtenerTodasParticipaciones();
		for (Participacion p : todas) {
			if (p.getPiloto().getNumero() == numeroPiloto) {
				resultado.add(p);
			}
		}
		return resultado;
	}

	/**
	 * Obtiene participaciones de un gran premio (desde BD o MOCK)
	 */
	public ArrayList<Participacion> obtenerParticipacionesGranPremio(int idGp) {
		ArrayList<Participacion> participaciones = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numeroPiloto, tiempoCarrera, posicion FROM participaciones WHERE idGp = " + idGp + " ORDER BY posicion";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numeroPiloto = rs.getInt("numeroPiloto");
					String tiempoCarrera = rs.getString("tiempoCarrera");
					int posicion = rs.getInt("posicion");
					
					participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(numeroPiloto), tiempoCarrera, posicion));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!participaciones.isEmpty()) {
					return participaciones;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al obtener participaciones del GP: " + e.getMessage());
		}
		
		// Fallback a mock
		if (idGp == 1) {
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:28:45", 1));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "01:29:15", 2));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:29:45", 3));
		} else if (idGp == 2) {
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(33), "01:51:32", 1));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(1), "01:52:10", 2));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(16), "01:52:45", 3));
		} else if (idGp == 3) {
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(81), "02:27:35", 1));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(63), "02:27:50", 2));
			participaciones.add(new Participacion(pilotosDAO.obtenerPilotoPorNumero(44), "02:28:15", 3));
		}

		return participaciones;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar en modo mock
	}
}
