package ebuy.dto;

import java.text.DecimalFormat;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Goods 数据传输类
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-4-28 15:54:49
 * @version 1.0
 */
public class Goods implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String brandName;
	private double price;
	private double favorablePrice;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date groundingDate;
	private int storage;
	private String image;
	private String description;
	private GoodsType goodsType;
	private java.util.Date createDate;

	//购物车用到的商品购买数量
	private int buyNum;
	
	public double getBuyMoney(){
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(this.favorablePrice*this.buyNum));
    }
	
	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setBrandName(String brandName){
		this.brandName = brandName;
	}
	public String getBrandName(){
		return this.brandName;
	}
	public void setPrice(double price){
		this.price = price;
	}
	public double getPrice(){
		return this.price;
	}
	public void setFavorablePrice(double favorablePrice){
		this.favorablePrice = favorablePrice;
	}
	public double getFavorablePrice(){
		return this.favorablePrice;
	}
	public void setGroundingDate(java.util.Date groundingDate){
		this.groundingDate = groundingDate;
	}
	public java.util.Date getGroundingDate(){
		return this.groundingDate;
	}
	public void setStorage(int storage){
		this.storage = storage;
	}
	public int getStorage(){
		return this.storage;
	}
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}
	
	public GoodsType getGoodsType() {
        return goodsType;
    }
    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }
    
    public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	public java.util.Date getCreateDate(){
		return this.createDate;
	}
    public int getBuyNum() {
        return buyNum;
    }
    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

}