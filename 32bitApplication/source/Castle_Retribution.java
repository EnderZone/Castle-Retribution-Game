import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Castle_Retribution extends PApplet {

//This game was made by Enzo Arenas, Spencer Marling and Ralton Vaz
//This is the main class of the game and everything is connected to this class

//All possible GameStates
final int LOSE = -2;
final int OVERWORLD_MENU = -1;
final int OVERWORLD = 0;
final int COMBAT = 1;
final int WIN = 2;
final int GAME_OVER = 3;
final int SHOP = 4;
final int WINNER = 5;

Shop shop = new Shop();
Menu menu;
PImage heroPic;
PImage kingPic;
PImage princessPic;

int gold;

//this uses the the ID code declare aboved to see what the gamestate is
int gameState;

//Used for the movement of the character in gameState OVERWORLD
FloorTile[] levelOne,levelTwo,levelThree;
Floor[] floor;

//Variable to track what floor the player is on
int floorNumber = 1;

//start up equipment(will not have no stats in final build)
Equipment sword = new Equipment("Sword", new Status(0,0,0,0,0,0),0,"A Basic Sword");
Equipment cloth = new Equipment("Cloth Armor", new Status(0,0,0,0,0,0),0,"Basic Armor");
//Weapons
Equipment shortsword = new Equipment("Shortsword", new Status(0,0,0,5,0,0),100,"A basic shortsword. Nothing Special. \n+5 Attack");
Equipment longsword = new Equipment("Longsword", new Status(0,0,0,10,0,0),200,"A longer sword with a heavier blade. \n+10 Attack");
Equipment greatsword = new Equipment("Greatsword", new Status(0,0,0,20,0,0),400,"A very large sword with a massive blade. \n+20 Attack");
Equipment broadsword = new Equipment("Broadsword", new Status(0,0,0,3,0,3),100,"A simple sword with a thinner blade. \n+3 Attack, +3 Dexterity");
Equipment rapier = new Equipment("Rapier", new Status(0,0,0,6,0,6),200,"A very thin and long sword. \n+6 Attack, +6 Dexterity");
Equipment katana = new Equipment("Katana", new Status(0,0,0,12,0,12),400,"A very thin yet powerful blade. \n+12 Attack, +12 Dexterity");
//Armour
Equipment chainmail = new Equipment("Chainmail", new Status(0,0,0,0,5,0),100,"A vest made of steel rings. \n+5 Defense");
Equipment ironplate = new Equipment("Ironplate Armour", new Status(0,0,0,0,10,0),200,"Armour made of iron plates. \n+10 Defense");
Equipment dragonscale = new Equipment("Dragonscale armour", new Status(0,0,0,0,20,0),400,"Armour made of the scales of a dragon. \n+20 Defense");
Equipment leather = new Equipment("Leather Armour", new Status(0,0,0,0,3,3),100,"Armour made of monster hide. Lighter than mail armour. \n+3 Defense, +3 Dexterity");
Equipment thief = new Equipment("Thief Hood", new Status(0,0,0,0,6,6),200,"A hood that raises the wearer's stealthiness. \n+6 Defense, +6 Dexterity");
Equipment specter = new Equipment("Specter Cloak", new Status(0,0,0,0,12,12),400,"A cloak that makes the wearer ethereal. \n+12 Defense, +12 Dexterity");

Player[] heros = new Player[3];
Combat combat;

public void setup(){
  frameRate(30);
  
  background(random(50,150));
  // Player(String name, int level, int hp, int st, int att, int def, int dex, Equipment weapon, Equipment armor, int element, PImage icon){
  heros[0] = new Player("Hero",1,150,100,10,10,10,sword,cloth,round(random(1,7)),loadImage("Resources\\Images\\Main Player.png"));
  heros[1] = new Player("King",1,150,100,10,10,10,sword,cloth,round(random(1,7)),loadImage("Resources\\Images\\King.png"));
  heros[2] = new Player("Princess",1,150,100,10,10,10,sword,cloth,round(random(1,7)),loadImage("Resources\\Images\\Princess.png"));
  gameState = OVERWORLD;
  floor = new Floor[3];
  levelOne = new FloorTile[16];
  levelTwo = new FloorTile[16];
  levelThree = new FloorTile[16];
  for(int i = 0; i < levelOne.length; i++){
    levelOne[i] = new FloorTile(i);
    levelTwo[i] = new FloorTile(i+16);
    levelThree[i] = new FloorTile(i+32);
  }
  floor[0] = new Floor(4,1,1,6,4,levelOne);
  floor[1] = new Floor(4,3,3,4,5,levelTwo);
  floor[2] = new Floor(4,3,0,5,3,levelThree);
  combat = new Combat(heros);
  kingPic = loadImage("Resources\\Images\\King.png");
  princessPic = loadImage("Resources\\Images\\Princess.png");
  menu = new Menu(heros);
  gold = 0;
}

public void mousePressed(){
  //This is used to change the element of a player or focus on attacking one enemy
  if(gameState == COMBAT){
    //These three are set for each attack button and the flag is for changing the element
    if(combat.changeElement == -1){
      //change Hero's element
      if(mouseX > 445 && mouseX < 560 && mouseY > height-145 && mouseY < height-145+27){
        if(heros[0].getElement() == 7)
          heros[0].changeElement(1);
        else
          heros[0].changeElement((heros[0].getElement()+1));
      }
      //change King's element
      if(mouseX > 445 && mouseX < 560 && mouseY > height-145+30 && mouseY < height-145+27+30){
        if(heros[1].getElement() == 7)
          heros[1].changeElement(1);
        else
          heros[1].changeElement((heros[1].getElement()+1));
      }
      //change Princess's element
      if(mouseX > 445 && mouseX < 560 && mouseY > height-145+60 && mouseY < height-145+27+60){
        if(heros[2].getElement() == 7)
          heros[2].changeElement(1);
        else
          heros[2].changeElement((heros[2].getElement()+1));
      }
    }
    //used for selecting which enemy to target
    for(int i = 0; i < combat.enemy.length;i++){
      if(dist(mouseX,mouseY,2*width/3,165+50*i) <= 50 && !combat.enemy[i].dead){
        combat.focus = i;
        break;
      }
    }
  }
  //the logic for the Lose screen
  if(gameState == LOSE){
    //if clicked "RUN"
    if(mouseX > width/3-100 && mouseX < width/3+50 && mouseY > 2*height/3 && mouseY < 2*height/3 +100){
      //heals the party
      for(int i = 0; i < heros.length; i++)
        heros[i].reset();
      //changes gamestate to overworld
      gameState = OVERWORLD;
      //if it was a boss fight then remove the flag for the boss fight
      if(combat.bossFight[floorNumber-1])
        combat.bossFight[floorNumber-1] = false;
    }
    //if clicked "Fight Again"
    else if(mouseX > 2*width/3-50 && mouseX < 2*width/3+50 && mouseY > 2*height/3 && mouseY < 2*height/3 +100){
      //heals party
      for(int i = 0; i < heros.length; i++)
        heros[i].reset();
      //resets the enemy and battle
      combat.resetCombat();
      gameState = COMBAT;
    }
  }
}

public void keyPressed(){
  //logic for the shop
  if(gameState == SHOP){
      if (key == 'w') //Goes up one option
    {
      shop.screenPos--;
      shop.screenPos = shop.numCircle(shop.screenPos, shop.hiSelectNum);
    }
    if (key == 's') //Goes down one option
    {
      shop.screenPos++;
      shop.screenPos = shop.numCircle(shop.screenPos, shop.hiSelectNum);
    }
    if (key == 'z' || key == 'Z') //Activates the selected option
    {
      if (shop.shopState == 1 && shop.screenPos == 1)
      {
        shop.shopState = 2;
        shop.screenPos = 1;
      } else if (shop.shopState == 1 && shop.screenPos == 2)
      {
        gameState = OVERWORLD;
      } else if (shop.shopState == 2 && gold >= shop.shopInv[shop.screenPos-1].price)
      {
        shop.shopState = 3;
        shop.equipSelect = shop.shopInv[shop.screenPos-1];
        shop.screenPos = 1;
      } else if (shop.shopState == 3)
      {
        if (shop.equipSelect.stats.att > 0)
        {
          heros[shop.screenPos-1].equip[0] = shop.equipSelect;
          gold -= shop.equipSelect.price;
        } else if (shop.equipSelect.stats.def > 0)
        {
          heros[shop.screenPos-1].equip[1] = shop.equipSelect;
          gold -= shop.equipSelect.price;
        }
          shop.shopState = 2;
      }
    }
    if (key == 'x' || key == 'X') //Goes back one screen
    {
      if (shop.shopState > 1)
      {
        shop.shopState = 1;
        shop.screenPos = 1;    
        shop.info = "You gonna keep usin' that rusty thing?\nAnyways, buy some shiny new gear!";
      }
      else
      {
        gameState = OVERWORLD;
      }
    }
  }
  //logic for overworld controls
  if(gameState == OVERWORLD){
    switch(key){
      //move up
      case'w':
        floor[floorNumber-1].moveUp();
        //checks if combat happens
        gameState = combat.initiateCombat(floor[floorNumber-1].getStepsTaken());
        break;
      //move left
      case'a':
        floor[floorNumber-1].moveLeft();
        gameState = combat.initiateCombat(floor[floorNumber-1].getStepsTaken());
        break;
      //move down
      case's':
        floor[floorNumber-1].moveDown();
        gameState = combat.initiateCombat(floor[floorNumber-1].getStepsTaken());
        break;
      //move right
      case'd':
        floor[floorNumber-1].moveRight();
        gameState = combat.initiateCombat(floor[floorNumber-1].getStepsTaken());
        break;
      //open menu
      case'q':
        gameState = OVERWORLD_MENU;
        break;
      //opens shop
      case 'z':
        shop.shop(); //This runs the shop() function in the Shop class to initialize it
        gameState = SHOP;
        break;
      default:
        break;
    }
  }
  //logic for player to attack in combat
  else if(gameState == COMBAT){
    //hero attacks a random enemy
    if((key == 'z') && !heros[0].tired && !heros[0].dead){
      combat.playerAttack(0,round(random(combat.enemy.length-1)));
    }
    //king attacks a random enemy
    if((key == 'x') && !heros[1].tired && !heros[1].dead){
      combat.playerAttack(1,round(random(combat.enemy.length-1)));
    }
    //princess attacks a random enemy
    if((key == 'c') && !heros[2].tired && !heros[2].dead){
      combat.playerAttack(2,round(random(combat.enemy.length-1)));
    }
  }
  //logic for the win screen
  else if(gameState == WIN && (key == ' ') && combat.goldWon <= 0 && combat.xp <= 0){
    //heros gain a quarter of their life back after combat
    for(int i = 0; i < heros.length; i++)
      heros[i].gainHealth(heros[i].getMaxHealth()/4);
    //adds new enemy for next combat
    combat.newEnemy();
    //changes gamestate to the main overworld
    gameState = OVERWORLD;
  }
  //if in the menu and press q then exit menu
  else if(gameState == OVERWORLD_MENU){if(key == 'q') gameState = OVERWORLD;}
}

public void draw(){
  //if killed final boss then you win
  if(!combat.bossAlive[2])
    gameState = WINNER;
  //used to help set up boss fights
  if(combat.bossFight[floorNumber-1] && !combat.lose()){
    gameState = COMBAT;
  }
  //display the layout of the room of the castle
  if(gameState == OVERWORLD){
    floor[floorNumber-1].displayFloorTile();
  }
  //draw combat if its combat
  else if(gameState == COMBAT){
    combat.draw();
    //if you win draw the win screen
    if(combat.win()){gameState = WIN; floor[floorNumber-1].resetStepsTaken(); combat.setWinScreen();if(combat.bossFight[floorNumber-1]) combat.bossFight[floorNumber-1] = false;}
    //if lost then draw the lost screen
    if(combat.lose()){gameState = LOSE; drawLoseScreen();}
  }
  else if(gameState == WIN){
    //win comabt then draw the win screen
    combat.drawWinScreen();
  }
  
  //if the menu is up then draw the menu
  else if(gameState == OVERWORLD_MENU){
    menu.draw();
  }
  //if the shop is up then draw the shop
  else if(gameState == SHOP){shop.draw();}
  //if combat scenario is lost then draw the lost screen
  else if(gameState == LOSE){drawLoseScreen();}
  //if you won the game draw the won game screen
  else if(gameState == WINNER){drawWinner();}
}

//draws the game Won screen
public void drawWinner(){
  background(255);
  fill(255);
  textSize(110);
    text("YOU BEAT\nTHE GAME",40,height/3);
}

//draws the lost combat screen with the buttons lighing up if you hover over it
public void drawLoseScreen(){
  background(0);
  fill(255);
  textSize(110);
  text("YOU LOST",40,height/3);
  if(mouseX > width/3-100 && mouseX < width/3+50 && mouseY > 2*height/3 && mouseY < 2*height/3 +100)
    fill(150);
  rect(width/3-100,2*height/3,150,100);
  fill(255);
  if(mouseX > 2*width/3-50 && mouseX < 2*width/3+50 && mouseY > 2*height/3 && mouseY < 2*height/3 +100)
    fill(150);
  rect(2*width/3-50,2*height/3,150,100);
  textSize(46);
  fill(0);
  text("RUN",width/3-80,2*height/3+60);
  textSize(32);
  text("FIGHT \n AGAIN",2*width/3-30,2*height/3+40);
}
/*
//  Class Made by Enzo Arenas
//  Combat     -- This object is used to signfy the game when combat as happened
//                and how attacking and defending are calculated
//
//  Objectives -- 1: Determine when combat happens based on a random number of steps       [COMPLETED]
//                2: Calculate the attack damage and register it accordingly               [COMPLETED]
//                3: Calculate amount of damage blocked by defense                         [COMPLETED]
//                4: Calculate who attacks first based on speed and if the attack          [CHANGED]
//                   missed which is random but higher chance with larger difference
//                   in speed 
*/

//This is a static method used to get the name of the elements easily (we know it inefficent
public static String getElementName(int id){
  switch(id){
    case FIRE:return "Fire";
    case WATER:return "Water";
    case LIGHTNING:return "Lightning";
    case EARTH:return "Earth";
    case WIND:return "Wind";
    case HOLY:return "Holy";
    case VILE:return "Vile";
    default:return "EMPTY";
  }
}

class Combat{
  //used to keep track of all the players and enemies
  Player[] player;
  Enemy[] enemy;
  //colors used in the drawing section of combat
  private int yellow = 0xffDEA222;
  private int[] elementColors = {0xffF51A0F, 0xff0FFFF9, 0xffFFFF00, 0xff893501, 0xff03ED00, 0xffF3FCB0, 0xff484848};
  //flags used to do special actions in the drawing and resolution of the bs fights
  boolean[] bossAlive = {true,true,true};
  boolean[] bossFight = {false,false,false};
  //counters used during the boss fight or for the alst two, they are integer flags for element change and 
  //who to attack
  int timer, xp, goldWon, changeElement, focus;
  //used to animate attacks
  int[] att = new int[3];
  
  Combat(){
    player = null;
    enemy = new Enemy[round(random(1,5))];
    timer = 0;
    changeElement = -1;
    att[0] = att[1] = att[2] = 0;
  }
  
  Combat(Player[] player){
    this.player = player;
    newEnemy();
    timer = 0;
    changeElement = -1; 
    att[0] = att[1] = att[2] = 0;
  }
  
  //the following three methods are used to initialize the three boss fights
  public void initiateBossFightOne(){
    enemy = new Enemy[1];
    enemy[0] = new Enemy(100);
  }
  public void initiateBossFightTwo(){
    enemy = new Enemy[1];
    enemy[0] = new Enemy(200);
  }
  public void initiateBossFightThree(){
    enemy = new Enemy[1];
    enemy[0] = new Enemy(300);
  }
  
  //a catch all method to call the correct method when trying to start a boss battle
  public void initiateBossFight(){
    gameState = COMBAT;
    timer = 0;
    if(floorNumber == 1 && bossAlive[0]){
      bossFight[0] = true;
      initiateBossFightOne();
      return;
    }
    if(floorNumber == 2 && bossAlive[1]){
      bossFight[1] = true;
      initiateBossFightTwo();
      return;
    }
    if(floorNumber == 3 && bossAlive[2]){
      bossFight[2] = true;
      initiateBossFightThree();
    }
  }
  
  //this checks if a random encouter will occur based on steps
  public int initiateCombat(int steps){
    //no combat before 15 steps
    if(steps < 15)
      return 0;
    //chance to happen after 15 steps, but definitely after 31 steps
    if(steps > round(random(15,30))){
      timer = 0;
      //creates a new enemy to battle
      newEnemy();
      focus = -1;
      return 1;
    }
    return 0;
  }
  
  public void playerAttack(int i, int j){
    //used to animate attacks
    att[i] = 10;
    //will attack random enemy if there is not a focused enemy
    if(focus == -1){
      //buggy code ignore
      /*while(enemy[j].dead){
        fill(0,0,255);
        j = round(random(0,enemy.length-1));
      }*/
      //used to calculate if the player misses the enemy or does not do enough damage
      if(enemy[j].getDexerity()-player[i].getDexerity() > random(50,100) || player[i].getAttack()-enemy[j].getDefense() <= 0)
        return;
      //if hits then does damage based on the difference and multiplies it by the element multiplier
      enemy[j].takeDamage((int)((player[i].getAttack()-enemy[j].getDefense())*player[i].compareElement(enemy[j].getElement())));
      //loses stamina for every successful attack
      player[i].loseStamina(10); 
    }
    else{
      //if the enemy that is focused on is dead then attack a random enemy
      if(enemy[focus].dead){
        //removes the focus target
        focus = -1;
        playerAttack(i,j);
        return;
      }
      //used to calculate if the player misses the enemy or does not do enough damage
      if(enemy[j].getDexerity()-player[i].getDexerity() > random(50,100) || player[i].getAttack()-enemy[focus].getDefense() <= 0)
        return;
      //if hits then does damage based on the difference and multiplies it by the element multiplier
      enemy[focus].takeDamage((int)((player[i].getAttack()-enemy[focus].getDefense())*player[i].compareElement(enemy[focus].getElement())));
      //loses stamina for every successful attack
      player[i].loseStamina(10);  
      //if the enemy died then remove the focus flag
      if(enemy[focus].dead)
        focus = -1;
    }
  }
  
  //this method is used for the enemies to attack the player
  public void enemyAttack(int i, int j){
    //red screen appears breifly to signify an attack was attempted
    background(155,25,25);
    //if the enemy is dead then they do not attack
    if(enemy[j].dead)
      return;
      //buggy code ignore
    /*while(player[i].dead){
        i = round(random(0,heros.length-1));
      }*/
    //used to calculate if the enemy misses the enemy or does not do enough damage
    if(player[i].getDexerity()*random(0,2)/random(5,10) >= enemy[j].getDexerity()*random(1,4) || enemy[j].getAttack()-player[i].getDefense() <= 0)
      return;
    //if hits then does damage based on the difference and multiplies it by the element multiplier
    player[i].takeDamage((int)((enemy[j].getAttack()-player[i].getDefense())*enemy[j].compareElement(player[i].getElement())));
  }
  
  //used when the player lost combat to reset the fight
  public void resetCombat(){
    for(int i = 0; i < enemy.length; i++){
      enemy[i].reset();
    }
  }
  
  //used to create a new set of random enemies
  public void newEnemy(){
    //creates an array of random length of new enemies
    enemy = new Enemy[round(random(1,5))];
    //for loop to add new enemies
    for(int i = 0; i < enemy.length; i++){
      //do while loop to first create a new enemy
      do{
        //create an enemy from the first floor
        if(floorNumber == 1)
          enemy[i] = new Enemy(round(random(0.5f,5.45f)));
        //create an enemy from the second floor
        if(floorNumber == 2)
          enemy[i] = new Enemy(round(random(10.5f,15.45f)));
        //create an enemy from the third floor
        if(floorNumber == 3)
          enemy[i] = new Enemy(round(random(20.5f,25.454f)));
          //if the enemy is a higher level then make a new enemy
      }while(enemy[i].getLevel() > heros[0].getLevel());
      if(enemy[i].getLevel() > heros[0].getLevel()){
        enemy[i] = new Enemy(1 + 10*(floorNumber-1));
      }
    }
  }
  
  //method used to see if the player has won the combat instance
  public boolean win(){
    //checks if every enemy is dead, if one is alive then return false
    for(int i = 0; i < enemy.length; i++)
      if(!enemy[i].dead)
        return false;
    //if it was a boss fight remove the flags
    if(bossFight[floorNumber-1]){
      bossAlive[floorNumber-1] = false;
      bossFight[floorNumber-1] = false;
    }
    //the player has won the fight therefore true
    return true;
  }
  
  //does the same sa the win function but to see if they lost instead
  public boolean lose(){
    //loops through every enemy to see if they are all dead
    //if one is alive return false
    for(int i = 0; i < heros.length; i++)
      if(!heros[i].dead)
        return false;
    return true;
  }
  
  //method used to calcuate the total xp earned from enemies
  public int getXP(){
    int xp = 0;
    //adds the xp earned from each enemy
    for(int i = 0; i < enemy.length; i++)
      xp += enemy[i].getXP();
    return xp;
  }
  
  //same thing as the xp methods but with gold instead
  public int getGold(){
    int goldWon = 0;
    for(int i = 0; i < enemy.length; i++)
      goldWon += enemy[i].getGold();
    return goldWon;
  }
  
  //used to draw the combat scenarios
  public void draw(){
    //sets the background to grey
    background(150);
    //draw the players
    drawPlayer();
    //increments the timer
    timer++;
    //resets the timer to 0 to avoid integer errors
    if(timer > 1000)
      timer = 0;
    //players earn back stamina over time
    if(timer%25 == 0)
        for(int i = 0; i < 3; i++)
          if(!player[i].dead)
            player[i].gainStamina(5);
    //for loop to check each enemy's attack timers and if they are 0 then attack
    for(int j = 0; j < enemy.length; j++)
      if(!enemy[j].dead && timer%enemy[j].attack == 0)
        //they will attack a random player
        enemyAttack(round(random(-0.5f,2.4f)),j);
    //draws the enemies
    drawEnemy();
  }
  
  //This is the special draw case for boss fights
  public void drawBossFight(){
    //boss change to a random element at set intervals, sorter for higher floor bosses
    if(timer%(300-50*floorNumber) == 0)
      enemy[0].setElement(round(random(1,7)));
    //paints the element halo
    fill(elementColors[enemy[0].getElement()-1],100);
    ellipse(2*width/3+20,height/2,150,150);
    //draws the actual boss
    fill(enemy[0].enemyColor);
    ellipse(2*width/3+20,height/2,120,120);
    //displays the boss's name
    text(enemy[0].getEnemyName(),2*width/3-50,height/2-150);
    //red bar underneath the life bar
    fill(255,0,0);
    rect(2*width/3 - 70,height/2 - 125,200,20);
    //the boss's life bar
    fill(0,255,0);
    rect(2*width/3 - 70,height/2 - 125,200*((float)enemy[0].getHealth()/(float)enemy[0].getMaxHealth()),20);
  }
  
  public void drawPlayer(){
    //goes through every hero to display them and their status
    for(int i = 0; i < heros.length; i++){
      //displays the element halo around the character
      noStroke();
      fill(elementColors[heros[i].getElement()-1],100);
      ellipse(90+att[i],125+100*i,75,75);
      stroke(0);
      //display the heros icon/avatar and if they attacked then move them slightly forward
      image(heros[i].icon,10+att[i],100 + 100*i,heros[i].icon.width/3,heros[i].icon.height/3-10);
      //reset the attack display marker
      att[i] = 0;
      //display the player name in the status bar
      fill(0);
      textSize(24);
      text(heros[i].getPlayerName(),50,height-120 + 30*i);
      //red bar underneath the life bar
      fill(255,0,0);
      rect(160,height-140 + 30*i,100,20);
      //green life bar displaying current life
      fill(0,255,0);
      rect(160,height-140 + 30*i,100*((float)heros[i].getHealth()/(float)heros[i].getMaxHealth()),20);
      //red bar underneath stamina bar
      fill(255,0,0);
      rect(300,height-140 + 30*i,100,20);
      //yellow color for stamina
      fill(240,240,60);
      //if the hero is tired then use a slightly translucent yellow of the same color
      if(heros[i].tired)
        fill(240,240,60,50);
      //the actual stamina bar displaying the current stamina of the hero
      rect(300,height-140 + 30*i,100*((float)heros[i].getStamina()/(float)heros[i].getMaxStamina()),20);
      //displays the element of that player
      if(changeElement == -1){
        fill(yellow);
        rect(445,height-145 + 30*i,115,27);
        fill(0);
        text(heros[i].getElementName(),450,height-120 + 30*i);
      }
    }
    //an old way to change elements not in used anymore
    if(changeElement != -1){
      for(int i = 1; i <= 7; i++){
        fill(elementColors[i-1]);
        rect(445,height-225+25*(i-1),115,30);
        fill(0);
        text(getElementName(i),450,height-203 + 25*(i-1));
      }
    }
  }
  
  //this draws the enemies in combat
  public void drawEnemy(){
    //if it is a boss fight then draw that instead of normal combat
    if(bossFight[floorNumber-1]){
      drawBossFight();
      return;
    }
    //else draw every enemy
    for(int i = 0; i < enemy.length; i++){
      //if they are being focused on then draw the focused on halo
      if(i == focus){
        noStroke();
        fill(255,50);
        ellipse(2*width/3,165+60*i,60,60);
        stroke(0);
      }
      //if they are not dead display their element halo
      if(!enemy[i].dead){
        noStroke();
        fill(elementColors[enemy[i].getElement()-1],100);
        ellipse(2*width/3,165+60*i,50,50);
        stroke(0);
      }
      //fill with their color
      fill(enemy[i].enemyColor);
      //if dead give a translucent verison of their color
      if(enemy[i].dead)
        fill(enemy[i].enemyColor,25);
      //write the enemy name in their color and draw them
      text(enemy[i].getEnemyName(),2*width/3 + 50,160 + 60*i);
      ellipse(2*width/3,165 + 60*i,40,40);
      //red bar underneath the life bar
      fill(255,0,0);
      rect(2*width/3 + 50,165 + 60*i,100,20);
      //the green life bar of the enemy
      fill(0,255,0);
      rect(2*width/3 + 50,165 + 60*i,100*((float)enemy[i].getHealth()/(float)enemy[i].getMaxHealth()),20);
    }
  }
  
  //setup function for the win screen
  public void setWinScreen(){
    xp = getXP();
    goldWon = getGold();
  }
  
  public void drawWinScreen(){
    //if the key is pressed before all the gold and xp have been put into the characters
    //just add all thats left at once
    if(keyPressed && key == ' ' && (goldWon > 0 || xp > 0)){
      gold += goldWon;
      goldWon = 0;
      for(int i =0; i < 3; i++)
        heros[i].gainXp(xp);
      xp = 0;
    }
    //setup the background and text
    background(200);
    textSize(20);
    //displays the earned xp and gold from fight and the party's total gold
    fill(0);
    text("Earned XP: " + xp,50,50);
    text("Earned Gold: " + goldWon,225,50);
    text("Current Gold: " + gold,400,50);
    //display the hero's xp and level and his icon
    image(heros[0].icon,10,50,heros[0].icon.width/3,heros[0].icon.height/3-10);
    noFill();
    rect(200,125,100,25);
    fill(0,0,255);
    rect(200,125,((float)heros[0].getXp()/(float)heros[0].getNextLevel())*100,25);
    text("Level: "+heros[0].getLevel(),200,100);
    if (xp > 0 && !heros[0].dead){
      heros[0].gainXp(1);
    }
    //display the kings's xp and level and his icon
    image(heros[1].icon,10,200,heros[0].icon.width/3,heros[0].icon.height/3-10);
    noFill();
    rect(200,275,100,25);
    fill(0,0,255);
    rect(200,275,((float)heros[1].getXp()/(float)heros[1].getNextLevel())*100,25);
    text("Level: "+heros[0].getLevel(),200,250);
    if (xp > 0 && !heros[1].dead){
      heros[1].gainXp(1);
    }
    ////display the princess's xp and level and his icon
    image(heros[2].icon,10,350,heros[0].icon.width/3,heros[0].icon.height/3-10);
    noFill();
    rect(200,425,100,25);
    fill(0,0,255);
    rect(200,425,((float)heros[2].getXp()/(float)heros[2].getNextLevel())*100,25);
    text("Level: "+heros[0].getLevel(),200,400);
    if (xp > 0 && !heros[2].dead){
      heros[2].gainXp(1);
    }
    if (goldWon > 0){
      gold++;
      goldWon--;
    }
    if(xp > 0)
      xp--;
  }
}
//Made by Enzo Arenas
//The Element class is used to deal with anything that involves elements in the game
//it compares the weakness if two elements and can give you the name or change it

//These are the integer numbers for each element
final static int FIRE = 1;
final static int WATER = 2;
final static int LIGHTNING = 3;
final static int EARTH = 4;
final static int WIND = 5;
final static int HOLY = 6;
final static int VILE = 7;

class Element{
  private int element;
  
  Element(int element){
    this.element = element;
  }
  
  //getter methods
  public int getElement(){return element;}
  public String getElementName(){
    switch(element){
      case FIRE:return "Fire";
      case WATER:return "Water";
      case LIGHTNING:return "Lightning";
      case EARTH:return "Earth";
      case WIND:return "Wind";
      case HOLY:return "Holy";
      case VILE:return "Vile";
      default:return "EMPTY";
    }
  }
  
  //method to change element
  public void changeElement(int element){this.element = element;};
  
  //this compares the elements and returns the damage multiplier with nested switch cases
  public float compareElements(int element){
    switch(this.element){
      case FIRE:
        switch(element){
          case FIRE:case HOLY:case VILE:
            return 1f;
          case WATER:
            return 0.5f;
          case LIGHTNING:
            return 0.75f;
          case EARTH:
            return 1.5f;
          case WIND:
            return 2f;
          default:
            return 1f;
        }
      case WATER:
        switch(element){
          case WATER:case HOLY:case VILE:
            return 1f;
          case LIGHTNING:
            return 0.5f;
          case EARTH:
            return 0.75f;
          case WIND:
            return 1.5f;
          case FIRE:
            return 2f;
          default:
            return 1f;
        }
      case LIGHTNING:
        switch(element){
          case LIGHTNING:case HOLY:case VILE:
            return 1f;
          case EARTH:
            return 0.5f;
          case WIND:
            return 0.75f;
          case FIRE:
            return 1.5f;
          case WATER:
            return 2f;
          default:
            return 1f;
        }
      case EARTH:
        switch(element){
          case EARTH:case HOLY:case VILE:
            return 1f;
          case WIND:
            return 0.5f;
          case FIRE:
            return 0.75f;
          case WATER:
            return 1.5f;
          case LIGHTNING:
            return 2f;
          default:
            return 1f;
        }
      case WIND:
        switch(element){
          case WIND:case HOLY:case VILE:
            return 1f;
          case FIRE:
            return 0.5f;
          case WATER:
            return 0.75f;
          case LIGHTNING:
            return 1.5f;
          case EARTH:
            return 2f;
          default:
            return 1f;
        }
      case HOLY:
        switch(element){
          case FIRE:case WATER:case LIGHTNING:case EARTH:case WIND:
            return 1f;
          case HOLY:
            return 0f;
          case VILE:
            return 4f;
          default:
            return 1f;
        }
      case VILE:
        switch(element){
          case FIRE:case WATER:case LIGHTNING:case EARTH:case WIND:
            return 1f;
          case VILE:
            return 0f;
          case HOLY:
            return 4f;
          default:
            return 1f;
        }
      default:
            return 1f;
    }
  }
}
//made by Enzo Arenas
//This class is nearly identical to the player class except that they have a color, gold value, xp value and attack timer
class Enemy{
  //same as player
  private String name;
  private Status enemyStats;
  private Element element;
  boolean dead;
  //the xp value of the monster
  private int xp;
  //the color of the monster
  int enemyColor;
  //the gold vlaue and attack timer of the monster
  int gold, attack;
  
  Enemy(){
    name = null;
    enemyStats = null;
    element = null;
    xp = 0;
    dead = false;
  }
  
  Enemy(String name, Status stats, int element, int xp){
    this.name = name;
    enemyStats = stats;
    this.element = new Element(element);
    this.xp = xp;
  }
  
  //Creates an enemy based on an id number
  //Floor 1: 1-5
  //Floor 2: 11-15
  //Floor 3: 21-25
  //Floor 1 Boss: 100
  //Floor 2 Boss: 200
  //Floor 3 Boss: 300
  //Basic idea for each monster
  //name = name of monster
  //enemyStats = the stats of each enemy in a Status class
  //element = each monster is assigned a random element
  //xp = the xp value of the monster
  //gold = the gold value of the monster
  //attack = a random attack timer in a certain range
  //enemyColor = the color of the enemy
  Enemy(int ID){
   switch(ID){
     //Floor 1 Enemies
     case 1:
       name = "Golbin";
       enemyStats = new Status(0,25,25,15,5,5);
       element = new Element(round(random(1,7)));
       xp = 50;
       gold = 20;
       attack = round(random(50,85));
       enemyColor = 0xff3B6C13;
       break;
    case 2:
       name = "Kobold";
       enemyStats = new Status(7,50,50,30,15,15);
       element = new Element(round(random(1,7)));
       xp = 100;
       gold = 50;
       attack = round(random(40,65));
       enemyColor = 0xff818181;
       break;
    case 3:
       name = "Slime";
       enemyStats = new Status(4,10,10,10,10,15);
       element = new Element(round(random(1,7)));
       xp = 100;
       gold = 5;
       attack = round(random(10,20));
       enemyColor = 0xffAAFF1A;
       break;
    case 4:
       name = "Hobogoblin";
       enemyStats = new Status(9,200,200,60,20,30);
       element = new Element(round(random(1,7)));
       xp = 400;
       gold = 25;
       attack = round(random(25,30));
       enemyColor = 0xff304310;
       break;
    case 5:
       name = "Guard";
       enemyStats = new Status(5,100,200,60,20,20);
       element = new Element(round(random(1,7)));
       xp = 75;
       gold = 10;
       attack = round(random(60,70));
       enemyColor = 0xffC6C0C0;
       break;

    //Floor 2 enemies
    case 11:
      name = "Ooze";
      enemyStats = new Status(11,400,140,50,0,25);
      element = new Element(round(random(1,7)));
      xp = 525;
      gold = 25;
      attack = round(random(50,85));
      enemyColor = 0xffFEFF4B;
      break;
    
    case 12:
      name = "Zombie";
      enemyStats = new Status(13,265,175,55,30,20);
      element = new Element(round(random(1,7)));
      xp = 575;
      gold = 30;
      attack = round(random(50,85));
      enemyColor = 0xff316705;
      break;
    
    case 13:
      name = "Golem";
      enemyStats = new Status(15,280,155,50,50,25);
      element = new Element(round(random(1,7)));
      xp = 675;
      gold = 35;
      attack = round(random(50,85));
      enemyColor = 0xff6C6C6B;
      break;
    
    case 14:
      name = "Salamander";
      enemyStats = new Status(17,170,170,75,30,25);
      element = new Element(round(random(1,7)));
      xp = 850;
      gold = 40;
      attack = round(random(50,85));
      enemyColor = 0xffE05F09;
      break;
      
    case 15:
      name = "Dire Wolf";
      enemyStats = new Status(19,225,215,60,45,25);
      element = new Element(round(random(1,7)));
      xp = 1050;
      gold = 45;
      attack = round(random(50,85));
      enemyColor = 0xffE5E5E5;
      break;
    
    //Floor 3 enemies
    case 21:
      name = "Ent";
      enemyStats = new Status(21,390,215,75,50,40);
      element = new Element(round(random(1,7)));
      xp = 1300;
      gold = 50;
      attack = round(random(50,85));
      enemyColor = 0xff763C16;
      break;
    
    case 22:
      name = "Wraith";
      enemyStats = new Status(22,330,200,75,40,55);
      element = new Element(round(random(1,7)));
      xp = 1700;
      gold = 55;
      attack = round(random(50,85));
      enemyColor = 0xffEAE3DF;
      break;
    
    case 23:
      name = "Giant Wurm";
      enemyStats = new Status(23,380,220,65,65,45);
      element = new Element(round(random(1,7)));
      xp = 2250;
      gold = 60;
      attack = round(random(50,85));
      enemyColor = 0xffEA7EF0;
      break;
      
    case 24:
      name = "Hatchling";
      enemyStats = new Status(24,375,210,90,50,45);
      element = new Element(round(random(1,7)));
      xp = 2900;
      gold = 65;
      attack = round(random(50,85));
      enemyColor = 0xffA5020F;
      break;
      
    case 25:
      name = "Beholder";
      enemyStats = new Status(25,360,270,100,45,55);
      element = new Element(round(random(1,7)));
      xp = 850;
      gold = 40;
      attack = round(random(50,85));
      enemyColor = 0xff58AA6E;
      break;
      
    case 100:
      name = "Floor Boss 1";
      enemyStats = new Status(10,1000,1000,80,20,20);
      element = new Element(round(random(1,7)));
      xp = 20000;
      gold = 200;
      attack = round(random(30,50));
      enemyColor = 0xff811004;
      break;
      
    case 200:
      name = "Floor Boss 2";
      enemyStats = new Status(20,1500,1000,100,20,20);
      element = new Element(round(random(1,7)));
      xp = 20000;
      gold = 200;
      attack = round(random(30,50));
      enemyColor = 0xff811004;
      break;
      
    case 300:
      name = "Final Boss";
      enemyStats = new Status(30,2250,1000,120,20,20);
      element = new Element(round(random(1,7)));
      xp = 20000;
      gold = 200;
      attack = round(random(30,50));
      enemyColor = 0xff811004;
      break;
   }
  }
  
  //a mass hoard of getters for each attribute the monster has
  public String getEnemyName(){return name;}
  public String getElementName(){return element.getElementName();}
  public int getLevel(){return enemyStats.getLevel();}
  public int getMaxHealth(){return enemyStats.getMaxHealth();}
  public int getHealth(){return enemyStats.getHealth();}
  public int getStamina(){return enemyStats.getStamina();}
  public int getAttack(){return enemyStats.getAttack();}
  public int getDefense(){return enemyStats.getDefense();}
  public int getDexerity(){return enemyStats.getDexerity();}
  public int getElement(){return element.getElement();}
  public int getXP(){return xp;}
  public int getGold(){return gold;}
  //reset method to full restore the monster as good as new
  public void reset(){enemyStats.gainHealth(enemyStats.getMaxHealth()); dead = false;}
  
  //method to change the element of the monster(only for bosses)
  public void setElement(int element){this.element.changeElement(element);}
  //used to compare elements for an attack
  public float compareElement(int element){return this.element.compareElements(element);}
  //method for when the monster takes damage
  public void takeDamage(int loss){enemyStats.takeDamage(loss); if(enemyStats.getHealth() <= 0) dead = true;}
}
//Made by Enzo Arenas and Spencer Marling
//Small class that deals with the equipment the heros can equip

class Equipment{
  //name of the equipment
  String name;
  //the stats of the equipment
  Status stats;
  int price; //Variable to track item prices
  String desc; //Variable to hold the item description
  
  Equipment(String name, Status stats, int price, String desc){
    this.name = name;
    this.stats = stats;
    this.price = price;//Added price property
    this.desc = desc;//Added description property
  }
  
  //getter functions
  public String getEquipName(){return name;}
  public Status getStatus(){return stats;}
}
//Made by Enzo Arenas
//This class deals with the movement of the player in the overworld

class Floor{
  //N for the NxN matrix
   private int dim;
   //FloorTile object that houses all of the information
   private FloorTile[][] rooms;
   //Players positions X and Y
   private int playerX;
   private int playerY;
   //steps taken in the game(between each fight in each floor)
   private int stepsTaken;
   
   Floor(){
     stepsTaken = 0;
     dim = 4;
     rooms = new FloorTile[dim][dim];
     playerX = 0;
     playerY = 0;
   }
   
   Floor(int dim, int x, int y, int x2, int y2, FloorTile[] rooms){
     this.dim = dim;
     this.rooms = new FloorTile[dim][dim];
     setFloorTile(rooms);
     playerX = x;
     playerY = y;
     stepsTaken = 0;
     if(this.rooms[y][x] != null)
       this.rooms[y][x].setPlayerPos(y2,x2);
   }
   
   //getter methods for the class
   public int getStepsTaken(){return stepsTaken;}
   public int getFloorDimension(){return dim;}
   
   //resets the steps taken
   public void resetStepsTaken(){stepsTaken = 0;}
   //used to create a 2D array from a 1D Array of floorTiles
   public void setFloorTile(FloorTile[] room){
     for(int i = 0; i < room.length; i++)
       rooms[i/dim][i%dim] = room[i];
   }
   
   //moves the character UP
   public void moveUp(){
     //checks to see if the player is at the top of the screen
     if(rooms[playerY][playerX].getPlayerPosY() == 0){
       //checks whether there is a spot that the player can move into
       if(playerY-1 >= 0 && rooms[playerY-1][playerX].getFloorTileNumber(rooms[playerY-1][playerX].getFloorDimension()-1,rooms[playerY][playerX].getPlayerPosX()) == 0){
         //resets the tile the player is currently on
         rooms[playerY][playerX].resetTile();
         //set the player in the bottom of the screen above the current one at the same x position
         rooms[playerY-1][playerX].setPlayerPos(rooms[playerY-1][playerX].getFloorDimension()-1,rooms[playerY][playerX].getPlayerPosX());
         //tells the game that the player has moved up a screen
         playerY--;
         //increase steps Taken
         stepsTaken++;
         return;
       }return;
     }
     //if a wall then ignore
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()-1,rooms[playerY][playerX].getPlayerPosX()) == 1)
       return;
     //if its a boss icon try to initiate a boss fight
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()-1,rooms[playerY][playerX].getPlayerPosX()) == 2){
       combat.initiateBossFight();
       //if boss is dead then remove the boss icon
       if(!combat.bossAlive[floorNumber-1])
         rooms[playerY][playerX].setTile(rooms[playerY][playerX].getPlayerPosY()-1,rooms[playerY][playerX].getPlayerPosX(),0);
       return;
     }
     //if its a staircase up then move up a floor
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()-1,rooms[playerY][playerX].getPlayerPosX()) == 3){
       floorNumber++;
       return;
     }
     //if its a staircase down then move down a floor
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()-1,rooms[playerY][playerX].getPlayerPosX()) == 4){
       floorNumber--;
       return;
     }
     //increase steps taken and move the player up
     rooms[playerY][playerX].setPlayerPosY(rooms[playerY][playerX].getPlayerPosY()-1);
     stepsTaken++;
   }
   
   //moves the character DOWN
   public void moveDown(){
     //checks to see if the player is at the bottom of the screen
     if(rooms[playerY][playerX].getPlayerPosY()+1 == rooms[playerY][playerX].getFloorDimension()){
       //checks whether there is a spot that the player can move into
       if(playerY+1 < dim &&  rooms[playerY+1][playerX].getFloorTileNumber(0,rooms[playerY][playerX].getPlayerPosX()) == 0){
         //resets the tile the player is currently on
         rooms[playerY][playerX].resetTile();
         //set the player in the top of the screen below the current one at the same x position
         rooms[playerY+1][playerX].setPlayerPos(0,rooms[playerY][playerX].getPlayerPosX());
         //tells the game that the player has moved down a screen
         playerY++;
         //increase steps Taken
         stepsTaken++;
         return;
       }return;
     }
     //if a wall then ignore
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()+1,rooms[playerY][playerX].getPlayerPosX()) == 1)
        return;
     //if its a boss icon try to initiate a boss fight
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()+1,rooms[playerY][playerX].getPlayerPosX()) == 2){
       combat.initiateBossFight();
       //if boss is dead then remove the boss icon
       if(!combat.bossAlive[floorNumber-1])
         rooms[playerY][playerX].setTile(rooms[playerY][playerX].getPlayerPosY()+1,rooms[playerY][playerX].getPlayerPosX(),0);
       return;
     }
     //if its a staircase up then move up a floor
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()+1,rooms[playerY][playerX].getPlayerPosX()) == 3){
       floorNumber++;
       return;
     }
     //if its a staircase down then move down a floor
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY()+1,rooms[playerY][playerX].getPlayerPosX()) == 4){
       floorNumber--;
       return;
     }
     //increase steps taken and move the player down
      rooms[playerY][playerX].setPlayerPosY(rooms[playerY][playerX].getPlayerPosY()+1);
      stepsTaken++;
   }
   
   //moves the character LEFT
   public void moveLeft(){
     //basically the same as above but from the left persepective
     if(rooms[playerY][playerX].getPlayerPosX() == 0){
       if(playerX > 0 && rooms[playerY][playerX-1].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX-1].getFloorDimension()-1) == 0){
         rooms[playerY][playerX].resetTile();
         rooms[playerY][playerX-1].setPlayerPos(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getFloorDimension()-1);
         playerX--;
         stepsTaken++;
         return;
       }return;
     }
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()-1) == 1)
        return;
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()-1) == 2){
       combat.initiateBossFight(); 
       if(!combat.bossAlive[floorNumber-1])
         rooms[playerY][playerX].setTile(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()-1,0);
       return;
     }
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()-1) == 3){    
       floorNumber++;
       return;
     }
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()-1) == 4){
       floorNumber--;
       return;
     }
      rooms[playerY][playerX].setPlayerPosX(rooms[playerY][playerX].getPlayerPosX()-1);
      stepsTaken++;
   }
   
   //moves the character RIGHT
   public void moveRight(){
     //basically same as move up but with the right as the perspective
     if(rooms[playerY][playerX].getPlayerPosX()+1 == rooms[playerY][playerX].getFloorDimension()){
       if(playerX+1 < dim && rooms[playerY][playerX+1].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),0) == 0){
         rooms[playerY][playerX].resetTile();
         rooms[playerY][playerX+1].setPlayerPos(rooms[playerY][playerX].getPlayerPosY(),0);
         playerX++;
         stepsTaken++;
         return;
       }return;
     }
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()+1) == 1)
        return;
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()+1) == 2){
       combat.initiateBossFight(); 
       if(!combat.bossAlive[floorNumber-1])
         rooms[playerY][playerX].setTile(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()+1,0);
       return;
     }
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()+1) == 3){
       floorNumber++;
       return;
     }
     if(rooms[playerY][playerX].getFloorTileNumber(rooms[playerY][playerX].getPlayerPosY(),rooms[playerY][playerX].getPlayerPosX()+1) == 4){
       floorNumber--;
       return;
     }
        
      rooms[playerY][playerX].setPlayerPosX(rooms[playerY][playerX].getPlayerPosX()+1);
      stepsTaken++;
   }
   
   //used to draw the screen
   public void displayFloorTile(){
     rooms[playerY][playerX].displayFloor();
   }
}
//Made by Enzo Arenas and Spencer Marling
//This is used to draw the screen and what each screen looks like
//  0  = walkable floor
// >0  = impassable object
//  0< = player

