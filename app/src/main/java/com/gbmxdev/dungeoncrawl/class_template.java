package com.gbmxdev.dungeoncrawl;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner; 

public class class_template implements template { // character classes
	public String clas;
	private Random rand = new Random(); 
	//randomly generate class stats
	private int Dexterity=rand.nextInt(20);
	private int Stamina=rand.nextInt(20);
	private int Wisdom=rand.nextInt(20);
	private int Strength=rand.nextInt(20);
	private int Intelligence=rand.nextInt(20);
	private int Agility=rand.nextInt(20);
	public int hp=100+rand.nextInt(20);
	public int mana=100+rand.nextInt(20);
	//apply predefined class to generate states if desired
	public int apply_class(String class_name) throws IOException {
		@SuppressWarnings("resource")
		Scanner sc= new Scanner(System.in); 
		System.out.print("Enter a class: "); 
		this.clas = sc.nextLine(); //get class name
		if (this.clas.equals("") ){//use the default class if nothing is selected
			this.clas=class_name;
			System.out.println("Using randomly genorated values for your class.");
		} 
		switch(this.clas) {
		case "mage" ://start switch statement of predefined classes
			System.out.println("You have selected mage.");
			this.Dexterity=10;
			this.Stamina=10;
			this.Wisdom=10;
			this.Strength=10;
			this.Intelligence=18;
			this.Agility=16; 
			break;
		case "fighter" :
			System.out.println("You have selected fighter.");
			this.Dexterity=10;
			this.Stamina=16;
			this.Wisdom=10;
			this.Strength=18;
			this.Intelligence=10;
			this.Agility=10; 
			break;
		case "healer" :
			System.out.println("You have selected healer.");
			this.Dexterity=10;
			this.Stamina=16;
			this.Wisdom=18;
			this.Strength=10;
			this.Intelligence=10;
			this.Agility=10; 
			break;
		case "rouge" :
			System.out.println("You have selected rouge.");
			this.Dexterity=18;
			this.Stamina=10;
			this.Wisdom=10;
			this.Strength=10;
			this.Intelligence=10;
			this.Agility=16; 
			break;
		default:
			   System.out.println("You have selected " + this.clas + ".");
		}/*closing this closes std in and causes problems
		sc.close();*/
		return 0;
	}
	//setters/getters
	public int getDexterity() {
		return Dexterity;
	} 
	public void setDexterity(int dexterity) {
		Dexterity = dexterity;
	} 
	public int getStamina() {
		return Stamina;
	} 
	public void setStamina(int stamina) {
		Stamina = stamina;
	} 
	public int getWisdom() {
		return Wisdom;
	} 
	public void setWisdom(int wisdom) {
		Wisdom = wisdom;
	} 
	public int getStrength() {
		return Strength;
	} 
	public void setStrength(int strength) {
		Strength = strength;
	} 
	public int getIntelligence() {
		return Intelligence;
	} 
	public void setIntelligence(int intelligence) {
		Intelligence = intelligence;
	} 
	public int getAgility() {
		return Agility;
	} 
	public void setAgility(int agility) {
		Agility = agility;
	} 
	public int getHp() {
		return hp;
	} 
	public void setHp(int hp) {
		this.hp = hp;
	} 
	public int getMana() {
		return mana;
	} 
	public void setMana(int mana) {
		this.mana = mana;
	} 
	public String getClas() {
		return clas;
	} 
	public void setClas(String clas) {
		this.clas = clas;
	}
}

interface template { //generic class template of getters/setters
	public int getDexterity() ;
	public void setDexterity(int dexterity) ;
	public int getStamina() ;
	public void setStamina(int stamina);
	public int getWisdom() ;
	public void setWisdom(int wisdom);
	public int getStrength() ;
	public void setStrength(int strength) ;
	public int getIntelligence() ;
	public void setIntelligence(int intelligence) ;
	public int getAgility();
	public void setAgility(int agility);
	public int getHp() ;
	public void setHp(int hp) ;
	public int getMana() ;
	public void setMana(int mana) ;
	public String getClas() ;
	public void setClas(String clas) ;
}

class player extends class_template {//a player is a type of class
	public String name;
	public String inventory[][] = new String[255][255];
	private int inv_size = 0; 
	//player damage setter
	public int damage (int dmg) {
		 this.hp = this.hp - dmg;
		 return 0;
	}
	//setters/getters
	public String getName() {
		return name;
	}
	public void setName(String input) {
		this.name = input;
	}

	public int getInv_size() {
		return inv_size;
	}
	public void setInv_size(int inv_size) {
		this.inv_size = inv_size;
	}
}
class npc extends class_template {
	//a player is a type of class
	private String name;
	 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public  void main(String argsp[]) throws IOException {
		 Random rand = new Random();
		 int clas=rand.nextInt(3);
		switch (clas) {
			case 0 ://start switch statement of predefined classes
				this.apply_class("mage");
				break;
			case 1 :
				this.apply_class("fighter");
				break;
			case 2 :
				this.apply_class("healer");
				break;
			case 3 :
				this.apply_class("rouge");
				break;
			default :
				this.apply_class("");
				break;
		}
	 } 
}
