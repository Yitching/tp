package seedu.duke.commands;


import seedu.duke.budget.Budget;
import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.util.ArrayList;


public class AddCommand extends Command {
    private final Entry entry;

    public AddCommand(Entry entry) {
        this.entry = entry;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            normalFinanceManager.addEntry(entry);
            normalListDataManager.appendToEntryListTextFile(entry);
            ui.printEntryAdded(entry);
            if (entry.getType() == Type.Expense) {
                ArrayList<Entry> entries = normalFinanceManager.getEntryList();
                Expense expense = (Expense) entry;
                ExpenseCategory categoryOfCurrentEntry = expense.getCategory();
                double amountSpent = budgetManager.getMonthlySpendingCategory(categoryOfCurrentEntry, entries);
                Budget budgetOfCurrentEntry = budgetManager.getMonthlyBudgetFromCategory(categoryOfCurrentEntry);
                double spendingLimit = budgetOfCurrentEntry.getLimit();
                ui.printBudgetWarningMessage(categoryOfCurrentEntry, amountSpent, spendingLimit);

            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}


