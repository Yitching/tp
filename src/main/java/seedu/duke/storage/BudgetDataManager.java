package seedu.duke.storage;

import seedu.duke.budget.Budget;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.utility.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BudgetDataManager extends DataManagerActions {

    public static final String BUDGET_FILE_PATH = "data" + File.separator + "MintBudget.txt";

    public void writeToBudgetTextFile(ArrayList<Budget> budgetList) {
        FileWriter fileWriter = null;
        boolean cleared = false;
        try {
            fileWriter = new FileWriter(BUDGET_FILE_PATH, false);
            for (Budget budget: budgetList) {
                fileWriter.write(budget.getCategory().ordinal() + TEXT_DELIMITER
                        + budget.getLimit() + System.lineSeparator());
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBudgetListContents(ArrayList<Budget> budgetList) throws FileNotFoundException, MintException,
            ArrayIndexOutOfBoundsException {
        File mintBudgetList = new File(BUDGET_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintBudgetList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            if (fieldsInTextFile.length() == 0) {
                continue;
            }
            String[] params = fieldsInTextFile.split("\\|");
            assert (params[0] != null);
            assert (params[1] != null);
            String catNumStr = params[0];
            String amountStr = params[1];
            ValidityChecker.checkValidityOfFieldsInBudgetListTxt(catNumStr, amountStr);
            int catNum = Integer.parseInt(catNumStr);
            double amount = Double.parseDouble(amountStr);
            loadBudget(catNum, amount, budgetList);
        }
    }

    public void loadBudget(int catNum, double amount, ArrayList<Budget> budgetList) {
        Budget budget = budgetList.get(catNum);
        budget.setLimit(amount);
    }

    public void writeToTextFile(ArrayList<Budget> budgetList) {
        BudgetDataManager budgetDataManager = new BudgetDataManager();
        budgetDataManager.writeToBudgetTextFile(budgetList);
    }

    public void loadFromTextFile(ArrayList<Budget> budgetList) {
        BudgetDataManager budgetDataManager = new BudgetDataManager();
        try {
            budgetDataManager.loadBudgetListContents(budgetList);
        } catch (FileNotFoundException e) {
            Ui.printMissingFileMessage();
            createDirectory();
            createFiles();
        } catch (MintException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            Ui.printFieldsErrorMessage();
        }
    }
}
