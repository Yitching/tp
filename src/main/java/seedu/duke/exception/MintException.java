package seedu.duke.exception;

public class MintException extends Exception {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DATE_EDIT = "Invalid date entered! Unable to edit expense.";
    public static final String ERROR_INVALID_DESCRIPTION = "Invalid description entered! Unable to edit expense.";
    public static final String ERROR_INVALID_SORTTYPE = "Please input how you want the list to be sorted.";
    public static final String ERROR_NO_DELIMETER = "Invalid command entered!";
    public static final String ERROR_NO_NAME = "Please add the description of the item!";
    public static final String ERROR_EXPENSE_NOT_IN_LIST = "Hmm.. That item is not in the list.";
    public static final String ERROR_INVALID_COMMAND = "Sorry I don't know what that means. :(";
    public static final String ERROR_INVALID_TAG_ERROR = "Sorry, we are only able to process name, date, category, and"
            + " amount for normal entries:" + LINE_SEPARATOR + "n/ d/ a/ c/" + LINE_SEPARATOR + "For recurring entries,"
            + " name, date, category, amount, interval, and end date are available:" + LINE_SEPARATOR
            + "n/ d/ a/ c/ i/ e/";
    public static final String ERROR_INDEX_INVALID_NUMBER = "Please enter a valid number.";
    public static final String ERROR_INDEX_OUT_OF_BOUND = "Please enter a number between "
            + "1 and total number of items listed.";
    public static final String ERROR_INVALID_AMOUNT = "Please enter a valid amount!";
    public static final String ERROR_INVALID_DATE = "Please enter a valid date!";
    public static final String ERROR_INVALID_END_DATE = "End date must be after start date.";
    public static final String ERROR_INVALID_INTERVAL = "Please enter valid interval: MONTH, YEAR";
    public static final String ERROR_INVALID_MONTH = "Please enter a valid month!";
    public static final String ERROR_INVALID_YEAR = "Please enter a valid year! Year should be from 2000 to 2200.";
    public static final String ERROR_INVALID_CATNUM = "Please enter a valid category number! c/0 to c/7";
    public static final String ERROR_DUPLICATE_TAGS = "Please do not enter more than one duplicate tags!";
    public static final String ERROR_NO_SPACE_BEFORE_TAGS = "Please ensure spaces before each tag, "
            + "e.g. \" n/name\".";

    public static final String ERROR_MISSING_TAG_TYPE = "\"/\" is strictly used for tagging purposes ONLY. "
            + "Please specify tag type, e.g. \"n/\"";
    public static final String ERROR_AMOUNT_TOO_LARGE = "Please enter an amount less than or equal to 1 million!";
    public static final String ERROR_INVALID_FORWARD_SLASH = "\"/\" is strictly used for tagging purposes ONLY. "
            + "e.g. n/name or a/10";

    public MintException(String message) {
        super(message);
    }
}