//ALL SCREENS IN THE GAME
//First number = floor number
//Second number = column from left
//Third number = row from top
final static int[][] LAYOUT_111 =  {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_121 =  {{1,1,1,1,1,1,1,1},
                                    {1,1,0,0,0,0,0,0},
                                    {1,0,1,0,1,0,0,0},
                                    {1,0,1,1,0,1,0,0},
                                    {1,0,1,0,0,0,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};

final static int[][] LAYOUT_131 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,0,0,0,0},
                                    {0,0,0,1,1,1,0,0},
                                    {0,0,0,0,0,1,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {0,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};

final static int[][] LAYOUT_141 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,1,0,0,0,0,1},
                                    {0,0,1,1,1,0,0,1},
                                    {0,0,0,1,1,1,0,1},
                                    {0,0,0,1,0,1,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};

final static int[][] LAYOUT_112 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,0,1,1,0,0,0},
                                    {1,0,1,0,1,1,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,1,1,1,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};

final static int[][] LAYOUT_122 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,1,0,0,0,0,1},
                                    {0,1,1,1,1,0,0,1},
                                    {0,1,1,1,1,0,0,1},
                                    {0,0,1,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};

final static int[][] LAYOUT_132 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,1,1,0,0,0},
                                    {1,0,1,1,1,1,0,0},
                                    {1,0,1,1,1,1,0,0},
                                    {1,0,0,1,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_142 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,1,1,0,0,1},
                                    {0,0,0,0,1,1,0,1},
                                    {0,0,0,1,0,1,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};

