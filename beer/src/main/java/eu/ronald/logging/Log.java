package eu.ronald.logging;

import java.lang.System.Logger;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

public class Log {


    @Produces
    public Logger create(InjectionPoint injectionPoint){
        var clazz = injectionPoint.getMember().getDeclaringClass();
        return System.getLogger(clazz.getSimpleName());
    }
}