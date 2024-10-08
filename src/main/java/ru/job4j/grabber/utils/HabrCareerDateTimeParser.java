package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String parse) {
        return LocalDateTime.parse(String.valueOf(LocalDateTime.parse(parse, DateTimeFormatter.ISO_OFFSET_DATE_TIME)),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}