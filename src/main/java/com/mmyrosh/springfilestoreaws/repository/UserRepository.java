package com.mmyrosh.springfilestoreaws.repository;

import com.mmyrosh.springfilestoreaws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
