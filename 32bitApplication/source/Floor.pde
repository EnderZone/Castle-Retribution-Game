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
   int getStepsTaken(){return stepsTaken;}
   int getFloorDimension(){return dim;}
   
   //resets the steps taken
   void resetStepsTaken(){stepsTaken = 0;}
   //used to create a 2D array from a 1D Array of floorTiles
   void setFloorTile(FloorTile[] room){
     for(int i = 0; i < room.length; i++)
       rooms[i/dim][i%dim] = room[i];
   }
   
   //moves the character UP
   void moveUp(){
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
   void moveDown(){
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
   void moveLeft(){
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
   void moveRight(){
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
   void displayFloorTile(){
     rooms[playerY][playerX].displayFloor();
   }
}