package self.liang.springboot.example.collection.ioc;

import org.springframework.stereotype.Service;

@Service
public class NormalVip implements  IVipService {
    @Override
    public int discount(int price) {
        return (int) (price * 0.8);
    }
}
