package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Car {

    JFVisual jf;

    float halfW, halfH;
    float x, y;
    float roadX, roadY;
    float starsX, starsY;
    float sunY, moonY;
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
            randX[i] = jf.random(0, jf.width);
            randY[i] = jf.random(-(jf.height * 1.5f), halfH);
        }

        diameter = (int) (jf.height * 0.15f);
    }

    void render(boolean day, int scene, boolean visited) {        

        float centreScreen = halfW;
        float halfCar = centreScreen * 0.45f;
        float leftCorner = centreScreen - halfCar;
        float rightCorner = centreScreen + halfCar;
        float middle = (halfH) - 50;
        float incline = leftCorner * 0.2f;
        float outline = 10;
        float headlightsY = ((middle + incline) + (jf.height * 0.75f)) / 2;

        sky(day, scene, visited);
        if (day)
            sun();
        else
            moon();

        jf.colorMode(PConstants.RGB);
        jf.noStroke();

        // sand
        if (day)
            jf.fill(145, 117, 74);
        else
            jf.fill(118, 95, 60);
        jf.rectMode(PConstants.CORNER);
        jf.rect(0, middle + 20, jf.width, jf.height - middle - 20);

        // road
        if (day)
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

        jf.popMatrix();

        roadLines();

        jf.pushMatrix();
        jf.translate(x, y);

        // top section of car
        if (day)
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

        // front grill
        if (day) {
            jf.fill(175, 175, 175);
            jf.stroke(105, 105, 105);
        }
        else {
            jf.fill(114, 114, 114);
            jf.stroke(65, 65, 65);
        }
        jf.strokeWeight(2);
        jf.rect(halfW, headlightsY - 10, jf.width * 0.2f, incline - 10);

        float top = (headlightsY - 10) - ((incline - 10) / 2);
        float split = (incline - 10) / 4;
        for (int i = 1; i < 4; i++) {
            jf.line(jf.width * 0.4f, top + (split * i), jf.width * 0.6f, top + (split * i));
        }
        
        jf.strokeWeight(1);
        jf.noStroke();

        // headlights
        jf.fill(225, 173, 1);
        jf.ellipse(leftCorner + 20, headlightsY - 10, incline + 12 + 20, incline);
        jf.ellipse(rightCorner - 20, headlightsY - 10, incline + 12 + 20, incline);

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

    void sky(boolean day, int speed, boolean visited) {

        jf.colorMode(PConstants.HSB);
        float c, c1, c2;
        float b = 1;

        jf.calculateAverageAmplitude();

        if (day) {
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
            if (day) {
                jf.stroke(c, i + 50 + (10 * jf.getSmoothedAmplitude()), i);
            }
            else {
                jf.stroke(c, i * c, i * b);
            }

            jf.line(0, i, jf.width, i);
        }

        if (!day) {
            jf.rectMode(PConstants.CENTER);
            jf.fill(255);

            jf.pushMatrix();
            jf.translate(0, starsY);
            
            for (int j = 0; j < numStars; j++) {
                jf.rect(randX[j], randY[j], 2, 2);
            }

            jf.popMatrix();

            if (jf.getAudioPlayer().isPlaying()) {
                starsY += 0.1f * (speed + 1);
                if (visited)
                    moonY += 0.03f;
                    // rotMoon -= 0.01f * 0.013;
            }
        }
    }

    void sun() {

        jf.colorMode(PConstants.RGB);
        jf.noStroke();
        jf.fill(255, 255, 0);

        jf.pushMatrix();
        jf.translate(0, sunY);
        
        jf.ellipse(jf.width * 0.15f, (halfH - 50), diameter, diameter);

        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying())
            sunY += 0.035f;

        // rotating sun
        /*
        float cx = jf.width * 0.5f;
        float cy = jf.height * 0.8f;

        jf.pushMatrix();

        jf.translate(cx, cy);
        jf.rotate(rotSun);
        
        jf.ellipse(-cx + (jf.width * 0.2f), -cy + ((halfH - 50) * 0.5f), diameter, diameter);

        int g = 100;
        for (int r = diameter; r > 0; --r) {
            jf.fill(241, 241, g);
            jf.ellipse(-cx + (jf.width * 0.9f) * 1.07f, -cy + ((halfH - 50) + (diameter * 0.5f)) * 1.07f, r, r);
            g = g + 1 % 100;
        }
        
        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying())
            //rotSun -= 0.01f * 0.025;
        */
    }

    void moon() {

        jf.colorMode(PConstants.RGB);
        jf.noStroke();
        jf.fill(244, 241, 201);

        jf.pushMatrix();
        jf.translate(0, moonY);
        
        jf.ellipse(jf.width * 0.85f, (halfH - 30) * 0.33f, diameter, diameter);

        jf.popMatrix();

        // rotating moon
        /*
        float cx = jf.width * 0.5f;
        float cy = jf.height * 4.5f;
        
        jf.pushMatrix();
        jf.translate(cx, cy);
        jf.rotate(rotMoon);
        
        jf.ellipse(-cx + jf.width + (diameter * 0.5f), -cy + (halfH - 50) - (diameter * 0.75f), diameter, diameter);

        jf.popMatrix();
        */
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

    void reset() {
        sunY = 0;
        starsY = 0;
        moonY = 0;
    }
}
