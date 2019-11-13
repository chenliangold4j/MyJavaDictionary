package self.liang.springboot.example.config;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class XmlBeanForWX {

    String names;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
