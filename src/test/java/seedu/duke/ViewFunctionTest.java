package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.commands.ViewCommand;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Interval;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.parser.ViewOptions;
import seedu.duke.utility.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.parser.ValidityChecker.dateFormatter;

class ViewFunctionTest {

    @Test
    void view_allFieldsValid_success() {
        ArrayList<Entry> outputArray = new ArrayList<>();
        String name = "Samurai Burger";
        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
        double amount = Double.parseDouble("7.50");
        ExpenseCategory categoryE = ExpenseCategory.FOOD;
        Expense expense = new Expense(name, date, amount, categoryE);
        outputArray.add(expense);

        name = "Lottery";
        date = LocalDate.parse("2015-12-15", dateFormatter);
        amount = Double.parseDouble("250000");
        IncomeCategory categoryI = IncomeCategory.GIFT;
        Income income = new Income(name, date, amount, categoryI);
        outputArray.add(income);

        ArrayList<Entry> recurringOutputArray = new ArrayList<>();
        try {
            String[] argumentArray = {"view"};
            ViewOptions viewOptions = new ViewOptions(argumentArray);
            ViewCommand view = new ViewCommand(viewOptions);
            Ui ui = new Ui();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));
            view.view(outputArray,recurringOutputArray, ui, viewOptions.isViewAll);
            String expectedOutput = "Here is the list of your entries:" + System.lineSeparator()
                    + "  Type  | Category |    Date    |      Name      |   Amount    | Every |   Until"
                    + System.lineSeparator()
                    + "Expense |   FOOD   | 2021-02-01 | Samurai Burger |-$7.50       |       |"
                    + System.lineSeparator()
                    + "Income  |   GIFT   | 2015-12-15 |    Lottery     | $250,000.00 |       |"
                    + System.lineSeparator()
                    + "                                      Net Total: | $249,992.50" + System.lineSeparator()
                    + "Here is the list of all recurring entries, where some were added to the above list:"
                    + System.lineSeparator();
            assertEquals(expectedOutput, output.toString());
        } catch (MintException e) {
            e.printStackTrace();
        }
    }

    //    @Test
    //    void view_allFieldsValid_success() {
    //
    //        ArrayList<Entry> outputArray = new ArrayList<>();
    //
    //        String name = "Samurai Burger";
    //        LocalDate date = LocalDate.parse("2021-02-01", dateFormatter);
    //        double amount = Double.parseDouble("7.50");
    //        ExpenseCategory categoryE = ExpenseCategory.FOOD;
    //        Expense expense = new Expense(name, date, amount, categoryE);
    //        outputArray.add(expense);
    //
    //        name = "Maid Cafe";
    //        date = LocalDate.parse("2012-06-06", dateFormatter);
    //        Interval interval = Interval.YEAR;
    //        LocalDate endDate = LocalDate.parse("2023-01-06", dateFormatter);
    //        amount = Double.parseDouble("14.6");
    //        categoryE = ExpenseCategory.ENTERTAINMENT;
    //        RecurringExpense expenseR = new RecurringExpense(name, date, amount, categoryE, interval, endDate);
    //        outputArray.add(expenseR);
    //
    //        name = "OnlyFans";
    //        date = LocalDate.parse("2021-06-09", dateFormatter);
    //        interval = Interval.MONTH;
    //        endDate = LocalDate.parse("2027-04-01", dateFormatter);
    //        amount = Double.parseDouble("300");
    //        IncomeCategory categoryI = IncomeCategory.COMMISSION;
    //        RecurringIncome incomeR = new RecurringIncome(name, date, amount, categoryI, interval, endDate);
    //        outputArray.add(incomeR);
    //
    //        name = "Lottery";
    //        date = LocalDate.parse("2015-12-15", dateFormatter);
    //        amount = Double.parseDouble("250000");
    //        categoryI = IncomeCategory.GIFT;
    //        Income income = new Income(name, date, amount, categoryI);
    //        outputArray.add(income);
    //
    //        ArrayList<Entry> recurringOutputArray = new ArrayList<>();
    //        recurringOutputArray.add(expenseR);
    //        recurringOutputArray.add(incomeR);
    //
    //        try {
    //            String[] argumentArray = {"view"};
    //            ViewOptions viewOptions = new ViewOptions(argumentArray);
    //            ViewCommand view = new ViewCommand(viewOptions);
    //            Ui ui = new Ui();
    //            RecurringFinanceManager recurringFinanceManager = new RecurringFinanceManager();
    //            outputArray = recurringFinanceManager.appendEntryForView(viewOptions, outputArray,
    //            recurringOutputArray);
    //            ByteArrayOutputStream output = new ByteArrayOutputStream();
    //            System.setOut(new PrintStream(output));
    //            view.view(outputArray,recurringOutputArray, ui);
    //            String expectedOutput = "Here is the list of your entries:\n"
    //                    + "  Type  |  Category  |    Date    |      Name      |   Amount    | Every |   Until\n"
    //                    + "Income  | COMMISSION | 2021-10-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01\n"
    //                    + "Income  | COMMISSION | 2021-09-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01\n"
    //                    + "Income  | COMMISSION | 2021-08-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01\n"
    //                    + "Income  | COMMISSION | 2021-07-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01\n"
    //                    + "Income  | COMMISSION | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01\n"
    //                    + "Expense |   OTHERS   | 2021-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |    FOOD    | 2021-02-01 | Samurai Burger |-$7.50       |       |\n"
    //                    + "Expense |   OTHERS   | 2020-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2019-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2018-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2017-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2016-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Income  |    GIFT    | 2015-12-15 |    Lottery     | $250,000.00 |       |\n"
    //                    + "Expense |   OTHERS   | 2015-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2014-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2013-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "Expense |   OTHERS   | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n"
    //                    + "                                        Net Total: | $251,346.50\n"
    //                    + "Here is the list of recurring entries added to the above list:\n"
    //                    + "Income  | COMMISSION | 2021-06-09 |    OnlyFans    | $300.00     | MONTH | 2027-04-01\n"
    //                    + "Expense |   OTHERS   | 2012-06-06 |   Maid Cafe    |-$14.60      | YEAR  | 2023-01-06\n";
    //            assertEquals(expectedOutput, output.toString());
    //        } catch (MintException e) {
    //            e.printStackTrace();
    //        }
    //    }
}