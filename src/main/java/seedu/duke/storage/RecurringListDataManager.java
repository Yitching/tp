package seedu.duke.storage;

import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Entry;
import seedu.duke.entries.ExpenseCategory;
import seedu.duke.entries.RecurringEntry;
import seedu.duke.entries.RecurringExpense;
import seedu.duke.entries.RecurringIncome;
import seedu.duke.entries.Interval;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.utility.Ui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class RecurringListDataManager extends DataManagerActions {
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";

    public void appendToMintRecurringListTextFile(Entry entry) {
        // Format of MintRecurring.txt file: Expense|7|2021-10-25|SALARY|10000.0|MONTH
        FileWriter fileWriter;
        try {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            fileWriter = new FileWriter(RECURRING_FILE_PATH, true);
            fileWriter.write(entry.getType().toString() + TEXT_DELIMITER + entry.getCategory().ordinal()
                    + TEXT_DELIMITER + entry.getDate() + TEXT_DELIMITER + entry.getName() + TEXT_DELIMITER
                    + entry.getAmount() + TEXT_DELIMITER + recurringEntry.getInterval() + TEXT_DELIMITER
                    + recurringEntry.getEndDate() + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEntryListContents(ArrayList<Entry> entryList) throws FileNotFoundException, MintException,
            ArrayIndexOutOfBoundsException {
        File mintEntryList = new File(RECURRING_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintEntryList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            if (fieldsInTextFile.length() == 0) {
                continue;
            }
            try {
                String[] params = fieldsInTextFile.split("\\|");
                String type = params[0];
                String catNum = params[1];
                String date = params[2];
                String name = params[3];
                String amount = params[4];
                String interval = params[5];
                String endDate = params[6];
                ValidityChecker.checkValidityOfFieldsInNormalListTxt(type, name, date, amount, catNum);
                ValidityChecker.checkValidityOfFieldsInRecurringListTxt(interval, endDate);
                loadEntry(type, name, date, amount, catNum, interval, endDate, entryList);
            } catch (MintException e) {
                reload(entryList, fieldsInTextFile);
                throw new MintException(e.getMessage() + " Invalid line deleted. We have reloaded the list!");
            } catch (ArrayIndexOutOfBoundsException e) {
                reload(entryList, fieldsInTextFile);
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

    public void reload(ArrayList<Entry> entryList, String fieldsInTextFile) throws FileNotFoundException,
            MintException {
        deleteLineInTextFile(fieldsInTextFile);
        entryList.clear();
        loadEntryListContents(entryList);
    }

    public void loadEntry(String type, String name, String dateStr, String amountStr,
            String catNumStr, String intervalStr, String endDateStr, ArrayList<Entry> recurringList) {
        //should check type before loading
        RecurringEntry recurringEntry;
        LocalDate date = LocalDate.parse(dateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        double amount = Double.parseDouble(amountStr);
        Interval interval = null;
        try {
            interval = Interval.determineInterval(intervalStr);
        } catch (MintException e) {
            e.printStackTrace();
        }
        int index = Integer.parseInt(catNumStr);
        if (Objects.equals(type.toLowerCase(), "expense")) {
            ExpenseCategory category = ExpenseCategory.values()[index];
            recurringEntry = new RecurringExpense(name, date, amount, category, interval, endDate);
        } else {
            IncomeCategory category = IncomeCategory.values()[index];
            recurringEntry = new RecurringIncome(name, date, amount, category, interval, endDate);
        }
        recurringList.add(recurringEntry);
    }

    public void deleteLineInTextFile(String originalString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(RECURRING_FILE_PATH), StandardCharsets.UTF_8));
            lineRemoval(originalString, fileContent);
            editTextFile(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            new FileWriter(RECURRING_FILE_PATH, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void lineRemoval(String originalString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.remove(i);
                break;
            }
        }
    }

    protected void editTextFile(ArrayList<String> fileContent) throws IOException {
        Files.write(Path.of(RECURRING_FILE_PATH), fileContent, StandardCharsets.UTF_8);
    }

    public void editEntryListTextFile(String originalString, String newString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(RECURRING_FILE_PATH), StandardCharsets.UTF_8));
            setContentToBeChanged(originalString, newString, fileContent);
            editTextFile(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setContentToBeChanged(String originalString, String newString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.set(i, newString);
                break;
            }
        }
    }

    public void loadPreviousFileContents(ArrayList<Entry> recurringEntryList) {
        try {
            loadEntryListContents(recurringEntryList);
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
