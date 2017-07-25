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
  
  void colorChange() {
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
  
  boolean isIllegal(float c) {
    if (c < 0 || c > 255) {
      return false;
    }
    return true;
  }
  
}