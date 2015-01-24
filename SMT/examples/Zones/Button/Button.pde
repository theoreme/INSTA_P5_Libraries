/**
 * Demos the ButtonZone class.
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new ButtonZone("TestButton",100,100,200,200,"Button Text"));
}
void draw() {
  background(79, 129, 189);
}
void pressTestButton(){
  println("Button Pressed");
}

// SEB
// Test if subclass allows redrawing because it's ugly !!
public class MyButtonZone extends ButtonZone
{
  public void draw()
  {
    box(100);
  }
}
