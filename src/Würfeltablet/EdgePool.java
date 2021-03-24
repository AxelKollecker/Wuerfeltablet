package Würfeltablet;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@Stateful
@SessionScoped
public class EdgePool implements Serializable{
	private static final long 	serialVersionUID = 1L;
	private final int minimalGröße = 0;
	private final int maximalGröße = 7;
    private int wert;
    private boolean würfelWürfelbar;
    private boolean würfelDrehbar;
    private boolean nachwürfelbar;
    private boolean mitEdgeWürfelbar;
    
    
    @PostConstruct
    public void init (){
	setWert(0);
	berechnen();
    }

    private void berechnen() {
	this.würfelWürfelbar = istWürfelWürfelbar();
	this.würfelDrehbar = istWürfelDrehbar();
	this.nachwürfelbar = istNachwürfelbar();
	this.mitEdgeWürfelbar = istMitEdgeWürfelbar();
    }
    
    private boolean istWürfelWürfelbar() {
	if (this.wert >= 1) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istWürfelDrehbar() {
	if (this.wert >=2 ) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istNachwürfelbar() {
	if (this.wert >= 4) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istMitEdgeWürfelbar() {
	if (this.wert >= 4) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    public void setWert(int wert) {
	if (wert >=0 && wert <= maximalGröße) {
	    this.wert = wert;
	    berechnen();
		}
	else {
	    this.wert = 0;
		}
    }

    
    public void EdgePoolUmEinsErhöhen() {
	if (this.wert < maximalGröße) {
	    this.wert++;
	    berechnen();
	}
    }

    public void würfelNeuWürfeln() {
    	if (istWürfelWürfelbar()) {
	    this.wert--;
	    berechnen();
		}
    }
    
    public void würfelDrehen() {
    	if (istWürfelDrehbar()) {
    		this.wert -= 2;
		}
	berechnen();
    }
    
    public void nachWürfeln() {
    	if (istNachwürfelbar()) {
    		this.wert -= 4;
		}
		berechnen();
    }
    
    public void mitEdgeWürfeln() {
    	if(istMitEdgeWürfelbar()) {
    		this.wert -= 4;
		}
    	berechnen();
    }
    
    public int getMinimalGröße() {
		return minimalGröße;
	}

	public int getMaximalGröße() {
        return maximalGröße;
    }

    public int getWert() {
        return wert;
    }

    public boolean isWürfelWürfelbar() {
        return würfelWürfelbar;
    }

    public boolean isWürfelDrehbar() {
        return würfelDrehbar;
    }

    public boolean isNachwürfelbar() {
        return nachwürfelbar;
    }

    public boolean isMitEdgeWürfelbar() {
        return mitEdgeWürfelbar;
    }
    
}
