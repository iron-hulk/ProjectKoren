package com.mobileagent.app.utilities;

/**
 * Created by ironhulk on 2014/07/29.
 * Wrapper object used to persist context information
 */
public class ContextObject{
    private Long id;
    //Location Data
    private String Latitude;
    private String Longitude;
    private String Address;
    private String Code;
    private String Speed;
    private String Accuracy;
    private String TimeSinceUpdate;
    //Environment
    private String DeviceEnclosed;
    
    //Schedule
    private String CalendarEventTitle;
    private String CalendarEventOrganizer;
    private String CalendarEventStart;
    private String CalendarEventEnd;
    private String CalendarEventLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getSpeed() {
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

	public String getAccuracy() {
		return Accuracy;
	}

	public void setAccuracy(String accuracy) {
		Accuracy = accuracy;
	}

	public String getTimeSinceUpdate() {
		return TimeSinceUpdate;
	}

	public void setTimeSinceUpdate(String timeSinceUpdate) {
		TimeSinceUpdate = timeSinceUpdate;
	}

	public String getDeviceEnclosed() {
		return DeviceEnclosed;
	}

	public void setDeviceEnclosed(String deviceEnclosed) {
		DeviceEnclosed = deviceEnclosed;
	}

	public String getCalendarEventTitle() {
		return CalendarEventTitle;
	}

	public void setCalendarEventTitle(String calendarEventTitle) {
		CalendarEventTitle = calendarEventTitle;
	}

	public String getCalendarEventOrganizer() {
		return CalendarEventOrganizer;
	}

	public void setCalendarEventOrganizer(String calendarEventOrganizer) {
		CalendarEventOrganizer = calendarEventOrganizer;
	}

	public String getCalendarEventStart() {
		return CalendarEventStart;
	}

	public void setCalendarEventStart(String calendarEventStart) {
		CalendarEventStart = calendarEventStart;
	}

	public String getCalendarEventEnd() {
		return CalendarEventEnd;
	}

	public void setCalendarEventEnd(String calendarEventEnd) {
		CalendarEventEnd = calendarEventEnd;
	}

	public String getCalendarEventLocation() {
		return CalendarEventLocation;
	}

	public void setCalendarEventLocation(String calendarEventLocation) {
		CalendarEventLocation = calendarEventLocation;
	}
}
