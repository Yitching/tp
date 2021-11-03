package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import java.time.LocalDate;
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
}