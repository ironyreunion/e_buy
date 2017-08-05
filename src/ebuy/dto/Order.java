package ebuy.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Order 数据传输类
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-4-28 15:54:49
 * @version 1.0
 */
public class Order implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String orderCode;
	private java.util.Date createDate;
	private java.util.Date sendDate;
	private int tradingStatus;
	private double totalAmount;
	private User user;
	private int alipay;

	private List<OrderItem> orderItems = new ArrayList<>();
	
	public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    /** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setOrderCode(String orderCode){
		this.orderCode = orderCode;
	}
	public String getOrderCode(){
		return this.orderCode;
	}
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	public java.util.Date getCreateDate(){
		return this.createDate;
	}
	public void setSendDate(java.util.Date sendDate){
		this.sendDate = sendDate;
	}
	public java.util.Date getSendDate(){
		return this.sendDate;
	}
	public void setTradingStatus(int tradingStatus){
		this.tradingStatus = tradingStatus;
	}
	public int getTradingStatus(){
		return this.tradingStatus;
	}
	public void setTotalAmount(double totalAmount){
		this.totalAmount = totalAmount;
	}
	public double getTotalAmount(){
		return this.totalAmount;
	}
	
	public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setAlipay(int alipay){
		this.alipay = alipay;
	}
	public int getAlipay(){
		return this.alipay;
	}

}