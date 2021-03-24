package Würfeltablet;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.AbstractAjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;



@Named
@SessionScoped
@Stateful
public class WürfelTablet implements Serializable{
    private int würfelPoolGröße;
    private ArrayList<Integer> selectOneMenuWPG;
    private ArrayList<Integer> selectOneMenuEA;
    private int edgeAttribute;
    private final int minEdgeAttribute = 0;
    private final int maxEdgeAttribute = 7;
    private int edgePoolGröße;
    private boolean würfelDrehbar;
    private boolean einWürfelVonEinsAufZweiDrehbar;
    private boolean einWürfelVonVierAufFünfDrehbar;
    private boolean würfelGedreht;
    private boolean würfelNeuWürfelbar;
    private boolean einenWürfelNeuGewürfelt;
    private boolean nichterfolgeNeuWürfelbar;
    private boolean nichterfolgeNeuGewürfelt;
    private boolean mitEdgeWürfelbar;
    private boolean mitEdgeGewürfelt;
    
    @Inject 
    private WürfelPool wp;
    @Inject
    private EdgePool ep;
    
    @PostConstruct
    public void init (){
	wp.setPoolGröße(1);
	würfelPoolGröße = 1;
	edgeAttribute = minEdgeAttribute;
	edgePoolGröße = minEdgeAttribute;
	ep.setWert(minEdgeAttribute);
	bereinigen();
	selectOneMenuWerteSetzen();
    }
    
    private void selectOneMenuWerteSetzen() {
    	selectOneMenuWPG = new ArrayList<Integer>();
    	selectOneMenuEA = new ArrayList<Integer>();
    	for (int i = wp.getMinWürfel(); i <=wp.getMaxWürfel(); i++) {
    		selectOneMenuWPG.add(i);
    	}
    	for (int i = ep.getMinimalGröße(); i<=ep.getMaximalGröße(); i++) {
    		selectOneMenuEA.add(i);
    	}
	}

	private void bereinigen() {
	würfelDrehbar = false;
	würfelGedreht = false;
	würfelNeuWürfelbar = false;
	einenWürfelNeuGewürfelt = false;
	nichterfolgeNeuWürfelbar = false;
	nichterfolgeNeuGewürfelt = false;
	mitEdgeWürfelbar = false;
	mitEdgeGewürfelt = false;
    }
    
