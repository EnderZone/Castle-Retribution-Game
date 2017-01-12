//Made by Enzo Arenas and Spencer Marling
//This is used to draw the screen and what each screen looks like
//  0  = walkable floor
// >0  = impassable object
//  0< = player

//ALL SCREENS IN THE GAME
//First number = floor number
//Second number = column from left
//Third number = row from top
final static int[][] LAYOUT_111 =  {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_121 =  {{1,1,1,1,1,1,1,1},
                                    {1,1,0,0,0,0,0,0},
                                    {1,0,1,0,1,0,0,0},
                                    {1,0,1,1,0,1,0,0},
                                    {1,0,1,0,0,0,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};

final static int[][] LAYOUT_131 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,0,0,0,0},
                                    {0,0,0,1,1,1,0,0},
                                    {0,0,0,0,0,1,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {0,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};

final static int[][] LAYOUT_141 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,1,0,0,0,0,1},
                                    {0,0,1,1,1,0,0,1},
                                    {0,0,0,1,1,1,0,1},
                                    {0,0,0,1,0,1,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};

final static int[][] LAYOUT_112 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,0,1,1,0,0,0},
                                    {1,0,1,0,1,1,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,1,1,1,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};

final static int[][] LAYOUT_122 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,1,0,0,0,0,1},
                                    {0,1,1,1,1,0,0,1},
                                    {0,1,1,1,1,0,0,1},
                                    {0,0,1,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};

final static int[][] LAYOUT_132 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,1,1,0,0,0},
                                    {1,0,1,1,1,1,0,0},
                                    {1,0,1,1,1,1,0,0},
                                    {1,0,0,1,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_142 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,1,1,0,0,1},
                                    {0,0,0,0,1,1,0,1},
                                    {0,0,0,1,0,1,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};

