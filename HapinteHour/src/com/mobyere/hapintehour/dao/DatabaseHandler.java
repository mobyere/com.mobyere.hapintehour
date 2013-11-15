package com.mobyere.hapintehour.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author moby
 * Classe gérant la création et les mises à niveaux de toutes les tables de l'application
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(FavorisDao.TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(FavorisDao.TABLE_DROP);
	}

}
