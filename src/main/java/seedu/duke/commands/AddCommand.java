package seedu.duke.commands;


import seedu.duke.Entry;
import seedu.duke.storage.EntryListDataManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class AddCommand extends Command {

    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";

    public void add(Entry entry, ArrayList<Entry> entryList) {
        //        if (isCurrentMonthExpense(expense)) {
        //            CategoryList.addSpending(expense);
        //        }
        //        logger.log(Level.INFO, "User added expense: " + expense);
        System.out.println("I have added: " + entry);
        entryList.add(entry);
        try {
            EntryListDataManager.appendToEntryListTextFile(FILE_PATH, entry);
        } catch (IOException e) {
            System.out.println("Error trying to update external file!");
        }
    }
}
