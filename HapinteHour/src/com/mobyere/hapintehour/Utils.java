package com.mobyere.hapintehour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
 
public class Utils {
	
	//private static final String strURL = "http://192.168.2.103/";
	private static final String strURL = "http://hapintehour.olympe.in/";
	private static BarList listeTousBars; // Liste de tous les bars
	private static BarList listeBarsHH;   // Liste des bars affichés (liste de travail)
	private static final String ACTIVITY_PROPO_BAR = "PropositionBar";
	private static final String ACTIVITY_SIGNAL_ERREUR = "SignalErreur";
	private static final String ACTIVITY_LISTE_BARS = "listeBars";
	private static final String ACTIVITY_DETAILS_BAR = "detailsBar";
	private static boolean onlyHH = true; // si vrai, on n'affiche que les bars en HH
	private static int itemTriSelectionne = 0; // parametre de tri
    
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
    public static String makeWebCall() {
    	
    	String url = Utils.getStrURL() + "bars.php";
	    DefaultHttpClient client = new DefaultHttpClient();
	    HttpGet httpRequest = new HttpGet(url);
	    
	    try {
	        HttpResponse httpResponse = client.execute(httpRequest);
	         final int statusCode = httpResponse.getStatusLine().getStatusCode();
	         if (statusCode != HttpStatus.SC_OK) {
	            return null;
	         }
	         HttpEntity entity = httpResponse.getEntity();
	         InputStream instream = null;
	         if (entity != null) {
	              instream = entity.getContent();
	         }
            return iStream_to_String(instream);
	    }
	    catch (IOException e) {
	        httpRequest.abort();
	        Log.w(ListeBarsActivity.class.getSimpleName(), "Error for URL =>" + url, e);
	    }
	    return null;
	 }
    
