package seedu.duke.commands;

import seedu.duke.Expense;
import seedu.duke.ExpenseCategory;
import seedu.duke.ExpenseList;
import seedu.duke.MintException;
import seedu.duke.storage.EntryListDataManager;

import java.util.ArrayList;
import java.util.logging.Level;

public class DeleteExpenseCommand {

    //    public void deleteExpenseByKeywords(ArrayList<String> tags, Expense expense,
    //                                        ArrayList<Expense> expenseList) throws MintException {
    //        try {
    //            String name = expense.getName();
    //            String date = expense.getDate().toString();
    //            String amount = Double.toString(expense.getAmount());
    //            String catNum = String.valueOf(expense.getCategory().ordinal());
    //            Expense finalExpense = ExpenseList.chooseExpenseByKeywords(tags, true, name, date, amount, catNum,
    //                    expenseList);
    //            if (finalExpense != null) {
    //                deleteExpense(finalExpense, expenseList);
    //            }
    //        } catch (MintException e) {
    //            throw new MintException(e.getMessage());
    //        }
    //    }

    //    public void deleteExpense(Expense expense) throws MintException {
    //        deleteExpense(expense.getName(), expense.getDate().toString(),
    //                Double.toString(expense.getAmount()), Integer.toString(expense.getCatNum()));
    //    }

    public void deleteExpense(Expense expense, ArrayList<Expense> expenseList) throws MintException {
        //        Expense expense = new Expense(name, date, amount, catNum);
        if (expenseList.contains(expense)) {
            //            logger.log(Level.INFO, "User deleted expense: " + expense);
            System.out.println("I have deleted: " + expense);
            expenseList.remove(expense);
            String stringToDelete = ExpenseList.overWriteString(expense);
            //            if (isCurrentMonthExpense(expense)) {
            //                CategoryList.deleteSpending(expense);
            //            }
            EntryListDataManager.deleteLineInTextFile(stringToDelete);
        }
    }
}
