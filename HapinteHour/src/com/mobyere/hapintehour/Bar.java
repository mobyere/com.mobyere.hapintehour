package com.mobyere.hapintehour;

import android.os.Parcel;
import android.os.Parcelable;

public class Bar implements Parcelable {

	private int barID;
	private String barNom;
	private String barHeureDebutHH;
	private String barHeureFinHH;
	private String barPrixBiereHH;
	private String barPrixBiereHN;
	private String barPrixVinHH;
	private String barPrixVinHN;
	private float barDistance;
	private String barUrlImage;
	private String barRue;
	private String barCodePostal;
	private String barVille;
	private String barTelephone;
	private double barLatitude;
	private double barLongitude;
	private boolean isBarHH;
	private boolean isBarHHLundi;
	private boolean isBarHHMardi;
	private boolean isBarHHMercredi;
	private boolean isBarHHJeudi;
	private boolean isBarHHVendredi;
	private boolean isBarHHSamedi;
	private boolean isBarHHDimanche;
	private String barDebutHHLundi;
	private String barDebutHHMardi;
	private String barDebutHHMercredi;
	private String barDebutHHJeudi;
	private String barDebutHHVendredi;
	private String barDebutHHSamedi;
	private String barDebutHHDimanche;
	private String barFinHHLundi;
	private String barFinHHMardi;
	private String barFinHHMercredi;
	private String barFinHHJeudi;
	private String barFinHHVendredi;
	private String barFinHHSamedi;
	private String barFinHHDimanche;
	private String barDetails;
		
	public Bar() {

	}
	
	// Constructeur pour "déParcelable"
	public Bar(Parcel in) {
		this.getFromParcel(in);
	}
	
