package persistence;

import java.io.UnsupportedEncodingException;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Gender;
import com.prg3.mr_bid.model.entity.TypeDocument;
import com.prg3.mr_bid.model.entity.User;

public class BytesUserTest {
	public static void main(String[] args) {
		User user = new User("Luis", "Martinez", "luis1234@hotmail.com", "contraseña v.",
				new BidDate(21, 2, 1978), 
				"2131231",TypeDocument.CEDULA,Gender.MALE,
				new CreditCard(new BidDate(18, 4, 2020), "holder", "84923481", "24519201"));			
		try {
			byte[] bytes = user.getBytes();
			System.out.println("imprimiendo bytes");
			for (int i = 0; i < bytes.length; i++) {
				System.out.print(bytes[i]+" ");
			}
			System.out.println("\nReconstruyendo objeto...");
			User newUser = user.getData(bytes);
			System.out.println(newUser.toString());
			System.out.println(newUser.getPassword());
		} catch (UnsupportedEncodingException e) {
		}
		
	}
}
