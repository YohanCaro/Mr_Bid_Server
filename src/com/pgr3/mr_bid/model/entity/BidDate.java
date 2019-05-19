package com.pgr3.mr_bid.model.entity;

public class BidDate {
	
	private short day;
	private Month month;
	private short year;
	
	public BidDate(short day, Month month, short year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public BidDate(short day, int month, short year) {
		this.day = day;
		this.month = Month.values()[month];
		this.year = year;
	}
	
	public BidDate(int day, int month, int year) {
		this((short)day, Month.values()[month-2], (short)year);
	}

	public short getDay() {
		return day;
	}
	
	public Month getMonth() {
		return month;
	}
	
	public short getYear() {
		return year;
	}
	
	public String getDateString() {
		return day + "/" + (month.getValue()+1) + "/" + year;
	}
	
}
