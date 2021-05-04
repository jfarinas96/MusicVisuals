package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Car {

    JFVisual jf;

    float halfW, halfH;
    float x, y;
    float roadX, roadY;
    float starsX;
    float[] randX, randY;
    float rotStars = 0;
    float rotSun = 0;
    float rotMoon = 0;
    int diameter;
    int numStars = 200;
    
    public Car(JFVisual jf, float w, float h) {
        this.jf = jf;
        this.halfW = w;
        this.halfH = h;

        randX = new float[numStars];
        randY = new float[numStars];

        for (int i = 0; i < numStars; i++) {
            randX[i] = jf.random(-halfW, jf.width * 2.0f);
            randY[i] = jf.random(0, halfH);
        }

        diameter = (int) (jf.height * 0.15f);
    }

    void render(int change, int scene) {        

        float centreScreen = halfW;
        float halfCar = centreScreen * 0.45f;
        float leftCorner = centreScreen - halfCar;
        float rightCorner = centreScreen + halfCar;
        float middle = (halfH) - 50;
        float incline = leftCorner * 0.2f;
        float outline = 10;
        float headlightsY = ((middle + incline) + (jf.height * 0.75f)) / 2;

        sky(change, scene);
        if (change != 1)
            moon();

        jf.colorMode(PConstants.RGB);
        jf.noStroke();

        // sand
        if (change == 1)
            jf.fill(145, 117, 74);
        else
            jf.fill(118, 95, 60);
        jf.rectMode(PConstants.CORNER);
        jf.rect(0, middle + 20, jf.width, jf.height - middle - 20);

        // road
        if (change == 1)
            jf.fill(37, 34, 30);
        else
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
        if (change == 1)
            jf.fill(169, 0, 0);
        else
            jf.fill(138, 3, 3);
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

            if (x < -(jf.width * 0.2f)) {
                y += 4;
            }

            if (x > jf.width * 0.2f) {
                y -= 4;
            }
        }
    }

    void sky(int change, int speed) {

        jf.colorMode(PConstants.HSB);
        float c, c1, c2;
        float b = 1;

        jf.calculateAverageAmplitude();

        if (change == 1) {
            c1 = PApplet.map(10, 0, 360, 0, 255);
            c2 = PApplet.map(40, 0, 360, 0, 255);
            c = PApplet.map(jf.getSmoothedAmplitude(), 0, 1, c2, c1);
        }
        else {
            c1 = PApplet.map(210, 0, 360, 0, 255);
            c2 = PApplet.map(270, 0, 360, 0, 255);
            c = PApplet.map(jf.getSmoothedAmplitude(), 0, 1, c1, c2);
            b = jf.getSmoothedAmplitude();
        }
        
        for (int i = 0; i < (int) halfH; i++) {
            if (change == 1) {
                jf.stroke(c, i + (200 * jf.getSmoothedAmplitude()), i);
            }
            else {
                jf.stroke(c, i * c, i * b);
            }

            jf.line(0, i, jf.width, i);
        }

        if (change != 1) {
            jf.rectMode(PConstants.CENTER);

            jf.fill(255);

            jf.pushMatrix();
            jf.translate(starsX, 0);
            
            for (int j = 0; j < numStars; j++) {
                jf.rect(randX[j], randY[j], 2, 2);
            }

            jf.popMatrix();

            if (jf.getAudioPlayer().isPlaying()) {
                starsX -= 0.1f * (speed + 1);
                rotMoon -= 0.01f * 0.012;
            }
        }
    }

    void sun() {

        jf.colorMode(PConstants.RGB);
        jf.noStroke();

        float cx = jf.width * 0.5f;
        float cy = jf.height * 0.8f;

        jf.pushMatrix();

        jf.translate(cx, cy);
        jf.rotate(rotSun);
        
        jf.fill(255, 255, 0);
        jf.ellipse(-cx + (jf.width * 0.2f), -cy + ((halfH - 50) * 0.5f), diameter, diameter);

        /*
        int g = 100;
        for (int r = diameter; r > 0; --r) {
            jf.fill(241, 241, g);
            jf.ellipse(-cx + (jf.width * 0.9f) * 1.07f, -cy + ((halfH - 50) + (diameter * 0.5f)) * 1.07f, r, r);
            g = g + 1 % 100;
        }
        */

        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying())
            rotSun -= 0.01f * 0.025;
    }

    void moon() {

        jf.colorMode(PConstants.RGB);
        jf.noStroke();

        float cx = jf.width * 0.5f;
        float cy = jf.height * 4.5f;
        
        jf.pushMatrix();
        jf.translate(cx, cy);
        jf.rotate(rotMoon);
        
        jf.fill(244, 241, 201);
        jf.ellipse(-cx + jf.width + (diameter * 0.5f), -cy + (halfH - 50) - (diameter * 0.75f), diameter, diameter);

        jf.popMatrix();
    }


    void roadLines() {
        jf.pushMatrix();
        jf.translate(roadX, roadY);

        jf.fill(255);
        jf.beginShape();
        jf.vertex((halfW) * 0.98f, (jf.height * 0.8f) - 10);
        jf.vertex((halfW) * 1.02f, (jf.height * 0.8f) - 10);
        jf.vertex((halfW) * 1.03f, jf.height - 10);
        jf.vertex((halfW) * 0.97f, jf.height - 10);
        jf.endShape();

        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying()) {
            moveRoadLines();
        }
    }

    void moveRoadLines() {
        if (roadY > -((halfH) + 40)) {

            roadY -= 2.5f * 5;
        }
        else {
            respawnLine();
        }
    }

    void respawnLine() {
        roadY = (jf.height * 0.2f) + 10;
    }
}
