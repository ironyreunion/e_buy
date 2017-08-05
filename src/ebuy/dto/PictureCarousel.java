package ebuy.dto;

/**
 * Picturcarousel 数据传输类
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-4-28 15:54:49
 * @version 1.0
 */
public class PictureCarousel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String imageUrl;
	private String requestUrl;

	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}
	public String getImageUrl(){
		return this.imageUrl;
	}
	public void setRequestUrl(String requestUrl){
		this.requestUrl = requestUrl;
	}
	public String getRequestUrl(){
		return this.requestUrl;
	}

}