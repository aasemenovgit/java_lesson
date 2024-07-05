package ru.semenov.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.semenov.log.LogTransformation;
import ru.semenov.model.ModelAu;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@Component
@Order(3)
@LogTransformation("c:\\temp\\Log.txt")
public class CorrDateValid implements UnaryOperator<ModelAu> {
    Consumer<ModelAu> writer;
    public CorrDateValid(@Autowired
                         @Qualifier("file") Consumer<ModelAu> writer) {
        this.writer = writer;
    }
    @Override
    public ModelAu apply(ModelAu modelAu) {

        List<List<String>> modLists = modelAu.getAutomod();

        List<List<String>> res = new ArrayList<>();
        List<List<String>> resReject = new ArrayList<>();

        for (int i = 0; i < modLists.size(); i++) {
//            if (modLists.get(i).size() < 6
//                    || modLists.get(i).get(4).isEmpty()
//                    || modLists.get(i).get(5).isEmpty()
//            )
            if (modLists.get(i).size() < 4
                    || modLists.get(i).get(2).isEmpty()
                    || modLists.get(i).get(2).isBlank()

            )
            {
                resReject.add(modLists.get(i));
            }
            else res.add(modLists.get(i));
        }
        System.out.println("--------------дата///-------"+res.toString());
        modelAu.setAutomod(res);

         writer.accept(new ModelAu(resReject));

        return modelAu;
    }

}
