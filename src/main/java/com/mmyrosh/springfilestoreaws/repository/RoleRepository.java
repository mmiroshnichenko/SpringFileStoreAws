package com.mmyrosh.springfilestoreaws.repository;

import com.mmyrosh.springfilestoreaws.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
