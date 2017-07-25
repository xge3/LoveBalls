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

  void drawBall() {
    fill(c.red, c.blue, c.blue);
    stroke(c.red, c.blue, c.blue);

    moveBall();
    ellipse(x, y, d, d);

    fill(0);
    textSize(18);
    text("love", x - r, y + r / 3);
  }

  void moveBall() {
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
  
  int calculatePoints(float ms, float my, float currTime, float prevTime) {
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
  
  float getDistance(float ms, float my) {
    return sqrt((x - ms) * (x - ms) + (y - my) * (y - my));
  }
  
}