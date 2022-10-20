package com.example.gymmembersapplication;

public enum Status {
    ACTIVE("Active"), EXPIRED("Expired");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatusAsString() {
        return status;
    }
}
