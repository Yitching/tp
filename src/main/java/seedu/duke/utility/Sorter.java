package seedu.duke.utility;

import seedu.duke.entries.Entry;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;


public class Sorter  {

    public static Comparator<Entry> compareByName = Comparator.comparing(Entry::getName);

    public static Comparator<Entry> compareByAmount = (i, j) -> (int)(j.getAmount() - i.getAmount());

    public static Comparator<Entry> compareByDate = (i, j) -> j.getDate().compareTo(i.getDate());

    public static Comparator<Entry> compareByCategory = Comparator.comparingInt(i -> i.getCategory().ordinal());



    public static void trimByYear(ArrayList<Entry> outputArray, int year) {
        outputArray.removeIf(entry -> entry.getDate().getYear() != year);
    }

    public static void trimByMonth(ArrayList<Entry> outputArray, Month month) {
        outputArray.removeIf(entry -> entry.getDate().getMonthValue() != month.getValue());
    }

    public static void trimFrom(ArrayList<Entry> outputArray, LocalDate from) {
        outputArray.removeIf(entry -> entry.getDate().isBefore(from));
    }

    public static void trimEnd(ArrayList<Entry> outputArray, LocalDate end) {
        outputArray.removeIf(entry -> entry.getDate().isAfter(end));
    }
}
