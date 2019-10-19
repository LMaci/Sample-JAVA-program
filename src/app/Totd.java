package app;

import javax.swing.JFrame;

import com.l2fprod.common.swing.JTipOfTheDay;
import com.l2fprod.common.swing.tips.DefaultTip;
import com.l2fprod.common.swing.tips.DefaultTipModel;

/** 
* Program <code>Okno</code>
* Klasa <code>Totd</code> definiujaca okno Tip of the day
* @author Łukasz Maciejczyk
* @version 2.0	24/05/2017
*/

public class Totd {
	
	JTipOfTheDay porady;
	DefaultTipModel spis_porad;
	
	/**
	 * Konstruktor bezparametrowy klasy <code>Totd</code>
	 */
	
	public Totd(){
		
		spis_porad = new DefaultTipModel();
		spis_porad.add(new DefaultTip("Tip nr 1","Super porada numer 1."));
		spis_porad.add(new DefaultTip("Tip nr 2","Inna porada numer 2."));

		porady = new JTipOfTheDay(spis_porad);
		
	}
	
	/**
	 * Publiczna metoda klasy <code>Totd</code>
	 * wywołująca okno Tip of the day
	 */
	
	public void show(){
		try{
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		porady.showDialog(new JFrame("Tip"));
	}
	

}
