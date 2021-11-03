package seedu.duke.commands;


import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;


public class SetBudgetCommand extends Command {
    private final ExpenseCategory category;
    private final double amount;

    public SetBudgetCommand(ExpenseCategory category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        budgetManager.setBudget(category, amount);
        budgetDataManager.writeToBudgetTextFile(budgetManager.getBudgetList());
        ui.printSetBudget(category, amount);
    }
}


