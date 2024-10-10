package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";
    public static final int PAGES = 5;

    private static String retrieveDescription(String link) {
        String description = "";
        try {
            Connection connection = Jsoup.connect(link);
            Document document = connection.get();
            Elements rows = document.select(".faded-content__container");
            description = rows.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return description;
    }

    public static void main(String[] args) throws IOException {
        for (int pageNumber = 1; pageNumber <= PAGES; pageNumber++) {
            String fullLink = "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, pageNumber, SUFFIX);
            Connection connection = Jsoup.connect(fullLink);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element dateElement = row.select(".vacancy-card__date").first();
                Element timeElement = dateElement.child(0);
                String dateTimeAttr = timeElement.attr("datetime");
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String desc = retrieveDescription(link);
                System.out.printf("%s %s %s%n", dateTimeAttr, vacancyName, link);
                System.out.println(desc);
            });
        }
    }
}