package self.liang.springmvc.example.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import self.liang.mybatis.example.base.Employee;
import self.liang.mybatis.example.base.dao.EmployeeMapper;
import self.liang.springmvc.example.ConsoleAllBean;
import self.liang.springmvc.example.dao.Employee2Mapper;
import self.liang.springmvc.example.dao.EmployeeG2Mapper;
import self.liang.springmvc.example.entity.Employee2;
import self.liang.springmvc.example.entity.EmployeeG2;
import self.liang.springmvc.example.entity.EmployeeG2Example;
import self.liang.springmvc.example.otherbean.MVCTestService;

import java.util.List;

/**
 *
 * 流程为springBoot的demo，非此demo
 * springMvc执行流程分析。。从tomcat到springMvc
 *
 *分析的入口：
 *ApplicationFilterChain.internalDoFilter();用于调用过滤器链
 *          ApplicationFilterConfig[name=characterEncodingFilter, filterClass=org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter]
 *          ApplicationFilterConfig[name=formContentFilter, filterClass=org.springframework.boot.web.servlet.filter.OrderedFormContentFilter]
 *          ApplicationFilterConfig[name=requestContextFilter, filterClass=org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter]
 *          ApplicationFilterConfig[name=Tomcat WebSocket (JSR356) Filter, filterClass=org.apache.tomcat.websocket.server.WsFilter]
 *          当前由这四个ApplicationFilterConfig
 *               filter.doFilter(request, response, this);执行
 *                      首先看是不是HttpServletRequest，不是的话抛出异常
 *
 *                      String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
 * 		                boolean hasAlreadyFilteredAttribute = request.getAttribute(alreadyFilteredAttributeName) != null;
 * 		                查看是不是已经调用过。
 *
 * 		                request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);标记已经调用。
 * 		                doFilterInternal(httpRequest, httpResponse, filterChain);调用过滤器
 * 		                        if (encoding != null) {
 * 			                        if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
 * 			                    	request.setCharacterEncoding(encoding);
 *                                     }
 * 		                	    if (isForceResponseEncoding()) {
 * 			                        	response.setCharacterEncoding(encoding);
 *                  		    }
 *                  		    这里简单调用setCharacterEncoding即可
 *                              filterChain.doFilter(request, response);进入下一个filter
 *              执行完所有filter之后
 *              servlet.service(request, response);
 *                  HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());判断时get还是post
 *                  FrameworkServlet的 processRequest(HttpServletRequest request, HttpServletResponse response)
 *                      FrameworkServlet是Spring web框架的基本servlet实现类，通过JavaBean的方式集成了Application context，所有新实现的servlet最好都继承于该类。
 *                      该类提供了HttpServlet的所有接口实现，自带了一个web容器，它实现了WebApplicationContextAware接口，所以能根据指定的容器配置文件，来初始化自己管理的容器。
 *                          FrameworkServlet提供两个功能：
 *                              1.为每个servlet管理一个WebApplicationContext实例（即每个servlet会自己的一个web容器），
 *                              每个servlet都有自己的配置空间，相互独立。（每一个<servlet> tag之间的配置属于一个namespace, 配置一个application context。）
 *                              2.对每一个请求，无论是否成功处理，都会在每个请求上发布事件。（这里是不是可以做一些事情？）
 *
 *                      LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();//国际化信息
 * 		                LocaleContext localeContext = buildLocaleContext(request);//localContext的类型为DispatcherServlet
 *                      一系列初始化之后doService 这个就是DispatcherServlet的doService(HttpServletRequest request, HttpServletResponse response)方法
 *                              在大量设置attribute之后执行doDispatch(request, response); ！---重点---
 *                                   HandlerExecutionChain mappedHandler = getHandler(processedRequest);/获取HandlerExecutionChain,在HandlerExecutionChain中包含了我们的Controller以及拦截器
 *                                      首先可以明确HandlerExecutionChain与HandlerMapping关系非常紧密，HandlerExecutionChain只能通过HandlerMapping接口中的唯一方法来获得
 *                                                AbstractHandlerMethodMapping中的getHandlerInternal方法则会根据用户request信息中提供的Method来查找handler：
 *                                                AbstractUrlHandlerMapping中的getHandlerInternal方法会根据用户请求信息中的URL查找handler：
 *                                          Object handler = getHandlerInternal(request);会获取到需要执行的方法
 *                                          getHandlerExecutionChain(Object handler, HttpServletRequest request)，获取到方法和interceptors并封装。
 *                                                  在demo中 由一个映射到 com.example.demo.controller.TestController.test2(com.example.demo.bo.XmlBeanForWX) 的handler
 *                                                  以及conversionService（ConversionServiceExposingInterceptor），resourceUrlProvider(ResourceUrlProviderExposingInterceptor)  这两个interceptor；
 *                                                    其中conversionService包含了我添加的converter；！@！！！@！
 *                                                  AbstractHandlerMapping的initInterceptors（）方法，主要作用是返回一个HandlerInterceptor对象，然后将这个对象放到 this.adaptedInterceptors 集合中，这个集合比较重要
 *                                          HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

 *                                          mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
 *                                                  protected ModelAndView invokeHandlerMethod(HttpServletRequest request,HttpServletResponse response, HandlerMethod handlerMethod)
 *                                                          WebDataBinderFactory binderFactory = getDataBinderFactory(handlerMethod);//用于创建WebDataBinder用的，WebDataBinder用于参数绑定。主要用于参数于String之间的类型转换。
 *                                                          ModelFactory modelFactory = getModelFactory(handlerMethod, binderFactory);//用来处理Model，主要包含两个功能，1. 在Handler处理之前对Model初始化，2. 请求结束以后对Model参数进行更新
 *                                                          ServletInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(handlerMethod);//它继承自HandlerMethod，并且可以直接执行，实际请求的处理就是通过它来执行的，参数绑定、处理请求以及返回值的处理都在里面完成
 *                                                          这三个变量创建完之后的工作还有三步（省略异步处理）：
 *                                                              1.新建传递参数的ModelAndViewContainer容器，并将相应的参数设置到Model中
 *                                                              2.执行请求 invocableMethod.invokeAndHandle(webRequest, mavContainer);
 *                                                                      Object returnValue = invokeForRequest(webRequest, mavContainer, providedArgs);这里面做了两件事情。1.解析参数 2。调用方法。
 *                                                                              Object[] args = getMethodArgumentValues(request, mavContainer, providedArgs);//这里解析参数。
 *                                                                                  if (!this.resolvers.supportsParameter(parameter))
 *                                                                                      //这里会调用每一个解析器的supportsParameter 看解析器是否可以解析这种类型。如果匹配上，就会用resolveArgument来解析参数。
 *                                                                                          尴尬的是，会由一个默认的解析器解析对象。无法解析并不会报错。
 *                                                                                          private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache 对应的解析对象会用缓存保存起来。之后解析的时候会取出来
 *                                                                                  this.resolvers.resolveArgument(parameter, mavContainer, request, this.dataBinderFactory)；
 *                                                                        //TODO 关于converter的解析。之后继续。以上是关于自定义argumentResolver  之后是自定义的converter和formatter的调用
 *                                                              3.请求完成后进行一些后置处理 getModelAndView(mavContainer, modelFactory, webRequest);
 *
 *                                      简而言之就是：当用户请求到到DispaterServlet中后，配置的HandlerMapping会根据用户请求（也就是handler）会将它与所有的interceptors封装为HandlerExecutionChain对象
 *
 *                                   HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());       //通过我们的Handler来找到HandlerAdapter（处理器执行器）
 *
 */
@Controller
public class MVCTestController {

    @Autowired
    MVCTestService mvcTestService;

    @Autowired
    Employee2Mapper employee2Mapper;


    @Autowired
    EmployeeG2Mapper employeeG2Mapper;

    @RequestMapping("/testSuccess")
    @ResponseBody
    public String test() throws Exception {
        Employee2 employee = employee2Mapper.getEmpById(1);
        System.out.println(employee);
        return mvcTestService.goSuccess();
    }

    /**
     *
     * @return
     */
    @RequestMapping("/testMybatis")
    @ResponseBody
    public List<EmployeeG2> testMybatis() {
        EmployeeG2Example employeeG2Example = new EmployeeG2Example();
        EmployeeG2Example.Criteria criteria = employeeG2Example.createCriteria();
        criteria.andLastNameLike("%e%");
        criteria.andGenderEqualTo("1");
        EmployeeG2Example.Criteria criteria2 = employeeG2Example.createCriteria();
        criteria2.andEmailLike("%e%");
        employeeG2Example.or(criteria2);

        List<EmployeeG2> employeeG2List = employeeG2Mapper.selectByExample(employeeG2Example);
        return employeeG2List;
    }

}
