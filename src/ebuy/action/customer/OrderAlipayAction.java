package ebuy.action.customer;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ebuy.alipay.DirectPayService;
import ebuy.common.WebConstant;
import ebuy.dao.GoodsDao;
import ebuy.dao.GoodsTypeDao;
import ebuy.dao.OrderDao;
import ebuy.dao.OrderItemDao;
import ebuy.dao.PictureCarouselDao;
import ebuy.dao.TimeLimitedDao;
import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.Order;
import ebuy.dto.OrderItem;
import ebuy.dto.PictureCarousel;
import ebuy.dto.TimeLimited;
import ebuy.dto.User;



@Controller
@RequestMapping("/customer")
public class OrderAlipayAction extends HttpServlet {
    
	/**
	 * 提交订单处理方法
	 * @param goodsIdArrays
	 * @param session
	 * @return
	 */
    @RequestMapping(value="/submitOrder")
	public String submitOrder(String goodsIdArrays,HttpSession session){
        /*
         * 获取购物车对象
         * Map<K,V>,K为商品Id，V为商品数量
         */
        Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
        //创建GoodsDao，方便下面通过goodsId取得goods
        GoodsDao goodsDao = new GoodsDao();
        //创建购物车选中商品的总价格
        double totalMoney = 0.0;
        //创建List对象接收商品对象，方便页面调用
        List<Goods> goodsList = new ArrayList<>();
        
        //如果购物车不为空，则取得所有选中商品
        if(shopCar != null){
            //将从页面取得的产品Id从字符串分割成字符串数组（1,2,3）
            String[] ids = goodsIdArrays.split(",");
            //循环字符串数组ids成单个字符串
            for(String goodsId : ids){
                //Map<K,V>.get(key)
                int buyNum = shopCar.get(Integer.parseInt(goodsId));
                Goods goods = goodsDao.getGoodsById(Integer.parseInt(goodsId));
                goods.setBuyNum(buyNum);
                totalMoney += goods.getBuyMoney();
                goodsList.add(goods);
            }
        }

        //格式化总价
        DecimalFormat df = new DecimalFormat("0.00");
        totalMoney = Double.valueOf(df.format(totalMoney));
        //把数据放在session中
        session.setAttribute("totalMoney", totalMoney);
        session.setAttribute("goodsList", goodsList);
        //跳转到订单确认页面
        return "orderSubmit";
    }
    
    /**
     * 保存订单到数据库
     * @param session
     * @return
     */
    @RequestMapping(value="/saveOrder")
    public String saveOrder(HttpSession session,RedirectAttributes data){
        /*
         * 获取购物车对象
         * Map<K,V>,K为商品Id，V为商品数量
         */
        Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
        
        /*
         * 以下是在数据库中创建（单份）订单记录
         */
        
        //从session中取得订单总金额
        //attribute-totalMoney在submitOrder()方法中已经存入session中了
        double totalMoney = (double) session.getAttribute("totalMoney");
        //从session中取得商品列表
        List<Goods> goodsList = (List<Goods>) session.getAttribute("goodsList");
        
        //创建Order对象用来封装Order数据
        Order order = new Order();
        //getAttribute("session_user")
        User user = (User) session.getAttribute(WebConstant.SESSION_USER);
        /*
         * 开始填充order数据
         */
        //将用户绑定到订单对象
        order.setUser(user);
        //订单创建时间
        order.setCreateDate(new Date());
        //订单的状态（订单状态 0:物流运输中 1:交易成功 -1交易失败）
        order.setTradingStatus(0);
        //订单金额
        order.setTotalAmount(totalMoney);
        //订单编号
        order.setOrderCode(createOrderCode(user.getId()));
        //订单支付状态（是否支付 0:未支付 1:已支付）默认是0，所以其实可以不用写
        //order.setAlipay(0);
        //保存该用户的订单
        OrderDao orderDao = new OrderDao();
        orderDao.saveOrder(order);
        
        
        /*
         * 以下是在数据库中对指定订单 创建订单详情记录
         */
        
        //将goodsList列表中的商品全部循环出来
        OrderItemDao orderItemDao = new OrderItemDao();
        for(Goods goods:goodsList){
            OrderItem orderItem = new OrderItem();
            //订单明细绑定商品
            orderItem.setGoods(goods);
            //订单明细绑定订单对象
            orderItem.setOrder(order);
            //订单明细中的订单商品数量
            orderItem.setOrderNum(goods.getBuyNum());
            //保存订单明细
            orderItemDao.saveOrderItem(orderItem);
            //将订单明细与订单关联（一个订单有多个订单明细）
            order.getOrderItems().add(orderItem);
            //删除购物车中对应的商品
            shopCar.remove(goods.getId());
            
        }
        //存储购物车到session中(重置购物车状态)
        session.setAttribute(WebConstant.SHOP_CAR, shopCar);
        //清空session
        session.removeAttribute("goodsList");
        //相当于order存入session，但会在提取后自动清除
        data.addFlashAttribute("order", order);
        /** 跳转到 支付订单 页面 */ 
        return "redirect:/customer/orderAlipay";
    }
    
