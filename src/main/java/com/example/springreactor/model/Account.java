package com.example.springreactor.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("email")
    private String email;

    @Column("created_date")
    private Timestamp createdDate;

    @Column("status")
    private ProfileStatus status;
}
