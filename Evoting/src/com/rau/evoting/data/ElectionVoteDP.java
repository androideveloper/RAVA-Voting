package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rau.evoting.models.CutVote;
import com.rau.evoting.models.Vote;

public class ElectionVoteDP {

	public static final String TABLE_NAME = "ElectionVotes";
	public static final String CUT_TABLE_NAME = "CutVotes";
	
	public static final String ID = "id";
	public static final String ELECTION_ID = "electId";
	public static final String USER_ID = "userId";
	public static final String AUDIT_BALLOT = "auditBallot";
	public static final String AUDIT_SEQUENCE = "auditSequence";
	public static final String ENCODED1 = "encoded1";
	public static final String ENCODED2 = "encoded2";
	public static final String ANSWER_ID = "answerId";
	public static final String CHAUM_PEDERSEN = "chaumPedersen";
	
	public static final String ANSWER_SEQUENCE = "answersSequence";
	
	
	public static int setElectionVote(int elId, int voterId, int auditBallot, String auditSequence, 
			String encoded1, String encoded2, int answerId, String chaumPedersen){ 
		int id = 0;
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			
			String sql = "select * from " + TABLE_NAME + " where " + ELECTION_ID + " = ? and "
					+ USER_ID + " = ? ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, voterId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				return -1;
			}
			
			sql = "insert into " + TABLE_NAME + "( "
					+ ELECTION_ID + "," + USER_ID +  "," + AUDIT_BALLOT +  "," 
					+ AUDIT_SEQUENCE + "," + ENCODED1 + "," + ENCODED2 + "," + ANSWER_ID 
					+ "," + CHAUM_PEDERSEN  
					+ ") values(?,?,?,?,?,?,?,?,?)" 
					+ " select SCOPE_IDENTITY() as " + ID;
			statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, voterId);
			statement.setInt(3, auditBallot);
			statement.setString(4, auditSequence);
			statement.setString(5, encoded1);
			statement.setString(6, encoded2);
			statement.setInt(7, answerId);
			statement.setString(8, chaumPedersen);
			rs = statement.executeQuery();
			if(rs.next()) {
				id = rs.getInt(ID);
			}
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
	
	public static Vote getVote(int id) {
		Vote vote = null;
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "select * from " + TABLE_NAME + " where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				vote = new Vote(rs.getInt(ID), rs.getInt(ELECTION_ID), rs.getInt(USER_ID),
						rs.getInt(AUDIT_BALLOT), rs.getString(ENCODED1), rs.getString(ENCODED2),
						rs.getString(AUDIT_SEQUENCE), rs.getInt(ANSWER_ID),
						rs.getString(CHAUM_PEDERSEN));
			}
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
		return vote;
	}
	
	public static void cutVotes(int elId) {
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "insert into " + CUT_TABLE_NAME 
					+ "(" + ELECTION_ID + "," + ANSWER_SEQUENCE + "," + ANSWER_ID + ") "
					+ " (select " + ELECTION_ID +"," + ENCODED1 + "," + ANSWER_ID
					+ " from " + TABLE_NAME + " where " + ELECTION_ID + "  = ? "
						+ " and " + AUDIT_BALLOT + " = 2 "
					+ " union all select " + ELECTION_ID +"," + ENCODED2 + "," + ANSWER_ID
					+ " from " + TABLE_NAME + " where " + ELECTION_ID + "  = ? "
						+ " and " + AUDIT_BALLOT + " = 1 ) ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, elId);
			statement.executeUpdate();
			
			sql = " update " + ElectionDP.TABLE_NAME + " set " + ElectionDP.OPEN_STATE + " = 2 "
					+ " where id = ? ";
			statement = con.prepareStatement(sql);
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
		return ;
	}
	
	public static ArrayList<CutVote> getCutVotes(int elId){
		ArrayList<CutVote> l = new ArrayList<CutVote>();
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();

			String sql = "select "  + ELECTION_ID + "," +  ANSWER_SEQUENCE + "," + ANSWER_ID 
					 + " from " + CUT_TABLE_NAME + " where " + ELECTION_ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				CutVote vote = new CutVote(rs.getInt(ELECTION_ID),
						rs.getString(ANSWER_SEQUENCE), rs.getInt(ANSWER_ID));
				l.add(vote);
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
	
	public static void updateCutVotes(ArrayList<CutVote> votes, int elId) {
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			con.setAutoCommit(false);
			String deleteSql = "delete from " + CUT_TABLE_NAME + " where " + ELECTION_ID + " = ?";
			PreparedStatement delStat = con.prepareStatement(deleteSql);
			delStat.setInt(1, elId);
			delStat.executeUpdate();

			String sql = "insert into " + CUT_TABLE_NAME + "(" + ELECTION_ID + "," + ANSWER_SEQUENCE + "," 
					+ ANSWER_ID + ") values(?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			for (CutVote vote : votes) {
				statement.setString(2, vote.getAnswersSequence());
				statement.setInt(3, vote.getAnswerId());
				statement.executeUpdate();
			}
			con.commit();
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
