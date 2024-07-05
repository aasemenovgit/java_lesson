package ru.semenov.repo;


import org.springframework.data.repository.CrudRepository;
import ru.semenov.model.Login;


import java.util.List;

public interface LoginRepo extends CrudRepository<Login,Integer> {
    List<Login> findByApplication(String application);
}
