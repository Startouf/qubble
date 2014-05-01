package main;

import imageObject.Point;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * La caméra|Detection du mouvement doivent utiliser ces méthodes de QubbleInterface :
 * 		QubjectDetected(int ID, cameraPos pos)
 * 		QubjectHasMoved(int ID, cameraPos pos)
 * 		QubjectRemoved(int ID)
 * 		QubjectHasTurned(int ID, float dR)
 * @author Cyril
 */
public interface ImageDetectionInterface{

	/**
	 * Ajouter la dernière image reçue par la caméra
	 */
	public void setImage(BufferedImage newImage);
	
	/**
	 * Accède à la dernière image en mémoire
	 * @return
	 */
	public BufferedImage getLastImage();
	
	/**
	 * Permet au processus de reconnaissance de QR code qu'une nouvelle image non analysée est diponible
	 * @return
	 */
	public boolean isNewImageQR();
	
	/**
	 * Permet au processus de détection du mouvement qu'une nouvelle image non analysée est diponible
	 * @return
	 */
	
	public boolean isNewImageMotion();
	
	/**
	 * Lorsqu'une détection est terminée, prévenir l'objet Qubble des changements
	 */
	public void qrDetectionDone();
	
	/**
	 * Lorsqu'une estimation du mouvement est terminée, prévenir l'objet Qubble des déplacements
	 */
	public void motionEstimationDone();
	
	public void setQrDetectionDone(boolean qrDetectionDone);
	
	public void setMotionEstimationDone(boolean motionEstimationDone);
		
	public HashMap<Integer, Point> getRemovedQubbleList();
	public HashMap<Integer, Point> getAddedQubbleList();
	public void resetRemovedQubbleList();
	public void resetAddedQubbleList();
	
	public int getWidthCamera();
	public int getHeightCamera();
	
	/**
	 * Play/Pause de la caméra
	 */
	public void switchCamera();
	
	/**
	 * Play/Pause de la détection de QR
	 */
	public void switchDetection();
	
	/**
	 * Play/Pause de la détection de mouvement
	 */
	public void switchMotion();
		
}
