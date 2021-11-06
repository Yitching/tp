package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.Budget;
import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

class AddFunctionTest {

    @Test
    void addExpense_allFieldsValid_success() {
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        Double amount = Double.parseDouble("7.50");
        ExpenseCategory category = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        int index = normalFinanceManager.entryList.indexOf(expense);
        assertEquals(expense, normalFinanceManager.entryList.get(index));
    }

    @Test
    void addIncome_allFieldsValid_success() {
        String name = "OnlyFans";
        LocalDate date = LocalDate.parse("2021-06-09", dateFormatter);
        Double amount = Double.parseDouble("69");
        IncomeCategory category = IncomeCategory.COMMISSION;
        Income income = new Income(name, date, amount, category);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        try {
            normalFinanceManager.addEntry(income);
        } catch (MintException e) {
            e.printStackTrace();
        }
        int index = normalFinanceManager.entryList.indexOf(income);
        assertEquals(income, normalFinanceManager.entryList.get(index));
    }
  
    @Test
    void addExpense_largeAmount_warningMessage() {
        BudgetManager budgetManager = new BudgetManager();
        budgetManager.setBudget(ExpenseCategory.FOOD, 100);
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        Expense expense = new Expense("haidilao", LocalDate.now(), 90, ExpenseCategory.FOOD);
        try {
            normalFinanceManager.addEntry(expense);
        } catch (MintException e) {
            e.printStackTrace();
        }
        ArrayList<Entry> entries = normalFinanceManager.getEntryList();
        ArrayList<Entry> recurringEntries = recurringFinanceManager.getCopyOfRecurringEntryList();
        Budget budget = budgetManager.getMonthlyBudgetFromCategory(ExpenseCategory.FOOD);
        double amountSpent = budget.getMonthlySpending(entries, recurringEntries);
        double spendingLimit = budget.getLimit();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Ui ui = new Ui();
        ui.printBudgetWarningMessage(ExpenseCategory.FOOD, amountSpent, spendingLimit);

        String expectedOutput = "Slow down, you've set aside $100.00 for FOOD, but you already spent $90.00.\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}