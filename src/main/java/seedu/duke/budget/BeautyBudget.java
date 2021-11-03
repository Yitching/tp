package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class BeautyBudget extends Budget {

    public BeautyBudget(double limit) {
        this.category = ExpenseCategory.BEAUTY;
        this.name = ExpenseCategory.BEAUTY.name();
        this.limit = limit;
    }
}
