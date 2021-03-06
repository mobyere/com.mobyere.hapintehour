package com.mobyere.hapintehour;

import java.util.Collections;
import java.util.Comparator;

import com.mobyere.hapintehour.dao.FavorisDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListeBarsActivity extends Activity implements LocationListener {
	private ListView listViewBars;
	private LazyAdapter adapter;
	private LocationManager locationManager;
	private Location location;
    private String provider;
    private TextView txtListeVide;
    private AlertDialog dialogueTri;
    private Intent intent;
        
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Récupération des widgets
		listViewBars = (ListView) findViewById(R.id.listBarsHH);
		txtListeVide = (TextView) findViewById(R.id.txtListeVide);
		    	
		// Gestion de la géolocalisation
		locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		provider = LocationManager.NETWORK_PROVIDER;
		location = locationManager.getLastKnownLocation(provider);
		if (null == location) this.onLocationChanged(location);
		
		// La liste des bars est vide
		if (Utils.getListeBarsHH().isEmpty()) {
			txtListeVide.setVisibility(View.VISIBLE);
		} else {
			// Affichage de la liste
			adapter = new LazyAdapter(ListeBarsActivity.this, Utils.getListeBarsHH());
	        listViewBars.setAdapter(adapter);
	        
	        // Listener sur le clic de la listview
	        listViewBars.setOnItemClickListener(mListeBarsListener);
		}
	}

	// Implémentation de l'écoute sur le clic
	private OnItemClickListener mListeBarsListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
			// On transmet la position du bar dans la liste à la page de la carte
	    	Intent intent = new Intent(ListeBarsActivity.this, DetailsBarActivity.class);
	    	intent.putExtra("BarSelectionne", position);
	    	startActivity(intent);
		}
    };
	        
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_liste_bars, menu);
		return true;
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_onlyHH:
	    	// Affichage des bars : Tous ou que les HH
	    	Resources resources = getResources();
	    	txtListeVide.setVisibility(View.GONE);
    		if (Utils.isOnlyHH()) {
	    		// On affiche la liste de tous les bars
	    		Utils.setOnlyHH(false);
	    		// On modifie le titre du menu et l'icone
	    		item.setTitle(resources.getString(R.string.barsHH));
	    		item.setIcon(R.drawable.ic_action_tous_bars);
	    		Utils.getListeBarsHH().clear();
	    		for (Bar bar : Utils.getListeTousBars()) {
	    			Utils.getListeBarsHH().add(bar);
	    		}
	    		// Affichage d'un toast indiquant ce qu'on affiche
	    		Toast.makeText(ListeBarsActivity.this, "Liste de tous les bars", Toast.LENGTH_SHORT).show();
	    	} else {
	    		// On affiche la liste des bars en HH aujourd'hui
	    		Utils.setOnlyHH(true);
	    		// On modifie le titre du menu et l'icône
	    		item.setTitle(resources.getString(R.string.tousLesBars));
	    		item.setIcon(R.drawable.ic_action_bars_hh);
	    		Utils.getListeBarsHH().clear();
	    		for (Bar bar : Utils.getListeTousBars()) {
	    			if (bar.isBarHHAujourdhui() && !Utils.estHHFini(bar))
	    				Utils.getListeBarsHH().add(bar);
	    		}
	    		// Affichage d'un toast indiquant ce qu'on affiche
	    		Toast.makeText(ListeBarsActivity.this, "Liste des bars en Happy Hour en cours ou à venir", Toast.LENGTH_SHORT).show();
	    	}
	    	// On reconstruit la vue
	    	if (Utils.getListeBarsHH().isEmpty()) {
				txtListeVide.setVisibility(View.VISIBLE);
			} 
	    	adapter = new LazyAdapter(ListeBarsActivity.this, Utils.getListeBarsHH());
	        listViewBars.setAdapter(adapter);
	        listViewBars.setOnItemClickListener(mListeBarsListener);
			break;
    	case R.id.action_tri:
	    	// On affiche une boite de dialogue avec les 3 choix de tri
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	final String[] itemsTri = new String[] { "Prix", "Distance", "Heure de départ", "Favoris" };
	    	builder.setTitle("Trier par...");
	    	builder.setSingleChoiceItems(itemsTri, Utils.getItemTriSelectionne(), 
	    			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					// Appel fonction tri
					triListe(item);
					dialogueTri.dismiss();
				}
			});
	    	dialogueTri = builder.create();
	    	dialogueTri.show();
	    	break;
    	case R.id.action_maps:
    	// On transmet à la page de la carte la liste des bars
    	intent = new Intent(ListeBarsActivity.this, MapActivity.class);
    	intent.putExtra("listeBars", (Parcelable) Utils.getListeBarsHH());
    	intent.putExtra("origine", Utils.getActivityListeBars());
    	startActivity(intent);
    	break;
	    case R.id.action_refresh:
	    	// On vide la liste et on supprime le texte de liste vide
	    	Utils.getListeBarsHH().clear();
	    	
	    	if (View.VISIBLE == txtListeVide.getVisibility())
	    		txtListeVide.setVisibility(View.GONE);
	    	// On exécute la fonction qui alimente et affiche la liste des bars
	    	new GetServerData().execute();
	    	break;	    	
	    case R.id.action_prop_bar:
	    	// Proposer un bar : on ouvre la page avec le formulaire
	    	intent = new Intent(ListeBarsActivity.this, PropErreurActivity.class);
	    	intent.putExtra("origine", Utils.getActivityPropoBar());
	    	startActivity(intent);
	    	break;
	    default:
	      break;
	    }
	    return true;
	} 

	@Override
	protected void onResume() {
		super.onResume();
		// Aucune location n'est dispo, on propose de les activer (seulement la première fois)
		if (!Utils.isEcranDejaAppele() &&
				!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && 
				!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Utils.setEcranDejaAppele(true);
			createGpsDisabledAlert();
		}
		// On refait le tri pour être sûr d'être à jour
		adapter.notifyDataSetChanged();
		triListe(Utils.getItemTriSelectionne());
		listViewBars.setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
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
            .setMessage("Aucun service de localisation disponible. \nIl est préférable de les activer pour profiter " +
            				"de toutes les fonctionnalités de l'application")
            .setCancelable(false)
            .setPositiveButton("Activer la localisation ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    	startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                        finish();
                    }
                }
            );
        localBuilder.setNegativeButton("Continuer sans localisation ",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.cancel();
                }
            }
        );
        localBuilder.create().show();
    }
	
	private class GetServerData extends AsyncTask<Void, Void, String> {
		
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(ListeBarsActivity.this);
			progressDialog.setMessage("Veuillez patienter...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(Void... urls) {
			// Connection au serveur
			String strReponse = Utils.makeWebCall();
			if (null == strReponse) {
				return "ExceptionServer";
			}
			// On ouvre la BDD
			FavorisDao favorisDao = new FavorisDao(ListeBarsActivity.this);
			favorisDao.open();
			// Alimentation de la liste à partir du serveur
			Utils.alimentationListeBars(strReponse, ListeBarsActivity.this, location, favorisDao);
			// Fermeture de la BDD
			favorisDao.close();
	        return null;
		}
		
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			// Problème lors de la connexion au serveur
			if ("ExceptionServer".equalsIgnoreCase(result)){
				Toast.makeText(ListeBarsActivity.this, "Erreur lors de la connexion au serveur.\nVérifiez votre connexion réseau", 
						Toast.LENGTH_LONG).show();
			} else {
				// La liste est vide
				if (Utils.getListeBarsHH().isEmpty()) {
					txtListeVide.setVisibility(View.VISIBLE);
				} else {
					// Appel fonction tri
					triListe(Utils.getItemTriSelectionne());
				}
			}
		}
	}
	
	private void triListe(int item) {
		switch(item) {
		// Tri par prix (valeur par défaut)
		case 0:
			Utils.setItemTriSelectionne(0);
			Collections.sort(Utils.getListeBarsHH(), new Comparator<Bar>() {
				@Override
				public int compare(Bar bar1, Bar bar2) {
					return bar1.getBarPrixBiereActuel().compareTo(
							bar2.getBarPrixBiereActuel());
				}
			});
			Collections.sort(Utils.getListeTousBars(), new Comparator<Bar>() {
				@Override
				public int compare(Bar bar1, Bar bar2) {
					return bar1.getBarPrixBiereActuel().compareTo(
							bar2.getBarPrixBiereActuel());
				}
			});
			// On reconstruit la vue
			adapter = new LazyAdapter(ListeBarsActivity.this, 
					Utils.getListeBarsHH());
	        listViewBars.setAdapter(adapter);
			break;
		// Tri par distance
		case 1:
			Utils.setItemTriSelectionne(1);
			Collections.sort(Utils.getListeBarsHH(), new BarComparator());
			Collections.sort(Utils.getListeTousBars(), new BarComparator());
			// On reconstruit la vue
			adapter = new LazyAdapter(ListeBarsActivity.this, Utils.getListeBarsHH());
	        listViewBars.setAdapter(adapter);
			break;
		// Tri par horaire
		case 2:
			Utils.setItemTriSelectionne(2);
			Collections.sort(Utils.getListeBarsHH(), new Comparator<Bar>() {
				@Override
				public int compare(Bar bar1, Bar bar2) {
					return bar1.getBarHeureDebutHH().compareTo(bar2.getBarHeureDebutHH());
				}
			});
			Collections.sort(Utils.getListeTousBars(), new Comparator<Bar>() {
				@Override
				public int compare(Bar bar1, Bar bar2) {
					return bar1.getBarHeureDebutHH().compareTo(bar2.getBarHeureDebutHH());
				}
			});
			// On reconstruit la vue
			adapter = new LazyAdapter(ListeBarsActivity.this, 
					Utils.getListeBarsHH());
	        listViewBars.setAdapter(adapter);
			break;
		// Tri par favoris
		case 3:
			Utils.setItemTriSelectionne(3);
			Collections.sort(Utils.getListeBarsHH(), new Comparator<Bar>() {
				@Override
				public int compare(Bar bar1, Bar bar2) {
					return Boolean.valueOf(bar2.isBarFavori()).compareTo(
							Boolean.valueOf(bar1.isBarFavori()));
				}
			});
			Collections.sort(Utils.getListeTousBars(), new Comparator<Bar>() {
				@Override
				public int compare(Bar bar1, Bar bar2) {
					return Boolean.valueOf(bar2.isBarFavori()).compareTo(
							Boolean.valueOf(bar1.isBarFavori()));
				}
			});
			// On reconstruit la vue
			adapter = new LazyAdapter(ListeBarsActivity.this, 
					Utils.getListeBarsHH());
	        listViewBars.setAdapter(adapter);
			break;
		}
	}
		
}
