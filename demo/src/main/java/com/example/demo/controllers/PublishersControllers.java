package com.example.demo.controllers;



import com.example.demo.models.Publishers;
import com.example.demo.models.Stock;
import com.example.demo.repo.PublishersRepo;
import com.example.demo.service.PublishersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
public class PublishersControllers {

    @Autowired
    PublishersRepo repo;
    @Autowired
    private PublishersService publishersService;


    @GetMapping("/getAllPublishers")
    public List<Publishers> getAllPublishers() {
        return publishersService.getAllPublishers();
    }

    @GetMapping("/getAllActivePublishers")
    public List<Publishers> getAllActivePublishers() {
        return publishersService.getAllActivePublishers();
    }


    @GetMapping("/findStocksByPublisherAndNotDeleted/{publisherId}")
    public List<Stock> findStocksByPublisherAndNotDeleted(@PathVariable long publisherId) {
        return publishersService.findStocksByPublisherAndNotDeleted(publisherId);
    }

    @PostMapping("/addPublisher")
    public  ResponseEntity<Map<String,String>> addPublisher(@RequestBody Publishers publisher) {
        publishersService.addPublisher(publisher);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Publisher added successfully"));
    }


    @PatchMapping("/softDeletePublisherIfNoStocks/{poblisherId}")
    public ResponseEntity<Map<String,String>>  softDeletePublisherIfNoStocks(@PathVariable long poblisherId) {
        publishersService.softDeletePublisherIfNoStocks(poblisherId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","publisher deleted soft successfully"));
    }
    @GetMapping("/getPublisherById/{id}")
    public ResponseEntity<Publishers> getPublisherById(@PathVariable long id) {
        return publishersService.getPublisherById(id);
    }
}
