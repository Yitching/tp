package seedu.duke.utility;

import seedu.duke.budget.Budget;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Type;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.Interval;
import seedu.duke.entries.ExpenseCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ui {
    public static final String FIRST_TIME_USER_MESSAGE = "You must be a first time user!\n"
            + "Welcome! To get you started, type \"help\" to get a list of commands you can use.\n"
            + "Alternatively, Type \"add n/[description] a/[amount]\" to add your very first item.\n"
            + "To see what you have added, type \"view\"!";
    public static final String RETRY_FILE_CREATION_MESSAGE = "Seems like a directory had a text file's extension... "
            + "Deleting that and trying again... ";
    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public void printError(MintException e) {
        System.out.println(e.getMessage());
    }

    public boolean hasUnsafeCharacters(String text) {
        Pattern pattern = Pattern.compile("[$&+,:;=\\\\?@#|'<>^*()%!]");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public String readUserInput() {
        if (in.hasNextLine()) {
            return in.nextLine().trim();
        }
        return null;
    }

    protected static final String INDENT = "    ";
    public static final String SOLID_LINE = "_______________________________________________________________________\n";
    public static final String LINE = "    ____________________________________________________________";
    public static final String SUCCESSFUL_EDIT_MESSAGE = "Got it! I will update the fields accordingly!";
    protected static final String LINE_SEPARATOR = System.lineSeparator();
    public static final int MIN_NAME_INDENTATION = 4;
    public static final int MIN_AMOUNT_INDENTATION = 5;
    public static final int MIN_SPENDING_INDENTATION = 6;
    public static final int MIN_LIMIT_INDENTATION = 7;
    protected static final int INDEX_CANCEL = -1;
    protected static final String CANCEL_MESSAGE = " To cancel, type \"cancel\"";
    public static final String MISSING_FILE_MESSAGE = "Missing data detected! Creating the necessary files...";
    public static final String MISSING_FIELDS_MESSAGE = "There seems to be some extra/missing fields! "
            + "Please delete the text files and try again!";
    public static final String GREETINGS = "Hello! I'm Mint" + System.lineSeparator() + "What can I do for you?";
    public static final String SHUTDOWN = "Goodbye! Hope to see you again soon!";

    public void printGreetings() {
        System.out.println(GREETINGS);
    }

    public void shutdown() {
        System.out.println(SHUTDOWN);
    }

    public static void printInvalidTagError() {
        System.out.println("Sorry, we are only able to process name, date and process for now");
        System.out.println("The following tags are available: n/ d/ a/");
    }

    public void help() {
        System.out.println("Available tags: n/name d/date a/AMOUNT c/CATEGORY_NUMBER i/interval e/endDate\n"
                + "Order of tags does not matter.\n"
                + "Square brackets \"[ ]\" identifies an optional argument.\n"
                + "List of commands available.\n"
                + SOLID_LINE
                + "KEYING IN ENTRIES. Type \"cat\" to view category number.\n"
                + "- add n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Add expense. Example: add n/chicken rice a/3.50 d/2021-09-30 c/1\n"
                + "- add income n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Add income. Example: add income n/payday a/400 d/2021-10-10 c/1\n"
                + "- delete [n/{keyword}] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Delete entries using keyword search. Needs at least 1 tag. Example: delete n/chicken\n"
                + "- edit [n/{keyword}] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Edit entries using keyword search. Needs at least 1 tag. Example: edit n/chicken\n"
                + SOLID_LINE
                + "RECURRING EXPENSES AND INCOME. Type \"cat\" to view category number.\n"
                + "Similar to keying in entries, but includes interval(mandatory for adding), endDate(optional)\n"
                + "Commands requires a \"R\", \n"
                + "e.g. addR, addR income, deleteR, editR\n\n"
                + "- addR n/NAME a/AMOUNT i/INTERVAL [d/START_DATE] [e/END_DATE] [c/CATEGORY_NUMBER] \n"
                + INDENT + "Add recurring expenses. Example: add n/spotify subscription a/10 i/MONTH d/2021-09-30 c/1\n"
                + "- addR income n/NAME a/AMOUNT i/INTERVAL [d/START_DATE] [e/END_DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Add recurring income. Example: add n/payday a/400 i/MONTH d/2021-10-10 c/1\n"
                + "- deleteR [n/{keyword}] [i/INTERVAL] [d/START_DATE] [e/END_DATE] [c/CATEGORY_NUMBER]\n"
                + INDENT + "Delete recurring entries using keyword search. Needs at least 1 tag. "
                + "Example: deleteR n/spotify\n"
                + "- editR [n/{keyword}] [a/AMOUNT] [d/DATE] [c/category number]\n"
                + INDENT + "Edit recurring entries using keyword search. Needs at least 1 tag. "
                + "Example: editR n/spotify\n"
                + SOLID_LINE
                + "BUDGETING. Type \"cat\" to view category number.\n"
                + "- set c/category number a/AMOUNT\n"
                + INDENT + "Set spending limit for individual category. Example: set c/0 100\n"
                + "- budget\n"
                + INDENT + "View current month's expenditure and budget\n"
                + SOLID_LINE
                + "UTILITIES\n"
                + "- view\n"
                + INDENT + "View expenses\n"
                + "- cat\n"
                + INDENT + "View categories and category number\n"
                + "- exit\n"
                + INDENT + "Exits the app\n"
                + SOLID_LINE
        );
    }

    public static void viewGivenList(ArrayList<Entry> list) {
        int maxNameLength = MIN_NAME_INDENTATION;
        int maxAmountLength = MIN_AMOUNT_INDENTATION;
        System.out.println("Here is the list of items containing the keyword.");
        for (Entry entry : list) {
            if (entry.getName().length() > maxNameLength) {
                maxNameLength = entry.getName().length();
            }
            if (String.format("%,.2f", entry.getAmount()).length() > maxAmountLength) {
                maxAmountLength = String.format("%,.2f", entry.getAmount()).length();
            }
        }
        System.out.println(" Index |   Type  |     Category     |    Date    | "
                + getMiddleIndented("Name", maxNameLength) + " | "
                + getMiddleIndented("Amount", maxAmountLength + 1) + " | Every |   Until");
        for (Entry entry : list) {
            printViewIndividualEntry(entry, maxNameLength, maxAmountLength, list.indexOf(entry) + 1);
        }
    }

    public static int chooseItemToDeleteOrEdit(ArrayList<Entry> filteredList, boolean isDelete) throws MintException {
        if (isDelete) {
            System.out.println("Enter the index of the item you want to delete." + CANCEL_MESSAGE);
        } else {
            System.out.println("Enter the index of the item you want to edit." + CANCEL_MESSAGE);
        }

        Scanner in = new Scanner(System.in);
        int index = 0;
        boolean proceedToDelete = false;
        while (!proceedToDelete) {
            if (in.hasNextLine()) {
                String userInput = in.nextLine();
                if (userInput.trim().equals("cancel")) {
                    return INDEX_CANCEL;
                }
                try {
                    index = Integer.parseInt(userInput.trim());
                } catch (NumberFormatException e) {
                    System.out.println(MintException.ERROR_INDEX_INVALID_NUMBER + CANCEL_MESSAGE);
                }
                if (index < 1 || index > filteredList.size()) {
                    System.out.println(MintException.ERROR_INDEX_OUT_OF_BOUND + CANCEL_MESSAGE);
                } else {
                    proceedToDelete = true;
                }
            } else {
                throw new MintException("no new line found");
            }
        }
        return index - 1;
    }

    public static boolean isConfirmedToDeleteOrEdit(Entry entry, boolean isDelete) {
        if (isDelete) {
            System.out.println("Is this what you want to delete?");
        } else {
            System.out.println("Is this what you want to edit?");
        }
        System.out.println(INDENT + entry);
        System.out.println("Type \"y\" if yes. Type \"n\" if not.");
        return isConfirmed();
    }

    public static boolean isConfirmDeleteAll() {
        System.out.println("Are you sure you want to delete all entries?");
        System.out.println("Type \"y\" if yes. Type \"n\" if not.");
        return isConfirmed();
    }

    public static boolean isConfirmed() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String userInput = in.nextLine();
            switch (userInput.trim()) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("Sorry I don't understand what that means. +"
                        + "Type \"y\" if yes. Type \"n\" if not.");
                break;
            }
        }
        return false;
    }

    public static void deleteAllConfirmation() {
        System.out.println("All entries successfully deleted.");
    }

    public static void deleteAborted() {
        System.out.println("Delete aborted.");
    }

    public void printNoMatchingEntryMessage() {
        System.out.println(MintException.ERROR_EXPENSE_NOT_IN_LIST);
    }

    public void printCancelMessage() {
        System.out.println("Ok. I have cancelled the process.");
    }

    public static void printOutcomeOfEditAttempt() {
        System.out.println(SUCCESSFUL_EDIT_MESSAGE);
    }

    public void printCategoryList() {
        System.out.println("Here are the categories and its tag number\n"
                + "Expenses           | Income\n"
                + "c/0 FOOD           | c/0 ALLOWANCE\n"
                + "c/1 ENTERTAINMENT  | c/1 WAGES\n"
                + "c/2 TRANSPORTATION | c/2 SALARY\n"
                + "c/3 HOUSEHOLD      | c/3 INTERESTED\n"
                + "c/4 APPAREL        | c/4 INVESTMENT\n"
                + "c/5 BEAUTY         | c/5 COMMISSION\n"
                + "c/6 GIFT           | c/6 GIFT\n"
                + "c/7 OTHERS         | c/7 OTHERS\n");
    }

    public static void printMissingFileMessage() {
        System.out.println(MISSING_FILE_MESSAGE);
    }

    public static void printFirstTimeUserMessage() {
        System.out.println(FIRST_TIME_USER_MESSAGE);
    }

    public static void printRetryFileCreationMessage() {
        System.out.println(RETRY_FILE_CREATION_MESSAGE);
    }

    public static void printFieldsErrorMessage() {
        System.out.println(MISSING_FIELDS_MESSAGE);
    }

    //    public static void setLimitMessage(String catNumString, String amount) {
    //        int catNumInt = Integer.parseInt(catNumString);
    //        System.out.println("Set Limit of " + CategoryList.getCatName(catNumInt) + " to $" + amount);
    //    }

    public static StringBuilder constructErrorMessage(ArrayList<String> missingDelimiters) throws MintException {
        int index = 1;
        StringBuilder missingFieldsErrorMessage = new StringBuilder();
        missingFieldsErrorMessage.append(Parser.STRING_INCLUDE);
        for (String delimiter : missingDelimiters) {
            switch (delimiter.trim()) {
            case "n/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_DESCRIPTION);
                index++;
                break;
            case "d/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_DATE);
                index++;
                break;
            case "a/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_AMOUNT);
                index++;
                break;
            case "c/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_CATNUM);
                index++;
                break;
            case "i/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_INTERVAL);
                index++;
                break;
            case "e/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_END_DATE);
                index++;
                break;
            default:
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        }
        return missingFieldsErrorMessage;
    }

    public int[] printView(ArrayList<Entry> outputArray, LocalDate fromDate, LocalDate endDate, double total) {
        int maxNameLength = MIN_NAME_INDENTATION;
        int maxAmountLength = MIN_AMOUNT_INDENTATION;
        System.out.println("Here is the list of your entries:");
        if (fromDate != null) {
            System.out.println("Since " + fromDate + " to " + endDate + ":");
        }
        for (Entry entry : outputArray) {
            if (entry.getName().length() > maxNameLength) {
                maxNameLength = entry.getName().length();
            }
            if (String.format("%,.2f", entry.getAmount()).length() > maxAmountLength) {
                maxAmountLength = String.format("%,.2f", entry.getAmount()).length();
            }
        }
        System.out.println("  Type  |     Category     |    Date    | " + getMiddleIndented("Name", maxNameLength)
                + " | " + getMiddleIndented("Amount", maxAmountLength + 1) + " | Every |   Until");
        for (Entry entry : outputArray) {
            printViewIndividualEntry(entry, maxNameLength, maxAmountLength, 0);
        }
        System.out.print(getIndent(maxNameLength, 0, "")
                + "                                Net Total: |");
        if (total < 0) {
            total = Math.abs(total);
            System.out.print("-$" + String.format("%,.2f", total));
        } else {
            System.out.print(" $" + String.format("%,.2f", total));
        }
        System.out.println();
        return new int[]{maxNameLength, maxAmountLength};
    }

    private static void printViewIndividualEntry(Entry entry, int maxNameLength, int maxAmountLength, int indexInt) {
        String index = indexInt == 0 ? "" : "   " + indexInt + "   | ";
        String type = entry.getType() == Type.Expense ? entry.getType().toString() : entry.getType() + " ";
        StringBuilder category = getCategoryIndented(entry.getCategory());
        String date = entry.getDate().toString();
        String name = getMiddleIndented(entry.getName(), maxNameLength);
        String amount = getRightIndented(String.format("%,.2f", entry.getAmount()), maxAmountLength);
        String negativeSign = entry.getType() == Type.Expense ? "-$" : " $";
        if (entry instanceof RecurringEntry) {
            String interval = entry.getInterval() == Interval.MONTH ? entry.getInterval().toString()
                    : entry.getInterval() + " ";
            String until = entry.getEndDate().toString();
            until = until.equals("2200-12-31") ? "Forever :D" : until;
            System.out.println(index + type + " | " + category + " | " + date + " | " + name + " |" + negativeSign
                    + amount + " | " + interval + " | " + until);
        } else {
            System.out.println(index + type + " | " + category + " | " + date + " | " + name + " |" + negativeSign
                    + amount + " |       |");
        }
    }

    public void printViewRecurring(ArrayList<Entry> entryList, int maxNameIndent, int maxAmountIndent) {
        System.out.println("Here is the list of recurring entries added to the above list:");
        for (Entry entry : entryList) {
            printViewIndividualEntry(entry, maxNameIndent, maxAmountIndent, 0);
        }
    }

    public static StringBuilder getIndent(int leftIndent, int rightIndent, String item) {
        StringBuilder itemWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            itemWithIndent.append(" ");
            leftIndent--;
        }

        itemWithIndent.append(item);

        while (rightIndent != 0) {
            itemWithIndent.append(" ");
            rightIndent--;
        }
        return itemWithIndent;
    }

    public static StringBuilder getCategoryIndented(Enum category) {
        double length = category.name().length();
        int leftIndent = (int) Math.floor((16 - length) / 2);
        int rightIndent = (int) Math.ceil((16 - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return getIndent(leftIndent, rightIndent, category.name());
    }

    public static String getRightIndented(String amount, int indent) {
        double length = amount.length();
        int rightIndent = (int) (indent - length);
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return getIndent(0, rightIndent, amount).toString();
    }

    public static String getMiddleIndented(String name, int indent) {
        double length = name.length();
        int leftIndent = (int) Math.floor((indent - length) / 2);
        int rightIndent = (int) Math.ceil((indent - length) / 2);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        if (rightIndent < 0) {
            rightIndent = 0;
        }
        return Ui.getIndent(leftIndent, rightIndent, name).toString();
    }

    public static String getLeftIndented(String amount, int indent) {
        double length = amount.length();
        int leftIndent = (int) (indent - length);
        if (leftIndent < 0) {
            leftIndent = 0;
        }
        return getIndent(leftIndent, 0, amount).toString();
    }

    public void printEntryAdded(Entry entry) {
        System.out.println("I've added: " + entry);
    }

    public void printInvalidCommand(String message) {
        System.out.println(message);
    }

    public void printEntryDeleted(Entry entry) {
        System.out.println("I have deleted: " + entry);
    }

    public void printSetBudget(ExpenseCategory category, double amount) {
        System.out.printf("Budget for %s set to $%.2f\n", category, amount);
    }

    public void printBudgetBreakdown(ArrayList<Budget> budgetList, ArrayList<Entry> entries,
            ArrayList<Entry> recurringEntries) {
        int maxSpendingLength = MIN_SPENDING_INDENTATION;
        int maxLimitLength = MIN_LIMIT_INDENTATION;
        for (Budget budget : budgetList) {
            if (String.format("$%,.2f",
                    budget.getMonthlySpending(entries, recurringEntries)).length() > maxSpendingLength) {
                maxSpendingLength = String.format("$%,.2f",
                        budget.getMonthlySpending(entries, recurringEntries)).length();
            }
            if (String.format("$%,.2f", budget.getLimit()).length() > maxLimitLength) {
                maxLimitLength = String.format("$%,.2f", budget.getLimit()).length();
            }
        }
        System.out.println("Here is the budget for ." + LocalDate.now().getMonth() + " " + LocalDate.now().getYear());
        System.out.println("    Category     | " + getLeftIndented("Amount", maxSpendingLength)
                + " | " + getRightIndented("Budget", maxLimitLength) + " | Percentage");
        for (Budget budget : budgetList) {
            printBudgetIndividualEntry(budget, entries, recurringEntries, maxSpendingLength, maxLimitLength);
        }

    }

    public void printBudgetIndividualEntry(Budget budget, ArrayList<Entry> entries,
            ArrayList<Entry> recurringEntries, int maxSpendingLength,
            int maxLimitLength) {
        String categoryIndented = getCategoryIndented(budget.getCategory()).toString();
        String spending = getLeftIndented(String.format("$%,.2f",
                budget.getMonthlySpending(entries, recurringEntries)),
                maxSpendingLength);
        String limit = budget.getLimit() == 0 ? getRightIndented("Not set", maxLimitLength) :
                getRightIndented(String.format("$%,.2f", budget.getLimit()), maxLimitLength);
        String percentage = "";
        if (budget.getLimit() != 0 && budget.getMonthlySpending(entries, recurringEntries) != 0) {
            percentage = String.format("%,.2f",
                    budget.getMonthlySpending(entries, recurringEntries) / budget.getLimit() * 100) + "%";
        }
        System.out.println(categoryIndented + " | " + spending + " / " + limit + " | " + percentage);
    }

    public void printBudgetWarningMessage(ExpenseCategory category, double spending, double limit) {
        if (spending > 0.8 * limit && limit != 0) {
            System.out.printf("Slow down, you've set aside $%.2f for %s, "
                    + "but you already spent $%.2f.\n", limit, category, spending);
        }
    }

    public void printUnsafeCharacters() {
        System.out.println("Please do not use special characters. "
                + "Only '.', '/', '-' are allowed. '/' is strictly use for tags. ");
    }
}



