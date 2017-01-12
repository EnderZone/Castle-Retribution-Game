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
static String getElementName(int id){
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
  private color yellow = #DEA222;
  private color[] elementColors = {#F51A0F, #0FFFF9, #FFFF00, #893501, #03ED00, #F3FCB0, #484848};
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
  void initiateBossFightOne(){
    enemy = new Enemy[1];
    enemy[0] = new Enemy(100);
  }
  void initiateBossFightTwo(){
    enemy = new Enemy[1];
    enemy[0] = new Enemy(200);
  }
  void initiateBossFightThree(){
    enemy = new Enemy[1];
    enemy[0] = new Enemy(300);
  }
  
  //a catch all method to call the correct method when trying to start a boss battle
  void initiateBossFight(){
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
  int initiateCombat(int steps){
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
  
  void playerAttack(int i, int j){
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
  void enemyAttack(int i, int j){
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
  void resetCombat(){
    for(int i = 0; i < enemy.length; i++){
      enemy[i].reset();
    }
  }
  
  //used to create a new set of random enemies
  void newEnemy(){
    //creates an array of random length of new enemies
    enemy = new Enemy[round(random(1,5))];
    //for loop to add new enemies
    for(int i = 0; i < enemy.length; i++){
      //do while loop to first create a new enemy
      do{
        //create an enemy from the first floor
        if(floorNumber == 1)
          enemy[i] = new Enemy(round(random(0.5,5.45)));
        //create an enemy from the second floor
        if(floorNumber == 2)
          enemy[i] = new Enemy(round(random(10.5,15.45)));
        //create an enemy from the third floor
        if(floorNumber == 3)
          enemy[i] = new Enemy(round(random(20.5,25.454)));
          //if the enemy is a higher level then make a new enemy
      }while(enemy[i].getLevel() > heros[0].getLevel());
      if(enemy[i].getLevel() > heros[0].getLevel()){
        enemy[i] = new Enemy(1 + 10*(floorNumber-1));
      }
    }
  }
  
  //method used to see if the player has won the combat instance
  boolean win(){
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
  boolean lose(){
    //loops through every enemy to see if they are all dead
    //if one is alive return false
    for(int i = 0; i < heros.length; i++)
      if(!heros[i].dead)
        return false;
    return true;
  }
  
  //method used to calcuate the total xp earned from enemies
  int getXP(){
    int xp = 0;
    //adds the xp earned from each enemy
    for(int i = 0; i < enemy.length; i++)
      xp += enemy[i].getXP();
    return xp;
  }
  
  //same thing as the xp methods but with gold instead
  int getGold(){
    int goldWon = 0;
    for(int i = 0; i < enemy.length; i++)
      goldWon += enemy[i].getGold();
    return goldWon;
  }
  
  //used to draw the combat scenarios
  void draw(){
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
        enemyAttack(round(random(-0.5,2.4)),j);
    //draws the enemies
    drawEnemy();
  }
  
  //This is the special draw case for boss fights
  void drawBossFight(){
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
  
  void drawPlayer(){
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
  void drawEnemy(){
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
  void setWinScreen(){
    xp = getXP();
    goldWon = getGold();
  }
  
  void drawWinScreen(){
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