package com.example.mupin_v2.models;

public class Mupins {

    private int  caras;
    private String idMupi, tipoMueble, estacion, ubicacion, ciudad, limpieza, observaciones, mantenimiento, usuario;

    public Mupins() {

    }

    public Mupins(String idMupi, int caras, String tipoMueble, String estacion, String ubicacion,
                  String ciudad, String limpieza, String observaciones, String mantenimiento) {

        this.idMupi = idMupi;
        this.caras = caras;
        this.tipoMueble = tipoMueble;
        this.estacion = estacion;
        this.ubicacion = ubicacion;
        this.ciudad = ciudad;
        this.limpieza = limpieza;
        this.observaciones = observaciones;
        this.mantenimiento = mantenimiento;

    }

    public String getIdMupi() {
        return idMupi;
    }

    public int getCaras() {
        return caras;
    }

    public String getTipoMueble() {
        return tipoMueble;
    }

    public String getEstacion() {
        return estacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getLimpieza() {
        return limpieza;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getMantenimiento() {
        return mantenimiento;
    }

    public void setCaras(int caras) {
        this.caras = caras;
    }

    public void setIdMupi(String idMupi) {
        this.idMupi = idMupi;
    }

    public void setTipoMueble(String tipoMueble) {
        this.tipoMueble = tipoMueble;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setLimpieza(String limpieza) {
        this.limpieza = limpieza;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setMantenimiento(String mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
