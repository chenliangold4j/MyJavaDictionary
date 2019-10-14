package self.liang.spring.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;


@Aspect
public class LogAspects {

    @Pointcut("execution(public int self.liang.spring.example.aop.MathCalculator.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.out.println("run logStart："+joinPoint.getSignature()+":"+ Arrays.toString(joinPoint.getArgs()));
    }


    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("run logEnd："+joinPoint.getKind()+":"+joinPoint.getSignature().getName());
    }


    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result) {//JoinPoint在第一位
        System.out.println("run logReturn："+result+joinPoint.getTarget());
    }


    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void logException(Exception e) {
        System.out.println("run logException："+e.getMessage());
    }


}


