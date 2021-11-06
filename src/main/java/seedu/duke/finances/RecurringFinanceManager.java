package seedu.duke.finances;

import seedu.duke.entries.Entry;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.parser.Parser;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.parser.ViewOptions;
import seedu.duke.utility.Filter;
import seedu.duke.utility.Ui;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RecurringFinanceManager extends FinanceManager {
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static final String END_DATE_SEPARATOR = "e/";
    public static final String INTERVAL_SEPARATOR = "i/";
    public ArrayList<Entry> recurringEntryList = new ArrayList<>();
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";


    public void addEntry(Entry entry) throws MintException {
        recurringEntryList.add(entry);
    }

    @Override
    public ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags,
                                                  Entry query) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(recurringEntryList);
        RecurringEntry queryToSearch = (RecurringEntry) query;
        for (String tag : tags) {
            switch (tag.trim()) {
            case "n/":
                filteredList = Filter.filterEntryByName(queryToSearch.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(queryToSearch.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(queryToSearch.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(queryToSearch.getCategory().ordinal(), filteredList);
                break;
            case "i/":
                filteredList = Filter.filterEntryByInterval(queryToSearch.getInterval().label, filteredList);
                break;
            case "e/":
                filteredList = Filter.filterEntryByEndDate(queryToSearch.getEndDate(), filteredList);
                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    @Override
    public void deleteEntry(Entry entry) {
        assert recurringEntryList.contains(entry) : "recurringEntryList should contain the entry to delete.";
        logger.log(Level.INFO, "User deleted recurring entry: " + entry);
        recurringEntryList.remove(entry);
    }

    @Override
    public ArrayList<String> editEntry(Entry entry) throws MintException {
        String choice;
        int indexToBeChanged = 0;
        String originalEntryStr = "";
        originalEntryStr = overWriteString((RecurringEntry) entry);
        if (recurringEntryList.contains(entry)) {
            indexToBeChanged = recurringEntryList.indexOf(entry);
            choice = scanFieldsToUpdate();
        } else {
            //                logger.log(Level.INFO, "User entered invalid entry");
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST); // to link to exception class
        }
        ValidityChecker.checkTagsFormatSpacing(choice);
        editSpecifiedEntry(choice, indexToBeChanged, entry);
        String newEntryStr = overWriteString((RecurringEntry) recurringEntryList.get(indexToBeChanged));
        Ui.printOutcomeOfEditAttempt();
        return new ArrayList<>(Arrays.asList(originalEntryStr, newEntryStr));
    }

    public void amendEntry(int index, ArrayList<String> choice, Entry entry) throws MintException {
        try {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            Parser parser = new Parser();
            HashMap<String, String> entryFields = parser.prepareRecurringEntryToAmendForEdit(entry);
            Type type = recurringEntry.getType();
            int count = 0;
            for (String word : choice) {
                assert (word != null);
                if (word.contains(NAME_SEPARATOR)) {
                    String name = nonEmptyNewDescription(word);
                    entryFields.put("name", name);
                    count++;
                } else if (word.contains(DATE_SEPARATOR)) {
                    String dateStr = word.substring(word.indexOf(DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("date", dateStr);
                    count++;
                } else if (word.contains(AMOUNT_SEPARATOR)) {
                    String amountStr = word.substring(word.indexOf(AMOUNT_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("amount",amountStr);
                    count++;
                } else if (word.contains(CATEGORY_SEPARATOR)) {
                    String catNumStr = word.substring(word.indexOf(CATEGORY_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("catNum", catNumStr);
                    count++;
                } else if (word.contains(END_DATE_SEPARATOR)) {
                    String endDateStr = word.substring(word.indexOf(END_DATE_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("endDate", endDateStr);
                    count++;
                } else if (word.contains(INTERVAL_SEPARATOR)) {
                    String intervalStr = word.substring(word.indexOf(INTERVAL_SEPARATOR) + LENGTH_OF_SEPARATOR).trim();
                    entryFields.put("interval", intervalStr);
                    count++;
                }
            }
            if (count == 0) {
                throw new MintException("No Valid Fields Entered!");
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
        String intervalStr = entryFields.get("interval");
        String endDateStr = entryFields.get("endDate");

        ValidityChecker.checkValidityOfFieldsInNormalListTxt("expense", name, dateStr, amountStr, catNumStr);
        ValidityChecker.checkValidityOfFieldsInRecurringListTxt(intervalStr, endDateStr);
        RecurringEntry recurringEntry = parser.convertRecurringEntryToRespectiveTypes(entryFields, type);
        recurringEntryList.set(index, recurringEntry);
    }

    public String getStringToUpdate(int index) {
        Entry entry = recurringEntryList.get(index);
        RecurringEntry recurringEntry = (RecurringEntry) entry;
        return recurringEntry.getType() + "|" + recurringEntry.getCategory().ordinal()
                + "|" + recurringEntry.getDate() + "|" + recurringEntry.getName()
                + "|" + recurringEntry.getAmount() + "|" + recurringEntry.getInterval()
                + "|" + recurringEntry.getEndDate();
    }

    public static String overWriteString(RecurringEntry entry) {
        return entry.getType() + "|" + entry.getCategory().ordinal() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount() + "|" + entry.getInterval() + "|" + entry.getEndDate();
    }

    public ArrayList<Entry> getCopyOfRecurringEntryList() {
        ArrayList<Entry> outputArray;
        outputArray = new ArrayList<>(recurringEntryList);
        return outputArray;
    }

    public RecurringEntry createRecurringEntryObject(RecurringEntry entry) {
        if (entry.getType() == Type.Expense) {
            return new RecurringExpense((RecurringExpense) entry);
        } else {
            return new RecurringIncome((RecurringIncome) entry);
        }
    }

    public LocalDate createLocalDateWithYearMonth(YearMonth yearMonth, int day) {
        assert day >= 1 && day <= 31 : "Day should be within 1 - 31";
        String dateToString = yearMonth.toString() + "-" + day;
        return LocalDate.parse(dateToString, Parser.dateFormatter);
    }

    public LocalDate createLocalDateWithIndividualValues(int year, int month, int day) {
        assert month >= 1 && month <= 12 : "Month should be within 1 - 12";
        assert day >= 1 && day <= 31 : "Day should be within 1 - 31";
        String dateToString = year + "-" + month + "-" + day;
        return LocalDate.parse(dateToString, Parser.dateFormatter);
    }

    public void appendEntryByMonth(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            int month, int year) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(entry.getDate());
            YearMonth currentYM = YearMonth.of(year, month);
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryByMonth(entryList, rawRecurringList, recurringEntry, currentYM);
                break;
            case YEAR:
                appendYearlyEntryByMonth(entryList, rawRecurringList, recurringEntry, startYM, currentYM);
                break;
            default:
                break;
            }
        }
    }

    public void appendMonthlyEntryByMonth(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, YearMonth currentYM) {
        LocalDate startDate = entry.getDate();
        LocalDate endDate = entry.getEndDate();
        int recurringDay = startDate.getDayOfMonth();
        LocalDate currentDate = createLocalDateWithYearMonth(currentYM, recurringDay);
        boolean isBetween = startDate.compareTo(currentDate) <= 0
                && currentDate.compareTo(endDate) <= 0;
        if (isBetween) {
            RecurringEntry newExpense =  createRecurringEntryObject(entry);
            newExpense.setDate(currentDate);
            entryList.add(newExpense);
            rawRecurringList.add(entry);
        }
    }

    public void appendYearlyEntryByMonth(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, YearMonth startYM, YearMonth currentYM) {
        boolean isSameMonthAsStart = startYM.getMonth() == currentYM.getMonth();
        if (isSameMonthAsStart) {
            appendMonthlyEntryByMonth(entryList, rawRecurringList, entry, currentYM);
        }
    }

    public void appendEntryByYear(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList, int year) {
        for (Entry entry: recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            YearMonth startYM = YearMonth.from(entry.getDate());
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryByYear(entryList, rawRecurringList, recurringEntry, year);
                break;
            case YEAR:
                appendYearlyEntryByYear(entryList, rawRecurringList, recurringEntry, startYM, year);
                break;
            default:
                break;
            }
        }
    }

    public void appendMonthlyEntryByYear(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, int currentY) {
        YearMonth iteratorYM = YearMonth.of(currentY, Month.JANUARY);
        YearMonth endLoopYM = YearMonth.of(currentY, Month.DECEMBER);
        ArrayList<Entry> dummyList = new ArrayList<>();
        boolean isAdded = false;
        while (iteratorYM.compareTo(endLoopYM) <= 0) {
            appendMonthlyEntryByMonth(entryList, dummyList, entry, iteratorYM);
            if (dummyList.size() > 0) {
                isAdded = true;
                dummyList.remove(0);
            }
            iteratorYM = iteratorYM.plusMonths(1);
        }

        if (isAdded) {
            rawRecurringList.add(entry);
        }
    }

    public void appendYearlyEntryByYear(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, YearMonth startYM,
            int currentY) {
        YearMonth currentYM = YearMonth.of(currentY, startYM.getMonthValue());
        appendYearlyEntryByMonth(entryList, rawRecurringList, entry, startYM, currentYM);
    }

    public void appendEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            LocalDate startDate, LocalDate endDate) {
        for (Entry entry : recurringEntryList) {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            LocalDate startRecurringDate = entry.getDate();
            LocalDate endRecurringDate = entry.getEndDate();
            switch (recurringEntry.getInterval()) {
            case MONTH:
                appendMonthlyEntryBetweenTwoDates(entryList, rawRecurringList, recurringEntry, startDate, endDate,
                        startRecurringDate, endRecurringDate);
                break;
            case YEAR:
                appendYearlyEntryBetweenTwoDates(entryList, rawRecurringList, recurringEntry, startDate, endDate,
                        startRecurringDate, endRecurringDate);
                break;
            default:
                break;
            }
        }
    }

    public void appendMonthlyEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, LocalDate startDate, LocalDate endDate,
            LocalDate startRecurringDate, LocalDate endRecurringDate) {

        YearMonth endRecurringYM = YearMonth.from(endRecurringDate);
        YearMonth endYM = YearMonth.from(endDate);
        YearMonth iteratorYM = YearMonth.from(startRecurringDate);
        YearMonth endLoopYM = endYM.isBefore(endRecurringYM) ? endYM : endRecurringYM;
        int recurringDay = entry.getDate().getDayOfMonth();
        boolean isAdded = false;

        while (iteratorYM.compareTo(endLoopYM) <= 0) {
            LocalDate currentDate = createLocalDateWithYearMonth(iteratorYM, recurringDay);
            boolean isBetween = isBetweenQueryAndRecurring(currentDate, startDate, endDate, startRecurringDate,
                    endRecurringDate);
            if (isBetween) {
                RecurringEntry newExpense =  createRecurringEntryObject(entry);
                newExpense.setDate(currentDate);
                entryList.add(newExpense);
                isAdded = true;
            }
            iteratorYM = iteratorYM.plusMonths(1);
        }

        if (isAdded) {
            rawRecurringList.add(entry);
        }
    }

    public boolean isBetweenQueryAndRecurring(LocalDate currentDate, LocalDate startDate, LocalDate endDate,
            LocalDate startRecurringDate, LocalDate endRecurringDate) {
        boolean isBetweenQuery = startDate.compareTo(currentDate) <= 0
                && currentDate.compareTo(endDate) <= 0;
        boolean isBetweenRecurringPeriod = startRecurringDate.compareTo(currentDate) <= 0
                && currentDate.compareTo(endRecurringDate) <= 0;
        return isBetweenQuery && isBetweenRecurringPeriod;
    }

    public void appendYearlyEntryBetweenTwoDates(ArrayList<Entry> entryList, ArrayList<Entry> rawRecurringList,
            RecurringEntry entry, LocalDate startDate, LocalDate endDate,
            LocalDate startRecurringDate, LocalDate endRecurringDate) {

        int startRecurringYear = startRecurringDate.getYear();
        int startRecurringMonth = startRecurringDate.getMonthValue();
        int startRecurringDay = startRecurringDate.getDayOfMonth();
        int endRecurringYear = entry.getEndDate().getYear();
        int endYear = endDate.getYear();
        boolean isAdded = false;

        int effectiveEndYear = Math.min(endRecurringYear, endYear);
        for (int i = startRecurringYear; i <= effectiveEndYear; i++) {
            LocalDate currentDate = createLocalDateWithIndividualValues(i, startRecurringMonth, startRecurringDay);
            boolean isBetween = isBetweenQueryAndRecurring(currentDate, startDate, endDate, startRecurringDate,
                    endRecurringDate);

            if (isBetween) {
                RecurringEntry newExpense = createRecurringEntryObject(entry);
                newExpense.setDate(currentDate);
                entryList.add(newExpense);
                isAdded = true;
            }
        }

        if (isAdded) {
            rawRecurringList.add(entry);
        }
    }

    public ArrayList<Entry> appendEntryForView(ViewOptions viewOptions, ArrayList<Entry> entryList,
            ArrayList<Entry> recurringOnlyList) {
        if (viewOptions.fromDate != null) {
            appendEntryBetweenTwoDates(entryList, recurringOnlyList, viewOptions.fromDate, viewOptions.endDate);
        } else if (viewOptions.isViewAll) {
            appendAllEntry(entryList);
            recurringOnlyList.addAll(recurringEntryList);
        } else {
            if (viewOptions.month == null) {
                appendEntryByYear(entryList, recurringOnlyList, viewOptions.year);
            } else {
                appendEntryByMonth(entryList, recurringOnlyList, viewOptions.month.getValue(), viewOptions.year);
            }
        }
        return entryList;
    }

    public void appendAllEntry(ArrayList<Entry> entryList) {
        LocalDate earliestDate = LocalDate.now();
        for (Entry recurringExpense : recurringEntryList) {
            if (recurringExpense.getDate().isBefore(earliestDate)) {
                earliestDate = recurringExpense.getDate();
            }
        }
        ArrayList<Entry> dummyList = new ArrayList<>();
        appendEntryBetweenTwoDates(entryList, dummyList, earliestDate, LocalDate.now());
    }

    public void deleteAll() {
        recurringEntryList.clear();
    }
}
