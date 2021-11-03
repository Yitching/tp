package seedu.duke.budget;

import seedu.duke.entries.ExpenseCategory;

public class GiftBudget extends Budget {

    public GiftBudget(double limit) {
        this.category = ExpenseCategory.GIFT;
        this.name = ExpenseCategory.GIFT.name();
        this.limit = limit;
    }
}
