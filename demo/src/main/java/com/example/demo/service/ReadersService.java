package com.example.demo.service;


import com.example.demo.models.Borrow;
import com.example.demo.models.Readers;
import com.example.demo.repo.BorrowingRepo;
import com.example.demo.repo.ReadersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadersService {

    @Autowired
    private ReadersRepo repo;
    @Autowired
    private BorrowingRepo borroworepo;
    public void addReader(Readers reader) {
        repo.save(reader);
    }

    public List<Readers> getAllReaders() {
        return repo.findAll();
    }

    public ResponseEntity<Readers> getReaderById(long id) {
        Optional<Readers> readersOptional = repo.findById(id);

        if (readersOptional.isPresent()) {
            return ResponseEntity.ok(readersOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public List<Readers> getAllActiveReaders() {
        return repo.findByDeletedFalse();
    }
    public void softDeleteReaderIfNoOpenBorrows(long readerId) {
        Optional<Readers> readerOptional = repo.findById(readerId);
        if (readerOptional.isPresent()) {
            Readers reader = readerOptional.get();
            List<Borrow> openBorrows = borroworepo.findByIdReader_IdReaderAndDateReturnIsNull(reader.getIdReader());
            repo.softDeleteReaderIfNoOpenBorrows(reader, openBorrows);
        }
    }
}
