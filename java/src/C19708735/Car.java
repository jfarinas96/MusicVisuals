package C19708735;

import processing.core.PConstants;

public class Car {

    JFVisual jf;

    float w, h;
    
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

        jf.noStroke();
        jf.fill(255);

        // top section of car
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

        jf.rectMode(PConstants.CENTER);

        // wheels
        jf.rect(leftCorner + 10, h * 0.75f, incline + 20, incline * 0.8f);
        jf.rect(rightCorner - 10, h * 0.75f, incline + 20, incline * 0.8f);
        
        jf.fill(0);

        // windshield
        jf.beginShape();
        jf.vertex(leftCorner + outline, middle);
        jf.vertex(leftCorner + incline + outline * 0.8f, middle * 0.45f + outline);
        jf.vertex(rightCorner - incline - outline * 0.8f, middle * 0.45f + outline);
        jf.vertex(rightCorner - outline, middle);
        jf.endShape();

        jf.fill(255, 255, 0);

        //headlights
        jf.ellipse(leftCorner + 10, headlightsY - 10, incline + 12, incline + 12);
        jf.ellipse(rightCorner - 10, headlightsY - 10, incline + 12, incline + 12);
    }
}
