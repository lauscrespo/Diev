package com.diev.aplicacion.diev.brl;

import android.support.annotation.NonNull;


abstract class Brl {

    protected String[] columns;


    public Brl(@NonNull String columns) {
        this.columns = columns.split(",");
    }
}
