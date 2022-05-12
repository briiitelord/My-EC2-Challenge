package com.revature.models;


import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="User")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    public User(){
        super();
    }

    public User(int id, String username, String password) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }

   
}