package persistence;

import com.google.gson.Gson;
import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Gender;
import com.prg3.mr_bid.model.entity.TypeDocument;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.UsersPersistence;

public class RegisterUserTest {
	public static void main(String[] args) {
		UsersPersistence persistence = new UsersPersistence(new Gson());
		User user = new User("Luis", "Martinez", "luis1234@hotmail.com", "contraseņa v.",
					new BidDate(21, 2, 1978), 
					"2131231",TypeDocument.CEDULA,Gender.MALE,new CreditCard(new BidDate(18, 4, 2020), "holder", "84923481", "24519201"));
		try {
			User user1 = persistence.getUserByFullName("Luis Martinez");
			System.out.println(new Gson().toJson(user1).getBytes().length);
			System.out.println(user1.getEmail()+" email");
//			persistence.addNewUser(user);
//			persistence.deleteUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
