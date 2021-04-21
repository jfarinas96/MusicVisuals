package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Car {

    JFVisual jf;

    float w, h;
    float x, y;
    
    public Car(JFVisual jf, float w, float h) {
        this.jf = jf;
        this.w = w;
        this.h = h;
    }

    void drawCar() {        
        float centreScreen = w / 2;
        float halfW = centreScreen * 0.45f;
        float leftCorner = centreScreen - halfW;
        float rightCorner = centreScreen + halfW;
        float middle = (h / 2) - 50;
        float incline = leftCorner * 0.2f;
        float outline = 10;
        float headlightsY = ((middle + incline) + (h * 0.75f)) / 2;

        jf.colorMode(PConstants.RGB);
        jf.noStroke();

        // sand
        jf.fill(85, 74, 41);
        jf.rectMode(PConstants.CORNER);
        jf.rect(0, middle + 20, w, h - middle - 20);

        // road
        jf.fill(27, 25, 21);
        jf.beginShape();
        jf.vertex(0, h);
        jf.vertex(leftCorner + 20, middle + 20);
        jf.vertex(rightCorner - 20, middle + 20);
        jf.vertex(w, h);
        jf.endShape();

        jf.translate(x, y);

        // wheels
        jf.fill(0);
        jf.rectMode(PConstants.CENTER);
        jf.rect(leftCorner + 10, h * 0.75f, incline + 20, incline * 0.8f);
        jf.rect(rightCorner - 10, h * 0.75f, incline + 20, incline * 0.8f);

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
        jf.vertex(leftCorner - incline, h * 0.75f);
        jf.vertex(rightCorner + incline, h * 0.75f);
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

        if (jf.getAudioPlayer().isPlaying() && (jf.frameCount % 30) == 0) {
            x += jf.random(-3, 3);
        }
    }

    void sky() {
        jf.colorMode(PConstants.HSB);

        jf.calculateAverageAmplitude();
        float c = PApplet.map(jf.getSmoothedAmplitude(), 0, 1, 20, 60);

        for (int i = (int) h / 2; i > 0; i--) {
            jf.stroke(c, i * c, i);
            jf.line(0, i, w, i);
        }
    }
}
