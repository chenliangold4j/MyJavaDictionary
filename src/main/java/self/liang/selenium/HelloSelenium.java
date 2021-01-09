package self.liang.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloSelenium {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
//        #get 方法 打开指定网址
        driver.get("https://www.baidu.com");

//#get 方法 打开指定网址
//        driver.get('http://www.baidu.com')
//
//#选择网页元素
//                element_keyword = driver.find_element_by_id('kw')
//
//#输入字符
//        element_keyword.send_keys('宋曲')
//
//#找到搜索按钮
//                element_search_button = driver.find_element_by_id('su')
        By by = By.className("s_ipt");
        WebElement element = driver.findElement(by);
        element.sendKeys("csdn");
        by = By.id("su");
        element = driver.findElement(by);
        element.submit();
    }

}
