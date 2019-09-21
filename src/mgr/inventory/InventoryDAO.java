package mgr.inventory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class InventoryDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "java";
	private String password = "itbank";
			
			private static InventoryDAO instance;
			
			public static InventoryDAO getInstance() {
				if(instance==null) {
					synchronized (InventoryDAO.class) {instance = new InventoryDAO();}}
				return instance;}
			
			public InventoryDAO() {
				try {Class.forName(driver);} 
				catch (ClassNotFoundException e) {e.printStackTrace();}}//생성자
			
			public Connection getConnection() {
				Connection conn = null;
				try {conn = DriverManager.getConnection(url, user, password);}
				catch (SQLException e) {e.printStackTrace();}
				return conn;}
			
			public void takeInventoryOrder(int itemNum, int itemMnt) {
				int number = itemNum;
				int mount  = itemMnt;
				
				Connection conn = this.getConnection();
				String sql = "update 물품 set 재고=재고-"+mount+" where 상품번호 ="+number;	
				PreparedStatement pstmt = null;

				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.executeUpdate();}
				catch (SQLException e) {e.printStackTrace();}
				finally {
					try {
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();} 
					catch (SQLException e) {e.printStackTrace();}}
			}//DB에 주문 반영
			
			public ArrayList<InventoryDTO> getInventoryList(){//화면에 목록을 뿌리는 용도
				ArrayList<InventoryDTO> list = new ArrayList<InventoryDTO>();
				String sql = "select 상품번호,상품명,가격,재고 from 물품";
				Connection conn = getConnection();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						InventoryDTO dto = new InventoryDTO();
						dto.setItemNum(rs.getInt("상품번호"));
						dto.setItem(rs.getString("상품명"));
						dto.setPrice(rs.getInt("가격"));
						dto.setStock(rs.getInt("재고"));
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
			}

			//DB에 물품추가 시키는 메소드
			public void addInventory(InventoryDTO inventoryDTO) {
				String sql = "insert into 물품 values(?,?,?,?)";

				Connection conn = this.getConnection();
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, inventoryDTO.getItemNum());
					pstmt.setString(2, inventoryDTO.getItem());
					pstmt.setInt(3, inventoryDTO.getPrice());
					pstmt.setInt(4, inventoryDTO.getStock());

					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}// addInventory
			
			//DB 물품삭제 메소드
			public void deleteInventory(String itemName) {

				String sql = "delete from 물품 where 상품명 = ?";

				Connection conn = this.getConnection();
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, itemName);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}//deleteInventory
			
			//DB에 물품수정 메소드
			public void modifyInventory(InventoryDTO inventoryDTO) {
				String sql = "update 물품 set 상품명=?, 가격=?, 재고=? where 상품번호=?";

				Connection conn = this.getConnection();
				PreparedStatement pstmt = null;
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, inventoryDTO.getItem());
					pstmt.setInt(2, inventoryDTO.getPrice());
					pstmt.setInt(3, inventoryDTO.getStock());
					pstmt.setInt(4, inventoryDTO.getItemNum());

					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}//modifyInventory
			
}//InventoryDAO class
