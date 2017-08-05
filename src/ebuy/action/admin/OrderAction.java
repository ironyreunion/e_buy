package ebuy.action.admin;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ebuy.dao.OrderDao;
import ebuy.dao.OrderItemDao;
import ebuy.dto.Order;
import ebuy.dto.OrderItem;


/**
 * 订单 相关操作 控制器
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/admin/order")
public class OrderAction {

    /**
     * 显示订单明细
     * @param id
     * @param data
     * @return
     */
    @RequestMapping("/showDetail")
    public String showDetail(int id,Model data){
        OrderItemDao orderItemDao = new OrderItemDao();
        List<OrderItem> orderItemList = orderItemDao.getOrderById(id);
        data.addAttribute("orderItemList", orderItemList);
        return "order/orderItem";
    }
    
    /**
     * 显示所有订单
     * @param data
     * @return
     */
    @RequestMapping("/orderList")
    public String showOrderList(Model data){
        OrderDao orderDao = new OrderDao();
        List<Order> orderList = orderDao.getOrderList();
        
        data.addAttribute("orderList", orderList);
        return "order/orderList";
    }
}
