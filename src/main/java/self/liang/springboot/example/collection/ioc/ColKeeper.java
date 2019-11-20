package self.liang.springboot.example.collection.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ColKeeper {

    @Autowired
    List<IVipService> vipTypes;


    public List<IVipService> getVipTypes() {
        return vipTypes;
    }

    public void setVipTypes(List<IVipService> vipTypes) {
        this.vipTypes = vipTypes;
    }
}