final static int[][] LAYOUT_113 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_123 =  {{1,1,1,1,1,1,1,1},
                                    {1,1,1,0,0,0,0,0},
                                    {1,1,0,1,0,0,0,0},
                                    {1,0,1,1,1,0,0,0},
                                    {1,0,0,1,1,1,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_133 =  {{1,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,1,0,0,0,1,1},
                                    {0,0,1,0,0,1,0,1},
                                    {0,1,0,0,0,1,0,1},
                                    {0,0,0,0,0,1,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_143 =  {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_114 =  {{1,0,0,0,0,0,0,1},
                                    {1,1,0,0,0,0,0,0},
                                    {1,0,0,1,0,1,0,0},
                                    {1,0,1,1,0,0,0,1},
                                    {1,0,0,0,1,0,0,0},
                                    {1,0,0,1,0,0,0,0},
                                    {1,1,0,0,0,1,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_124 =  {{1,0,0,0,0,0,0,0},
                                    {0,1,0,0,0,0,1,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,1,1,1,1,1,1,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_134 =  {{0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,0,0,1,1,1,0,1},
                                    {0,0,1,0,1,0,0,1},
                                    {0,1,1,1,1,1,0,1},
                                    {0,0,1,0,1,0,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_144 =  {{1,0,0,0,0,0,0,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,2,0,1,1},
                                    {1,1,1,1,3,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                  
final static int[][] LAYOUT_211 =  {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_221 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,1,0,1,1,0},
                                    {0,0,0,1,0,0,0,0},
                                    {0,0,0,0,0,1,0,0},
                                    {0,1,1,0,0,1,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_231 =  {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_241 =  {{1,1,1,1,1,1,1,1},
                                    {0,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,2,3,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_212 =  {{1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,1,0,0,0,0,0},
                                    {1,0,1,0,0,0,0,0},
                                    {1,0,1,1,1,1,1,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_222 =  {{0,0,0,0,0,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,1,1,1,1,1,1,0},
                                    {0,1,1,1,1,1,1,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_232  = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,1,0,1,0,1},
                                    {0,0,0,0,1,0,1,1},
                                    {0,0,0,0,0,1,0,1},
                                    {0,0,0,0,0,1,0,1},
                                    {0,0,0,0,1,0,1,1},
                                    {0,0,0,1,0,1,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_242  = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,1,1,1,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}}; 
                                    
final static int[][] LAYOUT_213  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,0,1,1,0,0,1},
                                    {1,0,1,0,0,1,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_223  = {{1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_233  = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,1,1,1,0,0},
                                    {0,0,1,1,0,1,0,0},
                                    {0,0,1,0,1,1,0,0},
                                    {0,0,1,1,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] LAYOUT_243  = {{0,0,0,0,0,0,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,0,0,1,1,1,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,0,0,0,1,0,0,1},
                                    {0,1,1,1,1,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};  
                                    
final static int[][] LAYOUT_214  = {{1,0,0,0,0,0,0,0},
                                    {1,0,0,0,1,0,0,0},
                                    {1,1,0,1,0,0,0,0},
                                    {1,1,0,0,0,1,0,0},
                                    {1,1,0,1,1,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,1,0,0,0,1,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_224  = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,1,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,0,0,1,0,0},
                                    {0,0,0,1,1,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_234  = {{0,0,0,0,0,0,0,0},
                                    {0,0,1,0,1,0,0,0},
                                    {0,1,1,0,1,1,0,0},
                                    {0,1,1,0,0,0,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {0,1,1,1,0,1,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};   
                                    
final static int[][] LAYOUT_244  = {{1,1,1,1,1,1,1,1},
                                    {0,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,1,1},
                                    {0,1,1,1,4,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_341  = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,4,1},
                                    {1,0,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};   
                                    
final static int[][] LAYOUT_312  = {{1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,0,0},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1}};
                                    
final static int[][] LAYOUT_322  = {{1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {0,0,0,0,1,1,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_332  = {{1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,0,2,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1},
                                    {1,1,1,1,1,1,1,1}}; 
                                    
final static int[][] LAYOUT_342  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_313   = {{1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,1,0,0,0,0,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_323  = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,1}};                                    
                                    
final static int[][] LAYOUT_333  = {{1,1,1,1,1,1,1,1},
                                    {1,0,1,0,1,0,1,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {1,0,1,0,1,0,1,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_343  = {{1,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1}};
                                    
final static int[][] LAYOUT_314  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,0,0,0,0,0,0,0},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_324  = {{1,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {0,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
final static int[][] LAYOUT_344  = {{1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,1,1,1,1,1,1,1}};
                                    
                                    
//OTHER FLOOR LAYOUTS  
final static int[][] emptyLayout = {{0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,0,0,0,0,0,0}};
                                    
final static int[][] layout1     = {{1,1,1,1,1,1,1,1},
                                    {0,0,0,0,0,1,0,1},
                                    {1,1,1,0,1,0,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {1,1,1,0,0,0,0,0},
                                    {0,0,0,0,0,1,1,0},
                                    {0,1,1,0,0,0,0,0},
                                    {0,1,0,0,0,1,1,0}};
                                    
final static int[][] layout2     = {{0,0,0,1,1,0,0,0},
                                    {0,0,1,1,1,0,0,1},
                                    {0,0,0,0,0,1,0,0},
                                    {0,0,0,0,0,0,1,0},
                                    {0,0,0,1,1,0,0,0},
                                    {0,0,0,0,0,0,1,1},
                                    {0,0,0,0,0,0,0,0},
                                    {0,0,1,1,1,0,0,1}};
                                    
final static int[][] layout3     = {{0,0,1,1,0,0,0,0},
                                    {0,0,1,0,0,0,0,0},
                                    {1,1,1,0,0,1,0,0},
                                    {1,0,1,0,0,1,0,0},
                                    {0,0,1,1,1,1,1,0},
                                    {1,0,0,0,0,1,0,0},
                                    {1,0,0,0,0,1,0,0},
                                    {1,1,1,0,0,0,0,0}};
                                    
final static int[][] layout_Shop = {{1,1,1,1,1,1,1,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,0,3,4,3,4,0,1},
                                    {1,0,2,2,2,2,0,1},
                                    {1,0,2,2,2,2,0,1},
                                    {1,0,2,2,2,2,0,1},
                                    {1,0,0,0,0,0,0,1},
                                    {1,1,1,0,0,1,1,1}};

class FloorTile{
  //length of all the tiles
   final private int TILE_WIDTH = 75;
   //dimension of the sqaure array
   private int dim;
   //the square array
   private int [][] tile;
   //players position
   private int playerX;
   private int playerY;
  
  FloorTile(){
    dim = 8;
    tile = emptyLayout;
    playerX = playerY = -1;
  }
  
  FloorTile(int[][] layout, int dim){
    this.dim = dim;
    tile = layout;
    playerX = playerY = -1;
  }
  
  //creates the floor based on the id given (null because player never enters it
  FloorTile(int ID){
    dim = 8;
    playerX = playerY = -1;
    switch(ID){
      case 0:  tile = LAYOUT_111;     break;
      case 1:  tile = LAYOUT_121;     break;
      case 2:  tile = LAYOUT_131;     break;
      case 3:  tile = LAYOUT_141;     break;
      case 4:  tile = LAYOUT_112;     break;
      case 5:  tile = LAYOUT_122;     break;
      case 6:  tile = LAYOUT_132;     break;
      case 7:  tile = LAYOUT_142;     break;
      case 8:  tile = LAYOUT_113;     break;
      case 9:  tile = LAYOUT_123;     break;
      case 10: tile = LAYOUT_133;     break;
      case 11: tile = LAYOUT_143;     break;
      case 12: tile = LAYOUT_114;     break;
      case 13: tile = LAYOUT_124;     break;
      case 14: tile = LAYOUT_134;     break;
      case 15: tile = LAYOUT_144;     break;
      case 16: tile = LAYOUT_211;     break;
      case 17: tile = LAYOUT_221;     break;
      case 18: tile = LAYOUT_231;     break;
      case 19: tile = LAYOUT_241;     break;
      case 20: tile = LAYOUT_212;     break;
      case 21: tile = LAYOUT_222;     break;
      case 22: tile = LAYOUT_232;     break;
      case 23: tile = LAYOUT_242;     break;
      case 24: tile = LAYOUT_213;     break;
      case 25: tile = LAYOUT_223;     break;
      case 26: tile = LAYOUT_233;     break;
      case 27: tile = LAYOUT_243;     break;
      case 28: tile = LAYOUT_214;     break;
      case 29: tile = LAYOUT_224;     break;
      case 30: tile = LAYOUT_234;     break;
      case 31: tile = LAYOUT_244;     break;
      case 32: tile = null;           break;
      case 33: tile = null;           break;
      case 34: tile = null;           break;
      case 35: tile = LAYOUT_341;     break;
      case 36: tile = LAYOUT_312;     break;
      case 37: tile = LAYOUT_322;     break;
      case 38: tile = LAYOUT_332;     break;
      case 39: tile = LAYOUT_342;     break;
      case 40: tile = LAYOUT_313;     break;
      case 41: tile = LAYOUT_323;     break;
      case 42: tile = LAYOUT_333;     break;
      case 43: tile = LAYOUT_343;     break;
      case 44: tile = LAYOUT_314;     break;
      case 45: tile = LAYOUT_324;     break;
      case 46: tile = null;           break;
      case 47: tile = LAYOUT_344;     break;
    }
  }
  
  //getter methods for each attributes
  int getPlayerPosX(){return playerX;}
  int getPlayerPosY(){return playerY;}
  int getFloorDimension(){return dim;}
  int getFloorTileNumber(int y, int x){return tile[y][x];}  
  
  //set the intial player position on a walkable tile
  void setPlayer(int y, int x){tile[y][x] -= 2;}
  //set the number for the tile
  void setTile(int y, int x, int t){tile[y][x] = t;}
  
  //more advanced player positon placement, for moving him around
  void setPlayerPos(int y, int x){
    if(!(playerY < 0 || playerX < 0))
      tile[playerY][playerX] = 0; 
    playerY = y;
    playerX = x;
    tile[playerY][playerX] -= 2;
  }
  //set the exact player X position
  void setPlayerPosX(int x){
    if(!(playerY < 0 || playerX < 0))
      tile[playerY][playerX] = 0; 
    playerX = x;
    tile[playerY][playerX] -= 2;
  }
  //set the exact player Y position
  void setPlayerPosY(int y){
   if(!(playerY < 0 || playerX < 0))
      tile[playerY][playerX] = 0;
    playerY = y;
    tile[playerY][playerX] -= 2;
  }
  
  //resets the trialling player tile back to a walkable tile
  void resetTile(){tile[playerY][playerX] += 2;}
  
  //this is the method for drawing the overworld screen
  void displayFloor(){
    //double for loop to go through every element in the array
    for(int i = 0; i < dim; i++)
      for(int j = 0; j < dim; j++){
        //for movable tile
        if(tile[i][j] == 0){
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //for walls
        else if(tile[i][j] == 1){
          fill(100);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //for the boss icon tile to initiate boss fights
        else if(tile[i][j] == 2){
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          fill(0,0,150);
          textSize(85);
          text("B",TILE_WIDTH*j + 10,TILE_WIDTH*(i+1)-5);
        }
        //for the upwards staircase
        else if(tile[i][j] == 3){
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          fill(130,80,0);
          noStroke();
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i,-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+TILE_WIDTH/3,2*-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+2*TILE_WIDTH/3,3*-TILE_WIDTH/4,TILE_WIDTH/3);
          stroke(0);
          noFill();
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //for the downwards staircase
        else if(tile[i][j] == 4){
          fill(130,80,0);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          fill(50);
          noStroke();
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i,3*-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+TILE_WIDTH/3,2*-TILE_WIDTH/4,TILE_WIDTH/3);
          rect(TILE_WIDTH*(j+1),TILE_WIDTH*i+2*TILE_WIDTH/3,-TILE_WIDTH/4,TILE_WIDTH/3);
          stroke(0);
          noFill();
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
        //to draw the player with hero's icon for the marker
        else{
          fill(50);
          rect(TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
          image(heros[0].icon,TILE_WIDTH*j,TILE_WIDTH*i,TILE_WIDTH,TILE_WIDTH);
        }
      }
  }
}