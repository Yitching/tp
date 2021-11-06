package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

class EditExpenseTest {

    @Test
    void editExpense_editOneFieldValid_success() {
        String input = "n/TESTING";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
            index = normalFinanceManager.entryList.indexOf(expense);
            normalFinanceManager.editEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense  | FOOD | 2021-02-01 | TESTING | $7.50",
                normalFinanceManager.entryList.get(index).toString());
    }

    @Test
    void editExpense_editTwoFieldsValid_success() {
        String input = "n/TESTING a/99999";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
            index = normalFinanceManager.entryList.indexOf(expense);
            normalFinanceManager.editEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense  | FOOD | 2021-02-01 | TESTING | $99,999.00",
                normalFinanceManager.entryList.get(index).toString());
    }

    @Test
    void editExpense_editAllFieldsValid_success() {
        String input = "n/TESTING a/99999 d/2021-12-12 c/7";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
            index = normalFinanceManager.entryList.indexOf(expense);
            normalFinanceManager.editEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense  | OTHERS | 2021-12-12 | TESTING | $99,999.00",
                normalFinanceManager.entryList.get(index).toString());
    }

    //    @Test
    //    public void editExpense_invalidCategoryNumber_expectFailure() {
    //        CategoryList.initialiseCategories();
    //        String input = "c/1000";
    //        InputStream in = new ByteArrayInputStream(input.getBytes());
    //        System.setIn(in);
    //        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //        System.setOut(new PrintStream(outContent));
    //        ExpenseList expenseList = new ExpenseList();
    //        expenseList.addExpense("Movie", "2021-12-23", "40", "1");
    //        Exception exception = assertThrows(MintException.class,
    //            () -> expenseList.editExpense("Movie", "2021-12-23", "40", "1"));
    //        String expectedMessage = "Please enter a valid category number! c/0 to c/7";
    //        String actualMessage = exception.getMessage();
    //        assertTrue(actualMessage.contains(expectedMessage));
    //    }
    //
}