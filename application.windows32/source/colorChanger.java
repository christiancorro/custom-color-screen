import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class colorChanger extends PApplet {


//import processing.sound.*;
int numPixels;
PImage stars;
int bg = color (255, 255, 255);
Capture video;
int keyColor = 30;
int keyR = (keyColor >> 16) & 0xFF;
int keyG = (keyColor >> 8) & 0xFF;
int keyB = keyColor & 0xFF;

int thresh = 60; // tolerance of
public void setup() {
  
  stars = loadImage("stars.jpg");
  video = new Capture(this, width, height);
  video.start();
  numPixels = video.width * video.height;
}

public void draw() {
  background(bg);
  loadPixels();   
  video.read(); // Read a new video frame
  video.loadPixels(); // Make the pixels of video available
  bg = color(mouseX, mouseY, mouseX-mouseY);
  for (int i = 0; i < numPixels; i++) { // For each pixel in the video frame...
    // Fetch the current color in that location
    int currColor = video.pixels[i];
    int currR = (currColor >> 16) & 0xFF;
    int currG = (currColor >> 8) & 0xFF;
    int currB = currColor & 0xFF;

    // Compute the difference of the red, green, and blue values
    int diffR = abs(currR - keyR);
    int diffG = abs(currG - keyG);
    int diffB = abs(currB - keyB);

    // Render the pixels wich are not the close to the keyColor to the screen

    if ((diffR + diffG + diffB)> thresh) {
      pixels[i] = video.pixels[i];
    } else {

      pixels[i] = stars.pixels[i];
    }
  }
  updatePixels(); // Notify that the pixels[] array has changed
}  



public void mousePressed() {
  keyColor = get(mouseX, mouseY);
  keyR = (keyColor >> 16) & 0xFF;
  keyG = (keyColor >> 8) & 0xFF;
  keyB = keyColor & 0xFF;
}
  public void settings() {  size(640, 480, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "colorChanger" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
