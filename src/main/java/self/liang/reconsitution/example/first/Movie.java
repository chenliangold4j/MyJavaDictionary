package self.liang.reconsitution.example.first;

import sun.java2d.cmm.Profile;

import java.util.ArrayList;
import java.util.List;


/**
 *  这个简单示例的问题：
 *      1. statement() 方法做的事情有点多
 *      2. 假设现在要返回html，statement()方法无法复用，很可能为了快速开发，就复制粘贴并修改-> htmlStatement()
 *      3.计费标准发生变化的话，新增的htmlStatement 和statement都要发生变化
 *      4.用户希望修改影片分类规则，但是还没有决定怎么改。--- 预期会新增的事项。如果有好几处逻辑都和分类相关，那么都要修改
 *      5.古老谚语：如果没坏，就不要动它。。但是现在不动，其实会有很大问题
 *
 *   重点1：如果你发现自己需要为程序添加一个特性，而代码结构使你无法很方便的达成目的，拿就先重构那个程序，使得特性添加比较容易，
 *          之后再添加特性。（一旦要对一个特性做添加，，往往之后还会继续添加）
 *
 *    重构的第一步：
 *          1）为修改的代码建立一组可靠的测试环境。这些测试是非常必要的。是人就会犯错，技术再高也要流程保证。
 *          2）你必须让测试有自能自我检验，否则就得耗费大把时间来回比对。（就像我之前做的修改设备的测试，必须到设备上去查看是否成功，所以：如果你的测试不能直接告诉你对错，那你的测试环境就没有完成）
 *          3）开始重构
 *
 *    重构statement：
 *          1）找出函数内的局部变量和参数：这里是rental和 thisAmount。前者并未被修改，后者会被修改。任何不会被修改的变量都可以当成参数传入，
 *             而会被修改的就需格外小心，如果只有一个变量会被修改，我们可以把它当作返回值。thisAmount是个临时变量，我们可以把它当作新函数返回值。
 *          2）修改之后的变量名，，重构之前可以理解的变量名，重构之后可能难以理解。所以用尽可能有意义的变量名  ！//重构第一步结束
 *          3） 观察amountFor函数 这个函数使用了Rental类的信息，却没有使用customer类的信息
 *              绝大多数的情况下，函数应该放在它所使用的数据的所属对象内，所以amountFor（）应该移动到Rental类 ！//重构第二步
 *          4）thisAmount重构两步之后变得多余  可以用replace把它去掉
 *              作者喜欢尽量除去临时变量，因为往往会引发问题。会导致大量参数传来传去。去除会付出性能代价，但是可以通过缓存来优化。
 *          5）之后对 int frequentRenterPoints = 0; “常客积分计算”做类似处理。而这个积分规则似乎没什么变化。可以放在Rental类 ！//重构第三步
 *          6)去除临时变量，他们只有在自己所属的函数中有效。这里有两个临时变量，两者都是从Customer对象相关的Rental对象中获得某个总量
 *            运用 replace temp with query，利用查询函数来取代 totalAmount和frequentRentalPoints这两个临时变量。 ！//重构第四步
 *
 *          这四步结束之后。会发现statement5的String获取。几乎都被封装成了外面的函数，如果需要一个html的模板，方法很容易就能给出正确的数值
 *
 *          但是。因为预期分类方法马上要变更，与之相对应的费用计算和积分计算还有待确定。所以不能直接修改计算方法，所以我们必须进入计算方法中
 *          把【因条件而异的代码】替换掉。继续
 *
 *          6）switch语句中，最好不要在另一个对象的属性基础上运用switch语句，如果非要使用，也应该是在对象自己的数据上使用
 *              所以将getCharge方法放到Movie类，并传入Rental的租期长度（daysRented）。这么做的理由是：因为会引入新的影片
 *              类型，这方法逻辑又和影片类型相关，所以把引入的影响控制在一个对象内，在真正添加类型的时候，不需要修改两个类
 *              的代码。同理重构获取积分的方法。因为它也和电影类型相关 ！//重构第五步
 *
 *          7)继承，首先考虑对Movie继承，每种类型一个Movie的子类，被否决。因为Movie可以修改自己的类型，但是movie的子类却不行。不符合
 *            逻辑，所以用了state模式（状态模式），引入一个price类，每种类型的影片都又自己的price的实现类(不同的获取价格的计算方法)
 *            详细说明，引入price类之后，将getCharge和getFrequentRenterPoints都用继承的方式实现了一遍  这样修改影片类型的话，只需要
 *            添加一个子类并加入相应的实现类即可，改变费用计算规则也和其他信息独立开来.最终由Price类的子类应对类型，积分和价格变化的修改。而
 *            其他逻辑不用改变
 *            详见movie2 和price类及其继承类。重要的是。。不用改变对外的方法
 *            ！//重构第六步   这里最帅的一步
 *
 * 总结：
 *      1.所有的重构行为都使责任的分配更合理，代码的维护更轻松。具有强烈的有别与过程化的风格。习惯这种风格！！！！
 *      2.测试，小修改，测试，小修改 循环往复。。。像是重构价格类的时候，，简单的逻辑我都复制错了。。。对于稍微复杂的逻辑。。没有测试的话
 *        这种修改很难保证正确性
 *
 * 这个第一章。。仔细读来。。。劳资头皮发麻。。。劳资好菜
 *
 */

