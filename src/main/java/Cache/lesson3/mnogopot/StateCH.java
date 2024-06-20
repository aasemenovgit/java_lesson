package Cache.lesson3.mnogopot;

import lombok.EqualsAndHashCode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public class StateCH {

    private final   Map<Method, List<Object>> methodMap= new ConcurrentHashMap<>();

   public StateCH() {    }

    public StateCH(StateCH st, Method meth , Object[] args) {

        methodMap.putAll(st.methodMap);
        methodMap.put(meth, Arrays.asList(args));//List.of(args));//Arrays.asList(args));
    }


}
