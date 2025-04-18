package com.example.SpringWEbORDINI.controllers;

import com.example.SpringWEbORDINI.models.Ordine;
import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.services.OrdineService;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/ordini")
public class OrdineController {
    @Autowired
    OrdineService ordineService;

    @GetMapping
    public @ResponseBody List<Ordine> getOrdini() {
        try {
            List<Ordine> ordini = ordineService.getAllOrdini();
            return ordini;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOOrdineById(@PathVariable Long id) {

        try{
            return new ResponseEntity<Ordine>(ordineService.getOrdineById(id), HttpStatus.OK);
        } catch (NoSuchElementException | EntityNotFoundException e){
            return new ResponseEntity<String>(e.getMessage()+" - ID Ordine Sbagliato!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrdine(@RequestBody Ordine ordine) {

        try{
            return new ResponseEntity<Ordine>(ordineService.saveOrdine(ordine), HttpStatus.CREATED);
        } catch (NoSuchElementException | EntityNotFoundException | DataIntegrityViolationException e){
            return new ResponseEntity<String>(e.getMessage()+" - Ordine inserimento ERROR!", HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePutOrdine(@PathVariable Long id, @RequestBody Ordine ordine) {


        try {
            ordineService.getOrdineById(id).getId();
            if(Objects.equals(id, ordine.getId())){

                return new ResponseEntity<Ordine>(ordineService.modifyOrdine(ordine), HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<String>("Ordine update ERROR!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage()+" Ordine update ERROR!", HttpStatus.NOT_FOUND);
        }


    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteOrdine(@PathVariable Long id) {
        try {
            if (ordineService.existsOrdineById(id)) {
                ordineService.deleteOrdineById(id);
                return new ResponseEntity<String>("Ordine deleted successfully",HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Ordine not found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage()+" -Ordine DELETE ERROR!", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<?> patchUpdateOrdine(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Ordine ordine = ordineService.getOrdineById(id);
            if (ordine == null) {
                return new ResponseEntity<>("Ordine not found", HttpStatus.NOT_FOUND);
            } else {
                updates.forEach((k, v) -> {
                    Field field = ReflectionUtils.findField(Ordine.class, k);
                    if (field != null) {
                        field.setAccessible(true);
                        try {
                            Object convertedValue;

                            if (field.getType().isEnum()) {
                                convertedValue = Enum.valueOf((Class<Enum>) field.getType(), v.toString());
                            } else {
                                convertedValue = convertValue(field.getType(), v); // Прочие типы
                            }
                            ReflectionUtils.setField(field, ordine, convertedValue);
                        } catch (IllegalArgumentException ex) {
                            throw new IllegalArgumentException("Field '" + k + "' has a type mismatch: " + ex.getMessage());
                        }
                    } else {
                        throw new IllegalArgumentException("Field '" + k + "' does not exist");
                    }
                });

                return new ResponseEntity<>(ordineService.modifyOrdine(ordine), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() + " - Ordine Update ERROR!", HttpStatus.BAD_REQUEST);
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