    /**
     * 以时间为变量生成订单号
     * @param userId
     * @return
     */
    private String createOrderCode(int userId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        return "LHB_" + userId + "_" + sdf.format(new Date());
    }
    
    /**
     * 支付订单页面
     * @param order 从session中取出“order”并赋予Order order
     * @return
     */
    @RequestMapping(value="/orderAlipay")
    public ModelAndView orderAlipay(@ModelAttribute("order")Order order){
        //拼装支付需要的参数
        Map<String,String> params = DirectPayService.getRequestDataByDirectPay(order.getOrderCode(), 
                order.getOrderItems().get(0).getGoods().getTitle(), 
                String.valueOf(order.getTotalAmount()), 
                order.getOrderItems().get(0).getGoods().getBrandName());
        //指定跳转支付的中转界面
        ModelAndView modelAndView = new ModelAndView("alipay/request_alipay");
        //配置支付的中转界面参数
        modelAndView.addObject("params", params);
        modelAndView.addObject("requestUrl", DirectPayService.direct_alipay_gateway);
        
        return modelAndView;
    }
    
    /**
     * 支付完毕，返回处理
     * @return
     */
    @RequestMapping(value="/directPayResult")
    public String directPayResult(String out_trade_no,String trade_no,String trade_status,String buyer_id,String total_fee){
        System.out.println("订单编号："+out_trade_no);
        System.out.println("支付宝交易流水号："+trade_no);
        System.out.println("交易状态："+trade_status);
        System.out.println("买家支付宝帐号："+buyer_id);
        System.out.println("订单金额："+total_fee);
        if(trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")){
            OrderDao orderDao = new OrderDao();
            orderDao.updateOrderAliPayStatus(out_trade_no);
        }
        //去订单详情界面进行显示
        return "redirect:/customer/orderDetails";
    }
    
    /**
     * 我的订单列表界面
     * @param session
     * @param data
     * @return
     */
    @RequestMapping(value="orderDetails")
    public String orderDetails(HttpSession session,Model data){
        //取得session中的用户
        User user = (User) session.getAttribute(WebConstant.SESSION_USER);
        //根据用户Id查到订单以及订单明细
        OrderDao orderDao = new OrderDao();
        //一个用户可能有多张订单，用List对象接收
        List<Order> orderList = orderDao.getOrderByUserId(user.getId());
        //去到myOrder.html页面取得数据
        data.addAttribute("orderList",orderList);
        return "myOrder";
    }
    
    /**
     * 去支付
     * @param orderCode 订单编号
     * @param data
     * @return
     */
    @RequestMapping(value="/toAlipay")
    public String orderDetails(String orderCode,RedirectAttributes data){
        OrderDao orderDao = new OrderDao();
        Order order = orderDao.getOrderByOrderCode(orderCode);
        //将order传给下一个方法使用，放在session中
        data.addFlashAttribute("order",order);
        return "redirect:/customer/orderAlipay";
    }
}
