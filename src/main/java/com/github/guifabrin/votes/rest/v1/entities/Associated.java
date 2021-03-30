package com.github.guifabrin.votes.rest.v1.entities;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.github.guifabrin.votes.rest.v1.utils.ChyperUtils;

@Entity
@Table(name = "associated", uniqueConstraints = { @UniqueConstraint(columnNames = { "cpf" }) })
public class Associated {

    @Id
    private String cpf;

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
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
