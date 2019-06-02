package com.prg3.mr_bid.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.Bidding;

public class BiddingPersistence {
	private Gson gson;
	private File bidFile;
	private File commentFile;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public BiddingPersistence(Gson gson) {
		this.gson = gson;
		bidFile = new File("data/appData/bidsData.json");
		commentFile = new File("data/appData/bidComments.json");
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
	
	public void updateBiddings(ArrayList<Bidding> biddings) throws IOException {
		String data = "";
		for (int i = 0; i < biddings.size(); i++) 
			data+=gson.toJson(biddings.get(i))+"\n";
		this.overWriteFile(data);
	}
	
	public ArrayList<Bidding> getAllBiddings() throws IOException {
		ArrayList<Bidding> biddings = new ArrayList<Bidding>();
		openFile('r', true);
		String line = "";
		while((line = bufferedReader.readLine())!=null) {
			biddings.add(gson.fromJson(line, Bidding.class));
		}		
		closeFile('r');
		return biddings;
	}
	
	public Bidding getBiddingByName(String biddingName) throws IOException {
		Bidding bidding = null;
		openFile('r', true);
		String line = "";
		while(bidding==null&&(line = bufferedReader.readLine())!=null) {
			String[] splits = line.split("\"");
			if(splits[5].equals(biddingName)) 
				bidding = gson.fromJson(line, Bidding.class);
		}
		closeFile('r');			
		return bidding;
	}
	
	public ArrayList<String> getBiddingChatById(long id) throws IOException{
		ArrayList<String> chat = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(commentFile));
		String currentLine = "";
		while((currentLine+=br.readLine())!=null&&chat.isEmpty()) {
			if(Long.parseLong(currentLine.split("|")[0])==id) {
				String[] currentChat = currentLine.split("|");
				currentLine="";
				for (int i = 1; i < currentChat.length; i++, currentLine+=currentChat[i]);
				chat = gson.fromJson(currentLine, ArrayList.class);
			}
		}		
		br.close();
		return chat;
	}
	
	public void addToChat(long bidId, String comment) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(commentFile));
		String chats = "";
		String currentLine="";
		boolean added = false;
		while((currentLine+=br.readLine())!=null) {
			if(Long.parseLong(currentLine.split("|")[0])==bidId) {
				String[] comments = currentLine.split("|");
				currentLine="";
				for (int i = 1; i < comments.length; i++, currentLine+=comments[i]); 
				ArrayList<String> bidChat = gson.fromJson(currentLine, ArrayList.class);
				bidChat.add(comment);
				chats+=gson.toJson(bidChat)+"\n";
				added = true;
			}else {
				chats+=currentLine+"\n";
			}				
		}
		br.close();
		if(!added) {
			ArrayList<String> newChat = new ArrayList<String>();
			newChat.add(comment);
			chats+=bidId+"|"+gson.toJson(newChat)+"\n";
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(commentFile, false));
		String[] chatLines = chats.split("\n");
		for (long i = 0; i < chatLines.length; i++) {
			bw.write(chatLines[(int) i]);
			bw.newLine();
		}
		bw.close();
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
	 * @param accumulate if it's true, the file doesn't overwrite
	 * @throws IOException Activated when the file doesn't exists
	 */
	private void openFile(char mode, boolean accumulate) throws IOException {
		switch (mode) {
		case 'w':
			FileWriter fileWriter = new FileWriter(bidFile, accumulate);
			bufferedWriter = new BufferedWriter(fileWriter);
			break;
		case 'r':
			FileReader fileReader = new FileReader(bidFile);				
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
