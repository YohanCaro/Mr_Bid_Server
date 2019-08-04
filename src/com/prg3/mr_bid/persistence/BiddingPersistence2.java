package com.prg3.mr_bid.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.structures.bst_file.BSTFile;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

public class BiddingPersistence2 {
	private BSTFile<Bidding> bstBiddings;
	private Comparator<Bidding> bComparator;
	
	public BiddingPersistence2(String pathBiddsFile, String indexBSTFile) throws FileNotFoundException {
		bComparator = new Comparator<Bidding>() {
			@Override
			public int compare(Bidding o1, Bidding o2) {
				return (int) (o1.getId()-o2.getId());
			}
		};
		bstBiddings = new BSTFile<>(Bidding.class, Bidding.RECORD_SIZE, 
				indexBSTFile, pathBiddsFile, bComparator);
	}
	
	public void addNewBidding(Bidding bidding) throws IOException {
		bstBiddings.add(bidding);
	}
	
	public void deleteBidding(Bidding bidding) {
		bstBiddings.delete(bidding);
	}
	
	public void updateBiddings(SimpleList<Bidding> biddings) throws IOException {
		long currentIndex = 0;
		Cursor<Bidding> bidCursor = new Cursor<>(biddings);
		while(!bidCursor.isOut()) {
			bstBiddings.insert(currentIndex, bidCursor.getInfo());
			currentIndex++;
		}
	}
	
	public void updateBidding(Bidding bidding) throws IOException {
		bstBiddings.insert(bidding.getId(), bidding);
	}
	
	public SimpleList<Bidding> getAllBiddings() {
		SimpleList<Bidding> bidList = new SimpleList<>();
		long tempIndex = 0;
		Bidding bidding = null;
		do {
			bidding = bstBiddings.getData(tempIndex);
			if(bidding!=null) {
				bidList.add(bidding);
			}
			tempIndex++;
		}while(bidding!=null);
		return bidList;
	}
	
	public Bidding getBiddingById(long id) {
		return bstBiddings.getData(id);
	}
	
}
