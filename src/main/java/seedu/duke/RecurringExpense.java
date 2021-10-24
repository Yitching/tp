package seedu.duke;

import java.time.LocalDate;
import java.util.Objects;

public class RecurringExpense extends Expense {
    private Interval interval;
    private LocalDate endDate;

    public RecurringExpense(Expense expense) {
        super(expense.getName(), expense.getDate(), expense.getAmount(), expense.getCategory());
    }

    public RecurringExpense(String name, LocalDate date, Double amount, ExpenseCategory category,
                            Interval interval, LocalDate endDate) {
        super(name, date, amount, category);
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

    @Override
    public String toString() {
        return getCategory() + " | " + getInterval().label + " | " + getDate() + " | "
                + getName() + " | $" + String.format("%,.2f", getAmount());
    }

    //@@author nipafx-reusedS
    //Reused from https://www.sitepoint.com/implement-javas-equals-method-correctly/
    //with minor modifications
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }

        RecurringExpense recurringExpense = (RecurringExpense) object;
        boolean isNameEqual = Objects.equals(getName(), recurringExpense.getName());
        boolean isDateEqual = Objects.equals(getDate(), recurringExpense.getDate());
        boolean isAmountEqual = Objects.equals(getAmount(), recurringExpense.getAmount());
        boolean isCategoryEqual = Objects.equals(getCategory(), recurringExpense.getCategory());
        boolean isIntervalEqual = Objects.equals(interval, recurringExpense.interval);
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual && isIntervalEqual;
    }
}
