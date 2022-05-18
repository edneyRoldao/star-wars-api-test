package com.test.starwarsapi.aspect;

import com.test.starwarsapi.aspect.annotation.SkipLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class LoggingMethodAspect {

    @Around("@annotation(com.test.starwarsapi.aspect.annotation.Logger)")
    public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
        UUID ID = UUID.randomUUID();
        final Logger logger = LoggerFactory.getLogger(pjp.getSourceLocation().getWithinType());

        String methodName = getClassAndMethodName(pjp);
        Object[] args = getArgsValue(pjp);

        logger.info("stage=init method={} args={} ID={}", methodName, args, ID);

        try {
            Object proceed = pjp.proceed();

            if (Objects.nonNull(proceed))
                logger.info("stage=end method={} return={} ID={}", methodName, proceed, ID);
            else
                logger.info("stage=end method={} ID={}", methodName, ID);

            return proceed;

        } catch (Throwable t) {
            String baseMsg = "%s.%s(line:%s)";
            StackTraceElement el = t.getStackTrace()[0];
            String location = String.format(baseMsg, el.getClassName(), el.getMethodName(), el.getLineNumber());
            String error = Objects.nonNull(t.getMessage()) ? t + ":" + t.getMessage() : t.toString();
            logger.info("stage=error method={} location={} error={} ID={}", methodName, location, error, ID);
            throw t;
        }

    }

    private String getClassAndMethodName(ProceedingJoinPoint pjp) {
        return pjp.getSourceLocation().getWithinType().getSimpleName() + "." + pjp.getSignature().getName();
    }

    private Object[] getArgsValue(ProceedingJoinPoint pjp) {
        Method method = getMethod(pjp);
        Object[] parametersShouldBeLogged = removeHiddenParameters(pjp.getArgs(), method);

        return checkParametersToStringNotImplemented(parametersShouldBeLogged, method);
    }

    private Object[] removeHiddenParameters(Object[] parameters, Method method) {
        List<Object> filteredParameters = new ArrayList<>();
        Annotation[][] annotations = method.getParameterAnnotations();

        for (int i = 0; i < parameters.length; i++) {
            Annotation[] annotationsFromParam = annotations[i];

            if (Arrays.stream(annotationsFromParam).noneMatch(a -> a instanceof SkipLog)) {
                filteredParameters.add(parameters[i]);
            }
        }

        return filteredParameters.toArray();
    }

    private Object[] checkParametersToStringNotImplemented(Object[] parameters, Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        List<Object> filteredParameters = new ArrayList<>();

        for (int i = 0; i < parameters.length; i++) {
            Object param = parameters[i];
            Class<?> paramType = paramTypes[i];

            if (param.toString().contains(paramType.getName() + "@")) {
                String argValue = paramType.getSimpleName() + "=toString not implemented";
                filteredParameters.add(argValue);
            } else {
                filteredParameters.add(param);
            }
        }

        return filteredParameters.toArray();
    }

    private Method getMethod(ProceedingJoinPoint pjp) {
        return Arrays.stream(pjp.getSourceLocation().getWithinType().getDeclaredMethods())
                .filter(m -> m.getName().equals(pjp.getSignature().getName()))
                .findFirst().orElse(null);
    }

}
