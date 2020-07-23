package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

enum Option{
	EntireSearch,
	QuerySearch
}

public class BbsDAO {
	
	private Connection conn;
	private ResultSet rs;
	public BbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?serverTimezone=Asia/Seoul&useSSL=false";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		String SQL = "select now()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; 	//데이터베이스 오류
	}
	
	public int getNext() {
		String SQL = "select bbsID from bbs order by bbsID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 	//데이터베이스 오류
	}
	
	public int getTotalNum() {
		String SQL = "select count(*) from bbs where bbsAvailable != 0";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 	//데이터베이스 오류
	}
	
	public int getTotalNum(String query) {
		String SQL = "select count(*) from bbs where bbsAvailable != 0 and bbsContent like ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(0, query);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 	//데이터베이스 오류
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		
		String SQL = "insert into bbs values (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 	//데이터베이스 오류
	}
	
	public ArrayList<Bbs> getList(int pageNumber){
		
		String SQL = "select b.* from (SELECT * FROM bbs WHERE bbsAvailable = 1 ORDER BY bbsID DESC) as b LIMIT ?, 10;";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1 , (pageNumber-1)*10);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; 	
	}
	
public ArrayList<Bbs> getList(int pageNumber, String query){
		
	String SQL = "select * from (select @rownum:=@rownum+1 as rownum, b.* from (SELECT * FROM bbs WHERE bbsAvailable = 1 AND (bbsContent like \"?\" OR bbsTitle like \"?\") ORDER BY bbsID DESC) b where (@rownum:=0)=0) c where c.rownum>=? and c.rownum <= ?;";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + query + "%");
			pstmt.setString(2, "%" + query + "%");
			pstmt.setInt(3, 1);
			pstmt.setInt(4, 10);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list; 	
	}
	
	
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM bbs WHERE bbsID < ? AND bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1) * 10);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM bbs WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE bbs SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 	//데이터베이스 오류
	}
	
	public int delete(int bbsID) {
		String SQL = "UPDATE bbs SET bbsAvailable = 0 where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 	//데이터베이스 오류
	}
}
