package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rau.evoting.models.Answer;

public class ElectonAnswerDP {

	public static final String TABLE_NAME = "ElectionAnswers";
	
	public static final String ELECTION_ID = "electId";
	public static final String ANSWER_ID = "answerId";
	public static final String ANSWER = "answer";
	
	public static ArrayList<Answer> getElectionAnswers(int elId) {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();

			String sql = "select * from " + TABLE_NAME + " where " + ELECTION_ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Answer ans = new Answer(rs.getInt(ANSWER_ID),
						rs.getString(ANSWER));
				answers.add(ans);
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
		return answers;
	}

	public static void insertAnswer(int elId, Answer answer) {

		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "insert into " + TABLE_NAME + "(" + ELECTION_ID + "," + ANSWER_ID + "," 
					+ ANSWER + ") values(?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, answer.getId());
			statement.setString(3, answer.getAnswer());
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

	public static void removeAnswer(int elId, Answer answer) {

		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			String sql = "delete from " + TABLE_NAME + " where " + ELECTION_ID + " = ? + and " + ANSWER_ID + " = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, answer.getId());
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

	public static void insertAnswers(int elId, ArrayList<Answer> answers) {

		Connection con = null;
		try {
			con =  SqlDataProvider.getInstance().getConnection();
			con.setAutoCommit(false);
			String deleteSql = "delete from " + TABLE_NAME + " where " + ELECTION_ID + " = ?";
			PreparedStatement delStat = con.prepareStatement(deleteSql);
			delStat.setInt(1, elId);
			delStat.executeUpdate();

			String sql = "insert into " + TABLE_NAME + "(" + ELECTION_ID + "," + ANSWER_ID + "," 
					+ ANSWER + ") values(?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			for (Answer ans : answers) {
				statement.setInt(2, ans.getId());
				statement.setString(3, ans.getAnswer());
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
