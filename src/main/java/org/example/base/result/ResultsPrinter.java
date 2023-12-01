package org.example.base.result;

import java.util.List;

public class ResultsPrinter {

    public static void log(List<YearResult> yearResults) {

        for (YearResult yearResult : yearResults) {
            System.out.println(yearResult.year());

            for (DayResult dayResult : yearResult.dayResults()) {

                System.out.println(dayResult.day() + " | " +
                                           dayResult.part1Result() + " | " +
                                           dayResult.part1Duration() + " | " +
                                           dayResult.part2Result() + " | " +
                                           dayResult.part2Duration() + " | ");
            }
        }
    }
}
