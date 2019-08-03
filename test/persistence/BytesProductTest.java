package persistence;

import java.io.UnsupportedEncodingException;

import com.prg3.mr_bid.model.entity.Product;
import com.prg3.mr_bid.utilities.DataOperations;

public class BytesProductTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		Product product = new Product("Navaja Suiza", "Sirve para apuñalar",
				"data/biddingImages/bidding_02.png");
		System.out.println("Imprimiendo producto: \nNombre: "+product.getNameProduct()
				+"\nDescripción: "+product.getDescription()+"\nPath Imagen: "+product.getImage());
		byte[] productBytes = product.getBytes();
		System.out.println("Imprimiendo objeto en bytes: ");
		for (int i = 0; i < productBytes.length; i++) {
			System.out.print(productBytes[i]+" ");
		}
		System.out.println();
		
		Product sameProd = DataOperations.getInstanceOf().getProductByBytes(productBytes);
		System.out.println(sameProd.getNameProduct()+"|nombre");
		System.out.println(sameProd.getDescription()+"|Descripción");
		System.out.println(sameProd.getImage()+"|ruta imagen");
	}
}
