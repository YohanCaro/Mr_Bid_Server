package persistence;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.BidTime;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.TypeProduct;
import com.prg3.mr_bid.persistence.BiddingPersistence;

public class RegisterBiddingTest {
	public static void main(String[] args) {
		BiddingPersistence persistence = new BiddingPersistence(new Gson());
		Bidding bidding = new Bidding("Subasta de Navaja Suiza (2)", 
				TypeProduct.OTHERS, 
				new Product("Navaja Suiza", "Sirve para apuñalar", null), 
				new BidTime(new BidDate(21, 5, 2019), 24), 
				new BidTime(new BidDate(21, 5, 2019), 24), 
				new BidTime(new BidDate(30, 5, 2019), 24), 
				true, true);
		try {
//			persistence.addNewBidding(bidding);
			Bidding bidding2 = persistence.getBiddingByName("Subasta de Navaja Suiza");
			System.out.println("Si sirve!"+bidding2.getTypeProduct());
//			persistence.deleteUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
