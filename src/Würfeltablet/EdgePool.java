package W�rfeltablet;

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
	private final int minimalGr��e = 0;
	private final int maximalGr��e = 7;
    private int wert;
    private boolean w�rfelW�rfelbar;
    private boolean w�rfelDrehbar;
    private boolean nachw�rfelbar;
    private boolean mitEdgeW�rfelbar;
    
    
    @PostConstruct
    public void init (){
	setWert(0);
	berechnen();
    }

    private void berechnen() {
	this.w�rfelW�rfelbar = istW�rfelW�rfelbar();
	this.w�rfelDrehbar = istW�rfelDrehbar();
	this.nachw�rfelbar = istNachw�rfelbar();
	this.mitEdgeW�rfelbar = istMitEdgeW�rfelbar();
    }
    
    private boolean istW�rfelW�rfelbar() {
	if (this.wert >= 1) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istW�rfelDrehbar() {
	if (this.wert >=2 ) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istNachw�rfelbar() {
	if (this.wert >= 4) {
	    return true;
	}
	else {
	    return false;
	}
    }

    private boolean istMitEdgeW�rfelbar() {
	if (this.wert >= 4) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    public void setWert(int wert) {
	if (wert >=0 && wert <= maximalGr��e) {
	    this.wert = wert;
	    berechnen();
		}
	else {
	    this.wert = 0;
		}
    }

    
    public void EdgePoolUmEinsErh�hen() {
	if (this.wert < maximalGr��e) {
	    this.wert++;
	    berechnen();
	}
    }

    public void w�rfelNeuW�rfeln() {
    	if (istW�rfelW�rfelbar()) {
	    this.wert--;
	    berechnen();
		}
    }
    
    public void w�rfelDrehen() {
    	if (istW�rfelDrehbar()) {
    		this.wert -= 2;
		}
	berechnen();
    }
    
    public void nachW�rfeln() {
    	if (istNachw�rfelbar()) {
    		this.wert -= 4;
		}
		berechnen();
    }
    
    public void mitEdgeW�rfeln() {
    	if(istMitEdgeW�rfelbar()) {
    		this.wert -= 4;
		}
    	berechnen();
    }
    
    public int getMinimalGr��e() {
		return minimalGr��e;
	}

	public int getMaximalGr��e() {
        return maximalGr��e;
    }

    public int getWert() {
        return wert;
    }

    public boolean isW�rfelW�rfelbar() {
        return w�rfelW�rfelbar;
    }

    public boolean isW�rfelDrehbar() {
        return w�rfelDrehbar;
    }

    public boolean isNachw�rfelbar() {
        return nachw�rfelbar;
    }

    public boolean isMitEdgeW�rfelbar() {
        return mitEdgeW�rfelbar;
    }
    
}
