package seedu.duke.storage;


import seedu.duke.entries.Income;
import seedu.duke.entries.IncomeCategory;
import seedu.duke.entries.Entry;
import seedu.duke.entries.Expense;
import seedu.duke.entries.ExpenseCategory;
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

public class NormalListDataManager extends DataManagerActions {

    public static final String TEXT_DELIMITER = "|";
    public static final String NORMAL_FILE_PATH = "data" + File.separator + "Mint.txt";


    public void appendToEntryListTextFile(Entry entry) {
        // Format of Mint.txt file: 0|2021-12-03|Textbook|15.0
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(NORMAL_FILE_PATH, true);
            fileWriter.write(entry.getType().toString() + TEXT_DELIMITER + entry.getCategory().ordinal()
                    + TEXT_DELIMITER + entry.getDate() + TEXT_DELIMITER + entry.getName() + TEXT_DELIMITER
                    + entry.getAmount() + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editEntryListTextFile(String originalString, String newString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH), StandardCharsets.UTF_8));
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

    public void deleteLineInTextFile(String originalString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH), StandardCharsets.UTF_8));
            lineRemoval(originalString, fileContent);
            editTextFile(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            new FileWriter(NORMAL_FILE_PATH, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void lineRemoval(String originalString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.remove(i);
                break;
            }
        }
    }

    protected void editTextFile(ArrayList<String> fileContent) throws IOException {
        Files.write(Path.of(NORMAL_FILE_PATH), fileContent, StandardCharsets.UTF_8);
    }

    public void removeAll() {
        try {
            ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH),
                    StandardCharsets.UTF_8));
            fileContent.clear();
            editTextFile(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEntryListContents(ArrayList<Entry> entryList) throws FileNotFoundException,
            ArrayIndexOutOfBoundsException, MintException {
        File mintEntryList = new File(NORMAL_FILE_PATH); // create a File for the given file path
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
                ValidityChecker.checkValidityOfFieldsInNormalListTxt(type, name, date, amount, catNum);
                loadEntry(type, name, date, amount, catNum, entryList);
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
            String catNumStr, ArrayList<Entry> entryList) {
        //should check type before loading
        Entry entry;
        LocalDate date = LocalDate.parse(dateStr);
        double amount = Double.parseDouble(amountStr);
        int index = Integer.parseInt(catNumStr);
        if (Objects.equals(type.toLowerCase(), "expense")) {
            ExpenseCategory category = ExpenseCategory.values()[index];
            entry = new Expense(name, date, amount, category);
        } else {
            IncomeCategory category = IncomeCategory.values()[index];
            entry = new Income(name, date, amount, category);
        }
        entryList.add(entry);
    }

    public void loadPreviousFileContents(ArrayList<Entry> entryList) {
        try {
            loadEntryListContents(entryList);
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
