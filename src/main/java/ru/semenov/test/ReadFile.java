package ru.semenov.test;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.semenov.log.LogTransformation;
import ru.semenov.model.ModelAu;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

@Component
@Qualifier("file")
@LogTransformation("c:\\temp\\log.txt")
public class ReadFile implements Supplier<ModelAu> {
   private String pathinput;
    public ReadFile(@Value("${spring.application.pathinput}") String path){this.pathinput = path;}

    public String getPathinput(){return pathinput;}
    ;

        @Override
    public ModelAu get() {
        List<List< String>> ret = new ArrayList<>();
        File fPath = new File(getPathinput());
        //    File fPath = new File(pathinput);
            System.out.println("--------------"+fPath.toString());
        for (File file: fPath.listFiles()) {
            System.out.println("-------------1:-");
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ress =scanner.nextLine();
                System.out.println("-------------ress:-"+ress);
                ret.add(Arrays.stream(ress.split(",")).toList()) ;

            }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return new ModelAu(ret);
    }
}
