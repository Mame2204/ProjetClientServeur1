package server;

import java.io.Serializable;

public class Article implements Serializable{
	
	private String reference;
	private String famille;
	private double prix;
	private int nbStock;
	
	public Article(String reference, String famille, double prix, int nbStock) {
		super();
		this.reference = reference;
		this.famille = famille;
		this.prix = prix;
		this.nbStock = nbStock;
	}

	public Article() {
		super();
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the famille
	 */
	public String getFamille() {
		return famille;
	}

	/**
	 * @param famille the famille to set
	 */
	public void setFamille(String famille) {
		this.famille = famille;
	}

	/**
	 * @return the prix
	 */
	public double getPrix() {
		return prix;
	}

	/**
	 * @param prix the prix to set
	 */
	public void setPrix(double prix) {
		this.prix = prix;
	}

	/**
	 * @return the nbStock
	 */
	public int getNbStock() {
		return nbStock;
	}

	/**
	 * @param nbStock the nbStock to set
	 */
	public void setNbStock(int nbStock) {
		this.nbStock = nbStock;
	}
	
	
	

}
