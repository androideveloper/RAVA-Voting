package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rau.evoting.models.Trustee;

public class ElectionTrusteeDP {

	public static final String TABLE_NAME = "ElectionTrustees";

	public static final String ID = "id";
	public static final String ELECTION_ID = "electId";
	public static final String USER_ID = "userId";
	public static final String EMAIL = "email";
	public static final String IS_GENERATED = "isGenerated";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String TOKEN = "token";
	public static final String IS_DECODED = "isDecoded";
	public static final String MIX_DONE = "mixDone";
	public static final String MIX_SERVER = "mixServer";

	/*
	 * public static ArrayList<Trustee> getElectionNotTempTrustees(int elId) {
	 * ArrayList<Trustee> l = new ArrayList<Trustee>(); Connection con = null;
	 * try { con = SqlDataProvider.getInstance().getConnection();
	 * 
	 * String sql = "select " + USER_ID + "," + EMAIL + "," + IS_GENERATED + ","
	 * + TOKEN + " from " + TABLE_NAME + " where " + ELECTION_ID + " = ?";
	 * PreparedStatement statement = con.prepareStatement(sql);
	 * statement.setInt(1, elId); ResultSet rs = statement.executeQuery();
	 * 
	 * while (rs.next()) { Trustee tr = new Trustee(rs.getInt(USER_ID),
	 * rs.getString(EMAIL), rs.getBoolean(IS_GENERATED), rs.getString(TOKEN));
	 * l.add(tr); } rs.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } finally { try { if (con
	 * != null) { con.close(); } } catch (SQLException e) { } } return l; }
	 */

	public static ArrayList<Trustee> getElectionTrustees(int elId) {
		ArrayList<Trustee> l = new ArrayList<Trustee>();
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select " + ID + "," + USER_ID + "," + EMAIL + ","
					+ IS_GENERATED + "," + TOKEN + "," + PUBLIC_KEY + ","
					+ IS_DECODED + "," + MIX_DONE + "," + MIX_SERVER + " from "
					+ TABLE_NAME + " where " + ELECTION_ID + " = ?";
			// sql +=
			// "union select id as trusteeId,email,0 as isGenerated from TempTrustees where electId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			// statement.setInt(2, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Trustee tr = new Trustee(rs.getInt(ID), rs.getInt(USER_ID),
						rs.getString(EMAIL), rs.getBoolean(IS_GENERATED),
						rs.getString(PUBLIC_KEY), elId, rs.getString(TOKEN),
						rs.getBoolean(IS_DECODED), rs.getBoolean(MIX_DONE),
						rs.getInt(MIX_SERVER));
				l.add(tr);
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
		return l;
	}

	public static ArrayList<String> getElectionTrusteesPublicKeys(int elId) {
		ArrayList<String> l = new ArrayList<String>();
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "select " + PUBLIC_KEY + " from " + TABLE_NAME
					+ " where " + ELECTION_ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				l.add(rs.getString(PUBLIC_KEY));
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
		return l;
	}

	public static Trustee getElectionTrustee(int id) {
		Trustee tr = null;
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select " + ID + "," + USER_ID + "," + EMAIL + ","
					+ IS_GENERATED + "," + PUBLIC_KEY + "," + ELECTION_ID + ","
					+ TOKEN + "," + IS_DECODED + "," + MIX_DONE + ","
					+ MIX_SERVER + " from " + TABLE_NAME + " where " + ID
					+ " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				tr = new Trustee(rs.getInt(ID), rs.getInt(USER_ID),
						rs.getString(EMAIL), rs.getBoolean(IS_GENERATED),
						rs.getString(PUBLIC_KEY), rs.getInt(ELECTION_ID),
						rs.getString(TOKEN), rs.getBoolean(IS_DECODED),
						rs.getBoolean(MIX_DONE), rs.getInt(MIX_SERVER));
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
		return tr;
	}

	public static int insertTrustee(int elId, Trustee trustee) {
		int id = 0;
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "insert into " + TABLE_NAME + "(" + ELECTION_ID + ","
					+ USER_ID + "," + EMAIL + "," + IS_GENERATED + "," + TOKEN
					+ "," + MIX_DONE + "," + MIX_SERVER
					+ ") values(?,?,?,?,?,?,?) select SCOPE_IDENTITY() as " + ID;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, trustee.getId());
			statement.setString(3, trustee.getEmail());
			statement.setInt(4, trustee.isGenerated() ? 1 : 0);
			statement.setString(5, trustee.getToken());
			statement.setInt(6, trustee.isMixDone() ? 1 : 0);
			statement.setInt(7, trustee.getMixServer());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				id = rs.getInt(ID);
				/*sql = "update " + TABLE_NAME + " set " + MIX_SERVER + " = "
						+ ID + " where " + ID + " = ? ";
				statement = con.prepareStatement(sql);
				statement.setInt(1, id);
				statement.executeUpdate();*/
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

	public static void updateTrustee(int id, Trustee trustee) {
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "update " + TABLE_NAME + " set " + USER_ID + " = ?, "
					+ EMAIL + " = ? where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, trustee.getId());
			statement.setString(2, trustee.getEmail());
			statement.setInt(3, id);
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

	public static void setTrusteeGenerated(int elId, int trId) {
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "update " + TABLE_NAME + " set " + IS_GENERATED
					+ " = 1 where " + ELECTION_ID + " = ? and " + USER_ID
					+ " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, trId);
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

	public static void setTrusteePublicKey(String key, int id) {
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "update " + TABLE_NAME + " set " + PUBLIC_KEY
					+ " = ?, " + IS_GENERATED + " = 1 where " + ID + " = ? ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, key);
			statement.setInt(2, id);
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

	public static boolean setTrusteeDecoded(int trId, int elId) {
		Connection con = null;
		boolean allDecoded = false;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "update " + TABLE_NAME + " set " + IS_DECODED
					+ " = 1 where " + ID + " = ? ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, trId);
			statement.executeUpdate();

			sql = "select * from " + TABLE_NAME + " where " + IS_DECODED
					+ " = 0 and " + ELECTION_ID + " = ? ";
			statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			if (!rs.next()) {
				allDecoded = true;
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
		return allDecoded;
	}

	public static int getTrusteeElectionId(int id) {
		Connection con = null;
		int elId = 0;
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "select " + ELECTION_ID + " from " + TABLE_NAME
					+ " where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
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
	
	public static Trustee getTrusteeByMixServer(int elId, int mixServer) {
		Trustee tr = null;
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select " + ID + "," + USER_ID + "," + EMAIL + ","
					+ IS_GENERATED + "," + PUBLIC_KEY + "," + ELECTION_ID + ","
					+ TOKEN + "," + IS_DECODED + "," + MIX_DONE + ","
					+ MIX_SERVER + " from " + TABLE_NAME + " where " + MIX_SERVER
					+ " = ? and " + ELECTION_ID + " = ? ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, mixServer);
			statement.setInt(2, elId);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				tr = new Trustee(rs.getInt(ID), rs.getInt(USER_ID),
						rs.getString(EMAIL), rs.getBoolean(IS_GENERATED),
						rs.getString(PUBLIC_KEY), rs.getInt(ELECTION_ID),
						rs.getString(TOKEN), rs.getBoolean(IS_DECODED),
						rs.getBoolean(MIX_DONE), rs.getInt(MIX_SERVER));
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
		return tr;
	}

	public static void deleteTrustee(int id) {
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();
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

}
