package self.liang.reconsitution.example.eighth;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * 你有一些领域数据身于gui控件中，而领域函数需要访问这些数据。
 *
 * 将该数据复制到有个领域对象中。建立一个Observer模式，用以同步领域对象和gui对象内的重复数据。
 *  用以同步领域对象和gui对象内的重复数据。
 *
 *  一个分层良好的系统，应该将处理用户界面和处理业务逻辑的代码分开。之所以这样做，原因有以下几点：
 *         1）你可能需要使用不用的用户界面来表现相同的业务逻辑，如果同时承担两种责任，用户界面会变得过分复杂；
 *         2）与gui隔离之后，领域对象的维护和演化都会更容易，你甚至可以让不同的开发者负责不同部分的开发。
 *
 *  业务逻辑不应该内嵌于用户界面中。
 *
 * 尽量用观察者模式更新ui，像是android。activity就做更新ui的操作即可，业务逻辑，数据交换交给其他层。
 *
 * 然后测试的话。直接从业务逻辑层开始测试。避免测试需要启动activity。
 *
 */
public class DuplicateObservedData {

    public static void main(String[] args) {
        Publish publish = new Publish();
        Subscribe subscribe = new Subscribe("监察1",publish);
        Subscribe subscribe2 = new Subscribe("监察2",publish);

        publish.setData("test");
        subscribe.delOb();
        publish.setData("test2");
    }

}
class Subscribe implements Observer{

    private Observable obs;

    private String name;

    public Subscribe(String name,Observable observable) {
        this.obs = observable;
        this.name = name;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name+"收到通知:"+((Publish)o).getData());
    }

    public void delOb(){
        obs.deleteObserver(this);
    }
}

class Publish extends  Observable{

    private String data = "";
    public String getData(){
        return data;
    }

    public void setData(String data){
        if(!this.data.equals(data)){
            this.data = data;
            setChanged();
        }
        notifyObservers();
    }




}


