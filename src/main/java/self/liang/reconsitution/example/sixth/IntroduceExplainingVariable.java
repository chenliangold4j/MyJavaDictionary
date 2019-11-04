package self.liang.reconsitution.example.sixth;

/**
 *
 * 引入解释性变量
 *
 * 重构不完全是减法。。
 * 在表达式不清晰的情况下，添加变量说明表达式的含义。。结构看起来更良好。
 *
 *  例子的复杂度还好。其实可以不用。。
 *  而且也可以用方法代替。（ExtractMethod）
 *  作者更喜欢  ExtractMethod
 *
 *  但是如果方法的局部变量巨多。。产生居多的方法没有必要。
 *  这时候Introduce Explaining Variable更适用一些
 */
public class IntroduceExplainingVariable {

    String platform = "mac";
    String browser = "ie";
    boolean isInit = false;
    int resize = 10;

    public boolean isInit() {
        return isInit;
    }

    public void ex1(){
        if(platform.toUpperCase().indexOf("MAC") > -1 &&
            browser.toUpperCase().indexOf("IE") > -1 &&
                isInit() && resize > 0 ){
            //do something.
        }
    }


    public void ex2(){

        final  boolean isMacOS = platform.toUpperCase().indexOf("MAC") > -1;
        final  boolean isIEBrowser = browser.toUpperCase().indexOf("IE") > -1;
        final  boolean wasResized = resize > 0;

        if(isIEBrowser && isMacOS && wasResized){
            //do something
        }

    }


}
