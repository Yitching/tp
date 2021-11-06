package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.commands.DeleteCommand;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.exception.MintException;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.utility.Ui;
import seedu.duke.utility.UiStub;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteCommandTest {

    NormalFinanceManager normalFinanceManager = new NormalFinanceManager();
    ArrayList<Entry> entryList = normalFinanceManager.entryList;
    Ui uiStub = new UiStub();

    @Test
    public void determineEntryToDelete_multipleExpenseMatch_success() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("Cocoa", date, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("Nada de coco", date, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new Expense("coco", null, 0.0, null);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToDelete(normalFinanceManager, uiStub);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToDelete_queryCategory_successFindingIncome() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense dummyEntry = new Expense("yam", date, 7.1, ExpenseCategory.FOOD);
        Income targetEntry = new Income("bonus", date, 3.9, IncomeCategory.ALLOWANCE);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("c/");
        //ExpenseCategory.FOOD has same category num as IncomeCategory.SALARY
        Entry query = new Expense(null, null, 0.0, ExpenseCategory.FOOD);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToDelete(normalFinanceManager, uiStub);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }


    @Test
    public void determineEntryToDelete_singleExpenseMatch_findTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("jelly", date, 3.9, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("belly", date, 5.0, ExpenseCategory.OTHERS);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("c/");
        Entry query = new Expense(null, null, 0.0, ExpenseCategory.FOOD);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToDelete(normalFinanceManager, uiStub);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToDelete_bothExpenseAndIncomeMatch_findTargetSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense dummyEntry = new Expense("bonus round of burger", date, 3.9, ExpenseCategory.FOOD);
        Income targetEntry = new Income("bonus", date, 3.9, IncomeCategory.SALARY);
        entryList.add(dummyEntry);
        entryList.add(targetEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new Expense("bonus", null, 0.0, null);


        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToDelete(normalFinanceManager, uiStub);
            assertEquals(entryToDelete, targetEntry);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToDelete_cancelSingleMatch_cancelSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("jelly", date, 7.0, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("bean", date, 5.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("n/");
        Entry query = new Expense("jelly", null, 0.0, null);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToDelete(normalFinanceManager, uiStub);
            assertNull(entryToDelete);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

    @Test
    public void determineEntryToDelete_queryAmountAndCancelMultipleMatch_cancelSuccess() {
        LocalDate date = LocalDate.parse("2021-10-17");
        Expense targetEntry = new Expense("jelly", date, 100.0, ExpenseCategory.FOOD);
        Expense dummyEntry = new Expense("bean", date, 100.0, ExpenseCategory.FOOD);
        entryList.add(targetEntry);
        entryList.add(dummyEntry);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a/");
        Entry query = new Expense(null, null, 100.0, null);

        DeleteCommand command = new DeleteCommand(tags, query);
        try {
            Entry entryToDelete = command.determineEntryToDelete(normalFinanceManager, uiStub);
            assertNull(entryToDelete);
        } catch (MintException e) {
            e.printStackTrace();
        }
        entryList.remove(targetEntry);
        entryList.remove(dummyEntry);
    }

}
