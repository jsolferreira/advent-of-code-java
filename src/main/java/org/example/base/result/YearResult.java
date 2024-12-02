package org.example.base.result;

import java.util.List;

public record YearResult(String year, List<DayResult> dayResults) {
}
