ArrayList<Ball> balls = new ArrayList<Ball>();

int numOfBalls = 10;
int numOfBallsRemaining = numOfBalls;

int points;
int pointsSum;

int time;
float prevMillis;

PFont font;
void setup() {
  smooth();
  background(0);
  size(500, 500);
  frameRate(60);
  
  font = createFont("snap itc", 32, true);
  
  textFont(font);

  for (int i = 0; i < numOfBalls; i++) {
    balls.add(new Ball());
  }
}

void draw() {
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
      text(time, 466.7, 485);
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

void mousePressed() {
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