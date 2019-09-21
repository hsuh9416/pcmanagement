package mgr.inventory;

import java.io.*;

@SuppressWarnings("serial")
public class InventoryDTO implements Serializable {
	private int itemNum;//물품명-key(seq로 설정시 변경해야 함)
	private String item;//상품명
	private int price;//개당 가격
	private int stock;//재고
	
	public InventoryDTO() {}

	public InventoryDTO(int itemNum, String item, int price, int stock) {
		this.itemNum = itemNum;
		this.item = item;
		this.price = price;
		this.stock = stock;}
	
	public int getItemNum() {return itemNum;}
	public void setItemNum(int itemNum) {this.itemNum = itemNum;}
	
	public String getItem() {return item;}
	public void setItem(String item) {this.item = item;}
	
	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}
	
	public int getStock() {return stock;}
	public void setStock(int stock) {this.stock = stock;}
	
	@Override
	public String toString() {//고객이 보는 목록에서 보여지는 방법
		String itemTitle; 
		if (stock<=0) itemTitle ="["+item+"]  "+price+"(원/개) [품절]";//품절시에는 품절이라는 문구 추가
		else itemTitle ="["+item+"]  "+price+"(원/개)";
		return itemTitle;}

}//InventortyDTO Class
