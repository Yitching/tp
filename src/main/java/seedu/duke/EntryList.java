package seedu.duke;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class EntryList {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit?";
    public static final String NAME_SEPARATOR = "n/";
    public static final String DATE_SEPARATOR = "d/";
    public static final String AMOUNT_SEPARATOR = "a/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final int LENGTH_OF_SEPARATOR = 2;
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_INVALID_SORTDATE = "Please input a valid date.";
    public static final String CATEGORY_SEPARATOR = "c/";
    public static final String BLANK = "";
    //    public ArrayList<Expense> expenseList = new ArrayList<>();
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";


    public static ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry entry,
                                                      ArrayList<Entry> entryList) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(entryList);
        for (String tag : tags) {
            switch (tag) {
            case "n/":
                filteredList = Filter.filterEntryByName(entry.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(entry.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(entry.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(entry.getCategory(), filteredList);

                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    // Common method
    public static Entry chooseEntryByKeywords(ArrayList<String> tags, boolean isDelete, Entry query,
                                           ArrayList<Entry> entryList) throws MintException {
        ArrayList<Entry> filteredList = filterEntryByKeywords(tags, query, entryList);
        Entry entry = null;
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        } else if (filteredList.size() == 1) {
            Entry onlyEntry = filteredList.get(0);
            if (Ui.isConfirmedToDeleteOrEdit(onlyEntry, isDelete)) {
                entry = onlyEntry;
            }
            return entry;
        }

        Ui.viewGivenList(filteredList);
        try {
            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
            if (index >= 0) {
                entry = filteredList.get(index);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return entry;
    }

    public double calculateTotalEntry(ArrayList<Entry> entryList) {
        double total = 0;
        for (Entry entry : entryList) {
            total += entry.getAmount();
        }
        return total;
    }

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getCategory() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

}
