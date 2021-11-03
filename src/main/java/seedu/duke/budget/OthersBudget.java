package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class OthersBudget extends Budget {

    public OthersBudget(double limit) {
        this.category = ExpenseCategory.OTHERS;
        this.name = ExpenseCategory.OTHERS.name();
        this.limit = limit;
    }
}