public class Movie {

    /**
     * 影片，纯数据类
     */

    public static final int CHILDRENS = 2; //childrens
    public static final int REGULAR = 0;//regular 普通的，定期的
    public static final int NEW_RELEASE=1;// 新发行

    private String title;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        setPriceCode(priceCode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    //第五步
    public  double getCharge(int daysRented){
        double thisAmount = 0;
        switch (getPriceCode()) {
            case Movie.CHILDRENS:
                thisAmount += 2;
                if (daysRented > 2)
                    thisAmount += (daysRented - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += daysRented * 3;
                break;
            case Movie.REGULAR:
                thisAmount += 1.5;
                if ( daysRented > 3)
                    thisAmount += ( daysRented - 3) * 1.5;
                break;
            default:
                break;
        }
        return  thisAmount;
    }

    public int getFrequentRenterPoints(int daysRented){
        int frequentRenterPoints = 0;
        //    //add frequent renter points  增加经常租赁点?
//    //add bonus for a two day new release rental  增加奖金为一个为期两天的新发布租赁?
        if((getPriceCode() == Movie.NEW_RELEASE) && daysRented > 1){
            frequentRenterPoints = 2;
        }else{
            frequentRenterPoints = 1;
        }

        return frequentRenterPoints;
    }
    //第五步结束
}

//第六步 为了说明第六步
class Movie2 {
    private String title;
    private Price priceCode;

    public Movie2(String title, int priceCode) {
        this.title = title;
        setPriceCode(priceCode);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriceCode() {
        return priceCode.getPriceCode();
    }

    public void setPriceCode(int priceCode) {
        switch (priceCode){
            case Movie.REGULAR:
                this.priceCode = new RegularPrice();
                break;
            case Movie.CHILDRENS:
                this.priceCode = new ChildrensPrice();
                break;
            case Movie.NEW_RELEASE:
                this.priceCode = new NewReleasePrice();
                break;
            default:throw new IllegalArgumentException("incorrent price code");
        }
    }
    public  double getCharge(int daysRented){
        double thisAmount = 0;
        switch (getPriceCode()) {
            case Movie.CHILDRENS:
                thisAmount += 2;
                if (daysRented > 2)
                    thisAmount += (daysRented - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += daysRented * 3;
                break;
            case Movie.REGULAR:
                thisAmount += 1.5;
                if ( daysRented > 3)
                    thisAmount += ( daysRented - 3) * 1.5;
                break;
            default:
                break;
        }
        return  thisAmount;
    }

    public int getFrequentRenterPoints(int dayRenteds){
        return  priceCode.getFrequentRenterPoints(dayRenteds);
    }

}

//第六步结束

/**
 * 租赁
 */
class Rental {
    private Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    //第二步重构
    public  double getCharge(){
        double thisAmount = 0;
        switch (getMovie().getPriceCode()) {
            case Movie.CHILDRENS:
                thisAmount += 2;
                if (getDaysRented() > 2)
                    thisAmount += (getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount +=  getDaysRented() * 3;
                break;
            case Movie.REGULAR:
                thisAmount += 1.5;
                if ( getDaysRented() > 3)
                    thisAmount += ( getDaysRented() - 3) * 1.5;
                break;
            default:
                break;
        }
        return  thisAmount;
    }
    //end 第二步重构

    //第三步：
    public int getFrequentRenterPoints(){
        int frequentRenterPoints = 0;
        //    //add frequent renter points  增加经常租赁点?
//    //add bonus for a two day new release rental  增加奖金为一个为期两天的新发布租赁?
            if((getMovie().getPriceCode() == Movie.NEW_RELEASE) && getDaysRented() > 1){
                frequentRenterPoints = 2;
            }else{
                frequentRenterPoints = 1;
            }

        return frequentRenterPoints;
    }
    //end 第三步

    //第五步
    public  double getCharge2(){
        return  movie.getCharge(daysRented);
    }

    public int getFrequentRenterPoints2(){
        return movie.getFrequentRenterPoints(daysRented);
    }
    //end第五步

}


class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {

        double totalAmount = 0;//合计 费用
        int frequentRenterPoints = 0;  //合计积分
        String result = "Rental record for" + getName()+"\n";
        for (Rental rental : rentals) {

            double thisAmount = 0;
            switch (rental.getMovie().getPriceCode()) {
                case Movie.CHILDRENS:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2)
                        thisAmount += (rental.getDaysRented() - 2) * 1.5;
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += rental.getDaysRented() * 3;
                    break;
                case Movie.REGULAR:
                    thisAmount += 1.5;
                    if (rental.getDaysRented() > 3)
                        thisAmount += (rental.getDaysRented() - 3) * 1.5;
                    break;
                default:
                    break;
            }

            //add frequent renter points  增加经常租赁点?
            frequentRenterPoints ++;
            //add bonus for a two day new release rental  增加奖金为一个为期两天的新发布租赁?
            if((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1) frequentRenterPoints++;

            //show figures for this rental  显示租金的数字
            result +="\t"+rental.getMovie().getTitle()+"\t"+String.valueOf(thisAmount)+"\n";
            totalAmount += thisAmount;
        }

        //add footer lines;

        result += "Amount owd is "+String.valueOf(totalAmount)+"\n";
        result += "you earned"+String.valueOf(frequentRenterPoints) +"frequent renter points";
        return result;
    }

    //第一步重构的版本
    public String statement2() {

        double totalAmount = 0;//合计 费用
        int frequentRenterPoints = 0;  //合计积分
        String result = "Rental record for" + getName()+"\n";
        for (Rental rental : rentals) {
            double thisAmount = amountFor(rental);


            //show figures for this rental  显示租金的数字
            result +="\t"+rental.getMovie().getTitle()+"\t"+String.valueOf(thisAmount)+"\n";
            totalAmount += thisAmount;
        }

        //add footer lines;

        result += "Amount owd is "+String.valueOf(totalAmount)+"\n";
        result += "you earned"+String.valueOf(frequentRenterPoints) +"frequent renter points";
        return result;
    }

    private  double amountFor(Rental rental){
        double thisAmount = 0;
        switch (rental.getMovie().getPriceCode()) {
            case Movie.CHILDRENS:
                thisAmount += 2;
                if (rental.getDaysRented() > 2)
                    thisAmount += (rental.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += rental.getDaysRented() * 3;
                break;
            case Movie.REGULAR:
                thisAmount += 1.5;
                if (rental.getDaysRented() > 3)
                    thisAmount += (rental.getDaysRented() - 3) * 1.5;
                break;
            default:
                break;
        }
        return  thisAmount;
    }
    //end 第一步重构

    //第二步重构
    public String statement3() {
        double totalAmount = 0;//合计 费用
        int frequentRenterPoints = 0;  //合计积分
        String result = "Rental record for" + getName()+"\n";
        for (Rental rental : rentals) {
            double thisAmount = rental.getCharge();
            //add frequent renter points  增加经常租赁点?
            frequentRenterPoints ++;
            //add bonus for a two day new release rental  增加奖金为一个为期两天的新发布租赁?
            if((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1) frequentRenterPoints++;

            //show figures for this rental  显示租金的数字
            result +="\t"+rental.getMovie().getTitle()+"\t"+String.valueOf(thisAmount)+"\n";
            totalAmount += thisAmount;
        }
        //add footer lines;

        result += "Amount owd is "+String.valueOf(totalAmount)+"\n";
        result += "you earned"+String.valueOf(frequentRenterPoints) +"frequent renter points";
        return result;
    }
    //end第二步重构

    //第三步
    public String statement4() {
        double totalAmount = 0;//合计 费用
        int frequentRenterPoints = 0;  //合计积分
        String result = "Rental record for" + getName()+"\n";
        for (Rental rental : rentals) {
            frequentRenterPoints += rental.getFrequentRenterPoints();
            //show figures for this rental  显示租金的数字
            result +="\t"+rental.getMovie().getTitle()+"\t"+String.valueOf( rental.getCharge())+"\n";
            totalAmount +=  rental.getCharge();
        }
        //add footer lines;
        result += "Amount owd is "+String.valueOf(totalAmount)+"\n";
        result += "you earned"+String.valueOf(frequentRenterPoints) +"frequent renter points";
        return result;
    }
    //end 第三步

    //第四步
     private double getTotalCharge(){
        double result = 0;
        for(Rental rental : rentals){
            result += rental.getCharge();
        }
        return result;
     }

     private int getTotalFrequentRenterPoints(){
        int result = 0;
        for(Rental rental :rentals){
            result += rental.getFrequentRenterPoints();
        }
        return result;
     }

    public String statement5() {
        String result = "Rental record for" + getName()+"\n";
        for (Rental rental : rentals) {
            //show figures for this rental  显示租金的数字
            result +="\t"+rental.getMovie().getTitle()+"\t"+String.valueOf( rental.getCharge())+"\n";
        }
        //add footer lines;
        result += "Amount owd is "+String.valueOf(getTotalCharge())+"\n";
        result += "you earned"+String.valueOf(getTotalFrequentRenterPoints()) +"frequent renter points";
        return result;
    }
    //第四步结束
}
//第六步
abstract  class  Price{
    abstract  int getPriceCode();
    abstract double getCharge(int daysRented);
    public int getFrequentRenterPoints(int daysRented){
        return 1;
    }
}

class ChildrensPrice extends Price{
    @Override
    int getPriceCode() {
        return Movie.CHILDRENS;
    }
    double getCharge(int daysRented){
        double result = 1.5;
        if(daysRented > 3)
            result += (daysRented -3) * 1.5;
        return result;
    }
}

class NewReleasePrice extends Price{
    @Override
    int getPriceCode() {
        return Movie.NEW_RELEASE;
    }

    double getCharge(int daysRented){
        return daysRented * 3;
    }

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return daysRented > 1 ? 2: 1;
    }
}

class RegularPrice extends Price{
    @Override
    int getPriceCode() {
        return Movie.REGULAR;
    }

    double getCharge(int daysRented){
        double result = 2;
        if(daysRented > 2)
            result += (daysRented -2) * 1.5;
        return result;
    }
}
