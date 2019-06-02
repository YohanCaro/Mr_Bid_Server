package com.prg3.mr_bid.model.entity;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase BidTime - Crea una fecha con una hora especifica
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class BidTime {
	
	private BidDate date;
	private float hours;
	private Calendar calendar;
	
	/**
	 * Constructor que crea un tiempo de subasta con:
	 * @param date fecha
	 * @param hours hora
	 */
	public BidTime(BidDate date, float hours) {
		this.date = date;
		this.hours = hours;
		calendar = new GregorianCalendar();
	}
	
	/**
	 * Convierte la edad
	 * @return edad
	 */
	public short getTimeOnDays() {
		short year = (short) (date.getYear() - calendar.get(calendar.YEAR));
		short month = (short) (date.getMonth() - calendar.get(calendar.MONTH));
		short day = (short) (date.getDay() - calendar.get(calendar.DAY_OF_MONTH));
		
		return (short) (year*365 + month*30 + day);
	}
	
	/**
	 * Obtiene el tiempo en horas 
	 * @return horas t
	 */
	public float getTimeOnHours() {
		return this.getTimeOnDays()*24;
	}
	
	/**
	 * Obtiene la fecha
	 * @return date
	 */
	public BidDate getDate() {
		return date;
	}
	
	/**
	 * Obtiene la hora
	 * @return hour
	 */
	public float getHours() {
		return hours;
	}

}
