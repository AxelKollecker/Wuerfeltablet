package W�rfeltablet;

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
public class W�rfelTablet implements Serializable{
    private int w�rfelPoolGr��e;
    private ArrayList<Integer> selectOneMenuWPG;
    private ArrayList<Integer> selectOneMenuEA;
    private int edgeAttribute;
    private final int minEdgeAttribute = 0;
    private final int maxEdgeAttribute = 7;
    private int edgePoolGr��e;
    private boolean w�rfelDrehbar;
    private boolean einW�rfelVonEinsAufZweiDrehbar;
    private boolean einW�rfelVonVierAufF�nfDrehbar;
    private boolean w�rfelGedreht;
    private boolean w�rfelNeuW�rfelbar;
    private boolean einenW�rfelNeuGew�rfelt;
    private boolean nichterfolgeNeuW�rfelbar;
    private boolean nichterfolgeNeuGew�rfelt;
    private boolean mitEdgeW�rfelbar;
    private boolean mitEdgeGew�rfelt;
    
    @Inject 
    private W�rfelPool wp;
    @Inject
    private EdgePool ep;
    
    @PostConstruct
    public void init (){
	wp.setPoolGr��e(1);
	w�rfelPoolGr��e = 1;
	edgeAttribute = minEdgeAttribute;
	edgePoolGr��e = minEdgeAttribute;
	ep.setWert(minEdgeAttribute);
	bereinigen();
	selectOneMenuWerteSetzen();
    }
    
    private void selectOneMenuWerteSetzen() {
    	selectOneMenuWPG = new ArrayList<Integer>();
    	selectOneMenuEA = new ArrayList<Integer>();
    	for (int i = wp.getMinW�rfel(); i <=wp.getMaxW�rfel(); i++) {
    		selectOneMenuWPG.add(i);
    	}
    	for (int i = ep.getMinimalGr��e(); i<=ep.getMaximalGr��e(); i++) {
    		selectOneMenuEA.add(i);
    	}
	}

	private void bereinigen() {
	w�rfelDrehbar = false;
	w�rfelGedreht = false;
	w�rfelNeuW�rfelbar = false;
	einenW�rfelNeuGew�rfelt = false;
	nichterfolgeNeuW�rfelbar = false;
	nichterfolgeNeuGew�rfelt = false;
	mitEdgeW�rfelbar = false;
	mitEdgeGew�rfelt = false;
    }
    
