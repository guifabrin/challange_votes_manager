package com.github.guifabrin.votes.rest.v1.entities;

public class Status {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAble() {
        return this.status.equals("ABLE_TO_VOTE");
    }

}
