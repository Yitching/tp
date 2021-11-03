package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.exception.MintException;
import seedu.duke.finances.RecurringFinanceManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

public class EditRecurringExpenseTest {
    @Test
    void editRecurringExpense_editOneFieldValid_success() {
        String input = "n/TESTING";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        RecurringExpense recurringExpense = new RecurringExpense(name, date, amount, category, interval, endDate);
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        try {
            recurringFinanceManager.addEntry(recurringExpense);
            index = recurringFinanceManager.recurringEntryList.indexOf(recurringExpense);
            recurringFinanceManager.editEntry(recurringExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense | FOOD | 2021-02-01 | TESTING |-$7.50 | MONTH | 2021-12-12",
                recurringFinanceManager.recurringEntryList.get(index).toString());
    }

    @Test
    void editRecurringExpense_editTwoFieldsValid_success() {
        String input = "n/TESTING d/2020-12-12";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        RecurringExpense recurringExpense = new RecurringExpense(name, date, amount, category, interval, endDate);
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        try {
            recurringFinanceManager.addEntry(recurringExpense);
            index = recurringFinanceManager.recurringEntryList.indexOf(recurringExpense);
            recurringFinanceManager.editEntry(recurringExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense | FOOD | 2020-12-12 | TESTING |-$7.50 | MONTH | 2021-12-12",
                recurringFinanceManager.recurringEntryList.get(index).toString());
    }

    @Test
    void editRecurringExpense_editAllFieldsValid_success() {
        String input = "n/TESTING d/2020-12-12 c/7 a/99999 i/YEAR e/2021-02-19";
        int index = 0;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Interval interval = Interval.MONTH;
        LocalDate endDate = LocalDate.parse("2021-12-12", dateFormatter);
        RecurringExpense recurringExpense = new RecurringExpense(name, date, amount, category, interval, endDate);
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        try {
            recurringFinanceManager.addEntry(recurringExpense);
            index = recurringFinanceManager.recurringEntryList.indexOf(recurringExpense);
            recurringFinanceManager.editEntry(recurringExpense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        assertEquals("Expense | OTHERS | 2020-12-12 | TESTING |-$99,999.00 | YEAR | 2021-02-19",
                recurringFinanceManager.recurringEntryList.get(index).toString());
    }

}
