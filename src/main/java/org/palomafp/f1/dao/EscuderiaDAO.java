package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.palomafp.f1.model.Escuderia;
import org.palomafp.f1.model.ModeloCoche;
import org.palomafp.f1.model.Piloto;

public class EscuderiaDAO {

	private String url;
	private String usuario;
	private String contrasena;
	private PilotosDAO pilotosDAO;
	private ModeloCochesDAO modeloCochesDAO;

	public EscuderiaDAO(String url, String usuario, String contrasena) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.pilotosDAO = new PilotosDAO(url, usuario, contrasena);
		this.modeloCochesDAO = new ModeloCochesDAO(url, usuario, contrasena);
	}
	
	private Connection obtenerConexion() throws Exception {
		if (url == null || usuario == null || contrasena == null) {
			return null;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todas las escuderías (desde BD o MOCK)
	 */
	public ArrayList<Escuderia> obtenerTodasEscuderias() {
		ArrayList<Escuderia> escuderias = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idEscuderia, nombre, pais, directorEquipo, fechaFundacion, campeonatos, victorias, podios, color, idModelo FROM escuderias";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int idEscuderia = rs.getInt("idEscuderia");
					String nombre = rs.getString("nombre");
					String pais = rs.getString("pais");
					String director = rs.getString("directorEquipo");
					Date fecha = new Date(rs.getDate("fechaFundacion").getTime());
					int campeonatos = rs.getInt("campeonatos");
					int victorias = rs.getInt("victorias");
					int podios = rs.getInt("podios");
					String color = rs.getString("color");
					int idModelo = rs.getInt("idModelo");
					
					ModeloCoche modelo = modeloCochesDAO.obtenerModeloPorId(idModelo);
					ArrayList<Piloto> pilotos = obtenerPilotosEscuderia(idEscuderia);
					
					escuderias.add(new Escuderia(idEscuderia, nombre, pais, director, fecha, campeonatos, victorias, podios, color, modelo, pilotos, null));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!escuderias.isEmpty()) {
					return escuderias;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al conectar con BD: " + e.getMessage());
		}
		
		// Fallback a mock
		return generarEscuderiassMock();
	}
	
	private ArrayList<Escuderia> generarEscuderiassMock() {
		ArrayList<Escuderia> escuderias = new ArrayList<>();

		ArrayList<Piloto> pilotos1 = new ArrayList<>();
		pilotos1.add(pilotosDAO.obtenerPilotoPorNumero(1));
		pilotos1.add(pilotosDAO.obtenerPilotoPorNumero(81));
		Escuderia mclaren = new Escuderia(1, "McLaren", "Reino Unido", "Andrea Stella", 
			parseDate("1966-01-01"), 9, 13, 195, "FF8000", 
			modeloCochesDAO.obtenerModeloPorId(1), pilotos1, null);
		escuderias.add(mclaren);

		ArrayList<Piloto> pilotos2 = new ArrayList<>();
		pilotos2.add(pilotosDAO.obtenerPilotoPorNumero(63));
		pilotos2.add(pilotosDAO.obtenerPilotoPorNumero(44));
		Escuderia mercedes = new Escuderia(2, "Mercedes", "Alemania", "Toto Wolff",
			parseDate("2010-01-01"), 8, 9, 129, "00D2BE",
			modeloCochesDAO.obtenerModeloPorId(2), pilotos2, null);
		escuderias.add(mercedes);

		ArrayList<Piloto> pilotos3 = new ArrayList<>();
		pilotos3.add(pilotosDAO.obtenerPilotoPorNumero(16));
		pilotos3.add(pilotosDAO.obtenerPilotoPorNumero(55));
		Escuderia ferrari = new Escuderia(3, "Ferrari", "Italia", "Frederic Vasseur",
			parseDate("1950-01-01"), 16, 15, 248, "DC0000",
			modeloCochesDAO.obtenerModeloPorId(3), pilotos3, null);
		escuderias.add(ferrari);

		ArrayList<Piloto> pilotos4 = new ArrayList<>();
		pilotos4.add(pilotosDAO.obtenerPilotoPorNumero(33));
		pilotos4.add(pilotosDAO.obtenerPilotoPorNumero(11));
		Escuderia redBull = new Escuderia(4, "Red Bull Racing", "Austria", "Christian Horner",
			parseDate("2005-01-01"), 6, 8, 122, "1E41FF",
			modeloCochesDAO.obtenerModeloPorId(4), pilotos4, null);
		escuderias.add(redBull);

		ArrayList<Piloto> pilotos5 = new ArrayList<>();
		pilotos5.add(pilotosDAO.obtenerPilotoPorNumero(14));
		pilotos5.add(pilotosDAO.obtenerPilotoPorNumero(18));
		Escuderia astonMartin = new Escuderia(5, "Aston Martin", "Reino Unido", "Mike Krack",
			parseDate("2021-01-01"), 0, 0, 1, "006F62",
			modeloCochesDAO.obtenerModeloPorId(5), pilotos5, null);
		escuderias.add(astonMartin);

		return escuderias;
	}
	
	private ArrayList<Piloto> obtenerPilotosEscuderia(int idEscuderia) {
		ArrayList<Piloto> pilotos = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT numeroPiloto FROM escuderiaPilotos WHERE idEscuderia = " + idEscuderia;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int numeroPiloto = rs.getInt("numeroPiloto");
					Piloto piloto = pilotosDAO.obtenerPilotoPorNumero(numeroPiloto);
					if (piloto != null) {
						pilotos.add(piloto);
					}
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			// Ignorar
		}
		
		return pilotos;
	}

	/**
	 * Obtiene una escudería por su ID (desde BD o MOCK)
	 */
	public Escuderia obtenerEscuderiaPorId(int idEscuderia) {
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idEscuderia, nombre, pais, directorEquipo, fechaFundacion, campeonatos, victorias, podios, color, idModelo FROM escuderias WHERE idEscuderia = " + idEscuderia;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					String nombre = rs.getString("nombre");
					String pais = rs.getString("pais");
					String director = rs.getString("directorEquipo");
					Date fecha = new Date(rs.getDate("fechaFundacion").getTime());
					int campeonatos = rs.getInt("campeonatos");
					int victorias = rs.getInt("victorias");
					int podios = rs.getInt("podios");
					String color = rs.getString("color");
					int idModelo = rs.getInt("idModelo");
					
					ModeloCoche modelo = modeloCochesDAO.obtenerModeloPorId(idModelo);
					ArrayList<Piloto> pilotos = obtenerPilotosEscuderia(idEscuderia);
					
					Escuderia escuderia = new Escuderia(idEscuderia, nombre, pais, director, fecha, campeonatos, victorias, podios, color, modelo, pilotos, null);
					rs.close();
					stmt.close();
					con.close();
					return escuderia;
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.err.println("Error al obtener escudería por ID: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<Escuderia> todas = obtenerTodasEscuderias();
		for (Escuderia e : todas) {
			if (e.getIdEscuderia() == idEscuderia) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Obtiene una escudería por su nombre (desde BD o MOCK)
	 */
	public Escuderia obtenerEscuderiaPorNombre(String nombre) {
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idEscuderia, nombre, pais, directorEquipo, fechaFundacion, campeonatos, victorias, podios, color, idModelo FROM escuderias WHERE nombre = '" + nombre + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					int idEscuderia = rs.getInt("idEscuderia");
					String pais = rs.getString("pais");
					String director = rs.getString("directorEquipo");
					Date fecha = new Date(rs.getDate("fechaFundacion").getTime());
					int campeonatos = rs.getInt("campeonatos");
					int victorias = rs.getInt("victorias");
					int podios = rs.getInt("podios");
					String color = rs.getString("color");
					int idModelo = rs.getInt("idModelo");
					
					ModeloCoche modelo = modeloCochesDAO.obtenerModeloPorId(idModelo);
					ArrayList<Piloto> pilotos = obtenerPilotosEscuderia(idEscuderia);
					
					Escuderia escuderia = new Escuderia(idEscuderia, nombre, pais, director, fecha, campeonatos, victorias, podios, color, modelo, pilotos, null);
					rs.close();
					stmt.close();
					con.close();
					return escuderia;
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.err.println("Error al obtener escudería por nombre: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<Escuderia> todas = obtenerTodasEscuderias();
		for (Escuderia e : todas) {
			if (e.getNombre().equalsIgnoreCase(nombre)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Método auxiliar para parsear fechas
	 */
	private Date parseDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar en modo mock
	}
}
