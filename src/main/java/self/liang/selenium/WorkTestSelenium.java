package self.liang.selenium;

import com.google.common.collect.Lists;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class WorkTestSelenium {
    public static ChromeDriver driver;
//    window.navigator.webdriver
    public static void main(String[] args) throws InterruptedException {
        getDriver();
        driver.get("http://192.168.0.170:9527");
        getByXpathAndSendKeys(driver, "//input[@name='username']", "admin");
        getByXpathAndSendKeys(driver, "//input[@name='password']", "123456");
        Thread.sleep(30000);
        getAndClick(driver, "//button[@class='el-button el-button--primary el-button--medium']");
    }

    private static void getByXpathAndSendKeys(WebDriver driver, String path, String keys) {
        By by = By.xpath(path);
        WebElement element = driver.findElement(by);
        element.sendKeys(keys);
    }

    private static void getAndClick(WebDriver driver, String path) {
        By by = By.xpath(path);
        WebElement element = driver.findElement(by);
        element.click();
    }


    private static void getDriver() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        // 关闭界面上的---Chrome正在受到自动软件的控制
        options.addArguments("--disable-infobars");
        // 允许重定向
        options.addArguments("--disable-web-security");
        // 最大化
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        List<String> excludeSwitches = Lists.newArrayList("enable-automation");
        options.setExperimentalOption("excludeSwitches", excludeSwitches);
        driver = new ChromeDriver(options);
    }

}
