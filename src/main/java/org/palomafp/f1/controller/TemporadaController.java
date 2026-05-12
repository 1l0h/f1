package org.palomafp.f1.controller;

import java.util.ArrayList;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.dao.TemporadaDAO;
import org.palomafp.f1.model.Temporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/temporadas")
@CrossOrigin(origins = "*")
public class TemporadaController {

	@Autowired
	private DatabaseConfig dbConfig;

	/**
	 * GET /api/temporadas - Obtiene todas las temporadas
	 */
	@GetMapping
	public ResponseEntity<ArrayList<Temporada>> obtenerTodas() {
		try {
			TemporadaDAO dao = new TemporadaDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			ArrayList<Temporada> temporadas = dao.obtenerTodasTemporadas();
			dao.cerrarConexion();
			return ResponseEntity.ok(temporadas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/temporadas/{anio} - Obtiene una temporada por año
	 */
	@GetMapping("/{anio}")
	public ResponseEntity<Temporada> obtenerPorAnio(@PathVariable int anio) {
		try {
			TemporadaDAO dao = new TemporadaDAO(dbConfig.getUrl(), dbConfig.getUsuario(),
					dbConfig.getContrasena());
			Temporada temporada = dao.obtenerTemporadaPorAnio(anio);
			dao.cerrarConexion();

			if (temporada != null) {
				return ResponseEntity.ok(temporada);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
