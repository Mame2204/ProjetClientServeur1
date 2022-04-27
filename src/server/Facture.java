package server;

public class Facture {
    private String reference;
    private String date;
    private double modePayement;
    private int montant;
    public Facture(String date, double modePayement, int montant) {
        super();
        this.date = date;
        this.modePayement = modePayement;
        this.montant = montant;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getModePayement() {
        return modePayement;
    }
    public void setModePayement(double modePayement) {
        this.modePayement = modePayement;
    }
    public int getMontant() {
        return montant;
    }
    public void setMontant(int montant) {
        this.montant = montant;
    }

}
