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





    /*
    * private class Snapshot implements Leadable {
        private String name;
        private Map<Currence, Integer> mapVal;

        public Snapshot() {
            this.name = Account.this.name;
            this.mapVal = new HashMap<>(Account.this.mapVal);
        }

        @Override
        public void load() {
            Account.this.name = this.name;
            Account.this.mapVal = new HashMap<>(this.mapVal);
        }

    }

    public Leadable Save() {
        return new Snapshot();
    }
    *
    * */


}
