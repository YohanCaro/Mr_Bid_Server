package com.prg3.mr_bid.structures.bst_file;

import com.prg3.mr_bid.utilities.Utilities;

/**
 * Clase BSTNode - 
 *
 * @author Yohan Caro
 * @version 1.0 - 17/07/2019
 */
public class BSTNode implements IDataRecorder<BSTNode> {
	
	public static final int RECORD_SIZE = 24;
	protected long dataIndex;
	protected long leftIndex;
	protected long rightIndex;
	
	public BSTNode(long dataIndex) {
		this.dataIndex = dataIndex;
		this.leftIndex = BSTFile.NULL;
		this.rightIndex = BSTFile.NULL;
	}

	public BSTNode() {
		this.dataIndex = BSTFile.NULL;
		this.leftIndex = BSTFile.NULL;
		this.rightIndex = BSTFile.NULL;
	}

	@Override
	public byte[] getBytes() {
		byte[] bytes = new byte[RECORD_SIZE];
		bytes = Utilities.completeBytes(bytes, Utilities.longToBytes(dataIndex), 0);
		bytes = Utilities.completeBytes(bytes, Utilities.longToBytes(leftIndex), 8);
		bytes = Utilities.completeBytes(bytes, Utilities.longToBytes(rightIndex), 16);
		return bytes;
	}

	@Override
	public BSTNode getData(byte[] bytes) {
		BSTNode node = new BSTNode();
		node.dataIndex = Utilities.bytesToLong(Utilities.cutBytes(bytes, 0, 8));
		node.leftIndex = Utilities.bytesToLong(Utilities.cutBytes(bytes, 8, 16));
		node.rightIndex = Utilities.bytesToLong(Utilities.cutBytes(bytes, 16, 24));
		return node;
	}
	
}
