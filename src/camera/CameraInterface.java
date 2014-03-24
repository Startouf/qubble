package camera;
/**
 * La caméra|Detection du mouvement doivent utiliser ces méthodes de QubbleInterface :
 * 		QubjectDetected(int ID, cameraPos pos)
 * 		QubjectHasMoved(int ID, cameraPos pos)
 * 		QubjectRemoved(int ID)
 * 		QubjectHasTurned(int ID, float dR)
 * @author Cyril
 */
public interface CameraInterface
{
	/**
	 * La référence sur la caméra sera supprimée après l'éxécution de cette méthode.
	 * Il faut désallouer toute mémoire non gérée par le Garbage Collector de la Virtual Machine
	 */
	public void terminate();
}
