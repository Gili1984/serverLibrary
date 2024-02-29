//package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

//@RestController
//public class CopyController {
//    @Autowired
//    CopyRepo repo;

//    @PostMapping("/addCopy")
//    public  void addCopy(@RequestBody Copy copy){
//        repo.save(copy);
//
//    }


//    @PostMapping("/addCopy")
//    public void addCopy(@RequestBody Copy copy) {
//        // Check if the book with the specified idBook exists
//        Optional<Book> existingBook = booksRepo.findById(copy.getIdBook());
//
//        if (existingBook.isPresent()) {
//            // Book exists, you can proceed to save the copy
//            repo.save(copy);
//        } else {
//            // Book does not exist, handle the situation (throw an exception, return an error response, etc.)
//            throw new BookNotFoundException("Book with id " + copy.getIdBook() + " not found.");
//        }
//    }


//    @GetMapping("/getAllCopy")
//    public List<Copy> getAllCopy() {
//        return repo.findAll();
//    }
//
//    @GetMapping("/getCopyById/{id}")
//    public ResponseEntity<Copy> getCopyById(@PathVariable long id) {
//        try {
//            Optional<Copy> copyOptional = repo.findById(id);
//
//            if (copyOptional.isPresent()) {
//
//                return new ResponseEntity<>(copyOptional.get(), HttpStatus.OK);
//            } else {
//
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}
