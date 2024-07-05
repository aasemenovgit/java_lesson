package ru.semenov.test;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.semenov.log.LogTransformation;
import ru.semenov.model.ModelAu;


import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Order(1)
@LogTransformation("c:\\temp\\Log.txt")
public class CorrBigLeter implements UnaryOperator<ModelAu> {
    @Override
    public ModelAu apply(ModelAu modelAu) {
        List<List<String>> modLists = new ArrayList<>(modelAu.getAutomod());
        List<List<String>> res = new ArrayList<>();
      //  System.out.println("--------------///1-------"+modLists.toString());
for(List<String> ls:modLists){
    if (ls.size() < 2) {
        res.add(ls);
        continue;
    }
   // System.out.println("--------------///1res-------"+res.toString());
    List<String> modRecord = new ArrayList<>();
    for (int i = 0; i < ls.size(); i++) {
        if (i > 0 && i < 2)

        { modRecord.add(correct(ls.get(i)));}
        else
            modRecord.add(ls.get(i));
    }
    res.add(modRecord);
}

        modelAu.setAutomod(res);
        return modelAu;
    }
    public String correct(String text) {
        StringBuilder sbr = new StringBuilder(text);
        if(Character.isAlphabetic((text.codePointAt(0))))
            sbr.setCharAt(0, Character.toUpperCase(text.charAt(0)));
        for(int i =1; i<text.length();i++)
            if(Character.isAlphabetic((text.charAt(i)))&& Character.isSpaceChar(text.charAt(i-1)))
                sbr.setCharAt(i, Character.toUpperCase(text.charAt(i)));
        return sbr.toString();
    }


}





