package com.lett4s.logger;

public enum LogType {

    ERROR("\u001B[31m"),
    DEBUG("\u001B[33m"),
    LOG("\u001B[32m");

    LogType(String colour) {
        this.colour = colour;
    }

    private final String colour;

    private String getColour() {
        return this.colour;
    }

    @Override
    public String toString() {
        return getColour() + "[" + super.toString() + "]";
    }
}
