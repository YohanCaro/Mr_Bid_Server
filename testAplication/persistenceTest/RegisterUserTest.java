package persistenceTest;

import java.time.LocalDate;

import com.pgr3.mr_bid.model.entity.CreditCard;
import com.pgr3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.UsersPersistence;

public class RegisterUserTest {
	public static void main(String[] args) {
		UsersPersistence persistence = new UsersPersistence();
		User user = new User("Luis", "Martinez", "luis1234@hotmail.com", "contraseña v.",
					LocalDate.of(1978, 4, 14), false, 
					new CreditCard(LocalDate.of(2020, 4, 18), "holder", "84923481", "24519201"));
		try {
			persistence.addNewUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
