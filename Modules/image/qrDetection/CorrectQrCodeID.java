package qrDetection;

/**
 * Analyse la valeur du QRcode
 * Permet de gérer les qrCodes double symétrique afin de leur associé une valeur
 * Reconnaitre le cube en cas d'erreur unique
 * @author masseran
 * 
 * Règle : 
 * # case noire du qrCode
 * ^ localisation d'une erreur
 */
public class CorrectQrCodeID {
	
	public static int correctQrCodeID(int qrID){
		
		int correctedID = qrID;
		
		/* QrCode : 11
		 * |#  |
		 * | # |
		 * |  #|
		 */
		if (qrID == 11 || qrID == 21){
			correctedID = 11;
		}
		
		/* QrCode : 10
		 * |#  |
		 * |   |
		 * |  #|
		 */
		if (qrID == 10 || qrID == 20){
			correctedID = 10;
		}
		
		/* QrCode : 190
		 * |# #|
		 * |# #|
		 * |# #|
		 */
		if (qrID == 190 || qrID == 350){
			correctedID = 190;
		}
		
		/* QrCode : 31
		 * |# #|
		 * | # |
		 * |# ^#|
		 */
		if (qrID == 29 || qrID == 27 || qrID == 23 || qrID == 15){
			correctedID = 31;
		}
		
		/* QrCode : 30
		 * |# #|
		 * |   |
		 * |# ^#|
		 */
		if (qrID == 28 || qrID == 26 || qrID == 22 || qrID == 14){
			correctedID = 30;
		}
		
		/* QrCode : 480
		 * | # |
		 * |# #|
		 * | # |
		 */
		
		/* QrCode : 481
		 * | # |
		 * |###|
		 * | # |
		 */
		
		/* QrCode : 0
		 * |   |
		 * |   |
		 * |   |
		 */
		
		/* QrCode : 1
		 * |   |
		 * | # |
		 * |   |
		 */
		
		/* QrCode : 510
		 * |###|
		 * |# ^#|
		 * |###|
		 */
		
		/* QrCode : 511
		 * |###|
		 * |###|
		 * |###|
		 */
		
		return correctedID;
	}
	

}
