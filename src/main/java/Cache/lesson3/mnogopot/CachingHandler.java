package Cache.lesson3.mnogopot;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CachingHandler<T> implements InvocationHandler {
    private Object zobj;
    private StateCH stateCh;

    private Map<StateCH, Map<Method, Ress>> cacheMap = new ConcurrentHashMap<>();

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
            Ress ress = mapRes.get(m); // поменять ресс на хаш
            //System.out.println(ress);
            long ttimes = m.getDeclaredAnnotation(Cache.class).vltime();
            //System.out.println("ttimes : "+ttimes);
            if (ress != null) {
                ress.tTime = System.currentTimeMillis() + ttimes;
                if (ttimes == 0) ress.tTime = 0L;
                mapRes.put(m, ress);
                //System.out.println(mapRes);
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
            mapRes = new ConcurrentHashMap<>();
            cacheMap.put(stateCh, mapRes);
        }

        if(m.isAnnotationPresent(Mutator .class)){
            cacheClear().start();
            //cacheClear().join();

        }
        {


    //            stateCh = new StateCH(stateCh,m,args);
    //            if (cacheMap.containsKey(stateCh)) {
    //                mapRes =cacheMap.get(stateCh);
    //
    //            }
    //            else{
    //                mapRes = new ConcurrentHashMap<>();
    //                cacheMap.put(stateCh, mapRes);
    //            }


        }
    //cacheClear().start();

        return(T)method.invoke(zobj,args);
}



    public  Thread cacheClear(){

        Runnable task = () -> {
//            for(Map <Method,Ress> map: cacheMap.values()){
//
//                for(Method ress: map.keySet()){
//                    if(map.get(ress).tTime<System.nanoTime()){
//                        map.remove(ress);
//                    }
//                }
//            }
                //Map copy = new HashMap(cacheMap);

            for(Map.Entry<StateCH, Map<Method,Ress>> entry : cacheMap.entrySet())
            {
                //System.out.println("dell0");
               for(Map.Entry<Method,Ress> innerEntry : entry.getValue().entrySet())
               {
                   if ((innerEntry.getValue().tTime < System.currentTimeMillis() ) && innerEntry.getValue().tTime!= 0L)
                   {
                        
                      System.out.println("dell");
                       entry.getValue().remove(innerEntry.getKey());

                   }
               }
            }



        };

        Thread thread = new Thread(task);
        thread.setName("POtok Semenov");


        return thread;
    }
//тредпул
    // cop
//    private class RessTred extends Thread {
//        @Override
//        public void run() {
//            for(Map.Entry<StateCH, Map<Method,Ress>> entry : cacheMap.entrySet())
//            {
//                for(Map.Entry<Method,Ress> innerEntry : entry.getValue().entrySet())
//                {
//                    if ((innerEntry.getValue().tTime < System.nanoTime()) && innerEntry.getValue().tTime!= 0L)
//                    {
//                        entry.getValue().remove(innerEntry.getKey());
//                    }
//                }
//            }
//        }
//    }


}


