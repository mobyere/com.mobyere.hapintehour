package com.mobyere.hapintehour;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends Activity implements LocationListener {

	private LocationManager locationManager;
	private Location location;
    private String provider;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		// Initialisation de la liste des bars
		Utils.setListeBarsHH(new BarList());
		// Récupération location
		locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		provider = LocationManager.NETWORK_PROVIDER;
		location = locationManager.getLastKnownLocation(provider);
		if (null == location) 
			this.onLocationChanged(location);
		// Chargement de la liste en fond
		new GetServerData().execute();
	}
	
	
	private class GetServerData extends AsyncTask<Void, Void, String> {
	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Void... urls) {
			// Connection au serveur
			String strReponse = Utils.makeWebCall();
			if (null == strReponse) {
				return "ExceptionServer";
			}			
			// Alimentation de la liste à partir du serveur
			Utils.alimentationListeBars(strReponse, SplashActivity.this, location);
	        return null;
		}
		
		protected void onPostExecute(String result) {
			// Problème lors de la connexion au serveur
			if ("ExceptionServer".equalsIgnoreCase(result)){
				Toast.makeText(SplashActivity.this, "Erreur lors de la connexion au serveur.\nVérifiez votre connexion réseau", 
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				// On fait passer la liste des bars à l'écran suivant
				Intent intent = new Intent(SplashActivity.this, ListeBarsActivity.class);
				//intent.putExtra("listeBars", (Parcelable) Utils.getListeBarsHH());
		    	startActivity(intent);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Si la localisation par réseau est dispo on choisit celle là (plus rapide)
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
						10000, 100, this);
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
		// On vérifie que le GPS est dispo (au cas où le network ne l'est pas
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
						10000, 100, this);
			provider = LocationManager.GPS_PROVIDER;
		}
		// Aucun des deux n'est dispo, on propose de les activer
		if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && 
				!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			createGpsDisabledAlert();
		}
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	private void createGpsDisabledAlert() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder
            .setMessage("Aucun service de localisation disponible, vous devez en activer un pour utiliser l'application")
            .setCancelable(false)
            .setPositiveButton("Activer ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    	startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                        finish();
                    }
                }
            );
        localBuilder.setNegativeButton("Quitter ",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.cancel();
                    finish();
                }
            }
        );
        localBuilder.create().show();
    }
}
