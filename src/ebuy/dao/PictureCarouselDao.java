package ebuy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.PictureCarousel;
import ebuy.dto.User;

/**
 * 专门针对Picturecarousel对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class PictureCarouselDao extends BaseDao {
    
    public List<PictureCarousel> getPictureCarousel(){
        SqlSession session = getSqlSession();
        List<PictureCarousel> pictureCarouselList = session.selectList("ebuy.mapper.PictureCarouselMapper.getALLPictureCarouselList");
        closeSqlSession();
        return pictureCarouselList;
    }
    
    //新增产品
    public boolean savePictureCarousel(PictureCarousel pictureCarousel){
        SqlSession session = getSqlSession();
        int flag = session.insert("ebuy.mapper.PictureCarouselMapper.savepictureCarousel",pictureCarousel);
        session.commit();
        closeSqlSession();
        return flag>0?true:false;
    }
}
