package com.glauber.santanderdevbootcamp.domain.repository;

import com.glauber.santanderdevbootcamp.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccountNumber(String number);

    boolean existsByCardNumber(String number);
}
