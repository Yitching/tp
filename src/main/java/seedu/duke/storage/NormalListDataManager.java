package seedu.duke.storage;


import seedu.duke.entries.*;


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

    public NormalListDataManager(String path) {
        super(path);
    }

    public void appendToEntryListTextFile(String filePath, Entry entry) {
        // Format of Mint.txt file: 0|2021-12-03|Textbook|15.0
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, true);
            fileWriter.write(entry.getType().toString() + TEXT_DELIMITER + entry.getCategory().ordinal() + TEXT_DELIMITER
                    + entry.getDate() + TEXT_DELIMITER + entry.getName() + TEXT_DELIMITER + entry.getAmount()
                    + System.lineSeparator());
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
            editTextFile(fileContent, NORMAL_FILE_PATH);
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
            editTextFile(fileContent, NORMAL_FILE_PATH);
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

    public void removeAll() {
        try {
            ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Path.of(NORMAL_FILE_PATH),
                    StandardCharsets.UTF_8));
            fileContent.clear();
            editTextFile(fileContent, NORMAL_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEntryListContents(ArrayList<Entry> entryList) throws FileNotFoundException {
        File mintEntryList = new File(NORMAL_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintEntryList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            String type = params[0];
            String catNum = params[1];
            String date = params[2];
            String name = params[3];
            String amount = params[4];
            loadEntry(type, name, date, amount, catNum, entryList);
        }
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
}