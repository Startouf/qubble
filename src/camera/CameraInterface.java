package camera;

public interface CameraInterface
{
	/**
	 * La référence sur la caméra sera supprimée après l'éxécution de cette méthode.
	 * Il faut désallouer toute mémoire non gérée par le Garbage Collector de la Virtual Machine
	 */
	public void terminate();
}
