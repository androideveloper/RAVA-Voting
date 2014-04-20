package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rau.evoting.models.Election;
import com.rau.evoting.models.ElectionState;

public class ElectionDP {

	public static final String TABLE_NAME = "Elections";

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "descript";
	public static final String OPEN_STATE = "openState";
	public static final String CREATOR_ID = "creatorId";
	public static final String PUBLIC_KEY = "publicKey";

	public static Election getElection(int elId) {
		Connection con = null;
		Election elect = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select " 
					+ ID          + ","
					+ NAME        + ","
					+ DESCRIPTION + ","
					+ OPEN_STATE  + ","
					+ CREATOR_ID  + ","
					+ PUBLIC_KEY  
					+ " from " + TABLE_NAME + " where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				elect = new Election(elId, rs.getString(NAME),
						rs.getString(DESCRIPTION),
						states[rs.getInt(OPEN_STATE)], rs.getInt(CREATOR_ID),
						rs.getString(PUBLIC_KEY));
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
		return elect;
	}

	public static ArrayList<Election> loadElections() {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select " 
					+ ID          + ","
					+ NAME        + ","
					+ DESCRIPTION + ","
					+ OPEN_STATE  + ","
					+ CREATOR_ID  + ","
					+ PUBLIC_KEY  
					+ " from " + TABLE_NAME;
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Election el = new Election(rs.getInt(ID), rs.getString(NAME),
						rs.getString(DESCRIPTION),
						states[rs.getInt(OPEN_STATE)], rs.getInt(CREATOR_ID),
						rs.getString(PUBLIC_KEY));
				l.add(el);
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

	public static ArrayList<Election> loadOpenElections() {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select "  
					+ ID          + ","
					+ NAME        + ","
					+ DESCRIPTION + ","
					+ OPEN_STATE  + ","
					+ CREATOR_ID  + ","
					+ PUBLIC_KEY  
					+ " from " + TABLE_NAME 
					+ " where " + OPEN_STATE + " = 1";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Election el = new Election(rs.getInt(ID), rs.getString(NAME),
						rs.getString(DESCRIPTION), ElectionState.ONE,
						rs.getInt(CREATOR_ID), rs.getString(PUBLIC_KEY));
				l.add(el);
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

	public static ArrayList<Election> loadOpenElectionsforUser(int userId) {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "select * from " + TABLE_NAME + " as e"
					+ " where " + "e." + OPEN_STATE + " = 1 "
					+ " and not exists " 
					+ "(select * from " + ElectionVoterDP.TABLE_NAME + " where " + ElectionVoterDP.ELECTION_ID + " = e." + ID + ")"
					+ " and not exists " 
					+ "(select * from " + ElectionVoteDP.TABLE_NAME + " where " + ElectionVoteDP.ELECTION_ID + " = e." + ID + ")"
					+ " union all select e.* from " + TABLE_NAME + " as e "
					+ " join " +  ElectionVoterDP.TABLE_NAME + " as v "
					+ " on(e." + ID + " = v." + ElectionVoterDP.ELECTION_ID + ") "
					+ " join " + UserGroupDP.TABLE_NAME + " as u " 
					+ " on(u." + UserGroupDP.USER_ID + " = ? and u." + UserGroupDP.GROUP_ID +" = v." + ElectionVoterDP.VOTER_ID + ")"
					+ " where v." +  ElectionVoterDP.VOTER_TYPE + " = 0 and e. " + OPEN_STATE + " = 1 "
					+ " and not exists "  
					+ "(select * from " + ElectionVoteDP.TABLE_NAME + " where " + ElectionVoteDP.ELECTION_ID + " = e." + ID + ")";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Election el = new Election(rs.getInt(ID), rs.getString(NAME),
						rs.getString(DESCRIPTION), ElectionState.ONE,
						rs.getInt(CREATOR_ID), rs.getString(PUBLIC_KEY));
				l.add(el);
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

	public static int insert(Election el, int userId) {
		int id = 0;
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "insert into " + TABLE_NAME + "( " + NAME + ","
					+ DESCRIPTION + "," + OPEN_STATE + "," + CREATOR_ID
					+ ") values(?,?, 0,?)  select SCOPE_IDENTITY() as " + ID;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, el.getName());
			statement.setString(2, el.getDescription());
			statement.setInt(3, userId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
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

	public static void openElection(int elId, String publicKey) {
		Connection con = null;
		try {
			con = SqlDataProvider.getInstance().getConnection();

			String sql = "update " + TABLE_NAME + " set " + OPEN_STATE
					+ "= 1, " + PUBLIC_KEY + " = ? where " + ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, publicKey);
			statement.setInt(2, elId);
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
	}

	public static ArrayList<Election> getUserElections(int userId) {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = SqlDataProvider.getInstance().getConnection();
			String sql = "select * from " + TABLE_NAME + " where " + CREATOR_ID + "= ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Election el = new Election(rs.getInt(ID),
						rs.getString(NAME), rs.getString(DESCRIPTION),
						states[rs.getInt(OPEN_STATE)], userId,
						rs.getString(PUBLIC_KEY));
				l.add(el);
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
	
	public static Election getTrusteeElection(int id) {
		Connection con = null;
		Election el = null;
		ElectionState states[] = ElectionState.values();
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "select e." + ID + " as " + ID + "," 
					+ NAME + "," + DESCRIPTION + "," + OPEN_STATE + "," + CREATOR_ID 
					+ " from (select * from " + ElectionTrusteeDP.TABLE_NAME + " where " + ElectionTrusteeDP.ID + " = ?) as tr ";
			sql += " join " + TABLE_NAME + " as e on tr." + ElectionTrusteeDP.ELECTION_ID + " = e." + ID;
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				el = new Election(rs.getInt(ID), rs.getString(NAME),
						rs.getString(DESCRIPTION),
						states[rs.getInt(OPEN_STATE)],
						rs.getInt(CREATOR_ID), rs.getString(PUBLIC_KEY));
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
		return el;
	}
}
