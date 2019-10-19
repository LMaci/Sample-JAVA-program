package app;

import java.util.Random;

import javax.swing.table.AbstractTableModel;

/** 
* Program <code>Okno</code>
* Klasa <code>FloatTableModel</code> definiujaca tabelę
* opis aplikacji
* @author Łukasz Maciejczyk
* @version 2.0	24/05/2017
*/

public class FloatTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final int countRowTable = 5;
	private final int countColumnTable = 5;
	private Float[][] data = new Float[countRowTable][countColumnTable];
	private String[] colName = {"1","2","3","4","5"};
	
	public FloatTableModel() {
		super();
		setZeroTable();
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * pobierająca liczbę kolumn
	 */
	public int getColumnCount() {
		return countColumnTable;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * pobierająca liczbę wierszy
	 */
	public int getRowCount() {
		return countColumnTable;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * pobierająca wartość wskazanej komórki
	 */
	public Object getValueAt(int row, int col) {
		Object object = (Object) data[row][col];
		return object;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracająca tabelę
	 */
	public Float[][] getFloatValuesTable() {
		return data;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * pobierająca wartości tabeli za pomocą string'a
	 */
	public String getStringValuesTable() {
		String str = "";
		for(int i=0; i<countRowTable; i++){
			for(int j=0; j<countColumnTable; j++) {
				str = str + data[i][j] +" ";
			} str = str+System.lineSeparator();}
		return str;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracającą nazwę kolumny
	 */
	public String getColumnName(int col) {
		return colName[col];
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracającą nazwę kolumn
	 */
	public String[] getColumnNames() { 
		return colName;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * ustawiająca wartość na wskazanym polu
	 */
	public void setValue(Float value, int row, int col) {
		data[row][col] = value;
		fireTableDataChanged();	
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * ustawiająca losową wartość dla każdej komórki
	 */
	public void setRandomTable() {
		Random random = new Random();
		for(int i=0; i<countRowTable; i++)
			for(int j=0; j<countColumnTable; j++) {
				// ograniczenie znaku liczby i zakresu do 10000
				data[i][j] = Math.abs(random.nextFloat()) % 10000;
			}
		fireTableDataChanged();
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zerująca tabelę
	 */
	public void setZeroTable() {
		for(int i=0; i<countRowTable; i++)
			for(int j=0; j<countColumnTable; j++) {
				data[i][j] = new Float(0);
			}
		fireTableDataChanged();
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracająca sumę wartości komórek w tabeli
	 */
	public Float calculateSum() {
		Float sum = new Float(0);
		for(int i=0; i<countRowTable; i++)
			for(int j=0; j<countColumnTable; j++) {
				sum = sum + data[i][j];
			}
		return sum;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracającą wartość średnią tabeli
	 */
	public Float calculateAverage() {
		Float avg = new Float(0.0);
		Float sum = calculateSum();
		if(sum > 0) avg = (sum.floatValue())/(countRowTable*countColumnTable);
		return avg;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracająca wartość minimalną tabeli
	 */
	public Float znajdzMin(){
		float min = Float.MAX_VALUE;
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				if(data[i][j]<min) min=data[i][j];
		return min;
	}
	/*
	 * Metoda klasy <code>FloatTableModel</code>
	 * zwracającą wartość maksymalną tabeli
	 */
	public Float znajdzMax(){
		float max = Float.MIN_VALUE;
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				if(data[i][j]>max) max=data[i][j];
		return max;
	}
}
