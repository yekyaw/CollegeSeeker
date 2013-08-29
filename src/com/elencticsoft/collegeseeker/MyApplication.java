package com.elencticsoft.collegeseeker;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

import com.appbrain.AppBrain;

@ReportsCrashes(
        formKey = "",
        formUri = "https://collegeseeker.iriscouch.com/acra-myapp/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="user",
        formUriBasicAuthPassword="password")
public class MyApplication extends Application {
  @Override
  public void onCreate() {
    // The following line triggers the initialization of ACRA
    super.onCreate();
    ACRA.init(this);
    AppBrain.initApp(this);
  }
}
