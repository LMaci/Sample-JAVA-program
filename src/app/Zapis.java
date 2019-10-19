package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

/** 
* Program <code>Okno</code>
* Klasa <code>Zapis</code> definiujaca zapis do pliku
* @author Łukasz Maciejczyk
* @version 2.0	24/05/2017
*/

public class Zapis {
	
	/**
	 * Statyczna metoda klasy <code>Zapis</code>
	 * wywołująca okno do zapisu pliku.
	 */
	
	static void save(String dane){
		
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {		  
			PrintWriter printWriter;
			try {
				printWriter = new PrintWriter(new FileOutputStream(new File(fileChooser.getSelectedFile() + ".txt"), true));
				printWriter.println(dane);
				printWriter.close();
			
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		  
		}
		
	}

}

