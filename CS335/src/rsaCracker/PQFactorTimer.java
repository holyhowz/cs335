package rsaCracker;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PQFactorTimer {
	public static void main(String[] args) {
		int BitLength = 14;
		BigInteger P = BigInteger.ZERO;
		boolean go = true;
		long milliSeconds = 0;
		long milliStart = 0;
		long milliEnd = 0;
		BigInteger PQ = BigInteger.ZERO;
		//Print Table Headers
		System.out.println("BitLength: \t Time: \n");
		//Get Data
		while(go){
			//Generate PQ of bitlength BitLength
			PQ = makeKeys(BitLength);
//for debug:System.out.println("Started Timer");
			//Crack, with timer
			milliStart = System.currentTimeMillis();
			P = RSACracker.crack(PQ);
			milliEnd = System.currentTimeMillis();
//for debug:System.out.println("Stopped Timer");
			if(P.equals(BigInteger.ZERO)){
				System.out.println("Something Broke at bitlength " + BitLength + ".");
			}
			//Calculate time taken
			milliSeconds = milliEnd - milliStart;
			//Print data
			System.out.println(BitLength + "\t" + milliSeconds + "\n");
			//Keep Going?
			if(milliSeconds >= 60000){//Quit
				go = false;
			} else { 				 //Increment BitLength
				BitLength++;
			}
		}
	}
	
	public static BigInteger makeKeys(int BitLength){
				SecureRandom r = new SecureRandom();
				BigInteger p = BigInteger.ZERO;
				BigInteger q = BigInteger.ZERO;
				while (p.equals(q)) {
					p = new BigInteger(BitLength / 2, 100, r);
					q = new BigInteger(BitLength / 2, 100, r);
				}
				return p.multiply(q);
	}

	
}