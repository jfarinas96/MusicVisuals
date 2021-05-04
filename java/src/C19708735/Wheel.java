package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Wheel {

    JFVisual jf;

    float halfW, halfH;
    float x, y = 0;
    float rotation = 0;

    public Wheel(JFVisual jf, float w, float h) {
        this.jf = jf;
        this.halfW = w;
        this.halfH = h;
    }

    void render() {
    
        jf.rectMode(PConstants.CORNER);
        
        // road
        jf.noStroke();
        jf.fill(27, 25, 21);
        jf.rect(0, 0, jf.width, jf.height);

        jf.pushMatrix();
        jf.translate(x, y);

        // car
        jf.fill(169, 0, 0);
        jf.rect(0, -halfH, jf.width, jf.height + (halfH * 0.2f));

        // wheel
        jf.fill(0);
        jf.ellipse(halfW, halfH, 0.9f * jf.height, 0.9f * jf.height);

        jf.fill(169, 169, 169);
        jf.ellipse(halfW, halfH, 0.6f * jf.height, 0.6f * jf.height);

        jf.fill(128, 128, 128);
        jf.ellipse(halfW, halfH, 0.5f * jf.height, 0.5f * jf.height);

        jf.fill(169, 169, 169);
        jf.ellipse(halfW, halfH, 0.2f * jf.height, 0.2f * jf.height);

        spinningWheel();

        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying() && (jf.frameCount % 10) == 0) {
            y += jf.random(-1, 1);
            
            if (y < -(0.05f * jf.height)) {
                y += 2;
            }

            if (y > 0.05f * jf.height) {
                y -= 2;
            }
        }
    }

    void spinningWheel() {

        int numLines = 5;
        float theta = PConstants.TWO_PI / (float) numLines;
        float radius = (0.6f * jf.height) * 0.45f;

        jf.stroke(169, 169, 169);
        jf.strokeWeight(20);

        jf.pushMatrix();
        jf.translate(halfW, halfH);
        jf.rotate(rotation);

        for(int i = 0 ; i < numLines ; i ++)
        {
            float angle = theta * i;
            float x_pos = PApplet.sin(angle) * radius;
            float y_pos = PApplet.cos(angle) * radius;
            
            jf.line(0, 0, x_pos, y_pos);               
        }
        jf.popMatrix();

        jf.calculateAverageAmplitude();
        rotation -= jf.getSmoothedAmplitude();

        jf.strokeWeight(1);
    }

}
