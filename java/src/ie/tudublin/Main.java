package ie.tudublin;

import C19708735.JFVisual;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new JFVisual());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();			
	}
}