package com.example.demo.controllers;



import java.security.Timestamp;
import java.time.LocalDate;
import java.util.*;


import com.example.demo.models.Borrow;
import com.example.demo.models.Stock;
import com.example.demo.repo.BorrowingRepo;
import com.example.demo.repo.StockRepo;
import com.example.demo.service.BorrowingService;
import com.example.demo.service.ReadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BorrowingController {
    @Autowired
    private ReadersService readersService;
    @Autowired
    BorrowingRepo repo;
    @Autowired
    StockRepo repoStock;
    @Autowired
    private BorrowingService borrowingService;

    @GetMapping("/getAllBorrowing")
    public List<Borrow> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/getTop5BorrowedBooks")
    public List<Stock> getTop5BorrowedBooks() {
        return borrowingService.findTop5BorrowedBooks();
    }

    @GetMapping("/getBorrowingsWithNullReturnDateForBook/{bookId}")
    public List<Borrow> getBorrowingsWithNullReturnDateForBook(@PathVariable long bookId) {
        return borrowingService.getBorrowingsWithNullReturnDateForBook(bookId);
    }





    @PatchMapping("/return/{id}")
    public ResponseEntity<Map<String,String>>  updateBorrowingDateReturn(@PathVariable long id) {
        LocalDate dateReturn = LocalDate.now(); // Get current LocalDate
        borrowingService.updateBorrowingDateReturn(id, dateReturn);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Borrowing dateReturn updated successfully"));
    }


    @GetMapping("/getBorrowingsWithNullReturnDate")
    public List<Borrow> getBorrowingsWithNullReturnDate() {
        return borrowingService.getBorrowingsWithNullReturnDate();
    }

    @GetMapping("/byIdBook/{idBook}")
    public ResponseEntity<?> getBorrowingsByBookId(@PathVariable long idBook) {
        return borrowingService.getBorrowingsByBookId(idBook);
    }
    @PostMapping("/addBorrowing")
    public ResponseEntity<Object> addBorrowing(@RequestBody Borrow borrowing) {
        return borrowingService.addBorrowing(borrowing);
    }
    @GetMapping("/getBorrowingsWithNullReturnDateByIdReader/{idReader}")
    public List<Borrow> getBorrowingsWithNullReturnDateByIdReader(@PathVariable long idReader) {
        return borrowingService.getBorrowingsWithNullReturnDateByIdReader(idReader);
    }


    @GetMapping("/getBorrowingOptionalById/{id}")
    public ResponseEntity<Borrow> getCopyById(@PathVariable long id) {
        try {
            Optional<Borrow> borrowingOptional = repo.findById(id);

            if (borrowingOptional.isPresent()) {

                return new ResponseEntity<>(borrowingOptional.get(), HttpStatus.OK);
            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PutMapping("/updateBorrowingDateReturn/{id}")
//    public ResponseEntity<Map<String, String>> updateBorrowingDateReturn(@PathVariable long id, @RequestBody Map<String, String> dateUpdate) {
//        return borrowingService.updateBorrowingDateReturn(id, dateUpdate);
//    }


    @GetMapping("/getBorrowingsByReaderId/{readerId}")
    public List<Borrow> getBorrowingsByReaderId(@PathVariable long readerId) {
        return borrowingService.getBorrowingsByReaderId(readerId);
    }


}
