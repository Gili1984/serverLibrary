package com.example.demo.service;


import com.example.demo.models.Publishers;
import com.example.demo.models.Stock;
import com.example.demo.repo.PublishersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublishersService {
    @Autowired
    private PublishersRepo repo;
    public List<Publishers> getAllPublishers() {
        return repo.findAll();
    }

    public void addPublisher(Publishers publisher) {
        repo.save(publisher);
    }
    public void softDeletePublisherIfNoStocks(long publisherId) {
        repo.softDeletePublisherIfNoStocks(publisherId);
    }
    public List<Publishers> getAllActivePublishers() {
        return repo.findByDeletedFalse();
    }
    public List<Stock> findStocksByPublisherAndNotDeleted(long idPublishers) {
        return repo.findStocksByPublisherIdAndNotDeleted(idPublishers);
    }
    public ResponseEntity<Publishers> getPublisherById(long id) {
        Optional<Publishers> publishersOptional = repo.findById(id);

        if (publishersOptional.isPresent()) {
            return ResponseEntity.ok(publishersOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
