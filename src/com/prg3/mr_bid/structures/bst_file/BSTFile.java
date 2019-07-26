package com.prg3.mr_bid.structures.bst_file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

/**
 * Clase BSTFile - 
 *
 * @author Yohan Caro
 * @version 1.0 - 17/07/2019
 */
public class BSTFile <T extends IDataRecorder<T>> {
	
	protected static final long NULL = -1;
	
	private long rootIndex;
	private DataFile<T> masterDataBin;
	private DataFile<BSTNode> idDataBin;
	private Comparator<T> comparator;
	
	public BSTFile(Class<T> classType, int recordSize, String fileName, String masterFileName, Comparator<T> comparator)
			throws FileNotFoundException {
		this.masterDataBin = new DataFile<>(masterFileName, recordSize, classType);
		this.idDataBin = new DataFile<>(fileName, BSTNode.RECORD_SIZE, BSTNode.class);
		this.comparator = comparator;
		this.rootIndex = this.masterDataBin.quantity + NULL;
	}
	
	public void add(T data) throws IOException {
		if (rootIndex == NULL) {
			rootIndex++;
			this.idDataBin.write(new BSTNode(rootIndex), rootIndex);
		} else {
			rootIndex++;
			insert(0, data);
		}
		this.masterDataBin.write(data, rootIndex);

	}

	private void insert(long index, T data) throws IOException {
		T t = this.masterDataBin.read(index);
		int compValue = this.comparator.compare(data, t);
		BSTNode nodeBin = this.idDataBin.read(index);

		if (compValue < 0) {
			if (nodeBin.leftIndex == NULL) {
				nodeBin.leftIndex = this.rootIndex;
				this.idDataBin.write(new BSTNode(this.rootIndex), this.rootIndex);
				this.idDataBin.write(nodeBin, nodeBin.dataIndex);
			} else {
				this.insert(nodeBin.leftIndex, data);
			}
		} else if (compValue > 0) {
			if (nodeBin.rightIndex == NULL) {
				nodeBin.rightIndex = this.rootIndex;
				this.idDataBin.write(new BSTNode(this.rootIndex), this.rootIndex);
				this.idDataBin.write(nodeBin, nodeBin.dataIndex);
			} else {
				this.insert(nodeBin.rightIndex, data);
			}
		}
	}

	public void delete(T data) {
		rootIndex--;
	}

	public long search(T data) {
		return this.search(data, this.comparator);
	}

	public long search(T data, Comparator<T> comparator) {
		return search(0, data, comparator);
	}

	private long search(long index, T data, Comparator<T> comparator) {
		T t = null;
		int compValue = 0;
		BSTNode nodeBin = null;
		try {
			t = this.masterDataBin.read(index);
			compValue = comparator.compare(data, t);
			if (compValue == 0)
				return index;
			nodeBin = this.idDataBin.read(index);
		} catch (IOException e) {
			return NULL;
		}

		if (compValue < 0)
			return search(nodeBin.leftIndex, data, comparator);
		else if (compValue > 0)
			return search(nodeBin.rightIndex, data, comparator);
		return NULL;
	}

	public T getData(long index) {
		T t = null;
		try {
			t = this.masterDataBin.read(index);
		} catch (IOException e) {
		}
		return t;
	}
	

}
