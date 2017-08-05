package ebuy.alipay;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 支付核心工具类
 * 
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-5-03 11:24:11
 * @version 1.0
 */
public class AlipayCore {
	
	/** 合作身份者ID,以2088开头由16位纯数字组成的字符串 */
	public static String partner = "2088021119469553";
	/** 商户的私钥key */
	public static String key = "6x7f5gin1grq6wvpfwji07m7rtqtx6ey";
	/** 收款支付宝账号 */
	public static String seller_email = "2355908671@qq.com";
	
	/** 字符编码格式 目前支持 gbk 或 utf-8 */
	public static String input_charset = "utf-8";
	/** 签名方式 不需修改 */
	public static String sign_type = "MD5";
	
	/**
     * 签名字符串
     * @param para 需要签名的参数
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(Map<String, String> para) {
    	try{
    		/** 除去集合中的空值和签名参数；返回一个新的Map集合 */
    		Map<String, String> maps = paraFilter(para);
    		/** 把集合中所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串 */
        	String prestr = createLinkString(maps);
        	/** 生成签名 */
        	String mysign = "";
        	prestr = prestr + key;
 	    	if (input_charset == null || "".equals(input_charset)) {
 	    		mysign = getMD5(prestr.getBytes());
 	        }else{
 	        	mysign = getMD5(prestr.getBytes(input_charset));
 	        }
            return mysign;
    	}catch(Exception ex){
    		throw new RuntimeException("签名时出现异常！");
    	}
    }
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) { //拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
	/**
	 * 获取MD5加密过后的密码
	 * @param bytes 字节数组
	 * @return 加密后的内容
	 */
	private static String getMD5(byte[] bytes) {
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			/** 调用MessageDigest的update()进行加密 */
			md.update(bytes);
			/** 获取加密后的字节数组 */
			byte[] md5Bytes = md.digest();
			/** 拼接加密后的内容 */
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < md5Bytes.length; i++){
				int temp = md5Bytes[i] & 0xff;
				if (temp <= 0xf){
					res.append("0");
				}
				res.append(Integer.toHexString(temp));
			}
			return res.toString();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}