package com.example.SpringWEbORDINI.repositories;

import com.example.SpringWEbORDINI.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
