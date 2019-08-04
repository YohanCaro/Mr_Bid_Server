package com.prg3.mr_bid.model.entity;

import java.io.UnsupportedEncodingException;

import com.prg3.mr_bid.structures.bst_file.IDataRecorder;
import com.prg3.mr_bid.utilities.Utilities;

/**
 * Clase User - Clase que crea un usuario de la aplicación
 *
 * @author Yohan Caro
 * @version 1.0 - 2/06/2019
 */
public class User implements IDataRecorder<User> {
	
	public final static int RECORD_SIZE = 118;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private BidDate birthDate;
	private String document;
	private TypeDocument typeDocument;
	private Gender gender;
	private CreditCard creditCard;
	
	/**
	 * Crea un usuario con los siguientes datos
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param email correo
	 * @param password contraseña
	 * @param birthDate fecha de nacimiento
	 * @param document documento
	 * @param typeDocument tipo de documento
	 * @param isFemale genero
	 * @param creditCard tarjeta de credito
	 * @param myBiddings mis subastas
	 * @param myParcitipations mis participaciones
	 */
	public User(String firstName, String lastName, String email, String password, BidDate birthDate, String document,
			TypeDocument typeDocument, Gender gender, CreditCard creditCard) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.document = document;
		this.typeDocument = typeDocument;
		this.gender = gender;
		this.creditCard = creditCard;
		
	}
	
	/**
	 * Cambia la tarjeta de credito del usuario
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	/**
	 * Cambia la contraseña
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el nombre
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Obtiene el apellido
	 * @return lastName a
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Obtiene correo
	 * @return email c
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Obtiene la contraseña 
	 * @return password c
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Obtiene la fecha de nacimiento
	 * @return birthDate b
	 */
	public BidDate getBirthDate() {
		return birthDate;
	}

	/**
	 * Obtiene el genero
	 * @return gender genero
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Obtiene la tarjeta de credito
	 * @return creditCard cc
	 */
	public CreditCard getCreditCard() {
		return creditCard;
	}
		
	@Override
	public String toString() {
		return "Nombre y apellido: " + firstName + " email " + email +
				"\nFecha de nacimiento: " + birthDate.getDateString() + " genero " + gender.getValue();
	}

	@Override
	public byte[] getBytes() {
		byte[] bytes = new byte[RECORD_SIZE];
		bytes = Utilities.completeBytes(bytes, 
				Utilities.stringToBytes(Utilities.completeLength(firstName, 20)), 0);
		bytes = Utilities.completeBytes(bytes, 
				Utilities.stringToBytes(Utilities.completeLength(lastName,20)), 20);
		bytes = Utilities.completeBytes(bytes, 
				Utilities.stringToBytes(Utilities.completeLength(email,20)), 40);
		bytes = Utilities.completeBytes(bytes, 
				Utilities.stringToBytes(Utilities.completeLength(password,20)), 60);
		bytes = Utilities.completeBytes(bytes, 
				Utilities.stringToBytes(Utilities.completeLength(birthDate.getDateString(),10)), 80);
		bytes = Utilities.completeBytes(bytes, 
				Utilities.stringToBytes(Utilities.completeLength(document,20)), 90);
		bytes = Utilities.completeBytes(bytes, Utilities.intToBytes(typeDocument.ordinal()), 110);
		bytes = Utilities.completeBytes(bytes, Utilities.intToBytes(gender.ordinal()), 114);
		return bytes;
	}

	@Override
	public User getData(byte[] bytes) throws UnsupportedEncodingException {
		return new User(
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 0, 20))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 20, 40))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 40, 60))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 60, 80))),
				new BidDate(Utilities.bytesToString(Utilities.cutBytes(bytes, 80, 90))),
				Utilities.cutStringWhitAditionalSpace(Utilities.bytesToString(Utilities.cutBytes(bytes, 90, 110))),
				TypeDocument.values()[Utilities.bytesToInt(bytes, 110)],
				Gender.values()[Utilities.bytesToInt(bytes, 114)], null);
	}

}
