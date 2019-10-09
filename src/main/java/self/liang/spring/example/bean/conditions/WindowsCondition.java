package self.liang.spring.example.bean.conditions;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        System.out.println("-----------------"+context.getEnvironment().getProperty("os.name"));
        if(context.getEnvironment().getProperty("os.name").contains("Windows"))return true;
        return false;
    }
}
