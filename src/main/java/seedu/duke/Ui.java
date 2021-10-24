package seedu.duke;

import seedu.duke.parser.Parser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner in;

    public Ui() {
        this.in = new Scanner(System.in);
    }

    public String readUserInput() {
        return in.nextLine().trim();
    }

    protected static final String INDENT = "    ";
    public static final String SUCCESSFUL_EDIT_MESSAGE = "Got it! I will update the fields accordingly!";
    public static final String UNSUCCESSFUL_EDIT_MESSAGE = "No difference detected!"
            + "I was unable to perform any edits! "
            + "Please check that you have made changes or included the tags of the fields you wish to edit! :(";

    protected static final String LINE_SEPARATOR = System.lineSeparator();
    protected static final int INDEX_CANCEL = -1;
    protected static final String CANCEL_MESSAGE = " To cancel, type \"cancel\"";
    public static final String MISSING_FILE_MESSAGE = "Missing data detected! Creating the necessary files...";

    public void printGreetings() {
        System.out.println("Hello! I'm Mint");
        System.out.println("What can I do for you?");
    }

    public static void shutdown() {
        System.out.println("Goodbye! Hope to see you again soon!");
    }

    public static void printInvalidTagError() {
        System.out.println("Sorry, we are only able to process name, date and process for now");
        System.out.println("The following tags are available: n/ d/ a/");
    }

    public static void help() {
        System.out.println("Available tags: n/name d/date a/amount c/category_number\n"
                + "Order of tags does not matter.\n"
                + "List of commands available.\n"
                + "Square brackets \"[ ]\" identifies an optional argument.\n"
                + " - view\n"
                + INDENT + "View expenses\n"
                + "- cat\n"
                + INDENT + " View categories and category number\n"
                + "- add n/NAME a/amount [d/YYYY-MM-DD] [c/category_number]\n"
                + INDENT + "Add expense. Example: add n/chicken rice a/3.50 d/2021-09-30 c/1\n"
                + "- delete [n/{keyword}] [a/amount] [d/YYYY-MM-DD] [c/category_number]\n"
                + INDENT + "Delete expense using keyword search. Example: delete n/chicken\n"
                + "- edit [n/{keyword}] [a/amount] [d/YYYY-MM-DD] [c/category number]\n"
                + INDENT + "Edit expense using keyword search. Example: edit n/chicken\n"
                + "- limit c/category number a/amount\n"
                + INDENT + "Set spending limit for individual category. Example: limit c/0 100\n"
                + "- breakdown\n"
                + INDENT + "View breakdown on current month's expenses\n"
                + "- exit\n"
        );
    }

    public static void viewGivenList(ArrayList<Entry> list) {
        System.out.println("Here is the list of items containing the keyword.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENT + (i + 1) + "  " + list.get(i).toString());
        }
    }

    public static int chooseItemToDeleteOrEdit(ArrayList<Entry> filteredList, boolean isDelete) throws MintException {
        if (isDelete) {
            System.out.println("Enter the index of the item you want to delete." + CANCEL_MESSAGE);
        } else {
            System.out.println("Enter the index of the item you want to edit." + CANCEL_MESSAGE);
        }

        Scanner in = new Scanner(System.in);
        int index = 0;
        boolean proceedToDelete = false;
        while (!proceedToDelete) {
            String userInput = in.nextLine();
            if (userInput.trim().equals("cancel")) {
                System.out.println("Delete process cancelled.");
                return INDEX_CANCEL;
            }
            try {
                index = Integer.parseInt(userInput);
                if (index < 1 || index > filteredList.size()) {
                    System.out.println(MintException.ERROR_INDEX_OUT_OF_BOUND + CANCEL_MESSAGE);
                } else {
                    proceedToDelete = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(MintException.ERROR_INDEX_INVALID_NUMBER + CANCEL_MESSAGE);
            }
        }
        return index - 1;
    }

    public static boolean isConfirmedToDeleteOrEdit(Entry entry, boolean isDelete) {
        if (isDelete) {
            System.out.println("Is this what you want to delete?");
        } else {
            System.out.println("Is this what you want to edit?");
        }
        System.out.println(INDENT + entry);
        System.out.println("Type \"y\" if yes. Type \"n\" if not.");
        Scanner in = new Scanner(System.in);
        while (true) {
            String userInput = in.nextLine();
            switch (userInput.trim()) {
            case "y":
                return true;
            case "n":
                System.out.println("Ok. I have cancelled the delete process.");
                return false;
            default:
                System.out.println("Sorry I don't understand what that means. +"
                        + "Type \"y\" if yes. Type \"n\" if not.");
                break;
            }
        }
    }

    public static void printOutcomeOfEditAttempt(Boolean printEditSuccess, Boolean exceptionThrown) {
        if (!exceptionThrown) {
            if (printEditSuccess) {
                System.out.println(SUCCESSFUL_EDIT_MESSAGE);
            } else {
                System.out.println(UNSUCCESSFUL_EDIT_MESSAGE);
            }
        }
    }

    public static void printMissingFileMessage() {
        System.out.println(MISSING_FILE_MESSAGE);
    }

    //    public static void setLimitMessage(String catNumString, String amount) {
    //        int catNumInt = Integer.parseInt(catNumString);
    //        System.out.println("Set Limit of " + CategoryList.getCatName(catNumInt) + " to $" + amount);
    //    }

    public static StringBuilder constructErrorMessage(ArrayList<String> missingDelimiters) throws MintException {
        int index = 1;
        StringBuilder missingFieldsErrorMessage = new StringBuilder();
        missingFieldsErrorMessage.append(Parser.STRING_INCLUDE);
        for (String delimiter : missingDelimiters) {
            switch (delimiter) {
            case "n/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_DESCRIPTION);
                index++;
                break;
            case "d/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_DATE);
                index++;
                break;
            case "a/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_AMOUNT);
                index++;
                break;
            case "c/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_CATNUM);
                index++;
                break;
            case "i/":
                missingFieldsErrorMessage.append(index).append(Parser.SEPARATOR).append(Parser.STRING_INTERVAL);
                index++;
                break;
            default:
                throw new MintException(MintException.ERROR_INVALID_COMMAND);
            }
        }
        return missingFieldsErrorMessage;
    }

    public static void printView (ArrayList<Entry> outputArray, LocalDate fromDate, LocalDate endDate, double total) {
        System.out.println("Here is the list of your expenses:");
        if (fromDate != null) {
            System.out.println("Since " + fromDate + " to " + endDate + ":");
        }
        System.out.println("  Type  |     Category     |    Date    |       Name       | Amount");
        for (Entry entry : outputArray) {
            System.out.println(entry.toString());
        }
        System.out.print("                                                Net Total: |");
        if (total < 0) {
            total = Math.abs(total);
            System.out.print("-$" + String.format("%,.2f", total));
        } else {
            System.out.print(" $" + String.format("%,.2f", total));
        }
        System.out.println();
    }

    public static StringBuilder getIndent(int leftIndent, int rightIndent, String item) {
        StringBuilder itemWithIndent = new StringBuilder();
        while (leftIndent != 0) {
            itemWithIndent.append(" ");
            leftIndent--;
        }

        itemWithIndent.append(item);

        while (rightIndent != 0) {
            itemWithIndent.append(" ");
            rightIndent--;
        }
        return itemWithIndent;
    }
}



