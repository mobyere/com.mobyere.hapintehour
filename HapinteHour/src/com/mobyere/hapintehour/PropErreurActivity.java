package com.mobyere.hapintehour;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mobyere.hapintehour.R;

public class PropErreurActivity extends Activity {

	private EditText txtNomBar;
	private ImageView imgTelBar;
	private EditText txtTelBar;
	private ImageView imgMailBar;
	private EditText txtMailBar;
	private TextView txtTitreInfosProp;
	private EditText txtInfosProp;
	private String ecranAppelant;
	private Button btnValider;
	private String sujetMail;
	private String texteMail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prop_erreur);
		// Show the Up button in the action bar.
		setupActionBar();
				
		// On récupère la position du bar cliqué
		Bundle b = getIntent().getExtras();
		ecranAppelant = b.getString("origine");
				
		// Récupération des widgets
		txtNomBar = (EditText) findViewById(R.id.etxtNomBarPropErreur);
		imgTelBar = (ImageView) findViewById(R.id.imgTelephonePropErreur);
		txtTelBar = (EditText) findViewById(R.id.etxtNumTelBarPropErreur);
		imgMailBar = (ImageView) findViewById(R.id.imgMailPropErreur);
		txtMailBar = (EditText) findViewById(R.id.etxtMailBarPropErreur);
		txtTitreInfosProp = (TextView) findViewById(R.id.txtTitreInfosPropErreur);
		txtInfosProp = (EditText) findViewById(R.id.etxtInfosPropErreur);
		btnValider = (Button) findViewById(R.id.btnValidPropErreur);
		
		// On est là pour proposer un bar
		if (ecranAppelant.equals(Utils.getPropoBar())) {
			// On affiche les champs mail et tel
			txtNomBar.setEnabled(true);
			txtNomBar.setText("");
			imgTelBar.setVisibility(View.VISIBLE);
			txtTelBar.setVisibility(View.VISIBLE);
			imgMailBar.setVisibility(View.VISIBLE);
			txtMailBar.setVisibility(View.VISIBLE);
			txtTitreInfosProp.setText("Informations utiles : ");
			sujetMail = "Proposition d'un nouveau bar";
		} else {
			// On signale une erreur
			// on pré-remplit le nom du bar
			txtNomBar.setText(b.getString("nomBar"));
			txtNomBar.setEnabled(false);
			txtTitreInfosProp.setText("Détail de l'erreur");
			sujetMail = "Signalement d'une erreur";
			// On cache les champs mail et tel
			imgTelBar.setVisibility(View.GONE);
			txtTelBar.setVisibility(View.GONE);
			imgMailBar.setVisibility(View.GONE);
			txtMailBar.setVisibility(View.GONE);
		}
		
		// Listener sur le bouton
		btnValider.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean controlesOK = true;
				// Contrôles
				String numTel = txtTelBar.getText().toString();
				// Si un numéro de téléphone est saisi
				if (numTel.length() > 0) {
					// il doit faire 10 chiffres
					if (numTel.length() != 10) {
						controlesOK = false;
					} else {
						controlesOK = numTel.matches("[0-9]*");
					}
					if (!controlesOK)
						Toast.makeText(PropErreurActivity.this, "Le numéro de téléphone n'est pas correct", 
								Toast.LENGTH_SHORT).show();
				}
				// Adresse mail
				if (controlesOK && txtMailBar.getText().length() > 0) {
					if (!txtMailBar.getText().toString().matches("[a-9]@s")) {
						controlesOK = false;
						Toast.makeText(PropErreurActivity.this, "Le mail saisi n'est pas correct", 
								Toast.LENGTH_SHORT).show();
					}
				}
				// On vérifie que le nom du bar soit bien saisi
				if (controlesOK && txtNomBar.getText().length() == 0) {
					controlesOK = false;
					Toast.makeText(PropErreurActivity.this, "Le nom du bar doit être saisi", 
							Toast.LENGTH_SHORT).show();
				}
				if (controlesOK){
					// Récupération des infos
					texteMail = "Bar : " + txtNomBar.getText() + "\nTéléphone : " + 
							txtTelBar.getText() + "\nMail : " + txtMailBar.getText() + 
							"\nMessage : " + txtInfosProp.getText();
					
					// Envoi du mail
					new sendEmail().execute();
				}
				
			}
		});
	}
	
	private class sendEmail extends AsyncTask<Void, Void, String> {

		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(PropErreurActivity.this);
			progressDialog.setTitle("Envoi du message en cours");
			progressDialog.setMessage("Veuillez patienter...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			String result = "OK";
			Mail m = new Mail();
			m.setSubject(sujetMail);
			m.setBody(texteMail);
			try {
				if(!m.send()) { 
		        	result = "KO";
		        } 
			} catch(Exception e) { 
		        Log.e("MailApp", "Could not send email", e); 
		    } 
			return result;
		}
		
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if ("OK".equalsIgnoreCase(result)) {
				Toast.makeText(PropErreurActivity.this, "Merci ! Nous le prendrons en compte le plus rapidement possible", 
						Toast.LENGTH_LONG).show(); 
			} else {
				Toast.makeText(PropErreurActivity.this, "Erreur lors de l'envoi du message", 
	        			Toast.LENGTH_LONG).show(); 
			}
			PropErreurActivity.this.finish();
		}
		
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
			//NavUtils.navigateUpFromSameTask(this);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
