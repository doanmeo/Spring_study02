package com.hocSpring.bai_01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hocSpring.bai_01.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // custom query methods if needed
}
