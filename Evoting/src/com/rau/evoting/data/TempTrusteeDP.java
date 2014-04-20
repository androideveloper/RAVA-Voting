package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TempTrusteeDP {

	public static final String TABLE_NAME = "TempTrustees";
	
	public static final String ID = "id";
	public static final String ELECTION_ID = "electId";
	public static final String EMAIL = "email";
	
	public int insertTemtTrustee(int elId, String email) {
		Connection con = null;
		int id = 0;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "insert into " + TABLE_NAME + "(" + ELECTION_ID + "," +  EMAIL 
					+ ") values(?,?)  select SCOPE_IDENTITY() as " + ID;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setString(2, email);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				id = rs.getInt(ID);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public void deleteTempTrustee(int id) {
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "delete from " + TABLE_NAME + " where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	public int getTempTrusteeElectionId(int trId) {
		Connection con = null;
		int elId = 0;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "select " + ELECTION_ID + " from " + TABLE_NAME + " where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, trId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				elId = rs.getInt(ELECTION_ID);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return elId;
	}
	
}
