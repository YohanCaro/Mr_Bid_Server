package persistence;

import java.io.UnsupportedEncodingException;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.BidTime;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.model.entity.TypeProduct;

public class BytesBiddingTest {
	public static void main(String[] args) {
		Bidding bidding = new Bidding(1,"Subasta de Navaja Suiza", 
				TypeProduct.OTHERS, 
				new Product("Navaja Suiza", "Sirve para apuñalar", "data/biddingImages/bidding0_1.png"), 
				new BidTime(new BidDate(21, 5, 2019), 24), 
				new BidTime(new BidDate(21, 5, 2019), 24), 
				new BidTime(new BidDate(30, 5, 2019), 24), 
				true, true, "Don Luis" ,1000);	
		try {
			byte[] bytes = bidding.getBytes();
			for (int i = 0; i < bytes.length; i++) {
				System.out.print(bytes[i]+" ");
			}
			System.out.println();
			System.out.println("Reconstruyendo objeto...");
			Bidding newBidding = bidding.getData(bytes);
			System.out.println(newBidding.toString());
		} catch (UnsupportedEncodingException e) {
			System.out.println("esto no debería pasar");
		}
	}
}
