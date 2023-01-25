package it.sosinski.aspectdirectory.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Aspect
public class MethodAroundAspect {

    /***
     * Logging method entry in format: methodName(parameters)
     * And method exit in format: methodName()=result
     *
     * For readability, it's good to implement toString() method in all the classes which can be used as method params.
     */
    @Around("@annotation(it.sosinski.aspectdirectory.logger.LogMethodAround)")
    public Object logMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = getFullClassName(joinPoint);
        String methodName = getMethodName(joinPoint);
        List<String> parameters = mapArgumentsToStringList(joinPoint.getArgs());

        Logger logger = Logger.getLogger(className);
        logger.info(formatEnterMessage(methodName, parameters));

        Object proceed = joinPoint.proceed();

        logger.info(formatExitMessage(methodName, proceed));

        return proceed;
    }

    private String getFullClassName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getTarget()
                .getClass()
                .getName();
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature()
                .getName();
    }

    private List<String> mapArgumentsToStringList(Object[] args) {
        return Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private String formatEnterMessage(String methodName, List<String> params) {
        String collectedParams = String.join(", ", params);
        return String.format("%s(%s)", methodName, collectedParams);
    }

    private String formatExitMessage(String methodName, Object result) {
        String resultAsString = result.toString();
        return String.format("%s()=%s", methodName, resultAsString);
    }
}
