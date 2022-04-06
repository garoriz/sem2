package ru.kpfu.stud.garipov.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /*@Pointcut("execution(* ru.kpfu.stud.garipov..*.*.*(..))")
    public void logActions() {

    }*/

    @Pointcut("@annotation(Loggable)")
    public void logActions() {

    }

    @Around("logActions()")
    public Object logAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getName();
        String methodName = methodSignature.getName();

        Object result = proceedingJoinPoint.proceed();

        if (className.equals("ru.kpfu.stud.garipov.controller.MainController")) {
            if (methodName.equals("getSignUp")) {
                LOGGER.info("User in sign up page");
            } else if (methodName.equals("getHome")) {
                LOGGER.info("User in home page");
            } else if (methodName.equals("getIndexPage")) {
                LOGGER.info("User in index page");
            }
        } else if (className.equals("ru.kpfu.stud.garipov.controller.UserController")) {
            if (methodName.equals("getAll")) {
                LOGGER.info("Shown all users");
            } else if (methodName.equals("get")) {
                LOGGER.info("Shown user by id");
            } else if (methodName.equals("verify")) {
                LOGGER.info("User verified account");
            }
        } else if (className.equals("ru.kpfu.stud.garipov.controller.WeatherController")) {
            if (methodName.equals("weather")) {
                LOGGER.info("Shown weather");
            }
        }

        return result;
    }
}
