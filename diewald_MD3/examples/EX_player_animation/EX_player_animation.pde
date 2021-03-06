//--------------------------------------------------------
//
// author: thomas diewald
// date:   05.12.2011
// 
//--------------------------------------------------------
// description:
// loading a quake3-player and quake3-weapon and an animationfile
//--------------------------------------------------------
// interaction:
// 1) peasycam controls
// 2) view animation : press any key and move mouse in X-direction

// 3) keys:
// 'x' ..... next animation
// 'y' ..... previous animation
//--------------------------------------------------------

import processing.opengl.*;
//import peasy.*;

import diewald_MD3.q3.*;
import diewald_MD3.q3.displaystyle.interfaces.*;
import diewald_MD3.q3.constants.*;
import diewald_MD3.q3.tools.*;
import diewald_MD3.q3.files.md3.*;
import diewald_MD3.q3.displaystyle.default_styles.*;
import diewald_MD3.q3.files.skin.*;
import diewald_MD3.q3.files.animation.*;


//PeasyCam cam;

Q3_Player player;
Q3_Weapon weapon;
Q3_TexturePool texture_pool;

ArrayList<Q3_Animation> player_animations;

Q3_Animation current_animation;
int animation_index = 0;
int animation_timer = 0;
int frame_IDX = 0;

public void setup() {
  int setup_timer = millis();
  size(800, 800, OPENGL);
  initPeasyCam();

  // some console-logging settings, ... useful for debugging
  AnimationCfg.LOG_CONSOLE = false;
  Skin_File.LOG_CONSOLE = false;
  MD3_Header       .LOG_CONSOLE = !true;
  MD3_Frame        .LOG_CONSOLE = !true;
  MD3_Tag          .LOG_CONSOLE = !true;
  MD3_Surface      .LOG_CONSOLE = !true;
  MD3_SurfaceFrame .LOG_CONSOLE = !true;
  MD3_TagFrame     .LOG_CONSOLE = !true;
  MD3_Shader       .LOG_CONSOLE = !true;
  MD3_Triangle     .LOG_CONSOLE = !true;
  MD3_TexCoord     .LOG_CONSOLE = !true;
  MD3_XYZnormal    .LOG_CONSOLE = !true;

  // make a new texturepool
  // if no texture pool is used, it can happen that the same texture 
  // gets loaded multiple times, depending on the model
  texture_pool = new Q3_TexturePool(this);

  //loading the weapon
  weapon = new Q3_Weapon(this, texture_pool);
  weapon.loadFromDirectory(sketchPath+ "/../models_Q3DEMO/weapons2/rocketl");

  //loading the model
  player = new Q3_Player(this, Team.DEFAULT, texture_pool);
  player.loadFromDirectory(sketchPath+ "/../models_Q3DEMO/players/sarge");

  //assign the weapon to the model
  player.setWeapon(weapon);


  // display-style settings
  player.getNodeSystem().setNodeSystemDisplayStyle( new DefaultDisplayStyle(this));
  player.getNodeSystem().getDisplayStyle().styleShaded()     .display(true);
  player.getNodeSystem().getDisplayStyle().styleNormals()    .display(false);
  player.getNodeSystem().getDisplayStyle().styleBoundingbox().display(false);
  player.getNodeSystem().getDisplayStyle().styleVertices()   .display(false);
  player.getNodeSystem().getDisplayStyle().styleWireframe()  .display(false);
  player.getNodeSystem().getDisplayStyle().styleTags()       .display(false);

  player_animations = player.getAnimations();
  current_animation = player_animations.get(animation_index);
  frame_IDX = current_animation.first_frame;


  System.out.println( "lower frames "+player.getLower().getMD3_File().frames.length) ;
  System.out.println( "upper frames "+player.getUpper().getMD3_File().frames.length) ;
  System.out.println( "head frames "+player.getHead().getMD3_File().frames.length) ;

  println("FINISHED SETUP: " + (millis() - setup_timer) + " ms");
}

public void draw() {
  background(255);

  

  //drawGrid(100, 10, 1);
  //bks(10, 2);
  pushMatrix();
  translate(width / 2, height /2, 0);
  rotateZ(mouseX / 100f);

  
  scale(9);

  //    if( keyPressed ){
  //      int dif = (mouseX-pmouseX)/2;
  //      if( dif < 0) frame_IDX--;
  //      if( dif > 0) frame_IDX++;
  //      if( frame_IDX < 0 ) frame_IDX = 0;
  //      println("frame_IDX = "+frame_IDX);
  //    } 
  //    
  updateFrame();

  player.drawModel(frame_IDX);

  popMatrix();
  
  // println(frameRate);
}

public void updateFrame() {
  if ( millis() - animation_timer > 50) {
    animation_timer = millis();

    frame_IDX++;
    if ( frame_IDX >= current_animation.end_frame )
      frame_IDX = current_animation.first_frame;
  }
  //    println(frame_IDX);
}


public void drawGrid(int size, int grid_gap, int stroke_weight) {
  pushStyle();
  pushMatrix();
  translate(-size / 2, -size / 2, 0);
  fill(180);
  stroke(150, 0, 0);
  strokeWeight(stroke_weight);

  int i;
  for (i = 0; i < size; i += grid_gap) {
    line(0, i, size, i);
    line(i, 0, i, size);
  }
  line(0, i, size, i);
  line(i, 0, i, size);
  popMatrix();
  popStyle();
}

public void bks(int s, int stroke_weight) {
  pushStyle();
  strokeWeight(stroke_weight);
  stroke(255, 0, 0); 
  line(0, 0, 0, s, 0, 0);
  stroke(0, 255, 0); 
  line(0, 0, 0, 0, s, 0);
  stroke(0, 0, 255); 
  line(0, 0, 0, 0, 0, s);
  popStyle();
}

// ----------------------------------------------------------------------------------------------------
void initPeasyCam() 
{
  /*
  cam = new PeasyCam(this, 0, 0, 0, 1000);
  cam.setMinimumDistance(1);
  cam.setMaximumDistance(100000);

  cam.lookAt(16.837477, 193.56741, 406.83347);
  cam.setRotations(1.4167833, 0.48761615, -3.1252024);
  cam.setDistance(900, 0);
  */
}

void printlnCam() 
{
  /*
  float  rot[] = cam.getRotations();
  float  pos[] = cam.getPosition();
  float  lat[] = cam.getLookAt();
  double dis   = cam.getDistance();
  println("rot = "+ rot[0]+", "+rot[1] +", "+rot[2]);
  println("pos = "+ pos[0]+", "+pos[1] +", "+pos[2]);
  println("lat = "+ lat[0]+", "+lat[1] +", "+lat[2]);
  println("dis = "+ dis);
  */
}


public void keyReleased() {
  if ( key == 'p') printlnCam();
  if ( key == 'x' || key == 'y') {
    if ( key == 'y') animation_index--;
    if ( key == 'x') animation_index++;
    if ( animation_index < 0 ) 
      animation_index = 0;
    if ( animation_index >= player_animations.size() ) 
      animation_index = player_animations.size()-1;

    current_animation = player_animations.get(animation_index);
    frame_IDX = current_animation.first_frame;

    println("activated animation["+animation_index+"]:"+current_animation.description);
    current_animation.printData();
  }
}



