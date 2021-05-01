package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Car {

    JFVisual jf;

    float halfW, halfH;
    float x, y;
    float x1, y1;
    
    public Car(JFVisual jf, float w, float h) {
        this.jf = jf;
        this.halfW = w;
        this.halfH = h;
    }

    void render() {        

        float centreScreen = halfW;
        float halfCar = centreScreen * 0.45f;
        float leftCorner = centreScreen - halfCar;
        float rightCorner = centreScreen + halfCar;
        float middle = (halfH) - 50;
        float incline = leftCorner * 0.2f;
        float outline = 10;
        float headlightsY = ((middle + incline) + (jf.height * 0.75f)) / 2;

        jf.colorMode(PConstants.RGB);
        jf.noStroke();

        // sand
        jf.fill(85, 74, 41);
        jf.rectMode(PConstants.CORNER);
        jf.rect(0, middle + 20, jf.width, jf.height - middle - 20);

        // road
        jf.fill(27, 25, 21);
        jf.beginShape();
        jf.vertex(0, jf.height);
        jf.vertex(leftCorner + 20, middle + 20);
        jf.vertex(rightCorner - 20, middle + 20);
        jf.vertex(jf.width, jf.height);
        jf.endShape();

        jf.pushMatrix();
        jf.translate(x, y);

        // wheels
        jf.fill(0);
        jf.rectMode(PConstants.CENTER);
        jf.rect(leftCorner + 10, jf.height * 0.75f, incline + 20, incline * 0.8f);
        jf.rect(rightCorner - 10, jf.height * 0.75f, incline + 20, incline * 0.8f);

        roadLines();

        // top section of car
        jf.fill(169, 27, 13);
        jf.beginShape();
        jf.vertex(leftCorner, middle);
        jf.vertex(leftCorner + incline, middle * 0.45f);
        jf.vertex(rightCorner - incline, middle * 0.45f);
        jf.vertex(rightCorner, middle);
        jf.endShape();

        // bottom section of car
        jf.beginShape();
        jf.vertex(leftCorner, middle);
        jf.vertex(leftCorner - incline, middle + incline);
        jf.vertex(leftCorner - incline, jf.height * 0.75f);
        jf.vertex(rightCorner + incline, jf.height * 0.75f);
        jf.vertex(rightCorner + incline, middle + incline);
        jf.vertex(rightCorner, middle);
        jf.endShape();

        // windshield
        jf.fill(0);
        jf.beginShape();
        jf.vertex(leftCorner + outline, middle);
        jf.vertex(leftCorner + incline + outline * 0.8f, middle * 0.45f + outline);
        jf.vertex(rightCorner - incline - outline * 0.8f, middle * 0.45f + outline);
        jf.vertex(rightCorner - outline, middle);
        jf.endShape();

        //headlights
        jf.fill(225, 173, 1);
        jf.ellipse(leftCorner + 10, headlightsY - 10, incline + 12, incline + 12);
        jf.ellipse(rightCorner - 10, headlightsY - 10, incline + 12, incline + 12);
        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying() && (jf.frameCount % 30) == 0) {
            x += jf.random(-3, 3);
        }
    }

    void sky(int change) {
        jf.colorMode(PConstants.HSB);
        float c, c1, c2;
        float b;

        jf.calculateAverageAmplitude();

        if (change == 1) {
            c1 = PApplet.map(0, 0, 360, 0, 255);
            c2 = PApplet.map(40, 0, 360, 0, 255);
            c = PApplet.map(jf.getSmoothedAmplitude(), 0, 1, c2, c1);
            b = jf.getSmoothedAmplitude() * 2.0f;
        } 
        else {
            c1 = PApplet.map(230, 0, 360, 0, 255);
            c2 = PApplet.map(300, 0, 360, 0, 255);
            c = PApplet.map(jf.getSmoothedAmplitude(), 0, 1, c1, c2);
            b = jf.getSmoothedAmplitude();
        }
        
        for (int i = 0; i < (int) halfH; i++) {
            jf.stroke(c, i * c, i * b);
            jf.line(0, i, jf.width, i);            
        }
    }

    void roadLines() {
        jf.pushMatrix();
        jf.translate(x1, y1);

        jf.fill(255);
        jf.beginShape();
        jf.vertex((halfW) * 0.98f, jf.height);
        jf.vertex((halfW) * 1.02f, jf.height);
        jf.vertex((halfW) * 1.03f, jf.height * 1.2f);
        jf.vertex((halfW) * 0.97f, jf.height * 1.2f);
        jf.endShape();

        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying()) {
            moveRoadLines();
        }
    }

    void moveRoadLines() {
        if (y1 > -((halfH) + 100)) {

            y1 -= 2.5f * 5;
        }
        else {
            respawnLine();
        }
    }

    void respawnLine() {
        y1 = 0;
    }
}
