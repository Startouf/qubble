package audio;

import java.io.File;

import javax.sound.sampled.*;

public class ExampleWAVReading
{
	public static void ReadWav(File file){
		try
	    {
	        final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));

	        clip.addLineListener(new LineListener()
	        {
	            @Override
	            public void update(LineEvent event)
	            {
	                if (event.getType() == LineEvent.Type.STOP)
	                    clip.close();
	            }
	        });

	        clip.open(AudioSystem.getAudioInputStream(file));
	        clip.start();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}
	
	public static void main(String[] args){
		ReadWav(new File("data/samples/bruits.wav"));
	}
}
