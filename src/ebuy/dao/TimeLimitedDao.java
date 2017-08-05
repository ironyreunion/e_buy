package ebuy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.PictureCarousel;
import ebuy.dto.TimeLimited;
import ebuy.dto.User;

/**
 * 专门针对限时抢购TimeLimited对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class TimeLimitedDao extends BaseDao {
    /**
     * 返回所有限时抢购商品
     * @return
     */
    public List<TimeLimited> getTimeLimitedList(){
        SqlSession session = getSqlSession();
        List<TimeLimited> timeLimitedList = session.selectList("ebuy.mapper.TimeLimitedMapper.getALLTimeLimited");
        closeSqlSession();
        return timeLimitedList;
    }
    
    /**
     * 新增限时抢购
     * @param timeLimited
     * @return
     */
    public boolean save(TimeLimited timeLimited){
        SqlSession session = getSqlSession();
        int count = session.insert("ebuy.mapper.TimeLimitedMapper.save",timeLimited);
        session.commit();
        closeSqlSession();
        return count>0?true:false;
    }
    
}
