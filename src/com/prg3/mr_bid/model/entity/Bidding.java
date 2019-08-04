package com.prg3.mr_bid.model.entity;

import java.io.UnsupportedEncodingException;

import javax.rmi.CORBA.Util;

import com.prg3.mr_bid.structures.bst_file.IDataRecorder;
import com.prg3.mr_bid.utilities.Utilities;

/**
 * Clase Bidding - Modelo de la subasta con todos sus datos
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class Bidding implements IDataRecorder<Bidding>{
	public final static int RECORD_SIZE = 191;
	private long id;
	private String biddingName;
	private TypeProduct typeProduct;
	private Product product;
	private BidTime publicationTime;
	private BidTime initTime;
	private BidTime finishTime;
	private boolean isAutomaticIncremet;
	private boolean isPublic;
	private String owner;
	private int value;
	
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
	 */
	public Bidding(long id,String biddingName, TypeProduct typeProduct, Product product, BidTime publicationTime,
			BidTime initTime, BidTime finishTime, boolean isAutomaticIncremet, boolean isPublic, int value) {
		this.id = id;
		this.biddingName = biddingName;
		this.typeProduct = typeProduct;
		this.product = product;
		this.publicationTime = publicationTime;
		this.initTime = initTime;
		this.finishTime = finishTime;
		this.isAutomaticIncremet = isAutomaticIncremet;
		this.isPublic = isPublic;
		this.value = value;
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
	public Bidding(long id,String biddingName, TypeProduct typeProduct, Product product, BidTime publicationTime,
			BidTime initTime, BidTime finishTime, boolean isAutomaticIncremet, boolean isPublic, String emailUser) {
		this.id = id;
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
	public Bidding(long id,String biddingName, TypeProduct typeProduct, Product product, BidTime publicationTime,
			BidTime initTime, BidTime finishTime, boolean isAutomaticIncremet, boolean isPublic, String emailUser, int value) {
		this.id = id;
		this.biddingName = biddingName;
		this.typeProduct = typeProduct;
		this.product = product;
		this.publicationTime = publicationTime;
		this.initTime = initTime;
		this.finishTime = finishTime;
		this.isAutomaticIncremet = isAutomaticIncremet;
		this.isPublic = isPublic;
		this.owner = emailUser;
		this.value = value;
	}
	
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
		return value;
	}
	
	/**
	 * Cambia 
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
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
		return id;
	}
	/**
	 * Cambia 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Nombre de publicacion: " + biddingName + ", tipo: " + typeProduct.name() + 
				"\nProducto: " + product.toString() + "\nTiempo de publicacion: " + publicationTime.toString() 
				+ "\nTiempo de inico: " + initTime.toString() + "\nTiempo final: " + finishTime.toString();
	}
	
	public String stringBid() {
		return id + "-" + biddingName + "-" + typeProduct.ordinal() + "-" + product.stringProduct()
		+ "-" + publicationTime.stringTime() + "-" + initTime.stringTime() + "-"
		+ finishTime.stringTime() + "-" + (isAutomaticIncremet?1:0) + "-" 
		+ (isPublic?1:0) + "-" + owner + "-" + value;
	}
	
	public Product getProduct(byte[] bytes) throws UnsupportedEncodingException {
		return new Product(
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 0, 20))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 20, 40))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 40, 81))));
	}

	/**
	 * id long = 8 bytes
	 * biddingName 30 char = 30 bytes
	 * typeProduct Integer = 4 bytes
	 * product = 81 bytes   -123
	 * publicationTime = 14 bytes  -137
	 * initTime = 14 bytes         -151
	 * finishTime = 14 bytes       -165
	 * isAutomaticIncrement = 1 byte (0 true, 1 false) -166
	 * isPublic = 1 byte               -167
	 * owner 20 char = 20 bytes      -187
	 * value int = 4 bytes       -191
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] bytes = new byte[191];
		bytes = Utilities.completeBytes(bytes, Utilities.longToBytes(id), 0);
		bytes = Utilities.completeBytes(bytes,
				Utilities.stringToBytes(Utilities.completeLength(biddingName, 20)), 8);
		bytes = Utilities.completeBytes(bytes, Utilities.intToBytes(typeProduct.ordinal()), 38);
		bytes = Utilities.completeBytes(bytes, product.getBytes(), 42);
		bytes = Utilities.completeBytes(bytes, publicationTime.getBytes(), 123);
		bytes = Utilities.completeBytes(bytes, initTime.getBytes(), 137);
		bytes = Utilities.completeBytes(bytes, finishTime.getBytes(), 151);
		bytes = Utilities.completeBytes(bytes, (byte) ((isAutomaticIncremet)?0:1), 165);
		bytes = Utilities.completeBytes(bytes, (byte) ((isPublic)?0:1), 166);
		bytes = Utilities.completeBytes(bytes,
				Utilities.stringToBytes(Utilities.completeLength(owner, 20)), 167);
		bytes = Utilities.completeBytes(bytes, Utilities.intToBytes(value), 187);
		return bytes;
	}
	
	/**
	 * id long = 8 bytes
	 * biddingName 30 char = 30 bytes
	 * typeProduct Integer = 4 bytes
	 * product = 81 bytes   -123
	 * publicationTime = 14 bytes  -137
	 * initTime = 14 bytes         -151
	 * finishTime = 14 bytes       -165
	 * isAutomaticIncrement = 1 byte (0 true, 1 false) -166
	 * isPublic = 1 byte               -167
	 * owner 20 char = 20 bytes      -187
	 * value int = 4 bytes       -191
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public Bidding getData(byte[] bytes) throws UnsupportedEncodingException {
		return new Bidding(Utilities.bytesToLong(Utilities.cutBytes(bytes, 0, 8)),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 8, 38))),
				TypeProduct.values()[Utilities.bytesToInt(bytes, 38)],
				this.getProduct(Utilities.cutBytes(bytes, 42, 123)), 				
				new BidTime(
						new BidDate(
						Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 123, 133)))), 
						Utilities.bytesToFloat(Utilities.cutBytes(bytes, 133, 137))), 
				new BidTime(
						new BidDate(
						Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 137, 147)))), 
						Utilities.bytesToFloat(Utilities.cutBytes(bytes, 147, 151))), 
				new BidTime(
						new BidDate(
						Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 151, 161)))), 
						Utilities.bytesToFloat(Utilities.cutBytes(bytes, 161, 165))), 
				(Utilities.cutBytes(bytes, 165, 166)[0]==0), 
				(Utilities.cutBytes(bytes, 166, 167)[0]==0), 
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 167, 187))),
				Utilities.bytesToInt(bytes, 187));
	}

}
