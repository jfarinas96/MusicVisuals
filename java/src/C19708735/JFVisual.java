package C19708735;

import ie.tudublin.Visual;

public class JFVisual extends Visual {

    Car car;

    int scene = 0;

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
    }

    public void settings() {
        size(1024, 500);
    }

    public void setup() {
        startMinim();
        loadAudio("crash-my-car.mp3");

        car = new Car(this, width, height);
    }

    public void draw() {
        background(0);

        switch(scene) {
            case 0: {
                if (getAudioPlayer().isPlaying()) {
                    car.moveCar();
                }
                
                car.blinking();
                car.sky();
                car.drawCar();
                
                break;
            }

            case 1: {
                break;
            }
            
            case 2: {
                break;
            }
            
            case 3: {
                break;
            }
        }
        
    }
}
