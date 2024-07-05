package ru.semenov.test;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.semenov.log.LogTransformation;
import ru.semenov.model.ModelAu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Component
@Qualifier("file")
@LogTransformation("C:\\temp\\log.txt")
public class WriteFile implements Consumer<ModelAu> {
    private String pathoutput;
    public WriteFile(@Value("${spring.application.pathoutput}") String path ){this.pathoutput =path;}

    @Override
    public void accept(ModelAu modelAu) {

        FileWriter fileWriter;
        try{fileWriter = new FileWriter(pathoutput,true);
        for(List<String> str :modelAu.getAutomod()){
            fileWriter.write(String.join(";", str) + "\n");
            fileWriter.close();}
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
