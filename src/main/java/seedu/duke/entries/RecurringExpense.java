package seedu.duke.entries;

import seedu.duke.utility.Ui;

import java.time.LocalDate;
import java.util.Objects;

public class RecurringExpense extends RecurringEntry {
    private ExpenseCategory category;

    public RecurringExpense(RecurringExpense expense) {
        super(expense);
        this.category = expense.getCategory();
        this.type = Type.Expense;
    }

    public RecurringExpense(String name, LocalDate date, Double amount, ExpenseCategory category,
                            Interval interval, LocalDate endDate) {
        super(name, date, amount, interval, endDate);
        this.category = category;
        this.type = Type.Expense;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        if (getEndDate().equals(LocalDate.parse("2200-12-31"))) {
            return getType() + " | " + getCategory().toString() + " | " + getDate() + " | "
                    + getName() + " |-$" + String.format("%,.2f", getAmount()) + " | " + getInterval().label
                    + " | " + "Forever :D";
        }
        return getType() + " | " + getCategory().toString() + " | " + getDate() + " | "
                + getName() + " |-$" + String.format("%,.2f", getAmount()) + " | " + getInterval().label
                + " | " + getEndDate();
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
        boolean isIntervalEqual = Objects.equals(getInterval(), recurringExpense.getInterval());
        boolean isEndDateEqual = Objects.equals(getEndDate(), recurringExpense.getEndDate());
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual && isIntervalEqual && isEndDateEqual;
    }
}
