package com.pgr3.mr_bid.model.entity;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BidTime {
	
	private BidDate date;
	private float hours;
	private Calendar calendar;
	
	public BidTime(BidDate date, float hours) {
		this.date = date;
		this.hours = hours;
		calendar = new GregorianCalendar();
	}
	
	public short getTimeOnDays() {
		short year = (short) (date.getYear() - calendar.get(calendar.YEAR));
		short month = (short) (date.getMonth().getValue() - calendar.get(calendar.MONTH));
		short day = (short) (date.getDay() - calendar.get(calendar.DAY_OF_MONTH));
		
		return (short) (year*365 + month*30 + day);
	}
	
	public BidDate getDate() {
		return date;
	}
	
	public float getHours() {
		return hours;
	}

}
