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

  void shop()
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

  void draw()
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
  
  void findEquipment(String equipName)
  {
    for(int i=0; i<14; i++)
    {
      if (equipName == allItems[i].getEquipName())
      {
        tempEquip = allItems[i];
      }
    }
  }

  int numCircle(int lowNum, int hiNum) //Function to handle menu looping
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