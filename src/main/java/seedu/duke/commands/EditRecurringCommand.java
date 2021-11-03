package seedu.duke.commands;

import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.util.ArrayList;

public class EditRecurringCommand extends Command {
    private Entry query;
    ArrayList<String> tags;


    public EditRecurringCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            EditCommand dummyEditCommand = new EditCommand(tags, query);
            Entry entryToEdit = dummyEditCommand.determineEntryToEdit(recurringFinanceManager, ui);
            if (entryToEdit != null) {
                ArrayList<String> list = recurringFinanceManager.editEntry(entryToEdit);
                String stringToOverwrite = list.get(0);
                String stringToUpdate = list.get(1);
                recurringListDataManager.editEntryListTextFile(stringToOverwrite, stringToUpdate);
            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }

}
