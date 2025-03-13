package it.eng.tz.urbamid.administration.mappa.dao.model;

import java.io.Serializable;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;

/**
 * DTO for table public.u_mappa_layer
 */
public class UMappaLayer implements Serializable{

    private static final long serialVersionUID = 4477113761L;
    private Boolean isNew;
    //COLUMN id
    private int id;
    //COLUMN id_mappa
    private int idMappa;
    //COLUMN nome_layer
    private String nomeLayer;
    //COLUMN title_layer
    private String titleLayer;
    //COLUMN id_parent
    private String idParent;
    //COLUMN tipo
    private String tipo;
    //COLUMN abilitato
    private boolean abilitato;
    //COLUMN trasparenza
    private String trasparenza;
    //COLUMN campo_1
    private String campo1;
    //COLUMN campo_2
    private String campo2;
    //COLUMN campo_3
    private String campo3;
    //COLUMN pos
    private int pos;
    
    public Boolean getIsNew() {
		return isNew;
	}
    
    public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
    
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getIdMappa(){
        return this.idMappa;
    }

    public void setIdMappa(int idMappa){
        this.idMappa = idMappa;
    }

    public String getNomeLayer(){
        return this.nomeLayer;
    }

    public void setNomeLayer(String nomeLayer){
        this.nomeLayer = nomeLayer;
    }
    public String getTitleLayer() {
		return titleLayer;
	}
	public void setTitleLayer(String titleLayer) {
		this.titleLayer = titleLayer;
	}
    public String getIdParent(){
        return this.idParent;
    }

    public void setIdParent(String idParent){
        this.idParent = idParent;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public boolean getAbilitato(){
        return this.abilitato;
    }

    public void setAbilitato(boolean abilitato){
        this.abilitato = abilitato;
    }

    public String getTrasparenza(){
        return this.trasparenza;
    }

    public void setTrasparenza(String trasparenza){
        this.trasparenza = trasparenza;
    }

    public String getCampo1(){
        return this.campo1;
    }

    public void setCampo1(String campo1){
        this.campo1 = campo1;
    }

    public String getCampo2(){
        return this.campo2;
    }

    public void setCampo2(String campo2){
        this.campo2 = campo2;
    }

    public String getCampo3(){
        return this.campo3;
    }
    
    public void setCampo3(String campo3){
        this.campo3 = campo3;
    }
    
    public int getPos() {
		return pos;
	}
    
    public void setPos(int pos) {
		this.pos = pos;
	}
}
