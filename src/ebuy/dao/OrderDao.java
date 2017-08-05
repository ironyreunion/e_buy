package ebuy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.Order;
import ebuy.dto.User;

/**
 * 专门针对订单Order对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class OrderDao extends BaseDao {
    /**
     * 获取所有订单记录
     * @return
     */
    public List<Order> getOrderList(){
        SqlSession session = getSqlSession();
        List<Order> orderList = session.selectList("ebuy.mapper.OrderMapper.getOrderList");
        closeSqlSession();
        return orderList;
    }
    
    /**
     * 保存订单到数据库
     * @param order
     * @return
     */
    public String saveOrder(Order order){
        SqlSession session = getSqlSession();
        int count = session.insert("ebuy.mapper.OrderMapper.saveOrder",order);
        session.commit();
        closeSqlSession();
        return count>1?"保存成功":"保存失败";
    }
    
    /**
     * 根据用户Id查询用户的订单
     * @param userId
     * @return
     */
    public List<Order> getOrderByUserId(int userId){
        SqlSession session = getSqlSession();
        List<Order> orderList = session.selectList("ebuy.mapper.OrderMapper.getOrderByUserId",userId);
        closeSqlSession();
        return orderList;
    }
    
    /**
     * 更新订单物流状态和支付状态
     * @param orderCode
     * @return
     */
    public boolean updateOrderAliPayStatus(String orderCode){
        SqlSession session = getSqlSession();
        int count = session.update("ebuy.mapper.OrderMapper.updateOrderAliPayStatus",orderCode);
        session.commit();
        closeSqlSession();
        return count>1?true:false;
    }
    
    /**
     * 根据订单号查找订单
     * @param orderCode
     * @return
     */
    public Order getOrderByOrderCode(String orderCode){
        SqlSession session = getSqlSession();
        Order order = session.selectOne("ebuy.mapper.OrderMapper.getOrderByOrderCode",orderCode);
        closeSqlSession();
        return order;
    }
}
