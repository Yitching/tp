package seedu.duke.parser;

import seedu.duke.ExpenseCategory;
import seedu.duke.IncomeCategory;
import seedu.duke.MintException;
import seedu.duke.Interval;
import seedu.duke.commands.AddCommand;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.commands.ViewCommand;
import seedu.duke.Entry;
import seedu.duke.Expense;
import seedu.duke.Income;
import seedu.duke.RecurringExpenseList;
import seedu.duke.Ui;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // take note of the blank space,example " n/"
    public static final String userTag = "\\s[a-z]/";
    public static final String userTagRaw = "(.*)\\s[a-z]/(.*)";
    public static final String STRING_EMPTY = "";
    public static final String SEPARATOR = ". ";
    public static final String STRING_INCLUDE = "Please include the following in your command or make them valid: \n";
    public static final String STRING_DESCRIPTION = "Description of item\n";
    public static final String STRING_DATE = "Date of purchase\n";
    public static final String STRING_AMOUNT = "Amount of purchase\n";
    public static final String STRING_CATNUM = "Category number of item\n";
    public static final String STRING_INTERVAL = "Interval of item\n";
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    public static final String CAT_NUM_FOOD = "0";
    public static final String CAT_NUM_OTHERS = "7";
    protected String command;
    protected String name;
    protected String dateStr;
    protected LocalDate date;
    protected String amountStr;
    protected double amount;
    protected String catNumStr;
    protected ExpenseCategory expenseCategory;
    protected IncomeCategory incomeCategory;
    protected String interval;
    protected String endDate;
    protected boolean isRecurring = false;
    protected String[] argumentsArray;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");

    public Parser() {
    }

    private static int indexOfTag(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.start() : -1;
    }

    public String parserExtractCommand(String userInput) {
        return userInput.split(" ", 2)[0];
    }

    public int getCurrentTagIndex(String userInput) {
        int currentTagIndex;
        currentTagIndex = indexOfTag(userInput, userTag);
        return currentTagIndex;
    }

    public String getTagType(String userInput, int currentTagIndex) {
        String tagType;
        tagType = String.valueOf(userInput.charAt(currentTagIndex + 1));
        return tagType;
    }

    public String getDescription(String userInput, int currentTagIndex) {
        String description;
        description = userInput.substring(currentTagIndex + 3).trim();
        return description;
    }

    public String getDescription(String userInput, int currentTagIndex, int nextTagIndex) {
        String description;
        description = userInput.substring(currentTagIndex + 3, nextTagIndex).trim();
        return description;
    }

    public int getNextTagIndex(String userInput, int currentTagIndex) {
        int nextTagIndex;
        nextTagIndex = indexOfTag(userInput.substring(currentTagIndex + 3), userTag) + 3 + currentTagIndex;
        return nextTagIndex;
    }

    public boolean hasNextTag(String userString, int currentTagIndex) {
        return userString.substring(currentTagIndex + 3).matches(userTagRaw);
    }

    private void setAmount(String param) {
        amountStr = param.trim();
    }

    private ExpenseCategory setExpenseCategoryViaCatNum(String catNum) throws MintException {
        switch (catNum) {
        case "0":
            return ExpenseCategory.FOOD;
        case "1":
            return ExpenseCategory.ENTERTAINMENT;
        case "2":
            return ExpenseCategory.TRANSPORTATION;
        case "3":
            return ExpenseCategory.HOUSEHOLD;
        case "4":
            return ExpenseCategory.APPAREL;
        case "5":
            return ExpenseCategory.BEAUTY;
        case "6":
            return ExpenseCategory.GIFT;
        case "7":
            return ExpenseCategory.OTHERS;
        default:
            throw new MintException("Invalid category");
        }
    }

    private IncomeCategory setIncomeCategoryViaCatNum(String catNum) throws MintException {
        switch (catNum) {
        case "0":
            return IncomeCategory.FOOD;
        case "1":
            return IncomeCategory.ENTERTAINMENT;
        case "2":
            return IncomeCategory.TRANSPORTATION;
        case "3":
            return IncomeCategory.HOUSEHOLD;
        case "4":
            return IncomeCategory.APPAREL;
        case "5":
            return IncomeCategory.BEAUTY;
        case "6":
            return IncomeCategory.GIFT;
        case "7":
            return IncomeCategory.OTHERS;
        default:
            throw new MintException("Invalid category");
        }
    }

    private Interval setIntervalViaString(String interval) throws MintException {
        switch (interval) {
        case "MONTHLY":
            return Interval.MONTH;
        case "YEARLY":
            return Interval.YEAR;
        default:
            throw new MintException("Invalid interval");
        }
    }

    private void setFieldsByTag(String description, String tagType) throws MintException {
        switch (tagType) {
        case "n":
            this.name = description;
            break;
        case "d":
            this.dateStr = description;
            break;
        case "a":
            this.amountStr = description;
            break;
        case "c":
            this.catNumStr = description;
            this.expenseCategory = setExpenseCategoryViaCatNum(catNumStr);
            this.incomeCategory = setIncomeCategoryViaCatNum(catNumStr);
            break;
        case "i":
            if (isRecurring) {
                this.interval = description;
            }
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
        }
    }

    private void initDate() {
        this.dateStr = LocalDate.now().toString();
    }

    private void initCatNum() {
        this.catNumStr = CAT_NUM_OTHERS;
    }

    private void initEndDate() {
        this.endDate = "2200-12-31";
    }

    private void parseInputByTagsLoop(String userInput) throws MintException {
        String tagType;
        String description;
        while (userInput.matches(userTagRaw)) {
            int currentTagIndex = getCurrentTagIndex(userInput);
            int nextTagIndex = userInput.length();
            tagType = getTagType(userInput, currentTagIndex);

            if (hasNextTag(userInput, currentTagIndex)) {
                nextTagIndex = getNextTagIndex(userInput, currentTagIndex);
                description = getDescription(userInput, currentTagIndex, nextTagIndex);
            } else {
                description = getDescription(userInput, currentTagIndex);
            }

            setFieldsByTag(description, tagType);
            userInput = userInput.substring(nextTagIndex);
        }
    }

    public ArrayList<String> parseInputByTags(String userInput) throws MintException {
        // for Add, initialise Date to today's date and category to "Others"
        if (command.equals("add")) {
            initDate();
            initCatNum();

        } else if (command.equals("addR")) {
            initDate();
            initCatNum();
            initEndDate();
        }

        parseInputByTagsLoop(userInput);
        return ValidityChecker.checkExistenceAndValidityOfTags(this, userInput);
    }

    public void parserSetLimit(String[] userInput) throws MintException {
        ValidityChecker.checkSetFormat(userInput);
        String[] parameters = getParamsWithoutCommand(userInput);
        for (String param : parameters) {
            if (param.contains("c/")) {
                extractCatNumFromTag(param);
            } else {
                setAmount(param);
            }
        }
    }

    private String[] getParamsWithoutCommand(String[] userInput) {
        return Arrays.copyOfRange(userInput, 1, userInput.length);
    }

    private void extractCatNumFromTag(String param) {
        catNumStr = String.valueOf(param.trim().charAt(2));
    }

    public Entry checkType (String[] argumentsArray) {
        if (Objects.equals(argumentsArray[1], "income")) {
            return createIncomeObject();
        } else {
            return createExpenseObject();
        }
    }

    public void parseInputByArguments(String userInput) {
        argumentsArray = userInput.split(" ");
    }

    private Expense createExpenseObject() {
        date = LocalDate.parse(dateStr);
        amount = Double.parseDouble(amountStr);
        int catNum = Integer.parseInt(catNumStr);
        expenseCategory = ExpenseCategory.values()[catNum];
        return new Expense(name, date, amount, expenseCategory);
    }

    private Income createIncomeObject() {
        date = LocalDate.parse(dateStr);
        amount = Double.parseDouble(amountStr);
        int catNum = Integer.parseInt(catNumStr);
        incomeCategory = IncomeCategory.values()[catNum];
        return new Income(name, date, amount, incomeCategory);
    }

    public int executeCommand(String userInput, ArrayList<Entry> entryList,
                              RecurringExpenseList recurringExpenseList) throws MintException {
        ArrayList<String> validTags;
        Expense expense;
        this.command = parserExtractCommand(userInput);
        try {
            switch (command) {
            case "help":
                Ui.help();
                break;
            // case cat
            case "list":
                //fallthrough
            case "view":
                parseInputByArguments(userInput);
                ViewCommand viewCommand = new ViewCommand();
                viewCommand.view(argumentsArray, recurringExpenseList, entryList);
                break;
            //case limit, breakdown
            case "add":
                parseInputByArguments(userInput);
                parseInputByTags(userInput);
                assert name != null : "Name should not be empty";
                assert amountStr != null : "Amount should not be empty";
                AddCommand addCommand = new AddCommand();
                addCommand.add(checkType(argumentsArray), entryList);
                //expense = createExpenseObject();
                //addCommand.add(expense, entryList);
                break;
            case "delete":
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                expense = createExpenseObject();
                DeleteCommand deleteCommand = new DeleteCommand();
                deleteCommand.deleteByKeywords(validTags, expense, entryList);
                break;
            case "edit":
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                //                expenseList.editExpenseByKeywords(validTags, expense, expenseList);
                EditCommand editCommand = new EditCommand();
                expense = createExpenseObject();
                editCommand.editByKeywords(validTags, expense, entryList);
                break;
            case "addR":
                isRecurring = true;
                parseInputByTags(userInput);
                assert name != null : "Name should not be empty";
                assert amountStr != null : "Amount should not be empty";
                recurringExpenseList.addRecurringExpense(name, dateStr, amountStr,
                        expenseCategory, Interval.valueOf(interval), endDate);
                break;
            case "deleteR":
                isRecurring = true;
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                expense = createExpenseObject();
                recurringExpenseList.deleteRecurringExpenseByKeywords(validTags, expense, interval);
                break;
            case "editR":
                isRecurring = true;
                validTags = parseInputByTags(userInput);
                assert validTags.size() >= 1 : "There should be at least one valid tag";
                expense = createExpenseObject();
                recurringExpenseList.editRecurringExpenseByKeywords(validTags, expense, interval);
                break;
            case "bye":
                //fallthrough
            case "exit":
                Ui.shutdown();
                return -1;
            default:
                isRecurring = false;
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        } catch (MintException e) {
            isRecurring = false;
            System.out.println(e.getMessage());
        }
        isRecurring = false;
        return 0;
    }

}
