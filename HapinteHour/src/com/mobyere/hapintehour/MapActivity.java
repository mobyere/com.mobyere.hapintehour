package com.mobyere.hapintehour;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobyere.hapintehour.R;

public class MapActivity extends FragmentActivity implements LocationListener {
	
	private GoogleMap gMap;
	private LocationManager locationManager;
	private BarList listeBarsCarte;
	private String activityOrigine;
	private Intent intent;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// Show the Up button in the action bar.
		setupActionBar();
				
		gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		gMap.setMyLocationEnabled(true);
		
		Bundle b = getIntent().getExtras();
		listeBarsCarte = b.getParcelable("listeBars");
		activityOrigine = b.getString("origine");
		
	    Marker marker;
	    for (Bar bar : listeBarsCarte) {
	    	// Récupération coordonnées GPS
	    	marker = gMap.addMarker(new MarkerOptions().title(bar.getBarNom())
	    			.position(new LatLng(bar.getBarLatitude(), bar.getBarLongitude()))
	    			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_marker)));
	    	// Si on est sur le premier bar, on centre la carte dessus et on affiche
	    	// son titre
	    	if (listeBarsCarte.indexOf(bar) == 0) {
	    		gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
	    				new LatLng(bar.getBarLatitude(), bar.getBarLongitude()), 15)
	    				, 2000, null);
	    		marker.showInfoWindow();
	    	}
	    }
	    gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				// On ne peut cliquer que si on ne vient pas déjà du détail
				if(!activityOrigine.equals(Utils.getActivityDetailsBar())) {
					intent = new Intent(MapActivity.this, DetailsBarActivity.class);
					intent.putExtra("BarSelectionne", Integer.parseInt(marker.getId().substring(1)));
					startActivity(intent);
				} else {
					Bar bar = listeBarsCarte.get(Integer.parseInt(marker.getId().substring(1)));
			    	allerVersGMaps(bar);
				}
			}
		});
	}

	@Override
    public void onResume() {
        super.onResume();
 
        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
 
        //Si le GPS est disponible, on s'y abonne
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
 
        //On appelle la méthode pour se désabonner
        desabonnementGPS();
    }
 
    /**
     * Méthode permettant de s'abonner à la localisation par GPS.
     */
    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }
 
    /**
     * Méthode permettant de se désabonner de la localisation par GPS.
     */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }
    
	@Override
	public void onLocationChanged(Location location) {
		//gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
		//Si le GPS est activé on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			//finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void allerVersGMaps(final Bar bar) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder
            .setMessage("Voulez-vous naviguer vers ce bar ? ")
            .setCancelable(false)
            .setPositiveButton("Oui ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    	intent = new Intent(android.content.Intent.ACTION_VIEW, 
    			    			Uri.parse("geo:0,0?q=" + bar.getBarLatitude() + "," + 
    			    					bar.getBarLongitude() + " (" + bar.getBarNom() + ")"));
    			    	startActivity(intent);
    			    	finish();
                    }
                }
            );
        localBuilder.setNegativeButton("Non ",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.cancel();
                }
            }
        );
        localBuilder.create().show();
    }

}
