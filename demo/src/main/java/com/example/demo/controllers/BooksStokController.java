package com.example.demo.controllers;



import com.example.demo.models.Category;
import com.example.demo.models.Stock;
import com.example.demo.repo.StockRepo;
import com.example.demo.service.BookStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
//@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class BooksStokController {

    @Autowired
    StockRepo repo;

    @Autowired
    private BookStockService bookStockService;


    @PostMapping("/addBook")
    public ResponseEntity<Map<String,String>> addBook(@RequestBody Stock bookStock) {
        String message = bookStockService.addBook(bookStock);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Book added successfully"));
    }


    @PatchMapping("/incrementAmount/{stockId}")
    public ResponseEntity<Map<String,String>>  incrementAmount(@PathVariable long stockId, @RequestParam long amountToAdd) {
        bookStockService.incrementAmountById(stockId, amountToAdd);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Book update amount successfully"));
    }



    @PatchMapping("/softDeleteIfNoOpenBorrows/{stockId}")
    public ResponseEntity<String> softDeleteIfNoOpenBorrows(@PathVariable long stockId) {
        bookStockService.softDeleteIfNoOpenBorrows(stockId);
        return ResponseEntity.ok("Soft delete operation completed.");
    }


    @PatchMapping("/{id}/publicationDate")
    public String updatePublicationDate(@PathVariable("id") long id, @RequestParam("publicationDate") Date newPublicationDate) {
        return bookStockService.updatePublicationDate(id, newPublicationDate);
    }



    @PatchMapping("/updateTopBooks/{stockId}")
    public ResponseEntity<Void> updateTopBooks(@PathVariable Long stockId) {
        Stock stock = repo.findById(stockId).orElse(null);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }

        bookStockService.updateTopBooks(stock);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/getAllActiveStocks")
    public List<Stock> getAllActiveStocks() {
        return bookStockService.getAllActiveStocks();
    }


    @GetMapping("/getStockByCategoryAndNotDeleted/{category}")
    public List<Stock> getStockByCategoryAndNotDeleted(@PathVariable Category category) {
        return bookStockService.findStockByCategoryAndNotDeleted(category);
    }


    @GetMapping("/getBookById/{id}")
    public Optional<Stock> getBookById(@PathVariable long id) {
        return bookStockService.getBookByIdResponse(id);
    }





    @GetMapping("/getAllBooks")
    public List<Stock> getAllBooks() {
        return bookStockService.getAllBooks();
    }




    @PutMapping("/updateAmountInLibrary/{id}")
    public ResponseEntity<String> updateAmountInLibrary(@PathVariable long id) {
        String message = bookStockService.updateAmountInLibrary(id);
        return ResponseEntity.ok(message);
    }



    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable long id) {
        String message = bookStockService.deleteBookById(id);
        return ResponseEntity.ok(message);
    }

//    @GetMapping("/getBooksByCategory/{category}")
//    public ResponseEntity<List<Stock>> getBooksByCategory(@PathVariable Category category) {
//        List<Stock> books = bookStockService.getBooksByCategory(category);
//        if (books.isEmpty()) {
//            return ResponseEntity.noContent().build(); // Return 204 No Content if the list is empty
//        } else {
//            return ResponseEntity.ok(books); // Return list of books
//        }
//    }

}

