/**
 * 
 */
package com.gbmxdev.dungeoncrawl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class item_db {//item.db is stored in csv format
	public item_db() throws FileNotFoundException {
		;
	}
	public String items_db [][]= new String[255][255]; //initialize itemdb
	private int size; 
	//list items in database
	public void list() {
		int i=0; 
		for(i=0;i<this.size;++i){
			System.out.print( this.items_db[i][0]);
			System.out.print( this.items_db[i][1]);
			System.out.println( this.items_db[i][2]); 
		}
	}//load items from item.db file 
    File file = new File("item.db");
    public void load () throws IOException{
    	System.out.println("Loading the database.");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String st;
    	int i=0;//cycle through each line of file
    	while ((st = br.readLine()) != null) { 
        	String[] parts = st.split(","); 
        	this.items_db[i][0]=parts[0]; 
        	this.items_db[i][1]=parts[1]; 
        	this.items_db[i][2]=parts[2]; 
        	i++;//split lines by comma and store in items_db
    	}
    	this.setSize(i); //save db size
    }//setters/getters
	public int getSize() {
		return size;
	} 
	public void setSize(int size) {
		this.size = size;
	}
}
