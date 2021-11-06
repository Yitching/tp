package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.budget.Budget;
import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetBudgetTest {

    @Test
    void setFoodBudget_AllFieldsValid_success() {
        BudgetManager budgetManager = new BudgetManager();
        ExpenseCategory category = ExpenseCategory.FOOD;
        double amount = 1000.00;
        budgetManager.setBudget(category, amount);
        Budget foodBudget = budgetManager.getMonthlyBudgetFromCategory(category);
        assertEquals(amount, foodBudget.getLimit());
    }

    @Test
    void setMultiple_AllFieldsValid_printCorrectUi() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BudgetManager budgetManager = new BudgetManager();
        NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
        RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
        Ui ui = new Ui();
        budgetManager.setBudget(ExpenseCategory.FOOD, 100);
        budgetManager.setBudget(ExpenseCategory.ENTERTAINMENT, 200);
        ui.printBudgetBreakdown(budgetManager.getBudgetList(), normalFinanceManager.getEntryList(),
                recurringFinanceManager.getCopyOfRecurringEntryList());
        String expectedOutput = "Here is the budget for NOVEMBER 2021" + System.lineSeparator()
                + "   Category    | Amount | Budget  | Percentage" + System.lineSeparator()
                + "     FOOD      |  $0.00 / $100.00 | " + System.lineSeparator()
                + "ENTERTAINMENT  |  $0.00 / $200.00 | " + System.lineSeparator()
                + "TRANSPORTATION |  $0.00 / Not set | " + System.lineSeparator()
                + "  HOUSEHOLD    |  $0.00 / Not set | " + System.lineSeparator()
                + "   APPAREL     |  $0.00 / Not set | " + System.lineSeparator()
                + "    BEAUTY     |  $0.00 / Not set | " + System.lineSeparator()
                + "     GIFT      |  $0.00 / Not set | " + System.lineSeparator()
                + "    OTHERS     |  $0.00 / Not set | " + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

}
