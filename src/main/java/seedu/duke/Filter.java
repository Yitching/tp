package seedu.duke;

import seedu.duke.parser.ValidityChecker;

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

    public static ArrayList<Entry> filterEntryByAmount(Double amount, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getAmount() == amount)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }


    public static ArrayList<Entry> filterEntryByCategory(Enum category, ArrayList<Entry> listToFilter) {
        ArrayList<Entry> filteredArrayList = listToFilter
                .stream()
                .filter(e -> e.getCategory().equals(category))
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredArrayList;
    }
}
