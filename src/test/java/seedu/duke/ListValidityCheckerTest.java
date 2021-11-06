package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.duke.parser.ValidityChecker.checkValidityOfFieldsInNormalListTxt;
import static seedu.duke.parser.ValidityChecker.checkValidityOfFieldsInRecurringListTxt;

public class ListValidityCheckerTest {

    @Test
    void checkValidityOfFieldsInNormalListTxt_checkAmountFieldExceedInvalid_failure() {
        String type = "EXPENSE";
        String catNum = "7";
        String name = "Private Property";
        String amount = "1000000";
        String date = "2020-12-12";
        assertThrows(MintException.class, () -> checkValidityOfFieldsInNormalListTxt(type, name, date, amount, catNum));
    }

    @Test
    void checkValidityOfFieldsInNormalListTxt_checkTypeFieldInvalid_failure() {
        String type = "xx";
        String catNum = "7";
        String name = "Private Property";
        String amount = "80";
        String date = "2020-12-12";
        assertThrows(MintException.class, () -> checkValidityOfFieldsInNormalListTxt(type, name, date, amount, catNum));
    }

    @Test
    void checkValidityOfFieldsInNormalListTxt_checkAmountFieldInvalid_failure() {
        String type = "EXPENSE";
        String catNum = "7";
        String name = "Private Property";
        String amount = "10d";
        String date = "2020-12-12";
        assertThrows(MintException.class, () -> checkValidityOfFieldsInNormalListTxt(type, name, date, amount, catNum));
    }

    @Test
    void checkValidityOfFieldsInRecurringListTxt_checkIntervalFieldInvalid_failure() {
        String interval = "xxx";
        String endDate = "2020-12-31";
        assertThrows(MintException.class, () -> checkValidityOfFieldsInRecurringListTxt(interval, endDate));
    }

    @Test
    void checkValidityOfFieldsInRecurringListTxt_checkEndDateFieldInvalid_failure() {
        String interval = "MONTH";
        String endDate = "2000000";
        assertThrows(MintException.class, () -> checkValidityOfFieldsInRecurringListTxt(interval, endDate));
    }

    @Test
    void checkInvalidCatNum_checkInvalidCatNum_failure() {
        String catNum = "10";
        assertThrows(MintException.class, () -> ValidityChecker.checkInvalidCatNum(catNum));
    }
}
