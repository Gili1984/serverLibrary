package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name="readers")
public class Readers {
    @Id
    private  long idReader;
    private String readerName;
    private String phone;
    private String email;
    private Date birthDate;
    private boolean deleted = false;
    public Readers(long idReader) {
        this.idReader = idReader;
    }

    public Readers() {
    }
}
