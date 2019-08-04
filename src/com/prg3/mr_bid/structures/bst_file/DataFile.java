package com.prg3.mr_bid.structures.bst_file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.prg3.mr_bid.utilities.Utilities;

/**
 * Clase DataFile - 
 *
 * @author Yohan Caro
 * @version 1.0 - 17/07/2019
 */
public class DataFile <T extends IDataRecorder<T>> extends RandomAccessFile {

	protected long quantity;
	public final int recordSize;
	private Class<T> classType;
	
	public DataFile(String path, int size, Class<T> classType) throws FileNotFoundException {
		super(path,"rw");
		this.recordSize = size;
		this.classType = classType;
		try {
			this.seek(0);
			this.quantity = this.readLong();
		} catch (IOException e) {
			this.quantity = 0;
		}
	}
	
	public void write(T data, long index) throws IOException {
		this.quantity++;
		this.seek(0);
		this.writeLong(this.quantity);
		this.seek((this.recordSize * index) + 8);
		this.write(data.getBytes());
	}

	public T read(long index) throws IOException {
		this.seek((this.recordSize * index) + 8);
		byte[] b = new byte[this.recordSize];
		this.read(b);
		T t = null;
		if(!Utilities.isByteArrayEmpty(b)) {
			try {
				t = this.classType.newInstance();
				t = t.getData(b);
			} catch (InstantiationException | IllegalAccessException e ) {
				System.err.println(e.getMessage());
			}
		}
		return t;
	}
	
	
}
