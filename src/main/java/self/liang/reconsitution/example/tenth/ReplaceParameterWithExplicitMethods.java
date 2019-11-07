package self.liang.reconsitution.example.tenth;

/**
 * 以明确函数取代参数。
 *
 * 你有一个函数，其中完全取决于参数值而采取不同行为。
 *
 * 针对改参数的每一个可能值，建立一个函数。
 *
 */
public class ReplaceParameterWithExplicitMethods {

    int width;
    int height;

    void setValue(String name ,int val){
        if(name.equals("width"))width =val;
        if(name.equals("height"))height = val;
    }

    //修改
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
