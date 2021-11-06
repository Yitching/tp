package seedu.duke.entries;

import java.time.LocalDate;

public abstract class RecurringEntry extends Entry {
    private Interval interval;
    private LocalDate endDate;

    public RecurringEntry(RecurringEntry entry) {
        super(entry);
        this.interval = entry.getInterval();
        this.endDate = entry.getEndDate();
    }

    public RecurringEntry(String name, LocalDate date, double amount, Interval interval, LocalDate endDate) {
        super(name, date, amount);
        this.interval = interval;
        this.endDate = endDate;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


}
