package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {

    @Query("SELECT u FROM User u WHERE u.username =: username")
    User findByUsername(@Param("userName") String userName);
}
