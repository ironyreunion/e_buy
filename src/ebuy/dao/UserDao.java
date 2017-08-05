package ebuy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import ebuy.dto.User;

/**
 * 专门针对User对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class UserDao extends BaseDao {

    /**
     * 查询所有用户，用List接收
     * @param u
     * @return
     */
    public List<User> getUserList(User user){
        //连接数据库
        SqlSession session = getSqlSession();
        List<User> userList = session.selectList("ebuy.mapper.UserMapper.getUserList",user);
        closeSqlSession();
        return userList;
    }
    
    /**
     * 根据id删除数据的操作
     * @param u
     * @return
     */
    public int deleteUser(int id){
        
        SqlSession session = getSqlSession();
        int count = session.delete("ebuy.mapper.UserMapper.deleteUser", id);
        session.commit();
        closeSqlSession();
        return count;
    }
    
    
    /**
     * 通过用户编号查询用户信息（更新信息）
     * @param u
     * @return
     */
    public User getUserById(int id){
        SqlSession session = getSqlSession();
        User user = session.selectOne("ebuy.mapper.UserMapper.getUserById", id);
        closeSqlSession();
        return user;
    }
    
    
    /**
     * 管理员登录查询
     * @param u
     * @return
     */
    public User getAdmin(User u){
        
        SqlSession session = getSqlSession();
        User user = session.selectOne("ebuy.mapper.UserMapper.getAdmin", u);
        closeSqlSession();
        return user;
    }
    
	/**
	 * 通过用户名和密码查询用户名（登录）
	 * @param u
	 * @return
	 */
	public User getUserByUserIdAndByPassword(User u){
		
		SqlSession session = getSqlSession();
		User user = session.selectOne("ebuy.mapper.UserMapper.getUserByUserIdAndByPassword", u);
		closeSqlSession();
		return user;
	}
	
	/**
     * 通过用户名查询账号（注册时查重）
     * @param u
     * @return
     */
    public boolean getUserByUserId(String userId){
        
        SqlSession session = getSqlSession();
        int count = session.selectOne("ebuy.mapper.UserMapper.getUserByUserId", userId);
        closeSqlSession();
        //true表示查到人，false表示查不到人
        return count>0?true:false;
    }
	
	/**
	 * 注册，往数据库中插入新增用户
	 * @param u
	 * @return
	 */
	public int save(User u){
	    SqlSession session = getSqlSession();
	    int count = session.insert("ebuy.mapper.UserMapper.save", u);
	    session.commit();
        closeSqlSession();
	    
	    return count;
	}
	
	/**
	 * 激活用户
	 * @param map
	 * @return
	 */
	public int activeUser(Map<String,String> map){
        
        SqlSession session = getSqlSession();
        int count = session.update("ebuy.mapper.UserMapper.activeUser",map);
        session.commit();
        closeSqlSession();
        return 0;
    }
	
	/**
	 * 更新用户数据
	 * @param user
	 * @return
	 */
	public int updateUser(User user){
        
        SqlSession session = getSqlSession();
        int count = session.update("ebuy.mapper.UserMapper.updateUser",user);
        session.commit();
        closeSqlSession();
        return 0;
    }
}
