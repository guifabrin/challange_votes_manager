package com.github.guifabrin.votes.rest.v1.entities;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.guifabrin.votes.rest.v1.utils.ChyperUtils;

@Entity
@Table(name = "associated")
public class Associated {

    @Id
    private String cpf;

    private String name;

    private String password = "";

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Vote> votes = new LinkedHashSet<Vote>();

    public String getName() {
        return name;
    }

    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
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

    public void addVote(Vote vote) {
        votes.add(vote);
    }
}
