package com.mobyere.hapintehour.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class FavorisDao extends DAOBase {

	public FavorisDao(Context pContext) {
		super(pContext);
	}
	
	public static final String TABLE_NAME = "favoris";
	public static final String KEY = "id";
	public static final int NUM_KEY = 0;
	public static final String BAR_ID = "barId";
	public static final int NUM_BAR_ID = 0;
	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BAR_ID + " INTEGER);";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

	  /**
	   * @param m le bar favori à ajouter à la base
	   */
	  public void ajouterBarId(long barId) {
	    ContentValues value = new ContentValues();
	    value.put(BAR_ID, barId);
	    long rowId = mDb.insert(TABLE_NAME, null, value);
	    System.out.println("Id de la ligne ajoutée : " + String.valueOf(rowId));
	  }
	  
	  /**
	   * @param barId l'identifiant du bar à supprimer des favoris
	   */
	  public void supprimerBarId(long barId) {
	    int nbSupp = mDb.delete(TABLE_NAME, BAR_ID + " = ?", new String[] {String.valueOf(barId)});
	    System.out.println("Nombre ligne supprimée pour le barId " + String.valueOf(barId) + " : " + nbSupp);
	  }
	  
	  /**
	   * @param id l'identifiant du bar à récupérer
	   */
	  public FavorisMetier getFavoriByBarId(long barId) {
		  Cursor c = mDb.rawQuery("select " + BAR_ID + " from " + TABLE_NAME + " where barId = ?", new String[]{String.valueOf(barId)});
		  return ConvertCursorToObject(c);
	  }
	  
	  /**
	   * Conversion du curseur en objet FavorisMetier
	   * @param c
	   * @return
	   */
	  private FavorisMetier ConvertCursorToObject(Cursor c){
          if (c.getCount() == 0)
                return null;
          
          c.moveToFirst();
          FavorisMetier favoris = new FavorisMetier();
   		  favoris.setId(c.getLong(0));
   		  favoris.setBarId(c.getLong(NUM_BAR_ID));
          
          c.close();
          return favoris;
    }
}
