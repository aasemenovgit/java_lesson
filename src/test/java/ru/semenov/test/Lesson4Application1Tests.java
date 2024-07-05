package ru.semenov.test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.semenov.model.Login;
import ru.semenov.model.ModelAu;
import ru.semenov.model.User;
import ru.semenov.repo.LoginRepo;
import ru.semenov.repo.UserRepo;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Testcontainers
class Lesson4Application1Tests {

        @Autowired
        LoginRepo loginRepo;

        @Autowired
        UserRepo userRepo;

        @Test
        @DisplayName("correct DB writer")
        void testDBWriter() {
            loginRepo.deleteAll();
            userRepo.deleteAll();
            List<List<String>> recs = new ArrayList<>(List.of(
                    Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
            ));

            ModelAu authModel = new ModelAu(recs);
            new InsertStrDB(userRepo, loginRepo).accept(authModel);
            List<User> users = userRepo.findByUsername("SemanovMV");
            System.out.println("users "+users);
            List<Login> logins = loginRepo.findByApplication("web");
            System.out.println("logins "+logins);
            Assertions.assertEquals(users.size(), 1);
            Assertions.assertEquals(users.get(0).getFio(), "Семенов Михаил Владимирович");

            Assertions.assertEquals(logins.size(), 1);
            Assertions.assertEquals(logins.get(0).getApplication(), "web");
        }

        @Test
        @DisplayName("correct app")
        void testCorrTypeSoft() {
            List<List<String>> recs = new ArrayList<>(List.of(
                    Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
                    , Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,jhjklhjk".split(",")).toList()
            ));
            List<List<String>> actualRecs = new ArrayList<>(List.of(
                    Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
                    , Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,other:jhjklhjk".split(",")).toList()
            ));

            ModelAu auModel = new ModelAu(recs);
            new CorrTypeSoft().apply(auModel);

            Assertions.assertLinesMatch(auModel.getAutomod().get(0), actualRecs.get(0));
            Assertions.assertLinesMatch(auModel.getAutomod().get(1), actualRecs.get(1));
        }

        @Test
        @DisplayName("correct  writing to/reading from a file")
        void testcorrDate() {
            List<List<String>> recs = new ArrayList<>(List.of(
                    Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
                    , Arrays.stream("SemanovMV Семенов Михаил Владимирович".split(",")).toList()
            ));

            List<String> actualStr = Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList();
            List<String> actualRejStr = Arrays.stream("SemanovMV Семенов Михаил Владимирович".split(",")).toList();

            for (File fileToDelete : new File("C:\\temp\\").listFiles())
                if (fileToDelete.isFile()) fileToDelete.delete();

            ModelAu auModel = new ModelAu(recs);
            CorrDateValid corrdate = new CorrDateValid(new WriteFile("C:\\temp\\output.txt"));
            corrdate.apply(auModel);

            Assertions.assertEquals(auModel.getAutomod().size(), 1);
            Assertions.assertLinesMatch(auModel.getAutomod().get(0), actualStr);

            ModelAu actualRejAuthModel = new ReadFile("C:\\temp\\").get();

            Assertions.assertEquals(actualRejAuthModel.getAutomod().size(), 1);
            Assertions.assertLinesMatch(actualRejAuthModel.getAutomod().get(0), actualRejStr);
        }

        @Test
        @DisplayName("correct FIO ")
        void testCorrBigLeter() {
            List<List<String>> recs = new ArrayList<>(List.of(
                    Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
                    , Arrays.stream("SemanovMV,семенов Михаил владимирович,2024-01-02 21:20:10,web".split(",")).toList()
            ));
            List<List<String>> actualRecs = new ArrayList<>(List.of(
                    Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
                    , Arrays.stream("SemanovMV,Семенов Михаил Владимирович,2024-01-02 21:20:10,web".split(",")).toList()
            ));

            ModelAu auModel = new ModelAu(recs);
            new CorrBigLeter().apply(auModel);

            Assertions.assertLinesMatch(auModel.getAutomod().get(0), actualRecs.get(0));
            Assertions.assertLinesMatch(auModel.getAutomod().get(1), actualRecs.get(1));
        }



}
