package org.palomafp.f1.controller;

import java.util.ArrayList;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.dao.ParticipacionDAO;
import org.palomafp.f1.model.Participacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/participaciones")
@CrossOrigin(origins = "*")
public class ParticipacionController {

	@Autowired
	private DatabaseConfig dbConfig;

	/**
	 * GET /api/participaciones - Obtiene todas las participaciones
	 */
	@GetMapping
	public ResponseEntity<ArrayList<Participacion>> obtenerTodas() {
		try {
			ParticipacionDAO dao = new ParticipacionDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<Participacion> participaciones = dao.obtenerTodasParticipaciones();
			dao.cerrarConexion();
			return ResponseEntity.ok(participaciones);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/participaciones/piloto/{numero} - Obtiene participaciones de un piloto
	 */
	@GetMapping("/piloto/{numero}")
	public ResponseEntity<ArrayList<Participacion>> obtenerPorPiloto(@PathVariable int numero) {
		try {
			ParticipacionDAO dao = new ParticipacionDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<Participacion> participaciones = dao.obtenerParticipacionesPiloto(numero);
			dao.cerrarConexion();
			return ResponseEntity.ok(participaciones);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/participaciones/granpremio/{id} - Obtiene participaciones de un GP
	 */
	@GetMapping("/granpremio/{id}")
	public ResponseEntity<ArrayList<Participacion>> obtenerPorGranPremio(@PathVariable int id) {
		try {
			ParticipacionDAO dao = new ParticipacionDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<Participacion> participaciones = dao.obtenerParticipacionesGranPremio(id);
			dao.cerrarConexion();
			return ResponseEntity.ok(participaciones);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
