package seedu.duke;

import seedu.duke.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseList {
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

    //    public void addExpense(Expense expense) {
    //        addExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }
    //
    //    // MOVED
    //    public void addExpense(String name, String date, String amount, String catNum) {
    //        Expense expense = new Expense(name, date, amount, catNum);
    //        if (isCurrentMonthExpense(expense)) {
    //            CategoryList.addSpending(expense);
    //        }
    //        logger.log(Level.INFO, "User added expense: " + expense);
    //        System.out.println("I have added: " + expense);
    //        expenseList.add(expense);
    //        try {
    //            ExpenseListDataManager.appendToExpenseListTextFile(FILE_PATH, expense);
    //        } catch (IOException e) {
    //            System.out.println("Error trying to update external file!");
    //        }
    //    }

    //    public static ArrayList<Expense> filterExpenseByKeywords(ArrayList<String> tags,Entry entry,
    //                                   ArrayList<Expense> expenseList) throws MintException {
    //
    //        ArrayList<Expense> filteredList = new ArrayList<>(expenseList);
    //        for (String tag : tags) {
    //            switch (tag) {
    //            case "n/":
    //                filteredList = Filter.filterExpenseByName(entry.getName(), filteredList);
    //                break;
    //            case "d/":
    //                filteredList = Filter.filterExpenseByDate(entry.getDate(), filteredList);
    //                break;
    //            case "a/":
    //                filteredList = Filter.filterExpenseByAmount(entry.getAmount(), filteredList);
    //                break;
    //            case "c/":
    //                filteredList = Filter.filterExpenseByCategory(entry.getCategory(), filteredList);
    //                break;
    //            default:
    //                throw new MintException("Unable to locate tag");
    //            }
    //        }
    //        return filteredList;
    //    }

    //    public void deleteExpenseByKeywords(ArrayList<String> tags, String name,
    //                                 String date, String amount, String catNum) throws MintException {
    //        try {
    //            Expense expense = chooseExpenseByKeywords(tags, true, name, date, amount, catNum);
    //            if (expense != null) {
    //                deleteExpense(expense);
    //            }
    //        } catch (MintException e) {
    //            throw new MintException(e.getMessage());
    //        }
    //    }
    //
    //    // MOVED
    //    public void editExpenseByKeywords(ArrayList<String> tags, String name,
    //                               String date, String amount, String catNum) throws MintException {
    //        try {
    //            Expense expense = chooseExpenseByKeywords(tags, false, name, date, amount, catNum);
    //            if (expense != null) {
    //                editExpense(expense);
    //            }
    //        } catch (MintException e) {
    //            throw new MintException(e.getMessage());
    //        }
    //    }

    //    //    Common method
    //     public static Expense chooseExpenseByKeywords(ArrayList<String> tags, boolean isDelete, String name,
    //                                              String date, String amount, String catNum,
    //                                              ArrayList<Expense> expenseList) throws MintException {
    //    ArrayList<Expense> filteredList = filterExpenseByKeywords(tags, name, date, amount, catNum, expenseList);
    //    Expense expense = null;
    //    if (filteredList.size() == 0) {
    //        throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
    //    } else if (filteredList.size() == 1) {
    //        Expense onlyExpense = filteredList.get(0);
    //        if (Ui.isConfirmedToDeleteOrEdit(onlyExpense, isDelete)) {
    //            expense = onlyExpense;
    //        }
    //        return expense;
    //    }
    //
    //    Ui.viewGivenList(filteredList);
    //    try {
    //        int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
    //        if (index >= 0) {
    //            expense = filteredList.get(index);
    //        }
    //    } catch (MintException e) {
    //        throw new MintException(e.getMessage());
    //    }
    //    return expense;
    //}


    //    public void deleteExpense(Expense expense) throws MintException {
    //        deleteExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }
    //
    //    // MOVED
    //    public void deleteExpense(String name, String date, String amount, String catNum) throws MintException {
    //        Expense expense = new Expense(name, date, amount, catNum);
    //        if (expenseList.contains(expense)) {
    //            logger.log(Level.INFO, "User deleted expense: " + expense);
    //            System.out.println("I have deleted: " + expense);
    //            expenseList.remove(expense);
    //            String stringToDelete = overWriteString(expense);
    //            if (isCurrentMonthExpense(expense)) {
    //                CategoryList.deleteSpending(expense);
    //            }
    //            ExpenseListDataManager.deleteLineInTextFile(stringToDelete);
    //        }
    //    }
    //
    //    // MOVED
    //    public void viewExpense(String[] argumentArrayInput,
    //                            RecurringExpenseList recurringExpenseList) throws MintException {
    //        String sortType;
    //        LocalDate fromDate;
    //        LocalDate endDate;
    //        Month month;
    //        String year = null;
    //        ArrayList<String> argumentArray = new ArrayList<>(Arrays.asList(argumentArrayInput));
    //        ArrayList<Expense> outputArray = new ArrayList<Expense>(expenseList);
    //
    //        if (argumentArray.contains("by")) {
    //            try {
    //                sortType = argumentArray.get(argumentArray.indexOf("by") + 1);
    //                sort(outputArray, sortType);
    //            } catch (IndexOutOfBoundsException e) {
    //                System.out.println(MintException.ERROR_INVALID_SORTTYPE);
    //                return;
    //            }
    //        }
    //
    //        System.out.println("Here is the list of your expenses:");
    //
    //        if (argumentArray.contains("year")) {
    //            try {
    //                year = argumentArray.get(argumentArray.indexOf("year") + 1);
    //            } catch (IndexOutOfBoundsException e) {
    //                year = Integer.toString(LocalDate.now().getYear());
    //            }
    //            System.out.println("For the year " + year + ":");
    //            Sorter.trimByYear(outputArray, year);
    //        }
    //
    //        if (argumentArray.contains("month")) {
    //            try {
    //                month = Month.of(Integer.parseInt(argumentArray.get(argumentArray.indexOf("month") + 1)));
    //                if (year == null) {
    //                    year = Integer.toString(LocalDate.now().getYear());
    //                    Sorter.trimByYear(outputArray, year);
    //                }
    //            } catch (IndexOutOfBoundsException e) {
    //                month = LocalDate.now().getMonth();
    //            }
    //            System.out.println("For the month of " + month + ":");
    //            Sorter.trimByMonth(outputArray, month);
    //            if (year == null) {
    //                year = Integer.toString(LocalDate.now().getYear());
    //            }
    //            //recurringExpenseList.viewRecurringExpenseByMonth(outputArray, month.getValue(),
    //            //        Integer.parseInt(year));
    //        }
    //
    //        if (argumentArray.contains("from")) {
    //            try {
    //                fromDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 1));
    //                try {
    //                    endDate = LocalDate.parse(argumentArray.get(argumentArray.indexOf("from") + 2));
    //                } catch (IndexOutOfBoundsException | DateTimeParseException ignored) {
    //                    endDate = null;
    //                }
    //                System.out.print("Since " + fromDate);
    //                Sorter.trimFrom(outputArray, fromDate);
    //                if (endDate != null) {
    //                    Sorter.trimEnd(outputArray, endDate);
    //                    System.out.print(" to " + endDate);
    //                }
    //                System.out.println();
    //
    //            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
    //                System.out.println(MintException.ERROR_INVALID_SORTDATE);
    //                return;
    //            }
    //            //recurringExpenseList.viewRecurringExpenseBetweenTwoDates(outputArray, fromDate,
    //            //        endDate);
    //        }
    //
    //        if (argumentArray.contains("ascending") || argumentArray.contains("up")) {
    //            Collections.reverse(outputArray);
    //        }
    //
    //        for (Expense expense : outputArray) {
    //            System.out.println(expense.viewToString());
    //        }
    //    }

    public double calculateTotalExpense(ArrayList<Expense> expenseList) {
        double total = 0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        return total;
    }


    //    public void sort(ArrayList<Expense> outputArray, String sortType) throws MintException {
    //        assert sortType != null : "sortType should have a command";
    //        switch (sortType) {
    //        case "name":
    //            outputArray.sort(Sorter.compareByName);
    //            break;
    //        case "date":
    //            outputArray.sort(Sorter.compareByDate);
    //            break;
    //        case "amount":
    //            outputArray.sort(Sorter.compareByAmount);
    //            break;
    //        case "category":
    //            outputArray.sort(Sorter.compareByCategory);
    //            break;
    //        default:
    //            throw new MintException(MintException.ERROR_INVALID_COMMAND);
    //        }
    //    }
    //
    //    // MOVED
    //    public void editExpense(Expense expense) throws MintException {
    //        editExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }
    //
    //    // MOVED
    //    public void editExpense(String name, String date, String amount, String catNum) throws MintException {
    //        String choice;
    //        int indexToBeChanged;
    //        boolean printEditSuccess = false;
    //        boolean exceptionThrown = false;
    //        try {
    //            Expense originalExpense = new Expense(name, date, amount, catNum);
    //            final String originalExpenseStr = originalExpense.toString();
    //            final String stringToOverwrite = overWriteString(originalExpense);
    //            if (expenseList.contains(originalExpense)) {
    //                indexToBeChanged = expenseList.indexOf(originalExpense);
    //                choice = scanFieldsToUpdate();
    //            } else {
    //                logger.log(Level.INFO, "User entered invalid entry");
    //                throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
    //            }
    //            editSpecifiedEntry(choice, indexToBeChanged, originalExpense);
    //            printEditSuccess = isEditSuccessful(indexToBeChanged, originalExpenseStr);
    //            String stringToUpdate = overWriteString(expenseList.get(indexToBeChanged));
    //            final Expense newExpense = expenseList.get(indexToBeChanged);
    //            CategoryList.editSpending(originalExpense, newExpense);
    //            ExpenseListDataManager.editExpenseListTextFile(stringToOverwrite, stringToUpdate);
    //
    //        } catch (NumberFormatException e) {
    //            exceptionThrown = true;
    //            System.out.println(MintException.ERROR_INVALID_NUMBER);
    //        } catch (DateTimeParseException e) {
    //            exceptionThrown = true;
    //            System.out.println(MintException.ERROR_INVALID_DATE_EDIT);
    //        }
    //        Ui.printOutcomeOfEditAttempt(printEditSuccess, exceptionThrown);
    //    }

    // common method
    public static String overWriteString(Expense expense) {
        return expense.getCategory() + "|" + expense.getDate() + "|" + expense.getName()
                + "|" + expense.getAmount();
    }


    //    private void editSpecifiedEntry(String userInput, int indexToBeChanged, Expense expense)
    //    throws MintException {
    //        Parser parser = new Parser();
    //        ArrayList<String> splitChoice = new ArrayList<>();
    //        String choice = " " + userInput;
    //        while (choice.matches(userTagRaw)) {
    //            int currentIndex = parser.getCurrentTagIndex(choice);
    //            int nextIndex = choice.length();
    //
    //            if (parser.hasNextTag(choice, currentIndex)) {
    //                nextIndex = parser.getNextTagIndex(choice, currentIndex);
    //            }
    //            choice = remainingString(splitChoice, choice, currentIndex, nextIndex);
    //        }
    //        amendExpense(indexToBeChanged, splitChoice, expense);
    //    }
    //
    //    // MOVED
    //    private String remainingString(ArrayList<String> splitChoice, String choice, int currentIndex,
    //    int nextIndex) {
    //        String description;
    //        description = choice.substring(currentIndex, nextIndex).trim();
    //        extractFieldsToAmend(splitChoice, description);
    //        choice = choice.substring(nextIndex);
    //        return choice;
    //    }
    //
    //    // MOVED
    //    private void extractFieldsToAmend(ArrayList<String> splitChoice, String description) {
    //        splitChoice.add(description);
    //    }
    //
    //    // MOVED
    //    private String scanFieldsToUpdate() {
    //        String choice;
    //        Scanner scan = new Scanner(System.in);
    //        System.out.println(STRING_PROMPT_EDIT);
    //        choice = scan.nextLine();
    //        return choice;
    //    }
    //
    //    // MOVED
    //    private Boolean isEditSuccessful(int indexToBeChanged, String originalExpense) {
    //        String newExpense = expenseList.get(indexToBeChanged).toString();
    //        return !originalExpense.equals(newExpense);
    //    }
    //
    //    // MOVED
    //    private void amendExpense(int index, ArrayList<String> choice, Expense expense) throws MintException {
    //        String name = expense.getName();
    //        String date = expense.getDate().toString();
    //        String amount = Double.toString(expense.getAmount());
    //        String catNum = Integer.toString(expense.getCatNum());
    //        for (String word : choice) {
    //            assert (word != null);
    //            if (word.contains(NAME_SEPARATOR)) {
    //                name = nonEmptyNewDescription(word);
    //            }
    //            if (word.contains(DATE_SEPARATOR)) {
    //                date = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
    //            }
    //            if (word.contains(AMOUNT_SEPARATOR)) {
    //                amount = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
    //            }
    //            if (word.contains(CATEGORY_SEPARATOR)) {
    //                catNum = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
    //                CategoryList.checkValidCatNum(catNum);
    //            }
    //        }
    //        expenseList.set(index, new Expense(name, date, amount, catNum));
    //    }
    //
    //    // MOVED
    //    private String nonEmptyNewDescription(String word) throws MintException {
    //        String description;
    //        String newDescription = word.substring(word.indexOf(NAME_SEPARATOR) + LENGTH_OF_SEPARATOR);
    //        if (!newDescription.trim().equalsIgnoreCase(BLANK)) {
    //            description = newDescription.trim();
    //        } else {
    //            throw new MintException(MintException.ERROR_INVALID_DESCRIPTION);
    //        }
    //        return description;
    //    }
    //
    //    public ArrayList<Expense> getExpenseList() {
    //        return expenseList;
    //    }
}
