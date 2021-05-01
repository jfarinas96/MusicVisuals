package C19708735;

import processing.core.PApplet;
import processing.core.PConstants;

public class Wheel {

    JFVisual jf;

    float w, h;
    float x, y = 0;
    float rotation = 0;

    public Wheel(JFVisual jf, float w, float h) {
        this.jf = jf;
        this.w = w;
        this.h = h;
    }

    void drawWheel() {
    
        jf.rectMode(PConstants.CENTER);
        // road
        jf.noStroke();
        jf.fill(27, 25, 21);
        jf.rect(0, 0, w * 2, h * 2);

        jf.pushMatrix();
        jf.translate(x, y);

        // car
        jf.fill(169, 27, 13);
        jf.rect(0, 0, w * 2, h * 1.2f);

        // wheel
        jf.fill(0);
        jf.ellipse(w / 2, h / 2, 0.9f * h, 0.9f * h);

        jf.fill(169, 169, 169);
        jf.ellipse(w / 2, h / 2, 0.6f * h, 0.6f * h);

        jf.fill(128, 128, 128);
        jf.ellipse(w / 2, h / 2, 0.5f * h, 0.5f * h);

        jf.fill(169, 169, 169);
        jf.ellipse(w / 2, h / 2, 0.2f * h, 0.2f * h);

        int numLines = 5;
        float theta = PConstants.TWO_PI / (float) numLines;
        float radius = (0.6f * h) * 0.45f;

        jf.stroke(169, 169, 169);
        jf.strokeWeight(20);

        jf.pushMatrix();
        jf.translate(w / 2, h / 2);
        jf.rotate(rotation);

        for(int i = 0 ; i < numLines ; i ++)
        {
            float angle = theta * i;
            float x_pos = PApplet.sin(angle) * radius;
            float y_pos = PApplet.cos(angle) * radius;
            
            jf.line(0, 0, x_pos, y_pos);               
        }
        jf.popMatrix();
        jf.popMatrix();

        if (jf.getAudioPlayer().isPlaying() && (jf.frameCount % 10) == 0) {
            y += jf.random(-1, 1);
            
            if (y < -(0.05f * h)) {
                y += 2;
            }

            if (y > 0.05f * h) {
                y -= 2;
            }
        }

        jf.calculateAverageAmplitude();
        rotation -= jf.getAmplitude();
    }

}
