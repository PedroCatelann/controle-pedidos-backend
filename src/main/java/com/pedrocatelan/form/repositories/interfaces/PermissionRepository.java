package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByDescription(String description);
}
