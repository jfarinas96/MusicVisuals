package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Lights {

    JFVisual jf;
    
    float halfW, halfH;
    float x, y;
    float midTop, midBot;

    float[] lerpedBuffer;


    public Lights(JFVisual jf, float halfW, float halfH) {
        this.jf = jf;
        this.halfW = halfW;
        this.halfH = halfH;

        midTop = PApplet.map(1, 0, 2, halfW * 1.1f, halfW * 1.25f);
        midBot = PApplet.map(1, 0, 2, jf.width * 0.2f, halfW * 1.2f);
        x = 0;
        y = 0;

        lerpedBuffer = new float[jf.width];
    }

    void render() {
        northernLights();

        jf.colorMode(PConstants.RGB);
        jf.noStroke();
        jf.rectMode(PConstants.CORNER);

        // sand
        jf.fill(85, 74, 41);
        jf.rect(0, halfH, jf.width, halfH);

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

            x -= 2 * 5;
            y += 2.5f * 5;
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
        if (jf.getAudioPlayer().isPlaying() && y > halfH * 0.5f) {
            newCheck = 1;
        }

        return newCheck;
    }

    void northernLights() {
        jf.colorMode(PConstants.HSB);
        jf.strokeWeight(1);
        float c1 = PApplet.map(80, 0, 360, 0, 255);
        float c2 = PApplet.map(280, 0, 360, 0, 255);
        float c3 = PApplet.map(230, 0, 360, 0, 255);
        float c4 = PApplet.map(300, 0, 360, 0, 255);

        jf.calculateAverageAmplitude();
        float c = PApplet.map(jf.getSmoothedAmplitude(), 0, 1, c3, c4);

        for (int i = (int) halfH; i > 0; i--) {
            jf.stroke(c, i * c * jf.getSmoothedAmplitude() * 1.5f, i * jf.getSmoothedAmplitude() * 2.0f);
            jf.line(0, i, jf.width, i);
        }

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
