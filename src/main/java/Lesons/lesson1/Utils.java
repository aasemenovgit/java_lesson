package Lesons.lesson1;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Utils  {

    public static <T>  T cache ( T objt){

        return (T) Proxy.newProxyInstance(
                objt.getClass().getClassLoader(),
                objt.getClass().getInterfaces(),
                new  CachingHandler <>(objt)
                );
}




}
