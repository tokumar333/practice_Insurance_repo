package com.insurencetech.life.repo;

import com.insurencetech.life.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserLogin,Integer> {

    public UserLogin findByEmail(String email);

}
