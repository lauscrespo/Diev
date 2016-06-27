package com.diev.aplicacion.diev.connection;


public enum Table2 {

    tbl_Evento("tbl_Evento");

    private final String text;


    Table2(final String text) {
        this.text = text;
    }


    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
