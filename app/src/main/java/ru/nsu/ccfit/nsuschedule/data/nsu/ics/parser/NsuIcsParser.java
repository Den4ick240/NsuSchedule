package ru.nsu.ccfit.nsuschedule.data.nsu.ics.parser;

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
}
