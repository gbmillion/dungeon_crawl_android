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

    public void load(   ) throws IOException{

		System.out.println("Loading the database.");

		//got rid of items.db and storing it here in a String
		String items;
		items = "potion,Dexterity,1,\n" +
                "potion,Stamina,1,\n" +
                "potion,Wisdom,1,\n" +
                "potion,Strength,1,\n" +
                "potion,Intellience,1,\n" +
                "potion,Agility,1,\n" +
                "potion,hp,1,\n" +
                "potion,mana,1,\n" +
				";\n";
		String [] lines;
		String st;
		int e=0; //cycle through each line of file
		lines = items.split("\n");
		while(lines[e]!=null){
			if(';'== lines[e].charAt(0)) break;
			String parts[] = lines[e].split(",");
			this.items_db[e][0]=parts[0];
			this.items_db[e][1]=parts[1];
			this.items_db[e][2]=parts[2];
			e++;//split lines by comma and store in items
		}
		e=0;
    	this.setSize(e); //save db size
    }//setters/getters
	public int getSize() {
		return size;
	} 
	public void setSize(int size) {
		this.size = size;
	}
}
