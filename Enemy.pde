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
  color enemyColor;
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
       enemyColor = #3B6C13;
       break;
    case 2:
       name = "Kobold";
       enemyStats = new Status(7,50,50,30,15,15);
       element = new Element(round(random(1,7)));
       xp = 100;
       gold = 50;
       attack = round(random(40,65));
       enemyColor = #818181;
       break;
    case 3:
       name = "Slime";
       enemyStats = new Status(4,10,10,10,10,15);
       element = new Element(round(random(1,7)));
       xp = 100;
       gold = 5;
       attack = round(random(10,20));
       enemyColor = #AAFF1A;
       break;
    case 4:
       name = "Hobogoblin";
       enemyStats = new Status(9,200,200,60,20,30);
       element = new Element(round(random(1,7)));
       xp = 400;
       gold = 25;
       attack = round(random(25,30));
       enemyColor = #304310;
       break;
    case 5:
       name = "Guard";
       enemyStats = new Status(5,100,200,60,20,20);
       element = new Element(round(random(1,7)));
       xp = 75;
       gold = 10;
       attack = round(random(60,70));
       enemyColor = #C6C0C0;
       break;

    //Floor 2 enemies
    case 11:
      name = "Ooze";
      enemyStats = new Status(11,400,140,50,0,25);
      element = new Element(round(random(1,7)));
      xp = 525;
      gold = 25;
      attack = round(random(50,85));
      enemyColor = #FEFF4B;
      break;
    
    case 12:
      name = "Zombie";
      enemyStats = new Status(13,265,175,55,30,20);
      element = new Element(round(random(1,7)));
      xp = 575;
      gold = 30;
      attack = round(random(50,85));
      enemyColor = #316705;
      break;
    
    case 13:
      name = "Golem";
      enemyStats = new Status(15,280,155,50,50,25);
      element = new Element(round(random(1,7)));
      xp = 675;
      gold = 35;
      attack = round(random(50,85));
      enemyColor = #6C6C6B;
      break;
    
    case 14:
      name = "Salamander";
      enemyStats = new Status(17,170,170,75,30,25);
      element = new Element(round(random(1,7)));
      xp = 850;
      gold = 40;
      attack = round(random(50,85));
      enemyColor = #E05F09;
      break;
      
    case 15:
      name = "Dire Wolf";
      enemyStats = new Status(19,225,215,60,45,25);
      element = new Element(round(random(1,7)));
      xp = 1050;
      gold = 45;
      attack = round(random(50,85));
      enemyColor = #E5E5E5;
      break;
    
    //Floor 3 enemies
    case 21:
      name = "Ent";
      enemyStats = new Status(21,390,215,75,50,40);
      element = new Element(round(random(1,7)));
      xp = 1300;
      gold = 50;
      attack = round(random(50,85));
      enemyColor = #763C16;
      break;
    
    case 22:
      name = "Wraith";
      enemyStats = new Status(22,330,200,75,40,55);
      element = new Element(round(random(1,7)));
      xp = 1700;
      gold = 55;
      attack = round(random(50,85));
      enemyColor = #EAE3DF;
      break;
    
    case 23:
      name = "Giant Wurm";
      enemyStats = new Status(23,380,220,65,65,45);
      element = new Element(round(random(1,7)));
      xp = 2250;
      gold = 60;
      attack = round(random(50,85));
      enemyColor = #EA7EF0;
      break;
      
    case 24:
      name = "Hatchling";
      enemyStats = new Status(24,375,210,90,50,45);
      element = new Element(round(random(1,7)));
      xp = 2900;
      gold = 65;
      attack = round(random(50,85));
      enemyColor = #A5020F;
      break;
      
    case 25:
      name = "Beholder";
      enemyStats = new Status(25,360,270,100,45,55);
      element = new Element(round(random(1,7)));
      xp = 850;
      gold = 40;
      attack = round(random(50,85));
      enemyColor = #58AA6E;
      break;
      
    case 100:
      name = "Floor Boss 1";
      enemyStats = new Status(10,1000,1000,80,20,20);
      element = new Element(round(random(1,7)));
      xp = 20000;
      gold = 200;
      attack = round(random(30,50));
      enemyColor = #811004;
      break;
      
    case 200:
      name = "Floor Boss 2";
      enemyStats = new Status(20,1500,1000,100,20,20);
      element = new Element(round(random(1,7)));
      xp = 20000;
      gold = 200;
      attack = round(random(30,50));
      enemyColor = #811004;
      break;
      
    case 300:
      name = "Final Boss";
      enemyStats = new Status(30,2250,1000,120,20,20);
      element = new Element(round(random(1,7)));
      xp = 20000;
      gold = 200;
      attack = round(random(30,50));
      enemyColor = #811004;
      break;
   }
  }
  
  //a mass hoard of getters for each attribute the monster has
  String getEnemyName(){return name;}
  String getElementName(){return element.getElementName();}
  int getLevel(){return enemyStats.getLevel();}
  int getMaxHealth(){return enemyStats.getMaxHealth();}
  int getHealth(){return enemyStats.getHealth();}
  int getStamina(){return enemyStats.getStamina();}
  int getAttack(){return enemyStats.getAttack();}
  int getDefense(){return enemyStats.getDefense();}
  int getDexerity(){return enemyStats.getDexerity();}
  int getElement(){return element.getElement();}
  int getXP(){return xp;}
  int getGold(){return gold;}
  //reset method to full restore the monster as good as new
  void reset(){enemyStats.gainHealth(enemyStats.getMaxHealth()); dead = false;}
  
  //method to change the element of the monster(only for bosses)
  void setElement(int element){this.element.changeElement(element);}
  //used to compare elements for an attack
  float compareElement(int element){return this.element.compareElements(element);}
  //method for when the monster takes damage
  void takeDamage(int loss){enemyStats.takeDamage(loss); if(enemyStats.getHealth() <= 0) dead = true;}
}