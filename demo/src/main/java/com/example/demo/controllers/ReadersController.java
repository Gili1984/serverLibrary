package com.example.demo.controllers;


import com.example.demo.models.Readers;
import com.example.demo.repo.ReadersRepo;
import com.example.demo.service.ReadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReadersController {

    @Autowired
    ReadersRepo repo;

    @Autowired
    private ReadersService readersService;

    @PostMapping("/addReader")
    public ResponseEntity<Map<String,String>> addReader(@RequestBody Readers reader) {
        readersService.addReader(reader);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","reader added successfully"));
    }

    @GetMapping("/getAllActiveReaders")
    public List<Readers> getAllActiveReaders() {
        return readersService.getAllActiveReaders();
    }

    @PatchMapping("/softDeleteReaderIfNoOpenBorrows/{readerId}")
    public ResponseEntity<Map<String,String>>  softDeleteReaderIfNoOpenBorrows(@PathVariable long readerId) {
        readersService.softDeleteReaderIfNoOpenBorrows(readerId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Book deleted soft successfully"));
    }

    @GetMapping("/getAllReaders")
    public List<Readers> getAllReaders() {
        return readersService.getAllReaders();
    }

    @GetMapping("/getreaderById/{id}")
    public ResponseEntity<Readers> getReaderById(@PathVariable long id) {
        return readersService.getReaderById(id);
    }
}
