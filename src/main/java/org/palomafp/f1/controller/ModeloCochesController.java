package org.palomafp.f1.controller;

import java.util.ArrayList;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.dao.ModeloCochesDAO;
import org.palomafp.f1.model.ModeloCoche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modelos")
@CrossOrigin(origins = "*")
public class ModeloCochesController {

	@Autowired
	private DatabaseConfig dbConfig;

	/**
	 * GET /api/modelos - Obtiene todos los modelos de coches
	 */
	@GetMapping
	public ResponseEntity<ArrayList<ModeloCoche>> obtenerTodos() {
		try {
			ModeloCochesDAO dao = new ModeloCochesDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<ModeloCoche> modelos = dao.obtenerTodosModelos();
			dao.cerrarConexion();
			return ResponseEntity.ok(modelos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/modelos/{id} - Obtiene un modelo por su ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ModeloCoche> obtenerPorId(@PathVariable int id) {
		try {
			ModeloCochesDAO dao = new ModeloCochesDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ModeloCoche modelo = dao.obtenerModeloPorId(id);
			dao.cerrarConexion();

			if (modelo != null) {
				return ResponseEntity.ok(modelo);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/modelos/nombre/{nombre} - Obtiene modelos por nombre
	 */
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<ArrayList<ModeloCoche>> obtenerPorNombre(@PathVariable String nombre) {
		try {
			ModeloCochesDAO dao = new ModeloCochesDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<ModeloCoche> modelos = dao.obtenerModelosPorNombre(nombre);
			dao.cerrarConexion();
			return ResponseEntity.ok(modelos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/modelos/motor/{motor} - Obtiene modelos por motor
	 */
	@GetMapping("/motor/{motor}")
	public ResponseEntity<ArrayList<ModeloCoche>> obtenerPorMotor(@PathVariable String motor) {
		try {
			ModeloCochesDAO dao = new ModeloCochesDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<ModeloCoche> modelos = dao.obtenerModelosPorMotor(motor);
			dao.cerrarConexion();
			return ResponseEntity.ok(modelos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
