package self.liang.spring.example.bean;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFIlter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        //获取当前类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata =  metadataReader.getClassMetadata();

        //获取当前类资源信息（类路径)
        Resource resource = metadataReader.getResource();

        String classname = classMetadata.getClassName();
        System.out.println("class name : "+classname);
        if(classname.contains("er"))return true;
        return false;
    }
}
