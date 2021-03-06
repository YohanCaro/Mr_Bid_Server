package com.prg3.mr_bid.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.structures.bst_file.BSTFile;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;
import com.prg3.mr_bid.utilities.Constants;

/**
 * Clase que maneja la persistencia de los objetos Bidding, usando un arbol BST para esto
 * @author Luis!
 * @since 2/08/2019 v1.0
 */
public class BiddingPersistence {
	private BSTFile<Bidding> bstBiddings;
	private Comparator<Bidding> bComparator;
	
	/**
	 * Constructor de la clase BiddingPersistence
	 * @param pathBiddsFile ruta del archivo que contiene la informaci�n de las subastas
	 * @param indexBSTFile ruta del archivo que contiene los indices de memoria ordenados 
	 * para obtener un tratamiento de informaci�n mas optimo
	 * @throws FileNotFoundException si alguno de los archivos no existe
	 */
	public BiddingPersistence(String pathBiddsFile, String indexBSTFile) throws FileNotFoundException {
		bComparator = new Comparator<Bidding>() {
			@Override
			public int compare(Bidding o1, Bidding o2) {
				return (int) (o1.getId()-o2.getId());
			}
		};
		bstBiddings = new BSTFile<>(Bidding.class, Bidding.RECORD_SIZE, 
				indexBSTFile, pathBiddsFile, bComparator);
	}
	
	/**
	 * A�ade un nuevo objeto Bidding al arbol bst en persistencia
	 * @param bidding objeto Bidding a a�adir
	 * @throws IOException problema en la escritura del archivo
	 */
	public void addNewBidding(Bidding bidding) throws IOException {
		bstBiddings.add(bidding);
	}
	
	/**
	 * Elimina un objeto Bidding del arbol bst en persistencia
	 * @param bidding objeto Bidding a eliminar
	 */
	public void deleteBidding(Bidding bidding) {
		bstBiddings.delete(bidding);
	}
	
	/**
	 * Limpia los archivos que contienen tanto la informaci�n de las subastas, 
	 * como los indices del arbol bst de registros de las mismas.
	 * @throws IOException problema con archivos
	 */
	private void cleanFiles() throws IOException {
		this.bstBiddings.setRootIndex(BSTFile.NULL);
		BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.biddingsFilePath));
		bw.write("");
		bw.close();
		bw = new BufferedWriter(new FileWriter(Constants.indexBiddingsPath));
		bw.write("");
		bw.close();
	}
	
	/**
	 * Actualiza todos los objetos Bidding almacenados actualmente en persistencia
	 * @param biddings lista simple con los objetos Bidding atualizados
	 * @throws IOException error en la escritura de archivos
	 */
	public void updateBiddings(SimpleList<Bidding> biddings) throws IOException {
		this.cleanFiles();		
		Cursor<Bidding> bidCursor = new Cursor<>(biddings);
		while(!bidCursor.isOut()) {
			bstBiddings.add(bidCursor.getInfo());
			bidCursor.next();
		}
	}
	
	/**
	 * Actualiza un objeto Bidding almacenado actualmente en persistencia
	 * @param biddings objeto Bidding a actualizar
	 * @throws IOException error en la escritura de archivos
	 */
	public void updateBidding(Bidding bidding) throws IOException {
		bstBiddings.insert(bidding.getId()-1, bidding);
	}
	
	/**
	 * Obtiene todos los objetos Bidding almacenados en persistencia
	 * @return lista simple con objetos Bidding
	 */
	public SimpleList<Bidding> getAllBiddings() {
		SimpleList<Bidding> bidList = new SimpleList<>();
		long tempIndex = 0;
		Bidding bidding = null;
		do {
			bidding = bstBiddings.getData(tempIndex);
			if(bidding!=null) {
				bidList.add(bidding);
			}
			tempIndex++;
		}while(bidding!=null);
		return bidList;
	}
	
	/**
	 * Obtiene un objeto Bidding extraido del arbol bst por su identificador
	 * @param id identificador del objeto Bidding requerido
	 * @return objeto Bidding
	 */
	public Bidding getBiddingById(long id) {
		return bstBiddings.getData(id-1);
	}
	
}
