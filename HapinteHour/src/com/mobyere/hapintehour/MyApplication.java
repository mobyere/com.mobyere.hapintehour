package com.mobyere.hapintehour;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "",
formUri="https://mobyere.cloudant.com/acra-hapintehour/_design/acra-storage/_update/report",
reportType = org.acra.sender.HttpSender.Type.JSON,
httpMethod = org.acra.sender.HttpSender.Method.PUT,
formUriBasicAuthLogin = "mobyere", 
formUriBasicAuthPassword = "ClouSl1p82",
mode=ReportingInteractionMode.SILENT)

public class MyApplication extends Application {

	@Override
    public void onCreate() {
        super.onCreate();

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}
