package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.CreditCard;
import com.prg3.mr_bid.model.entity.Gender;
import com.prg3.mr_bid.model.entity.TypeDocument;
import com.prg3.mr_bid.model.entity.User;
import com.prg3.mr_bid.persistence.FileOperations;
import com.prg3.mr_bid.utilities.Utilities;

/**
 * Test de la persistencia de usuarios por medio de una estructura bst en archivo plano
 * @author Luis!
 * @since 3/08/2019 v1.0
 */
public class BstUserTest {
	public static void main(String[] args) {
		User user = new User("Luis", "Martinez", "luis1234@hotmail.com", "contraseña v.",
				new BidDate(21, 2, 1978), 
				"2131231",TypeDocument.CEDULA,Gender.MALE,
				new CreditCard(new BidDate(18, 4, 2020), "holder", "84923481", "24519201"));
		try {
			System.out.println("Escribiendo en archivo de usuarios... ");
			FileOperations fileOperations2 = FileOperations.getInstanceOf();	
			fileOperations2.addUser(user);
			System.out.println("Leyendo del archivo...");
			User newUser = fileOperations2.getUserByEmail("luis1234@hotmail.com");
			System.out.println(newUser.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
