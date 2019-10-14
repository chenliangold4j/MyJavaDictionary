package self.liang.spring.example.bean.conditions;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//自定义逻辑。返回需要导入的组件
public class MyImportSelector implements ImportSelector {



    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        importingClassMetadata.get
        return new String[]{"self.liang.spring.example.bean.simulate.Blue","self.liang.spring.example.bean.simulate.Red"};
    }
}
