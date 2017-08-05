package ebuy.dto;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Timlimited 数据传输类
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-4-28 15:54:49
 * @version 1.0
 */

/**
 * @author Administrator
 *
 */
public class TimeLimited implements Serializable{

    private static final long serialVersionUID = 1L;
    private int id;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date limitDate;
	private Goods goods;
	//private int goodsId;
	
    private int isEnd;

	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setLimitDate(java.util.Date limitDate){
		this.limitDate = limitDate;
	}
	public java.util.Date getLimitDate(){
		return this.limitDate;
	}
	
    public Goods getGoods() {
        return goods;
    }
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public void setIsEnd(int isEnd){
		this.isEnd = isEnd;
	}
    public int getIsEnd(){
		return this.isEnd;
	}

}