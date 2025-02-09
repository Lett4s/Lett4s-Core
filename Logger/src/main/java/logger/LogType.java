package logger;

public enum LogType {

    WARN("\u001B[33m"),
    ERROR("\u001B[91m"),
    FATAL("\u001B[31m"),
    DEBUG("\u001B[93m"),
    INFO("\u001B[92m"),
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
