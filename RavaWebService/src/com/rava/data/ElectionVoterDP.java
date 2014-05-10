package com.rava.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElectionVoterDP {

	public static final String TABLE_NAME = "ElectionVoters";
	
	public static final String ELECTION_ID = "electId";
	public static final String VOTER_TYPE = "voterType";
	public static final String VOTER_ID = "voterId";
	
	public static void setElectionVotersByGroup(int elId, String voterId) {
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "insert into " + TABLE_NAME + "( " + ELECTION_ID + "," 
					+ VOTER_TYPE + "," + VOTER_ID + ") values(?,0,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setString(2, voterId);
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

	public static String getElectionVoterByGroup(int elId) {
		String groupId = null;
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "select " + VOTER_ID + " from " + TABLE_NAME 
					+ " where " + ELECTION_ID + " = ? and " + VOTER_TYPE + " = 0 ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				groupId = rs.getString(VOTER_ID);
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
		return groupId;
	}

	public static boolean canVoteByGroup(int elId, String groupId) {
		String voterId = null;
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "select " + VOTER_ID + " from " + TABLE_NAME 
					+ " where " + ELECTION_ID + " = ? and " + VOTER_TYPE + " = 0 ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				voterId = rs.getString(VOTER_ID);
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
		if (groupId == null || groupId.equals(voterId)) {
			return true;
		}
		return false;
	}

	public static void deleteElectionVoters(int elId) {
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "delete from " + TABLE_NAME + " where " + ELECTION_ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
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
