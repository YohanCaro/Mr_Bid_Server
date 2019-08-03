package com.prg3.mr_bid.utilities;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.BidState;
import com.prg3.mr_bid.model.entity.BidTime;
import com.prg3.mr_bid.model.entity.Bidding;

/** Clase Utilities - Utilidades de la apicación
 *
 * @author Yohan Caro
 * @version 1.0 - 1/06/2019
 */
public class Utilities {
	
	/**
	 * Envia un mensaje de advertencia
	 * @param menssage mensjae
	 * @param title titulo del JOptionPane
	 */
	public static void showMessageWarning(String menssage, String title) {
		JOptionPane.showMessageDialog(null, menssage, title, JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Envia un mensaje de Error
	 * @param menssage mensjae
	 * @param title titulo del JOptionPane
	 */
	public static void showMessageError(String menssage, String title) {
		JOptionPane.showMessageDialog(null, menssage, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Envia un mensaje de información
	 * @param menssage mensjae
	 * @param title titulo del JOptionPane
	 */
	public static void showMessageInfo(String menssage, String title) {
		JOptionPane.showMessageDialog(null, menssage, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Pasa una cadena a un array de caracteres con un numero maxiomo de caracteres
	 * Si no pasa el numero de caracteres, llena con espacios
	 * @param text texto
	 * @param max maximo
	 * @return output array de chars
	 */
	public static char[] stringToCharArray(String text, int max) {
		char[] output = new char[max];
		for (int i = 0; i < max; i++) { //max*2
			if (i < text.length()) {
				output[i] = text.charAt(i);
			} else { 
				output[i] = 32; //Espacio en codigo ASCII
			}			
		}
		return output;
	}
	
	/**
	 * Le quita los espacios adicionales al final de la cadena
	 * @param string cadena
	 * @return string cadena
	 */
	public static String cutStringWhitAditionalSpace(String string) {
		for (int i = 0, count = 0; i < string.length(); i++) {
			count += (string.charAt(i)==32)?1:-count;
			if (count >= 2) {
				return string.substring(0, i-1);
			}
		}
		return string;
	}
	
	/**
	 * Convierte un int en un array bytes
	 * @param integer int
	 * @return bytes array
	 */
	public static byte[] intToBytes(int integer) {
		byte[] bytes = new byte[4];
		for (int i = 3; i >= 0; i--) {
			bytes[i] = (byte) (integer & 0xFF);
			integer >>= 4;
		}
		return bytes;
	}

	/**
	 * Convierte un long en un array de bytes
	 * @param num long
	 * @return bytes array
	 */
	public static byte[] longToBytes(long num) {
		byte[] bytes = new byte[8];
		for (int i = 7; i >= 0; i--) {
			bytes[i] = (byte) (num & 0xFF);
			num >>= 8;
		}
		return bytes;
	}
	
	/**
	 * Agrega un array de bytes a otro array de bytes
	 * NOTA: El array a añadir debe ser de menor tamaño al que se le va a añadir
	 * @param out array en donde se va a guardar
	 * @param add array que se va a añadir
	 * @param pos posicion del array en donde empezar
	 * @return out array de bytes
	 */
	public static byte[] completeBytes(byte[] out, byte[] add, int pos) {
		for (int i = 0; i < add.length; i++) {
			out[pos+i] = add[i];
		}
		return out;
	}
	
	/**
	 * Convierte una cadena en una array de bytes
	 * @param string cadena
	 * @return bytes array
	 */
	public static byte[] stringToBytes(String string) {
		byte[] bytes = new byte[string.length()];
		for (int i = 0; i < string.length(); i++) {
			bytes[i] = (byte) string.charAt(i);
		}
		return bytes;
	}
	
	/**
	 * Convierte una cadena en una fecha de tipo LocalDate
	 * Formato (yyyy-mm-dd)
	 * @param date fecha
	 * @return array a
	 */
	public static LocalDate stringToDateF1(String date) {
		String[] array = date.split("-");
		return LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
	}
	
	/**
	 * Convierte una cadena en una fecha de tipo LocalDate
	 * Formato (yyyy/mm/dd)
	 * @param date fecha
	 * @return array a
	 */
	public static LocalDate stringToDateF2(String date) {
		String[] array = date.split("/");
		return LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
	}
	
	/**
	 * Convierte un array de bytes a String
	 * 
	 * @param bytes array de bytes
	 * @return String con bytes decodificados
	 */
	public static String bytesToString(byte[] bytes) {
		String string = "";
		for (int i = 0; i < bytes.length; i++) {
			string += getCharTo(bytes[i]);
		}
		return string;
	}
	
	/**
	 * Convierte un array de bytes en un long
	 * @param bytes array
	 * @return num numero
	 */
	public static long bytesToLong(byte[] bytes) {
		long num = 0;
		for (int i = 0; i < 8; i++) {
			num <<= 8;
			num |= (bytes[i] & 0xFF);
		}
		return num;
	}
	
	/**
	 * Convierte un array de bytes en un entero
	 * @param bytes array
	 * @param initB donde iniciar
	 * @return num - numero
	 */
	public static int bytesToInt(byte[] bytes, int initB) {
		int num = 0;
		for (int i = initB; i < initB + 4; i++) {
			num <<= 4;
			num |= (bytes[i] & 0xFF);
		}
		return num;
	}
	
	/**
	 * Obtiene un character de un byte
	 * @param b byte
	 * @return char c
	 */
	public static char getCharTo(byte b) {
		return (char) (b < 0 ? b + 256 : b);
	}
	
	/**
	 * Corta los un array de bytes, en un posicion inicialy una final
	 * @param bytes b
	 * @param posI posicion inicial
	 * @param posF posicion final
	 * @return bytes out
	 */
	public static byte[] cutBytes(byte[] bytes, int posI, int posF) {
		byte[] out = new byte[posF-posI];
		for (int i = 0; i < posF-posI; i++) {
			out[i] = bytes[i+posI];
		}
		return out;
	}

}
