package org.palomafp.f1.controller;

import java.util.ArrayList;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.dao.EscuderiaDAO;
import org.palomafp.f1.model.Escuderia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/f1/escuderias")
@CrossOrigin(origins = "*")
public class EscuderiaController {

	@Autowired
	private DatabaseConfig dbConfig;

	/**
	 * GET /api/escuderias - Obtiene todas las escuderías
	 */
	@GetMapping
	public ResponseEntity<ArrayList<Escuderia>> obtenerTodas() {
		try {
			EscuderiaDAO dao = new EscuderiaDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<Escuderia> escuderias = dao.obtenerTodasEscuderias();
			dao.cerrarConexion();
			return ResponseEntity.ok(escuderias);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/escuderias/{id} - Obtiene una escudería por su ID
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Escuderia> obtenerPorId(@PathVariable int id) {
		try {
			EscuderiaDAO dao = new EscuderiaDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			Escuderia escuderia = dao.obtenerEscuderiaPorId(id);
			dao.cerrarConexion();

			if (escuderia != null) {
				return ResponseEntity.ok(escuderia);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/escuderias/nombre/{nombre} - Obtiene una escudería por nombre
	 */
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<Escuderia> obtenerPorNombre(@PathVariable String nombre) {
		try {
			EscuderiaDAO dao = new EscuderiaDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			Escuderia escuderia = dao.obtenerEscuderiaPorNombre(nombre);
			dao.cerrarConexion();

			if (escuderia != null) {
				return ResponseEntity.ok(escuderia);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
