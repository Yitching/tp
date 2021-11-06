package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static seedu.duke.parser.ValidityChecker.dateFormatter;

class NormalFinanceManagerTest {
    NormalFinanceManager financeManager = new NormalFinanceManager();
    ArrayList<Entry> entryList = financeManager.entryList;

    @Test
    public void deleteEntry_expense_success() {
        String name = "Cheese";
        LocalDate date = LocalDate.parse("2021-12-23", dateFormatter);
        Double amount = 15.5;
        ExpenseCategory category = ExpenseCategory.FOOD;

        entryList.add(new Expense(name, date, amount, category));
        Expense query = new Expense(name, date, amount, category);
        assertTrue(entryList.contains(query));
        financeManager.deleteEntry(query);
        assertFalse(entryList.contains(query));
    }

    @Test
    public void deleteEntry_income_success() {
        String name = "Salary";
        LocalDate date = LocalDate.parse("2021-12-23", dateFormatter);
        Double amount = 10000.0;
        IncomeCategory category = IncomeCategory.SALARY;

        entryList.add(new Income(name, date, amount, category));
        Income query = new Income(name, date, amount, category);
        assertTrue(entryList.contains(query));
        financeManager.deleteEntry(query);
        assertFalse(entryList.contains(query));
    }

    @Test
    public void filterEntryByKeywords_queryFullName_oneMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry = new Expense("Pear", date1, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("Apple", date2, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        Expense query = new Expense("Pear", date3, 5.0, ExpenseCategory.FOOD);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryFullName_multipleMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry1 = new Expense("pork", date1, 3.9, ExpenseCategory.FOOD);
        Expense targetEntry2 = new Expense("PoRk", date2, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        Expense query = new Expense("pork", date3, 5.0, ExpenseCategory.OTHERS);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(2, filteredList.size());
            assertTrue(filteredList.contains(targetEntry1));
            assertTrue(filteredList.contains(targetEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryFullName_noMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry1 = new Expense("jelly", date1, 3.9, ExpenseCategory.FOOD);
        Expense targetEntry2 = new Expense("worm", date2, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Expense query = new Expense("yoyo", date2, 5.0, ExpenseCategory.FOOD);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(0, filteredList.size());
            assertFalse(filteredList.contains(targetEntry1));
            assertFalse(filteredList.contains(targetEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryNameWithAbbreviation_multipleMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        Expense targetEntry1 = new Expense("Pear", date1, 1.0, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("apple", date2, 200.2, ExpenseCategory.FOOD);
        Income targetEntry2 = new Income("Green pea", date3, 5.0, IncomeCategory.OTHERS);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Expense query = new Expense("peA", date2, 10.0, ExpenseCategory.OTHERS);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(2, filteredList.size());
            assertTrue(filteredList.contains(targetEntry1));
            assertTrue(filteredList.contains(targetEntry2));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryAmount_multipleMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry1 = new Expense("Ice", date1, 3.9, ExpenseCategory.FOOD);
        Expense targetEntry2 = new Expense("yoyo", date2, 3.9, ExpenseCategory.OTHERS);
        entryList.add(targetEntry1);
        entryList.add(targetEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        Expense query = new Expense("eel", date3, 3.9, ExpenseCategory.APPAREL);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(2, filteredList.size());
            assertTrue(filteredList.contains(targetEntry1));
            assertTrue(filteredList.contains(targetEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry1);
        entryList.remove(targetEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryDate_oneMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Income targetEntry = new Income("Salary", date1, 1000.0, IncomeCategory.SALARY);
        Expense dummyEntry = new Expense("banana", date2, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("d/");
        Expense query = new Expense("yoyo", date1, 1.0, ExpenseCategory.OTHERS);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryCategory_oneMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry = new Expense("choco", date1, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("movie", date2, 5.0, ExpenseCategory.ENTERTAINMENT);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("c/");
        LocalDate date3 = LocalDate.parse("2021-11-12", dateFormatter);
        Expense query = new Expense("yoyo", date3, 1.0, ExpenseCategory.FOOD);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void filterEntryByKeywords_queryNameAndDate_oneMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry = new Expense("bread", date1, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry1 = new Expense("kinder", date2, 5.0, ExpenseCategory.FOOD);
        Income dummyEntry2 = new Income("Bread", date2, 5.0, IncomeCategory.OTHERS);
        entryList.add(targetEntry);
        entryList.add(dummyEntry1);
        entryList.add(dummyEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("d/");
        tags.add("n/");
        Expense query = new Expense("Bread", date1, 1.0, ExpenseCategory.OTHERS);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry1));
            assertFalse(filteredList.contains(dummyEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry1);
        entryList.remove(dummyEntry2);
    }

    @Test
    public void filterEntryByKeywords_queryDateAndAmountAndCategory_oneMatchingResult() {
        LocalDate date1 = LocalDate.parse("2021-10-29", dateFormatter);
        LocalDate date2 = LocalDate.parse("2021-11-29", dateFormatter);
        Expense targetEntry = new Expense("bread", date1, 1.0, ExpenseCategory.FOOD);
        Expense dummyEntry1 = new Expense("kinder", date2, 5.0, ExpenseCategory.FOOD);
        Income dummyEntry2 = new Income("Bread", date1, 1.0, IncomeCategory.OTHERS);
        entryList.add(targetEntry);
        entryList.add(dummyEntry1);
        entryList.add(dummyEntry2);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("d/");
        tags.add("c/");
        tags.add("a/");
        Expense query = new Expense("Bread", date1, 1.0, ExpenseCategory.FOOD);
        try {
            ArrayList<Entry> filteredList = financeManager.filterEntryByKeywords(tags, query);
            assertEquals(1, filteredList.size());
            assertTrue(filteredList.contains(targetEntry));
            assertFalse(filteredList.contains(dummyEntry1));
            assertFalse(filteredList.contains(dummyEntry2));
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry1);
        entryList.remove(dummyEntry2);
    }

    /*
    public void deleteEntry_existingItem_success() throws MintException {
        NormalFinanceManager financeManager = new NormalFinanceManager();
        String name = "Cheese";
        LocalDate date = LocalDate.parse("2021-12-23", dateFormatter);
        Double amount = 15.5;
        ExpenseCategory category = ExpenseCategory.FOOD;

        financeManager.entryList.add(new Expense("Cheese", date, amount, category));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        financeManager.deleteEntry(new Expense(name, date, amount, category));
        String expectedOutput  = "I have deleted: Food | 2021-12-23 | Cheese burger | $15.50"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }



    @Test
    public void deleteExpense_wrongAmountFormat_printErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("delete n/Cheese d/2021-12-23 a/15.5abc c/1", expenseList, recurringExpenseList);
        String expectedOutput  = Parser.STRING_INCLUDE + "1. " + Parser.STRING_AMOUNT + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void deleteExpense_wrongDateFormat_printErrorMessage() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
        Parser parser = new Parser();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        parser.executeCommand("delete n/Cheese d/2021-12 a/15.5 c/1", expenseList, recurringExpenseList);
        String expectedOutput  = Parser.STRING_INCLUDE + "1. " + Parser.STRING_DATE + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewExpense_twoExpenses_success() throws MintException {
        CategoryList.initialiseCategories();
        ExpenseList expenseList = new ExpenseList();
        expenseList.expenseList.add(new Expense("Cheese burger", "2021-12-23", "15.5"));
        expenseList.expenseList.add(new Expense("book", "2022-12-23", "9"));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] emptyArray = {"view"};
        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();
        expenseList.viewExpense(emptyArray, recurringExpenseList);
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator()
                + "     Others     | 2021-12-23 | Cheese burger | $15.50" + System.lineSeparator()
                + "     Others     | 2022-12-23 | book | $9.00" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void viewExpense_zeroExpenses_success() throws MintException {
        ExpenseList expenseList = new ExpenseList();
        RecurringExpenseList recurringExpenseList = new RecurringExpenseList();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String[] emptyArray = {"view"};
        expenseList.viewExpense(emptyArray, recurringExpenseList);
        String expectedOutput  = "Here is the list of your expenses:" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

     */
}