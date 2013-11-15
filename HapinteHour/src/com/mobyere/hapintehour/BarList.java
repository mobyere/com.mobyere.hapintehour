package com.mobyere.hapintehour;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

class BarList extends ArrayList<Bar> implements Parcelable
{
    private static final long serialVersionUID = 1L;

	public BarList()
    {
 
    }
 
    public BarList(Parcel in)
    {
        this.getFromParcel(in);
    }
 
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public BarList createFromParcel(Parcel in)
        {
            return new BarList(in);
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
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            Bar bar = this.get(i); //On vient lire chaque objet personne
            dest.writeInt(bar.getBarID());
    		dest.writeString(bar.getBarNom());
    		dest.writeString(bar.getBarRue());
    		dest.writeString(bar.getBarCodePostal());
    		dest.writeString(bar.getBarVille());
    		dest.writeString(bar.getBarTelephone());
    		dest.writeFloat(bar.getBarDistance());
    		dest.writeString(bar.getBarPrixBiereHH());
    		dest.writeString(bar.getBarPrixBiereHN());
    		dest.writeString(bar.getBarPrixVinHH());
    		dest.writeString(bar.getBarPrixVinHN());
    		dest.writeString(bar.getBarHeureDebutHH());
    		dest.writeString(bar.getBarHeureFinHH());
    		dest.writeString(bar.getBarUrlImage());
    		dest.writeDouble(bar.getBarLatitude());
    		dest.writeDouble(bar.getBarLongitude());
    		dest.writeInt(bar.isBarHH() ? 0 : 1);
    		dest.writeInt(bar.isBarHHAujourdhui() ? 0 : 1);
    		dest.writeInt(bar.isBarHHLundi() ? 0 : 1);
    		dest.writeInt(bar.isBarHHMardi() ? 0 : 1);
    		dest.writeInt(bar.isBarHHMercredi() ? 0 : 1);
    		dest.writeInt(bar.isBarHHJeudi() ? 0 : 1);
    		dest.writeInt(bar.isBarHHVendredi() ? 0 : 1);
    		dest.writeInt(bar.isBarHHSamedi() ? 0 : 1);
    		dest.writeInt(bar.isBarHHDimanche() ? 0 : 1);
    		dest.writeString(bar.getBarDebutHHLundi());
    		dest.writeString(bar.getBarDebutHHMardi());
    		dest.writeString(bar.getBarDebutHHMercredi());
    		dest.writeString(bar.getBarDebutHHJeudi());
    		dest.writeString(bar.getBarDebutHHVendredi());
    		dest.writeString(bar.getBarDebutHHSamedi());
    		dest.writeString(bar.getBarDebutHHDimanche());
    		dest.writeString(bar.getBarFinHHLundi());
    		dest.writeString(bar.getBarFinHHMardi());
    		dest.writeString(bar.getBarFinHHMercredi());
    		dest.writeString(bar.getBarFinHHJeudi());
    		dest.writeString(bar.getBarFinHHVendredi());
    		dest.writeString(bar.getBarFinHHSamedi());
    		dest.writeString(bar.getBarFinHHDimanche());
    		dest.writeString(bar.getBarDetails());
    		dest.writeInt(bar.isBarFavori() ? 0 : 1);
        }
    }
 
    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();
 
        //Récupération du nombre d'objet
        int size = in.readInt();
 
        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            Bar bar = new Bar();
            bar.setBarID(in.readInt());
            bar.setBarNom(in.readString());
            bar.setBarRue(in.readString());
            bar.setBarCodePostal(in.readString());
            bar.setBarVille(in.readString());
            bar.setBarTelephone(in.readString());
            bar.setBarDistance(in.readFloat());
            bar.setBarPrixBiereHH(in.readString());
            bar.setBarPrixBiereHN(in.readString());
            bar.setBarPrixVinHH(in.readString());
            bar.setBarPrixVinHN(in.readString());
            bar.setBarHeureDebutHH(in.readString());
            bar.setBarHeureFinHH(in.readString());
            bar.setBarUrlImage(in.readString());
            bar.setBarLatitude(in.readDouble());
            bar.setBarLongitude(in.readDouble());
            bar.setBarHH(in.readInt() == 0);
            bar.setBarHHAujourdhui(in.readInt() == 0);
            bar.setBarHHLundi(in.readInt() == 0);
            bar.setBarHHMardi(in.readInt() == 0);
            bar.setBarHHMercredi(in.readInt() == 0);
            bar.setBarHHJeudi(in.readInt() == 0);
            bar.setBarHHVendredi(in.readInt() == 0);
            bar.setBarHHSamedi(in.readInt() == 0);
            bar.setBarHHDimanche(in.readInt() == 0);
            bar.setBarDebutHHLundi(in.readString());
            bar.setBarDebutHHMardi(in.readString());
            bar.setBarDebutHHMercredi(in.readString());
            bar.setBarDebutHHJeudi(in.readString());
            bar.setBarDebutHHVendredi(in.readString());
            bar.setBarDebutHHSamedi(in.readString());
            bar.setBarDebutHHDimanche(in.readString());
            bar.setBarFinHHLundi(in.readString());
            bar.setBarFinHHMardi(in.readString());
            bar.setBarFinHHMercredi(in.readString());
            bar.setBarFinHHJeudi(in.readString());
            bar.setBarFinHHVendredi(in.readString());
            bar.setBarFinHHSamedi(in.readString());
            bar.setBarFinHHDimanche(in.readString());
            bar.setBarDetails(in.readString());
            bar.setBarFavori(in.readInt() == 0);
            
            this.add(bar);
        }
 
    }
}
