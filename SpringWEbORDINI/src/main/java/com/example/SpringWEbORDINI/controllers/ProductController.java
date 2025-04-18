package com.example.SpringWEbORDINI.controllers;

import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;
//    Implementare il controller ProductController con i seguenti endpoint:
//    GET /products → restituisce tutti i prodotti.
//    GET /products/{id} → restituisce un prodotto specifico.
//    POST /products → aggiunge un nuovo prodotto.
//    PUT /products/{id} → aggiorna un prodotto.
//            PATCH /products/{id} → aggiorna un prodotto.
//            DELETE /products/{id} → elimina un prodotto.


    @GetMapping
    public @ResponseBody List<Product> getProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return products;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {

        try{
            return new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
        } catch (NoSuchElementException | EntityNotFoundException e){
            return new ResponseEntity<String>(e.getMessage()+" - ID Prodotto Sbagliato!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {

        try{
            return new ResponseEntity<Product>(productService.saveProdotto(product), HttpStatus.CREATED);
        } catch (NoSuchElementException | EntityNotFoundException | DataIntegrityViolationException e){
            return new ResponseEntity<String>(e.getMessage()+" - Prodotto inserimento ERROR!", HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePutProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productService.existsProductById(id)) {
            return new ResponseEntity<String>("Product con ID " + id + " non e trovato", HttpStatus.NOT_FOUND);
        }
        if (!id.equals(product.getId())) {
            return new ResponseEntity<String>(" ID nella richiesta e nei dati non corrispondono", HttpStatus.BAD_REQUEST);
        }

        try {


                return new ResponseEntity<Product>(productService.modifyProdotto(product), HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage()+" Prodotto update ERROR!", HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            if (productService.existsProductById(id)) {
                productService.deleteProdottoById(id);
                return new ResponseEntity<String>("Product deleted successfully",HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Product not found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage()+" - DELETE ERROR!", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<?> patchUpdateProduct(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            // Recupera il prodotto dalla base di dati usando il suo ID
            Product product = productService.getProductById(id);

            // Verifica se il prodotto esiste; se non esiste, restituisce un errore
            if (product == null) {
                return new ResponseEntity<>("Prodotto non trovato", HttpStatus.NOT_FOUND);
            } else {
                // Itera sulla mappa di aggiornamenti e modifica i campi del prodotto
                updates.forEach((k, v) -> {
                    // Trova il campo corrispondente nella classe Product usando Reflection
                    Field field = ReflectionUtils.findField(Product.class, k);
                    if (field != null) {
                        field.setAccessible(true); // Rende accessibile il campo privato

                        try {
                            // Converte il valore fornito al tipo appropriato del campo
                            Object convertedValue = convertValue(field.getType(), v);
                            ReflectionUtils.setField(field, product, convertedValue); // Aggiorna il valore del campo
                        } catch (IllegalArgumentException ex) {
                            // Gestisce errori di tipo, ad esempio quando il tipo del valore non corrisponde al tipo del campo
                            throw new IllegalArgumentException("Il campo '" + k + "' ha un tipo incompatibile");
                        }
                    } else {
                        // Se il campo non esiste nella classe Product, solleva un'eccezione
                        throw new IllegalArgumentException("Il campo '" + k + "' non esiste");
                    }
                });

                // Salva il prodotto modificato nella base di dati e restituisce la risposta
                return new ResponseEntity<>(productService.modifyProdotto(product), HttpStatus.OK);
            }
        } catch (Exception e) {
            // Restituisce una risposta di errore generico in caso di eccezioni
            return new ResponseEntity<>(e.getMessage() + " - Errore di aggiornamento!", HttpStatus.BAD_REQUEST);
        }
    }
    private Object convertValue(Class<?> fieldType, Object value) {
        // Controlla se il campo è di tipo Long e il valore è Integer, quindi converte il valore
        if (fieldType.equals(Long.class) && value instanceof Integer) {
            return Long.valueOf((Integer) value);
        }
        // Controlla se il campo è di tipo Integer e il valore è di tipo Number, quindi converte il valore
        else if (fieldType.equals(Integer.class) && value instanceof Number) {
            return ((Number) value).intValue();
        }
        // Controlla se il campo è di tipo Double e il valore è di tipo Number, quindi converte il valore
        else if (fieldType.equals(Double.class) && value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        // Controlla se il campo è di tipo BigDecimal e il valore è di tipo Number, quindi converte il valore
        else if (fieldType.equals(BigDecimal.class) && value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        // Controlla se il campo è di tipo String, quindi converte il valore in una stringa
        else if (fieldType.equals(String.class)) {
            return value.toString();
        }
        // Restituisce il valore senza modifiche se non è richiesto alcun tipo di conversione
        return value;
    }
}

