package com.example.SpringWEbORDINI.services;

import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.models.User;
import com.example.SpringWEbORDINI.repositories.UserRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired @Qualifier("createFakeUser")
    ObjectProvider<User> userObjectProvider;

    // User Method
    public User createFakeUser() {
       return userObjectProvider.getObject();
    }

    // CRUD Method

    public User create(User user) {
        userRepository.save(user);
        return user;
    }

    public User update(User user) {
        userRepository.save(user);
        return user;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public long userNum(){
        return userRepository.count();
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    public User getRandomUser() {
        List<User> usersList = getAllUser();
        if (usersList.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(usersList.size());
        return usersList.get(randomIndex);
    }
    public boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }

}
