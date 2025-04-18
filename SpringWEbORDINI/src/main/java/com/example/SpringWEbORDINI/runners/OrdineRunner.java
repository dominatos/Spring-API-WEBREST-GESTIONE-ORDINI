package com.example.SpringWEbORDINI.runners;

import com.example.SpringWEbORDINI.models.Ordine;
import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.models.User;
import com.example.SpringWEbORDINI.services.OrdineService;
import com.example.SpringWEbORDINI.services.ProductService;
import com.example.SpringWEbORDINI.services.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.example.SpringWEbORDINI.models.OrderStatus.PENDING;

@Component
@Order(3)
public class OrdineRunner implements CommandLineRunner {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OrdineService ordineService;
    @Autowired
    private UserRunner userRunner;
    @Autowired
    private ProductRunner productRunner;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("OrdineRunner");
        //generareNumOrdini(10);
    }
    public void generareNumOrdini(int n) {
        for (int i = 0; i < n; i++) {

            Faker faker=new Faker();
            List<Product> productList=productService.getRandomProducts(faker.number().numberBetween(1, 5));

            User user=userService.getRandomUser();
            //System.out.println(product);
            //System.out.println(user);
            if(productList!=null && user!=null)
            {
                Ordine ordine=ordineService.createCustomOrdine();
                ordine.setUser(user);


                ordine.setProducts(productList);




                ordineService.saveOrdine(ordine);
                List<Ordine> ordini=List.of(ordine);
                user.setOrdini(ordini);
                userService.update(user);
                System.out.println("Ordine: "+ordine.getId()+" "+ordine.getStatus()+" "+ordine.getTotalPrice()+" "+ordine.getProducts()+" "+ordine.getUser().getFirstname()+" "+ordine.getUser().getLastname()+" "+ordine.getUser().getEmail()


                        );
            }
            else if(productList==null){
                System.out.println("PRODOTTI NON ESISTe");


                return;
            }
            else if(user==null){
                System.out.println("USER NON ESISTe");


                return;
            }



        }
        System.out.println("Database inizializzato con " + n + " ORDINI!!!") ;
    }
}
