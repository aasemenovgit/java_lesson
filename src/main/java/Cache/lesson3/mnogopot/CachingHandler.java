package Cache.lesson3.mnogopot;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CachingHandler<T> implements InvocationHandler {
    private Object zobj;
    private StateCH stateCh;

    private Map<StateCH, Map<Method, Ress>> cacheMap = new HashMap<>();

    private Map<Method, Ress> mapRes;

    Object resObj = null;

    public CachingHandler(Object zobj) {
        this.zobj = zobj;
        stateCh = new StateCH();
        mapRes = new ConcurrentHashMap<>();
        cacheMap.put(stateCh, mapRes);
    }

    @Override
    public  Object invoke(Object obj, @NotNull Method method, Object[] args) throws Throwable {

        Method m = zobj.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (m.isAnnotationPresent(Cache.class))
        {
            Ress ress = mapRes.get(m);
           long ttimes = m.getDeclaredAnnotation(Cache.class).vltime();

            if (ress != null) {
                ress.tTime = System.currentTimeMillis() + ttimes;
                if (ttimes == 0) ress.tTime = 0L;
                mapRes.put(m, ress);

                return (T) ress.val;
            }

            resObj = method.invoke(zobj, args);
            ress = new Ress(System.currentTimeMillis()  + ttimes, resObj);
            if (ttimes == 0) ress.tTime = 0L;
            mapRes.put(m, ress);
            return (T) resObj;

        }
        stateCh = new StateCH(stateCh, m, args);
        if (cacheMap.containsKey(stateCh)) {
            mapRes = cacheMap.get(stateCh);

        } else {
            mapRes = new HashMap<>();
            cacheMap.put(stateCh, mapRes);
        }


        if(m.isAnnotationPresent(Mutator .class) || cacheMap.size()%5 ==0 ){
           CacheClear myThread = new CacheClear();
            myThread.start();

        }

    return(T)method.invoke(zobj,args);
}

    private class CacheClear extends Thread {
        private Map<StateCH, Map<Method, Ress>> cacheMap2 = new HashMap<>(cacheMap);

        @Override
        public void run() {

            for (Map.Entry<StateCH, Map<Method, Ress>> entry : cacheMap2.entrySet()) {

                for (Map.Entry<Method, Ress> innerEntry : entry.getValue().entrySet()) {
                    if ((innerEntry.getValue().tTime.longValue() < System.currentTimeMillis()) && innerEntry.getValue().tTime.longValue() != 0L) {
                        entry.getValue().remove(innerEntry.getKey());

                    }

                }
               cacheMap = new HashMap<>(cacheMap2);

            }
        }
    }
}
