package alignDNA;

import java.util.Map;
import java.util.concurrent.Semaphore;

public class DNAMatcher {
	Map<String, Integer> matchCost;
	int[][] optimalCost;
	Semaphore[][] semaphores;
	

	public DNAMatcher(Map<String, Integer> matchCost) {
		this.matchCost = matchCost;
		return;
	}

	public class tableFiller implements Runnable {
		public void run(int i, int j, String string1, String string2) {
			try {
				if (i > 1 || j > 1) {
					semaphores[i - 1][j - 1].acquire();
					semaphores[i - 1][j].acquire();
					semaphores[i][j - 1].acquire();
				}
				optimalCost[i][j] = Math.max(Math.max(
						findMatchCost(string1.charAt(i - 1),
								string2.charAt(j - 1))
								+ optimalCost[i - 1][j - 1],
						findMatchCost(string1.charAt(i - 1), '-')
								+ optimalCost[i - 1][j]),
						findMatchCost('-', string2.charAt(j - 1))
								+ optimalCost[i][j - 1]);
				if (optimalCost[i][j] <= 0) {
					optimalCost[i][j] = 0;
				}
				semaphores[i][j].release(3);
			} catch (InterruptedException e) {
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
		}

	}

	public LocalAlignment findLocalAlignment(String string1, String string2) {
		fillsemaphores(string2, string1);
		fillTable(string2, string1);
//	    printTable(string2, string1); /*Used for Debug*/
		int[] end = findEnd();
		int[] start = findStart(end);
		
//		System.out.println(start[0] + "through" + end[0]);
//		System.out.println(start[1] + "through" + end[1]);
		return new LocalAlignment(string1, string2, start[0], start[1], end[0]-start[0]);
	}

	private void fillTable(String string1, String string2) {
		optimalCost = new int[string1.length() + 1][string2.length() + 1];
		for (int i = 1; i < string1.length() + 1; i++) {
			for (int j = 1; j < string2.length() + 1; j++) {
				new tableFiller().run(i, j, string1, string2);
			}
		}
	}

	// public int findOptimalCost(int i, int j, String string1, String string2)
	// {
	// return Math.max(Math.max(
	// findMatchCost(string1.charAt(i - 1),string2.charAt(j - 1))
	// + optimalCost[i - 1][j - 1],
	// findMatchCost(string1.charAt(i - 1), '-')
	// + optimalCost[i - 1][j]),
	// findMatchCost('-', string2.charAt(j - 1))
	// + optimalCost[i][j - 1]);
	// }

	private int findMatchCost(char a, char b) {
		String matchkey = new String("");
		matchkey += a;
		matchkey += b;
		return matchCost.get(matchkey);
	}

	private void fillsemaphores(String string1,String string2) {
		semaphores = new Semaphore [string1.length()+1][string2.length()+1];
		for (int i = 0; i < semaphores.length; i++) {
			for (int j = 0; j < semaphores[i].length; j++) {
				if (j == 0 || i == 0)
					semaphores[i][j] = new Semaphore(3);
				else
					semaphores[i][j] = new Semaphore(0);
			}
		}
	}

	private int [] findStart(int[] end) {
		for(int i = 0; i< optimalCost.length; i++){
			for(int j = 0; j < optimalCost[i].length;j++){
				if (optimalCost[i][j] > 0)
					return new int [] {i,j};
			}
		}
		return null;
	}

	private int [] findEnd() {
		int maxVal = 0;
		int [] coord = new int [2];
		for(int i = 0; i< optimalCost.length; i++){
			for(int j = 0; j < optimalCost[i].length;j++){
				if (optimalCost[i][j] > maxVal){
					maxVal = optimalCost[i][j];
					coord[0] = i;
					coord[1] = j;
				}
			}
		}
		return coord;
	}
	
//	  private void printTable(String string1, String string2) {//For Debug
//	  System.out.print("    e"); for (int j = 1; j < string2.length() + 1; j++)
//	  { System.out.printf("%4s",string2.charAt(j - 1)); } System.out.println();
//	  for (int i = 0; i < string1.length() + 1; i++) { if (i == 0)
//	  System.out.print("e"); else System.out.print(string1.charAt(i - 1)); for
//	  (int j = 0; j < string2.length() + 1; j++) { System.out.printf("%4d",
//	  optimalCost[i][j]); } System.out.print("\n"); } }
}
