package self.liang.spring.example.attribute;

import org.springframework.beans.factory.annotation.Value;

public class Man {
    @Value("三")
    String name;

    @Value("${man.address}")
    String address;


    @Value("#{20-2}")
    String level;


    @Override
    public String toString() {
        return "Man{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
