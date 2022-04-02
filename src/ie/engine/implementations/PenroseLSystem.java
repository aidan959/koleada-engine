package ie.engine.implementations;

import ie.engine.maths.Coordinate;
import processing.core.PApplet;

public class PenroseLSystem extends  LSystem{
  int steps = 0;
  float somestep = 0.1f;
  String ruleW;
  String ruleX;
  String ruleY;
  String ruleZ;

  Coordinate position;
  Coordinate rotation;
  public PenroseLSystem(PApplet pa, Coordinate startPos, Coordinate startAngle) {
    super(pa);
    axiom = "[X]++[X]++[X]++[X]++[X]";
    ruleW = "YF++ZF4-XF[-YF4-WF]++";
    ruleX = "+YF--ZF[3-WF--XF]+";
    ruleY = "-WF++XF[+++YF++ZF]-";
    ruleZ = "--YF++++WF[+ZF++++XF]--XF";
    startLength = 460.0f;
    theta = PApplet.radians(36);  
    position = startPos;
    rotation = startAngle;
    reset();
  }

  public void useRule(String r) {
    rule = r;
  }

  public void useAxiom(String a) {
    axiom = a;
  }

  public void useLength(float l) {
    startLength = l;
  }

  public void useTheta(float t) {
    theta = PApplet.radians(t);
  }
  @Override
  public void reset() {
    production = axiom;
    drawLength = startLength;
    generations = 0;
  }
  @Override
  public int getAge() {
    return generations;
  }
  @Override
  public void render() {
    pa.pushMatrix();
    pa.translate(position.x, position.y, position.z);
    pa.rotateX(rotation.x);
    pa.rotateY(rotation.y);
    pa.rotateZ(rotation.z);

    int pushes = 0;
    int repeats = 1;
    steps += 12;          
    if (steps > production.length()) {
      steps = production.length();
    }

    for (int i = 0; i < steps; i++) {
      char step = production.charAt(i);
      if (step == 'F') {
        pa.stroke(255, 60);
        for (int j = 0; j < repeats; j++) {
          pa.line(0, 0, 0, -drawLength);
          pa.noFill();
          pa.translate(0, -drawLength);
        }
        repeats = 1;
      } 
      else if (step == '+') {
        for (int j = 0; j < repeats; j++) {
          pa.rotate(theta);
        }
        repeats = 1;
      } 
      else if (step == '-') {
        for (int j =0; j < repeats; j++) {
          pa.rotate(-theta);
        }
        repeats = 1;
      } 
      else if (step == '[') {
        pushes++;            
        pa.pushMatrix();
      } 
      else if (step == ']') {
        pa.popMatrix();
        pushes--;
      } 
      else if ( (step >= 48) && (step <= 57) ) {
        repeats = (int)step - 48;
      }
    }

    // Unpush if we need too
    while (pushes > 0) {
      pa.popMatrix();
      pushes--;
    }
    pa.popMatrix();

  }
  @Override
  public String iterate(String prod_, String rule_) {
    String newProduction = "";
    for (int i = 0; i < prod_.length(); i++) {
      char step = production.charAt(i);
      if (step == 'W') {
        newProduction = newProduction + ruleW;
      } 
      else if (step == 'X') {
        newProduction = newProduction + ruleX;
      }
      else if (step == 'Y') {
        newProduction = newProduction + ruleY;
      }  
      else if (step == 'Z') {
        newProduction = newProduction + ruleZ;
      } 
      else {
        if (step != 'F') {
          newProduction = newProduction + step;
        }
      }
    }

    drawLength = drawLength * 0.5f;
    generations++;
    return newProduction;
  }
}