    public void berechnen() {
    	einWürfelVonEinsAufZweiDrehbar = einWürfelVonEinsAufZweiDrehbar();
    	einWürfelVonVierAufFünfDrehbar = einWürfelVonVierAufFünfDrehbar();
		würfelNeuWürfelbar = einenNichterfolgNeuWürfelbar();
		würfelDrehbar = würfelDrehbar();
		nichterfolgeNeuWürfelbar = nichterfolgeNeuWürfelbar();
		mitEdgeWürfelbar = ep.isMitEdgeWürfelbar();
    }
    public String getWerteAusgabe() {
    	return wp.wertAusgabe();
    }
    
    
    private boolean einenNichterfolgNeuWürfelbar() {
	if (wp.isNachwürfelbar() && ep.isWürfelWürfelbar() && !würfelGedreht && !nichterfolgeNeuGewürfelt
		&& !mitEdgeGewürfelt) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    private boolean würfelDrehbar() {
	if (wp.isDrehbar() && ep.isWürfelWürfelbar() && !einenWürfelNeuGewürfelt && !nichterfolgeNeuGewürfelt
		&& !mitEdgeGewürfelt) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    private boolean einWürfelVonEinsAufZweiDrehbar() {
    	if (wp.isEinWürfelAufZweiDrehbar() && ep.isWürfelWürfelbar() && !nichterfolgeNeuGewürfelt
    		&& !mitEdgeGewürfelt) {
    	    return true;
    	}
    	else {
    	    return false;
    	}
    }
    
    private boolean einWürfelVonVierAufFünfDrehbar() {
    	if (wp.isEinWürfelAufFünfDrehbar() && ep.isWürfelWürfelbar() && !nichterfolgeNeuGewürfelt
    		&& !mitEdgeGewürfelt) {
    	    return true;
    	}
    	else {
    	    return false;
    	}
    }
    
    private boolean nichterfolgeNeuWürfelbar() {
		if(wp.isNachwürfelbar() && ep.isNachwürfelbar() && !einenWürfelNeuGewürfelt && !würfelGedreht 
			&& !nichterfolgeNeuGewürfelt && !mitEdgeGewürfelt) {
		    return true;
		}
		else {
		    return false;
		}
    }
    
    private boolean mitEdgeWürfelbar() {
		if(ep.isMitEdgeWürfelbar() && !einenWürfelNeuGewürfelt && !würfelGedreht && !nichterfolgeNeuGewürfelt
			&& !mitEdgeGewürfelt) {
		    return true;
		}
		else {
		    return false;
		}
    }
    
    public void setWürfelPoolGröße(int würfelPoolGröße) {
	this.würfelPoolGröße = würfelPoolGröße;
	wp.setPoolGröße(this.würfelPoolGröße);
    }
    
    public void setEdgeAttribute(int edgeAttribute) {
	this.edgeAttribute = edgeAttribute;
	ep.setWert(edgeAttribute);
    }
    
    public int getEdgeAttribute() {
	return this.edgeAttribute;
    }
    
    public void setEdgePoolGröße (int edgePool) {
	this.edgePoolGröße = edgePool;
	ep.setWert(this.edgePoolGröße);
    }
    
    public int getEdgePoolGröße () {
	return this.edgePoolGröße;
    }
        
    public ArrayList<Integer> getSelectOneMenuWPG() {
		return selectOneMenuWPG;
	}

	public void setSelectOneMenuWPG(ArrayList<Integer> selectOneMenuWPG) {
		this.selectOneMenuWPG = selectOneMenuWPG;
	}

	public ArrayList<Integer> getSelectOneMenuEA() {
		return selectOneMenuEA;
	}

	public void setSelectOneMenuEA(ArrayList<Integer> selectOneMenuEA) {
		this.selectOneMenuEA = selectOneMenuEA;
	}

	public void einenNichterfolgNeuWürfeln() {
		if (würfelNeuWürfelbar) {
		    wp.einenWürfelNeuWürfeln();
		    ep.würfelNeuWürfeln();
		    einenWürfelNeuGewürfelt = true;
		    edgePoolGröße = ep.getWert();
		}
    }
    
    public void einenWürfelVonEinsAufZweiDrehen() {
		if (einWürfelVonEinsAufZweiDrehbar()) {
		    wp.einenWürfelVonEinsAufZweiDrehen();
		    ep.würfelDrehen();
		    würfelGedreht = true;
		    edgePoolGröße = ep.getWert();
		    berechnen();
		}
    }
    
    public void einenWürfelVonVierAufFünfDrehen() {
	if (würfelDrehbar) {
	    wp.einenWürfelVonVierAufFünfDrehen();
	    ep.würfelDrehen();
	    würfelGedreht = true;
	    edgePoolGröße = ep.getWert();
	    berechnen();
	}
    }
    
    public void nichterfolgeNeuWürfeln() {
		if (nichterfolgeNeuWürfelbar) {
		    wp.nichterfolgeNachwürfeln();
		    ep.nachWürfeln();
		    nichterfolgeNeuGewürfelt = true;
		    edgePoolGröße = ep.getWert();
		    berechnen();
		}
    }
    
    public void mitEdgeWürfeln() {
		if (mitEdgeWürfelbar) {
		    wp.mitEdgeWürfeln(this.edgeAttribute);
		    ep.mitEdgeWürfeln();
		    mitEdgeGewürfelt = true;
		    edgePoolGröße = ep.getWert();
		    berechnen();
		}
    }
        
    public void würfeln() {
    	wp.setPoolGröße(würfelPoolGröße);
    	wp.würfeln();
    	bereinigen();
    	berechnen();
    }

    public int getWürfelPoolGröße() {
        return würfelPoolGröße;
    }
    
    public boolean isBtnPlusEinEdgeEnabled() {
    	if (edgePoolGröße < 7) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    public boolean isBtnEinenNichterfolgNeuWürfelnEnabled() {
    	return einenNichterfolgNeuWürfelbar();
    }
    
    public boolean isBtnEinenWürfelVonEinsAufZweiDrehenEnabled() {
    	return einWürfelVonEinsAufZweiDrehbar();
    }
    
    public boolean isBtnEinenWürfelVonVierAufFünfDrehenEnabled() {
    	return einWürfelVonVierAufFünfDrehbar();
    }
    
    public boolean isBtnNichterfolgeNeuWürfelnEnabled() {
    	return nichterfolgeNeuWürfelbar();
    }
    
    public boolean isBtnMitEdgeWürfelnEnabled() {
    	return mitEdgeWürfelbar();
    }

    public void edgePoolPlusEins(ActionEvent actionEvent) {
    	System.out.println("edgePoolPlusEins gedückt");
    	if (actionEvent.getComponent().getId().equals("EdgePoolPlusEins") && edgePoolGröße < ep.getMaximalGröße()) {
    		ep.EdgePoolUmEinsErhöhen();
    		edgePoolGröße++;
    		berechnen();
    	}
    }
    
    public void onWürfelPoolGrößeChange(final AbstractAjaxBehaviorEvent event) {
    	if (würfelPoolGröße >= wp.getMinWürfel() && würfelPoolGröße <= wp.getMaxWürfel()) {
    		wp.setPoolGröße(würfelPoolGröße);
    		this.edgePoolGröße = würfelPoolGröße;
    	}
    	else {
    		würfelPoolGröße = 0;
    	}
    	berechnen();
    }
    
    public void onEdgeAttributeChange(SelectEvent event) {
    	int i = (Integer) event.getObject(); 
    	if (i >= ep.getMinimalGröße() && i <= ep.getMaximalGröße()) {
    		ep.setWert(i);
    		this.edgePoolGröße = i;
    		
    	}
    	else {
    		edgeAttribute = 0;
    	}
    	berechnen();
    }
    
    public void btnWürfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("Würfeln")) {
    		würfeln();
    	}
    }
    
    public void btnMitEdgeWürfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("MitEdgeWürfeln")) {
    		mitEdgeWürfeln();
    	}
    }
    
    public void btnEinenNichterfolgNeuWürfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("EinenNichterfolgNeuWürfeln")) {
    		einenNichterfolgNeuWürfeln();
    	}
    }
    
    public void btnEinenWürfelVonEinsAufZweiDrehen(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("EinenWürfelVonEinsAufZweiDrehen")) {
    		einenWürfelVonEinsAufZweiDrehen();
    	}
    }
    
    public void btnEinenWürfelVonVierAufFünfDrehen(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("EinenWürfelVonVierAufFünfDrehen")) {
    		einenWürfelVonVierAufFünfDrehen();
    	}
    }
    
    public void btnNichterfolgeNeuWürfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("NichterfolgeNeuWürfeln")) {
    		nichterfolgeNeuWürfeln();
    	}
    }
}
