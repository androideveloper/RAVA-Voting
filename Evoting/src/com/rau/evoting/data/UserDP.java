package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rau.evoting.models.User;

public class UserDP {

	public static final String TABLE_NAME = "Users";

	public static final String ID = "id";
	public static final String FB_ID = "fbId";
	public static final String EMAIL = "email";
	
	public static int insert(String fbId, String email) {
		int id = 0;
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "select "  + ID + "," + FB_ID + "," + EMAIL + " from " + TABLE_NAME  + " where " + FB_ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, fbId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				id = rs.getInt(ID);
			} else {
				sql = "insert into " + TABLE_NAME + "(" + FB_ID + "," +  EMAIL 
						+ ") values(?,?) select SCOPE_IDENTITY() as " + ID;
				statement = con.prepareStatement(sql);
				statement.setString(1, fbId);
				statement.setString(2, email);
				rs = statement.executeQuery();

				if (rs.next()) {
					id = rs.getInt(ID);
				}
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
	
	public static User getUser(int id) {
		Connection con = null;
		User user = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select "  + ID + "," + FB_ID + "," + EMAIL + " from " + TABLE_NAME + " where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt(ID),
						rs.getString(FB_ID), rs.getString(EMAIL));
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
		return user;
	}
	
}