final static int[][] LAYOUT_113 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_123 =  {{1,1,1,1,1,1,1,1},
                                    {1,1,1,0,0,0,0,0},
                                    {1,1,0,1,0,0,0,0},
                                    {1,0,1,1,1,0,0,0},
                                    {1,0,0,1,1,1,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_133 =  {{1,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,1,0,0,0,1,1},
                                    {0,0,1,0,0,1,0,1},
                                    {0,1,0,0,0,1,0,1},
                                    {0,0,0,0,0,1,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_143 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_114 =  {{1,0,0,0,0,0,0,1},
                                    {1,1,0,0,0,0,0,0},
                                    {1,0,0,1,0,1,0,0},
                                    {1,0,1,1,0,0,0,1},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,1,0,0,0,0},
                                    {1,1,0,0,0,1,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_124 =  {{1,0,0,0,0,0,0,0},
                                    {0,1,0,0,0,0,1,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,1,1,1,1,1,1,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_134 =  {{0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,0,0,1,1,1,0,1},
                                    {0,0,1,0,1,0,0,1},
                                    {0,1,1,1,1,1,0,1},
                                    {0,0,1,0,1,0,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_144 =  {{1,0,0,0,0,0,0,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,2,0,1,1},
                                    {1,1,1,1,3,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                  
final static int[][] LAYOUT_211 =  {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_221 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,1,0,1,1,0},
                                    {0,0,0,1,0,0,0,0},
                                    {0,0,0,0,0,1,0,0},
                                    {0,1,1,0,0,1,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_231 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_241 =  {{1,1,1,1,1,1,1,1},
                                    {0,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,2,3,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_212 =  {{1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,1,0,0,0,0,0},
                                    {1,0,1,0,0,0,0,0},
                                    {1,0,1,1,1,1,1,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_222 =  {{0,0,0,0,0,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,1,1,1,1,1,1,0},
                                    {0,1,1,1,1,1,1,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_232  = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,1,0,1,0,1},
                                    {0,0,0,0,1,0,1,1},
                                    {0,0,0,0,0,1,0,1},
                                    {0,0,0,0,0,1,0,1},
                                    {0,0,0,0,1,0,1,1},
                                    {0,0,0,1,0,1,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_242  = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}}; 
                                    
final static int[][] LAYOUT_213  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_223  = {{1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_233  = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,1,1,1,0,0},
                                    {0,0,1,1,0,1,0,0},
                                    {0,0,1,0,1,1,0,0},
                                    {0,0,1,1,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_243  = {{0,0,0,0,0,0,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,0,0,1,1,1,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,1,1,1,1,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};  
                                    
final static int[][] LAYOUT_214  = {{1,0,0,0,0,0,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,1,0,1,0,0,0,0},
                                    {1,1,0,0,0,1,0,0},
                                    {1,1,0,1,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,1,0,0,0,1,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_224  = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,0,0,1,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_234  = {{0,0,0,0,0,0,0,0},
                                    {0,0,1,0,1,0,0,0},
                                    {0,1,1,0,1,1,0,0},
                                    {0,1,1,0,0,0,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {0,1,1,1,0,1,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};   
                                    
final static int[][] LAYOUT_244  = {{1,1,1,1,1,1,1,1},
                                    {0,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,1,1,1,4,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_341  = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,4,1},
                                    {1,0,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};   
                                    
final static int[][] LAYOUT_312  = {{1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1}};
                                    
final static int[][] LAYOUT_322  = {{1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {0,0,0,0,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_332  = {{1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,2,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1}}; 
                                    
final static int[][] LAYOUT_342  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_313   = {{1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_323  = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};                                    
                                    
final static int[][] LAYOUT_333  = {{1,1,1,1,1,1,1,1},
                                    {1,0,1,0,1,0,1,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {1,0,1,0,1,0,1,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_343  = {{1,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_314  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_324  = {{1,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_344  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
                                    
//OTHER FLOOR LAYOUTS  
final static int[][] emptyLayout = {{0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] layout1     = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,1,0,1},
                                    {1,1,1,0,1,0,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {1,1,1,0,0,0,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {0,1,1,0,0,0,0,0},
                                    {0,1,0,0,0,1,1,0}};
                                    
final static int[][] layout2     = {{0,0,0,1,1,0,0,0},
                                    {0,0,1,1,1,0,0,1},
                                    {0,0,0,0,0,1,0,0},
                                    {0,0,0,0,0,0,1,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,0,0,1}};
                                    
final static int[][] layout3     = {{0,0,1,1,0,0,0,0},
                                    {0,0,1,0,0,0,0,0},
                                    {1,1,1,0,0,1,0,0},
                                    {1,0,1,0,0,1,0,0},
                                    {0,0,1,1,1,1,1,0},
                                    {1,0,0,0,0,1,0,0},
                                    {1,0,0,0,0,1,0,0},
                                    {1,1,1,0,0,0,0,0}};
                                    
final static int[][] layout_Shop = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,3,4,3,4,0,1},
                                    {1,0,2,2,2,2,0,1},
                                    {1,0,2,2,2,2,0,1},
                                    {1,0,2,2,2,2,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,1,1,0,0,1,1,1}};

class FloorTile{
  //length of all the tiles
   final private int TILE_WIDTH = 75;
   //dimension of the sqaure array
   private int dim;
   //the square array
   private int [][] tile;
   //players position
   private int playerX;
   private int playerY;
  
  FloorTile(){
    dim = 8;
    tile = emptyLayout;
    playerX = playerY = -1;
  }
  
  FloorTile(int[][] layout, int dim){
    this.dim = dim;
    tile = layout;
    playerX = playerY = -1;
  }
  
  //creates the floor based on the id given (null because player never enters it
  FloorTile(int ID){
    dim = 8;
    playerX = playerY = -1;
    switch(ID){
      case 0:  tile = LAYOUT_111;     break;
      case 1:  tile = LAYOUT_121;     break;
      case 2:  tile = LAYOUT_131;     break;
      case 3:  tile = LAYOUT_141;     break;
      case 4:  tile = LAYOUT_112;     break;
      case 5:  tile = LAYOUT_122;     break;
      case 6:  tile = LAYOUT_132;     break;
      case 7:  tile = LAYOUT_142;     break;
      case 8:  tile = LAYOUT_113;     break;
      case 9:  tile = LAYOUT_123;     break;
      case 10: tile = LAYOUT_133;     break;
      case 11: tile = LAYOUT_143;     break;
      case 12: tile = LAYOUT_114;     break;
      case 13: tile = LAYOUT_124;     break;
      case 14: tile = LAYOUT_134;     break;
      case 15: tile = LAYOUT_144;     break;
      case 16: tile = LAYOUT_211;     break;
      case 17: tile = LAYOUT_221;     break;
      case 18: tile = LAYOUT_231;     break;
      case 19: tile = LAYOUT_241;     break;
      case 20: tile = LAYOUT_212;     break;
      case 21: tile = LAYOUT_222;     break;
      case 22: tile = LAYOUT_232;     break;
      case 23: tile = LAYOUT_242;     break;
      case 24: tile = LAYOUT_213;     break;
      case 25: tile = LAYOUT_223;     break;
      case 26: tile = LAYOUT_233;     break;
      case 27: tile = LAYOUT_243;     break;
      case 28: tile = LAYOUT_214;     break;
      case 29: tile = LAYOUT_224;     break;
      case 30: tile = LAYOUT_234;     break;
      case 31: tile = LAYOUT_244;     break;
      case 32: tile = null;           break;
      case 33: tile = null;           break;
      case 34: tile = null;           break;
      case 35: tile = LAYOUT_341;     break;
      case 36: tile = LAYOUT_312;     break;
      case 37: tile = LAYOUT_322;     break;
      case 38: tile = LAYOUT_332;     break;
      case 39: tile = LAYOUT_342;     break;
      case 40: tile = LAYOUT_313;     break;
      case 41: tile = LAYOUT_323;     break;
      case 42: tile = LAYOUT_333;     break;
      case 43: tile = LAYOUT_343;     break;
      case 44: tile = LAYOUT_314;     break;
      case 45: tile = LAYOUT_324;     break;
      case 46: tile = null;           break;
      case 47: tile = LAYOUT_344;     break;
    }
  }
  
  //getter methods for each attributes
  public int getPlayerPosX(){return playerX;}
  public int getPlayerPosY(){return playerY;}
  public int getFloorDimension(){return dim;}
  public int getFloorTileNumber(int y, int x){return tile[y][x];}  
  
  //set the intial player position on a walkable tile
  public void setPlayer(int y, int x){tile[y][x] -= 2;}
  //set the number for the tile
  public void setTile(int y, int x, int t){tile[y][x] = t;}
  
  //more advanced player positon placement, for moving him around
  public void setPlayerPos(int y, int x){
    if(!(playerY < 0 || playerX < 0))
      tile[playerY][playerX] = 0; 
    playerY = y;
    playerX = x;
    tile[playerY][playerX] -= 2;
  }
  //set the exact player X position
  public void setPlayerPosX(int x){
    if(!(playerY < 0 || playerX < 0))
      tile[playerY][playerX] = 0; 
    playerX = x;
    tile[playerY][playerX] -= 2;
  }
  //set the exact player Y position
  public void setPlayerPosY(int y){
   if(!(playerY < 0 || playerX < 0))
      tile[playerY][playerX] = 0;
    playerY = y;
    tile[playerY][playerX] -= 2;
  }
  
  //resets the trialling player tile back to a walkable tile
  public void resetTile(){tile[playerY][playerX] += 2;}
  
  //this is the method for drawing the overworld screen
  public void displayFloor(){
    //double for loop to go through every element in the array
    for(int i = 0; i < dim; i++)
      for(int j = 0; j < dim; j++){
        //for movable tile
        if(tile[i][j] == 0){
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //for walls
        else if(tile[i][j] == 1){
          fill(100);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //for the boss icon tile to initiate boss fights
        else if(tile[i][j] == 2){
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          fill(0,0,150);
          textSize(85);
          text("B",TILE_WIDTH*j + 10,TILE_WIDTH*(i+1)-5);
        }
        //for the upwards staircase
        else if(tile[i][j] == 3){
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          fill(130,80,0);
          noStroke();
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i,-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+TILE_WIDTH/3,2*-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+2*TILE_WIDTH/3,3*-TILE_WIDTH/4,TILE_WIDTH/3);
          stroke(0);
          noFill();
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //for the downwards staircase
        else if(tile[i][j] == 4){
          fill(130,80,0);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          fill(50);
          noStroke();
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i,3*-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+TILE_WIDTH/3,2*-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+2*TILE_WIDTH/3,-TILE_WIDTH/4,TILE_WIDTH/3);
          stroke(0);
          noFill();
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //to draw the player with hero's icon for the marker
        else{
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          image(heros[0].icon,TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
      }
  }
}
//Made by Spencer Marling

class Menu{
  int screenPos;
  int hiSelectNum;
  //REMOVED SCREEN STATES
  Boolean charSelect;
  Player[] heros;
  
  Menu(Player[] heros){
    this.heros = heros;
    screenPos = 1;
    hiSelectNum = 4;
    charSelect = false;
  }
  
  public void draw()
  {
    //Setting the BG
    background(0);
    //Setting up text boxes
    fill(255);
    rect(5,5,590,175);
    rect(5,215,590,175);
    rect(5,420,590,175);
    //Party member info boxes
    image(heros[0].icon,0,50,100,90);
    image(heros[1].icon,0,270,100,90);
    image(heros[2].icon,0,480,100,90);
    fill(0);
    textSize(20);
    //Changed the info the boxes display
    text("Hero \nLV: " + heros[0].level + "\nXP: " + heros[0].getXp() + "/" + heros[0].getNextLevel(),100,35);
    text("HP: " + heros[0].getHealth() + "/" + heros[0].getMaxHealth() + "\nST: " + heros[0].getStamina() + "/" + heros[0].getMaxStamina() + "\nElement: " + heros[0].getElementName(),260,35);
    text("Attack: " + heros[0].getAttack() + "\nDefense: " + heros[0].getDefense() + "\nDexterity: " + heros[0].getDexerity(),445,35);
    text("Weapon: " + heros[0].getWeaponName() + "\nArmour: " + heros[0].getArmorName(),100,135);
    
    text("King \nLV: " + heros[1].level + "\nXP: " + heros[1].getXp() + "/" + heros[1].getNextLevel(),100,245);
    text("HP: " + heros[1].getHealth() + "/" + heros[1].getMaxHealth() + "\nST: " + heros[1].getStamina() + "/" + heros[1].getMaxStamina() + "\nElement: " + heros[1].getElementName(),260,245);
    text("Attack: " + heros[1].getAttack() + "\nDefense: " + heros[1].getDefense() + "\nDexterity: " + heros[1].getDexerity(),445,245);
    text("Weapon: " + heros[1].getWeaponName() + "\nArmour: " + heros[1].getArmorName(),100,345);
    
    text("Princess \nLV: " + heros[2].level + "\nXP: " + heros[2].getXp() + "/" + heros[2].getNextLevel(),100,450);
    text("HP: " + heros[2].getHealth() + "/" + heros[2].getMaxHealth() + "\nST: " + heros[2].getStamina() + "/" + heros[2].getMaxStamina() + "\nElement: " + heros[2].getElementName(),260,450);
    text("Attack: " + heros[2].getAttack() + "\nDefense: " + heros[2].getDefense() + "\nDexterity: " + heros[2].getDexerity(),445,450);
    text("Weapon: " + heros[2].getWeaponName() + "\nArmour: " + heros[2].getArmorName(),100,550);
  }
  
  public void keyPressed()
  {
    //REMOVED UNNECESSARY MENU HANDLERS
    if (key == 'x' || key == 'X') //Exits the menu
    {
      gameState = OVERWORLD;
    }
  }
}
//Made by Enzo Arenas
//This class keeps track of all things related to the heros of the game
//the class inheirts form the Status class to ease leveling up
class Player extends Status{
  //name of hero, how much xp he has, how much to level up,
  private String name;
  private int xpCount;
  private int nextLevel;
  //an array of equipment, will normally be 2, [0] = weapon; [1] = armor
  private Equipment[] equip;
  //the element of the player
  private Element element;
  //the icon of the player
  PImage icon;
  //if the character is tired they cannot attack, same with death
  boolean tired, dead;
  
  Player(){
    super();
    name = null;
    xpCount = 0;
    nextLevel = 100;
    element = null;
    equip = null;
    tired = dead = false;
  }
  
  Player(String name, int level, int hp, int st, int att, int def, int dex, Equipment weapon, Equipment armor, int element, PImage icon){
    super(level,hp,st,att,def,dex);
    this.name = name;
    this.level = level;
    equip = new Equipment[2];
    equip[0] = weapon;
    equip[1] = armor;
    this.element = new Element(element);
    xpCount = 0;
    this.icon = icon;
    nextLevel = 100;
  }
  
  //All getter functions to get attributes that lay in this class
  public String getPlayerName(){return name;}
  public String getElementName(){return element.getElementName();}
  public String getWeaponName(){return equip[0].getEquipName();}
  public String getArmorName(){return equip[1].getEquipName();}
  public int getAttack(){return this.att+equip[0].getStatus().getAttack()+equip[1].getStatus().getAttack();}
  public int getDefense(){return this.def+equip[0].getStatus().getDefense()+equip[1].getStatus().getDefense();}
  public int getDexerity(){return this.dex+equip[0].getStatus().getDexerity()+equip[1].getStatus().getDexerity();}
  public int getElement(){return element.getElement();}
  public int getXp(){return xpCount;}
  public int getNextLevel(){return nextLevel;}
  
  //method used to add xp, if they earned enough to level up 
  public void gainXp(int gain){xpCount += gain;if(xpCount >= nextLevel){this.levelUp();};}
  //level up function
  public void levelUp(){
    //remove the xp needed to level up
    this.xpCount -= this.nextLevel;
    //level up for the hero
    if(name == "Hero"){
      this.level++;
      this.maxHp += round(random(7,12));
      this.maxSt += round(random(3,7));
      this.att += round(random(3,7));
      this.def += round(random(1,4));
      this.dex += round(random(2,5));
      hp = maxHp;
      st = maxSt;
      dead = false;
    }
    //level up for the king
    else if(name == "King"){
      this.level++;
      this.maxHp += round(random(15,25));
      this.maxSt += round(random(2,8));
      this.att += round(random(1,4));
      this.def += round(random(2,6));
      this.dex += round(random(2,3));
      hp = maxHp;
      st = maxSt;
      dead = false;
    }
    //level up for the princess
    else if(name == "Princess"){
      this.level++;
      this.maxHp += round(random(5,10));
      this.maxSt += round(random(7,12));
      this.att += round(random(2,4));
      this.def += round(random(1,3));
      this.dex += round(random(4,6));
      hp = maxHp;
      st = maxSt;
      dead = false;
    }
    //increase the levelUp cap
    this.nextLevel += level/3*100;
    //if they can level up then level up again
    if(xpCount >= nextLevel)
      levelUp();
  }
  
  //group of methods that deal with healing and losing health and stamina
  public void loseStamina(int loss){st -= loss; if(st < 0){st = 0; tired = true;}}
  public void gainStamina(int gain){st += gain; if(st > 50) tired = false; if(st > maxSt) st = maxSt;}
  public void takeDamage(int loss){super.takeDamage(loss); if(hp <= 0){ dead = true; st = 0;}}
  public void gainHealth(int gain){super.gainHealth(gain); if(dead) dead = false;}
  //resets the player to max life, like a level up without the added stats
  public void reset(){hp = maxHp; st = maxSt; dead = false;}
  
  //compare element method to check for damage multipliers
  public float compareElement(int element){return this.element.compareElements(element);}
  //methods to change the element of this player
  public void changeElement(int element){this.element.changeElement(element);}
}
//Made by Spencer Marling

class Shop {
  String info = "You gonna keep usin' that rusty thing?\nAnyways, buy some shiny new gear!"; //Variable to store inf o box text
  int shopState; //tracks which shop screen the player is on
  int screenPos; //Tracks which option the player has selected
  int hiSelectNum; //Represents the number of options on the current active screen
  //int gold; //Amount of gold the player has
  String [] playerInv; //Items the player has
  int [] playerPrices; //Prices of the player's items
  String [] playerDesc; //Descriptions for the player's items
  int playerNum = 20; //Number of items the player can hold
  int currentNum;
  int shopNum = 4; //Number of items the shop has
  String equipComp;
  Equipment tempEquip;
  Equipment equipSelect;
  
  //Array to contain all items
  Equipment [] allItems = new Equipment[14];
  
  Equipment shopInv[];//Equipment array

  public void shop()
  {
    //Filling in the allItems array
    allItems[0] = sword;
    allItems[1] = cloth;
    allItems[2] = shortsword;
    allItems[3] = longsword;
    allItems[4] = greatsword;
    allItems[5] = broadsword;
    allItems[6] = rapier;
    allItems[7] = katana;
    allItems[8] = chainmail;
    allItems[9] = ironplate;
    allItems[10] = dragonscale;
    allItems[11] = leather;
    allItems[12] = thief;
    allItems[13] = specter;
    
    shopState = 1; //This state corresponds to the opening screen
    screenPos = 1;
    currentNum = 0;
    //Shopkeeper's inventory
    shopInv = new Equipment [shopNum];
    //Shop inventory changes based on what floor the player is on
    if (floorNumber == 1)
    {
      shopInv[0] = shortsword;
      shopInv[1] = broadsword;
      shopInv[2] = chainmail;
      shopInv[3] = leather;
    }
    else if (floorNumber == 2)
    {
      shopInv[0] = longsword;
      shopInv[1] = rapier;
      shopInv[2] = ironplate;
      shopInv[3] = thief;
    }
    else if (floorNumber == 3)
    {
      shopInv[0] = greatsword;
      shopInv[1] = katana;
      shopInv[2] = dragonscale;
      shopInv[3] = specter;
    }
  }

  public void draw()
  {
    //Setting the BG
    background(125);
    text(screenPos,50,50);
    //Setting the interface BG
    fill(0);
    rect(0, 500, 600, 100);
    rect(350, 0, 250, 90);
    rect(350, 225, 250, 280);
    //Setting up text boxes
    fill(255);
    rect(5, 535, 80, 60);
    rect(90, 505, 505, 90);
    rect(355, 5, 240, 80);
    rect(355, 230, 240, 270);
    rect(5, 505, 80, 25);
    //Menu handling
    if (shopState == 1) //Main screen
    {
      hiSelectNum = 2;
      //Selector box
      fill(255, 255, 0);
      rect(5, (505+(30*screenPos)), 80, 30);
    } else if (shopState == 2) //Buy screen
    {
      hiSelectNum = shopInv.length;
      //Selection box
      fill(255, 255, 0);
      rect(355, (5+(20*(screenPos-1))), 240, 20);
      info = shopInv[screenPos-1].desc;
      //Adding shop inventory items to box
      fill(0);
    } else if (shopState == 3) //Equip purchase screen
    {
      hiSelectNum = 3;
      //Selection box
      fill(255, 255, 0);
      rect(355, (230+(90*(screenPos-1))), 240, 90);
      fill(0);
    }
    //Adding text
    fill(0);
    textSize(25);
    text("Buy", 10, 560);
    text("Exit", 10, 590);
    textSize(15);
    text(info, 95, 525);
    //Showing gold
    text(gold+" G", 10, 525);
    
    //Displaying the shop inventory
    for (int i=0; i<shopNum; i++)
    {
      text(shopInv[i].name +" ("+shopInv[i].price +"G)", 360, (20+(20*i)));
    }
    
    //Adding comparison text
    equipComp = "Hero \n    Weapon: +";
    findEquipment(heros[0].getWeaponName());
    equipComp += tempEquip.stats.att + " Att, +" + tempEquip.stats.dex + " Dex \n    Armour: +";
    findEquipment(heros[0].getArmorName());
    equipComp += tempEquip.stats.def + " Def, +" + tempEquip.stats.dex + " Dex \n\nKing \n    Weapon: +";
    findEquipment(heros[1].getWeaponName());
    equipComp += tempEquip.stats.att + " Att, +" + tempEquip.stats.dex + " Dex \n    Armour: +";
    findEquipment(heros[1].getArmorName());
    equipComp += tempEquip.stats.def + " Def, +" + tempEquip.stats.dex + " Dex \n\nPrincess \n    Weapon: +";
    findEquipment(heros[2].getWeaponName());
    equipComp += tempEquip.stats.att + " Att, +" + tempEquip.stats.dex + " Dex \n    Armour: +";
    findEquipment(heros[2].getArmorName());    
    equipComp += tempEquip.stats.def + " Def, +" + tempEquip.stats.dex + " Dex";
    text(equipComp,360,250);
  }
  
  public void findEquipment(String equipName)
  {
    for(int i=0; i<14; i++)
    {
      if (equipName == allItems[i].getEquipName())
      {
        tempEquip = allItems[i];
      }
    }
  }

  public int numCircle(int lowNum, int hiNum) //Function to handle menu looping
  {
    if (lowNum < 1)
    {
      return hiNum;
    } else if (lowNum > hiNum)
    {
      return 1;
    } else
    {
      return lowNum;
    }
  }
}
//Made by Enzo Arenas
//Small Class to deal with the status values of 

class Status{
  protected int level;//roughly indicates power
  protected int maxHp;//maximum hit points
  protected int maxSt;//maximum stamina points
  protected int hp;//current hit points
  protected int st;//current stamina points
  protected int att;//attack value, larger means more damage
  protected int def;//defense value, larger mean less damage
  protected int dex;//dexerity value, larger means attack hits and can dodge attacks easier
  
  Status(){level = 1;maxHp = hp = st = def = att = dex = 0;}
  
  Status(int level, int hp, int st, int att, int def, int dex){
    this.level = level;
    this.maxHp = this.hp = hp;
    this.st = this.maxSt = st;
    this.att = att;
    this.def = def;
    this.dex = dex;
  }
  
  //Basic getter methods for each stat
  public int getLevel(){return level;}
  public int getMaxHealth(){return maxHp;}
  public int getMaxStamina(){return maxSt;}
  public int getHealth(){return hp;}
  public int getStamina(){return st;}
  public int getAttack(){return att;}
  public int getDefense(){return def;}
  public int getDexerity(){return dex;}
  
  //deals with changing the current hp and st of the object
  public void gainHealth(int gain){hp += gain; if(hp > maxHp) hp = maxHp;}
  public void gainStamina(int gain){st += gain; if(st > maxSt) st = maxSt;}
  public void takeDamage(int loss){hp -= loss; if(hp < 0) hp = 0;}
  public void loseStamina(int loss){st -= loss; if(st < 0) st = 0;}
}
  public void settings() {  size(600,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Castle_Retribution" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
