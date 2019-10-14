package self.liang.spring.example.bean.simulate;

import org.springframework.beans.factory.FactoryBean;

public class ColorFaceotryBean implements FactoryBean<Color> {

    //返回color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        System.out.println("get color");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
