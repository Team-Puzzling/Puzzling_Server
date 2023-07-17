package com.puzzling.puzzlingServer.common.util;

import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class DateUtil {

    public static Boolean checkTodayIsReviewDay(String today, String reviewCycle) {
        String dayOfWeekKorean = getDayOfWeek(today);
        List<String> weekdayList = Arrays.asList(reviewCycle.split(","));
        return weekdayList.contains(dayOfWeekKorean);
    }

    public static String getDayOfWeek(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate todayDate = LocalDate.parse(dateStr, formatter);

        DayOfWeek dayOfWeek = todayDate.getDayOfWeek();
        Locale koreanLocale = new Locale("ko", "KR");
        String dayOfWeekKorean = dayOfWeek.getDisplayName(TextStyle.SHORT, koreanLocale);

        return dayOfWeekKorean;
    }
}
