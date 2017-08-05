package ebuy.dto;

/**
 * OrderItem 数据传输类
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-4-28 15:54:49
 * @version 1.0
 */
public class OrderItem implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Order order;
	private Goods goods;
	private int orderNum;

	/** setter and getter method */
	
	public void setOrderNum(int orderNum){
		this.orderNum = orderNum;
	}
	public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Goods getGoods() {
        return goods;
    }
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public int getOrderNum(){
		return this.orderNum;
	}

}