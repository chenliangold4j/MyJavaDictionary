package self.liang.lambda.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class SomeExample
{
   
   public static void main(String[] args)
   {
      
   // Java 8之前：
      List<String> features1 = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
      for (String feature : features1) {
          System.out.println(feature);
      }

      // Java 8之后：
      List<String> features2 = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
      features2.forEach(n -> System.out.println(n));
       
      // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
      // 看起来像C++的作用域解析运算符
      features2.forEach(System.out::println);
      
      
      List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
      
      System.out.println("Languages which starts with J :");
      filter(languages, (str)->str.startsWith("J"));
   
      System.out.println("Languages which ends with a ");
      filter(languages, (str)->str.endsWith("a"));
   
      System.out.println("Print all languages :");
      filter(languages, (str)->true);
   
      System.out.println("Print no language : ");
      filter(languages, (str)->false);
   
      System.out.println("Print language whose length greater than 4:");
      filter(languages, (str)->str.length() > 4);
   }
   

   public static void filter(List<String> names, Predicate<String> condition) {
      for(String name: names)  {
          if(condition.test(name)) {
              System.out.println(name + " ");
          }
      }
  }
   
   
}
