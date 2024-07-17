package com.melogtm.wikinhistory.model;

public class Event {
    private String eventMonthAndDay;
    private String eventYear;
    private String eventUrlImage;
    private String eventDescription;
    private String eventType;

    public Event() {}

    public Event(String eventMonthAndDay, String eventYear, String eventUrlImage, String eventDescription,
                 String eventType) {
        this.eventMonthAndDay = eventMonthAndDay;
        this.eventYear = eventYear;
        this.eventUrlImage = eventUrlImage;
        this.eventDescription = eventDescription;
        this.eventType = eventType;
    }

    public String getEventMonthAndDay() {
        return eventMonthAndDay;
    }

    public void setEventMonthAndDay(String eventDate) {
        this.eventMonthAndDay = eventDate;
    }

    public String getEventUrlImage() {
        return eventUrlImage;
    }

    public void setEventUrlImage(String eventUrlImage) {
        this.eventUrlImage = eventUrlImage;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventYear() {
        return eventYear;
    }

    public void setEventYear(String eventYear) {
        this.eventYear = eventYear;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventDate=" + eventMonthAndDay +
                ", eventUrlImage='" + eventUrlImage + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventYear='" + eventYear + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
