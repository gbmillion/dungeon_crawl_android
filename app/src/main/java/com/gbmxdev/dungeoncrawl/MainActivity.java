package com.gbmxdev.dungeoncrawl;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Called when the user taps the Send button
     */
    public void sendCMD(View view) throws IOException {

        //game dungeon = new game();
        this.main(view);
    }
    public void sendMessage(View view) throws IOException {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.cmd);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void step(player toon, item_db itemdb){//take a step
        Random rand = new Random();
        if (rand.nextInt(100) < 30) this.trap(toon);//30% to hit trap
        else if (rand.nextInt(100) > 90) this.item( toon,itemdb);//10% chance to get item
    }
    private   void trap(player toon ){//apply trap damage
        TextView output = (TextView)findViewById(R.id.textView2);
        Random rand = new Random();
        toon.setHp(toon.getHp() - rand.nextInt(10));
        output.append("You have stepped on a trap, your hp is now " + toon.getHp()  +".\n");
    }
    private   void item( player toon, item_db itemdb ){ //find random item
        TextView output = (TextView)findViewById(R.id.textView2);
        output.setMovementMethod(new ScrollingMovementMethod());
        Random rand = new Random();
        int item_found=rand.nextInt(itemdb.getSize());
        output.append("You have found a " + itemdb.items_db[item_found][0]+".\n");
        toon.inventory[toon.getInv_size()][0]= itemdb.items_db[item_found][0];//assign item from item_db to inventory
        toon.inventory[toon.getInv_size()][1]= itemdb.items_db[item_found][1];
        toon.inventory[toon.getInv_size()][2]= itemdb.items_db[item_found][2];
        toon.setInv_size( toon.getInv_size() + 1 );//track size of inventory
    }
    private   void combat( player toon, int monster_hp){//start combat
        int hit_dmg=0, heal=0;
        Random rand = new Random();
        npc boss = new npc();
        TextView output = (TextView)findViewById(R.id.textView2);
        output.setMovementMethod(new ScrollingMovementMethod());
        if (monster_hp  > 100) {

            monster_hp = 100 + rand.nextInt(200);
            output.append("Monster has "+monster_hp+".\n");
            output.append("A Boss monster has attacked!"+".\n");

            while(monster_hp>0){//combat loop until monster dead
                if (rand.nextInt(100)  > 50){ //monster hits you
                    output.append("The monster hits you."+".\n");
                    if(toon.getDexterity() < rand.nextInt(20) ){//use dex to determine if you avoid an attack
                        boss.setStamina(boss.getStamina() - 10);
                        if (boss.getStamina()<= 0 ) hit_dmg = boss.getStrength() *  rand.nextInt(5) ;
                        else hit_dmg = boss.getStrength() *  rand.nextInt(10) ;
                        toon.setHp(toon.getHp()-hit_dmg) ;
                        output.append("You have been hit for "+hit_dmg+" hitpoints."+".\n");
                        hit_dmg=0;
                    } else if (boss.getWisdom() < rand.nextInt(20)) {//healing
                        heal = rand.nextInt(10);
                        output.append("The gods have favor on the monster, healing you for "+heal+".\n");
                        toon.setHp(toon.getHp()+heal);
                    } else {
                        output.append("You jump out of the way, avoiding damage."+".\n");
                    }
                    if(toon.getHp()<=0){//check players hp & exit if dead
                        output.append("You have died."+".\n");
                        System.exit(0);
                        break;
                    } else {
                        output.append("Player Health: "+ toon.getHp()+".\n");//track hit points during combat
                        output.append("Monster Health: "+ boss.getHp()+".\n");//track hit points during combat

                    }
                    if (boss.getAgility() > rand.nextInt(20)){//check agil for chance of additional attack from boss
                        output.append("Monster dodges a blow and are granted an additional attack."+".\n");
                        hit_dmg = boss.getStrength() *  rand.nextInt(10) ;
                        toon.setHp(toon.getHp() - hit_dmg);
                        output.append("The monster has hit you for "+hit_dmg+"."+".\n");
                        hit_dmg=0;
                    }
                    if(boss.getIntelligence()>rand.nextInt(20 )){
                        if (boss.getMana() > 0){
                            hit_dmg = rand.nextInt(10) ;
                            toon.setHp(toon.getHp() - hit_dmg);
                            output.append("Monster's spell has hit you for "+hit_dmg+"."+".\n");
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
                            output.append("You have hit the monster for "+hit_dmg+"."+".\n");
                            hit_dmg=0;
                        } else {//healing
                            heal = rand.nextInt(10);
                            output.append("The gods have favor on you, healing you for "+heal+"."+".\n");
                            toon.setHp(toon.getHp()+heal);
                        }
                    } else {
                        output.append("The monster avoids your blow."+".\n");
                    }

                }
                //cast a spell
                if(toon.getIntelligence()>rand.nextInt(20 )){
                    if (toon.getMana() > 0){
                        hit_dmg = rand.nextInt(10) ;
                        monster_hp = monster_hp - hit_dmg;
                        output.append("Your spell  has hit the monster for "+hit_dmg+"."+".\n");
                        hit_dmg=0;
                        toon.setMana(toon.getMana()-10);
                    }

                }
                if (toon.getAgility() > rand.nextInt(20)){//check agil for chance of additional attack
                    output.append("You dodge a blow and are granted an additional attack."+".\n");
                    hit_dmg = toon.getStrength() *  rand.nextInt(10) ;
                    monster_hp = monster_hp - hit_dmg;
                    output.append("You have hit the monster for "+hit_dmg+"."+".\n");
                    hit_dmg=0;
                }
                if(monster_hp<=0){//you have killed the monster, reset player
                    output.append("The monster is dead."+".\n");
                    toon.setMana(100);
                    toon.setStamina(100);
                    toon.setHp(100);
                }
            }
        }

        while(monster_hp>0 && monster_hp  < 100){//combat loop until monster dead
            if (rand.nextInt(100)  > 50){ //monster hits you
                output.append("The monster hits you."+".\n");
                if(toon.getDexterity() < rand.nextInt(20) ){//use dex to determine if you avoid an attack
                    hit_dmg = rand.nextInt(20) * rand.nextInt(10) ;
                    toon.setHp(toon.getHp()-hit_dmg) ;
                    output.append("You have been hit for "+hit_dmg+"."+".\n");
                    hit_dmg=0;
                } else {
                    output.append("You jump out of the way, avoiding damage."+".\n");
                }
                if(toon.getHp()<=0){//check players hp & exit if dead
                    output.append("You have died."+".\n");
                    System.exit(0);
                    break;
                } else {
                    output.append("Health: "+ toon.getHp()+".\n");//track hit points during combat
                }
            } else {
                //you hit monster (or heal yourself)
                if(toon.getWisdom() < rand.nextInt(20)){
                    toon.setStamina(toon.getStamina() - 10);
                    if (toon.getStamina()<= 0 ) hit_dmg = toon.getStrength() *  rand.nextInt(5) ;
                    else hit_dmg = toon.getStrength() *  rand.nextInt(10) ;
                    // if you're out of stamina you do half damage
                    monster_hp = monster_hp - hit_dmg;
                    output.append("You have hit the monster for "+hit_dmg+"."+".\n");
                    hit_dmg=0;
                } else {//healing
                    heal = rand.nextInt(10);
                    output.append("The gods have favor on you, healing you for "+heal+"."+".\n");
                    toon.setHp(toon.getHp()+heal);
                }
            }
            //cast a spell
            if(toon.getIntelligence()>rand.nextInt(20 )){
                if (toon.getMana() > 0){
                    hit_dmg = rand.nextInt(10) ;
                    monster_hp = monster_hp - hit_dmg;
                    output.append("Your spell  has hit the monster for "+hit_dmg+"."+".\n");
                    hit_dmg=0;
                    toon.setMana(toon.getMana()-10);
                }

            }
            if (toon.getAgility() > rand.nextInt(20)){//check agil for chance of additional attack
                output.append("You dodge a blow and are granted an additional attack."+".\n");
                hit_dmg = toon.getStrength() *  rand.nextInt(10) ;
                monster_hp = monster_hp - hit_dmg;
                output.append("You have hit the monster for "+hit_dmg+"."+".\n");
                hit_dmg=0;
            }
            if(monster_hp<=0){//you have killed the monster, reset player
                output.append("The monster is dead."+".\n");
                toon.setMana(100);
                toon.setStamina(100);
                toon.setHp(100);
            }
        }
    }
    private   void use (player toon, int invent_item  ) {
        TextView output = (TextView)findViewById(R.id.textView2);
        output.setMovementMethod(new ScrollingMovementMethod());
        Random rand = new Random();
        int effect = 0;
        char input = toon.inventory[invent_item][2].charAt(0);//get item identification number from inventory entry
        switch (input) {
            case '1'://type 1 is potion
                effect = rand.nextInt(3);
                //make sure the potion isn't empty and tell the player the effects
                if( toon.inventory[invent_item][1] != "empty") output.append("Using "+toon.inventory[invent_item][0]+" of "+toon.inventory[invent_item][1]+" for effect of "+effect+"."+".\n");
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
                        output.append("This potion is unknown."+".\n");
                }//empty the potion
                toon.inventory[invent_item][1] = "empty";
                break;
            default://just add case to this switch statement to extend item types
                output.append("Unknown item type."+".\n");
        }
    }
    public void main(View view) throws IOException {
        TextView output = (TextView)findViewById(R.id.textView2);
        output.setMovementMethod(new ScrollingMovementMethod());
        EditText editText = (EditText) findViewById(R.id.cmd);
        String message = editText.getText().toString();
        player toon = new player();
        item_db itemdb = new item_db();
        int[][] map = new int[100][100];
        int i=0;
        int e=0;
        String input;
        Random rand = new Random();
        //fill in the 100x100 map with monsters (50% chance of monsters)

        output.append("Welcome to dungeon hack!\nCommands: i[inventory],x[exit],u[use],c[stats]"+".\n");
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
        output.append("Generated 100x100 map."+".\n");
        itemdb.load( );//reads the item.db file into the itemdb and then start player creation
        output.append("What would you like your player to be called?"+".\n");
        EditText etext = (EditText) findViewById(R.id.cmd2);
        input = etext.getText().toString();
        //need to get input for player name & not use generated classes
        toon.setName(input);//apply class to player
        output.append("Predefined classes are: mage,fighter,healer,rouge [or enter your own]"+".\n");
        etext = (EditText) findViewById(R.id.cmd3);
        input = etext.getText().toString();
        toon.apply_class(input);
        i=rand.nextInt(100); //set starting position to a random location on the map
        e =rand.nextInt(100);
        //main game loop
        output.append("Movement keys: [w,a,s,d]"+".\n");
        command (   toon,   itemdb ,  map );
    }
    public void command (  player toon, item_db itemdb ,int[][] map ){
        EditText editText = (EditText) findViewById(R.id.cmd);
        String message = editText.getText().toString();
        Random rand = new Random();
        TextView output = (TextView)findViewById(R.id.textView2);
        int count =0 ;
        int i=rand.nextInt(100);
        int e=rand.nextInt(100);//random starting position on map
        { //gather input key
            String c1 = message;
            switch(c1){ //determine direction
                case "b":
                    combat(toon,101);
                    break;
                case "a":
                    if (e == 100) {//make sure we still on the map
                        output.append("You can not go that way."+".\n");
                    } else {
                        e++;
                        output.append("You have went left[w,a,s,d]."+".\n");
                        if(map[e][i]>0){//check for monster
                            combat(toon,map[e][i]);
                        } else step(toon,  itemdb);//take a step
                    }
                    break;
                case "s":
                    if (i == 0) {
                        output.append("You can not go that way."+".\n");
                    } else {
                        i--;
                        output.append("You have gone back[w,a,s,d]."+".\n");
                        if(map[e][i]>0){
                            combat(toon,map[e][i]);
                        }else step(toon,  itemdb);
                    }
                    break;
                case "d":
                    if (e == 0) {
                        output.append("You can not go that way."+".\n");
                    } else {
                        e--;
                        output.append("You have gone right[w,a,s,d]."+".\n");
                        if(map[e][i]>0){
                            combat(toon,map[e][i]);
                        }else step(toon,  itemdb);
                    }
                    break;
                case "w":
                    if (i == 1000) {
                        output.append("You can not go that way."+".\n");
                    } else {
                        i++;
                        output.append("You have gone forward[w,a,s,d]."+".\n");
                        if(map[e][i]>0){
                            combat(toon,map[e][i]);
                        }else step(toon,  itemdb);
                    }
                    break;
                case "i": //list inventory contents
                    for(count=0;count< toon.getInv_size();++count){
                        output.append(count + " " +toon.inventory[count][0] + " of " + toon.inventory[count][1]);
                    }

                    break;
                case "l"://debug command to dump the itemdb
                    itemdb.list();
                    break;
                case "x"://quit game
                    System.exit(0);
                    break;
                case "u":  //use item
                    Scanner scanner = new Scanner(System.in);//I don't know why
                    String buff = scanner.nextLine(); //		these lines are needed
                    output.append("Which inventory item to use:"+".\n");
                    //need to get item number input for use method, replace 0 in method call
                    use(toon, 0);
                    break;
                case "g"://debug command to randomly give item
                    item(toon, itemdb);
                    break;
                case "c"://print player stats
                    output.append(""+"New text line."+".\n");
                    output.append("Your name is " + toon.getName() + "."+".\n");
                    output.append("Class: " + toon.getClas() + "."+".\n");
                    output.append("Agi: \t" + toon.getAgility() + "."+".\n");
                    output.append("Dex: \t" + toon.getDexterity() + "."+".\n");
                    output.append("Int: \t" + toon.getIntelligence() + "."+".\n");
                    output.append("Sta: \t" + toon.getStamina() + "."+".\n");
                    output.append("Str: \t" + toon.getStrength() + "."+".\n");
                    output.append("Wis: \t" + toon.getWisdom() + "."+".\n");
                    output.append("Hit points: \t" + toon.getHp() + "."+".\n");
                    output.append("Mana Points: \t" + toon.getMana() + "."+".\n");
                    break;
                case "\0":
                    break;
                default:
                    output.append("Movement keys: [w,a,s,d]\n");
                    //System.exit(0);//remove this one we have input
            }
        }
    }

}