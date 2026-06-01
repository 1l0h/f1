package org.palomafp.f1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.palomafp.f1.model.ModeloCoche;

public class ModeloCochesDAO {
	
	private String url;
	private String usuario;
	private String contrasena;

	public ModeloCochesDAO(String url, String usuario, String contrasena) {
		this.url = url;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}
	
	private Connection obtenerConexion() throws Exception {
		if (url == null || usuario == null || contrasena == null) {
			return null;
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, usuario, contrasena);
	}

	/**
	 * Obtiene todos los modelos de coches (desde BD o MOCK)
	 */
	public ArrayList<ModeloCoche> obtenerTodosModelos() {
		ArrayList<ModeloCoche> coches = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idModelo, nombre, motor, potencia, peso, velocidadMaxima FROM modelosCoches";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int idModelo = rs.getInt("idModelo");
					String nombre = rs.getString("nombre");
					String motor = rs.getString("motor");
					int potencia = rs.getInt("potencia");
					int peso = rs.getInt("peso");
					int velocidad = rs.getInt("velocidadMaxima");
					
					coches.add(new ModeloCoche(idModelo, nombre, motor, potencia, peso, velocidad, null));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!coches.isEmpty()) {
					return coches;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al conectar con BD: " + e.getMessage());
		}
		
		// Fallback a mock
		coches.add(new ModeloCoche(1, "McLaren MCL38", "Mercedes", 900, 340, 798, null));
		coches.add(new ModeloCoche(2, "Mercedes W15", "Mercedes", 900, 342, 798, null));
		coches.add(new ModeloCoche(3, "Ferrari SF-24", "Ferrari", 900, 340, 798, null));
		coches.add(new ModeloCoche(4, "Red Bull RB20", "Honda RBPT", 900, 341, 798, null));
		coches.add(new ModeloCoche(5, "Aston Martin AMR24", "Mercedes", 900, 339, 798, null));

		return coches;
	}

	/**
	 * Obtiene un modelo de coche por su ID (desde BD o MOCK)
	 */
	public ModeloCoche obtenerModeloPorId(int idModelo) {
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idModelo, nombre, motor, potencia, peso, velocidadMaxima FROM modelosCoches WHERE idModelo = " + idModelo;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					String nombre = rs.getString("nombre");
					String motor = rs.getString("motor");
					int potencia = rs.getInt("potencia");
					int peso = rs.getInt("peso");
					int velocidad = rs.getInt("velocidadMaxima");
					
					ModeloCoche coche = new ModeloCoche(idModelo, nombre, motor, potencia, peso, velocidad, null);
					rs.close();
					stmt.close();
					con.close();
					return coche;
				}
				
				rs.close();
				stmt.close();
				con.close();
			}
		} catch (Exception e) {
			System.err.println("Error al obtener modelo por ID: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<ModeloCoche> todos = obtenerTodosModelos();
		for (ModeloCoche coche : todos) {
			if (coche.getIdModelo() == idModelo) {
				return coche;
			}
		}
		return null;
	}

	/**
	 * Obtiene modelos de coche por nombre (desde BD o MOCK)
	 */
	public ArrayList<ModeloCoche> obtenerModelosPorNombre(String nombre) {
		ArrayList<ModeloCoche> resultado = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idModelo, nombre, motor, potencia, peso, velocidadMaxima FROM modelosCoches WHERE nombre LIKE '%" + nombre + "%'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int idModelo = rs.getInt("idModelo");
					String nombreOb = rs.getString("nombre");
					String motor = rs.getString("motor");
					int potencia = rs.getInt("potencia");
					int peso = rs.getInt("peso");
					int velocidad = rs.getInt("velocidadMaxima");
					
					resultado.add(new ModeloCoche(idModelo, nombreOb, motor, potencia, peso, velocidad, null));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!resultado.isEmpty()) {
					return resultado;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al obtener modelos por nombre: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<ModeloCoche> todos = obtenerTodosModelos();
		for (ModeloCoche coche : todos) {
			if (coche.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
				resultado.add(coche);
			}
		}
		return resultado;
	}

	/**
	 * Obtiene modelos de coche por motor (desde BD o MOCK)
	 */
	public ArrayList<ModeloCoche> obtenerModelosPorMotor(String motor) {
		ArrayList<ModeloCoche> resultado = new ArrayList<>();
		
		try {
			Connection con = obtenerConexion();
			if (con != null) {
				String sql = "SELECT idModelo, nombre, motor, potencia, peso, velocidadMaxima FROM modelosCoches WHERE motor = '" + motor + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					int idModelo = rs.getInt("idModelo");
					String nombre = rs.getString("nombre");
					String motorOb = rs.getString("motor");
					int potencia = rs.getInt("potencia");
					int peso = rs.getInt("peso");
					int velocidad = rs.getInt("velocidadMaxima");
					
					resultado.add(new ModeloCoche(idModelo, nombre, motorOb, potencia, peso, velocidad, null));
				}
				
				rs.close();
				stmt.close();
				con.close();
				
				if (!resultado.isEmpty()) {
					return resultado;
				}
			}
		} catch (Exception e) {
			System.err.println("Error al obtener modelos por motor: " + e.getMessage());
		}
		
		// Fallback
		ArrayList<ModeloCoche> todos = obtenerTodosModelos();
		for (ModeloCoche coche : todos) {
			if (coche.getMotor().equalsIgnoreCase(motor)) {
				resultado.add(coche);
			}
		}
		return resultado;
	}

	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrarConexion() {
		// No hay conexión que cerrar
	}
}
