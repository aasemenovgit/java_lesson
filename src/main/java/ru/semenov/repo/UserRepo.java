package ru.semenov.repo;



import org.springframework.data.repository.CrudRepository;
import ru.semenov.model.User;


import java.util.List;

public interface UserRepo extends CrudRepository<User,Integer> {
    List<User> findByUsername(String username);
}
