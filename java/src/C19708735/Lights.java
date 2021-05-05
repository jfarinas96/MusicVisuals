package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Lights {

    JFVisual jf;
    
    float halfW, halfH;
    float x1, y1;
    float x2, y2;
    float midTop, midBot;

    float[] lerpedBuffer;


    public Lights(JFVisual jf, float halfW, float halfH) {
        this.jf = jf;
        this.halfW = halfW;
        this.halfH = halfH;

        midTop = PApplet.map(1, 0, 2, halfW * 1.1f, halfW * 1.25f);
        midBot = PApplet.map(1, 0, 2, jf.width * 0.2f, halfW * 1.2f);

        lerpedBuffer = new float[jf.width];
    }

    void render(boolean day) {
        if (!day)
            northernLights();

        jf.colorMode(PConstants.RGB);
        jf.noStroke();
        jf.rectMode(PConstants.CORNER);

        // sand
        jf.fill(118, 95, 60);
        jf.rect(0, halfH, jf.width, halfH);

        // road
        jf.fill(27, 25, 21);
        jf.beginShape();
        jf.vertex(halfW * 1.1f, halfH);
        jf.vertex(halfW * 1.25f, halfH);
        jf.vertex(halfW * 1.2f, jf.height);
        jf.vertex(jf.width * 0.2f, jf.height);
        jf.endShape();

        roadLines();

        // mountains
        /*
        jf.fill(12, 20, 25);
        jf.beginShape();
        jf.vertex(0, halfH);
        jf.vertex(0, halfH * 0.95f);
        jf.vertex(jf.width * 0.18f, halfH * 0.65f);
        jf.vertex(jf.width * 0.2f, halfH * 0.69f);
        jf.vertex(jf.width * 0.28f, halfH * 0.55f);
        jf.vertex(jf.width * 0.3f, halfH * 0.6f);
        jf.vertex(jf.width * 0.31f, halfH * 0.59f);
        jf.vertex(jf.width * 0.58f, halfH);
        jf.endShape();

        jf.beginShape();
        jf.fill(27, 42, 51);
        jf.vertex(jf.width * 0.64f, halfH);
        jf.vertex(jf.width * 0.78f, halfH * 0.66f);
        jf.vertex(jf.width * 0.79f, halfH * 0.66f);
        jf.vertex(jf.width * 0.82f, halfH * 0.6f);
        jf.vertex(jf.width * 0.88f, halfH * 0.72f);
        jf.vertex(jf.width * 0.92f, halfH * 0.74f);
        jf.vertex(jf.width, halfH * 0.88f);
        jf.vertex(jf.width, halfH);
        jf.endShape();
        */

        jf.fill(27, 42, 51);
        jf.beginShape();
        jf.vertex(0, halfH);
        jf.vertex(0, halfH * 0.95f);
        jf.vertex(jf.width * 0.18f, halfH * 0.75f);
        jf.vertex(jf.width * 0.2f, halfH * 0.79f);
        jf.vertex(jf.width * 0.28f, halfH * 0.65f);
        jf.vertex(jf.width * 0.3f, halfH * 0.7f);
        jf.vertex(jf.width * 0.31f, halfH * 0.69f);
        jf.vertex(jf.width * 0.58f, halfH);
        jf.endShape();

        jf.beginShape();
        jf.fill(44, 64, 76);
        jf.vertex(jf.width * 0.45f, halfH);
        jf.vertex(jf.width * 0.55f, halfH * 0.7f);
        jf.vertex(jf.width * 0.62f, halfH * 0.8f);
        jf.vertex(jf.width * 0.65f, halfH * 0.79f);
        jf.vertex(jf.width * 0.78f, halfH * 0.9f);
        jf.vertex(jf.width * 0.82f, halfH * 0.88f);
        jf.vertex(jf.width, halfH * 0.98f);
        jf.vertex(jf.width, halfH);
        jf.endShape();
    }

    int newLine = 0;

    void roadLines() {
        jf.pushMatrix();
        jf.translate(x1 + (2 * 15), y1 - (2.5f * 15));

        jf.fill(255);
        jf.beginShape();
        jf.vertex(midTop * 0.99f + 2, halfH);
        jf.vertex(midTop * 1.01f - 2, halfH);
        jf.vertex(midTop * 0.96f, halfH * 1.15f);
        jf.vertex(midTop * 0.94f, halfH * 1.15f);
        jf.endShape();

        jf.popMatrix();

        if (y1 > (halfH + (2.5f * 15)) * 0.5f)
            newLine = 1;

        if (newLine == 1) {
            jf.pushMatrix();
            jf.translate(x2 + (2 * 15), y2 - (2.5f * 15));

            jf.fill(255);
            jf.beginShape();
            jf.vertex(midTop * 0.99f + 2, halfH);
            jf.vertex(midTop * 1.01f - 2, halfH);
            jf.vertex(midTop * 0.96f, halfH * 1.15f);
            jf.vertex(midTop * 0.94f, halfH * 1.15f);
            jf.endShape();

            jf.popMatrix();
        }

        
        if (jf.getAudioPlayer().isPlaying()) {
            moveRoadLines();
        }
    }

    void moveRoadLines() {
        if (x1 > -midBot - (2 * 15) && y1 < halfH + (2.5f * 15)) {
            x1 -= 2 * 6;
            y1 += 2.5 * 6;
        }
        else {
            respawnLine(0);
        }

        if (newLine == 1 && x2 > -midBot -(2 * 15) && y2 < halfH + (2.5f * 15)) {
            x2 -= 2 * 6;
            y2 += 2.5f * 6;
        }
        else {
            respawnLine(1);
        }
    }

    void respawnLine(int line) {
        if (line == 0) {
            x1 = 0;
            y1 = 0;
        }

        if (line == 1) {
            x2 = 0;
            y2 = 0;
        }
    }

    void northernLights() {
        jf.colorMode(PConstants.HSB);
        jf.strokeWeight(1);
        float c1 = PApplet.map(80, 0, 360, 0, 255);
        float c2 = PApplet.map(280, 0, 360, 0, 255);

        //for(int j = 0 ; j < jf.getBands().length ; j++)
        //{
          //  float startY = PApplet.map(jf.getSmoothedBands()[j], 0, jf.getBands().length, 0, halfH);

            for(int i = 0; i < jf.getAudioBuffer().size(); i ++)
            {
                lerpedBuffer[i] = PApplet.lerp(lerpedBuffer[i], jf.getAudioBuffer().get(i), 0.01f);
                
                jf.stroke(
                    PApplet.map(halfH * 0.5f + lerpedBuffer[i] * halfH * 0.5f * 10, 0, halfH, c2, c1)
                    , 255
                    , 255
                );
                
                //line(i, halfHeight - lerpedBuffer[i] * halfHeight * 4, i, halfHeight + lerpedBuffer[i] * halfHeight * 4);
                jf.line(i, halfH * 0.25f + lerpedBuffer[i] * halfH * 0.5f * 10, i, halfH * 0.75f + lerpedBuffer[i] * halfH * 0.5f * 10);
            }
        //}
    }
    
}
