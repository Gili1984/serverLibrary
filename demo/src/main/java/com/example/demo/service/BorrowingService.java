package com.example.demo.service;

import com.example.demo.models.Borrow;
import com.example.demo.models.Stock;
import com.example.demo.repo.BorrowingRepo;
import com.example.demo.repo.StockRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static java.security.Timestamp.*;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepo borrowingRepo;

    @Autowired
    private StockRepo stockRepo;


    public List<Borrow> getAllBorrowings() {
        return borrowingRepo.findAll();
    }
    public List<Borrow> getBorrowingsWithNullReturnDate() {
        List<Borrow> borrowings = borrowingRepo.findByDateReturnIsNull();
        if (borrowings != null && !borrowings.isEmpty()) {
            return borrowings;
        } else {
            return Collections.emptyList(); // Or you can return null or throw an exception based on your requirements
        }
    }
    // Method to get borrowings with null return date for a specific book ID
    public List<Borrow> getBorrowingsWithNullReturnDateForBook(long bookId) {
        return borrowingRepo.findByDateReturnIsNullAndIdBook_IdBook(bookId);
    }
    // Method to retrieve borrowings with null return date based on idReader
    public List<Borrow> getBorrowingsWithNullReturnDateByIdReader(Long idReader) {
        if (Objects.requireNonNull(idReader) != null) {
            return borrowingRepo.findByIdReader_IdReaderAndDateReturnIsNull(idReader);
        } else {
            // Handle the case where idReader is null, return an empty list or handle it as needed
            return Collections.emptyList();
        }
    }
    public ResponseEntity<?> getBorrowingsByBookId(long idBook) {
        List<Borrow> borrowingList = borrowingRepo.findByIdBook_IdBook(idBook);
        if (!borrowingList.isEmpty()) {
            return ResponseEntity.ok(borrowingList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public List<Stock> findTop5BorrowedBooks() {
        return borrowingRepo.findTop5BorrowedBooks();
    }
//    public void updateReturnDateToTodayForNullReturnDate() {
//        borrowingRepo.updateReturnDateToTodayForNullReturnDate();
//    }
    public ResponseEntity<Object> addBorrowing(Borrow borrowing) {
        Optional<Stock> bookOptional = stockRepo.findById(borrowing.getIdBook().getIdBook());

        if (bookOptional.isPresent()) {
            if (bookOptional.get().getAmountInLibrary() > 0) {
                // Book is available, update stock and save borrowing record
                bookOptional.get().setAmountInLibrary(bookOptional.get().getAmountInLibrary() - 1);
                borrowing.setIdBorrowing((long) (Math.random() * 900000L) + 100000L);
                borrowing.setDateBorrowing(new Date());
                stockRepo.save(bookOptional.get());
                borrowingRepo.save(borrowing);

                // Return a JSON response with success message
                Map<String, String> response = new HashMap<>();
                response.put("message", "Borrowing added successfully.");
                return ResponseEntity.ok(response);
            } else {
                // Book is not available in stock
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not available in stock.");
            }
        } else {
            // Book not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not found.");
        }
    }



    @Transactional
    public void updateBorrowingDateReturn(long id, LocalDate dateReturn) {
        borrowingRepo.updateDateReturn(id, dateReturn);
    }

//    @Transactional
//    public ResponseEntity<Map<String, String>> updateBorrowingDateReturn(long id, Map<String, String> dateUpdate) {
//        try {
//            Optional<Borrow> existingBorrow = borrowingRepo.findById(id);
//
//            if (existingBorrow.isPresent()) {
//                Borrow existingBorrowing = existingBorrow.get();
//
//                // Update the dateReturn property
//                String dateString = dateUpdate.get("dateReturn");
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Date updatedDateReturn = dateFormat.parse(dateString);
//
//                existingBorrowing.setDateReturn(updatedDateReturn);
//
//                // Save the updated borrowing
//                borrowingRepo.save(existingBorrowing);
//
//                // Return a JSON response
//                Map<String, String> response = new HashMap<>();
//                response.put("message", "Borrowing dateReturn updated successfully");
//                return ResponseEntity.ok(response);
//            } else {
//                // Borrowing not found
//                Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("error", "Borrowing not found with id: " + id);
//                return ResponseEntity.notFound().build();
//
//            }
//        } catch (ParseException e) {
//            // Log the exception for debugging
//            e.printStackTrace();
//            // Return an error response with a map
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "An error occurred while updating dateReturn for borrowing with id: " + id);
//            return ResponseEntity.internalServerError().body(errorResponse);
//        }
//    }
    // Define method to get borrowings by reader ID
    public List<Borrow> getBorrowingsByReaderId(long readerId) {
        return borrowingRepo.findByIdReader_IdReader(readerId);
    }
}
