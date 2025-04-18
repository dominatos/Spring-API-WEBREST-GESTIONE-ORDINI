package com.example.SpringWEbORDINI.services;

import com.example.SpringWEbORDINI.models.Ordine;
import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.repositories.OrdineREpository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.example.SpringWEbORDINI.models.OrderStatus.PENDING;

@Service
public class OrdineService {
    @Autowired
    OrdineREpository ordineRepository;
    @Autowired @Qualifier("createCustomOrder")
    ObjectProvider<Ordine> OrdineObjectProvider;
    public Ordine saveOrdine(Ordine ordine) {

        List<Product> products = ordine.getProducts();
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Lista di prodotti é vuoto!");
        }
        if (products.stream().anyMatch(Objects::isNull)) {

            throw new IllegalArgumentException("Ci sono NULL nella lista");

        }



        Double totale=ordine.getProducts().stream().mapToDouble(p->p.getPrice().doubleValue()).sum();
            ordine.setTotalPrice(BigDecimal.valueOf(totale));


        ordine.setStatus(PENDING);
        ordineRepository.save(ordine);
        return ordine;
    }
    public Ordine createCustomOrdine() {
        return OrdineObjectProvider.getObject();
    }
    public Ordine modifyOrdine(Ordine ordine) {
        List<Product> products = ordine.getProducts();
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Lista di prodotti é vuoto!");
        }
        if (products.stream().anyMatch(Objects::isNull)) {

            throw new IllegalArgumentException("Ci sono NULL nella lista");

        }



        Double totale=ordine.getProducts().stream().mapToDouble(p->p.getPrice().doubleValue()).sum();
        ordine.setTotalPrice(BigDecimal.valueOf(totale));
        ordineRepository.save(ordine);
        return ordine;
    }
    public void deleteOrdine(Ordine ordine) {
        ordineRepository.delete(ordine);
    }
    public void deleteOrdineById(Long id) {
        ordineRepository.deleteById(id);
    }
    public List<Ordine> getAllOrdine() {
        return ordineRepository.findAll();
    }
    public Ordine getOrdineById(Long id) {
        return ordineRepository.findById(id).get();
    }
    public boolean existsOrdineById(Long id) {
        return ordineRepository.existsById(id);
    }
    public long ordinerNum(){
        return ordineRepository.count();
    }
    public List<Ordine> getAllOrdini() {
        return ordineRepository.findAll();
    }
}
