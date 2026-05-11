package org.palomafp.f1.dao;

import java.sql.*;
import java.util.ArrayList;

import org.palomafp.f1.model.Piloto;

public class PilotosDAO {
	ArrayList<Piloto> pilotos;

	public PilotosDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("url de la bd", "Usuario de la bd",
					"Contraseña del usuario de la bd");
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery("select * from piloto");
			while (resultado.next()) {
				pilotos.add(new Piloto(resultado.getInt("numero"), resultado.getString("nombre"),
						resultado.getString("apellido"), resultado.getString("nacionalidad"),
						resultado.getDate("fecha_nacimiento"), resultado.getInt("podios"),
						resultado.getInt("victorias"), resultado.getInt("campeonatos"), resultado.getInt("poles"),
						resultado.getString("foto")));
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}

	public Piloto getPilotoPorNumero(int numero) {
		Piloto resultado = null;
		for (int i = 0; i < pilotos.size(); i++) {
			if (pilotos.get(i).getNumero() == numero) {
				resultado = pilotos.get(i);
				break;
			}
		}
		return resultado;

	}

}
