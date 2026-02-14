package com.lab.SoporteApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.SoporteApp.model.users;
import com.lab.SoporteApp.model.enums.Erol;

public interface usersRepository extends JpaRepository<users, Long> {    

    Optional<users> findByEmail(String email);

    List<users> findByRoluser(Erol roluser);

    
}
