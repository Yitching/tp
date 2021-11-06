package seedu.duke.entries;


import java.time.LocalDate; // import the LocalDate class
import java.util.Objects;

public class Expense extends Entry {
    protected ExpenseCategory category;

    public Expense(Expense expense) {
        super(expense);
        this.category = expense.getCategory();
        this.type = Type.Expense;
    }

    public Expense(String name, LocalDate date, double amount, ExpenseCategory category) {
        super(name, date, amount);
        this.category = category;
        this.type = Type.Expense;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String toString() {
        return getType() + "  | " + getCategory().toString() + " | " + getDate() + " | "
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

        Expense expense = (Expense) object;
        boolean isNameEqual = Objects.equals(getName(), expense.getName());
        boolean isDateEqual = Objects.equals(getDate(), expense.getDate());
        boolean isAmountEqual = Objects.equals(getAmount(), expense.getAmount());
        boolean isCategoryEqual = Objects.equals(getCategory(), expense.getCategory());
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual;
    }
}
