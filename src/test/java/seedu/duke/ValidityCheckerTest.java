package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ValidityCheckerTest {

    @Test
    public void checkEmptyName_emptyString_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkEmptyName(""));
        assertEquals(thrown.getMessage(), MintException.ERROR_NO_NAME);
    }

    @Test
    public void checkEmptyName_nonEmptyString_noException() {
        try {
            ValidityChecker.checkEmptyName("haha");
        } catch (MintException e) {
            fail();
        }
    }

    @Test
    public void checkInvalidAmount_empty_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidAmount(""));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_AMOUNT);
    }

    @Test
    public void checkInvalidAmount_alphabetMixed_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidAmount("24d"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_AMOUNT);
    }

    @Test
    public void checkInvalidAmount_infinity_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidAmount("infinity"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_AMOUNT);
    }

    @Test
    public void checkInvalidAmount_biggerThanMillion_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidAmount("1000001"));
        assertEquals(thrown.getMessage(), MintException.ERROR_AMOUNT_TOO_LARGE);
    }

    @Test
    public void checkInvalidAmount_million_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidAmount("1000000"));
        assertEquals(thrown.getMessage(), MintException.ERROR_AMOUNT_TOO_LARGE);
    }

    @Test
    public void checkInvalidAmount_negative_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidAmount("-2.8"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_AMOUNT);
    }

    @Test
    public void checkEmptyName_validNumber_noException() {
        try {
            ValidityChecker.checkInvalidAmount("999999.9");
        } catch (MintException e) {
            fail();
        }
    }

    @Test
    public void checkInvalidDate_farAwayYear_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidDate("1999-01-01"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_YEAR);
    }

    @Test
    public void checkInvalidDate_letters_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidDate("abc"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_DATE);
    }

    @Test
    public void checkInvalidDate_missingFields_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidDate("2020-01"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_DATE);
    }

    @Test
    public void checkInvalidDate_validDate_noException() {
        try {
            ValidityChecker.checkInvalidDate("2021-10-29");
        } catch (MintException e) {
            fail();
        }
    }

    String startDate = "2020-01-29";

    @Test
    public void checkInvalidEndDate_farAwayYear_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidEndDate("1999-01-01", startDate));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_YEAR);
    }

    @Test
    public void checkInvalidEndDate_letters_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidEndDate("abc", startDate));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_DATE);
    }

    @Test
    public void checkInvalidEndDate_missingFields_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidEndDate("2020-01", startDate));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_DATE);
    }

    @Test
    public void checkInvalidEndDate_beforeStartDate_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidEndDate("2019-01-12", startDate));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_END_DATE);
    }

    @Test
    public void checkInvalidEndDate_sameAsStartDate_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidEndDate("2020-01-29", startDate));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_END_DATE);
    }

    @Test
    public void checkInvalidEndDate_validDate_noException() {
        try {
            ValidityChecker.checkInvalidEndDate("2021-10-13", startDate);
        } catch (MintException e) {
            fail();
        }
    }

    @Test
    public void checkInvalidCatNum_outOfBound_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidCatNum("8"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_CATNUM);
    }

    @Test
    public void checkInvalidCatNum_noneNumber_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidCatNum("a"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_CATNUM);
    }

    @Test
    public void checkInvalidInterval_invalidInterval_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidInterval("week"));
        assertEquals(thrown.getMessage(), MintException.ERROR_INVALID_INTERVAL);
    }

    @Test
    public void checkInvalidInterval_validInterval_noException() {
        try {
            ValidityChecker.checkInvalidInterval("MonTh");
        } catch (MintException e) {
            fail();
        }
    }

    int size = 5;

    @Test
    public void checkInValidIndex_outOfBound_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidIndex("6", size));
        assertEquals(thrown.getMessage(), MintException.ERROR_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void checkInValidIndex_negative_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidIndex("-1", size));
        assertEquals(thrown.getMessage(), MintException.ERROR_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void checkInValidIndex_nonInteger_throwException() {
        Exception thrown = assertThrows(MintException.class,
                () -> ValidityChecker.checkInvalidIndex("1.5", size));
        assertEquals(thrown.getMessage(), MintException.ERROR_INDEX_INVALID_NUMBER);
    }

    @Test
    public void checkInValidIndex_validInterval_noException() {
        try {
            ValidityChecker.checkInvalidIndex("5", size);
        } catch (MintException e) {
            fail();
        }
    }
}
