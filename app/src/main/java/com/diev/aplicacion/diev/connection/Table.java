package com.diev.aplicacion.diev.connection;


public enum Table {

    tbl_Usuario("tbl_Usuario");

    private final String text;


    Table(final String text) {
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
