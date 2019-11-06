package self.liang.reconsitution.example.eighth;

import java.util.HashSet;
import java.util.Set;

/**
 * 将单向关联改为双向关联
 *
 *unidirectional
 * 英 /,juːnɪdɪ'rekʃ(ə)n(ə)l; ,juːnɪdaɪ-/  美 /,jʊnɪdə'rɛkʃənl/  全球(英国)
 * adj. 单向的；单向性的
 *
 *association
 * 英 /əˌsəʊʃiˈeɪʃn/  美 /əˌsosɪˈeʃən/  全球(美国)
 * n. 协会，联盟，社团；联合；联想
 *
 * bidirectional
 * 英 /ˌbaɪdəˈrekʃənl; ˌbaɪdaɪˈrekʃənl/  美 /ˌbaɪdəˈrekʃənl,ˌbaɪdaɪˈrekʃənl/  全球(美国)
 * adj. 双向的；双向作用的
 *
 * 需要有一个类控制关联关系。
 * 1.如果两者都是引用对象，而其间的关联是“一对多”关系，那么就由“拥有单一引用”的那一方承担“控制者”角色。
 * 2.如果某个对象是组成另一个对象的组件，由后者控制
 * 3.如果是互相引用的多对多。那么随便其中哪个对象来控制关联关系。
 *
 * 这里  一个客户可以由多个订单，那就就有order2类（订单）来控制关联关系。
 *
 * 下列示例。修改之后变成了双向的关联。
 *
 */
public class ChangeUnidirectionalAssociationToBidirectional {

}

class Order2{
    Customer2 customer2 ;

    public Customer2 getCustomer2() {
        return customer2;
    }

    public void setCustomer2(Customer2 arg) {
        if(customer2 != null){
            customer2.frendOrders().remove(this);
            customer2 = arg;
            if(customer2 != null)
                customer2.frendOrders().add(this);
        }
    }
}

class Customer2{

    private Set<Order2> order2s = new HashSet<>();

    Set<Order2> frendOrders(){
        return  order2s;
    }

    void addOrder(Order2 order2){
        order2.setCustomer2(this);
    }

}

