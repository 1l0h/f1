package org.palomafp.f1.controller;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/f1/temporadas")
@CrossOrigin(origins = "*")
public class TemporadaController {



	/**
	 * GET /api/temporadas - Obtiene todas las temporadas
	 */
	@GetMapping
	public List<Temporada> obtenerTodas() {
		try {
			TemporadaDAO dao = new TemporadaDAO();
			List<Temporada> temporadas = dao.obtenerTodasTemporadas();
			return temporadas;
		} catch (Exception e) {
			System.out.println("No chuta");
			return null;
		}
	}

	/**
	 * GET /api/temporadas/{anio} - Obtiene una temporada por año
	 */
	@GetMapping("/{anio}")
	public ResponseEntity<Temporada> obtenerPorAnio(@PathVariable int anio) {
		try {
			TemporadaDAO dao = new TemporadaDAO();
			Temporada temporada = dao.obtenerTemporadaPorAnio(anio);
			if (temporada != null) {
				return ResponseEntity.ok(temporada);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
