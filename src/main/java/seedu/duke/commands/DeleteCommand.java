package seedu.duke.commands;

import seedu.duke.entries.Entry;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;

import java.util.ArrayList;

public class DeleteCommand extends Command {
    private final Entry query;
    private final ArrayList<String> tags;

    public DeleteCommand(ArrayList<String> tags, Entry query) {
        this.query = query;
        this.tags = tags;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
                        RecurringFinanceManager recurringFinanceManager, Ui ui) {
        try {
            Entry deletedEntry = normalFinanceManager.deleteEntryByKeywords(tags, query);
            ui.printEntryDeleted(deletedEntry);
        } catch (MintException e) {
            ui.printError(e);
        }
    }
}
