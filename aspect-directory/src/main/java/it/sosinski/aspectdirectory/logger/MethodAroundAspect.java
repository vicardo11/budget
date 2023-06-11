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
    public Object logMethodAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String className = getFullClassName(joinPoint);
        final String methodName = getMethodName(joinPoint);
        final List<String> parameters = mapArgumentsToStringList(joinPoint.getArgs());

        final Logger logger = Logger.getLogger(className);
        logger.info(formatEnterMessage(methodName, parameters));

        final Object proceed = joinPoint.proceed();

        logger.info(formatExitMessage(methodName, proceed));

        return proceed;
    }

    private String getFullClassName(final ProceedingJoinPoint joinPoint) {
        return joinPoint.getTarget()
                .getClass()
                .getName();
    }

    private String getMethodName(final JoinPoint joinPoint) {
        return joinPoint.getSignature()
                .getName();
    }

    private List<String> mapArgumentsToStringList(final Object[] args) {
        return Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private String formatEnterMessage(final String methodName, final List<String> params) {
        final String collectedParams = String.join(", ", params);
        return String.format("%s(%s)", methodName, collectedParams);
    }

    private String formatExitMessage(final String methodName, final Object result) {
        final String resultAsString = result.toString();
        return String.format("%s()=%s", methodName, resultAsString);
    }
}
