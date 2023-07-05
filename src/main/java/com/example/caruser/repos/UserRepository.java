package com.example.caruser.repos;


import com.example.caruser.domain.CarUser;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<CarUser, Long> {

    List<CarUser> findByFIO(String fio);
}
