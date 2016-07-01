package com.diev.aplicacion.diev.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;


public class MyBackupAgent extends BackupAgentHelper {
    static final String File_Name_Of_Prefrences = "myPrefrences";
    static final String PREFS_BACKUP_KEY = "backup";
    // The name of the SharedPreferences file
  //  static final String PREFS = "myprefs";

    // A key to uniquely identify the set of backup data
  //  static final String PREFS_BACKUP_KEY = "myprefs";

    @Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, File_Name_Of_Prefrences);
        addHelper(PREFS_BACKUP_KEY, helper);
    }



}
