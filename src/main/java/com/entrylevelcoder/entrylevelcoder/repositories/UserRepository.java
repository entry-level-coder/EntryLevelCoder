package com.entrylevelcoder.entrylevelcoder.repositories;

import com.entrylevelcoder.entrylevelcoder.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    static Optional<User> findById(int userId) {
//
//    }
//    static boolean existsById(int userId) {
//
//    }
//
    User  deleteById(long id);
//
    User findByUsername(String username);

    User findById(long id);


}
