package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class TransportationBudget extends Budget {

    public TransportationBudget(double limit) {
        this.category = ExpenseCategory.TRANSPORTATION;
        this.name = ExpenseCategory.TRANSPORTATION.name();
        this.limit = limit;
    }
}
