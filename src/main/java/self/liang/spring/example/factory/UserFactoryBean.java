package self.liang.spring.example.factory;

import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean<User> {

    private static final User user = new User();

    private String name;
    private String emial;

    @Override
    public User getObject() throws Exception {
        user.setEmail(emial);
        user.setName(name);
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    public boolean isSingleton(){
        return  true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }
}
