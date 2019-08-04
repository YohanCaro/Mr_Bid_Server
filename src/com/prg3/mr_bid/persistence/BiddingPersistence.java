package com.prg3.mr_bid.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import com.prg3.mr_bid.model.entity.Bidding;
import com.prg3.mr_bid.structures.bst_file.BSTFile;
import com.prg3.mr_bid.structures.simple_list.Cursor;
import com.prg3.mr_bid.structures.simple_list.SimpleList;

/**
 * Clase que maneja la persistencia de los objetos Bidding, usando un arbol BST para esto
 * @author Luis!
 *
 */
public class BiddingPersistence {
	private BSTFile<Bidding> bstBiddings;
	private Comparator<Bidding> bComparator;
	
	/**
	 * Constructor de la clase BiddingPersistence
	 * @param pathBiddsFile ruta del archivo que contiene la información de las subastas
	 * @param indexBSTFile ruta del archivo que contiene los indices de memoria ordenados 
	 * para obtener un tratamiento de información mas optimo
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
	 * Añade un nuevo objeto Bidding al arbol bst en persistencia
	 * @param bidding objeto Bidding a añadir
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
	 * Actualiza todos los objetos Bidding almacenados actualmente en persistencia
	 * @param biddings lista simple con los objetos Bidding atualizados
	 * @throws IOException error en la escritura de archivos
	 */
	public void updateBiddings(SimpleList<Bidding> biddings) throws IOException {
		long currentIndex = 0;
		Cursor<Bidding> bidCursor = new Cursor<>(biddings);
		while(!bidCursor.isOut()) {
			bstBiddings.insert(currentIndex, bidCursor.getInfo());
			currentIndex++;
			bidCursor.next();
		}
	}
	
	/**
	 * Actualiza un objeto Bidding almacenado actualmente en persistencia
	 * @param biddings objeto Bidding a actualizar
	 * @throws IOException error en la escritura de archivos
	 */
	public void updateBidding(Bidding bidding) throws IOException {
		bstBiddings.insert(bidding.getId(), bidding);
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
		return bstBiddings.getData(id);
	}
	
}
