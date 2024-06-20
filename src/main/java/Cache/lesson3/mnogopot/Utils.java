package Cache.lesson3.mnogopot;

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