	@SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Bar createFromParcel(Parcel in)
        {
            return new Bar(in);
        }
 
        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
    
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(barID);
		dest.writeString(barNom);
		dest.writeString(barRue);
		dest.writeString(barCodePostal);
		dest.writeString(barVille);
		dest.writeString(barTelephone);
		dest.writeFloat(barDistance);
		dest.writeString(barPrixBiereHH);
		dest.writeString(barPrixBiereHN);
		dest.writeString(barPrixVinHH);
		dest.writeString(barPrixVinHN);
		dest.writeString(barHeureDebutHH);
		dest.writeString(barHeureFinHH);
		dest.writeString(barUrlImage);
		dest.writeDouble(barLatitude);
		dest.writeDouble(barLongitude);
		dest.writeInt(isBarHH ? 0 : 1);
		dest.writeInt(isBarHHLundi ? 0 : 1);
		dest.writeInt(isBarHHMardi ? 0 : 1);
		dest.writeInt(isBarHHMercredi ? 0 : 1);
		dest.writeInt(isBarHHJeudi ? 0 : 1);
		dest.writeInt(isBarHHVendredi ? 0 : 1);
		dest.writeInt(isBarHHSamedi ? 0 : 1);
		dest.writeInt(isBarHHDimanche ? 0 : 1);
		dest.writeString(barDebutHHLundi);
		dest.writeString(barDebutHHMardi);
		dest.writeString(barDebutHHMercredi);
		dest.writeString(barDebutHHJeudi);
		dest.writeString(barDebutHHVendredi);
		dest.writeString(barDebutHHSamedi);
		dest.writeString(barDebutHHDimanche);
		dest.writeString(barFinHHLundi);
		dest.writeString(barFinHHMardi);
		dest.writeString(barFinHHMercredi);
		dest.writeString(barFinHHJeudi);
		dest.writeString(barFinHHVendredi);
		dest.writeString(barFinHHSamedi);
		dest.writeString(barFinHHDimanche);
		dest.writeString(barDetails);
	}
	
	public void getFromParcel(Parcel in) {
		this.setBarID(in.readInt());
		this.setBarNom(in.readString());
		this.setBarRue(in.readString());
		this.setBarCodePostal(in.readString());
		this.setBarVille(in.readString());
		this.setBarTelephone(in.readString());
		this.setBarDistance(in.readFloat());
		this.setBarPrixBiereHH(in.readString());
		this.setBarPrixBiereHN(in.readString());
		this.setBarPrixVinHH(in.readString());
		this.setBarPrixVinHN(in.readString());
		this.setBarHeureDebutHH(in.readString());
		this.setBarHeureFinHH(in.readString());
		this.setBarUrlImage(in.readString());
		this.setBarLatitude(in.readDouble());
		this.setBarLongitude(in.readDouble());
		this.setBarHH(in.readInt() == 0);
		this.setBarHHLundi(in.readInt() == 0);
		this.setBarHHMardi(in.readInt() == 0);
		this.setBarHHMercredi(in.readInt() == 0);
		this.setBarHHJeudi(in.readInt() == 0);
		this.setBarHHVendredi(in.readInt() == 0);
		this.setBarHHSamedi(in.readInt() == 0);
		this.setBarHHDimanche(in.readInt() == 0);
		this.setBarDebutHHLundi(in.readString());
		this.setBarDebutHHMardi(in.readString());
		this.setBarDebutHHMercredi(in.readString());
		this.setBarDebutHHJeudi(in.readString());
		this.setBarDebutHHVendredi(in.readString());
		this.setBarDebutHHSamedi(in.readString());
		this.setBarDebutHHDimanche(in.readString());
		this.setBarFinHHLundi(in.readString());
		this.setBarFinHHMardi(in.readString());
		this.setBarFinHHMercredi(in.readString());
		this.setBarFinHHJeudi(in.readString());
		this.setBarFinHHVendredi(in.readString());
		this.setBarFinHHSamedi(in.readString());
		this.setBarFinHHDimanche(in.readString());
		this.setBarDetails(in.readString());
	}
	
	public int getBarID() {
		return barID;
	}
	public void setBarID(int barID) {
		this.barID = barID;
	}
	public String getBarNom() {
		return barNom;
	}
	public void setBarNom(String barNom) {
		this.barNom = barNom;
	}
	public String getBarHeureDebutHH() {
		return barHeureDebutHH;
	}
	public void setBarHeureDebutHH(String barHeureDebutHH) {
		this.barHeureDebutHH = barHeureDebutHH;
	}
	public String getBarHeureFinHH() {
		return barHeureFinHH;
	}
	public void setBarHeureFinHH(String barHeureFinHH) {
		this.barHeureFinHH = barHeureFinHH;
	}
	public String getBarPrixBiereHH() {
		return barPrixBiereHH;
	}
	public void setBarPrixBiereHH(String barPrixBiereHH) {
		this.barPrixBiereHH = barPrixBiereHH;
	}
	public String getBarPrixBiereHN() {
		return barPrixBiereHN;
	}
	public void setBarPrixBiereHN(String barPrixBiereHN) {
		this.barPrixBiereHN = barPrixBiereHN;
	}
	public String getBarPrixVinHH() {
		return barPrixVinHH;
	}
	public void setBarPrixVinHH(String barPrixVinHH) {
		this.barPrixVinHH = barPrixVinHH;
	}
	public String getBarPrixVinHN() {
		return barPrixVinHN;
	}
	public void setBarPrixVinHN(String barPrixVinHN) {
		this.barPrixVinHN = barPrixVinHN;
	}
	public float getBarDistance() {
		return barDistance;
	}
	public void setBarDistance(float barDistance) {
		this.barDistance = barDistance;
	}
	public String getBarUrlImage() {
		return barUrlImage;
	}
	public void setBarUrlImage(String barUrlImage) {
		this.barUrlImage = barUrlImage;
	}
	
	public String getBarRue() {
		return barRue;
	}

	public void setBarRue(String barRue) {
		this.barRue = barRue;
	}

	public String getBarCodePostal() {
		return barCodePostal;
	}

	public void setBarCodePostal(String barCodePostal) {
		this.barCodePostal = barCodePostal;
	}

	public String getBarVille() {
		return barVille;
	}

	public void setBarVille(String barVille) {
		this.barVille = barVille;
	}

	public String getBarTelephone() {
		return barTelephone;
	}

	public void setBarTelephone(String barTelephone) {
		this.barTelephone = barTelephone;
	}

	public double getBarLatitude() {
		return barLatitude;
	}

	public void setBarLatitude(double barLatitude) {
		this.barLatitude = barLatitude;
	}

	public double getBarLongitude() {
		return barLongitude;
	}

	public void setBarLongitude(double barLongitude) {
		this.barLongitude = barLongitude;
	}

	public boolean isBarHH() {
		return isBarHH;
	}

	public void setBarHH(boolean isBarHH) {
		this.isBarHH = isBarHH;
	}

	public boolean isBarHHLundi() {
		return isBarHHLundi;
	}

	public void setBarHHLundi(boolean isBarHHLundi) {
		this.isBarHHLundi = isBarHHLundi;
	}

	public boolean isBarHHMardi() {
		return isBarHHMardi;
	}

	public void setBarHHMardi(boolean isBarHHMardi) {
		this.isBarHHMardi = isBarHHMardi;
	}

	public boolean isBarHHMercredi() {
		return isBarHHMercredi;
	}

	public void setBarHHMercredi(boolean isBarHHMercredi) {
		this.isBarHHMercredi = isBarHHMercredi;
	}

	public boolean isBarHHJeudi() {
		return isBarHHJeudi;
	}

	public void setBarHHJeudi(boolean isBarHHJeudi) {
		this.isBarHHJeudi = isBarHHJeudi;
	}

	public boolean isBarHHVendredi() {
		return isBarHHVendredi;
	}

	public void setBarHHVendredi(boolean isBarHHVendredi) {
		this.isBarHHVendredi = isBarHHVendredi;
	}

	public boolean isBarHHSamedi() {
		return isBarHHSamedi;
	}

	public void setBarHHSamedi(boolean isBarHHSamedi) {
		this.isBarHHSamedi = isBarHHSamedi;
	}

	public boolean isBarHHDimanche() {
		return isBarHHDimanche;
	}

	public void setBarHHDimanche(boolean isBarHHDimanche) {
		this.isBarHHDimanche = isBarHHDimanche;
	}

	public String getBarDebutHHLundi() {
		return barDebutHHLundi;
	}

	public void setBarDebutHHLundi(String barDebutHHLundi) {
		this.barDebutHHLundi = barDebutHHLundi;
	}

	public String getBarDebutHHMardi() {
		return barDebutHHMardi;
	}

	public void setBarDebutHHMardi(String barDebutHHMardi) {
		this.barDebutHHMardi = barDebutHHMardi;
	}

	public String getBarDebutHHMercredi() {
		return barDebutHHMercredi;
	}

	public void setBarDebutHHMercredi(String barDebutHHMercredi) {
		this.barDebutHHMercredi = barDebutHHMercredi;
	}

	public String getBarDebutHHJeudi() {
		return barDebutHHJeudi;
	}

	public void setBarDebutHHJeudi(String barDebutHHJeudi) {
		this.barDebutHHJeudi = barDebutHHJeudi;
	}

	public String getBarDebutHHVendredi() {
		return barDebutHHVendredi;
	}

	public void setBarDebutHHVendredi(String barDebutHHVendredi) {
		this.barDebutHHVendredi = barDebutHHVendredi;
	}

	public String getBarDebutHHSamedi() {
		return barDebutHHSamedi;
	}

	public void setBarDebutHHSamedi(String barDebutHHSamedi) {
		this.barDebutHHSamedi = barDebutHHSamedi;
	}

	public String getBarDebutHHDimanche() {
		return barDebutHHDimanche;
	}

	public void setBarDebutHHDimanche(String barDebutHHDimanche) {
		this.barDebutHHDimanche = barDebutHHDimanche;
	}

	public String getBarFinHHLundi() {
		return barFinHHLundi;
	}

	public void setBarFinHHLundi(String barFinHHLundi) {
		this.barFinHHLundi = barFinHHLundi;
	}

	public String getBarFinHHMardi() {
		return barFinHHMardi;
	}

	public void setBarFinHHMardi(String barFinHHMardi) {
		this.barFinHHMardi = barFinHHMardi;
	}

	public String getBarFinHHMercredi() {
		return barFinHHMercredi;
	}

	public void setBarFinHHMercredi(String barFinHHMercredi) {
		this.barFinHHMercredi = barFinHHMercredi;
	}

	public String getBarFinHHJeudi() {
		return barFinHHJeudi;
	}

	public void setBarFinHHJeudi(String barFinHHJeudi) {
		this.barFinHHJeudi = barFinHHJeudi;
	}

	public String getBarFinHHVendredi() {
		return barFinHHVendredi;
	}

	public void setBarFinHHVendredi(String barFinHHVendredi) {
		this.barFinHHVendredi = barFinHHVendredi;
	}

	public String getBarFinHHSamedi() {
		return barFinHHSamedi;
	}

	public void setBarFinHHSamedi(String barFinHHSamedi) {
		this.barFinHHSamedi = barFinHHSamedi;
	}

	public String getBarFinHHDimanche() {
		return barFinHHDimanche;
	}

	public void setBarFinHHDimanche(String barFinHHDimanche) {
		this.barFinHHDimanche = barFinHHDimanche;
	}

	public String getBarDetails() {
		return barDetails;
	}

	public void setBarDetails(String barDetails) {
		this.barDetails = barDetails;
	}
	
}


