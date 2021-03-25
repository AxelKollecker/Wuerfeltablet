package W�rfeltablet;

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
public class W�rfelPool implements Serializable{
    private final int maxW�rfel = 50;
    private final int minW�rfel = 1;
    private List <W�rfel> w�rfelPool;
    private int anzahlW�rfel;
    private int anzahlErfolge;
    private int anzahlEinsen;
    private boolean glitch;
    private boolean kritischerGlitch;
    private boolean einW�rfelAufZweiDrehbar;
    private boolean einW�rfelAufF�nfDrehbar;
    private boolean nachw�rfelbar;
    private boolean nichterfolgeNeugew�rfelt;
    
    @PostConstruct
    public void init (){
    	w�rfelPool = new ArrayList<W�rfel>();
    	bereinigen();
    }
    
    private void bereinigen() {
    	anzahlW�rfel = w�rfelPool.size();
    	anzahlErfolge = 0;
    	anzahlEinsen = 0;
    	glitch = false;
    	kritischerGlitch = false;
    	einW�rfelAufZweiDrehbar = false;
    	einW�rfelAufF�nfDrehbar = false;
    	nachw�rfelbar = false;
    	nichterfolgeNeugew�rfelt = false;
    }
    
    public void setPoolGr��e(int gr��e) {
	if (minW�rfel <= gr��e && gr��e <= maxW�rfel) {
	    bereinigen();
	    erzeugeNeuenPool(gr��e);
		}
    }
    
    public int getPoolGr��e() {
    	return w�rfelPool.size();
    }
    
    private void erzeugeNeuenPool (int gr��e) {
    	w�rfelPool = new ArrayList<W�rfel>();
    	for (int i = 0; i < gr��e; i++) {
	    	w�rfelPool.add(new W�rfel());
	    	}
    	anzahlW�rfel = w�rfelPool.size();
    }
    
    public String wertAusgabe() {
    	List<String> sl = new ArrayList<String>();    	
    	for (W�rfel w : w�rfelPool) {
    		sl.add(((Integer)w.getWert()).toString());
    	}
    	List < String > sortedList = sl.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    	return sortedList.toString();
    }
    
    public void berechnen() {
	this.anzahlW�rfel = w�rfelPool.size();
	this.anzahlErfolge = anzahlErfolgeBerechnen();
	this.anzahlEinsen = anzahlEinsenBerechnen();
	this.glitch = istGlitch();
	this.kritischerGlitch = istKritischerGlitch();
	this.einW�rfelAufZweiDrehbar = istEinW�rfelAufZweiDrehbar();
	this.einW�rfelAufF�nfDrehbar = istEinW�rfelAufF�nfDrehbar1();
	this.nachw�rfelbar = istNachw�rfelbar();
    }

    private int anzahlErfolgeBerechnen() {
	int ergebnis = 0;
	for (W�rfel w : w�rfelPool) {
	    if (w.isErfolg()) {
		ergebnis++;
	    }
	}
	return ergebnis;
    }

    private int anzahlEinsenBerechnen() {
	int einsen = 0;
	for (W�rfel w : w�rfelPool) {
	    if (w.isGlitchM�glich() && ! w.isMitEdgeGew�rfelt()) {
		einsen++;
	    }
	}
	return einsen;
    }
    
    private boolean istGlitch() {
	if (anzahlEinsen > (w�rfelPool.size() / 2) ) {
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

    private boolean istEinW�rfelAufZweiDrehbar() {
	boolean ergebnis = false;
	for (W�rfel w : w�rfelPool) {
	    if (w.isAufZweiDrehbar()) {
		ergebnis = true;
		break;
	    }
	}
	return ergebnis;
    }
    
    private boolean istEinW�rfelAufF�nfDrehbar1() {
	boolean ergebnis = false;
	for (W�rfel w : w�rfelPool) {
	    if (w.isAufF�nfDrehbar()) {
		ergebnis = true;
		break;
	    }
	}
	return ergebnis;
    }


    private boolean istNachw�rfelbar() {
	if (anzahlErfolge < anzahlW�rfel) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    public void w�rfeln() {
    	int a = anzahlW�rfel;
    	bereinigen();
    	erzeugeNeuenPool(a);
    	for (W�rfel w : w�rfelPool) {
    		w.werfen();
    	}
    	berechnen();
    }
    
    public void einenW�rfelNeuW�rfeln() {
    	for (W�rfel w : w�rfelPool) {
    		if(w.isNichterfolg()) {
    			w.werfen();
			break;
    		}
    	}
    	berechnen();
    }
    
    public void nichterfolgeNachw�rfeln() {
    	for (W�rfel w : w�rfelPool) {
    		if(w.isNichterfolg()) {
    			w.werfen();
    		}
    	}
    	berechnen();
    }
    
    public void mitEdgeW�rfeln(int edgeAttribute) {
    	int a = anzahlW�rfel + edgeAttribute;
    	bereinigen();
    	erzeugeNeuenPool(a);
		int i = 0;
		while (i < a) {
		    w�rfelPool.get(i).werfen();
		    if (w�rfelPool.get(i).isExplodierenM�glich()) {
		    	W�rfel w = new W�rfel();
		    	w.setMitEdgeGew�rfelt(true);
		    	w�rfelPool.add(w);
		    	a++;
		    	}
		    i++;
		}	
		berechnen();
    }
    
    public void einenW�rfelVonEinsAufZweiDrehen() {
		for (W�rfel w : w�rfelPool) {
		    if(w.isAufZweiDrehbar()) {
		    	w.drehen();
				break;
		    }
		}
		berechnen();
    }
    
    public void einenW�rfelVonVierAufF�nfDrehen() {
	for (W�rfel w : w�rfelPool) {
	    if(w.isAufF�nfDrehbar()) {
	    	w.drehen();
	    	break;
	    	}
		}
		berechnen();
    }

    public int getMaxW�rfel() {
        return maxW�rfel;
    }

    public int getMinW�rfel() {
        return minW�rfel;
    }

    public boolean isNachw�rfelbar() {
        return nachw�rfelbar;
    }
    
    public boolean isDrehbar() {
	if (einW�rfelAufZweiDrehbar || einW�rfelAufF�nfDrehbar) {
	    return true;
	}
	else {
	    return false;
		}
    }
    
    public boolean isEinW�rfelAufZweiDrehbar() {
    	return einW�rfelAufZweiDrehbar;
    }
    
    public boolean isEinW�rfelAufF�nfDrehbar() {
    	return einW�rfelAufF�nfDrehbar;
    }

	public int getAnzahlW�rfel() {
		return anzahlW�rfel;
	}

	public int getAnzahlErfolge() {
		return anzahlErfolge;
	}

	public int getAnzahlEinsen() {
		return anzahlEinsen;
	}
    
    
}
