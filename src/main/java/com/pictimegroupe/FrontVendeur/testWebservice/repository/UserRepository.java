package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<User,String> {
    List<User> findByLogin(String login);
}
