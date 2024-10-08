package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class HabrCareerDateTimeParserTest {

    @Test
    public void formatDateTime1isTrue() {
        HabrCareerDateTimeParser parser= new HabrCareerDateTimeParser ();
        String input = "2024-09-18T11:03:12+03:00";
        String expected = "2024-09-18T11:03:12";
        LocalDateTime result = parser.parse(input);
        assertThat(result).isEqualTo(expected);
    }
    @Test
    public void formatDateTime2isTrue() {
        HabrCareerDateTimeParser parser= new HabrCareerDateTimeParser ();
        String input = "2024-10-06T10:07:35+03:00";
        String expected = "2024-10-06T10:07:35";
        LocalDateTime result = parser.parse(input);
        assertThat(result).isEqualTo(expected);
    }
    @Test
    public void formatDateTime3isTrue() {
        HabrCareerDateTimeParser parser= new HabrCareerDateTimeParser ();
        String input = "2024-10-05T11:56:42+03:00";
        String expected = "2024-10-05T11:56:42";
        LocalDateTime result = parser.parse(input);
        assertThat(result).isEqualTo(expected);
    }

}