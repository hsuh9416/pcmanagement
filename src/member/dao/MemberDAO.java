package member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import member.bean.MemberDTO;

public class MemberDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.6.27:1521:xe";
	//private String url = "jdbc:oracle:thin:@192.168.6.20:1521:xe";
	private String user = "java";
	private String password = "itbank";
	@SuppressWarnings("unused")
	private int seatNum;
	
	private static MemberDAO instance;
	
	public static MemberDAO getInstance() {
		if(instance==null) {
			synchronized (MemberDAO.class) {
				instance = new MemberDAO();
			}
		}
		return instance;
	}
	
	public MemberDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	
	public int idCheck(String id) {
		int result = 0 ;
		String sql = "select * from pcmanager where id=?";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getString("id").equals(id)) result=1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}//idCheck()

	public void regist(MemberDTO memberDTO) {
		String sql = "insert into pcmanager(id,pw,name,tel1,tel2,tel3,email1,email2) values(?,?,?,?,?,?,?,?)";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPw());
			pstmt.setString(3, memberDTO.getUserName());
			pstmt.setString(4, memberDTO.getTel1());
			pstmt.setString(5, memberDTO.getTel2());
			pstmt.setString(6, memberDTO.getTel3());
			pstmt.setString(7, memberDTO.getEmaiL1());
			pstmt.setString(8, memberDTO.getEmail2());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}//regist()

	public String searchID(String name, String email1, String email2) {
		String id = null;
		String sql = "select * from pcmanager where name=? and email1=? and email2=?";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name);
			pstmt.setString(2, email1);
			pstmt.setString(3, email2);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				id = rs.getString("id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}//searchID()

	public String searchPW(String id, String name, String email1, String email2) {
		String pw = null;
		String sql = "select * from pcmanager where id=? and name=? and email1=? and email2=?";
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, email1);
			pstmt.setString(4, email2);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pw = rs.getString("pw");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pw;
	}

	public int login(String id, String pw) {
		int result = 0;
		String dbID=null, dbPW=null;
		String sql = "select * from pcmanager where id=? and pw=?";
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dbID = rs.getString("id");
				dbPW = rs.getString("pw");
			}
			if(id.equals(dbID) && pw.equals(dbPW))
				result=1;
			else 
				result=0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int nonlogin(String nonmem) {
		int result = 0;
		String dbID=null;
		String sql = "select * from pcmanager where id=?";
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nonmem);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dbID = rs.getString("id");
			}
			if(nonmem.equals(dbID)) result=1;
			else result=0;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public MemberDTO getMemberInfo(String id, String pw) {
		MemberDTO memberDTO = new MemberDTO();
		String sql = "select * from pcmanager where id=? and pw=?";
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memberDTO.setId(rs.getString("id"));
				memberDTO.setPw(rs.getString("pw"));
				memberDTO.setUserName(rs.getString("name"));
				memberDTO.setTel1(rs.getString("tel1"));
				memberDTO.setTel2(rs.getString("tel2"));
				memberDTO.setTel3(rs.getString("tel3"));
				memberDTO.setEmaiL1(rs.getString("email1"));
				memberDTO.setEmail2(rs.getString("email2"));
				memberDTO.setTime(rs.getInt("time"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}//getUserInfo()

	public MemberDTO getNonMemInfo(String nonmem) {
		MemberDTO memberDTO = new MemberDTO();
		String sql = "select * from pcmanager where id=?";
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nonmem);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memberDTO.setId(rs.getString("id"));
				memberDTO.setUserName(rs.getString("name"));
				memberDTO.setTime(rs.getInt("time"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}

	public void setTime(int time, String id) {
		String sql = "update pcmanager set time=? where id=?";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, time);
			pstmt.setString(2, id);	
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<MemberDTO> getUserList() {//*******************************
		ArrayList<MemberDTO> userList = new ArrayList<MemberDTO>();
		
		String sql = "select id,name,tel1,tel2,tel3,email1,email2 from pcmanager where name!=? or name=?";
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "비회원");
			pstmt.setString(2, "null");
			rs = pstmt.executeQuery();
			

			while(rs.next()) {
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setId(rs.getString("id"));
				memberDTO.setUserName(rs.getString("name"));
				memberDTO.setTel1(rs.getString("tel1"));
				memberDTO.setTel2(rs.getString("tel2"));
				memberDTO.setTel3(rs.getString("tel3"));
				memberDTO.setEmaiL1(rs.getString("email1"));
				memberDTO.setEmail2(rs.getString("email2"));
				
				userList.add(memberDTO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userList;
		
	}

	public void infoModify(MemberDTO memberDTO) {//****************************************
		String sql = "update pcmanager set name=?, tel1=?, tel2=?, tel3=?, email1=?, email2=? where id=?";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getUserName());
			pstmt.setString(2, memberDTO.getTel1());
			pstmt.setString(3, memberDTO.getTel2());
			pstmt.setString(4, memberDTO.getTel3());
			pstmt.setString(5, memberDTO.getEmaiL1());
			pstmt.setString(6, memberDTO.getEmail2());
			pstmt.setString(7, memberDTO.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void deleteInfo(String id) {
		String sql = "delete pcmanager where id=?";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setOn(String id) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update pcmanager set on_off = 1 where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void setOff(String id) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update pcmanager set on_off = 0 where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public int getOnOff(int seatNum) {
		this.seatNum = seatNum;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int on = 0;
		String sql = "select * from pcmanager where seat = ? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(seatNum == rs.getInt("seat")) {
					on = 1;
					break;
				}
				else {
					on = 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return on;
	}
	
	public String getName(int seatNum) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String name = null;
		String sql = "select * from pcmanager where seat = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatNum);
			rs = pstmt.executeQuery();
			rs.next();
			name=rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}			
		return name;
	}	
	
	public String getUserID(int seatNum) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String userID = null;
		String sql = "select * from pcmanager where seat = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatNum);
			rs = pstmt.executeQuery();
			rs.next();
			 userID=rs.getString("ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}			
		return userID;
	}	
	
	public int getTime(int seatNum) {
		int time = 0;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from pcmanager where seat = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatNum);
			rs = pstmt.executeQuery();
			rs.next();
			time=rs.getInt("time");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return time;
	}
	
	public void setSeatOn(String seat,String id) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int seatNum = Integer.parseInt(seat);
		String sql = "update pcmanager set seat = ? where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatNum);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setSeatOut(String userID) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update pcmanager set seat = 0 where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public ArrayList<MemberDTO> getMemberList() {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from pcmanager where on_off = 1";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setSeat(rs.getInt("seat"));
				dto.setUserName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				list.add(dto);
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	//=================좌석 겟터
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;}

	public int getUserTime(String id) {
		int dbTime=0;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select time from pcmanager where id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dbTime = rs.getInt("time");
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return dbTime;
	}

	public void setNonMem(String nonMemNum) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		String sql = "insert into pcmanager (id, name) values (? ,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nonMemNum);
			pstmt.setString(2, "비회원");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public ArrayList<String> getNonMemNumList() {
		ArrayList<String> numList = new ArrayList<String>();
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id from pcmanager where name=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "비회원");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				numList.add(rs.getString("id"));
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return numList;
	}
	
}
