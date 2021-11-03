package seedu.duke.entries;

import seedu.duke.exception.MintException;

public enum Interval {
    MONTH("MONTH"),
    YEAR("YEAR");

    public final String label;

    Interval(String label) {
        this.label = label;
    }

    public static Interval determineInterval(String interval) throws MintException {
        switch (interval.toUpperCase()) {
        case "MONTH":
            return MONTH;
        case "YEAR":
            return YEAR;
        default:
            throw new MintException("You entered invalid interval");
        }
    }
}

