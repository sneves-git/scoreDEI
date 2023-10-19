package com.example.demo;

import com.example.data.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u from User u ")
    public List<User> findAllUsers();

    @Query("select u from User u where u.username = ?1 and u.password = ?2")
    public Optional<User> findUser(String user, String pass);

    @Modifying
    @Query("delete from User u where u.username = ?1")
    public void deleteUser(String username);

    @Query("select u from User u where u.id = ?1")
    public User getUserById(int id);

    @Query("select u from User u where u.username = ?1")
    public User getUserByUsername(String username);

    @Query("select u from User u where u.username = ?1 ")
    public User checkUsername(String username);

    @Query("select u from User u where u.email = ?1")
    public User checkEmail(String email);

    @Query("select u from User u where u.phone = ?1")
    public User checkPhone(String phone);

}
