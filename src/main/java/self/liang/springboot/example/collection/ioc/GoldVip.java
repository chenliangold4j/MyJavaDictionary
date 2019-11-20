package self.liang.springboot.example.collection.ioc;

import org.springframework.stereotype.Service;

@Service
public class GoldVip implements  IVipService {
    @Override
    public int discount(int price) {
        return (int) (price*0.5);
    }
}
