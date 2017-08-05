package ebuy.alipay;

import java.util.HashMap;
import java.util.Map;


/**
 * 支付宝 【即时到账交易】处理类
 * 
 * @author hanboliang
 * @email 940163229@qq.com
 * @date 2017-5-03 11:24:11
 * @version 1.0
 */
public class DirectPayService{
	
	/**
	 * 页面跳转同步通知页面路径 
	 * 需http://格式的完整路径，不能加?id=123这类自定义参数
	 * 不能写成http://localhost/ 
	 */
	public static String returnUrl = "http://127.0.0.1:8080/e_buysmvcf/customer/directPayResult";
	/** 支付宝提供给商户的服务接入网关URL(新) */ 
    public static String direct_alipay_gateway = "https://mapi.alipay.com/gateway.do?_input_charset=" + AlipayCore.input_charset;
	
    /**
	 * 【即时到账交易-请求数据获取】
	 * @param outTradeNo 订单号码
	 * @param subject 商品名称
	 * @param totalFee 付款金额
	 * @param body 订单描述
	 * @return 请求提交参数集合
	 */
	public static Map<String, String> getRequestDataByDirectPay(String outTradeNo, 
							String subject,String totalFee, 
							String body){
		/** 封装请求参数成Map集合 */
		Map<String, String> para = new HashMap<>();
		/** 指定支付方式(直接付款) 接口名称(必填) */
		para.put("service", "create_direct_pay_by_user");
		/** 合作身份者ID (必填)*/
		para.put("partner", AlipayCore.partner);
		/** 参数编码 (必填) */
		para.put("_input_charset", AlipayCore.input_charset);
		/** 卖家支付宝帐户  (可空)*/
		para.put("seller_email", AlipayCore.seller_email);
		
		/** 页面跳转同步通知页面路径 (可空)  */
		para.put("return_url", returnUrl);
		
		/** 商户网站唯一订单号(必填) */
		para.put("out_trade_no", outTradeNo);
		/** 商品名称(必填) */
		para.put("subject", subject);
		/** 支付类型  (必填) 1:商品购买*/
		para.put("payment_type", "1");
		/** 交易金额(必填) */
		para.put("total_fee", totalFee);
		/** 卖家支付宝用户号(必填) */
		para.put("seller_id", AlipayCore.partner);
		/** 订单描述 (可空)*/
		para.put("body", body);
		
		
		/** 生成签名结果 (必填) 对上面所有参数进行签名 */
		String mysign = AlipayCore.sign(para);
		/** 签名结果与签名方式加入请求提交参数组中 */
		para.put("sign", mysign);
		/** 签名类型  (必填)*/
		para.put("sign_type", AlipayCore.sign_type);   
		return para;
	}
}