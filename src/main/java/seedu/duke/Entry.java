package seedu.duke;

import java.time.LocalDate; // import the LocalDate class

public abstract class Entry {
    protected String name;
    protected LocalDate date;
    protected double amount;
    protected Type type;

    public Entry(String name, LocalDate date, double amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getNameIndented() {
        double length = name.length();
        int leftIndent = (int) Math.floor((16 - length) / 2);
        int rightIndent = (int) Math.ceil((16 - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return Ui.getIndent(leftIndent, rightIndent, name).toString();
    }
  
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getAmountString() {
        return Double.toString(amount);
    }

    public Type getType() {
        return type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract Enum getCategory();

    public abstract boolean equals(Object object);

}
