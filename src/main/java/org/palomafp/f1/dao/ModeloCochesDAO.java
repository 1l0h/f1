package org.palomafp.f1.dao;

import java.sql.*;
import java.util.ArrayList;

import org.palomafp.f1.model.ModeloCoche;

public class ModeloCochesDAO {
	ArrayList<ModeloCoche> coches;

	public ModeloCochesDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("url de la bd", "Usuario de la bd",
					"Contraseña del usuario de la bd");
			Statement declaracion = conexion.createStatement();
			ResultSet resultado = declaracion.executeQuery("select * from monoplaza");
			while (resultado.next()) {
				coches.add(new ModeloCoche(resultado.getInt("id_modelo"), resultado.getString("nombre"),
						resultado.getString("motor"), resultado.getInt("caballos"), resultado.getInt("velocidad_max"),
						resultado.getInt("peso"), resultado.getString("foto")));
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}
}
