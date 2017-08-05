package ebuy.action.customer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ebuy.common.AuthCode;
import ebuy.common.WebConstant;
import ebuy.dao.GoodsDao;
import ebuy.dto.Goods;


@Controller
@RequestMapping("/customer")
public class BuyCarAction{
    /**
     * 在详情页添加商品
     * Map实际上就是JSON对象
     * @param goodsId
     * @param buyNum
     * @param session
     * @return
     */
    @RequestMapping("/addBuyCar")
    @ResponseBody
    public Map<String,String> addBuyCar(int goodsId,int buyNum,HttpSession session) {
        /*
         * 购物车存在于session
         * Map<int goodsId,int buyNum>
         */
		Map<Integer,Integer> shopCar = (Map<Integer, Integer>) session.getAttribute(WebConstant.SHOP_CAR);
		//如果是第一次，就创建购物车
		if(shopCar == null){
		    shopCar = new LinkedHashMap<>();
		}
		//如果购物车里已经有将要添加的商品
		if(shopCar.containsKey(goodsId)){
		    //获取已经存在商品的数量
		    int oldNum = shopCar.get(goodsId);
		    //改变（增加）该商品数量
		    shopCar.put(goodsId, oldNum+buyNum);
		}else{
		    shopCar.put(goodsId, buyNum);
		}
		//覆盖session中购物车的状态
		session.setAttribute(WebConstant.SHOP_CAR,shopCar);
		//将JSON数据返回到页面
		Map<String,String> data = new HashMap<>();
		data.put("status", "0");
		return data;
	}

    /**
     * 进入购物车页面
     * @param session
     * @return
     */
    @RequestMapping(value="/showBuyCar")
    public String showBuyCar(HttpSession session,Model data){
        //获取购物车对象
        Map<Integer,Integer> shopCar = (Map<Integer, Integer>) session.getAttribute(WebConstant.SHOP_CAR);
        List<Goods> goodsList = new ArrayList<>();
        double totalMoney = 0.0;
        //判断购物车是否为空
        if(shopCar != null){
            /*
             * 若不为空，则取出购物车里的所有商品
             * Map.Entry<K, V>接口，作用是封装Map集合中的一组“键值对 对象”
             * entrySet() 是用来遍历map，取出所有的值和键
             */
            for(Map.Entry<Integer, Integer> map:shopCar.entrySet()){
                //map的键是商品Id
                int goodsId = map.getKey();
                //map的值是购物车里商品的数量
                int buyNum = map.getValue();
                
                GoodsDao goodsDao = new GoodsDao();
                //通过商品Id查商品
                Goods goods = goodsDao.getGoodsById(goodsId);
                //设置商品的购买数量，并设置给商品对象
                goods.setBuyNum(buyNum);
                //查得若干件商品的总价，并赋予totalMoney（购物车总价）
                totalMoney += goods.getBuyMoney();
                //把商品对象放在列表对象中
                goodsList.add(goods);
            }
        }
        //循环结束后，对总价做一个格式化处理
        DecimalFormat df = new DecimalFormat("0.00");
        totalMoney = Double.valueOf(df.format(totalMoney));
        //返回相应对象，方便页面获取
        data.addAttribute("totalMoney", totalMoney);
        data.addAttribute("goodsList", goodsList);
        //跳转到页面
        return "buyCar";
    }
    
    /**
     * 删除购物车中的商品
     * @param goodsId
     * @param session
     * @param data
     * @return
     */
    @RequestMapping(value="/deleteCar")
    @ResponseBody
    public Map<String,String> deleteCar(int goodsId,HttpSession session,Model data){
        //获取购物车对象
        Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
        
        //如果购物车不为空，则移除相对应的商品
        if(shopCar != null){
            shopCar.remove(goodsId);
        }
        //重新覆盖session里的购物车数据
        session.setAttribute(WebConstant.SHOP_CAR, shopCar);
        Map<String,String> result = new HashMap<>();
        //返回给Ajax的if(data.status == 0)使用
        result.put("status","0");
        return result;
    }
    
    /**
     * 更新购物车中的商品
     * @param goodsId 商品标识Id
     * @param buyNum 商品购买数量
     * @param session
     * @param data
     * @return
     */
    @RequestMapping(value="/updateCar")
    @ResponseBody
    public Map<String,String> updateCar(int goodsId,int buyNum,HttpSession session,Model data){
        //获取购物车对象
        Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
        
        //如果购物车不为空，则添加相对应的商品
        if(shopCar != null){
            shopCar.put(goodsId, buyNum);
        }
        //重新覆盖session里的购物车数据
        session.setAttribute(WebConstant.SHOP_CAR, shopCar);
        Map<String,String> result = new HashMap<>();
        //返回给Ajax的if(data.status == 0)使用
        result.put("status","0");
        return result;
    }
    
    /**
     * 获取购物车中的商品数量
     * @param session
     * @return
     */
    @RequestMapping(value="/loadAjaxShopCarNum")
    @ResponseBody
    public Map<String,Object> loadAjaxShopCarNum(HttpSession session){
        /*
         * 获取购物车对象
         * Map<K,V>,K为商品Id，V为商品数量
         */
        Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
        //totalNum 商品数量
        int totalNum = 0;
        //如果购物车不为空，则循环所有商品，并获得商品总数
        if(shopCar != null){
            //用map来接收shopCar的所有键值
            for(Map.Entry<Integer, Integer> map : shopCar.entrySet()){
                totalNum += map.getValue();
            }
        }
        //封装返回类型对象（Json）
        Map<String,Object> result = new HashMap<>();
        //返回给Ajax的if(data.status == 0)使用
        result.put("status","0");
        //返回商品总数量给页面使用
        result.put("totalNum",totalNum);
        return result;
    }
}
