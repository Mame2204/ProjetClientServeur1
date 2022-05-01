package serverSiege;

public class Facture {
    private String reference;
    private String date;
    private String modePayement;
    private double montant;
    public Facture(String date, String modePayement, double montant) {
        super();
        this.date = date;
        this.modePayement = modePayement;
        this.montant = montant;
    }
    public Facture() {
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
    public String getModePayement() {
        return modePayement;
    }
    public void setModePayement(String modePayement) {
        this.modePayement = modePayement;
    }
    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }

}
