package com.prg3.mr_bid.model.entity;

import java.io.UnsupportedEncodingException;
import com.prg3.mr_bid.structures.bst_file.IDataRecorder;
import com.prg3.mr_bid.utilities.Utilities;

/**
 * Clase Bidding - Modelo de la subasta con todos sus datos
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Bidding implements IDataRecorder<Bidding>{
	public final static int RECORD_SIZE = 221;
	private BidInfo bidInfo;
	private String biddingName;
	private TypeProduct typeProduct;
	private Product product;
	private BidTime publicationTime;
	private BidTime initTime;
	private BidTime finishTime;
	private boolean isAutomaticIncremet;
	private boolean isPublic;
	private String owner;
	
	/**
	 * Contruye una nueva subasta con los siguientes datos
	 * @param biddingName nombre de la subasta
	 * @param typeProduct tipo de susbasta 
	 * @param product producto
	 * @param publicationTime tiempo de publicación
	 * @param initTime tiempo de inicio
	 * @param finishTime tiempo de finalizado
	 * @param isAutomaticIncremet define el tipo de incremento
	 * @param isPublic define si es publica
	 * @param emailUser Dueño de la subasta
	 */
	public Bidding(long id, int value, String biddingName, TypeProduct typeProduct, Product product, BidTime publicationTime,
			BidTime initTime, BidTime finishTime, boolean isAutomaticIncremet, boolean isPublic, String emailUser) {
		this.bidInfo = new BidInfo(id, value);
		this.biddingName = biddingName;
		this.typeProduct = typeProduct;
		this.product = product;
		this.publicationTime = publicationTime;
		this.initTime = initTime;
		this.finishTime = finishTime;
		this.isAutomaticIncremet = isAutomaticIncremet;
		this.isPublic = isPublic;
		this.owner = emailUser;
	}
	
	/**
	 * Contruye una subasta con los siguientes datos
	 * @param biddingName nombre de la subasta
	 * @param typeProduct tipo de susbasta 
	 * @param product producto
	 * @param publicationTime tiempo de publicación
	 * @param initTime tiempo de inicio
	 * @param finishTime teiempo de finalizado
	 * @param isAutomaticIncremet define el tipo de incremento
	 * @param isPublic define si es publica
	 * @param emailUser Dueño de la subasta
	 */
	public Bidding(long id, int value, String highestBidder, String biddingName, TypeProduct typeProduct, Product product, BidTime publicationTime,
			BidTime initTime, BidTime finishTime, boolean isAutomaticIncremet, boolean isPublic, String emailUser) {
		this.bidInfo = new BidInfo(id, highestBidder, value);
		this.biddingName = biddingName;
		this.typeProduct = typeProduct;
		this.product = product;
		this.publicationTime = publicationTime;
		this.initTime = initTime;
		this.finishTime = finishTime;
		this.isAutomaticIncremet = isAutomaticIncremet;
		this.isPublic = isPublic;
		this.owner = emailUser;
	}
	
	/**
	 * Constructor vacío de la clase Bidding. Nota: es usado en operaciones de persistencia con arboles bst
	 */
	public Bidding() {
	}
	
	/**
	 * Cambia el propietario
	 * @param owner pripietario
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Obtiene el nombre
	 * @return nombre n
	 */
	public String getBiddingName() {
		return biddingName;
	}

	/**
	 * Obtiene el tipo de producto
	 * @return typeProduct t
	 */
	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	/**
	 * Obtiene el producto
	 * @return product p
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Obtiene el tiempo de publicación
	 * @return publicationTime pt
	 */
	public BidTime getPublicationTime() {
		return publicationTime;
	}

	/**
	 * Obtiene el tiempo de inicio
	 * @return initTime it
	 */
	public BidTime getInitTime() {
		return initTime;
	}

	/**
	 * Obtiene el tiempo de finalizado
	 * @return finishTime ft
	 */
	public BidTime getFinishTime() {
		return finishTime;
	}
	
	/**
	 * Obtiene 
	 * @return value
	 */
	public int getValue() {
		return bidInfo.value;
	}
	
	/**
	 * Cambia 
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.bidInfo.value = value;
	}
	
	/**
	 * Verifica si la susbasta es automatica
	 * @return isAutomaticIncremet ia
	 */
	public boolean isAutomaticIncremet() {
		return isAutomaticIncremet;
	}
	
	/**
	 * Obtiene si la subasta es publica
	 * @return isPublic ip
	 */
	public boolean isPublic() {
		return isPublic;
	}
	
	/**
	 * Obtiene le propietario de la subasta
	 * @return owner propietario
	 */
	public String getOwner() {
		return owner;
	}
	
	public long getId() {
		return bidInfo.id;
	}
	/**
	 * Cambia 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.bidInfo.id = id;
	}

	@Override
	public String toString() {
		return "Nombre de publicacion: " + biddingName + ", tipo: " + typeProduct.name() + 
				"\nProducto: " + product.toString() + "\nTiempo de publicacion: " + publicationTime.toString() 
				+ "\nTiempo de inico: " + initTime.toString() + "\nTiempo final: " + finishTime.toString()+
				"\nValor actual: "+bidInfo.value+"\nMejor postor: "+bidInfo.highestBidder;
	}
	
	public String stringBid() {
		return bidInfo.id+ "-" + bidInfo.value+ "-" + bidInfo.highestBidder + "-" + biddingName + "-" + typeProduct.ordinal() + "-" 
				+ product.stringProduct()
				+ "-" + publicationTime.stringTime() + "-" + initTime.stringTime() + "-"
				+ finishTime.stringTime() + "-" + (isAutomaticIncremet?1:0) + "-" 
				+ (isPublic?1:0) + "-" + owner ;
	}
	
	public Product getProduct(byte[] bytes) throws UnsupportedEncodingException {
		return new Product(
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 0, 20))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 20, 40))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 40, 81))));
	}

	/**
	 * bidInfo = 32 bytes
	 * biddingName 30 char = 30 bytes -62
	 * typeProduct Integer = 4 bytes -66
	 * product = 91 bytes   -157
	 * publicationTime = 14 bytes  -171
	 * initTime = 14 bytes         -185
	 * finishTime = 14 bytes       -199
	 * isAutomaticIncrement = 1 byte (0 true, 1 false) -200
	 * isPublic = 1 byte               -201
	 * owner 20 char = 20 bytes      -221
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] bytes = new byte[RECORD_SIZE];
		bytes = Utilities.completeBytes(bytes, this.bidInfo.getBytes(), 0);
		bytes = Utilities.completeBytes(bytes,
				Utilities.stringToBytes(Utilities.completeLength(biddingName, 30)), 32);
		bytes = Utilities.completeBytes(bytes, Utilities.intToBytes(typeProduct.ordinal()), 62);
		bytes = Utilities.completeBytes(bytes, product.getBytes(), 66);
		bytes = Utilities.completeBytes(bytes, publicationTime.getBytes(), 157);
		bytes = Utilities.completeBytes(bytes, initTime.getBytes(), 171);
		bytes = Utilities.completeBytes(bytes, finishTime.getBytes(), 185);
		bytes = Utilities.completeBytes(bytes, (byte) ((isAutomaticIncremet)?0:1), 199);
		bytes = Utilities.completeBytes(bytes, (byte) ((isPublic)?0:1), 200);
		bytes = Utilities.completeBytes(bytes,
				Utilities.stringToBytes(Utilities.completeLength(owner, 20)), 201);
		return bytes;
	}
	
	/**
	 * bidInfo = 32 bytes
	 * biddingName 30 char = 30 bytes -62
	 * typeProduct Integer = 4 bytes -66
	 * product = 91 bytes   -157
	 * publicationTime = 14 bytes  -171
	 * initTime = 14 bytes         -185
	 * finishTime = 14 bytes       -199
	 * isAutomaticIncrement = 1 byte (0 true, 1 false) -200
	 * isPublic = 1 byte               -201
	 * owner 20 char = 20 bytes      -221
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public Bidding getData(byte[] bytes) throws UnsupportedEncodingException {
		return new Bidding(
				Utilities.bytesToLong(Utilities.cutBytes(bytes, 0, 8)),
				Utilities.bytesToInt(Utilities.cutBytes(bytes, 8, 12)),
				Utilities.bytesToString(Utilities.cutBytes(bytes, 12, 32)),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 32, 62))),
				TypeProduct.values()[Utilities.bytesToInt(Utilities.cutBytes(bytes, 62, 66))],
				this.getProduct(Utilities.cutBytes(bytes, 66, 157)), 				
				new BidTime(
						new BidDate(
						Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 157, 167)))), 
						Utilities.bytesToFloat(Utilities.cutBytes(bytes, 167, 171))), 
				new BidTime(
						new BidDate(
						Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 171, 181)))), 
						Utilities.bytesToFloat(Utilities.cutBytes(bytes, 181, 185))), 
				new BidTime(
						new BidDate(
						Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 185, 195)))), 
						Utilities.bytesToFloat(Utilities.cutBytes(bytes, 195, 199))), 
				(Utilities.cutBytes(bytes, 199, 200)[0]==0), 
				(Utilities.cutBytes(bytes, 200, 201)[0]==0), 
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 201, 221))));
	}

}
