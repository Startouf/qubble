package main;
import imageObject.Point;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import qubject.MediaInterface;
import qubject.Qubject;
import audio.SampleControllerInterface;
import sequencer.QubbleInterface;


public class TestImageDetection implements QubbleInterface{
	
	public static void main(String[] args){
		ImageDetection imgD = new ImageDetection(new TestImageDetection());
		Thread t_imgD = new Thread(imgD);
		t_imgD.start();
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<Qubject> getAllQubjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Qubject> getQubjectsOnTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void QubjectDetected(int bitIdentifier, Point position) {
		System.out.println("Qubject " + bitIdentifier + " détecté en (" + position.getX() + "," +  position.getY() + ").");
	}

	@Override
	public void QubjectHasMoved(int bitIdentifier, Point position) {
		System.out.println("Qubject " + bitIdentifier + " déplacé en (" + position.getX() + "," +  position.getY() + ").");	
	}

	@Override
	public void QubjectHasTurned(int bitIdentifier, float dR) {
		System.out.println("Qubject " + bitIdentifier + " a pivoter de " + dR + "degrés.");
	}

	@Override
	public void QubjectRemoved(int bitIdentifier) {
		System.out.println("Qubject " + bitIdentifier + " a été enlevé.");
	}

	@Override
	public void playQubject(Qubject qubject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void soundHasFinishedPlaying(SampleControllerInterface sc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void toggleGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dimension getPosition(MediaInterface qubject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void panic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resynchronize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mute() {
		// TODO Auto-generated method stub
		
	}


}
