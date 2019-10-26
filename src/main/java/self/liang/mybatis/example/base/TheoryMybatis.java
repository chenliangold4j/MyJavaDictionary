package self.liang.mybatis.example.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import self.liang.mybatis.example.base.dao.EmployeeMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 一
 * 最上层是接口层：
 * <p>
 * 其次是数据处理层；
 * 参数映射（ParameterHandler）--->sql解析（slqSource）---->sql执行（executor）--->结果处理和映射(resultSetHandler）
 * <p>
 * 框架支撑层：
 * xml，注解，事务管理，连接池管理，缓存机制
 * <p>
 * 引导层：xml配置，java api
 * <p>
 * 执行流程
 * 1.获取sqlSessionFactory对象
 * SqlSessionFactoryBuilder().build(inputStream)
 * build(parser.parse());
 * parseConfiguration(XNode root)
 * 从node节点开始。。开始解析
 * 其中XMLConfigBuilder的settingsElement会设置默认值
 * mapperElement(XNode parent)解析mapper。xml
 * XMLStatementBuilder解析语句
 * builderAssistant.addMappedStatement 添加statement
 * 一个MappedStatement代表一个语句 ！
 * mapperRegistry保存了一些接口隐射信息。。以及代理对象的工厂MapperProxyFactory
 * 也有其他各种各种解析
 * 返回new DefaultSqlSessionFactory(config);工厂
 * 2.获取sqlSession对象
 * openSessionFromDataSource
 * ExecutorType：Simple
 * Executor executor = configuration.newExecutor(tx, execType);获取到Executor  mybatis重要的4大件之一
 * executor = new SimpleExecutor(this, transaction);
 * executor = new CachingExecutor(executor);
 * executor = (Executor) interceptorChain.pluginAll(executor); //用插件包装。实际为一个Interceptor拦截器
 * decorator装饰器模式
 * new DefaultSqlSession(configuration, executor, autoCommit);
 * 返回sqlSession对象
 * 3 getMapper
 * configuration.<T>getMapper(type, this);
 * mapperRegistry.getMapper(type, sqlSession);
 * MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
 * 获取到mapper的代理。这里是mapperRegistry里面再配置解析时让如的代理对象
 * new MapperProxy<T>(sqlSession, mapperInterface, methodCache);
 * MapperProxy<T> implements InvocationHandler 动态代理的接口
 * Proxy.newProxyInstance jdk的动态代理
 * 返回mapperProxy代理对象
 * 4.执行流程
 * MapperProxy.invoke
 * 先判断是不是object的方法或者是不是默认方法等
 * 最终执行 mapperMethod.execute(sqlSession, args)
 * 包装参数 method.convertArgsToSqlCommandParam(args);
 * result = sqlSession.selectOne(command.getName(), param)；
 * MappedStatement ms = configuration.getMappedStatement(statement);获取到MappedStatement。解析配置时获取到的mappedStatement
 * executor.query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);用executor执行
 * ms.getBoundSql(parameterObject);获取绑定的sql。
 * 实例：里面有sql语句：select * from tbl_employee where id = ?
 * 以及有参数列表
 * 之后执行query
 * 先检查二级缓存cache。。没有命中则继续query
 * delegate.<E> query(ms, parameterObject, rowBounds, resultHandler, key, boundSql); delegate就是SimpleExecutor
 * 再检查一级缓存   list = resultHandler == null ? (List<E>) localCache.getObject(key)
 * 没有就继续查询并放入缓存
 * list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);//真他妈的。。框架就是调用地狱
 * list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
 * StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);创建statementHandler 重要对象
 * delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);默认是就这个PreparedStatementHandler 重要对象
 * statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);拦截器包装statementHandler
 * prepareStatement(handler, ms.getStatementLog())
 * 里面会获取连接  并预编译语句
 * PreparedStatementHandler。setParameters来这是参数
 * typeHandler.setParameter(ps, i + 1, value, jdbcType);设置预编译参数
 * <p>
 * 执行查询PreparedStatement ps = (PreparedStatement) statement;//com.mysql.jdbc.JDBC42PreparedStatement@17fc391b: select * from tbl_employee where id = 1
 * ps.execute();
 * resultSetHandler处理查询结果其中用到了比较重要的typeHandler DefaultResultSetHandler
 * 返回结果
 * <p>
 * <p>
 * 总结：
 * 1.根据配置文件创建Configuration对象
 * 2.创建一个DefaultSqlSession对象
 * 里面包含了Configuration记忆Executro
 * 3.DefaultSqlSession。getMapper（）获取Mapper接口的MapperProxy
 * 4.MapperProxy里面有DefaultSqlSession对象
 * 5执行增删改查方法：
 * 调用DefaultSqlSession的增删改查
 * 创建StatementHandler对象的同时创建ParameterHandler和ResultSetHandler
 * 调用StatementHandler预编译参数和设置参数方法
 * 调用ParameterHandler设置参数
 * 调用StatementHandler的增删改查
 * ResultSetHandler封装结果
 * <p>
 * 四个主要对象（Executor，ParameterHandler，ResultSetHandler，StatementHandler）创建都有一个interceptorChain.pluginAll。用与插件
 * 1.Configuration对象有newExecutor 和newParameterHandler等方法。。创建对象之后都会调用pluginALl来增强
 * 2.获取到所有的Interceptor
 * 调用interceptor。plugin（target）；返回target包装后的对象
 * 3.插件机制，我们可以使用插件为目标创建一个代理对象
 */

public class TheoryMybatis {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory = getFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
//        employeeMapper.getEmpById(1);
        Page<Object> page = PageHelper.startPage(1, 5);
        System.out.println(employeeMapper.findAll());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
//   PageInfo更详细
    }

    private static SqlSessionFactory getFactory() throws IOException {
        //全局配置文件
        String resource = "mybatisConfig/config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }


    /**
     *
     */
    public void testPlugin() {

    }
}
