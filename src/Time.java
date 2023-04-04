public class Time implements Cloneable, Comparable<Time> {

    // fields to store the hour, minute, and AM/PM period of the time
    private int hour;
    private int minute;
    private boolean isPM;

    // constructor that takes an hour and minute (integers) and a boolean value representing
    // whether it is in the AM (false) or PM (true) time period.
    public Time(int hour, int minute, boolean isPM) {
        // check if hour is between 1 and 12 inclusive
        if (hour < 1 || hour > 12) {
            throw new IllegalArgumentException("Invalid hour: " + hour);
        }
        // check if minute is between 0 and 59 inclusive
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid minute: " + minute);
        }
        // set the fields
        this.hour = hour;
        this.minute = minute;
        this.isPM = isPM;
    }

    // static method that accepts a string such as "12: 03 PM" and converts it into a Time object,
    // which is returned. If the string is in an invalid format, such as missing a colon or AM/PM
    // or number, or if anything else goes wrong, throw an IllegalArgumentException.
    public static Time fromString(String str) {
        str = str.trim();
        String[] parts = str.split("[:\\s]+");
    
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time string: " + str);
        }
    
        try {
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
    
            // Convert the 24-hour format to 12-hour format with AM/PM
            boolean isPM = hour >= 12;
            if (hour > 12) {
                hour -= 12;
            } else if (hour == 0) {
                hour = 12;
            }
    
            return new Time(hour, minute, isPM);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time string: " + str);
        }
    }
    

    // clone method that returns a copy of the object
    @Override
    public Time clone() {
        try {
            return (Time) super.clone();
        } catch (CloneNotSupportedException e) {
            // should never happen since we are Cloneable
            throw new RuntimeException(e);
        }
    }

    // compareTo method that returns an integer indicating this time's placement in the natural ordering
    // of times relative to the given other time, as per the contract of compareTo from the Java API Specification.
    // Times are ordered chronologically; for example: 12:00 AM < 8:17 AM < 11:59 AM < 12:00 PM < 3:45 PM < 11:59 PM.
    @Override
    public int compareTo(Time other) {
        // compare the hours
        int result = Integer.compare(this.get24Hour(), other.get24Hour());
        // if the hours are equal, compare the minutes
        if (result == 0) {
            result = Integer.compare(this.minute, other.minute);
        }
        return result;
    }

    public int get24Hour() {
        if (hour == 12) {
            // special case for 12:00 PM and 12:00 AM
            return isPM ? 12 : 0;
        } else {
            // for other hours, convert from 12-hour format to 24-hour format
            return hour + (isPM ? 12 : 0);
        }
    }
    

    // equals method that returns true if and only if o refers to a Time object with exactly the same state
    // as this one; otherwise returns false.
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Time)) {
            return false;
        }
        Time other = (Time) o;
        return this.hour == other.hour && this.minute == other.minute && this.isPM == other.isPM;
    }

    public Time addMinutes(int minutes) {
        int totalMinutes = (hour * 60) + minute + minutes;
        hour = (totalMinutes / 60) % 12;
        if (hour == 0) {
            hour = 12;
        }
        minute = totalMinutes % 60;
        isPM = totalMinutes >= 720; // 12:00 PM is 720 minutes after midnight
        return null;
    }

    public int minutesUntil(Time other) {
        int thisMinutes = this.get24Hour() * 60 + this.minute;
        int otherMinutes = other.get24Hour() * 60 + other.minute;
        return otherMinutes - thisMinutes;
    }
    

}

