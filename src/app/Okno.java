package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;
import org.freixas.jcalendar.JCalendarCombo;

import com.l2fprod.common.demo.TaskPaneMain;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

/**
 * Klasa <code>Okno</code>
 * zawierająca funkcję main() programu <code>Okno</code>
 * @author Łukasz Maciejczyk
 * @version 2.0 24/05/2017
 */

public class Okno extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel status;
	private JTable table;
	private JLabel[] lStatus = new JLabel[2];
	private JLabel[] label = new JLabel[3];
	private JTextField textField;
	private JTextArea textArea;
	private FloatTableModel floatModel;
	private JSpinner[] spinner = new JSpinner[2];
	private JTextArea wynik;
	private JComboBox<String> comboBox;
	private HelpWindow helpWindow;
	private AboutWindow aboutWindow;	
	private JCalendarCombo jccData;
	private static Totd totd;
	
	private static Logger capp = Logger.getLogger("logger");
	private static Logger fapp = Logger.getLogger("myLogger");
	
	private JToolBar tb;
	private JButton tbSuma,tbSrednia,tbMinMax,tbWypelnij,tbWyzeruj,tbOAutorze,
					tbZapisz, tbHelp, bWpisz,bWyzeruj,bZapisz,bOblicz;
	private JMenuBar menuBar;
	private JMenu menuPlik, menuNarzedzia, menuObliczenia, menuPomoc;
	private JMenuItem mOtworz, mZapisz, mWyjscie, mWypelnij, mWyzeruj, 
					mSuma, mSrednia, mMinMax, mOAutorze, mHelp;
	private Icon[] icon = {
			new ImageIcon(getClass().getResource("resources/save.png")),	//0
			new ImageIcon(getClass().getResource("resources/clear.png")),	//1
			new ImageIcon(getClass().getResource("resources/random.png")),	//2
			new ImageIcon(getClass().getResource("resources/sum.png")),	//3
			new ImageIcon(getClass().getResource("resources/av.png")),	//4
			new ImageIcon(getClass().getResource("resources/minmax.png")),	//5
			new ImageIcon(getClass().getResource("resources/author.png"))	//6
	};
	
	private int width, height;
	
	/**
	 * Konstruktor bezparametryczny klasy <code>Okno</code>
	 */
	
	public Okno(){		
		
		totd = new Totd();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = (int) (gd.getDisplayMode().getWidth()/2.5); // pobierz szerokosc
		if (width<640) width = 640;
		height = (int) (gd.getDisplayMode().getHeight()/2.5); // pobierz wysokosc
		if (height<450) height = 500;
		
		floatModel = new FloatTableModel();
		table = new JTable(floatModel);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();	
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		for(int i=0; i<table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		
		
		this.setTitle("Zadanie nr 1"); // nazwa okna
		this.setSize(width, height); // ustawienie rozmiarow
		this.setLocationRelativeTo(null); // pozycjonowanie
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // operacja przy zamknięciu
		
		this.addWindowListener	(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int koniec = JOptionPane.showConfirmDialog(null, 
						"Czy chcesz zamknąć program?","Koniec?",JOptionPane.YES_NO_OPTION);
				if (koniec == JOptionPane.YES_OPTION) {
					dispose();
					capp.info("Koniec pracy programu");
					System.exit(0);
				}
			}
		}); 
		
		initMenu();
		initToolBar();
		initSrodek();
	}
	
	
	
	//////////////////////////////////////
	
	/**
	 * Publiczna metoda klasy <code>Okno</code>
	 * tworząca środkową część okna aplikacji
	 */
	
	public void initSrodek(){
		// utworzenie instacji obiektu JCalendar 
		jccData = new JCalendarCombo(
		Calendar.getInstance(),
		Locale.getDefault(),
			JCalendarCombo.DISPLAY_DATE,
			false
		);
		jccData.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		
		//PRAWY PANEL
		JPanel panelR = new JPanel();
		panelR.setLayout(new BoxLayout(panelR,BoxLayout.Y_AXIS));
		textField = new JTextField();
		textField.setText("0");
		label[0] = new JLabel("Wprowadź liczbę");
		panelR.add(label[0]);
		panelR.add(textField);
		panelR.add(Box.createVerticalStrut(10));
		
		label[1] = new JLabel("Wybierz X");
		spinner[0] = new JSpinner();
		spinner[0].setModel(new SpinnerNumberModel(1,1,5,1));
		panelR.add(label[1]);
		panelR.add(spinner[0]);
		panelR.add(Box.createVerticalStrut(5));
		label[2] = new JLabel("Wybierz Y");
		spinner[1] = new JSpinner();
		spinner[1].setModel(new SpinnerNumberModel(1,1,5,1));
		panelR.add(label[2]);
		panelR.add(spinner[1]);
		panelR.add(Box.createVerticalStrut(10));
		comboBox = new JComboBox<String>();
		comboBox.addItem("SUMA");
		comboBox.addItem("ŚREDNIA");
		comboBox.addItem("MIN I MAX");
		panelR.add(comboBox);
		panelR.add(Box.createVerticalStrut(10));
		panelR.add(bWpisz);
		panelR.add(Box.createVerticalStrut(10));
		panelR.add(jccData);
			
		
		status = new JPanel();
		lStatus[0] = new JLabel("Ostatnie zdarzenie: ");
		lStatus[1] = new JLabel("");
		status.add(lStatus[0]);
		status.add(lStatus[1]);
		
		//DOLNY PANEL
		JPanel panelD = new JPanel();
		JPanel guzikiD = new JPanel();
		guzikiD.setLayout(new BorderLayout());
		panelD.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		panelD.add(bOblicz);
		panelD.add(bZapisz);
		panelD.add(bWyzeruj);
		panelD.add(Box.createVerticalStrut(10));
		label[2] = new JLabel("Wynik");
		panelD.add(label[2]);
		wynik = new JTextArea();
		wynik.setEditable(false);
		wynik.setEnabled(false);
		wynik.setBackground(Color.BLACK);		
		
		panelD.add(wynik);
		guzikiD.add(panelD,BorderLayout.NORTH);
		guzikiD.add(status,BorderLayout.SOUTH);
		
		
		//LEWY PANEL
		JScrollPane jScrollPane = new JScrollPane(table);
		JPanel panelL = new JPanel();
		panelL.setLayout(new BoxLayout(panelL,BoxLayout.Y_AXIS));
		panelL.add(jScrollPane);
		textArea = new JTextArea();
		textArea.setText("\nLiczba:\nData:\n");
		textArea.setEditable(false);
		panelL.add(textArea);
		
		//TASK PANE
		JTaskPane taskPane = new JTaskPane();
		JTaskPaneGroup tpNarzedzia = new JTaskPaneGroup();
		JTaskPaneGroup tpObliczenia = new JTaskPaneGroup();
		JTaskPaneGroup tpPomoc = new JTaskPaneGroup();
		
		tpNarzedzia.setTitle("Narzędzia");
		tpNarzedzia.setToolTipText("Wypełnianie i zerowanie");
		tpNarzedzia.add(makeAction("Losuj", "Wpisz losowe wartości", "/resources/random.png"));
		tpNarzedzia.add(makeAction("Wyzeruj", "Wyzeruj tabelę", "/resources/clear.png"));
		tpNarzedzia.add(makeAction("Zapisz", "Zapisz do pliku", "/resources/save.png"));

		tpObliczenia.setTitle("Obliczenia");
		tpObliczenia.setToolTipText("Sumowanie itp");
		tpObliczenia.add(makeAction("Sumuj","Suma wszystkich elementów","/resources/sum.png"));
		tpObliczenia.add(makeAction("Średnia","Wartość średnia wszystkich elementów","/resources/av.png"));
		tpObliczenia.add(makeAction("Min/Max","Wartość minimalna i maksymalna wszystkich elementów","/resources/minmax.png"));
		
		tpObliczenia.setToolTipText("Wypełnianie i zerowanie");
		tpPomoc.setTitle("Inne");
		tpPomoc.setToolTipText("Pomoc");
		tpPomoc.add(makeAction("O Autorze","Informacje o autorze","/resources/author.png"));
		tpPomoc.add(makeAction("Pomoc","Wyświetl pomoc",""));


		taskPane.add(tpNarzedzia);
		taskPane.add(tpObliczenia);
		taskPane.add(tpPomoc);
		
		
		
		// CONTENT PANE
		JPanel cp = (JPanel) getContentPane(); 
		cp.setLayout(new BorderLayout(20,10)); // ustawienie layoutu
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelL,panelR);
		jSplitPane.setDividerLocation(width-350);
		
		cp.setBorder(new EmptyBorder(3, 10, 3, 10));
		cp.add(tb,BorderLayout.NORTH);
		cp.add(jSplitPane,BorderLayout.CENTER);
		cp.add(taskPane, BorderLayout.WEST);
		cp.add(guzikiD, BorderLayout.SOUTH);
	}
	
	/**
	 * Publiczna metoda klasy <code>Okno</code>
	 * tworząca pasek menu.
	 */
	public void initMenu(){
		menuBar = new JMenuBar();
		menuPlik = new JMenu("Plik");
			mOtworz = new JMenuItem("Otwórz");
			mZapisz = new JMenuItem("Zapisz");
			mWyjscie = new JMenuItem("Wyjście");
			
		menuNarzedzia = new JMenu("Narzędzia");
			menuObliczenia = new JMenu("Obliczenia");
				mSuma = new JMenuItem("Suma");
				mSrednia = new JMenuItem("Średnia");
				mMinMax = new JMenuItem("Znajdź Min i Max");
			mWypelnij = new JMenuItem("Wypełnij losowo");
			mWyzeruj = new JMenuItem("Wyzeruj");
			
		menuPomoc = new JMenu("Pomoc");
			mHelp = new JMenuItem("Pomoc");
			mOAutorze = new JMenuItem("O autorze");


		setJMenuBar(menuBar); 	//DODAJ MENU BAR
		menuBar.add(menuPlik); // PLIK
			menuPlik.add(mOtworz);
				mOtworz.setEnabled(false);
			menuPlik.add(mZapisz);
				mZapisz.addActionListener(this);
			menuPlik.addSeparator(); // -----------
			menuPlik.add(mWyjscie);
				mWyjscie.addActionListener(this);
				mWyjscie.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		
		menuBar.add(menuNarzedzia); // NARZĘDZIA
			menuNarzedzia.add(menuObliczenia); // PODMENU OBLICZENIA
				menuObliczenia.add(mSuma);
				mSuma.addActionListener(this);
				menuObliczenia.add(mSrednia);
				mSrednia.addActionListener(this);
				menuObliczenia.add(mMinMax);
				mMinMax.addActionListener(this);
			menuNarzedzia.add(mWypelnij);
				mWypelnij.addActionListener(this);
			menuNarzedzia.add(mWyzeruj);
				mWyzeruj.addActionListener(this);
				mWyzeruj.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
	
		menuBar.add(menuPomoc); // POMOC 
			menuPomoc.add(mHelp);
				mHelp.addActionListener(this);
			menuPomoc.add(mOAutorze);
				mOAutorze.addActionListener(this);
	}
	
	/**
	 * Publiczna metoda klasy <code>Okno</code>
	 * tworząca pasek typu toolbar.
	 */
	public void initToolBar(){
		// TOOLBAR
		tbZapisz = new JButton(icon[0]); // ZAPISZ
		tbZapisz.addActionListener(this);
		tbWyzeruj = new JButton(icon[1]); //ZEROWANIE
		tbWyzeruj.addActionListener(this);
		tbWypelnij = new JButton(icon[2]); // RANDOM
		tbWypelnij.addActionListener(this);
		tbSuma = new JButton(icon[3]); // SUMA
		tbSuma.addActionListener(this);
		tbSrednia = new JButton(icon[4]); // SREDNIA
		tbSrednia.addActionListener(this);
		tbMinMax = new JButton(icon[5]); // MinMax
		tbMinMax.addActionListener(this);
		tbOAutorze = new JButton(icon[6]); // SREDNIA
		tbOAutorze.addActionListener(this);
		tbHelp = new JButton(" ? ");
		tbHelp.addActionListener(this);
		

		// TOOLBAR
		tb = new JToolBar();
		tb.setFloatable(false);
		tb.add(tbZapisz);
		tb.addSeparator();
		tb.add(tbWypelnij);
		tb.add(tbWyzeruj);
		tb.addSeparator();
		tb.add(tbSuma);
		tb.add(tbSrednia);
		tb.add(tbMinMax);
		tb.addSeparator();
		tb.add(tbHelp);
		tb.add(tbOAutorze);
		bOblicz = new JButton("Oblicz");
		bOblicz.addActionListener(this);
		bWpisz = new JButton("Wpisz");
		bWpisz.addActionListener(this);
		bZapisz = new JButton("Zapisz do pliku");
		bZapisz.addActionListener(this);
		bWyzeruj = new JButton("Wyzeruj");
		bWyzeruj.addActionListener(this);
	}
	

	/**
	 * Publiczna metoda z interfejsu <code>ActionListener</code>
	 * obslugujaca zdarzenie akcji <code>makeAction</code>
	 */
    Action makeAction(String title, String tooltiptext, String iconPath) {
        Action action = new AbstractAction(title) {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				Object z=e.getActionCommand();
				
				if (z=="Sumuj"){
					wynik.setText(""+floatModel.calculateSum());
					lStatus[1].setText("Obliczono sumę");
					capp.info("obliczono sumę");
				}
				else if (z=="Średnia"){
					wynik.setText(""+floatModel.calculateAverage());
					lStatus[1].setText("Obliczono średnią");
					capp.info("obliczono średnią");
				}
				else if (z=="Min/Max"){
					wynik.setText("Min = " + floatModel.znajdzMin() + " | Max = " + floatModel.znajdzMax());
					lStatus[1].setText("Znaleziono Min i Max");
					capp.info("obliczono min/max");
				}
				else if (z=="Losuj"){
					floatModel.setRandomTable();
					lStatus[1].setText("Wypełniono tabelę losowymi wartościami");
					capp.info("wylosowano wartości");
				}
				else if (z=="Wyzeruj"){
					floatModel.setZeroTable();
					wynik.setText("0");
					lStatus[1].setText("Tabela wyzerowana");
					capp.info("tabela wyzerowana");
				}
				else if (z=="O Autorze"){
				 	if(aboutWindow != null) aboutWindow.setVisible(true);
				 	else {
				 		aboutWindow = new AboutWindow();
				 		aboutWindow.setVisible(true);
					lStatus[1].setText("Wyświetlono informacje o autorze");
					capp.info("wyświetlono informację o autorze");
				 	}
				}
				else if (z=="Pomoc"){
				 	if(helpWindow != null) helpWindow.setVisible(true);
				 	else {
				 	 	helpWindow = new HelpWindow();
				 		helpWindow.setVisible(true); 
				 		capp.info("wyświetlono help");
				 	}
				}
				else if(z=="Zapisz") {
					Zapis.save(floatModel.getStringValuesTable());
					capp.info("tabela zapisana do pliku");
				}	
			}
        };
        action.putValue(Action.SMALL_ICON, new ImageIcon(TaskPaneMain.class
          .getResource(iconPath)));
        action.putValue(Action.SHORT_DESCRIPTION, tooltiptext);
        return action;
      }
	
	@Override
	/**
	 * Publiczna metoda z interfejsu <code>TaskPane</code>
	 * obslugujaca zdarzenie akcji 
	 */
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		Object z1 = e.getActionCommand();
		if (z==mWyjscie){
			int koniec = JOptionPane.showConfirmDialog(null, 
				"Czy chcesz zamknąć program?","Koniec?",JOptionPane.YES_NO_OPTION);
		if (koniec == JOptionPane.YES_OPTION) {
			dispose(); 
			capp.info("Koniec pracy programu");
			System.exit(0);
			}
		}
		else if (z==bWpisz){
			try{
				if((int)spinner[0].getValue()<1) spinner[0].setValue(1);
				if((int)spinner[0].getValue()>5) spinner[0].setValue(5);
				if((int)spinner[1].getValue()<1) spinner[1].setValue(1);
				if((int)spinner[1].getValue()>5) spinner[1].setValue(5);
				float s = Float.parseFloat(textField.getText()); 
				int x = (int)spinner[1].getValue();
				int y = (int)spinner[0].getValue();
				floatModel.setValue(s, x-1, y-1);
				lStatus[1].setText("Wprowadzono "+s+ " do X" +y+" Y"+x);
				capp.info("wprowadzono "+s+" do X= " +x+ ", Y= "+y);
								
				Calendar cal = jccData.getCalendar();
				String data = ""+cal.get(Calendar.YEAR)+"-";
				int miesiac = cal.get(Calendar.MONTH)+1;
				if(miesiac <= 9) data = data+"0"+String.valueOf(miesiac)+"-"; 
				else data = data+String.valueOf(miesiac)+"-";	
				int dzien = cal.get(Calendar.DAY_OF_MONTH);
				if(dzien <= 9) data = data+"0"+String.valueOf(dzien);
				else data = data+String.valueOf(dzien);
				
				textArea.setText("\nLiczba: " + s + "\nData: " + data + "\n");
				
			} catch(NumberFormatException e1)
			{
				JOptionPane.showMessageDialog(null, "Możesz wprowadzić tylko liczby!",
						"Błąd!", JOptionPane.ERROR_MESSAGE);
				fapp.warn("Wprowadzono błędną danę! -> "+textField.getText());
				capp.warn("Wprowadzono błędną danę!");
				textField.setText("0");
				lStatus[1].setText("Błędna dana");
			}
		}
		else if (z==bOblicz && comboBox.getSelectedItem()=="SUMA" || z==mSuma || z==tbSuma || z1=="Sumuj"){
			wynik.setText(""+floatModel.calculateSum());
			lStatus[1].setText("Obliczono sumę");
			capp.info("obliczono sumę");
		}
		else if (z==bOblicz && comboBox.getSelectedItem()=="ŚREDNIA" || z==mSrednia || z==tbSrednia || z1=="Średnia"){
			wynik.setText(""+floatModel.calculateAverage());
			lStatus[1].setText("Obliczono średnią");
			capp.info("obliczono średnią");
		}
		else if (z==bOblicz && comboBox.getSelectedItem()=="MIN I MAX" || z==mMinMax || z==tbMinMax || z1=="Min/Max"){
			wynik.setText("Min = " + floatModel.znajdzMin() + " | Max = " + floatModel.znajdzMax());
			lStatus[1].setText("Znaleziono Min i Max");
			capp.info("obliczono min/max");
		}
		else if (z==mWypelnij || z==tbWypelnij || z1=="Losuj"){
			floatModel.setRandomTable();
			lStatus[1].setText("Wypełniono tabelę losowymi wartościami");
			capp.info("wylosowano tabelę");
		}
		else if (z==mWyzeruj || z==tbWyzeruj || z==bWyzeruj || z1=="Wyzeruj"){
			floatModel.setZeroTable();
			wynik.setText("0");
			lStatus[1].setText("Tabela wyzerowana");
			capp.info("Zerowanie tabeli");
		}
		else if (z==mOAutorze || z==tbOAutorze || z1=="O Autorze"){
		 	if(aboutWindow != null) aboutWindow.setVisible(true);
		 	else {
		 		aboutWindow = new AboutWindow();
		 		aboutWindow.setVisible(true);
				capp.info("Wyświetlono okno o autorze");
			lStatus[1].setText("Wyświetlono informacje o autorze");
		 	}
		}
		else if (z==mHelp || z==tbHelp || z1=="Pomoc"){
		 	if(helpWindow != null) helpWindow.setVisible(true);
		 	else {
		 	 	helpWindow = new HelpWindow();
		 		helpWindow.setVisible(true); 
		 		capp.info("wyświetlono help");
		 	}
		}
		else if(z==mZapisz || z==bZapisz || z==tbZapisz) {
			Zapis.save(floatModel.getStringValuesTable());
			capp.info("zapisano do pliku");
		}
		
	}
	

	
	public static void main(String[] args) {
		capp.info("Start aplikacji");
		Okno asd = new Okno();
		asd.setVisible(true);
		totd.show();
	}

}
