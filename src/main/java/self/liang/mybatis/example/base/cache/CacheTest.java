package self.liang.mybatis.example.base.cache;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.Employee;
import self.liang.mybatis.example.base.dao.EmployeeMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 两级缓存
 *
 * 一级缓存：本地缓存 sqlSession级别的缓存，默认开启，
 *      与数据库同一次会话查询到的数据会放到本地缓存中
 *      以后如果需要获取相同的数据，直接他从缓存中拿，没必要再去查数据库
 *      一级缓存失效
 *          1.sqlSession不用
 *          2.sqlSession相同，查询条件不用
 *          3.sqlSession相同，两次查询执行了增删改操作
 *          4.sqlSession相同，手动清除一级缓存
 * 二级缓存：全局缓存:基于nameSpace级别的缓存，一个nameSpace对应一个二级缓存
 *      工作机制：
 *          1.一个sqlSession查询一条数据，这个数据就会被放在当前会话的以及缓存中。
 *          2.如果会话关闭，一级缓存的数据会被保存到二级缓存中。
 *          3.sqlSession===EmployeeMapper===>Employee
 *                          DepartmentMapper===>Department
 *                         不同nameSpace查出的数据会放在自己的缓存中
 *      使用步骤：
 *          1.开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
 *          2.去mapper.xml中配置
 *              <mapper namespace="self.liang.mybatis.example.base.dao.EmployeeMapper">
 *              <cache eviction="" flushInterval="" readOnly="" size="" type=""></cache>
 *              eviction:回收策略：LRU（默认），FIFO，SOFT（软引用，移除基于垃圾回收器状态的和软引用规则的对象）
 *                       WEAK （弱引用，更积极的移除基于垃圾回收器状态的和软引用规则的对象）
 *              flushInterval:缓存刷新间隔：多长事件清空一次：默认不清空，可以设置毫秒值
 *              readOnly：缓存是否只读：
 *                  true：mybatis认为所有获取数据的操作都是只读，不会修改数据，会将缓存的引用传递给用户（不安全，速度快）
 *                  false（默认） ：非只读：mybatis觉得获取的数据可能会被修改。mybatis会用序列化&反序列化克隆一份新的数据（安全，慢）
 *              size：保存多少个元素；
 *              type：指定自定义缓存的全类名
 *                      实现mybatis的Cache接口，然后传入全类名
 *           3.我们的pojo需要实现序列化接口
 *               class Employee implements Serializable
 *  和缓存有关的设置：
 *          1。<setting name="cacheEnabled" value="true"/> 开启关闭二级缓存
 *          2.每个select都有useCache属性 默认true
 *                  false：关闭二级缓存
 *          3.每个insert update delete标签flushCache默认为true。select默认时false
 *                  每次执行会清除缓存。；一级和二级都清除
 *          4.sqlSession的clearCache（）；只是清除当前session的一级缓存
 *          5.setting的localCacheScope：本地缓存作用域：Session，当前会话的保存
 *                                                    STATEMENT：不保存
 *
 * 第三方缓存：需要扩展的时候看看吧
 */
public class CacheTest {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory = getFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession();


//        testFirstLevelCache(sqlSession);
        testSecondLevelCache(sqlSessionFactory);
        sqlSession.commit();
        sqlSession.close();

    }


    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }


    private static void testSecondLevelCache(SqlSessionFactory factory) {

        SqlSession sqlSession = factory.openSession(true);
        SqlSession sqlSession2 = factory.openSession(true);

        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        EmployeeMapper employeeMapper2 = sqlSession2.getMapper(EmployeeMapper.class);

        Employee e1 = employeeMapper.getEmpById(1);
        sqlSession.close();//不关闭。。不刷新到二级缓存
//        sqlSession.commit();//提交也会刷新到二级缓存
        System.out.println(e1);

        Employee e2 = employeeMapper2.getEmpById(1);
        System.out.println(e2);
        sqlSession2.close();
    }

    private static void testFirstLevelCache(SqlSession sqlSession) {

        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee);

//        employeeMapper.addEmp(new Employee(null,"testCache","123@123.com","2"));
        sqlSession.clearCache();
        Employee employee2 = employeeMapper.getEmpById(1);
        System.out.println(employee2);
        System.out.println(employee == employee2);
    }

}

