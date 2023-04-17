package com.example.springreactor.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Timestamp createdDate;

    private String locale;

    private ProfileStatus status;
}
