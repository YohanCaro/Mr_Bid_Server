package com.prg3.mr_bid.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

/**
 * A class for the bidding persistence
 * @author Luis!
 * @version 1.0 24/05/2019
 */
public class BiddingPersistence {
	private Gson gson;
	private File bidFile;
	private File commentFile;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	/**
	 * Constructor of the bidding persistence class
	 * @param gson a gson object to manage all the object conversion
	 */
	public BiddingPersistence(Gson gson) {
		this.gson = gson;
		bidFile = new File("data/appData/bidsData.json");
		commentFile = new File("data/appData/bidComments.json");
	}
	
	/**
	 * Adds a new bidding object into the bidding file
	 * @param bidding the bidding object to be added
	 * @throws Exception
	 */
	public void addNewBidding(Bidding bidding) throws Exception {
		openFile('w',true);
		bufferedWriter.write(gson.toJson(bidding));
		bufferedWriter.newLine();
		closeFile('w');
	}
	
	/**
	 * Deletes a bidding object from the bidding file
	 * @param bidding
	 * @throws IOException
	 */
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
	
	/**
	 * Updates the information of the bidding file
	 * @param biddings an array list of all the existing bidding objects
	 * @throws IOException
	 */
	public void updateBiddings(SimpleList<Bidding> biddings) throws IOException {
		String data = "";
		for (int i = 0; i < biddings.size(); i++) 
			data+=gson.toJson(biddings.get(i))+"\n";
		this.overWriteFile(data);
	}
	
	/**
	 * Extracts all the bidding objects from the file
	 * @return an array list with all the bidding objects
	 * @throws IOException
	 */
	public SimpleList<Bidding> getAllBiddings() throws IOException {
		SimpleList<Bidding> biddings = new SimpleList<Bidding>();
		openFile('r', true);
		String line = "";
		while((line = bufferedReader.readLine())!=null) {
			biddings.add(gson.fromJson(line, Bidding.class));
		}		
		closeFile('r');
		return biddings;
	}
	
	/**
	 * Gets a bidding object from the bidding file 
	 * @param biddingName the name of the bidding to be returned
	 * @return a bidding object
	 * @throws IOException
	 */
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
	
	/**
	 * Gets all the comments of a specific bidding
	 * @param id the identifier of the selected bidding
	 * @return an arrayList with all the comments of the bidding
	 * @throws IOException file exception
	 */
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
	/**
	 * Adds a new comment to a chat into the chats file
	 * @param bidId the identifier of the bidding
	 * @param comment the comment to be added
	 * @throws IOException file exception
	 */
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
		overwriteChatFile(chats);
	}
	/**
	 * Overwrites the chats file
	 * @param dataFile a string with the new information of the file
	 * @throws IOException file exception
	 */
	private void overwriteChatFile(String chats) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(commentFile, false));
		String[] chatLines = chats.split("\n");
		for (long i = 0; i < chatLines.length; i++) {
			bw.write(chatLines[(int) i]);
			bw.newLine();
		}
		bw.close();
	}
	/**
	 * Deletes a line of the persistence file
	 * @param indexLine the index of the file line to be deleted
	 * @throws IOException file exception
	 */
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
	
	/**
	 * Overwrites the biddings file
	 * @param dataFile a string with the new information of the file
	 * @throws IOException file exception
	 */
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
