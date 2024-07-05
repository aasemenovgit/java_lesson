package ru.semenov.streaming;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.semenov.model.ModelAu;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;


@Component
//@LogTransformation("C:\\temp\\log.txt")
public class Streaming implements Streamable{
    @Autowired
    Supplier<ModelAu> driader;

    @Autowired
    List<UnaryOperator<ModelAu>> oper;

    @Autowired  @Qualifier("db")
    Consumer<ModelAu> writer;

    @Override
    public void run() {
        ModelAu modelAu = driader.get();
        oper.stream().forEach(x->x.apply(modelAu));
        writer.accept(modelAu);
    }
}
