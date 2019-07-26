package com.prg3.mr_bid.structures.bst_file;

/**
 * Clase DataRecorder - 
 *
 * @author Yohan Caro
 * @version 1.0 - 17/07/2019
 */
public interface IDataRecorder <T> {

	/**
	 * Obtener los bytes de un objeto
	 * @return array de bytes
	 */
	public abstract byte[] getBytes();
	
	/**
	 * Convierte un array de bytes en un objeto
	 * @param bytes array
	 * @return T objeto
	 */
	public abstract T getData(byte[] bytes);
	
}
