package Würfeltablet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
@Stateful
public class WürfelPool implements Serializable{
    private final int maxWürfel = 50;
    private final int minWürfel = 1;
    private List <Würfel> würfelPool;
    private int anzahlWürfel;
    private int anzahlErfolge;
    private int anzahlEinsen;
    private boolean glitch;
    private boolean kritischerGlitch;
    private boolean einWürfelAufZweiDrehbar;
    private boolean einWürfelAufFünfDrehbar;
    private boolean nachwürfelbar;
    private boolean nichterfolgeNeugewürfelt;
    
    @PostConstruct
    public void init (){
    	würfelPool = new ArrayList<Würfel>();
    	bereinigen();
    }
    
    private void bereinigen() {
    	anzahlWürfel = würfelPool.size();
    	anzahlErfolge = 0;
    	anzahlEinsen = 0;
    	glitch = false;
    	kritischerGlitch = false;
    	einWürfelAufZweiDrehbar = false;
    	einWürfelAufFünfDrehbar = false;
    	nachwürfelbar = false;
    	nichterfolgeNeugewürfelt = false;
    }
    
    public void setPoolGröße(int größe) {
	if (minWürfel <= größe && größe <= maxWürfel) {
	    bereinigen();
	    erzeugeNeuenPool(größe);
		}
    }
    
    public int getPoolGröße() {
    	return würfelPool.size();
    }
    
    private void erzeugeNeuenPool (int größe) {
    	würfelPool = new ArrayList<Würfel>();
    	for (int i = 0; i < größe; i++) {
	    	würfelPool.add(new Würfel());
	    	}
    	anzahlWürfel = würfelPool.size();
    }
    
    public String wertAusgabe() {
    	List<String> sl = new ArrayList<String>();    	
    	for (Würfel w : würfelPool) {
    		sl.add(((Integer)w.getWert()).toString());
    	}
    	List < String > sortedList = sl.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    	return sortedList.toString();
    }
    
    public void berechnen() {
	this.anzahlWürfel = würfelPool.size();
	this.anzahlErfolge = anzahlErfolgeBerechnen();
	this.anzahlEinsen = anzahlEinsenBerechnen();
	this.glitch = istGlitch();
	this.kritischerGlitch = istKritischerGlitch();
	this.einWürfelAufZweiDrehbar = istEinWürfelAufZweiDrehbar();
	this.einWürfelAufFünfDrehbar = istEinWürfelAufFünfDrehbar1();
	this.nachwürfelbar = istNachwürfelbar();
    }

    private int anzahlErfolgeBerechnen() {
	int ergebnis = 0;
	for (Würfel w : würfelPool) {
	    if (w.isErfolg()) {
		ergebnis++;
	    }
	}
	return ergebnis;
    }

    private int anzahlEinsenBerechnen() {
	int einsen = 0;
	for (Würfel w : würfelPool) {
	    if (w.isGlitchMöglich() && ! w.isMitEdgeGewürfelt()) {
		einsen++;
	    }
	}
	return einsen;
    }
    
    private boolean istGlitch() {
	if (anzahlEinsen > (würfelPool.size() / 2) ) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istKritischerGlitch() {
	if (anzahlErfolge == 0 && istGlitch()) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istEinWürfelAufZweiDrehbar() {
	boolean ergebnis = false;
	for (Würfel w : würfelPool) {
	    if (w.isAufZweiDrehbar()) {
		ergebnis = true;
		break;
	    }
	}
	return ergebnis;
    }
    
    private boolean istEinWürfelAufFünfDrehbar1() {
	boolean ergebnis = false;
	for (Würfel w : würfelPool) {
	    if (w.isAufFünfDrehbar()) {
		ergebnis = true;
		break;
	    }
	}
	return ergebnis;
    }


    private boolean istNachwürfelbar() {
	if (anzahlErfolge < anzahlWürfel) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    public void würfeln() {
    	int a = anzahlWürfel;
    	bereinigen();
    	erzeugeNeuenPool(a);
    	for (Würfel w : würfelPool) {
    		w.werfen();
    	}
    	berechnen();
    }
    
    public void einenWürfelNeuWürfeln() {
    	for (Würfel w : würfelPool) {
    		if(w.isNichterfolg()) {
    			w.werfen();
			break;
    		}
    	}
    	berechnen();
    }
    
    public void nichterfolgeNachwürfeln() {
    	for (Würfel w : würfelPool) {
    		if(w.isNichterfolg()) {
    			w.werfen();
    		}
    	}
    	berechnen();
    }
    
    public void mitEdgeWürfeln(int edgeAttribute) {
    	int a = anzahlWürfel + edgeAttribute;
    	bereinigen();
    	erzeugeNeuenPool(a);
		int i = 0;
		while (i < a) {
		    würfelPool.get(i).werfen();
		    if (würfelPool.get(i).isExplodierenMöglich()) {
		    	Würfel w = new Würfel();
		    	w.setMitEdgeGewürfelt(true);
		    	würfelPool.add(w);
		    	a++;
		    	}
		    i++;
		}	
		berechnen();
    }
    
    public void einenWürfelVonEinsAufZweiDrehen() {
		for (Würfel w : würfelPool) {
		    if(w.isAufZweiDrehbar()) {
		    	w.drehen();
				break;
		    }
		}
		berechnen();
    }
    
    public void einenWürfelVonVierAufFünfDrehen() {
	for (Würfel w : würfelPool) {
	    if(w.isAufFünfDrehbar()) {
	    	w.drehen();
	    	break;
	    	}
		}
		berechnen();
    }

    public int getMaxWürfel() {
        return maxWürfel;
    }

    public int getMinWürfel() {
        return minWürfel;
    }

    public boolean isNachwürfelbar() {
        return nachwürfelbar;
    }
    
    public boolean isDrehbar() {
	if (einWürfelAufZweiDrehbar || einWürfelAufFünfDrehbar) {
	    return true;
	}
	else {
	    return false;
		}
    }
    
    public boolean isEinWürfelAufZweiDrehbar() {
    	return einWürfelAufZweiDrehbar;
    }
    
    public boolean isEinWürfelAufFünfDrehbar() {
    	return einWürfelAufFünfDrehbar;
    }

	public int getAnzahlWürfel() {
		return anzahlWürfel;
	}

	public int getAnzahlErfolge() {
		return anzahlErfolge;
	}

	public int getAnzahlEinsen() {
		return anzahlEinsen;
	}
    
    
}
