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

void setup(){
  frameRate(30);
  size(600,600);
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

void mousePressed(){
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

void keyPressed(){
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

void draw(){
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
void drawWinner(){
  background(255);
  fill(255);
  textSize(110);
    text("YOU BEAT\nTHE GAME",40,height/3);
}

//draws the lost combat screen with the buttons lighing up if you hover over it
void drawLoseScreen(){
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