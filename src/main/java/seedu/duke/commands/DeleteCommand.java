package seedu.duke.commands;

import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.exception.MintException;
import seedu.duke.finances.FinanceManager;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Ui;

import java.util.ArrayList;

public class DeleteCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;
    public static final int SIZE_NO_MATCH = 0;
    public static final int SIZE_ONE_MATCH = 1;
    boolean isDelete = true;

    public DeleteCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            Entry entryToDelete = determineEntryToDelete(normalFinanceManager, ui);
            if (entryToDelete != null) {
                normalFinanceManager.deleteEntry(entryToDelete);
                String stringToDelete = NormalFinanceManager.overWriteString(entryToDelete);
                normalListDataManager.deleteLineInTextFile(stringToDelete);
                ui.printEntryDeleted(entryToDelete);
            }
        } catch (MintException e) {
            ui.printError(e);
        }
    }

    public Entry determineEntryToDelete(FinanceManager financeManager, Ui ui) throws MintException {
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            switch (filteredList.size()) {
            case SIZE_NO_MATCH:
                ui.printNoMatchingEntryMessage();
                return null;
            case SIZE_ONE_MATCH:
                return confirmToDeleteOneMatch(filteredList, ui);
            default:
                return confirmToDeleteMultipleMatch(filteredList, ui);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public Entry confirmToDeleteOneMatch(ArrayList<Entry> filteredList, Ui ui) {
        if (ui.isConfirmedToDeleteOrEdit(filteredList.get(0), isDelete)) {
            return filteredList.get(0);
        } else {
            ui.printCancelMessage();
            return null;
        }
    }

    public Entry confirmToDeleteMultipleMatch(ArrayList<Entry> filteredList, Ui ui) {
        ui.viewGivenList(filteredList);
        int index = ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
        if (index >= 0) {
            return filteredList.get(index);
        } else {
            ui.printCancelMessage();
        }
        return null;
    }
}
