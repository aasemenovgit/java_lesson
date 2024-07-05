package ru.semenov;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.semenov.streaming.Streamable;


@SpringBootApplication
public class StartAPP implements ApplicationRunner {
    final Streamable streamable;

    public StartAPP(Streamable streamable) {
        this.streamable = streamable;
    }

    public static void main(String[] args) {
        SpringApplication.run(StartAPP.class,args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        streamable.run();
    }
}
