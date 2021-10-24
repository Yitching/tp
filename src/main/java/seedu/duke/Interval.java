package seedu.duke;

public enum Interval {
    WEEK("WEEK"),
    MONTH("MONTH"),
    YEAR("YEAR");

    public final String label;

    Interval(String label) {
        this.label = label;
    }
}

