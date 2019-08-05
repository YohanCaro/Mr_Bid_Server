package persistence;

import java.io.FileNotFoundException;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.BidTime;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.TypeProduct;
import com.prg3.mr_bid.persistence.FileOperations;

public class BstBiddingTest {
	public static void main(String[] args) {
		Bidding bidding = new Bidding(1,"Subasta de Navaja Suiza", 
				TypeProduct.OTHERS, 
				new Product("Navaja Suiza", "Sirve para apuñalar", "data/biddingImages/bidding0_1.png"), 
				new BidTime(new BidDate(21, 5, 2019), 24), 
				new BidTime(new BidDate(5, 8, 2019), 6), 
				new BidTime(new BidDate(30, 8, 2019), 24), 
				true, true, "Don Luis" ,1000);
		try {
			FileOperations fileOperations2 = FileOperations.getInstanceOf();
			fileOperations2.addBidding(bidding);
			System.out.println("Subasta añadida al archivo...");
			System.out.println("Obteniendo subasta por id");
			Bidding newBidding = fileOperations2.getBiddingById(1);
			System.out.println(newBidding.toString());
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}
		
	}
}
