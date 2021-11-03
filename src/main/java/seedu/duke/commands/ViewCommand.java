package seedu.duke.commands;


import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.Type;
import seedu.duke.finances.NormalFinanceManager;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ViewOptions;
import seedu.duke.storage.BudgetDataManager;
import seedu.duke.storage.DataManagerActions;
import seedu.duke.storage.NormalListDataManager;
import seedu.duke.storage.RecurringListDataManager;
import seedu.duke.utility.Sorter;
import seedu.duke.utility.Ui;
import seedu.duke.finances.RecurringFinanceManager;
import seedu.duke.entries.Entry;

import java.util.ArrayList;
import java.util.Collections;

public class ViewCommand extends Command {
    private final ViewOptions viewOptions;

    public ViewCommand(ViewOptions viewOptions) {
        this.viewOptions = viewOptions;
    }

    @Override
    public void execute(NormalFinanceManager normalFinanceManager,
            RecurringFinanceManager recurringFinanceManager, BudgetManager budgetManager,
            NormalListDataManager normalListDataManager, DataManagerActions dataManagerActions,
            RecurringListDataManager recurringListDataManager, BudgetDataManager budgetDataManager, Ui ui) {
        try {
            ArrayList<Entry> outputArray;
            ArrayList<Entry> recurringOutputArray = new ArrayList<>();

            outputArray = normalFinanceManager.getCopyOfArray();
            outputArray = recurringFinanceManager.appendEntryForView(viewOptions, outputArray, recurringOutputArray);

            outputArray.sort(Sorter.compareByDate);
            recurringOutputArray.sort(Sorter.compareByDate);
            applyModifiers(outputArray);
            applyRecurringModifiers(recurringOutputArray);

            double total = calculateTotal(outputArray);
            int[] indentations = ui.printView(outputArray, viewOptions.fromDate, viewOptions.endDate, total);
            ui.printViewRecurring(recurringOutputArray, indentations[0], indentations[1]);
        } catch (MintException e) {
            ui.printError(e);
        }
    }

    public void applyModifiers(ArrayList<Entry> outputArray) throws MintException {
        if (viewOptions.onlyExpense) {
            outputArray.removeIf(entry -> entry.getType() != Type.Expense);
        }

        if (viewOptions.onlyIncome) {
            outputArray.removeIf(entry -> entry.getType() != Type.Income);
        }

        if (viewOptions.year != 0) {
            System.out.println("For the year " + viewOptions.year + ":");
            Sorter.trimByYear(outputArray, viewOptions.year);
        }

        if (viewOptions.month != null) {
            System.out.println("For the month of " + viewOptions.month + ":");
            Sorter.trimByMonth(outputArray, viewOptions.month);
        }

        if (viewOptions.fromDate != null) {
            assert viewOptions.endDate != null : "There should be a valid end date";
            Sorter.trimFrom(outputArray, viewOptions.fromDate);
            Sorter.trimEnd(outputArray, viewOptions.endDate);
        }

        if (viewOptions.sortType != null) {
            sort(viewOptions.sortType, outputArray);
        }

        if (viewOptions.isAscending) {
            Collections.reverse(outputArray);
        }
    }

    public void applyRecurringModifiers(ArrayList<Entry> outputArray) throws MintException {
        if (viewOptions.onlyExpense) {
            outputArray.removeIf(entry -> entry.getType() != Type.Expense);
        }

        if (viewOptions.onlyIncome) {
            outputArray.removeIf(entry -> entry.getType() != Type.Income);
        }

        if (viewOptions.sortType != null) {
            sort(viewOptions.sortType, outputArray);
        }

        if (viewOptions.isAscending) {
            Collections.reverse(outputArray);
        }
    }

    public void sort(String sortType, ArrayList<Entry> outputArray) throws MintException {
        assert sortType != null : "sortType should have a command";
        switch (sortType) {
        case "name":
            outputArray.sort(Sorter.compareByName);
            break;
        case "date":
            outputArray.sort(Sorter.compareByDate);
            break;
        case "amount":
            outputArray.sort(Sorter.compareByAmount);
            break;
        case "cat":
            //fallthrough
        case "category":
            outputArray.sort(Sorter.compareByCategory);
            break;
        default:
            throw new MintException(MintException.ERROR_INVALID_COMMAND); // Link to MintException
        }
    }

    public double calculateTotal(ArrayList<Entry> list) {
        double total = 0;
        for (Entry entry : list) {
            if (entry.getType() == Type.Expense) {
                total -= entry.getAmount();
            } else {
                total += entry.getAmount();
            }
        }
        return total;
    }
}
