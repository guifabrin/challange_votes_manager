package com.github.guifabrin.votes.rest.v1.entities;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "shedule")
public class Shedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Date startDate;

    private Integer minutes = 1;

    @Transient
    private boolean voted;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Vote> votes = new LinkedHashSet<Vote>();

    public String getDescription() {
        return description;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public Long getYesVotes() {
        return this.votes.stream().filter(vote -> vote.isVote()).count();
    }

    public Long getNoVotes() {
        return this.votes.stream().filter(vote -> !vote.isVote()).count();
    }

    public Collection<Vote> getVotes() {
        return this.votes;
    }
}
