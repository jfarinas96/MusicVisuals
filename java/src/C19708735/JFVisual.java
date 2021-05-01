package C19708735;

import ie.tudublin.Visual;

public class JFVisual extends Visual {

    Car car;
    Wheel wheel;
    Lights lights1, lights2, lights3;

    int scene = 0;
    int ready = 0;
    int change = 1;

    public void keyPressed()
    {
        if (keyCode == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }

        if (keyCode >= '0' && keyCode <= '3') {
            scene = keyCode - '0';
        }
        
        if (keyCode == '0')
        {
            change = -change;
        }
    }

    public void settings() {
        size(1024, 500);
    }

    public void setup() {
        startMinim();
        loadAudio("crash-my-car.mp3");

        car = new Car(this, width / 2, height / 2);
        wheel = new Wheel(this, width / 2, height / 2);
        lights1 = new Lights(this, width / 2, height / 2);
        lights2 = new Lights(this, width / 2, height / 2);
    }

    public void draw() {
        background(0);

        switch(scene) {
            case 0: {
                car.sky(change);
                car.render();
                
                break;
            }

            case 1: {
                wheel.render();

                break;
            }
            
            case 2: {
                lights1.render();
                lights1.roadLines();

                ready = lights1.newLine();
                if (ready == 1)
                    lights2.roadLines();
                
                break;
            }
            
            case 3: {
                break;
            }
        }
        
    }
}
