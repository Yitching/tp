package seedu.duke;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;


public class Sorter  {

    public static Comparator<Entry> compareByName = Comparator.comparing(i -> i.getName());

    public static Comparator<Entry> compareByAmount = (i, j) -> (int)(j.getAmount() - i.getAmount());

    public static Comparator<Entry> compareByDate = (i, j) -> j.getDate().compareTo(i.getDate());

    public static Comparator<Entry> compareByCategory = (i, j) -> j.getCategory().compareTo(i.getCategory());



    public static void trimByYear(ArrayList<Entry> outputArray, String year) {
        outputArray.removeIf(entry -> entry.getDate().getYear() != Integer.parseInt(year));
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
