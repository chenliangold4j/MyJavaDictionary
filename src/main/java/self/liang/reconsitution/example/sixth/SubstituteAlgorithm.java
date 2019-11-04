package self.liang.reconsitution.example.sixth;

import java.util.Arrays;
import java.util.List;

/**
 * 替换算法
 *
 * 示例的修改之后，扩展字段会少写很多if
 *
 * 算法不一定是空间复杂度和时间复杂度。。也有结构复杂度。
 *
 */
public class SubstituteAlgorithm {

    public String foundPerson(String[] people){
        for(int i = 0;i<people.length;i++){
            if(people.equals("Don")){
                return "Don";
            }
            if(people.equals("John")){
                return "John";
            }
            if(people.equals("Kent")){
                return "Kent";
            }
        }
        return "";
    }


    public String foundPerson2(String[] people){
        List<String> condidates = Arrays.asList(new String[]{"Don","John","Kent"});
        for(int i = 0;i<people.length;i++){
          if(condidates.contains(people[i]))return  people[i];
        }
        return "";
    }
}
