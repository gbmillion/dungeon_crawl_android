package com.gbmxdev.dungeoncrawl;
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.util.Random;
import java.util.Scanner;
/**
 * @author george million
 * Features:
 * 	dynamically generated default class (with personal class name), predetermined class types, traps, usable items, loot, combat, 
 * 	boss fights, dynamically generated map, multiple statistics per class
 * todo: 
 * 
 * bugs:
 * 
 */

public class game {//main class
	public game() throws FileNotFoundException {//catch exceptions
		System.out.println("Error FileNotFoundException.");
	}
	private static void step(player toon, item_db itemdb ){//take a step
		Random rand = new Random(); 
		if (rand.nextInt(100) < 30) trap(toon);//30% to hit trap
		else if (rand.nextInt(100) > 90) item( toon,itemdb);//10% chance to get item
	}
	private static void trap(player toon ){//apply trap damage
		Random rand = new Random();
		toon.setHp(toon.getHp() - rand.nextInt(10)); 
		System.out.println("You have stepped on a trap, your hp is now " + toon.getHp()  );	
	}
	private static void item( player toon, item_db itemdb ){ //find random item
		Random rand = new Random();
		int item_found=rand.nextInt(itemdb.getSize());
		System.out.println("You have found a " + itemdb.items_db[item_found][0]+".");
		toon.inventory[toon.getInv_size()][0]= itemdb.items_db[item_found][0];//assign item from item_db to inventory
		toon.inventory[toon.getInv_size()][1]= itemdb.items_db[item_found][1];
		toon.inventory[toon.getInv_size()][2]= itemdb.items_db[item_found][2];
		toon.setInv_size( toon.getInv_size() + 1 );//track size of inventory
	}
	private static void combat( player toon, int monster_hp){//start combat
	int hit_dmg=0, heal=0;
	Random rand = new Random();
	npc boss = new npc(); 
	
	if (monster_hp  > 100) {
		
		monster_hp = 100 + rand.nextInt(200);
		System.out.println("Monster has "+monster_hp+".");
		System.out.println("A Boss monster has attacked!");
 
		while(monster_hp>0){//combat loop until monster dead
			if (rand.nextInt(100)  > 50){ //monster hits you
				System.out.println("The monster hits you.");
				if(toon.getDexterity() < rand.nextInt(20) ){//use dex to determine if you avoid an attack
					boss.setStamina(boss.getStamina() - 10); 
					if (boss.getStamina()<= 0 ) hit_dmg = boss.getStrength() *  rand.nextInt(5) ;
						else hit_dmg = boss.getStrength() *  rand.nextInt(10) ; 
					toon.setHp(toon.getHp()-hit_dmg) ;
					System.out.println("You have been hit for "+hit_dmg+" hitpoints.");
					hit_dmg=0; 
				} else if (boss.getWisdom() < rand.nextInt(20)) {//healing
					heal = rand.nextInt(10);
					System.out.println("The gods have favor on the monster, healing you for "+heal+".");
					toon.setHp(toon.getHp()+heal);
				} else {
					System.out.println("You jump out of the way, avoiding damage.");
				}
				if(toon.getHp()<=0){//check players hp & exit if dead
					System.out.println("You have died.");
					System.exit(0);
					break;
				} else {
					System.out.println("Player Health: "+ toon.getHp());//track hit points during combat
					System.out.println("Monster Health: "+ boss.getHp());//track hit points during combat
					
				}
				if (boss.getAgility() > rand.nextInt(20)){//check agil for chance of additional attack from boss
					System.out.println("Monster dodges a blow and are granted an additional attack.");
					hit_dmg = boss.getStrength() *  rand.nextInt(10) ; 
					toon.setHp(toon.getHp() - hit_dmg); 
					System.out.println("The monster has hit you for "+hit_dmg+".");
					hit_dmg=0;
				}
				if(boss.getIntelligence()>rand.nextInt(20 )){
					if (boss.getMana() > 0){
						hit_dmg = rand.nextInt(10) ;
						toon.setHp(toon.getHp() - hit_dmg); 
						System.out.println("Monster's spell has hit you for "+hit_dmg+".");
						hit_dmg=0;
						boss.setMana(boss.getMana()-10); 
					}

				}
			} else {
				//you hit monster (or heal yourself)
				if(boss.getDexterity() < rand.nextInt(20) ){//use dex to determine if monster avoid an attack
					if(toon.getWisdom() < rand.nextInt(20)){
						toon.setStamina(toon.getStamina() - 10); 
						if (toon.getStamina()<= 0 ) hit_dmg = toon.getStrength() *  rand.nextInt(5) ;
							else hit_dmg = toon.getStrength() *  rand.nextInt(10) ;
						// if you're out of stamina you do half damage
						monster_hp = monster_hp - hit_dmg;
						System.out.println("You have hit the monster for "+hit_dmg+".");
						hit_dmg=0;
					} else {//healing
						heal = rand.nextInt(10);
						System.out.println("The gods have favor on you, healing you for "+heal+".");
						toon.setHp(toon.getHp()+heal);
					}
				} else {
					System.out.println("The monster avoids your blow.");
				}

			}
			//cast a spell
			if(toon.getIntelligence()>rand.nextInt(20 )){
				if (toon.getMana() > 0){
					hit_dmg = rand.nextInt(10) ;
					monster_hp = monster_hp - hit_dmg;
					System.out.println("Your spell  has hit the monster for "+hit_dmg+".");
					hit_dmg=0;
					toon.setMana(toon.getMana()-10); 
				}

			}
			if (toon.getAgility() > rand.nextInt(20)){//check agil for chance of additional attack
				System.out.println("You dodge a blow and are granted an additional attack.");
				hit_dmg = toon.getStrength() *  rand.nextInt(10) ; 
				monster_hp = monster_hp - hit_dmg;
				System.out.println("You have hit the monster for "+hit_dmg+".");
				hit_dmg=0;
			}
			if(monster_hp<=0){//you have killed the monster, reset player
				System.out.println("The monster is dead.");
				toon.setMana(100); 
				toon.setStamina(100);
				toon.setHp(100);
			}
		}
	}
	
	while(monster_hp>0 && monster_hp  < 100){//combat loop until monster dead
		if (rand.nextInt(100)  > 50){ //monster hits you
			System.out.println("The monster hits you.");
			if(toon.getDexterity() < rand.nextInt(20) ){//use dex to determine if you avoid an attack
				hit_dmg = rand.nextInt(20) * rand.nextInt(10) ;
				toon.setHp(toon.getHp()-hit_dmg) ;
				System.out.println("You have been hit for "+hit_dmg+".");
				hit_dmg=0; 
			} else {
				System.out.println("You jump out of the way, avoiding damage.");
			}
			if(toon.getHp()<=0){//check players hp & exit if dead
				System.out.println("You have died.");
				System.exit(0);
				break;
			} else {
				System.out.println("Health: "+ toon.getHp());//track hit points during combat
			}
		} else {
			//you hit monster (or heal yourself)
			if(toon.getWisdom() < rand.nextInt(20)){
				toon.setStamina(toon.getStamina() - 10); 
				if (toon.getStamina()<= 0 ) hit_dmg = toon.getStrength() *  rand.nextInt(5) ;
					else hit_dmg = toon.getStrength() *  rand.nextInt(10) ;
				// if you're out of stamina you do half damage
				monster_hp = monster_hp - hit_dmg;
				System.out.println("You have hit the monster for "+hit_dmg+".");
				hit_dmg=0;
			} else {//healing
				heal = rand.nextInt(10);
				System.out.println("The gods have favor on you, healing you for "+heal+".");
				toon.setHp(toon.getHp()+heal);
			}
		}
		//cast a spell
		if(toon.getIntelligence()>rand.nextInt(20 )){
			if (toon.getMana() > 0){
				hit_dmg = rand.nextInt(10) ;
				monster_hp = monster_hp - hit_dmg;
				System.out.println("Your spell  has hit the monster for "+hit_dmg+".");
				hit_dmg=0;
				toon.setMana(toon.getMana()-10); 
			}

		}
		if (toon.getAgility() > rand.nextInt(20)){//check agil for chance of additional attack
			System.out.println("You dodge a blow and are granted an additional attack.");
			hit_dmg = toon.getStrength() *  rand.nextInt(10) ; 
			monster_hp = monster_hp - hit_dmg;
			System.out.println("You have hit the monster for "+hit_dmg+".");
			hit_dmg=0;
		}
		if(monster_hp<=0){//you have killed the monster, reset player
			System.out.println("The monster is dead.");
			toon.setMana(100); 
			toon.setStamina(100);
			toon.setHp(100);
		}
	}
} 
	private static void use (player toon, int invent_item) {
		Random rand = new Random(); 
		int effect = 0;
		char input = toon.inventory[invent_item][2].charAt(0);//get item identification number from inventory entry
		switch (input) {
			case '1'://type 1 is potion
				effect = rand.nextInt(3);
				//make sure the potion isn't empty and tell the player the effects
				if( toon.inventory[invent_item][1] != "empty") System.out.println("Using "+toon.inventory[invent_item][0]+" of "+toon.inventory[invent_item][1]+" for effect of "+effect+".");
				//check which attribute the potion effects and apply it
				switch (toon.inventory[invent_item][1]) {
				case "Dexterity":
					toon.setDexterity(toon.getDexterity() + effect);
					break;	
				case "Stamina":
					toon.setStamina(toon.getStamina() + effect);
					break;	
				case "Wisdom":
					toon.setWisdom(toon.getWisdom() + effect);
					break;	
				case "Strength":
					toon.setStrength(toon.getStrength() + effect);
					break;	
				case "Intellience":
					toon.setIntelligence(toon.getIntelligence() + effect);
					break;	
				case "hp":
					toon.setHp(toon.getHp() + (effect * 5 ));
					break;	
				case "mana":
					toon.setMana(toon.getMana() + (effect * 5));
					break;	
				case "Agility":
					toon.setAgility(toon.getAgility() + effect);
					break;	
				default:
					System.out.println("This potion is unknown.");
				}//empty the potion
				toon.inventory[invent_item][1] = "empty";
				break; 
			default://just add case to this switch statement to extend item types
				System.out.println("Unknown item type.");
		} 
	} 
	public static void main(String[] args) throws IOException {
		player toon = new player();
		item_db itemdb = new item_db();
		int c1 = 0; 
		int[][] map = new int[100][100];
		int i=0;
		int e=0;
		int count=0;
		String input;
		Random rand = new Random(); 
		//fill in the 100x100 map with monsters (50% chance of monsters)
		System.out.println("Welcome to dungeon hack!\nCommands: i[inventory],x[exit],u[use],c[stats]");
		for(i=0;i<100;++i){
			for(e=0;e<100;++e){
				//squares have a fifty fifty chance to have a random monster value on them
				if (rand.nextInt(100) > 50 && rand.nextInt(100) < 30 ){
					map[e][i]= 101;//monsters hp up to 100
				} else if (rand.nextInt(100) > 50 ){
					map[e][i]= rand.nextInt(100);
				} else {
					map[e][i]=0;
				}
			}
		}
		System.out.print("Generated 100x100 map.\n");
		itemdb.load();//reads the item.db file into the itemdb and then start player creation
		System.out.println("What would you like your player to be called?");
		Scanner sc= new Scanner(System.in); 
		input = sc.nextLine(); 
		while(0==0) {
			if (input.equals("") ){//make sure player enters something for a name
				System.out.println("You must enter a name.");
			} else break;
			input = sc.nextLine();
		}
		toon.setName(input);//apply class to player
		System.out.println("Predefined classes are: mage,fighter,healer,rouge [or enter your own]");
		toon.apply_class("default");
		i=rand.nextInt(100); //set starting position to a random location on the map
		e =rand.nextInt(100);
		//main game loop
		System.out.print("Movement keys: [w,a,s,d]\n");
		while (0==0){ //gather input key
			c1 = System.in.read(); 
			switch(c1){ //determine direction
			case 'b':
				combat(toon,101);
				break;
			case 'a':
				if (e == 100) {//make sure we still on the map
					System.out.println("You can not go that way.");
				} else {
					e++;
					System.out.print("You have went left[w,a,s,d].\n");
					if(map[e][i]>0){//check for monster
						combat(toon,map[e][i]);
					} else step(toon,  itemdb);//take a step
				}
				break;
			case 's':
				if (i == 0) {
					System.out.println("You can not go that way.");
				} else {
					i--;
					System.out.print("You have gone back[w,a,s,d].\n");
				 	if(map[e][i]>0){
				 		combat(toon,map[e][i]);
					}else step(toon,  itemdb);
				}
				break;
			case 'd':
				if (e == 0) {
					System.out.println("You can not go that way.");
				} else {
					e--;
					System.out.print("You have gone right[w,a,s,d].\n");
				 	if(map[e][i]>0){
				 		combat(toon,map[e][i]);
					}else step(toon,  itemdb);
				}
				break;
			case 'w':
				if (i == 1000) {
					System.out.println("You can not go that way.");
				} else {
					i++;
					System.out.print("You have gone forward[w,a,s,d].\n");
				 	if(map[e][i]>0){
				 		combat(toon,map[e][i]);
					}else step(toon,  itemdb);
				}
				break;
			case 'i'://list inventory contents
				for(count=0;count< toon.getInv_size();++count){
					System.out.println(count + " " +toon.inventory[count][0] + " of " + toon.inventory[count][1]);
				}
				
				break;
			case 'l'://debug command to dump the itemdb
				itemdb.list();
				break;
			case 'x'://quit game
				System.exit(0);
				break;
			case 'u':  //use item
				Scanner scanner = new Scanner(System.in);//I don't know why 
				String buff = scanner.nextLine(); //		these lines are needed	
				System.out.println("Which inventory item to use:");
				c1= System.in.read();
				c1=c1-48; //convert from ascii to decimal (ugly)
				use(toon, c1); 
				break;
			case 'g'://debug command to randomly give item
				item(toon, itemdb); 
				break;
			case 'c'://print player stats
				System.out.println("Your name is " + toon.getName() + ".");
				System.out.println("Class: " + toon.getClas() + ".");
				System.out.println("Agi: \t" + toon.getAgility() + ".");
				System.out.println("Dex: \t" + toon.getDexterity() + ".");
				System.out.println("Int: \t" + toon.getIntelligence() + ".");
				System.out.println("Sta: \t" + toon.getStamina() + ".");
				System.out.println("Str: \t" + toon.getStrength() + ".");
				System.out.println("Wis: \t" + toon.getWisdom() + ".");
				System.out.println("Hit points: \t" + toon.getHp() + ".");
				System.out.println("Mana Points: \t" + toon.getMana() + ".");
				break;
			case '\0':
				break;
			default:
				System.out.print("Movement keys: [w,a,s,d]\n");
			}
		}		
	} 
}
