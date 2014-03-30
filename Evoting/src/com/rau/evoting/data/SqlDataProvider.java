package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rau.evoting.models.*;
import com.restfb.types.Group;

public class SqlDataProvider {
	protected String dbUrl;
	protected String username;
	protected String password;
	private static SqlDataProvider dataprovider;

	private SqlDataProvider() {
		this.dbUrl = "jdbc:jtds:sqlserver://localhost/Evoting;instance=SQLEXPRESS";
		this.username = "sa";
		this.password = "sa";

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

	public int insertUser(String fbId, String email) {
		int id = 0;
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select * from Users where fbId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, fbId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			} else {
				sql = "insert into Users(fbId, email) values(?,?) select SCOPE_IDENTITY() as id";
				statement = con.prepareStatement(sql);
				statement.setString(1, fbId);
				statement.setString(2, email);
				rs = statement.executeQuery();

				if (rs.next()) {
					id = rs.getInt("id");
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

	public void insertUserGroups(int userId, List<Group> groups) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "delete from UserGroups where userId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.executeUpdate();
			for (Group gr : groups) {
				sql = "insert into UserGroups(userId,groupId) values(?,?) ";
				statement = con.prepareStatement(sql);
				statement.setInt(1, userId);
				statement.setString(2, gr.getId());
				statement.executeUpdate();
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
		return;
	}

	public User getUser(int id) {
		Connection con = null;
		User user = null;
		try {
			con = getConnection();

			String sql = "select * from Users where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt("id"),
						rs.getString("fbId"), rs.getString("email"));
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

	public Election getElection(int elId) {
		Connection con = null;
		Election elect = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = getConnection();

			String sql = "select * from Elections where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				elect = new Election(elId, rs.getString("name"),
						rs.getString("descript"),
						states[rs.getInt("openState")],
						rs.getInt("creatorId"), rs.getString("publicKey"));
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

	public ArrayList<Election> loadElections() {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = getConnection();

			String sql = "select * from Elections";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Election el = new Election(rs.getInt("id"),
						rs.getString("name"), rs.getString("descript"),
						states[rs.getInt("openState")],
						rs.getInt("creatorId"), rs.getString("publicKey"));
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

	public ArrayList<Election> loadOpenElections() {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = getConnection();

			String sql = "select * from Elections where openState = 1";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Election el = new Election(rs.getInt("id"),
						rs.getString("name"), rs.getString("descript"),
						ElectionState.ONE, rs.getInt("creatorId"), rs.getString("publicKey"));
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

	public ArrayList<Election> loadOpenElectionsforUser(int userId) {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = getConnection();

			String sql = "select * from Elections where openState = 1 and not exists( select * from ElectionVoters where electId = id) ";
			sql += "union all select e.* from Elections as e join ElectionVoters as v on(e.id = v.electId) join UserGroups as u on(u.userId = ? and u.groupId = v.voterId) where v.voterType = 0 and openState = 1 ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Election el = new Election(rs.getInt("id"),
						rs.getString("name"), rs.getString("descript"),
						ElectionState.ONE, rs.getInt("creatorId"),  rs.getString("publicKey"));
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

	public int insertElecttion(Election el, int userId) {
		int id = 0;
		Connection con = null;
		try {
			con = getConnection();

			String sql = "insert into Elections(name, descript,openState, creatorId) values(?,?, 0,?)  select SCOPE_IDENTITY() as id";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, el.getName());
			statement.setString(2, el.getDescription());
			statement.setInt(3, userId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("id");
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

	public void openElection(int elId, String publicKey) {
		Connection con = null;
		try {
			con = getConnection();

			String sql = "update Elections set openState = 1, publicKey = ? where id = ?";
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

	public ArrayList<Election> getUserElections(int userId) {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = getConnection();
			String sql = "select * from Elections where creatorId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Election el = new Election(rs.getInt("id"),
						rs.getString("name"), rs.getString("descript"),
						states[rs.getInt("openState")], userId, rs.getString("publicKey"));
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

	public ArrayList<Answer> getElectionAnswers(int elId) {
		ArrayList<Answer> l = new ArrayList<Answer>();
		Connection con = null;
		try {
			con = getConnection();

			String sql = "select * from ElectionAnswers where electId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Answer ans = new Answer(rs.getInt("answerId"),
						rs.getString("answer"));
				l.add(ans);
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

	public void insertAnswer(int elId, Answer answer) {

		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into ElectionAnswers(electId,answerId,answer) values(?,?,?)";
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

	public void removeAnswer(int elId, Answer answer) {

		Connection con = null;
		try {
			con = getConnection();
			String sql = "delete from ElectionAnswers where electId = ? and answerId = ?";
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

	public void insertAnswers(int elId, ArrayList<Answer> answers) {

		Connection con = null;
		try {
			con = getConnection();
			String deleteSql = "delete from ElectionAnswers where electId = ?";
			PreparedStatement delStat = con.prepareStatement(deleteSql);
			delStat.setInt(1, elId);
			delStat.executeUpdate();

			String sql = "insert into ElectionAnswers(electId,answerId,answer) values(?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			for (Answer ans : answers) {
				statement.setInt(2, ans.getId());
				statement.setString(3, ans.getAnswer());
				statement.executeUpdate();
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
		return;
	}

	public ArrayList<Trustee> getElectionNotTempTrustees(int elId) {
		ArrayList<Trustee> l = new ArrayList<Trustee>();
		Connection con = null;
		try {
			con = getConnection();

			String sql = "select * from ElectionTrustees where electId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Trustee tr = new Trustee(rs.getInt("trusteeId"),
						rs.getString("email"), rs.getBoolean("isGenerated"),
						rs.getString("token"));
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

	public ArrayList<Trustee> getElectionTrustees(int elId) {
		ArrayList<Trustee> l = new ArrayList<Trustee>();
		Connection con = null;
		try {
			con = getConnection();

			String sql = "select trusteeId,email,isGenerated,token,publicKey from ElectionTrustees where electId = ?";
			// sql +=
			// "union select id as trusteeId,email,0 as isGenerated from TempTrustees where electId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			// statement.setInt(2, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Trustee tr = new Trustee(rs.getInt("trusteeId"),
						rs.getString("email"), rs.getBoolean("isGenerated"),
						rs.getString("publicKey"), elId ,rs.getString("token"));
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

	public ArrayList<String> getElectionTrusteesPublicKeys(int elId) {
		ArrayList<String> l = new ArrayList<String>();
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select publicKey from ElectionTrustees where electId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				l.add(rs.getString("publicKey"));
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

	public Trustee getElectionTrustee(int id) {
		Trustee tr = null;
		Connection con = null;
		try {
			con = getConnection();

			String sql = "select trusteeId,email,isGenerated,publicKey,electId,token from ElectionTrustees where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				tr = new Trustee(rs.getInt("trusteeId"),
						rs.getString("email"), rs.getBoolean("isGenerated"),
						rs.getString("publicKey"), rs.getInt("electId"),
						rs.getString("token"));
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

	public int insertTrustee(int elId, Trustee trustee) {
		int id = 0;
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into ElectionTrustees(electId,trusteeId,email,isGenerated,token) values(?,?,?,?,?) select SCOPE_IDENTITY() as id";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, trustee.getId());
			statement.setString(3, trustee.getEmail());
			statement.setInt(4, trustee.isGenerated() ? 1 : 0);
			statement.setString(5, trustee.getToken());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
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

	public void updateTrustee(int id, Trustee trustee) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "update ElectionTrustees set trusteeId = ?, email = ? where id = ?";
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

	public void setTrusteeGenerated(int elId, int trId) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "update ElectionTrustees set isGenerated = 1 where electId = ? and trusteeId = ?";
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

	public void setTrusteePublicKey(String key, int id) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "update ElectionTrustees set publicKey = ?, isGenerated = 1 where id = ? ";
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

	public int getTrusteeElectionId(int id) {
		Connection con = null;
		int elId = 0;
		try {
			con = getConnection();
			String sql = "select electId from ElectionTrustees where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				elId = rs.getInt("electId");
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

	public Election getTrusteeElection(int id) {
		Connection con = null;
		Election el = null;
		ElectionState states[] = ElectionState.values();
		try {
			con = getConnection();
			String sql = "select e.id as id,name,descript,openState,creatorId from (select * from ElectionTrustees where id = ?) as tr ";
			sql += " join Elections as e on tr.electId = e.id ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				el = new Election(rs.getInt("id"), rs.getString("name"),
						rs.getString("descript"),
						states[rs.getInt("openState")],
						rs.getInt("creatorId"), rs.getString("publicKey"));
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

	public int insertTemtTrustee(int elId, String email) {
		Connection con = null;
		int id = 0;
		try {
			con = getConnection();
			String sql = "insert into TempTrustees(electId, email) values(?,?)  select SCOPE_IDENTITY() as id";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setString(2, email);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id");
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
			con = getConnection();
			String sql = "delete from TempTrustees where id = ?";
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
			con = getConnection();
			String sql = "select electId from TempTrustees where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, trId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				elId = rs.getInt("electId");
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

	public void setElectionVotersByGroup(int elId, String voterId) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into ElectionVoters(electId,voterType,voterId) values(?,0,?)";
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

	public String getElectionVoterByGroup(int elId) {
		String groupId = null;
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select voterId from ElectionVoters where electId = ? and voterType = 0 ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				groupId = rs.getString("voterId");
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

	public boolean canVoteByGroup(int elId, String groupId) {
		String voterId = null;
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select voterId from ElectionVoters where electId = ? and voterType = 0 ";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				voterId = rs.getString("voterId");
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

	public void deleteElectionVoters(int elId) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "delete from ElectionVoters where electId = ?";
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
	
	public int setElectionVote(int elId, int voterId, int auditBallot, String auditSequence, String encoded1, String encoded2, int answerId) {
		int id = 0;
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into ElectionVotes(electId,userId,auditBallot,auditSequence,encoded1,encoded2,answerId) values(?,?,?,?,?,?,?)" + 
					" select SCOPE_IDENTITY() as id";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, voterId);
			statement.setInt(3, auditBallot);
			statement.setString(4, auditSequence);
			statement.setString(5, encoded1);
			statement.setString(6, encoded2);
			statement.setInt(7, answerId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
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
	
	public Vote getVote(int id) {
		Vote vote = null;
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select * from ElectionVotes where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				vote = new Vote(rs.getInt("id"), rs.getInt("electId"), rs.getInt("userId"),
						rs.getInt("auditBallot"), rs.getString("encoded1"), rs.getString("encoded2"),
						rs.getString("auditSequence"), rs.getInt("answerId"));
						
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

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
