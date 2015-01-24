/*
 * CustomEvents
 * 
 * Nest Events can be subclassed to allow passing
 * custom data along with dispatched Events.
 * 
 * This example sets up mouse interaction and event handling
 * in a manner very similar to the MouseEvents_Simple example.
 * 
 * When clicked, EventSender instances dispatch an instance
 * of CustomEvent, a subclass of a Nest Event. CustomEvent
 * accepts an arbitrary String, and that String can be
 * accessed by any object handling the CustomEvent
 * via NestSprite::handleEvent(). 
 */

import com.transmote.nest.*;
import com.transmote.nest.events.*;

NestSpriteContainer spriteContainer;

void setup () {
  size(400, 400);
  setupSprites();
}

void draw () {
  background(0);
  spriteContainer.updateDisplayList();
}

void setupSprites () {
  // create a NestSpriteContainer to hold all other NestSprites
  spriteContainer = new NestSpriteContainer(this);
  
  // attach EventSender instances to the SpriteContainer
  EventSender spriteLeft = new EventSender("LEFT");
  spriteLeft.x = 100;
  spriteLeft.y = 200;
  spriteContainer.addChild(spriteLeft);
  
  EventSender spriteRight = new EventSender("RIGHT");
  spriteRight.x = 300;
  spriteRight.y = 200;
  spriteContainer.addChild(spriteRight);
  
  // set each EventSender up to handle
  // events generated by the other EventSender
  spriteLeft.addObserver(spriteRight);
  spriteRight.addObserver(spriteLeft);
}
