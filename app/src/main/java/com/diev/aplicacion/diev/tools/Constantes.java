package com.diev.aplicacion.diev.tools;

/**
 * Clase que contiene los códigos usados en "diev" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = ":8080";
    /**
     * Dirección IP de  AVD
     */
    private static final String IP = "192.168.0.7";
    /**
     * URLs del Web Service
     */

    public static final String GET = "http://" + IP + PUERTO_HOST + "/diev/obtener_user.php";
    public static final String INSERT = "http://" + IP + PUERTO_HOST + "/diev/insertar_user.php";

    /**
     * Clave para el valor extra que representa al identificador de un user
     */
    public static final String EXTRA_ID = "IDEXTRA";

}
