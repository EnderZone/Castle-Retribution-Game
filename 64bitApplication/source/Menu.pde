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
  
  void draw()
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
  
  void keyPressed()
  {
    //REMOVED UNNECESSARY MENU HANDLERS
    if (key == 'x' || key == 'X') //Exits the menu
    {
      gameState = OVERWORLD;
    }
  }
}