package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :userName")
    User findByUsername(@Param("userName") String userName);
}
