package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User findStudentByName(String name);
//
//    @Query("select u from User u where u.id = :id")
//    List<User> customStudentsQuery(int id);
}