    public static String iStream_to_String(InputStream is1) {
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is1), 4096);
	    String line;
	    StringBuilder sb = new StringBuilder();
	    try {
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    String contentOfMyInputStream = sb.toString();
	    return contentOfMyInputStream;
	}
    
    public static double[] recupererCoordonneesGPS(String adresse, Context context){
    	double[] coordonnees = new double[2];
    	
    	Geocoder gc = new Geocoder(context);
    	try {
    		List<Address> adressesTrouvees = gc.getFromLocationName(adresse, 1);
    		if (adressesTrouvees.size() > 0) {
    			coordonnees[0] = adressesTrouvees.get(0).getLatitude();
    			coordonnees[1] = adressesTrouvees.get(0).getLongitude();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	return coordonnees;
    }
	
	public static float calculDistance(Location location, double latitude, double longitude) {
		float[] results = new float[1];
		Location.distanceBetween(location.getLatitude(), location.getLongitude(), 
				latitude, longitude, results);		
		if (results[0] != 0) {
			return results[0];
		}
		return 0;
	}
	
	public static boolean estHHActuellement(Bar bar) {
		boolean estHH = false;
		String heureDebutHH = bar.getBarHeureDebutHH();
		String heureFinHH = bar.getBarHeureFinHH();
		if (bar.isBarHHAujourdhui()) {
			try {
				// Récupération heure locale
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.FRANCE);
				Date heureDeb = sdf.parse(heureDebutHH);
				Date heureFin = sdf.parse(heureFinHH);
				Calendar calendar = new GregorianCalendar();
				String strHeureAct = sdf.format(calendar.getTime());
				Date heureActuelle = sdf.parse(strHeureAct);
				if (heureActuelle.after(heureDeb) && heureActuelle.before(heureFin)) {
					estHH = true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return estHH;
	}
	
	// Fonction qui alimente la liste des bars à partir du serveur
	// Retourne la liste triée (bars en HH triés par distance)
	public static void alimentationListeBars(String strReponse, Context context, 
			Location location) {
		listeTousBars = new BarList();
		listeBarsHH = new BarList();
		
		Bar bar = null;
        URL aURL;
        double[] coordonneesGps = new double[2];
        try{
			JSONObject json_data=null;
			JSONArray jArray = new JSONArray(strReponse);
	        for(int i=0;i<jArray.length();i++){
	        	// Alimentation données du bar
	        	json_data = jArray.getJSONObject(i);
	        	bar = new Bar();
	        	bar.setBarHHAujourdhui(json_data.getInt("bar_isHH") == 1);
				bar.setBarID(Integer.parseInt(json_data.getString("bar_ID")));
	        	bar.setBarNom(json_data.getString("bar_Nom"));
	        	aURL = new URL(getStrURL() + json_data.getString("bar_Image"));
	        	bar.setBarUrlImage(aURL.toString());
	        	bar.setBarPrixBiereHH(json_data.getString("bar_PrixBiereHH"));
	        	bar.setBarPrixBiereHN(json_data.getString("bar_PrixBiereHN"));
	        	bar.setBarPrixVinHH(json_data.getString("bar_PrixVinHH"));
	        	bar.setBarPrixVinHN(json_data.getString("bar_PrixVinHN"));
        		bar.setBarHeureDebutHH(json_data.getString("bar_HeureDebutHH"));
	        	bar.setBarHeureFinHH(json_data.getString("bar_HeureFinHH"));
	        	bar.setBarRue(json_data.getString("bar_Rue")); 
				bar.setBarCodePostal(json_data.getString("bar_CodePostal"));
				bar.setBarVille(json_data.getString("bar_Ville"));
	        	bar.setBarTelephone(json_data.getString("bar_Telephone"));
	        	// Récupération localisation et calcul distance
				coordonneesGps = Utils.recupererCoordonneesGPS(bar.getBarRue() + " " +
						bar.getBarCodePostal() + " " + bar.getBarVille(), context);
	        	bar.setBarLatitude(coordonneesGps[0]);
	        	bar.setBarLongitude(coordonneesGps[1]);
				bar.setBarDistance(Utils.calculDistance(location, bar.getBarLatitude(), 
						bar.getBarLongitude()));
				bar.setBarHH(Utils.estHHActuellement(bar));
				bar.setBarHHLundi(json_data.getInt("bar_HHLundi") == 1);
				bar.setBarHHMardi(json_data.getInt("bar_HHMardi") == 1);
				bar.setBarHHMercredi(json_data.getInt("bar_HHMercredi") == 1);
				bar.setBarHHJeudi(json_data.getInt("bar_HHJeudi") == 1);
				bar.setBarHHVendredi(json_data.getInt("bar_HHVendredi") == 1);
				bar.setBarHHSamedi(json_data.getInt("bar_HHSamedi") == 1);
				bar.setBarHHDimanche(json_data.getInt("bar_HHDimanche") == 1);
				bar.setBarDebutHHLundi(json_data.getString("bar_HeureDebHHLundi"));
				bar.setBarDebutHHMardi(json_data.getString("bar_HeureDebHHMardi"));
				bar.setBarDebutHHMercredi(json_data.getString("bar_HeureDebHHMercredi"));
				bar.setBarDebutHHJeudi(json_data.getString("bar_HeureDebHHJeudi"));
				bar.setBarDebutHHVendredi(json_data.getString("bar_HeureDebHHVendredi"));
				bar.setBarDebutHHSamedi(json_data.getString("bar_HeureDebHHSamedi"));
				bar.setBarDebutHHDimanche(json_data.getString("bar_HeureDebHHDimanche"));
				bar.setBarFinHHLundi(json_data.getString("bar_HeureFinHHLundi"));
				bar.setBarFinHHMardi(json_data.getString("bar_HeureFinHHMardi"));
				bar.setBarFinHHMercredi(json_data.getString("bar_HeureFinHHMercredi"));
				bar.setBarFinHHJeudi(json_data.getString("bar_HeureFinHHJeudi"));
				bar.setBarFinHHVendredi(json_data.getString("bar_HeureFinHHVendredi"));
				bar.setBarFinHHSamedi(json_data.getString("bar_HeureFinHHSamedi"));
				bar.setBarFinHHDimanche(json_data.getString("bar_HeureFinHHDimanche"));
				bar.setBarDetails(json_data.getString("bar_Details"));
				// On rajoute le bar dans la liste de tous les bars
				listeTousBars.add(bar);
				// Si le bar est en HH aujourd'hui, on le rajoute dans la liste des bars en HH
				if (json_data.getInt("bar_isHH") == 1) {
					listeBarsHH.add(bar);
				}
	        }
	    } catch(JSONException e){
	    	 Log.i("tagjsonexp",""+e.toString());
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
        // Tri des bars par distance
        Collections.sort(listeTousBars, new BarComparator());
        Collections.sort(listeBarsHH, new BarComparator());
		//return null;
	}

	public static String getStrURL() {
		return strURL;
	}

	public static BarList getListeBarsHH() {
		return listeBarsHH;
	}

	public static void setListeBarsHH(BarList listeBarsHH) {
		Utils.listeBarsHH = listeBarsHH;
	}

	public static BarList getListeTousBars() {
		return listeTousBars;
	}

	public static void setListeTousBars(BarList listeTousBars) {
		Utils.listeTousBars = listeTousBars;
	}

	public static String getActivityPropoBar() {
		return ACTIVITY_PROPO_BAR;
	}

	public static String getActivitySignalErreur() {
		return ACTIVITY_SIGNAL_ERREUR;
	}

	public static String getActivityListeBars() {
		return ACTIVITY_LISTE_BARS;
	}

	public static String getActivityDetailsBar() {
		return ACTIVITY_DETAILS_BAR;
	}

	public static boolean isOnlyHH() {
		return onlyHH;
	}

	public static void setOnlyHH(boolean onlyHH) {
		Utils.onlyHH = onlyHH;
	}

	public static int getItemTriSelectionne() {
		return itemTriSelectionne;
	}

	public static void setItemTriSelectionne(int itemTriSelectionne) {
		Utils.itemTriSelectionne = itemTriSelectionne;
	}

}