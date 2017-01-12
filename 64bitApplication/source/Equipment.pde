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
  String getEquipName(){return name;}
  Status getStatus(){return stats;}
}