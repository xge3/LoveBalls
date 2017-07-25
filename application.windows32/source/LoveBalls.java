import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class LoveBalls extends PApplet {

ArrayList<Ball> balls = new ArrayList<Ball>();

int numOfBalls = 10;
int numOfBallsRemaining = numOfBalls;

int points;
int pointsSum;

int time;
float prevMillis;

PFont font;
public void setup() {
  
  background(0);
  
  frameRate(60);
  
  font = createFont("snap itc", 32, true);
  
  textFont(font);

  for (int i = 0; i < numOfBalls; i++) {
    balls.add(new Ball());
  }
}

public void draw() {
  background(0);

  if (millis() <= 5000) {
    fill(random(0, 255), 0, random(0, 255));
    textSize(24);

    text("Ready Go!", 180, 180);
    text("Click balls to get points!", 85, 220);
    text(4 - millis() / 1000, 240, 260);

    prevMillis = millis();
    
  } else {
    // Always - display balls
    for (int i = 0; i < numOfBalls; i++) {
      balls.get(i).drawBall();
    }

    // Always - display instruction
    fill(random(0, 255), random(0, 255), random(0, 255));
    textSize(14);
    text("Click balls to get points!", 150, 480);

    // Always - display points
    fill(255);
    textSize(24);
    text("Points: " + pointsSum, 20, 40);

    if (points > 0) {
      text("+ " + points, 400, 40);
    } else if (points == 0) {
      text("    0", 400, 40);
    } else {
      text(" - 10", 400, 40);
    }

    if (points >= 100) {
      fill(157, 102, 228);
      text("Perfect!", 370, 70);
      
    } else if (points >= 75) {
      fill(110, 192, 56);
      text("Good Job!", 355, 70);
      
    } else if (points >= 0) {
      fill(241, 209, 48);
      text("Come on!", 370, 70);
      
    } else {
      fill(255, 45, 33);
      text("Opps...", 400, 70);
    }

    // Always - display time
    if (numOfBallsRemaining > 0) {
      time = Math.max(30 - millis() / 1000 + 5, 0);
    }
    
    fill(255);
    stroke(255);
    ellipse(470, 470, 50, 50);

    fill(0);
    textSize(12);
    text("TIME", 452, 470);

    if (time >= 10) {
      text(time, 462, 485);
    } else {
      text(time, 466.7f, 485);
    }

    // display result
    if (numOfBallsRemaining == 0 || time <= 0) {
      fill(255);
      textSize(24);

      if (numOfBallsRemaining == 0) {
        if (pointsSum > 100 * numOfBalls) {
          text("CONGRATS! HIGH POINTS!", 60, 200);

          fill(random(0, 255), 0, random(0, 255));
          text("WE SUPER LOVE YOU!", 90, 240);
          
        } else {
          text("CONGRATS! WELL DONE!", 70, 200);

          fill(random(0, 255), 0, random(0, 255));
          text("WE LOVE YOU!", 140, 240);
        }
      } else {
        text("OPPS...TIME IS UP!", 110, 200);

        fill(random(0, 255), 0, random(0, 255));
        textSize(22);
        text("DON'T WORRY, WE STILL LOVE YOU!", 5, 240);
      }

      fill(0, random(0, 255), random(0, 255));
      textSize(16);
      text("@ BBing", 205, 280);
    }
  }
}

public void mousePressed() {
  if (numOfBallsRemaining > 0 && time > 0) {
    for (int i = numOfBalls - 1; i >= 0; i--) {
      points = balls.get(i).calculatePoints(mouseX, mouseY, millis(), prevMillis);

      if (points >= 0) {
        prevMillis = millis();
        numOfBallsRemaining--;
        break;
      }
    }
    
    pointsSum += points;
  }
  
}
class Ball {
  float d;
  float r;

  float x;
  float y;

  PVector speed;

  BallColor c = new BallColor();

  Ball() {
    d = 40;
    r = d / 2;
    
    x = random(r, width - r);
    y = random(r, height - r);

    speed = new PVector(random(-3, 3), random(-3, 3));
  }

  public void drawBall() {
    fill(c.red, c.blue, c.blue);
    stroke(c.red, c.blue, c.blue);

    moveBall();
    ellipse(x, y, d, d);

    fill(0);
    textSize(18);
    text("love", x - r, y + r / 3);
  }

  public void moveBall() {
    x += speed.x;
    y += speed.y;
    
    if (x < r || x + r > width) {
      speed.x = -speed.x;
    }
    
    if (y < r || y + r > height) {
      speed.y = -speed.y;
    }

    c.colorChange();
  }
  
  public int calculatePoints(float ms, float my, float currTime, float prevTime) {
    float distance = getDistance(ms, my);
    
    if (distance <= r) {
      int points = 10;
      
      float period =currTime - prevTime;
      int timeBonus = Math.max((int) (5000 - period) / 50, 0);
      int speedBonus = (int) (abs(speed.x) + abs(speed.y)) * 10;
      
      points += speedBonus + timeBonus;
      
      x = -100;
      y = -100;
      
      return points;
      
    } else {
      return -10;
    }
  }
  
  public float getDistance(float ms, float my) {
    return sqrt((x - ms) * (x - ms) + (y - my) * (y - my));
  }
  
}
class BallColor {
  float red;
  float green;
  float blue;
  
  PVector change;
  
  BallColor() {
    red = random(100, 255);
    green = random(100, 255);
    blue = random(100, 255);
    
    change = new PVector(random(-20, 20), random(-20, 20), random(-20, 20));
  }
  
  public void colorChange() {
    red += change.x;
    green += change.y;
    blue += change.z;
    
    if (!isIllegal(red)) {
      change.x = -change.x;
    }
    
    if (!isIllegal(green)) {
      change.y = -change.y;
    }
    
    if (!isIllegal(blue)) {
      change.z = -change.z;
    }
  }
  
  public boolean isIllegal(float c) {
    if (c < 0 || c > 255) {
      return false;
    }
    return true;
  }
  
}
  public void settings() {  size(500, 500);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LoveBalls" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
