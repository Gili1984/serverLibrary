package com.example.demo.service;


import com.example.demo.models.Borrow;
import com.example.demo.models.Category;
import com.example.demo.models.Stock;
import com.example.demo.repo.BorrowingRepo;
import com.example.demo.repo.StockRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class BookStockService {

    @Autowired
    private StockRepo repo;
    @Autowired
    private BorrowingRepo borroworepo;
    public String addBook(Stock bookStock) {
        Optional<Stock> existingBook = repo.findById(bookStock.getIdBook());

        if (existingBook.isPresent()) {
            // Book already exists, update the amount
            Stock existingStock = existingBook.get();
            existingStock.setAmount(existingStock.getAmount() + bookStock.getAmount());
            existingStock.setAmountInLibrary(existingStock.getAmountInLibrary() + bookStock.getAmountInLibrary());
            repo.save(existingStock);
            return "Book updated successfully";
        } else {
            // Book doesn't exist, save a new entry
            repo.save(bookStock);
            return "New book added successfully";
        }
    }


    public void updateTopBooks(Stock stock) {
        List<Stock> topBooks = borroworepo.findTop5BorrowedBooks();

        // Check if the provided stock is in the top books list
        for (Stock book : topBooks) {
            if (Long.valueOf(book.getIdBook()).equals(stock.getIdBook())) {
                stock.setIsStar(true);
                break;
            }
        }

        repo.save(stock); // Save the changes for the provided book
    }




    public List<Stock> findStockByCategoryAndNotDeleted(Category category) {
        return repo.findByCategoryAndDeletedIsFalse(category);
    }


    public Optional<Stock> getBookByIdResponse(long id) {
        return repo.findById(id);

    }

    private boolean isValidCategory(Category category) {
        for (Category validCategory : Category.values()) {
            if (validCategory == category) {
                return true;
            }
        }
        return false;
    }




    public String updatePublicationDate(long idBook, Date newPublicationDate) {
        // Call the repository method to update the publication date
        repo.updatePublicationDate(idBook, newPublicationDate);
        return "Publication date updated successfully";
    }


    @Transactional
    public void incrementAmountById(long id, long amountToAdd) {
        repo.incrementAmountById(id, amountToAdd);
    }
    public List<Stock> getAllActiveStocks() {
        return repo.findByDeletedFalse();
    }
    public List<Stock> getAllBooks() {
        return repo.findAll();
    }

    public void softDeleteIfNoOpenBorrows(long stockId) {
        Optional<Stock> stockOptional = repo.findById(stockId);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            List<Borrow> openBorrows = borroworepo.findByDateReturnIsNullAndIdBook_IdBook(stock.getIdBook());
            repo.softDeleteStockIfNoOpenBorrows(stock, openBorrows);
        }
    }


    public String updateAmountInLibrary(long id) {
        try {
            Optional<Stock> existingBook = repo.findById(id);
            if (existingBook.isPresent()) {
                Stock existingStock = existingBook.get();
                existingStock.setAmountInLibrary(existingStock.getAmountInLibrary() + 1);
                repo.save(existingStock);
                return "Amount in Library updated successfully";
            } else {
                return "Book not found";
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return "Internal Server Error";
        }
    }

    public String deleteBookById(long id) {
        try {
            Optional<Stock> existingBook = repo.findById(id);
            if (existingBook.isPresent()) {
                repo.delete(existingBook.get());
                return "Book deleted successfully";
            } else {
                return "Book not found";
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return "Internal Server Error";
        }
    }
    public List<Stock> getBooksByCategory(Category category) {
        List<Stock> books = repo.findByCategory(category);
        if (category == null || books == null || books.isEmpty()) {
            return Collections.emptyList(); // Return an empty list if category is null or no books found
        } else {
            return books; // Return list of books for the specified category
        }
    }
}
