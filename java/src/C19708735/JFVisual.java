package C19708735;

import ie.tudublin.Visual;

public class JFVisual extends Visual {

    Car car;
    Wheel wheel;
    Lights lights;

    int scene = 1;
    boolean day = true;
    boolean visited = false;

    public void keyPressed()
    {
        if (keyCode == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();

            // reset
            day = true;
            visited = false;
            car.reset();
        }

        if (keyCode >= '1' && keyCode <= '3') {
            scene = keyCode - '0';
        }
    
        if (keyCode == '1')
        {
            visited = true;
        }
        
        if (keyCode == '3')
        {
            if (getAudioPlayer().isPlaying()) {
                day = false;
            }
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
        lights = new Lights(this, width / 2, height / 2);
    }

    public void draw() {
        background(0);

        switch(scene) {
            case 1: {
                car.sky(day, scene, visited);
                car.render(day);
                
                break;
            }

            case 2: {
                wheel.render();

                break;
            }
            
            case 3: {
                car.sky(day, scene, visited);
                lights.render(day);
            }
        }
        
    }
}
