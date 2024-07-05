package ru.semenov.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.semenov.log.LogTransformation;
import ru.semenov.model.Login;
import ru.semenov.model.ModelAu;

import ru.semenov.model.User;
import ru.semenov.repo.LoginRepo;
import ru.semenov.repo.UserRepo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
@Component
@Qualifier("db")
@LogTransformation("C:\\temp\\log.txt")
public class InsertStrDB implements Consumer<ModelAu> {
UserRepo userRepo;
LoginRepo loginRepo;

    public InsertStrDB(@Autowired UserRepo userRepo, @Autowired LoginRepo loginRepo) {
        this.userRepo = userRepo;
        this.loginRepo = loginRepo;
    }

    @Override
    public void accept(ModelAu modelAu) {

        List<List<String>> modList = new ArrayList<>(modelAu.getAutomod());
       
        for (List<String> modRecord : modList) {

            List<User> users = userRepo.findByUsername(modRecord.get(0));

            User curUser;

            if (users.isEmpty()) {
                curUser = userRepo.save(new User(modRecord.get(0), modRecord.get(1) ));
            } else curUser = users.get(0);

            loginRepo.save(new Login(Timestamp.valueOf(modRecord.get(2)) , curUser, modRecord.get(3)));
        }
    }



    }


