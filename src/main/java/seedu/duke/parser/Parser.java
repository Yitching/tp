package seedu.duke.parser;


import seedu.duke.commands.AddCommand;
import seedu.duke.commands.AddRecurringCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteAllCommand;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.commands.DeleteRecurringCommand;
import seedu.duke.commands.EditCommand;
import seedu.duke.commands.EditRecurringCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.HelpCommand;
import seedu.duke.commands.InvalidCommand;
import seedu.duke.commands.SetBudgetCommand;
import seedu.duke.commands.ViewBudgetCommand;
import seedu.duke.commands.ViewCategoriesCommand;
import seedu.duke.commands.ViewCommand;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.Type;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.Income;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.exception.MintException;
import seedu.duke.finances.RecurringFinanceManager;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    public static final String STRING_DATE = "Date of purchase or start date of the recurring period\n";
    public static final String STRING_AMOUNT = "Amount (Valid positive number below 1 million)\n";
    public static final String STRING_CATNUM = "Category number (0 to 7)\n";
    public static final String STRING_INTERVAL = "Interval of item (month or year in case-insensitive format e.g.,"
            + " mOnTh, year, MONTH)\n";
    public static final String STRING_END_DATE = "End date of the recurring period\n";
    public static final String ERROR_INVALID_CATNUM = "Please enter a valid category number! c/0 to c/7";
    public static final int CAT_NUM_FOOD_INT = 0;
    public static final int CAT_NUM_OTHERS_INT = 7;
    public static final String CAT_NUM_FOOD = "0";
    public static final String CAT_NUM_OTHERS = "7";
    public static final String ADD_ENTRY = "add";
    public static final String ADD_RECURRING = "addR";
    public static final String DELETE_ENTRY = "delete";
    public static final String DELETE_RECURRING = "deleteR";
    public static final String EDIT_ENTRY = "edit";
    public static final String VIEW = "view";
    public static final String VIEW_ALL_CATEGORIES = "cat";
    public static final String EDIT_RECURRING = "editR";
    public static final String SET_BUDGET = "set";
    public static final String VIEW_BUDGET = "budget";
    public static final String DELETEALL = "deleteAll";
    public static final String DELETEALL2 = "deleteall";
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    private static final String ERROR_MISSING_PARAMS = "Seems like you forgot to include your tags";
    protected String command;
    protected String name;
    protected String dateStr;
    protected LocalDate date;
    protected String amountStr;
    protected double amount;
    protected String catNumStr;
    protected ExpenseCategory expenseCategory;
    protected IncomeCategory incomeCategory;
    protected String intervalStr;
    protected Interval interval;
    protected String endDateStr;
    protected LocalDate endDate;
    protected Type type;
    protected RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
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

    private void setAmountViaAmountStr(String amountStr) {
        this.amount = Double.parseDouble(amountStr);
    }

    private ExpenseCategory setCategoryViaCatNum(String catNum) throws MintException {
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
            throw new MintException(ERROR_INVALID_CATNUM);
        }
    }

    private IncomeCategory setIncomeCategoryViaCatNum(String catNum) throws MintException {
        switch (catNum) {
        case "0":
            return IncomeCategory.ALLOWANCE;
        case "1":
            return IncomeCategory.WAGES;
        case "2":
            return IncomeCategory.SALARY;
        case "3":
            return IncomeCategory.INTEREST;
        case "4":
            return IncomeCategory.INVESTMENT;
        case "5":
            return IncomeCategory.COMMISSION;
        case "6":
            return IncomeCategory.GIFT;
        case "7":
            return IncomeCategory.OTHERS;
        default:
            throw new MintException(ERROR_INVALID_CATNUM);
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
            this.catNumStr = description.split(" ",2) [0];
            this.expenseCategory = setCategoryViaCatNum(catNumStr);
            this.incomeCategory = setIncomeCategoryViaCatNum(catNumStr);
            break;
        case "i":
            if (isRecurring) {
                this.intervalStr = description;
            } else {
                throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
            }
            break;
        case "e":
            if (isRecurring) {
                this.endDateStr = description;
            } else {
                throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
            }
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
        }
    }

    private void initDateStr() {
        this.dateStr = LocalDate.now().toString();
    }

    private void initAmountStr() {
        this.amountStr = "0";
    }

    private void initIntervalStr() {
        this.intervalStr = "MONTH";
    }

    private void initCatNumStr() {
        this.catNumStr = CAT_NUM_OTHERS;
    }

    private void initEndDateStr() {
        this.endDateStr = "2200-12-31";
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
        try {
            ValidityChecker.checkTagsFormatSpacing(userInput);
            ValidityChecker.identifyDuplicateTags(this, userInput);
            parseType(userInput);
            parseInputByTagsLoop(userInput);
            ArrayList<String> validTags = ValidityChecker.checkExistenceAndValidityOfTags(this, userInput);
            isRecurring = false;
            return validTags;
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public void parseType(String userInput) throws MintException {
        parseInputByArguments(userInput);
        if (argumentsArray.length > 1 && Objects.equals(argumentsArray[1], "income")) {
            type = Type.Income;
        } else {
            type = Type.Expense;
        }
    }

    public Entry checkType() {
        if (Objects.equals(argumentsArray[1], "income")) {
            return createIncomeObject();
        } else {
            return createExpenseObject();
        }
    }

    public void parseInputByArguments(String userInput) {
        argumentsArray = userInput.split("\\s+");
    }


    private Expense createExpenseObject() {
        date = LocalDate.parse(dateStr, dateFormatter);
        amount = Double.parseDouble(amountStr);
        int catNum = Integer.parseInt(catNumStr);
        expenseCategory = ExpenseCategory.values()[catNum];
        return new Expense(name, date, amount, expenseCategory);
    }

    private Income createIncomeObject() {
        date = LocalDate.parse(dateStr, dateFormatter);
        amount = Double.parseDouble(amountStr);
        int catNum = Integer.parseInt(catNumStr);
        incomeCategory = IncomeCategory.values()[catNum];
        return new Income(name, date, amount, incomeCategory);
    }

    private RecurringExpense createRecurringExpenseObject() throws MintException {
        try {
            date = LocalDate.parse(dateStr, dateFormatter);
            amount = Double.parseDouble(amountStr);
            int catNum = Integer.parseInt(catNumStr);
            expenseCategory = ExpenseCategory.values()[catNum];
            interval = Interval.determineInterval(intervalStr);
            endDate = LocalDate.parse(endDateStr, dateFormatter);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return new RecurringExpense(name, date, amount, expenseCategory, interval, endDate);
    }

    private RecurringIncome createRecurringIncomeObject() throws MintException {
        try {
            date = LocalDate.parse(dateStr, dateFormatter);
            amount = Double.parseDouble(amountStr);
            int catNum = Integer.parseInt(catNumStr);
            incomeCategory = IncomeCategory.values()[catNum];
            interval = Interval.determineInterval(intervalStr);
            endDate = LocalDate.parse(endDateStr, dateFormatter);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return new RecurringIncome(name, date, amount, incomeCategory, interval, endDate);
    }

    private Command prepareAddEntry(String userInput) {
        try {
            initDateStr();
            initCatNumStr();
            parseInputByTags(userInput);
            Entry entry = (type == Type.Income) ? createIncomeObject() : createExpenseObject();
            return new AddCommand(entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareDeleteEntry(String userInput) {
        try {
            initDateStr();
            initCatNumStr();
            initAmountStr();
            initIntervalStr();
            initEndDateStr();
            ArrayList<String> validTags = parseInputByTags(userInput);
            if (argumentsArray.length <= 1) {
                throw new MintException(MintException.ERROR_NO_DELIMETER);
            }
            Entry entry = (type == Type.Income) ? createIncomeObject() : createExpenseObject();
            assert Objects.requireNonNull(validTags).size() >= 1 : "There should be at least one valid tag";
            return new DeleteCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareView(String userInput) {
        try {
            parseInputByArguments(userInput);
            ViewOptions viewOptions = new ViewOptions(argumentsArray);
            return new ViewCommand(viewOptions);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareEditEntry(String userInput) {
        try {
            initDateStr();
            initCatNumStr();
            initAmountStr();
            initIntervalStr();
            initEndDateStr();
            ArrayList<String> validTags = parseInputByTags(userInput);
            Entry entry = (type == Type.Income) ? createIncomeObject() : createExpenseObject();
            assert validTags.size() >= 1 : "There should be at least one valid tag";
            return new EditCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareAddRecurringEntry(String userInput) {
        try {
            initDateStr();
            initCatNumStr();
            initEndDateStr();
            isRecurring = true;
            parseInputByTags(userInput);
            RecurringEntry entry = (type == Type.Income)
                    ? createRecurringIncomeObject() : createRecurringExpenseObject();
            return new AddRecurringCommand(entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareDeleteRecurringEntry(String userInput) {
        try {
            initDateStr();
            initCatNumStr();
            initAmountStr();
            initIntervalStr();
            initEndDateStr();
            isRecurring = true;
            ArrayList<String> validTags = parseInputByTags(userInput);
            if (argumentsArray.length <= 1) {
                throw new MintException(MintException.ERROR_NO_DELIMETER);
            }
            RecurringEntry entry = (type == Type.Income)
                    ? createRecurringIncomeObject() : createRecurringExpenseObject();
            return new DeleteRecurringCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareEditRecurringEntry(String userInput) {
        try {
            initDateStr();
            initCatNumStr();
            initAmountStr();
            initIntervalStr();
            initEndDateStr();
            isRecurring = true;
            ArrayList<String> validTags = parseInputByTags(userInput);
            assert validTags.size() >= 1 : "There should be at least one valid tag";
            RecurringEntry entry = (type == Type.Income)
                    ? createRecurringIncomeObject() : createRecurringExpenseObject();
            return new EditRecurringCommand(validTags, entry);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    private Command prepareSetBudget(String userInput) {
        try {
            parseInputByTags(userInput);
            setCategoryViaCatNum(catNumStr);
            setAmountViaAmountStr(amountStr);
            return new SetBudgetCommand(this.expenseCategory, this.amount);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }

    public HashMap<String, String> prepareRecurringEntryToAmendForEdit(Entry entry) {
        RecurringEntry recurringEntry = (RecurringEntry) entry;
        String name = recurringEntry.getName();
        LocalDate date = recurringEntry.getDate();
        double amount = recurringEntry.getAmount();
        LocalDate endDate = recurringEntry.getEndDate();
        Interval interval = recurringEntry.getInterval();
        String dateStr = date.toString();
        String amountStr = Double.toString(amount);
        String endDateStr = endDate.toString();
        String intervalStr = interval.toString();
        String catNumStr = String.valueOf(entry.getCategory().ordinal());
        String[] entryFieldsToAdd = {name, dateStr, amountStr, endDateStr, intervalStr, catNumStr};
        String[] entryFieldKeys = {"name", "date", "amount", "endDate", "interval", "catNum"};
        HashMap<String, String> entryFields = new HashMap<>();
        for (int index = 0; index < entryFieldsToAdd.length; index++) {
            entryFields.put(entryFieldKeys[index], entryFieldsToAdd[index]);
        }
        return entryFields;
    }

    public HashMap<String, String> prepareEntryToAmendForEdit(Entry entry) {
        String name = entry.getName();
        LocalDate date = entry.getDate();
        double amount = entry.getAmount();
        String catNumStr = String.valueOf(entry.getCategory().ordinal());
        String dateStr = date.toString();
        String amountStr = Double.toString(amount);
        String[] entryFieldsToAdd = {name, dateStr, amountStr, catNumStr};
        String[] entryFieldKeys = {"name", "date", "amount", "catNum"};
        HashMap<String, String> entryFields = new HashMap<>();
        for (int index = 0; index < entryFieldsToAdd.length; index++) {
            entryFields.put(entryFieldKeys[index], entryFieldsToAdd[index]);
        }
        return entryFields;
    }

    public RecurringEntry convertRecurringEntryToRespectiveTypes(HashMap<String, String> entryFields,
                                                                   Type type) throws MintException {
        String name = entryFields.get("name");
        String dateStr = entryFields.get("date");
        String amountStr = entryFields.get("amount");
        String catNumStr = entryFields.get("catNum");
        String intervalStr = entryFields.get("interval");
        String endDateStr = entryFields.get("endDate");

        LocalDate date = LocalDate.parse(dateStr, ValidityChecker.dateFormatter);
        double amount = Double.parseDouble(amountStr);
        LocalDate endDate = LocalDate.parse(endDateStr, ValidityChecker.dateFormatter);
        int pos = Integer.parseInt(catNumStr);
        ValidityChecker.checkValidCatNum(pos);
        Enum category = type == Type.Expense ? ExpenseCategory.values()[pos] : IncomeCategory.values()[pos];
        Interval interval = Interval.determineInterval(intervalStr);
        if (type == Type.Expense) {
            return new RecurringExpense(name, date, amount, (ExpenseCategory) category, interval, endDate);
        } else {
            return new RecurringIncome(name, date, amount, (IncomeCategory) category, interval, endDate);
        }
    }

    public Entry convertEntryToRespectiveTypes(HashMap<String, String> entryFields,
                                                                 Type type) throws MintException {
        String name = entryFields.get("name");
        String dateStr = entryFields.get("date");
        String amountStr = entryFields.get("amount");
        String catNumStr = entryFields.get("catNum");

        LocalDate date = LocalDate.parse(dateStr, ValidityChecker.dateFormatter);
        double amount = Double.parseDouble(amountStr);
        int pos = Integer.parseInt(catNumStr);
        ValidityChecker.checkValidCatNum(pos);
        Enum category = type == Type.Expense ? ExpenseCategory.values()[pos] : IncomeCategory.values()[pos];
        if (type == Type.Expense) {
            return new Expense(name, date, amount, (ExpenseCategory) category);
        } else {
            return new Income(name, date, amount, (IncomeCategory) category);
        }
    }

    public Command prepareDeleteAll(String userInput) {
        try {
            parseInputByArguments(userInput);
            if (Objects.equals(argumentsArray[1], "n") || Objects.equals(argumentsArray[1], "normal")) {
                return new DeleteAllCommand(true, false);
            } else if (Objects.equals(argumentsArray[1], "r") || Objects.equals(argumentsArray[1], "recurring")) {
                return new DeleteAllCommand(false, true);
            } else {
                throw new MintException(MintException.ERROR_NO_DELIMETER);
            }
        } catch (IndexOutOfBoundsException e) {
            return new DeleteAllCommand(true, true);
        } catch (MintException e) {
            return new InvalidCommand(e.getMessage());
        }
    }


    public Command parseCommand(String userInput) {
        this.command = parserExtractCommand(userInput);
        switch (command) {
        case ADD_ENTRY:
            return prepareAddEntry(userInput);
        case DELETE_ENTRY:
            return prepareDeleteEntry(userInput);
        case EDIT_ENTRY:
            return prepareEditEntry(userInput);
        case ADD_RECURRING:
            return prepareAddRecurringEntry(userInput);
        case DELETE_RECURRING:
            return prepareDeleteRecurringEntry(userInput);
        case EDIT_RECURRING:
            return prepareEditRecurringEntry(userInput);
        case SET_BUDGET:
            return prepareSetBudget(userInput);
        case VIEW_BUDGET:
            return new ViewBudgetCommand();
        case DELETEALL:
            //fallthrough
        case DELETEALL2:
            return prepareDeleteAll(userInput);
        case VIEW:
            return prepareView(userInput);
        case VIEW_ALL_CATEGORIES:
            return new ViewCategoriesCommand();
        case HELP:
            return new HelpCommand();
        case EXIT:
            return new ExitCommand();
        default:
            return new InvalidCommand(MintException.ERROR_INVALID_COMMAND);
        }
    }
}