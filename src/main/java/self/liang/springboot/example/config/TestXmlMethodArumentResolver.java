package self.liang.springboot.example.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import self.liang.xml.example.JaxbUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

public class TestXmlMethodArumentResolver implements HandlerMethodArgumentResolver {



    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        if(parameter.getParameterType().isAssignableFrom(XmlBeanForWX.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //从Session 获取用户
        System.out.println("MethodParameterName:"+parameter.getParameterName());
        String body = getRequestBody(webRequest);
        XmlBeanForWX wx = JaxbUtil.xmlToBean(body, XmlBeanForWX.class);
        return wx;
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody=null;
            try {
                jsonBody = testGetIn(servletRequest.getInputStream());
                System.out.println("tttttttttttttt"+jsonBody);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return jsonBody;
    }

    private String testGetIn(InputStream inputStream){
        byte[] bytes = new byte[1024];
        try {
            int leng =  inputStream.read(bytes);
            return new String(bytes,0,leng,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
