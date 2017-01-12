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
  int getLevel(){return level;}
  int getMaxHealth(){return maxHp;}
  int getMaxStamina(){return maxSt;}
  int getHealth(){return hp;}
  int getStamina(){return st;}
  int getAttack(){return att;}
  int getDefense(){return def;}
  int getDexerity(){return dex;}
  
  //deals with changing the current hp and st of the object
  void gainHealth(int gain){hp += gain; if(hp > maxHp) hp = maxHp;}
  void gainStamina(int gain){st += gain; if(st > maxSt) st = maxSt;}
  void takeDamage(int loss){hp -= loss; if(hp < 0) hp = 0;}
  void loseStamina(int loss){st -= loss; if(st < 0) st = 0;}
}