package ru.nsu.ccfit.nsuschedule.data.nsu.ics.parser;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;

public class NsuIcsParser {

    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final String EQUALLY = "=";

    public static class ParseValue {
        private static final String LOCATION = "LOCATION";
        private static final String DESCRIPTION = "DESCRIPTION";
        private static final String SUMMARY = "SUMMARY";
        private static final String RRULE = "RRULE";
        private static final String INTERVAL = "INTERVAL";
        private static final String UNTIL = "UNTIL";
        private static final String DTSTART = "DTSTART";
        private static final String DTEND = "DTEND";
        private static final String VEVENT = "VEVENT";

        private ParseValue() {
        }
    }

    public List<Event> parse(File file) throws IOException, ParserException {
        List<Event> resultList = new ArrayList<>();

        FileInputStream fin = new FileInputStream(file);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        for (Component component : (Iterable<Component>) calendar.getComponents()) {
            if (component.getName().equals(ParseValue.VEVENT)) {
                Event event = new Event();
                for (Property property : (Iterable<Property>) component.getProperties()) {
                    propertyHandler(property, event, propertyMap.get(property.getName()));
                }
                resultList.add(event);
            }
        }

        return resultList;
    }

    private static void propertyHandler(Property property, Event event, BiFunction<Property, Event, Event> function) {
        if (function != null) {
            function.apply(property, event);
        }
    }
}
