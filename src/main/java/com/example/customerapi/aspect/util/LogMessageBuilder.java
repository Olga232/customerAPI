package com.example.customerapi.aspect.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Util class to create string messages for logging aspects.
 *
 * @author Olha Ryzhkova
 * @version 1.0
 */
public class LogMessageBuilder {

    /**
     * Gets a method from JoinPoint and creates a string representation of the method's arguments names and their
     * values respectively.
     *
     * @param jp {@link JoinPoint}
     * @return string representation of arguments names and their values.
     */
    public static String argsNamesAndValuesAsString(JoinPoint jp) {
        final String[] parameterNames = ((MethodSignature) jp.getSignature()).getParameterNames();
        final Object[] pjpArgs = jp.getArgs();

        final StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            messageBuilder.append(parameterNames[i]).append("=");
            messageBuilder.append(pjpArgs[i]).append(" ");
        }
        return messageBuilder.toString();
    }
}
