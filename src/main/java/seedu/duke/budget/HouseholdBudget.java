package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class HouseholdBudget extends Budget {

    public HouseholdBudget(double limit) {
        this.category = ExpenseCategory.HOUSEHOLD;
        this.name = ExpenseCategory.HOUSEHOLD.name();
        this.limit = limit;
    }
}
