package ru.semenov.dt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.semenov.dt.entity.Agreement;

import java.util.List;

public interface AgreementRepo extends JpaRepository<Agreement, Integer> {
    @Query(value = "select product_id from agreement where number =?1 ", nativeQuery = true)
    List<String> findByParam(String number);

    @Query(value = "select id from agreement where product_id =?1 ", nativeQuery = true)
    List<String> findAllByParam(Integer product_id);
}