package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class ApparelBudget extends Budget {

    public ApparelBudget(double limit) {
        this.category = ExpenseCategory.APPAREL;
        this.name = ExpenseCategory.APPAREL.name();
        this.limit = limit;
    }
}
