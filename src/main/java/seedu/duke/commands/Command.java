package seedu.duke.commands;

import seedu.duke.budget.BudgetManager;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

public abstract class Command {
    public abstract void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager,
            Ui ui);

    public boolean isExit() {
        return false;
    }
}

