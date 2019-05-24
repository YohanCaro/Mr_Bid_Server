package com.prg3.mr_bid.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.User;

public class BiddingPersistence {
	private Gson gson;
	private File file;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public BiddingPersistence(Gson gson) {
		this.gson = gson;
		file = new File("data/appData/bidsData.json");
	}
	
	public void addNewBidding(Bidding bidding) throws Exception {
		openFile('w',true);
		bufferedWriter.write(gson.toJson(bidding));
		bufferedWriter.newLine();
		closeFile('w');
	}
	
	public void deleteBidding(Bidding bidding) throws IOException {
		openFile('r',true);
		boolean finded=false;
		String line = "";
		int numLine=0;
		while(!finded&&(line = bufferedReader.readLine())!=null) {
			if(gson.fromJson(line, bidding.getClass()).getBiddingName().equals(bidding.getBiddingName())) {
				finded=true;
			}else 
				numLine++;
		}
		deleteLine(numLine);
		closeFile('r');
	}
	
	public Bidding getBiddingByName(String biddingName) throws IOException {
		Bidding bidding = null;
		openFile('r', true);
		String line = "";
		while(bidding==null&&(line = bufferedReader.readLine())!=null) {
			String[] splits = line.split("\"");
			if(splits[3].equals(biddingName)) 
				bidding = gson.fromJson(line, Bidding.class);
		}
		closeFile('r');			
		return bidding;
	}
	
	private void deleteLine(int indexLine) throws IOException {
		String dataFile = "";
		String currentLine="";
		int counter = 0;
		openFile('r', true);
		while((currentLine=bufferedReader.readLine())!=null) {
			if(counter!=indexLine) 
				dataFile+=currentLine+"\n";
			counter++;	
		}
		overWriteFile(dataFile);
	}
	
	private void overWriteFile(String dataFile) throws IOException {
		openFile('w',false);
		String[] lines = dataFile.split("\n");
		for (int i = 0; i < lines.length; i++) { 
			bufferedWriter.write(lines[i]);
			bufferedWriter.newLine();
		}
		closeFile('w');
	}
	
	/**
	 * Prepares the file for the read/write operations
	 * @param mode indicates the mode to open the file (w=write/r=read)
	 * @throws IOException Activated when the file doesn't exists
	 */
	private void openFile(char mode, boolean accumulate) throws IOException {
		switch (mode) {
		case 'w':
			FileWriter fileWriter = new FileWriter(file, accumulate);
			bufferedWriter = new BufferedWriter(fileWriter);
			break;
		case 'r':
			FileReader fileReader = new FileReader(file);				
			bufferedReader = new BufferedReader(fileReader);
			break;
		}
	}
	
	/**
	 * Closes the read/write objects of the file
	 * @param mode indicates the object to be closed (w=write/r=read)
	 * @throws IOException Activated when the file doesn't exists
	 */
	private void closeFile(char mode) throws IOException {
		switch (mode) {
		case 'w':
			bufferedWriter.close();
			break;
		case 'r':		
			bufferedReader.close();
			break;
		}
	}
}
