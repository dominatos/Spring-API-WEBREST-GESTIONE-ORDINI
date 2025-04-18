package com.example.SpringWEbORDINI.configurations;

import com.example.SpringWEbORDINI.models.User;
import com.example.SpringWEbORDINI.repositories.OrdineREpository;
import com.example.SpringWEbORDINI.repositories.ProductRepository;
import com.example.SpringWEbORDINI.repositories.UserRepository;
import com.example.SpringWEbORDINI.services.ProductService;
import com.example.SpringWEbORDINI.services.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Locale;

@Configuration
public class UserConfiguration {


    @Bean
    @Scope("prototype")
    public User createCustomUser() {
        return new User();
    }

    @Bean
    @Scope("prototype")
    public User createFakeUser() {
        Faker fake = new Faker(new Locale("it-IT"));
        User user = new User();
        user.setFirstname(fake.name().firstName());
        user.setLastname(fake.name().lastName());

        user.setEmail(fake.internet().emailAddress()+fake.number().numberBetween(1,100));
        user.setPassword(fake.internet().password(6,10));
        return user;
    }

}
