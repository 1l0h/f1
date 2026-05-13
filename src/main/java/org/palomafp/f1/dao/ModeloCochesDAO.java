package org.palomafp.f1.dao;

import java.util.ArrayList;

import org.palomafp.f1.model.ModeloCoche;

public class ModeloCochesDAO {

	public ModeloCochesDAO(String url, String usuario, String contrasena) {
		// Constructor adaptado para modo mock - no se conecta a BD
	}

	/**
	 * Obtiene todos los modelos de coches (DATOS MOCK)
	 */
	public ArrayList<ModeloCoche> obtenerTodosModelos() {
		ArrayList<ModeloCoche> coches = new ArrayList<>();

		coches.add(new ModeloCoche(1, "McLaren MCL38", "Mercedes", 900, 340, 798, null));
		coches.add(new ModeloCoche(2, "Mercedes W15", "Mercedes", 900, 342, 798, null));
		coches.add(new ModeloCoche(3, "Ferrari SF-24", "Ferrari", 900, 340, 798, null));
		coches.add(new ModeloCoche(4, "Red Bull RB20", "Honda RBPT", 900, 341, 798, null));
		coches.add(new ModeloCoche(5, "Aston Martin AMR24", "Mercedes", 900, 339, 798, null));

		return coches;
	}

	/**
	 * Obtiene un modelo de coche por su ID (DATOS MOCK)
	 */
	public ModeloCoche obtenerModeloPorId(int idModelo) {
		ArrayList<ModeloCoche> todos = obtenerTodosModelos();
		for (ModeloCoche coche : todos) {
			if (coche.getIdModelo() == idModelo) {
				return coche;
			}
		}
		return null;
	}

	/**
	 * Obtiene modelos de coche por nombre (DATOS MOCK)
	 */
	public ArrayList<ModeloCoche> obtenerModelosPorNombre(String nombre) {
		ArrayList<ModeloCoche> resultado = new ArrayList<>();
		ArrayList<ModeloCoche> todos = obtenerTodosModelos();
		for (ModeloCoche coche : todos) {
			if (coche.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
				resultado.add(coche);
			}
		}
		return resultado;
	}

	/**
	 * Obtiene modelos de coche por motor (DATOS MOCK)
	 */
	public ArrayList<ModeloCoche> obtenerModelosPorMotor(String motor) {
		ArrayList<ModeloCoche> resultado = new ArrayList<>();
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
		// No hay conexión que cerrar en modo mock
	}
}
