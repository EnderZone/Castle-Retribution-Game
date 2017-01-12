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
  int getElement(){return element;}
  String getElementName(){
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
  void changeElement(int element){this.element = element;};
  
  //this compares the elements and returns the damage multiplier with nested switch cases
  float compareElements(int element){
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