import processing.video.*;
import processing.sound.*;
int numPixels;
PImage stars;
color bg = color (255, 255, 255);
Capture video;
int keyColor = 30;
int keyR = (keyColor >> 16) & 0xFF;
int keyG = (keyColor >> 8) & 0xFF;
int keyB = keyColor & 0xFF;

int thresh = 60; // tolerance 

SoundFile music;

void setup() {
  size(640, 480, P2D);
  stars = loadImage("stars.jpg");
  video = new Capture(this, width, height);
  
  music = new SoundFile(this, "music.mp3");
  music.play();
  
  video.start();
  numPixels = video.width * video.height;
}

void draw() {
  background(bg);
  loadPixels();   
  video.read(); // Read a new video frame
  video.loadPixels(); // Make the pixels of video available
  bg = color(mouseX, mouseY, mouseX-mouseY);
  for (int i = 0; i < numPixels; i++) { // For each pixel in the video frame...
    // Fetch the current color in that location
    color currColor = video.pixels[i];
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



void mousePressed() {
  keyColor = get(mouseX, mouseY);
  keyR = (keyColor >> 16) & 0xFF;
  keyG = (keyColor >> 8) & 0xFF;
  keyB = keyColor & 0xFF;
}