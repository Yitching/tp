package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class EntertainmentBudget extends Budget {

    public EntertainmentBudget(double limit) {
        this.category = ExpenseCategory.ENTERTAINMENT;
        this.name = ExpenseCategory.ENTERTAINMENT.name();
        this.limit = limit;
    }
}
