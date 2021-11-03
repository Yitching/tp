package seedu.duke.commands;

import seedu.duke.budget.BudgetManager;

import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

public class DeleteAllCommand extends Command {
    private final boolean isNormal;
    private final boolean isRecurring;

    public DeleteAllCommand(boolean isNormal, boolean isRecurring) {
        this.isNormal = isNormal;
        this.isRecurring = isRecurring;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {

        if (Ui.isConfirmDeleteAll()) {
            if (isNormal) {
                deleteAllNormal(normalFinanceManager, normalListDataManager);
            }
            if (isRecurring) {
                deleteAllRecurring(recurringFinanceManager, recurringListDataManager);
            }
            Ui.deleteAllConfirmation();
        } else {
            Ui.deleteAborted();
        }
    }

    public void deleteAllNormal(NormalFinanceManager normalFinanceManager,
            NormalListDataManager normalListDataManager) {
        normalFinanceManager.deleteAll();
        normalListDataManager.deleteAll();
    }

    public void deleteAllRecurring(RecurringFinanceManager recurringFinanceManager,
            RecurringListDataManager recurringListDataManager) {
        recurringFinanceManager.deleteAll();
        recurringListDataManager.deleteAll();
    }
}