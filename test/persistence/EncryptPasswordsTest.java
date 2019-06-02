package persistence;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.prg3.mr_bid.persistence.FileOperations;


public class EncryptPasswordsTest {
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String password = "Esta es una contraseÒa equisde";
		
		final String frase = "FraseLargaConDiferentesLetrasNumerosYCaracteresEspeciales_·¡È…ÌÕÛ”˙⁄¸‹Ò—1234567890!#%$&()=%_NO_USAR_ESTA_FRASE!_";
		final MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(frase.getBytes("UTF-8"));
		final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

		final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		
			aes.init(Cipher.ENCRYPT_MODE, key);
		 
		
		byte[] bytes = password.getBytes("UTF-8");
		Cipher aes2 = aes;
		final byte[] cifrado = aes2.doFinal(bytes);
		
		System.out.println("ContraseÒa encriptada: "+Arrays.toString(cifrado));
		
		aes.init(Cipher.DECRYPT_MODE, key);
		
		aes2 = aes;
		bytes = aes.doFinal(cifrado);
		final String desencrypted = new String(bytes, "UTF-8");
		
		System.out.println("contraseÒa desencriptada: "+desencrypted);
		
	}
}
