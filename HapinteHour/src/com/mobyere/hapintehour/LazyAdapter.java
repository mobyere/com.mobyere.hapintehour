package com.mobyere.hapintehour;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobyere.hapintehour.R;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<Bar> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
 
    public LazyAdapter(Activity a, ArrayList<Bar> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.activity_listview_bars, null);
 
        TextView txtNomBar = (TextView)vi.findViewById(R.id.txtNomBar); 
        TextView txtPrixBiere = (TextView)vi.findViewById(R.id.txtPrixBiere); 
        //TextView txtPrixVin = (TextView)vi.findViewById(R.id.txtPrixVin);
        TextView txtHeureDebut = (TextView)vi.findViewById(R.id.txtHeureDebut);
        TextView txtHeureFin = (TextView)vi.findViewById(R.id.txtHeureFin); 
        ImageView imgBar =(ImageView)vi.findViewById(R.id.imgBar);
        TextView txtDistanceBar = (TextView)vi.findViewById(R.id.txtDistanceBar); 
        TextView txtmKm = (TextView) vi.findViewById(R.id.txtmKm);
        TextView txtEnCours = (TextView) vi.findViewById(R.id.txtenCours);
	 
	    Bar bar = new Bar();
        bar = data.get(position);
 
        // Setting all values in listview
        imageLoader.DisplayImage(bar.getBarUrlImage(), imgBar);
        txtNomBar.setText(bar.getBarNom());
        if (bar.isBarHHAujourdhui()) {
        	txtPrixBiere.setText(bar.getBarPrixBiereHH());
        	txtHeureDebut.setText(bar.getBarHeureDebutHH());
        	txtHeureFin.setText(bar.getBarHeureFinHH());
        } else {
        	txtPrixBiere.setText(bar.getBarPrixBiereHN());
        	txtHeureDebut.setVisibility(View.INVISIBLE);
        	txtHeureFin.setVisibility(View.GONE);
        }
        // Gestion m/km
        float fDistance = bar.getBarDistance();
        DecimalFormat df;
        // Si la distance est > 2000 mètres, on convertit en km
        if (fDistance > 2000) {
        	fDistance = fDistance / 1000;
        	txtmKm.setText("km");
        	df = new DecimalFormat("#.000");
        	txtDistanceBar.setText(df.format(fDistance));
        } else {
        	df = new DecimalFormat("#");
            txtDistanceBar.setText(df.format(bar.getBarDistance()));
        }
        // On cache le texte "en cours" si l'happy hour n'est pas en cours
        if (!bar.isBarHH()) 
        	txtEnCours.setVisibility(View.INVISIBLE);

        return vi;
    }
}
