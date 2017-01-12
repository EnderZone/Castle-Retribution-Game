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
  String getPlayerName(){return name;}
  String getElementName(){return element.getElementName();}
  String getWeaponName(){return equip[0].getEquipName();}
  String getArmorName(){return equip[1].getEquipName();}
  int getAttack(){return this.att+equip[0].getStatus().getAttack()+equip[1].getStatus().getAttack();}
  int getDefense(){return this.def+equip[0].getStatus().getDefense()+equip[1].getStatus().getDefense();}
  int getDexerity(){return this.dex+equip[0].getStatus().getDexerity()+equip[1].getStatus().getDexerity();}
  int getElement(){return element.getElement();}
  int getXp(){return xpCount;}
  int getNextLevel(){return nextLevel;}
  
  //method used to add xp, if they earned enough to level up 
  void gainXp(int gain){xpCount += gain;if(xpCount >= nextLevel){this.levelUp();};}
  //level up function
  void levelUp(){
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
  void loseStamina(int loss){st -= loss; if(st < 0){st = 0; tired = true;}}
  void gainStamina(int gain){st += gain; if(st > 50) tired = false; if(st > maxSt) st = maxSt;}
  void takeDamage(int loss){super.takeDamage(loss); if(hp <= 0){ dead = true; st = 0;}}
  void gainHealth(int gain){super.gainHealth(gain); if(dead) dead = false;}
  //resets the player to max life, like a level up without the added stats
  void reset(){hp = maxHp; st = maxSt; dead = false;}
  
  //compare element method to check for damage multipliers
  float compareElement(int element){return this.element.compareElements(element);}
  //methods to change the element of this player
  void changeElement(int element){this.element.changeElement(element);}
}