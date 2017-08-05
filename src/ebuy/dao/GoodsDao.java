package ebuy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.User;

/**
 * 专门针对Goods对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class GoodsDao extends BaseDao {
    /**
     * 统计商品数量
     * @return
     */
    public int count(Map<String,Object> params){
        SqlSession session = getSqlSession();
        int count = session.selectOne("ebuy.mapper.GoodsMapper.count",params);
        closeSqlSession();
        return count;
    }
    
    /**
     * 查询产品分页数据
     * @param params
     * @return
     */
    public List<Goods> getPageGoods(Map<String,Object>params){
        SqlSession session = getSqlSession();
        List<Goods> goodsList = session.selectList("ebuy.mapper.GoodsMapper.findByPage",params);
        closeSqlSession();
        return goodsList;
    }
    
    /**
     * 新增产品
     * @param goods
     * @return true：新增成功；false：新增失败
     */
    public boolean saveGoods(Goods goods){
        SqlSession session = getSqlSession();
        int flag = session.insert("ebuy.mapper.GoodsMapper.saveGoods",goods);
        session.commit();
        closeSqlSession();
        return flag>0?true:false;
    }
    
    /**
     * 根据商品的类型获取商品
     * @param code
     * @return
     */
    public List<Goods> getGoodsByCode(String code){
        SqlSession session = getSqlSession();
        List<Goods> goodsList = session.selectList("ebuy.mapper.GoodsMapper.getGoodsByCode",code);
        closeSqlSession();
        return goodsList;
    }
    
    /**
     * 根据商品的Id获取商品
     * @param id
     * @return
     */
    public Goods getGoodsById(int id){
        SqlSession session = getSqlSession();
        Goods goods = session.selectOne("ebuy.mapper.GoodsMapper.getGoodsById",id);
        closeSqlSession();
        return goods;
    }
}
