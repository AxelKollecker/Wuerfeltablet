package Würfeltablet;

import java.io.Serializable;
import java.util.Random;

public class Würfel implements Serializable{
	private static final long 	serialVersionUID = 1L;
	private final int minWert = 1;
	private final int maxWert = 6;
    private int wert;
    private boolean aufZweiDrehbar;
    private boolean aufFünfDrehbar;
    private boolean erfolg;
    private boolean nichterfolg;
    private boolean explodierenMöglich;
    private boolean glitchMöglich;
    private boolean mitEdgeGewürfelt;
    
    
    public Würfel() {
	super();
    }
    
    public void drehen() {
	if (this.istAufZweiDrehbar() || this.istAufFünfDrehbar()) {
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
	this.aufFünfDrehbar = istAufZweiDrehbar();
	this.erfolg = istErfolg();
	this.nichterfolg = istNichterfolg();
	this.explodierenMöglich = istExplodierenMöglich();
	this.glitchMöglich = istGlitchMöglich();
    }

    private boolean istAufZweiDrehbar() {
	if (this.wert == 1) {
	    return true;
	}
	else {
	    return false;
	}   
    }

    private boolean istAufFünfDrehbar() {
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

    private boolean istExplodierenMöglich() {
	if (this.wert == 6) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istGlitchMöglich() {
	if (this.wert == 1) {
	    return true;
	}
	else {
	    return false;
	}
    }

    public boolean isMitEdgeGewürfelt() {
        return mitEdgeGewürfelt;
    }

    public void setMitEdgeGewürfelt(boolean mitEdgeGewürfelt) {
        this.mitEdgeGewürfelt = mitEdgeGewürfelt;
    }

    public int getWert() {
        return wert;
    }


    public boolean isAufZweiDrehbar() {
        return aufZweiDrehbar;
    }

    public boolean isAufFünfDrehbar() {
        return aufFünfDrehbar;
    }

    public boolean isErfolg() {
        return erfolg;
    }

    public boolean isNichterfolg() {
        return nichterfolg;
    }

    public boolean isExplodierenMöglich() {
        return explodierenMöglich;
    }

    public boolean isGlitchMöglich() {
        return glitchMöglich;
    }

    
}
