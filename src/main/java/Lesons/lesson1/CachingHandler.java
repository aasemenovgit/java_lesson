package Lesons.lesson1;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CachingHandler<T> implements InvocationHandler {
    private Object zobj;
    private Object obj_cash;
    private T values = null;
    private Boolean vlChange = false;

    public CachingHandler(Object zobj) {
        this.zobj = zobj;
    }

    @Override
    public Object invoke(Object obj, @NotNull Method method, Object[] args) throws Throwable {
        Object res = null;
        Method m = zobj.getClass().getMethod(method.getName(), method.getParameterTypes());

        Annotation[] ccache = m.getAnnotationsByType(Cache.class);
        Annotation[] mutab = m.getAnnotationsByType(Mutator.class);

        for (Annotation cch : ccache) {
            vlChange = true;
        }

        for (Annotation mut : mutab) {
            values = null;
            vlChange = false;
        }

        if (values == null) {
            res = method.invoke(zobj, args);
            if (vlChange) values = (T) res;
        } else {
            res = values;
        }

        return res;
    }
}
