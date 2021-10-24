package seedu.duke;

import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Income extends Entry {
    protected IncomeCategory category;

    public Income(String name, LocalDate date, double amount, IncomeCategory category) {
        super(name, date, amount);
        this.category = category;
        this.type = Type.Income;
    }

    public IncomeCategory getCategory () {
        return category;
    }

    public String getCategoryIndented () {
        double length = getCategory().toString().length();
        int leftIndent = (int) Math.floor((16 - length) / 2);
        int rightIndent = (int) Math.ceil((16 - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return Ui.getIndent(leftIndent, rightIndent, getCategory().toString()).toString();
    }

    public void setCategory (IncomeCategory category) {
        this.category = category;
    }

    public String toString () {
        return getType() + "  | " + getCategoryIndented() + " | " + getDate() + " | "
                + getNameIndented() + " | $" + String.format("%,.2f", getAmount());
    }

    @Override
    public boolean equals (Object object) {
        return false;
    }
}
