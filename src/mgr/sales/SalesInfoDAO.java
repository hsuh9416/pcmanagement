package mgr.sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import member.bean.MemberDTO;

public class SalesInfoDAO {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.6.27:1521:xe";
	//private String url = "jdbc:oracle:thin:@192.168.6.20:1521:xe";
	private String user = "java";
	private String password = "itbank";
	
	private static SalesInfoDAO instance;
	
	public static SalesInfoDAO getInstance() {
		if(instance==null) {
			synchronized (SalesInfoDAO.class) {instance = new SalesInfoDAO();}}
		return instance;}
	
	public SalesInfoDAO() {
		try {Class.forName(driver);} 
		catch (ClassNotFoundException e) {e.printStackTrace();}}//생성자
	
	public Connection getConnection() {
		Connection conn = null;
		try {conn = DriverManager.getConnection(url, user, password);}
		catch (SQLException e) {e.printStackTrace();}
		return conn;}
	
	public int getSeq(){//거래번호는 seq에서 가져온다.
		int seq=0;
		String sql = "select seq_sales.nextval from dual";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			seq = rs.getInt(1);} 
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}
		return seq;}
	
	public ArrayList<SalesInfoDTO> getSalesInfoList(SalesInfoSel selection){//화면에 목록을 뿌리는 용도
		ArrayList<SalesInfoDTO> list = new ArrayList<SalesInfoDTO>();
		String sql;//날짜함수가 분단위로 보이는 관계로 모든 열을 열거해서 표시함.
		if(selection == SalesInfoSel.SEARCHTODAY) {
			sql = "select 거래번호,상품번호,상품명,매출수량,매출액,to_char(매출일자,'YY/MM/DD') 매출일자, 매입여부, 매입자ID from 매상 where to_date(매출일자,'YY/MM/DD') = to_date(sysdate,'YY/MM/DD')";}
		else if(selection == SalesInfoSel.SEARCHMONTH) {
			sql ="select 거래번호,상품번호,상품명,매출수량,매출액,to_char(매출일자,'YY/MM/DD') 매출일자, 매입여부, 매입자ID from 매상 where trunc(to_date(매출일자,'YY/MM/DD'),'month') = trunc(to_date(sysdate,'YY/MM/DD'),'month')";}
		else if(selection == SalesInfoSel.SEARCHNONPAID) {
			sql="select 거래번호,상품번호,상품명,매출수량,매출액,to_char(매출일자,'YY/MM/DD') 매출일자, 매입여부, 매입자ID from 매상 where 매입여부 = 0";}
		else sql ="select 거래번호,상품번호,상품명,매출수량,매출액,to_char(매출일자,'YY/MM/DD') 매출일자, 매입여부, 매입자ID from 매상";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SalesInfoDTO dto = new SalesInfoDTO();
				dto.setTransactionNum(rs.getInt("거래번호"));
				dto.setProductNum(rs.getInt("상품번호"));//식별코드
				dto.setProductName(rs.getString("상품명"));
				dto.setProductMnt(rs.getInt("매출수량"));
				dto.setRevenue(rs.getInt("매출액"));
				dto.setSaleDate(rs.getString("매출일자"));
				dto.setIsPaid(rs.getBoolean("매입여부"));
				dto.setPerchaseID(rs.getString("매입자ID"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			list=null;
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();		
			}
		}
		return list;
	}//getSalesInfoList() 메소드 : 매상 확인 
	
	public int checkingDeposit() {
		int expDeposit=0;
		Connection conn = this.getConnection();
		String sql = "select sum(매출액) as 시재금  from 매상  where 매입여부=1";			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			expDeposit = rs.getInt("시재금");
			}
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return expDeposit;
		
	}//checkingDeposit() 메소드: 기록상 잔고를 확인하는 메소드
	
	public ArrayList<SalesInfoDTO> getPersonPurchase(MemberDTO memberDTO){//화면에 목록을 뿌리는 용도
		String memberID = memberDTO.getId();
		ArrayList<SalesInfoDTO> list = new ArrayList<SalesInfoDTO>();
		String sql;
		sql="select 상품번호,상품명,매출수량,매출액, 매입여부, 매입자ID from 매상 where 매입여부 = 0 and 매입자ID like ?";

		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SalesInfoDTO dto = new SalesInfoDTO();
				dto.setProductNum(rs.getInt("상품번호"));//식별코드
				dto.setProductName(rs.getString("상품명"));
				dto.setProductMnt(rs.getInt("매출수량"));
				dto.setRevenue(rs.getInt("매출액"));
				dto.setIsPaid(rs.getBoolean("매입여부"));
				dto.setPerchaseID(rs.getString("매입자ID"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			list=null;
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();		
			}
		}
		return list;
	}//getSalesInfoList() 메소드 : 사용자 정산용
	
	
	public void updatingTransaction(SalesInfoDTO salesInfoDTO) {//거래 반영
		SalesInfoDTO InfoDTO = salesInfoDTO;
		String sql="insert into 매상 values(?,?,?,?,?,?,?,?)";
		int isPaid;
		if(InfoDTO.getIsPaid()) isPaid = 1;
		else isPaid = 0; 
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,InfoDTO.getTransactionNum());
			pstmt.setInt(2,InfoDTO.getProductNum());
			pstmt.setString(3, InfoDTO.getProductName());
			pstmt.setInt(4, InfoDTO.getRevenue());
			pstmt.setString(5, InfoDTO.getSaleDate());
			pstmt.setInt(6,isPaid);
			pstmt.setInt(7, InfoDTO.getProductMnt());
			pstmt.setString(8, InfoDTO.getPerchaseID());
			pstmt.executeUpdate();}
		catch (SQLException e) {e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}
	}//매상 추가 메소드

	
	public int getItemSum(MemberDTO memberDTO,boolean totalpay) {//물품 구매액 합계 구하기
		int intemSum=0;
		String userID = memberDTO.getId();
		Connection conn = this.getConnection();
		String sql;
		if(totalpay) sql = "select sum(매출액) as 구입액  from 매상  where (매입자ID like ?)";			
		else sql = "select sum(매출액) as 구입액 from 매상  where (매입여부=0 and 매입자ID like ?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userID);
		rs = pstmt.executeQuery();
		rs.next();
		intemSum = rs.getInt("구입액");
		}
	catch (SQLException e) {e.printStackTrace();}
	finally {
		try {
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();} 
		catch (SQLException e) {e.printStackTrace();}
	}
	return intemSum;
	}

	public void getprePaid(int nowPrePaid) {//선결제하기
		int payment = nowPrePaid;
		int seq =  getSeq();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		String salesdate = sdf.format(today)+"";
		String sql = "insert into 매상 values(?,2002,'PC 선불요금',?,?,1,1,'SYSTEM')";
	
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq);
			pstmt.setInt(2, payment);
			pstmt.setString(3,salesdate);
			pstmt.executeUpdate();}
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}
	}
	
	public void getPaid(MemberDTO memberDTO,SalesInfoSel sel) {//결제하기
		int seq =  getSeq();
		String sql1="update 매상 set 매입여부=1,매입자ID= ? where 매입자ID like ?";
		String sql2="insert into 매상 values(?,2001,'PC 사용요금',?,?,1,1,'SYSTEM')";
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		String salesdate = sdf.format(today)+"";
		int salesprice = memberDTO.getPostPayment();
		System.out.println(salesprice);
		Connection conn = getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			if(sel == SalesInfoSel.TOTALPAY || sel == SalesInfoSel.ITEMPAY) {//sql1문 실행				
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1,"SYSTEM");
				String userId = memberDTO.getId();
				pstmt1.setString(2, userId);
				pstmt1.executeUpdate();;}//거래를 매입처리로 변경하는 실행문
				
			if(sel == SalesInfoSel.TOTALPAY || sel == SalesInfoSel.PCPAY) {//sql2문 실행
				
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1,seq);
				pstmt2.setInt(2,salesprice);
				pstmt2.setString(3,salesdate);
				pstmt2.executeUpdate();}//PC요금을 매입처리하는 실행문
			}
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(pstmt1 != null) pstmt1.close();
				if(pstmt2 != null) pstmt2.close();
				if(conn != null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}	
	}//getPaid 메소드: 지불 관련 메소드
}