    public void berechnen() {
    	einW�rfelVonEinsAufZweiDrehbar = einW�rfelVonEinsAufZweiDrehbar();
    	einW�rfelVonVierAufF�nfDrehbar = einW�rfelVonVierAufF�nfDrehbar();
		w�rfelNeuW�rfelbar = einenNichterfolgNeuW�rfelbar();
		w�rfelDrehbar = w�rfelDrehbar();
		nichterfolgeNeuW�rfelbar = nichterfolgeNeuW�rfelbar();
		mitEdgeW�rfelbar = ep.isMitEdgeW�rfelbar();
    }
    public String getWerteAusgabe() {
    	return wp.wertAusgabe();
    }
    
    
    private boolean einenNichterfolgNeuW�rfelbar() {
	if (wp.isNachw�rfelbar() && ep.isW�rfelW�rfelbar() && !w�rfelGedreht && !nichterfolgeNeuGew�rfelt
		&& !mitEdgeGew�rfelt) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    private boolean w�rfelDrehbar() {
	if (wp.isDrehbar() && ep.isW�rfelW�rfelbar() && !einenW�rfelNeuGew�rfelt && !nichterfolgeNeuGew�rfelt
		&& !mitEdgeGew�rfelt) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    private boolean einW�rfelVonEinsAufZweiDrehbar() {
    	if (wp.isEinW�rfelAufZweiDrehbar() && ep.isW�rfelW�rfelbar() && !nichterfolgeNeuGew�rfelt
    		&& !mitEdgeGew�rfelt) {
    	    return true;
    	}
    	else {
    	    return false;
    	}
    }
    
    private boolean einW�rfelVonVierAufF�nfDrehbar() {
    	if (wp.isEinW�rfelAufF�nfDrehbar() && ep.isW�rfelW�rfelbar() && !nichterfolgeNeuGew�rfelt
    		&& !mitEdgeGew�rfelt) {
    	    return true;
    	}
    	else {
    	    return false;
    	}
    }
    
    private boolean nichterfolgeNeuW�rfelbar() {
		if(wp.isNachw�rfelbar() && ep.isNachw�rfelbar() && !einenW�rfelNeuGew�rfelt && !w�rfelGedreht 
			&& !nichterfolgeNeuGew�rfelt && !mitEdgeGew�rfelt) {
		    return true;
		}
		else {
		    return false;
		}
    }
    
    private boolean mitEdgeW�rfelbar() {
		if(ep.isMitEdgeW�rfelbar() && !einenW�rfelNeuGew�rfelt && !w�rfelGedreht && !nichterfolgeNeuGew�rfelt
			&& !mitEdgeGew�rfelt) {
		    return true;
		}
		else {
		    return false;
		}
    }
    
    public void setW�rfelPoolGr��e(int w�rfelPoolGr��e) {
	this.w�rfelPoolGr��e = w�rfelPoolGr��e;
	wp.setPoolGr��e(this.w�rfelPoolGr��e);
    }
    
    public void setEdgeAttribute(int edgeAttribute) {
	this.edgeAttribute = edgeAttribute;
	ep.setWert(edgeAttribute);
    }
    
    public int getEdgeAttribute() {
	return this.edgeAttribute;
    }
    
    public void setEdgePoolGr��e (int edgePool) {
	this.edgePoolGr��e = edgePool;
	ep.setWert(this.edgePoolGr��e);
    }
    
    public int getEdgePoolGr��e () {
	return this.edgePoolGr��e;
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

	public void einenNichterfolgNeuW�rfeln() {
		if (w�rfelNeuW�rfelbar) {
		    wp.einenW�rfelNeuW�rfeln();
		    ep.w�rfelNeuW�rfeln();
		    einenW�rfelNeuGew�rfelt = true;
		    edgePoolGr��e = ep.getWert();
		}
    }
    
    public void einenW�rfelVonEinsAufZweiDrehen() {
		if (einW�rfelVonEinsAufZweiDrehbar()) {
		    wp.einenW�rfelVonEinsAufZweiDrehen();
		    ep.w�rfelDrehen();
		    w�rfelGedreht = true;
		    edgePoolGr��e = ep.getWert();
		    berechnen();
		}
    }
    
    public void einenW�rfelVonVierAufF�nfDrehen() {
	if (w�rfelDrehbar) {
	    wp.einenW�rfelVonVierAufF�nfDrehen();
	    ep.w�rfelDrehen();
	    w�rfelGedreht = true;
	    edgePoolGr��e = ep.getWert();
	    berechnen();
	}
    }
    
    public void nichterfolgeNeuW�rfeln() {
		if (nichterfolgeNeuW�rfelbar) {
		    wp.nichterfolgeNachw�rfeln();
		    ep.nachW�rfeln();
		    nichterfolgeNeuGew�rfelt = true;
		    edgePoolGr��e = ep.getWert();
		    berechnen();
		}
    }
    
    public void mitEdgeW�rfeln() {
		if (mitEdgeW�rfelbar) {
		    wp.mitEdgeW�rfeln(this.edgeAttribute);
		    ep.mitEdgeW�rfeln();
		    mitEdgeGew�rfelt = true;
		    edgePoolGr��e = ep.getWert();
		    berechnen();
		}
    }
        
    public void w�rfeln() {
    	wp.setPoolGr��e(w�rfelPoolGr��e);
    	wp.w�rfeln();
    	bereinigen();
    	berechnen();
    }

    public int getW�rfelPoolGr��e() {
        return w�rfelPoolGr��e;
    }
    
    public boolean isBtnPlusEinEdgeEnabled() {
    	if (edgePoolGr��e < 7) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    public boolean isBtnEinenNichterfolgNeuW�rfelnEnabled() {
    	return einenNichterfolgNeuW�rfelbar();
    }
    
    public boolean isBtnEinenW�rfelVonEinsAufZweiDrehenEnabled() {
    	return einW�rfelVonEinsAufZweiDrehbar();
    }
    
    public boolean isBtnEinenW�rfelVonVierAufF�nfDrehenEnabled() {
    	return einW�rfelVonVierAufF�nfDrehbar();
    }
    
    public boolean isBtnNichterfolgeNeuW�rfelnEnabled() {
    	return nichterfolgeNeuW�rfelbar();
    }
    
    public boolean isBtnMitEdgeW�rfelnEnabled() {
    	return mitEdgeW�rfelbar();
    }

    public void edgePoolPlusEins(ActionEvent actionEvent) {
    	System.out.println("edgePoolPlusEins ged�ckt");
    	if (actionEvent.getComponent().getId().equals("EdgePoolPlusEins") && edgePoolGr��e < ep.getMaximalGr��e()) {
    		ep.EdgePoolUmEinsErh�hen();
    		edgePoolGr��e++;
    		berechnen();
    	}
    }
    
    public void onW�rfelPoolGr��eChange(final AbstractAjaxBehaviorEvent event) {
    	if (w�rfelPoolGr��e >= wp.getMinW�rfel() && w�rfelPoolGr��e <= wp.getMaxW�rfel()) {
    		wp.setPoolGr��e(w�rfelPoolGr��e);
    		this.edgePoolGr��e = w�rfelPoolGr��e;
    	}
    	else {
    		w�rfelPoolGr��e = 0;
    	}
    	berechnen();
    }
    
    public void onEdgeAttributeChange(SelectEvent event) {
    	int i = (Integer) event.getObject(); 
    	if (i >= ep.getMinimalGr��e() && i <= ep.getMaximalGr��e()) {
    		ep.setWert(i);
    		this.edgePoolGr��e = i;
    		
    	}
    	else {
    		edgeAttribute = 0;
    	}
    	berechnen();
    }
    
    public void btnW�rfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("W�rfeln")) {
    		w�rfeln();
    	}
    }
    
    public void btnMitEdgeW�rfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("MitEdgeW�rfeln")) {
    		mitEdgeW�rfeln();
    	}
    }
    
    public void btnEinenNichterfolgNeuW�rfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("EinenNichterfolgNeuW�rfeln")) {
    		einenNichterfolgNeuW�rfeln();
    	}
    }
    
    public void btnEinenW�rfelVonEinsAufZweiDrehen(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("EinenW�rfelVonEinsAufZweiDrehen")) {
    		einenW�rfelVonEinsAufZweiDrehen();
    	}
    }
    
    public void btnEinenW�rfelVonVierAufF�nfDrehen(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("EinenW�rfelVonVierAufF�nfDrehen")) {
    		einenW�rfelVonVierAufF�nfDrehen();
    	}
    }
    
    public void btnNichterfolgeNeuW�rfeln(ActionEvent actionEvent) {
    	if (actionEvent.getComponent().getId().equals("NichterfolgeNeuW�rfeln")) {
    		nichterfolgeNeuW�rfeln();
    	}
    }
}
