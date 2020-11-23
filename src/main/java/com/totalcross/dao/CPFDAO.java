package com.totalcross.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.totalcross.util.DatabaseManager;

import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class CPFDAO {

    public boolean insertCPF(String cpf) throws SQLException {
		boolean success = false;
		Connection dbcon = DatabaseManager.getConnection();
		String sql = "insert into person values(?)";
		PreparedStatement ps = dbcon.prepareStatement(sql);
		ps.setString(1, cpf);

		int i = ps.executeUpdate();
		ps.close();

		if (i > 0) {
			success = true;
		} else {
			success = false;
		}
		return success;
	}

	public void updateCPF(String cpf_new, String cpf_old) throws SQLException {
		Connection dbcon = DatabaseManager.getConnection();
		String sql = "UPDATE person SET cpf = " + cpf_old + " WHERE cpf = " + cpf_new;
		PreparedStatement ps = dbcon.prepareStatement(sql);
		ps.executeUpdate();
	}

	public void deleteCPF(String cpf) throws SQLException {
		Connection dbcon = DatabaseManager.getConnection();
		String sql = "DELETE from person where cpf = ?";
		PreparedStatement ps = dbcon.prepareStatement(sql);
		ps.setString(1, cpf);
		ps.executeUpdate();
	}

	public ArrayList<String> getCPF() throws SQLException {
		ArrayList<String> cpfs = new ArrayList<>();

		try {
			Connection dbcon = DatabaseManager.getConnection();
			Statement st = dbcon.createStatement();
			ResultSet rs = st.executeQuery("select * from person");

			while (rs.next()) {
				String cpf = rs.getString("cpf");
				cpfs.add(cpf);
			}
			rs.close();
			st.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		return cpfs;
	}
}