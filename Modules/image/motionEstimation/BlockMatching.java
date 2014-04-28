package motionEstimation;

/**
 * Contient les paramètres pour effectuer une estimation du mouvement
 * @author masseran
 *
 */
public class BlockMatching {
	private int blockSizeRow, blockSizeCol,	search, fps;
	
	public BlockMatching(int blockSizeRow, int blockSizeCol, int search, int fps){
		this.blockSizeRow = blockSizeRow;
		this.blockSizeCol = blockSizeCol;
		this.search = search;
		this.fps = fps;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public int getBlockSizeRow() {
		return blockSizeRow;
	}

	public int getBlockSizeCol() {
		return blockSizeCol;
	}

	public int getSearch() {
		return search;
	}

}
