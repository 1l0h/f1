package org.palomafp.f1.controller;

import java.util.ArrayList;

import org.palomafp.f1.config.DatabaseConfig;
import org.palomafp.f1.dao.PilotosDAO;
import org.palomafp.f1.model.Piloto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/f1/pilotos")
@CrossOrigin(origins = "*")
public class PilotosController {

	@Autowired
	private DatabaseConfig dbConfig;

	/**
	 * GET /api/pilotos - Obtiene todos los pilotos
	 */
	@GetMapping
	public ResponseEntity<ArrayList<Piloto>> obtenerTodos() {
		try {
			PilotosDAO dao = new PilotosDAO(dbConfig.getUrl(), dbConfig.getUsuario(), dbConfig.getContrasena());
			ArrayList<Piloto> pilotos = dao.obtenerTodosPilotos();
			dao.cerrarConexion();
			return ResponseEntity.ok(pilotos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/pilotos/{numero} - Obtiene un piloto por su número
	 */
	@GetMapping("/{numero}")
	public ResponseEntity<Piloto> obtenerPorNumero(@PathVariable int numero) {
		try {
			PilotosDAO dao = new PilotosDAO(dbConfig.getUrl(), dbConfig.getUsuario(), dbConfig.getContrasena());
			Piloto piloto = dao.obtenerPilotoPorNumero(numero);
			dao.cerrarConexion();
			
			if (piloto != null) {
				return ResponseEntity.ok(piloto);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/pilotos/nacionalidad/{nacionalidad} - Obtiene pilotos por nacionalidad
	 */
	@GetMapping("/nacionalidad/{nacionalidad}")
	public ResponseEntity<ArrayList<Piloto>> obtenerPorNacionalidad(@PathVariable String nacionalidad) {
		try {
			PilotosDAO dao = new PilotosDAO(dbConfig.getUrl(), dbConfig.getUsuario(), dbConfig.getContrasena());
			ArrayList<Piloto> pilotos = dao.obtenerPilotosPorNacionalidad(nacionalidad);
			dao.cerrarConexion();
			return ResponseEntity.ok(pilotos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * GET /api/pilotos/apellido/{apellido} - Obtiene pilotos por apellido
	 */
	@GetMapping("/apellido/{apellido}")
	public ResponseEntity<ArrayList<Piloto>> obtenerPorApellido(@PathVariable String apellido) {
		try {
			PilotosDAO dao = new PilotosDAO(dbConfig.getUrl(), dbConfig.getUsuario(), dbConfig.getContrasena());
			ArrayList<Piloto> pilotos = dao.obtenerPilotosPorApellido(apellido);
			dao.cerrarConexion();
			return ResponseEntity.ok(pilotos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
