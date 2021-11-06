package seedu.duke.entries;

import java.time.LocalDate; // import the LocalDate class
import java.util.Objects;


public class Income extends Entry {
    protected IncomeCategory category;

    public Income(Income income) {
        super(income);
        this.category = income.getCategory();
        this.type = Type.Income;
    }

    public Income(String name, LocalDate date, double amount, IncomeCategory category) {
        super(name, date, amount);
        this.category = category;
        this.type = Type.Income;
    }

    public IncomeCategory getCategory() {
        return category;
    }

    public void setCategory(IncomeCategory category) {
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

        Income income = (Income) object;
        boolean isNameEqual = Objects.equals(getName(), income.getName());
        boolean isDateEqual = Objects.equals(getDate(), income.getDate());
        boolean isAmountEqual = Objects.equals(getAmount(), income.getAmount());
        boolean isCategoryEqual = Objects.equals(getCategory(), income.getCategory());
        return isNameEqual && isDateEqual && isAmountEqual && isCategoryEqual;
    }
}
