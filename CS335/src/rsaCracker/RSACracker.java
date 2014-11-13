package rsaCracker;

import java.math.BigInteger;

public class RSACracker {
	public static void main(String Args[]) {
		BigInteger[] cipherMessage = { new BigInteger("576322461849"),
				new BigInteger("122442824098"), new BigInteger("34359738368"),
				new BigInteger("29647771149"), new BigInteger("140835578744"),
				new BigInteger("546448062804"), new BigInteger("120078454173"),
				new BigInteger("42618442977") };
		BigInteger PQ = new BigInteger("608485549753");
		BigInteger E = new BigInteger("7");
		BigInteger P = crack(PQ);
		BigInteger Q = PQ.divide(P);
		BigInteger phiPQ = (P.subtract(BigInteger.ONE)).multiply(Q
				.subtract(BigInteger.ONE));
		BigInteger D = E.modInverse(phiPQ);

		System.out.println("P : " + P);
		System.out.println("Q : " + Q);
		System.out.println("PQ: " + PQ);
		System.out.println("E : " + E);
		System.out.println("D : " + D);

		decrypt(cipherMessage, D, PQ);
	}//

	public static BigInteger crack(BigInteger pQ) {
//for debug:System.out.println("PQ Value: " + pQ);
		BigInteger P = new BigInteger("3");
		while (P.compareTo(pQ.divide(new BigInteger("2"))) <= 0) {
			if (pQ.mod(P).equals(BigInteger.ZERO)) {
				return P;
			} else {
					P = P.add(BigInteger.ONE).add(BigInteger.ONE);
				}
//for debugSystem.out.println("Tried P Value: " + P);
		}
		return null;
	}

	public static void decrypt(BigInteger[] cipherMessage, BigInteger d,
			BigInteger pq) {
		String decodedMessage = "";
		for (int i = 0; i < cipherMessage.length; i++) {
			BigInteger c = cipherMessage[i];
			BigInteger m = c.modPow(d, pq);
			decodedMessage += (char) (m.intValue());
		}
		System.out.println("Decoded Message: \n" + decodedMessage);
	}
}
