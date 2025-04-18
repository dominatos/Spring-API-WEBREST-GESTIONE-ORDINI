package com.example.SpringWEbORDINI.runners;

import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.models.User;
import com.example.SpringWEbORDINI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("UserRunner...");
        //generareNumUsers(100);


        //User user=userService.getRandomUser();

        //System.out.println(user);
    }

    public void generareNumUsers(int n) {
        for (int i = 0; i < n; i++) {
            userService.create(userService.createFakeUser());
        }
        System.out.println("Database inizializzato con " + n + " utenti!!!");
    }

}

