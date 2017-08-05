package ebuy.dto;

import java.util.List;
import java.util.Map;

/**
 * GoodsType 数据传输类
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-4-28 15:54:49
 * @version 1.0
 */
public class GoodsType implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String remark;

	/**
	 * 返回一个大类型和小类型组合好的JSON数据
	 * 内部类
	 */
	public static class GoodsTypes{
	    /**
	     * 因为格式是
	     * [{  maxType:{} , minType:[{},{}...]  }]
	     */
	    private Map<String,String> maxType;
	    private List<Map<String,String>> minType;
        
	    /** setter and getter method */
	    public Map<String, String> getMaxType() {
            return maxType;
        }
        public void setMaxType(Map<String, String> maxType) {
            this.maxType = maxType;
        }
        public List<Map<String, String>> getMinType() {
            return minType;
        }
        public void setMinType(List<Map<String, String>> minType) {
            this.minType = minType;
        }
        
        /** toString method */
        @Override
        public String toString() {
            return "GoodsTypes [maxType=" + maxType + ", minType=" + minType + "]";
        }
	}
	
	/** setter and getter method */
	public void setCode(String code){
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}

}