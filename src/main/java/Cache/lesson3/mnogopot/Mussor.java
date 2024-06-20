package Cache.lesson3.mnogopot;

public class Mussor {
    /*package Cache.lesson3.mnogopot;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class CachingHandler<T> implements InvocationHandler {
    private Object zobj;
    private Object obj_cash;
    private T values = null;
    //TempRec rrecord = new TempRec();
   // private Map<T,Object[]> objMap = new HashMap<>();
    StateCH state;
   private Map<StateCH, T> cacheMap = new HashMap<>();

    Object res = null;
    //LocalDateTime
    //private Date date1 = new Date();

    public CachingHandler(Object zobj) {
        this.zobj = zobj;
    }
    //private final Map<State,Map<Method, Result>>
    @Override
    public Object invoke(Object obj, @NotNull Method method, Object[] args) throws Throwable {

        Method m = zobj.getClass().getMethod(method.getName(), method.getParameterTypes());
        //System.out.println("METH "+ Arrays.stream(m.getParameterTypes()).findFirst());

        //System.out.println(m.getParameters().);
       // Constructor<> c = zobj.getClass().getConstructor(  );
        //System.out.println(c.getParameters().toString());
       // Class[] parameterType = null;
       // System.out.println("Arr : "+Arrays.toString(Arrays.stream(zobj.getClass().getDeclaredFields()).toArray()));
     if (args == null) {

int i=0;
Object[] obbj = new Object[2];
 Field[] fl = zobj.getClass().getDeclaredFields();
        for (Field f : fl) {
            f.setAccessible(true);
            //System.out.println("fff "+f.get(zobj));
            //args.

            //    i++;    }
            obbj[i] = f.get(zobj);
            ++i;
            System.out.println(f.get(zobj));

        args = obbj;
     }
       // System.out.println(Arrays.stream(args).toArray().toString());
        Annotation[] ccache = m.getAnnotationsByType(Cache.class);
        Annotation[] mutab = m.getAnnotationsByType(Mutator.class);
      //  System.out.println(cacheMap.get(m));

        if(args!=null)
            for(Object arg : args)
            {
                System.out.println("arg: "+ arg);
            }

        boolean vlChange =false;

        for (Annotation cch : ccache) {
            vlChange = true;
        }

        for (Annotation mut : mutab) {

          // cacheMap.clear();
            vlChange = false;
        }

        //System.out.println("METH :"+m.toString());
        //System.out.println("OBJ :"+zobj.toString());
        String name;
//        Field[] fields = zobj.getClass().getDeclaredFields();
//        for (Field f : fields) {
//            System.out.println(f.));
//
//        }
        //rrecord(objd htrj)
        if (!cacheMap.equals(state) )
        {
            res = method.invoke(zobj, args);
            if (vlChange)
            {
                System.out.println(m.toString());
               // cacheMap.put(res, (T) res);
              // state = new StateCH(state, m, args) ;
               cacheMap.put(state, (T)res);
            };
            //else

        }

        else {


            res = cacheMap.get(state);

        }
       // System.out.println("state: "+state.getMethodMap().toString());
        System.out.println("res: "+res);
        //res = values;
       // System.out.println(values.getClass().getClassLoader());

    } return res;

    }
}
----18062024



package Cache.lesson3.mnogopot;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CachingHandler<T> implements InvocationHandler {
    private Object zobj;
    //private Object obj_cash;
    private T tObject = null;
    //TempRec rrecord = new TempRec();
   // private Map<T,Object[]> objMap = new HashMap<>();
    private StateCH stateCh;
    private Map<StateCH, Map<Method,Ress>> cacheMap = new HashMap<>();
    //private Map<Map<Method,Object>,T> mainMap = new HashMap<>();
   private Map<Method,Ress> timeRes;




   // private Map<Integer,T> timeMap = new HashMap<>();
    //private Map<Method, Map<Integer,T>> resMap = new HashMap<>();
    //private Map<StateCH, Map<Method, Map<Integer,T>>> cacheMap = new HashMap<>();

   Object resObj = null;
    //LocalDateTime
    //private Date date1 = new Date();

    public CachingHandler(Object zobj)
    {
        this.zobj = zobj;
        stateCh = new StateCH();
        timeRes= new ConcurrentHashMap<>();
        cacheMap.put(stateCh, timeRes);
    }

    @Override
    public Object invoke(Object obj, @NotNull Method method, Object[] args) throws Throwable {

    Method m = zobj.getClass().getMethod(method.getName(), method.getParameterTypes());

  //  Annotation ccache = m.getDeclaredAnnotation(Cache.class);// getAnnotationsByType(Cache.class);
   // Annotation mutab = m.getDeclaredAnnotation(Mutator.class);//m.getAnnotationsByType(Mutator.class);


      // if(args!= null)
     //   System.out.println("----------args"+ Arrays.stream(args).toList().);
     if (m.isAnnotationPresent(Cache.class)) {
        Ress res = timeRes.get(m);
        long ttimes= m.getDeclaredAnnotation(Cache.class).vltime();

        if(res!=null)
        {
            res.tTime=System.currentTimeMillis() +ttimes;
            if(ttimes ==0 ) res.tTime=0L;
            timeRes.put(m,res);
            return (T)res.val;


        }

        resObj = method.invoke(zobj, args);
        res = new Ress(System.currentTimeMillis() +ttimes, resObj);
        if(ttimes ==0 ) res.tTime=0L;
        timeRes.put(m,res);
        return (T)resObj;

     }
        if (m.isAnnotationPresent(Mutator.class)) {
            stateCh = new StateCH(stateCh,m,args);
            if (cacheMap.containsKey(stateCh)) {
                timeRes=cacheMap.get(stateCh);

            }
            else{
                timeRes= new ConcurrentHashMap<>();
                cacheMap.put(stateCh, timeRes);
            }
            //new CacheCleaner().start();
        }
        return (T)method.invoke(zobj,args);
         /*
         Map<Method, Object> filds = new HashMap<>();
         //if (args == null || args.length == 0) {
         // List dopPol = new ArrayList();
       // if (args == null && mainMap.isEmpty()){
         Field[] fl = m.getClass().getDeclaredFields();

         System.out.println("////"+fl.toString());
        // System.out.println("////"+fl2.toString());
         for (Field f : fl) {
             System.out.println( "--==---------"+f.getName() + " " + f.get(m.getClass()));
             f.setAccessible(true);
             filds.put(m, f.get(m.getClass()));
          //   System.out.println( "--==---------"+f.getName() + " " + f.get(m.getClass()));
         }
       // }
/// вместо стринга используем метод, тогда мы сможем стравнивать метод и пришедшее значение

         System.out.println("filds: "+filds.toString());
         for(Map<Method, Object> mm: mainMap.keySet()) {
            if (mm.entrySet().containsAll(filds.entrySet()))
                System.out.println("URA");
                return mainMap.get(mm);
         }

         res = method.invoke(zobj, args);
         mainMap.put(filds, (T) res);
     }
        System.out.println("mainMap: "+mainMap.toString());
                // dopPol.add(f.get(zobj));

           //  }

//             if (mainMap.equals())
//         System.out.println("pp "+cacheMap.toString());
//         System.out.println("pp "+cacheMap.get(stateCh));
//         if (cacheMap.get(stateCh).isEmpty() )
//         {res = method.invoke(zobj, args);
//             System.out.println("m: "+m);
//             System.out.println("stateCh: "+stateCh);
//             stateCh = new StateCH(stateCh, m, args) ;
//             timeMap.put(1500, (T)res);
//             resMap.put(m,timeMap);
//             cacheMap.put(stateCh, resMap);
//             System.out.println("CM: "+cacheMap.toString());
//         }




        System.out.println(cacheMap.toString());
     /*if( ccache!= null )
        {
            if (cacheMap.get(state)==null )
            {

                if (vlChange)
                {
                    //System.out.println(m.toString());
                    // cacheMap.put(res, (T) res);
                    if (state==null) state = new StateCH();


                    timeMap.put(1500, T)res);
                    resMap.put(m,timeMap);
                    cacheMap.put(state, resMap);


                } (
//            else
//            {
//
//            }

            }



        }






         //   vlChange = true;
        if (mutab!= null) {


        }







        //Найдем поля-----------------------------------
        if (args == null || args.length == 0) {
            List dopPol = new ArrayList();
        Field[] fl = zobj.getClass().getDeclaredFields();
            for (Field f : fl) {
                f.setAccessible(true);

                dopPol.add(f.get(zobj));

            }

          //  args = (Object[]) dopPol.toArray();
        }
        //-------------------------------------
        System.out.println("cacheMap.toString() :"+cacheMap.toString());
        System.out.println("cacheMap.equals(state): "+cacheMap.get(state)==null);
        System.out.println("args: "+Arrays.toString(args));
       // if (cacheMap.get(state)==null )
        {
           // res = method.invoke(zobj, args);
            if (vlChange)
            {
                //System.out.println(m.toString());
               // cacheMap.put(res, (T) res);
              if (state==null) state = new StateCH();
               state = new StateCH(state, m, args) ;

               timeMap.put(1500, T)res);
               resMap.put(m,timeMap);
               cacheMap.put(state, resMap);


            } (
//            else
//            {
//
//            }

        }

        else {


            res = cacheMap.get(state).get(m).get(1500);
            System.out.println("res0: "+res);
        }
       // System.out.println("state: "+state.getMethodMap().toString());
        System.out.println("res1: "+res);
       //res = values;
       // System.out.println(values.getClass().getClassLoader());
        timeMap.clear();
        resMap.clear();

    // return res;
} //

    }


             */
}
