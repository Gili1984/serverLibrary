package com.example.demo.service;
import java.lang.RuntimeException;

public class ExeptionsLibrary extends RuntimeException{
    public ExeptionsLibrary(String message) {
        super(message);
    }
}
