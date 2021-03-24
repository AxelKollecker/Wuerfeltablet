package W�rfeltablet;

import java.io.Serializable;
import java.util.Random;

public class W�rfel implements Serializable{
	private static final long 	serialVersionUID = 1L;
	private final int minWert = 1;
	private final int maxWert = 6;
    private int wert;
    private boolean aufZweiDrehbar;
    private boolean aufF�nfDrehbar;
    private boolean erfolg;
    private boolean nichterfolg;
    private boolean explodierenM�glich;
    private boolean glitchM�glich;
    private boolean mitEdgeGew�rfelt;
    
    
    public W�rfel() {
	super();
    }
    
    public void drehen() {
	if (this.istAufZweiDrehbar() || this.istAufF�nfDrehbar()) {
	    this.wert++;
	    berechnen();
	}
    }
    
    public void werfen() {
	this.wert = (int)(Math.random()*(maxWert - minWert +1)+ minWert);
	berechnen();
    }
    
    private void berechnen() {
	this.aufZweiDrehbar = istAufZweiDrehbar();
	this.aufF�nfDrehbar = istAufZweiDrehbar();
	this.erfolg = istErfolg();
	this.nichterfolg = istNichterfolg();
	this.explodierenM�glich = istExplodierenM�glich();
	this.glitchM�glich = istGlitchM�glich();
    }

    private boolean istAufZweiDrehbar() {
	if (this.wert == 1) {
	    return true;
	}
	else {
	    return false;
	}   
    }

    private boolean istAufF�nfDrehbar() {
	if (this.wert == 4) {
	    return true;
	}
	else {
	    return false;
	}   
    }
    
    private boolean istErfolg() {
	if (this.wert == 5|| this.wert == 6) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istNichterfolg() {
	if (this.wert <5) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istExplodierenM�glich() {
	if (this.wert == 6) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istGlitchM�glich() {
	if (this.wert == 1) {
	    return true;
	}
	else {
	    return false;
	}
    }

    public boolean isMitEdgeGew�rfelt() {
        return mitEdgeGew�rfelt;
    }

    public void setMitEdgeGew�rfelt(boolean mitEdgeGew�rfelt) {
        this.mitEdgeGew�rfelt = mitEdgeGew�rfelt;
    }

    public int getWert() {
        return wert;
    }


    public boolean isAufZweiDrehbar() {
        return aufZweiDrehbar;
    }

    public boolean isAufF�nfDrehbar() {
        return aufF�nfDrehbar;
    }

    public boolean isErfolg() {
        return erfolg;
    }

    public boolean isNichterfolg() {
        return nichterfolg;
    }

    public boolean isExplodierenM�glich() {
        return explodierenM�glich;
    }

    public boolean isGlitchM�glich() {
        return glitchM�glich;
    }

    
}
