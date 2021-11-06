package seedu.duke.parser;

import seedu.duke.entries.Interval;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.utility.Ui;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidityChecker {
    public static final int MIN_CATNUM = 0;
    public static final int MAX_CATNUM = 7;
    public static final String BLANK = "";
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final Pattern doublePattern = Pattern.compile("\\d+(\\.\\d+)?");
    public static final String userTagNoSpace = "(.*)[0-9a-zA-Z/\\-.][a-zA-Z]/(.*)";
    public static final String invalidForwardSlash = "(.*)[0-9a-zA-Z/\\-.][0-9/\\-.]/(.*)";
    public static final String forwardSlashWithoutTagType = "(.*)[ \\-.]/(.*)";
    public static final double AMOUNT_LIMIT = 1000000.0;

    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");

    public static void checkEmptyName(String name) throws MintException {
        boolean hasEmptyName = name.equals(Parser.STRING_EMPTY);
        if (hasEmptyName) {
            logger.log(Level.INFO, "User entered empty name");
            throw new MintException(MintException.ERROR_NO_NAME);
        }
    }

    public static void checkInvalidAmount(String amountStr) throws MintException {
        boolean isDoubleWithoutLetters = doublePattern.matcher(amountStr).matches();
        boolean isEmpty = amountStr == null;
        if (isEmpty || !isDoubleWithoutLetters) {
            throw new MintException(MintException.ERROR_INVALID_AMOUNT);
        }
        double amount = Double.parseDouble(amountStr);
        if (amount >= AMOUNT_LIMIT) {
            throw new MintException(MintException.ERROR_AMOUNT_TOO_LARGE);
        }
    }

    public static void checkInvalidDate(String dateStr) throws MintException {
        try {
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            int year = date.getYear();
            if (year < 2000 || year > 2200) {
                throw new MintException(MintException.ERROR_INVALID_YEAR);
            }
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException(MintException.ERROR_INVALID_DATE);
        }
    }

    public static void checkInvalidEndDate(String endDateStr, String startDateStr) throws MintException {
        try {
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
            LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);

            int year = endDate.getYear();
            if (year < 2000 || year > 2200) {
                throw new MintException(MintException.ERROR_INVALID_YEAR);
            }

            if (!endDate.isAfter(startDate)) {
                throw new MintException(MintException.ERROR_INVALID_END_DATE);
            }
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException(MintException.ERROR_INVALID_DATE);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public static void checkInvalidCatNum(String catNumStr) throws MintException {
        try {
            int catNumInt = Integer.parseInt(catNumStr);
            if (!(catNumInt >= MIN_CATNUM && catNumInt <= MAX_CATNUM)) {
                throw new MintException(MintException.ERROR_INVALID_CATNUM);
            }
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid category number");
            throw new MintException(MintException.ERROR_INVALID_CATNUM);
        }

    }

    public static void checkInvalidInterval(String intervalStr) throws MintException {
        try {
            Interval.valueOf(intervalStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "User entered invalid interval");
            throw new MintException(MintException.ERROR_INVALID_INTERVAL);
        }
    }

    public static int checkInvalidIndex(String indexStr, int size) throws MintException {
        try {
            int index = Integer.parseInt(indexStr);
            if (index < 1 || index > size) {
                logger.log(Level.INFO, "User entered out of bound index");
                throw new MintException(MintException.ERROR_INDEX_OUT_OF_BOUND);
            }
            return index;
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid number index");
            throw new MintException(MintException.ERROR_INDEX_INVALID_NUMBER);
        }
    }

    static void identifyDuplicateTags(Parser parser, String userInput) throws MintException {
        String[] tags = parser.isRecurring ? new String[]{"n/", "d/", "a/", "c/", "i/", "e/"}
                : new String[]{"n/", "d/", "a/", "c/"};

        for (String tag: tags) {
            int count = 0;
            Pattern pattern = Pattern.compile(tag);
            Matcher matcher = pattern.matcher(userInput);
            while (matcher.find()) {
                count++;
            }
            if (count > 1) {
                throw new MintException(MintException.ERROR_DUPLICATE_TAGS);
            }
        }
    }

    static ArrayList<String> identifyValidTags(Parser parser, String userInput,
                                               String[] mandatoryTags) throws MintException {
        ArrayList<String> validTags = new ArrayList<>();
        ArrayList<String> invalidTags = new ArrayList<>();
        String[] tags = parser.isRecurring ? new String[]{" n/", " d/", " a/", " c/", " i/", " e/"}
                : new String[]{" n/", " d/", " a/", " c/"};
        List<String> mandatoryTagsToBeChecked = Arrays.asList(mandatoryTags);

        for (String tag : tags) {
            try {
                if (userInput.contains(tag)) {
                    switch (tag.trim()) {
                    case "n/":
                        checkEmptyName(parser.name);
                        break;
                    case "d/":
                        checkInvalidDate(parser.dateStr);
                        break;
                    case "a/":
                        checkInvalidAmount(parser.amountStr);
                        break;
                    case "c/":
                        checkInvalidCatNum(parser.catNumStr);
                        break;
                    case "i/":
                        checkInvalidInterval(parser.intervalStr);
                        break;
                    case "e/":
                        checkInvalidEndDate(parser.endDateStr, parser.dateStr);
                        break;
                    default:
                        throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
                    }
                    validTags.add(tag);
                } else if (mandatoryTagsToBeChecked.contains(tag)) {
                    invalidTags.add(tag);
                }
            } catch (MintException e) {
                invalidTags.add(tag);
            }
        }

        if (invalidTags.size() > 0) {
            throw new MintException(Ui.constructErrorMessage(invalidTags).toString());
        } else if (validTags.size() == 0) {
            throw new MintException("Please enter at least one tag.");
        }
        return validTags;
    }

    static ArrayList<String> checkExistenceAndValidityOfTags(Parser parser, String userInput) throws MintException {
        try {
            String[] mandatoryTags;
            switch (parser.command) {
            case "add":
                mandatoryTags = new String[]{" n/", " a/"};
                break;
            case "addR":
                mandatoryTags = new String[]{" n/", " a/", " i/"};
                break;
            case "set":
                mandatoryTags = new String[]{" c/", " a/"};
                break;
            default:
                mandatoryTags = new String[]{};
            }
            return identifyValidTags(parser, userInput, mandatoryTags);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public static void checkValidCatNum(int catNum) throws MintException {
        if (!((catNum > -1) && (catNum < 8))) {
            throw new MintException(ERROR_INVALID_NUMBER);
        }
    }

    public static void checkValidityOfFieldsInNormalListTxt(String type, String name, String date, String amount,
                                                            String catNum) throws MintException {
        if (!((type.equalsIgnoreCase("Income") || type.equalsIgnoreCase("Expense")))) {
            throw new MintException("Invalid type detected!");
        }
        if (name.equals(BLANK)) {
            throw new MintException("Empty description detected!");
        }
        try {
            LocalDate.parse(date, dateFormatter);
            Double.parseDouble(amount);
            checkInvalidAmount(amount);
            int catNumInt = Integer.parseInt(catNum);
            checkValidCatNum(catNumInt);
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException("Invalid date detected!");
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid number!");
            throw new MintException("Invalid number detected!");
        }
    }

    public static void checkValidityOfFieldsInRecurringListTxt(String interval, String endDate) throws MintException {
        try {
            LocalDate parsedEndDate = LocalDate.parse(endDate, dateFormatter);
            LocalDate parsedDate = LocalDate.parse(endDate, dateFormatter);
            if (parsedEndDate.isBefore(parsedDate)) {
                throw new MintException("Invalid date detected!");
            }
            Interval.valueOf(interval.toUpperCase());
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException("Invalid date detected!");
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "User entered invalid interval");
            throw new MintException("Invalid interval detected!");
        }
    }

    public static void checkValidityOfFieldsInBudgetListTxt(String catNum, String amount) throws MintException {
        try {
            Double.parseDouble(amount);
            checkInvalidAmount(amount);
            checkValidCatNum(Integer.parseInt(catNum));
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid amount!");
            throw new MintException("Unable to load text file! Invalid number detected! "
                    + "Did u accidentally edit the file?");
        }
    }

    public static void checkTagsFormatSpacing(String userInput) throws MintException {
        if (userInput.matches(userTagNoSpace)) {
            throw new MintException(MintException.ERROR_NO_SPACE_BEFORE_TAGS);
        }
        if (userInput.matches(invalidForwardSlash)) {
            throw new MintException(MintException.ERROR_INVALID_FORWARD_SLASH);
        }
        if (userInput.matches(forwardSlashWithoutTagType)) {
            throw new MintException(MintException.ERROR_MISSING_TAG_TYPE);
        }
    }
}
