package com.diev.aplicacion.diev.backup;

import android.os.ParcelFileDescriptor;

/**
 * Created by Colo on 28/06/2016.
 */
public class BackupDataInput extends Object {

    public void onRestore(BackupDataInput data, int appVersionCode,
                          ParcelFileDescriptor newState) {
        while (data.readNextHeader()) {
            String key = data.getKey();
            int dataSize = data.getDataSize();

            if (key.equals(MY_BACKUP_KEY_ONE)) {
                // process this kind of record here
                byte[] buffer = new byte[dataSize];
                data.readEntityData(buffer, 0, dataSize); // reads the entire entity at once

                // now 'buffer' holds the raw data and can be processed however
                // the agent wishes
                processBackupKeyOne(buffer);
            } else if (key.equals(MY_BACKUP_KEY_TO_IGNORE) {
                // a key we recognize but wish to discard
                data.skipEntityData();
            } // ... etc.
        }
    }
}
