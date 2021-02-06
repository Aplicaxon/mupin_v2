package com.example.mupin_v2.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PublicidadModel {

    public PublicidadModel() {}

    private String plubicidadId, publicidadNombre;
    private Boolean izInMupi;
    @ServerTimestamp
    private Date date;

    public PublicidadModel(String plubicidadId, String publicidadNombre, Boolean izInMupi) {
        this.plubicidadId = plubicidadId;
        this.publicidadNombre = publicidadNombre;
        this.izInMupi = izInMupi;
    }

    public String getProductId() {
        return plubicidadId;
    }

    public String getPublicidadNombre() {
        return publicidadNombre;
    }

    public Boolean getIzInMupi() {
        return izInMupi;
    }

    public Date getDate() {
        return date;
    }
}
