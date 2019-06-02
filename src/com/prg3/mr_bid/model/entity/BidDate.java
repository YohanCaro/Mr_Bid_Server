package com.prg3.mr_bid.model.entity;

/**
 * Clase BidDate - Maneja las fechas
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class BidDate {
	
	private short day;
	private short month;
	private short year;
		
	/**
	 * Crea una fecha con un dia, un mes y un año
	 * @param day dia
	 * @param month mes
	 * @param year año
	 */
	public BidDate(short day, short month, short year) {
		this.day = day;
		this.month = (short) (month-1);
		this.year = year;
	}
		
	/**
	 * Crea una fecha con datos enteros
	 * @param day dia
	 * @param month mes
	 * @param year año
	 */
	public BidDate(int day, int month, int year) {
		this((short)day, (short) (month), (short)year);
	}
	
	/**
	 * Obtiene el día
	 * @return day dia
	 */
	public short getDay() {
		return day;
	}
	
	/**
	 * Obtiene el mes
	 * @return month mes
	 */
	public short getMonth() {
		return month;
	}
	
	/**
	 * Obtiene el año
	 * @return year año
	 */
	public short getYear() {
		return year;
	}
	
	/**
	 * Pasa la fecha a un formato string 
	 * @return string (dd-mm-yyyy)
	 */
	public String getDateString() {
		return day + "/" + month + "/" + year;
	}
	
}
