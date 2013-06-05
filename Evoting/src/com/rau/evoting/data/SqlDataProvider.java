package com.rau.evoting.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rau.evoting.models.*;



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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
//	public SqlDataProvider(String dbUrl, String username, String password) {
//		this.dbUrl = dbUrl;
//		this.username = username;
//		this.password = password;
//	}

	public static SqlDataProvider getInstance(){
		if(dataprovider==null){
			dataprovider = new SqlDataProvider();
		}
		return dataprovider;
	}
	
	
	public User getUser(String username) {
		Connection con = null;
		User user = null;
		try {
			con = getConnection();
			
			String sql = "select * from Users where username = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				user = new User(rs.getString("username"),rs.getString("password"), rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return user;
	}
	
	public ArrayList<Candidate> loadCandidates(){
		ArrayList<Candidate> l = new ArrayList<Candidate>();
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "select * from Candidates";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Candidate cand = new Candidate(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),rs.getString("email") ,rs.getDate("dateOfBirth"));
				l.add(cand);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public Election getElection(int elId) {
		Connection con = null;
		Election elect = null;
		try {
			con = getConnection();
			
			String sql = "select * from Elections where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				elect = new Election(elId, rs.getString("name"), rs.getString("descript"));
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return elect;
	}
	
	public ArrayList<Election> loadElections(){
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "select * from Elections";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Election el = new Election(rs.getInt("id"), rs.getString("name"), rs.getString("descript"));
				l.add(el);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public ArrayList<Election> loadOpenElections(){
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "select * from Elections where isOpen = 1";
			PreparedStatement statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Election el = new Election(rs.getInt("id"), rs.getString("name"), rs.getString("descript"), true);
				l.add(el);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public Candidate getCandidate(int id){
		Candidate cand = null;
		Connection con = null;
		
		try {
			con = getConnection();
			
			String sql = "select * from Candidates where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				cand = new Candidate(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),rs.getString("email") ,rs.getDate("dateOfBirth"));
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if( con != null)
					con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cand;
	}
	
	public void insertCandidates(ArrayList<Candidate> cands){
		Connection con = null;
		String sql = "insert into Candidates(name,surname,dateOfBirth,email) values(?,?,?,?) select SCOPE_IDENTITY() as id";
		try {
			con = getConnection();
			
			PreparedStatement statement = con.prepareStatement(sql);
			java.sql.Date date;
			ResultSet rs = null;
			for (Candidate cand : cands) {
				statement.setString(1, cand.getName());
				statement.setString(2, cand.getSurname());
				//date = new java.sql.Date(cand.getDateOfBirth().getTime());
				statement.setDate(3, new java.sql.Date(cand.getDateOfBirth().getTime()));
				statement.setString(4, cand.getEmail());
				
				//statement.executeUpdate();
				 rs = statement.executeQuery();
				while (rs.next()) {
					cand.setId(rs.getInt("id"));
				}
				rs.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if( con != null)
					con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}				
	}
	
	public ArrayList<Candidate> getElectionCandidaets(int elId){
		ArrayList<Candidate> l = new ArrayList<Candidate>();
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "select * from Candidates join ElectionCandidates on id = candId where electid = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Candidate cand = new Candidate(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),rs.getString("email") ,rs.getTimestamp("dateOfBirth"));
				l.add(cand);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public int insertElecttion(Election el, String userId){
		int id = 0;
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "insert into Elections(name, descript,isOpen, userId) values(?,?, 0,?)  select SCOPE_IDENTITY() as id";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, el.getName());
			statement.setString(2, el.getDescription());
			statement.setString(3, userId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				id = rs.getInt("id");
				System.out.println(id);
			}
			rs.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return id;
	}
	
	public void openElection(int elId) {
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "update Elections set isOpen = 1 where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void setElectionCandidates(int elId, ArrayList<Candidate> cands) {
		Connection con = null;
		String sql = "insert into ElectionCandidates(electID,candId) values(?,?) ";
		try {
			con = getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			for (Candidate cand : cands) {
				statement.setInt(2, cand.getId());
				statement.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if( con != null)
					con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public ArrayList<Election> getUserElections(String username) {
		ArrayList<Election> l = new ArrayList<Election>();
		Connection con = null;
		try {
			con = getConnection();
			String sql = "select * from Elections where userId = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				boolean isOpen = rs.getInt("isOpen") == 1 ? true : false; 
				Election el = new Election(rs.getInt("id"), rs.getString("name"),rs.getString("descript"), isOpen);
				l.add(el);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public ArrayList<Answer> getElectionAnswers(int elId){
		ArrayList<Answer> l = new ArrayList<Answer>();
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "select * from ElectionAnswers where electid = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Answer ans = new Answer(rs.getInt("answerId"), rs.getString("answer"));
				l.add(ans);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public void insertAnswer(int elId, Answer answer){
	
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
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return ;
	}
	
	public void removeAnswer(int elId, Answer answer){
		
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
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return ;
	}
	
	public void insertAnswers(int elId, ArrayList<Answer> answers){
		
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
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return ;
	}
	
	public ArrayList<Trustee> getElectionTrustees(int elId){
		ArrayList<Trustee> l = new ArrayList<Trustee>();
		Connection con = null;
		try {
			con = getConnection();
			
			String sql = "select * from ElectionTrustees where electid = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Trustee tr = new Trustee(rs.getInt("trusteeId"), rs.getString("name"), rs.getString("email"), rs.getBoolean("isGenerated"));
				l.add(tr);
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return l;
	}
	
	public void insertTrustee(int elId, Trustee trustee){
		
		Connection con = null;
		try {
			con = getConnection();		
			String sql = "insert into ElectionTrustees(electId,trusteeId,name,email,isGenerated) values(?,?,?,?,0)";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, trustee.getId());
			statement.setString(3, trustee.getName());
			statement.setString(4, trustee.getEmail());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return ;
	}
	
	public void setTrusteeGenerated(int elId, int trId) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "update ElectionTrustees set isGenerated = 1 where electId = ? and trusteeID = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, elId);
			statement.setInt(2, trId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
