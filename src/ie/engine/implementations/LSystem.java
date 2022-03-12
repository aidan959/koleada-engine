package ie.engine.implementations;
import processing.core.PApplet;
class LSystem 
{
  int steps = 0;

  String axiom;
  String rule;
  String production;
  PApplet pa;
  float startLength;
  float drawLength;
  float theta;

  int generations;

  public LSystem(PApplet pa) {
    axiom = "F";
    rule = "F+F-F";
    startLength = 190.0f;
    this.pa = pa;
    theta = PApplet.radians(120.0f);
    reset();
  }

  public void reset() {
    production = axiom;
    drawLength = startLength;
    generations = 0;
  }

  public int getAge() {
    return generations;
  }

  public void render() {
    pa.translate(pa.width/2f, pa.height/2f);
    steps += 5;          
    if (steps > production.length()) {
      steps = production.length();
    }
    for (int i = 0; i < steps; i++) {
      char step = production.charAt(i);
      if (step == 'F') {
        pa.rect(0, 0, -drawLength, -drawLength);
        pa.noFill();
        pa.translate(0, -drawLength);
      } 
      else if (step == '+') {
        pa.rotate(theta);
      } 
      else if (step == '-') {
        pa.rotate(-theta);
      } 
      else if (step == '[') {
        pa.pushMatrix();
      } 
      else if (step == ']') {
        pa.popMatrix();            
      }
    }
  }

  public void simulate(int gen) {
    while (getAge() < gen) {
      production = iterate(production, rule);
    }
  }

  public String iterate(String prod_, String rule_) {
    drawLength = drawLength * 0.6f;
    generations++;
    String newProduction = prod_;          
    newProduction = newProduction.replaceAll("F", rule_);
    return newProduction;
  }
}