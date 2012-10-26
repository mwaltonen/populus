package edu.umn.ecology.populus.model.sspg;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

//Lars - when is this class ever used?!?!?
public class SSPGTable extends AbstractTableModel{
   /**
	 * 
	 */
	private static final long serialVersionUID = 3842707077834095589L;

int dimension=0;

   /*each element of the array refers to a column, each element in the vector
   refers to the row values*/
   Vector[] table;
   JTable jt;

   public SSPGTable() {
      table = new Vector[3];
      for(int i=0; i<3; i++)
         table[i] = new Vector(0);

      super.fireTableChanged(new TableModelEvent(this));
   }

   public void setTable(JTable t){
      jt = t;
   }

   void clearData(){
      table = new Vector[3];
      for(int i=0; i<3; i++)
         table[i] = new Vector(0);
      dimension = 0;
      super.fireTableChanged(new TableModelEvent(this));
   }

   public void addDimension(String title, double initPop){
      dimension++;

      Vector[] temp = new Vector[3+dimension];
      for(int i=0; i<dimension-1+3; i++)
         temp[i] = table[i];
      temp[dimension-1+3] = new Vector(dimension);
      table = temp;

      table[0].add(new Integer(dimension));
      table[1].add(title);
      table[2].add(new Double(initPop));

      for(int i=0; i<dimension-1;i++)
         table[3+dimension-1].add(new Double(0));
      for(int i=0; i<dimension; i++)
         table[3+i].add(new Double(0));

      this.fireTableStructureChanged();
   }

   public void removeDimension(){
      dimension--;

      Vector[] temp = new Vector[table.length - 1];
      for(int i=0; i<temp.length; i++){
         temp[i] = table[i];
         temp[i].removeElementAt(dimension);
      }
      table = temp;

      this.fireTableStructureChanged();
   }

   public String getColumnName(int column) {
      if(column>2) return ""+(column-2);
      switch (column) {
         case 0:
            return "Stage";
         case 1:
            return "Title";
         case 2:
            return "N0";
         default:
            return null;
      }
   }

   /**
    *  Returns Object.class by default
    *
    */
   public Class getColumnClass(int c) {
      return getValueAt(0, c).getClass();
   }

   public boolean isCellEditable(int rowIndex, int columnIndex) {
      if(columnIndex==0) return false;
      return true;
   }

   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      fireTableDataChanged();
      table[columnIndex].setElementAt(aValue,rowIndex);
   }

   public int getRowCount() {
      return dimension;
   }
   public int getColumnCount() {
      return dimension+3;
   }

   public int getDataDimension(){
      return dimension;
   }

   public Object getValueAt(int row, int column) {
      if(column == 1) return transformName((String)table[1].get(row),row+1);
      return table[column].get(row);
   }

   public String getRawRowName(int row){
      return (String)table[1].get(row);
   }

   String transformName(String s, int index){
      int i = s.indexOf("%n",0);
      while(i != -1){
         s = s.substring(0,i) + index + s.substring(i+2,s.length());
         i = s.indexOf("%n",0);
      }
      return s;
   }

//   public String getName(boolean wantMapped){
//      if(wantMapped) return transformName(name);
//      else return name;
//   }

   /**
    * the matrix returned by this method "ignores" the positions on the input screen's table
    * and gives only the important data for the graph.
    * @return
    */
   synchronized double[][] getMatrix() {
      double[][] data = new double[dimension][dimension];
      for(int i=0; i<data.length; i++)
         for(int j=0; j<data[i].length; j++)
            data[i][j] = ((Double)getValueAt(i,j+3)).doubleValue();
      return data;
   }

   synchronized double[] getPopulations(){
      double[] data = new double[dimension];
      for(int i=0; i<data.length; i++)
         data[i] = ((Double)getValueAt(i,2)).doubleValue();
      return data;
   }

   synchronized String[] getTitles(){
      String[] data = new String[dimension];
      for(int i=0; i<data.length; i++)
         data[i] = (String)getValueAt(i,1);
      return data;
   }

   synchronized void setData(double[][] mat, double[] pops, String[] titles){
      clearData();
      if(mat.length != mat[0].length) return;
      if(mat.length > dimension){
         for(int i=dimension; i<mat.length; i++)
            this.addDimension("Stage #"+(i+1),10);
      }
      for(int i=0; i<mat.length; i++)
         for(int j=0; j<mat.length; j++)
            this.setValueAt(new Double(mat[i][j]),i,j+3);

      Vector p = new Vector();
      for(int i=0; i<pops.length; i++)
         p.add(new Double(pops[i]));
      table[2] = p;

      p = new Vector();
      for(int i=0; i<titles.length; i++)
         p.add(titles[i]);
      table[1] = p;

      fireTableDataChanged();
   }
}


