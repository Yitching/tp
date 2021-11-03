package seedu.duke.finances;

import seedu.duke.entries.Entry;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NormalFinanceManager extends FinanceManager {

    public ArrayList<Entry> entryList;
    public static boolean hasOneMatch;

    public NormalFinanceManager() {
        this.entryList = new ArrayList<>();
    }

    public void addEntry(Entry entry) throws MintException {
        entryList.add(entry);
    }

    public ArrayList<Entry> getEntryList() {
        return entryList;
    }

    //    @Override
    //    public Entry chooseEntryByKeywords(ArrayList<String> tags, boolean isDelete, Entry query)
    //    throws MintException {
    //        ArrayList<Entry> filteredList = filterEntryByKeywords(tags, query);
    //        Entry entry;
    //        if (filteredList.size() == 0) {
    //            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
    //        } else if (filteredList.size() == 1) {
    //            Entry onlyEntry = filteredList.get(0);
    //            if (Ui.isConfirmedToDeleteOrEdit(onlyEntry, isDelete)) {
    //                entry = onlyEntry;
    //            } else {
    //                throw new MintException("Ok. I have cancelled the process.");
    //            }
    //            return entry;
    //        }
    //
    //        Ui.viewGivenList(filteredList);
    //
    //        try {
    //            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
    //            if (index >= 0) {
    //                entry = filteredList.get(index);
    //            } else {
    //                throw new MintException("Ok. I have cancelled the process.");
    //            }
    //        } catch (MintException e) {
    //            throw new MintException(e.getMessage());
    //        }
    //        return entry;
    //    }

    @Override
    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(entryList);
        for (String tag : tags) {
            switch (tag.trim()) {
            case "n/":
                filteredList = Filter.filterEntryByName(query.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(query.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(query.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(query.getCategory().ordinal(), filteredList);
                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    @Override
    public void deleteEntry(Entry entry) {
        //logger.log(Level.INFO, "User deleted expense: " + entry);
        entryList.remove(entry);
    }

    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged = 0;
        String originalEntryStr = "";
        originalEntryStr = overWriteString(entry);
        if (entryList.contains(entry)) {
            indexToBeChanged = entryList.indexOf(entry);
            choice = scanFieldsToUpdate();
        } else {
            //                logger.log(Level.INFO, "User entered invalid entry");
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
        }
        editSpecifiedEntry(choice, indexToBeChanged, entry);

        String newEntryStr = overWriteString(entryList.get(indexToBeChanged));
        Ui.printOutcomeOfEditAttempt();
        return new ArrayList<>(Arrays.asList(originalEntryStr, newEntryStr));
    }

    @Override
    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            Parser parser = new Parser();
            HashMap<String, String> entryFields = parser.prepareEntryToAmendForEdit(entry);
            Type type = entry.getType();

            int count = 0;
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_SEPARATOR)) {
                    count++;
                    String name = nonEmptyNewDescription(word);
                    entryFields.put("name", name);
                } else if (word.contains(DATE_SEPARATOR)) {
                    count++;
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("date", dateStr);
                } else if (word.contains(AMOUNT_SEPARATOR)) {
                    count++;
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("amount",amountStr);
                } else if (word.contains(CATEGORY_SEPARATOR)) {
                    count++;
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("catNum", catNumStr);
                }
            }
            if (count == 0) {
                throw new MintException("No valid fields entered!");
            }
            setEditedEntry(index, entryFields, type);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    private void setEditedEntry(int index, HashMap<String, String> entryFields, Type type) throws MintException {
        Parser parser = new Parser();
        String name = entryFields.get("name");
        String dateStr = entryFields.get("date");
        String amountStr = entryFields.get("amount");
        String catNumStr = entryFields.get("catNum");

        ValidityChecker.checkValidityOfFieldsInNormalListTxt("expense", name, dateStr, amountStr, catNumStr);
        Entry entry = parser.convertEntryToRespectiveTypes(entryFields, type);
        entryList.set(index, entry);
    }

    public ArrayList<Entry> getCopyOfArray() {
        ArrayList<Entry> outputArray;
        outputArray = new ArrayList<>(entryList);
        return outputArray;
    }

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getType() + "|" + entry.getCategory().ordinal() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

    public void deleteAll() {
        entryList.clear();
    }
}
