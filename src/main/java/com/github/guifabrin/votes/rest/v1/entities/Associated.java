package com.github.guifabrin.votes.rest.v1.entities;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.github.guifabrin.votes.rest.v1.utils.ChyperUtils;

@Entity
public class Associated {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cpf;

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.password = ChyperUtils.encrypt(password);
    }

    public void setName(String name) {
        this.name = name;
    }

}
