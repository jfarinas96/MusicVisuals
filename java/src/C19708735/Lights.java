package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Lights {

    JFVisual jf;
    
    float halfW, halfH;
    float x, y;
    float midTop, midBot;

    public Lights(JFVisual jf, float halfW, float halfH) {
        this.jf = jf;
        this.halfW = halfW;
        this.halfH = halfH;

        midTop = PApplet.map(1, 0, 2, halfW * 1.1f, halfW * 1.25f);
        midBot = PApplet.map(1, 0, 2, jf.width * 0.2f, halfW * 1.2f);
        x = 0;
        y = 0;
    }

    void render() {
        jf.noStroke();
        jf.rectMode(PConstants.CORNER);

        // sand
        jf.fill(85, 74, 41);
        jf.rect(0, halfH, jf.width * 2, halfH);

        // road
        jf.fill(27, 25, 21);
        jf.beginShape();
        jf.vertex(halfW * 1.1f, halfH);
        jf.vertex(halfW * 1.25f, halfH);
        jf.vertex(halfW * 1.2f, jf.height);
        jf.vertex(jf.width * 0.2f, jf.height);
        jf.endShape();
    }

    void roadLines() {
        jf.pushMatrix();
        jf.translate(x, y);

        jf.fill(255);
        jf.beginShape();
        jf.vertex(midTop * 0.99f + 2, halfH);
        jf.vertex(midTop * 1.01f - 2, halfH);
        jf.vertex(midTop * 0.96f, halfH * 1.15f);
        jf.vertex(midTop * 0.94f, halfH * 1.15f);
        jf.endShape();

        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying()) {
            moveRoadLines();
            newLine();
        }
    }

    void moveRoadLines() {
        if (x > -(midTop - midBot) && y < halfH) {

            jf.calculateAverageAmplitude();
            x -= jf.getSmoothedAmplitude() * 2 * 10;
            y += jf.getSmoothedAmplitude() * 2.5f * 10;
        }
        else {
            respawnLine();
        }
    }

    void respawnLine() {
        x = 0;
        y = 0;
    }

    int newCheck = 0;

    int newLine() {
        if (jf.getAudioPlayer().isPlaying() && y > halfH * 0.48f) {
            newCheck = 1;
        }

        return newCheck;
    }

    void northernLights() {

    }
    
}
