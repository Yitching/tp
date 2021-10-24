package seedu.duke.storage;

import seedu.duke.MintException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class CategoryListDataManager extends DataManagerActions {

    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";
    public static final String CATEGORY_TAG = "c/";
    
    public CategoryListDataManager(String path) {
        super(path);
    }

    public void editCategoryTextFile(int catNum) {
        ArrayList<String> fileContent;
        try {
            String categoryToBeChanged = CATEGORY_TAG + catNum;
            fileContent = accessCategoryTextFile();
            setContentToBeChanged(catNum, fileContent, categoryToBeChanged);
            editTextFile(fileContent, CATEGORY_FILE_PATH);
        } catch (FileNotFoundException e) {
            createDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> accessCategoryTextFile() throws IOException {
        ArrayList<String> fileContent;
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(CATEGORY_FILE_PATH), StandardCharsets.UTF_8));
        if (fileContent.isEmpty()) {
            writeToCategoryTextFile();
        }
        return fileContent;
    }

    private void setContentToBeChanged(int catNum, ArrayList<String> fileContent, String categoryToBeChanged) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(categoryToBeChanged)) {
                fileContent.set(i, CATEGORY_TAG + catNum + TEXT_DELIMITER + catNum);
                break;
            }
        }
    }

    public static void writeToCategoryTextFile() throws IOException {
        FileWriter fileWriter = new FileWriter(CATEGORY_FILE_PATH, true);
        for (int catNum = 0; catNum < NUMBER_OF_CATEGORIES; catNum++) {
            fileWriter.write(CATEGORY_TAG + catNum + TEXT_DELIMITER
                    + catNum + System.lineSeparator());
            fileWriter.flush();
        }
        fileWriter.close();
    }

    public static void loadCategoryFileContents() throws FileNotFoundException {
        File categoryList = new File(CATEGORY_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(categoryList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            //c/0| Not Set
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            // String catNum = params[0].replaceAll(REGEX_TO_REPLACE, "").trim();
            // String limit = params[1].replaceAll(REGEX_TO_REPLACE, "").trim();
            // try {
            // if (!limit.equals("")) {
            //      CategoryList.setLimit(catNum, limit);
            //                }
            //            } catch (MintException e) {
            //                e.printStackTrace();
            //            }
        }
    }
    
}
