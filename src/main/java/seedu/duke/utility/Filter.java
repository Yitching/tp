package seedu.duke.utility;

import seedu.duke.entries.Entry;
import seedu.duke.entries.RecurringEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class Filter {
    public static ArrayList<Entry> filterEntryByName(String name, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByDate(LocalDate date, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByEndDate(LocalDate date, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> ((RecurringEntry) e).getEndDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByInterval(String interval, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> ((RecurringEntry) e).getInterval().label.equals(interval))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }

    public static ArrayList<Entry> filterEntryByAmount(Double amount, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getAmount() == amount)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }


    public static ArrayList<Entry> filterEntryByCategory(int category, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getCategory().ordinal() == category)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }
}
