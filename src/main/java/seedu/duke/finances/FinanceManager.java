package seedu.duke.finances;

import seedu.duke.exception.MintException;
import seedu.duke.entries.Type;
import seedu.duke.entries.Entry;
import seedu.duke.parser.Parser;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public abstract class FinanceManager {
    public static final String STRING_PROMPT_EDIT = "What would you like to edit? "
            + "Type the tag and what you want to change e.g. a/10";
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
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void deleteEntry(Entry entry) {

    }

    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry query) throws MintException {
        return null;
    }

    public ArrayList<String> editEntry(Entry query) throws MintException {
        return null;
    }

    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {

    }

    public void editSpecifiedEntry(String userInput, int indexToBeChanged, Entry entry) throws MintException {
        Parser parser = new Parser();
        ArrayList<String> splitChoice = new ArrayList<>();
        String choice = " " + userInput;
        while (choice.matches(userTagRaw)) {
            int currentIndex = parser.getCurrentTagIndex(choice);
            int nextIndex = choice.length();

            if (parser.hasNextTag(choice, currentIndex)) {
                nextIndex = parser.getNextTagIndex(choice, currentIndex);
            }
            choice = remainingString(splitChoice, choice, currentIndex, nextIndex);
        }
        try {
            amendEntry(indexToBeChanged, splitChoice, entry);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public String remainingString(ArrayList<String> splitChoice, String choice, int currentIndex, int nextIndex) {
        String description;
        description = choice.substring(currentIndex, nextIndex).trim();
        extractFieldsToAmend(splitChoice, description);
        choice = choice.substring(nextIndex);
        return choice;
    }

    public void extractFieldsToAmend(ArrayList<String> splitChoice, String description) {
        splitChoice.add(description);
    }

    public String scanFieldsToUpdate() {
        String choice;
        Scanner scan = new Scanner(System.in);
        System.out.println(STRING_PROMPT_EDIT);
        choice = scan.nextLine();
        return choice;
    }

    protected String nonEmptyNewDescription(String word) throws MintException {
        String description;
        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
        if (!newDescription.trim().equalsIgnoreCase(BLANK)) {
            description = newDescription.trim();
        } else {
            throw new MintException(ERROR_INVALID_DESCRIPTION);
        }
        return description;
    }
}
