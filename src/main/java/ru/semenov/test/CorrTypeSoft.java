package ru.semenov.test;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.semenov.log.LogTransformation;
import ru.semenov.model.ModelAu;


import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Order(2)
@LogTransformation("c:\\temp\\Log.txt")
public class CorrTypeSoft implements UnaryOperator<ModelAu> {

    @Override
    public ModelAu apply(ModelAu modelAu) {


    List<List<String>> authLists = new ArrayList<>(modelAu.getAutomod());
    List<List<String>> res = new ArrayList<>();
    List<String> templateApp = List.of("web", "mobile");

        for (List<String> lst : authLists) {


        if (templateApp.contains( lst.get(3).trim())) {

            res.add(lst);
            continue;
        }
        List<String> authRecord = new ArrayList<>(lst.subList(0, 3));

//
//
        authRecord.add("other:" + lst.get(3));
        res.add(authRecord);
    }

        modelAu.setAutomod(res);;
        return modelAu;
}








public String correct(String text) {
        if (text == "web"||text =="mobile")
        return text;
        return "other:" +text;
    }
}
