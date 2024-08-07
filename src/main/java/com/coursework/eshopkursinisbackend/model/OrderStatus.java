package com.coursework.eshopkursinisbackend.model;

public enum OrderStatus {
    PENDING("Pending"),
    URGENT("Urgent"),
    PROCESSING("Processing"),
    FINISHED("Finished");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}