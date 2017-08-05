package ebuy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.GoodsType;
import ebuy.dto.User;

/**
 * 专门针对GoodsType对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class GoodsTypeDao extends BaseDao {

    /**
     * 查询所有商品类型，用List接收
     * @param u
     * @return
     */
    public List<GoodsType> getGoodsTypeList(){
        //连接数据库
        SqlSession session = getSqlSession();
        List<GoodsType> goodsTypeList = session.selectList("ebuy.mapper.GoodsTypeMapper.getGoodsTypeList");
        closeSqlSession();
        return goodsTypeList;
    }
    
    /**
     * 获取商品类型数据，大类型和小类型
     * @return
     */
    public List<GoodsType.GoodsTypes> getGoodsTypes(){
        //用列表来装许多个封装对象
        List<GoodsType.GoodsTypes> gtsList = new ArrayList<>();
        
        //连接数据库
        SqlSession session = getSqlSession();
        //获取大类型,用Map类型包装，并用List接收
        List<Map<String,String>> maxTypes = session.selectList("ebuy.mapper.GoodsTypeMapper.getMaxType");
        //对接收到的List数据用forEach循环
        for(Map<String,String> max : maxTypes){
            //对每个循环出来的大类型，注入小类型（根据大类型的code）
            List<Map<String,String>> minType = session.selectList("ebuy.mapper.GoodsTypeMapper.getMinType",max.get("code"));
            //创建一个封装对象，用来装（大类型+小类型）
            GoodsType.GoodsTypes gts = new GoodsType.GoodsTypes();
            gts.setMaxType(max);
            gts.setMinType(minType);
            gtsList.add(gts);
        }
        closeSqlSession();
        return gtsList;
    }
    
    public GoodsType findGoodsTypeByCode(String code){
      //连接数据库
        SqlSession session = getSqlSession();
        GoodsType goodsType = session.selectOne("ebuy.mapper.GoodsTypeMapper.findGoodsTypeByCode",code);
        closeSqlSession();
        return goodsType;
    }
}
