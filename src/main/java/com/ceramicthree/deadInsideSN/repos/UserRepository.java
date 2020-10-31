package com.ceramicthree.deadInsideSN.repos;

import com.ceramicthree.deadInsideSN.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
}
