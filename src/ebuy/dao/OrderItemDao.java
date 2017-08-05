package ebuy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.Order;
import ebuy.dto.OrderItem;
import ebuy.dto.User;

/**
 * 专门针对订单Order对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class OrderItemDao extends BaseDao {
    
    /**
     * 根据商品Id获取订单
     * @param id
     * @return
     */
    public List<OrderItem> getOrderById(int id){
        SqlSession session = getSqlSession();
        List<OrderItem> orderItemList = session.selectList("ebuy.mapper.OrderItemMapper.getOrderItemByOrderId",id);
        closeSqlSession();
        return orderItemList;
    }
    
    
    public int saveOrderItem(OrderItem orderItem){
        SqlSession session = getSqlSession();
        int count = session.insert("ebuy.mapper.OrderItemMapper.saveOrderItem",orderItem);
        session.commit();
        closeSqlSession();
        return count;
    }
}
