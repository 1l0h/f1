package org.palomafp.f1.controller;

import java.util.ArrayList;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.dao.GranPremioDAO;
import org.palomafp.f1.model.GranPremio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/granpremios")
@CrossOrigin(origins = "*")
public class GranPremioController {

	@Autowired
	private DatabaseConfig dbConfig;

	/**
	 * GET /api/granpremios - Obtiene todos los grandes premios
	 */
	@GetMapping
	public ResponseEntity<ArrayList<GranPremio>> obtenerTodos() {
		try {
			GranPremioDAO dao = new GranPremioDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<GranPremio> gps = dao.obtenerTodosGrandesPremios();
			dao.cerrarConexion();
			return ResponseEntity.ok(gps);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/granpremios/{id} - Obtiene un GP por su ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<GranPremio> obtenerPorId(@PathVariable int id) {
		try {
			GranPremioDAO dao = new GranPremioDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			GranPremio gp = dao.obtenerGranPremioPorId(id);
			dao.cerrarConexion();

			if (gp != null) {
				return ResponseEntity.ok(gp);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/granpremios/nombre/{nombre} - Obtiene un GP por nombre
	 */
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<GranPremio> obtenerPorNombre(@PathVariable String nombre) {
		try {
			GranPremioDAO dao = new GranPremioDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			GranPremio gp = dao.obtenerGranPremioPorNombre(nombre);
			dao.cerrarConexion();

			if (gp != null) {
				return ResponseEntity.ok(gp);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/granpremios/ubicacion/{ubicacion} - Obtiene GPs por ubicación
	 */
	@GetMapping("/ubicacion/{ubicacion}")
	public ResponseEntity<ArrayList<GranPremio>> obtenerPorUbicacion(@PathVariable String ubicacion) {
		try {
			GranPremioDAO dao = new GranPremioDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<GranPremio> gps = dao.obtenerGrandesPremiosPorUbicacion(ubicacion);
			dao.cerrarConexion();
			return ResponseEntity.ok(gps);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
