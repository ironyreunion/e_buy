package ebuy.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 获取SqlSession和关闭SqlSession的基础类
 * @author Administrator
 *
 */
public class BaseDao {

	private static SqlSessionFactory factory;
	//解决线程安全
	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();
	//通过静态块初始化SqlSessionFactory，因为静态块只会在类第一次加载的时候执行
	static {
		try {
			InputStream in = Resources.getResourceAsStream("Mybatis_config.xml");
			factory  = new SqlSessionFactoryBuilder().build(in);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("数据库连接工厂获取失败！");
		}
	}
	/**
	 * 获取SqlSession对象
	 * @return
	 */
	public static SqlSession getSqlSession(){
		//通过ThreadLocal获取sqlSession对象
		SqlSession sqlSession = threadLocal.get();
		if(sqlSession == null){
			//获取一个SqlSession对象
			sqlSession = factory.openSession();
			//并存储到ThreadLocal对象中，通过ThreadLocal对象可以方便进行线程同步管理
			threadLocal.set(sqlSession);
		}
		return sqlSession;
	}
	/**
	 * 关闭SqlSession对象
	 */
	public static void closeSqlSession(){
		SqlSession sqlSession = threadLocal.get();
		if(sqlSession != null){
			sqlSession.close();
		}
		threadLocal.remove();
	}
	
}
