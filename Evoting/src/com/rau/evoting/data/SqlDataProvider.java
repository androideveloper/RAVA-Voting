package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlDataProvider {
	protected String dbUrl;
	protected String username;
	protected String password;
	private static SqlDataProvider dataprovider;

	private SqlDataProvider() {
		this.dbUrl = "jdbc:jtds:sqlserver://localhost/Evoting;";
		this.username = "sa";
		this.password = "SaSa111";

		String driver = "net.sourceforge.jtds.jdbc.Driver";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static SqlDataProvider getInstance() {
		if (dataprovider == null) {
			dataprovider = new SqlDataProvider();
		}
		return dataprovider;
	}	

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
