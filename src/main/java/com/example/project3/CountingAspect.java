package com.example.project3;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CountingAspect {
    private int count = 0;

    @Pointcut("execution(public * com.example..*(..))")
    public void publicMethod() {}

    @After("publicMethod() && @annotation(Counted)")
    public void addCount(final JoinPoint joinPoint) {
        System.out.println("*** Number of methods executed: " + ++count);
    }
}
