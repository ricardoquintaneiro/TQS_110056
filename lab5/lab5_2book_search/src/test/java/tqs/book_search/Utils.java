package tqs.book_search;

import java.time.LocalDate;

public class Utils {

    public static LocalDate localDateFromDateParts(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

}
