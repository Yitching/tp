package seedu.duke.entries;

import java.time.LocalDate; // import the LocalDate class

public abstract class Entry {
    protected String name;
    protected LocalDate date;
    protected double amount;
    protected Type type;
    protected Interval interval;
    protected LocalDate endDate;

    public Entry(Entry entry) {
        this.name = entry.getName();
        this.date = entry.getDate();
        this.amount = entry.getAmount();
        this.type = null;
    }

    public Entry(String name, LocalDate date, double amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public Interval getInterval() {
        return interval;
    }

    public Type getType() {
        return type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Enum getCategory() {
        return null;
    }
}
