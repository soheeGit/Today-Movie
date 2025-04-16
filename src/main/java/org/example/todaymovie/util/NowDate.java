package org.example.todaymovie.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class NowDate {
    public static String str() {
        LocalDate nowDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        nowDate = nowDate.minusDays(1);
        return nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}