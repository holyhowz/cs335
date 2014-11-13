package alignDNA;

import javax.swing.JTextField;

public class Main {
	public static void main(String[] args) {
		java.util.Map<String, Integer> matchCost = new java.util.HashMap<String, Integer>();
		
		String s1 = new String("AGATGGAATGATTAG");
		String s2 = new String("CGACTATT");
		
		matchCost.put("AA", new Integer(2));
		matchCost.put("AC", new Integer(-2));
		matchCost.put("AG", new Integer(-2));
		matchCost.put("AT", new Integer(-2));
		matchCost.put("A-", new Integer(-1));

		matchCost.put("CA", new Integer(-2));
		matchCost.put("CC", new Integer(4));
		matchCost.put("CG", new Integer(-1));
		matchCost.put("CT", new Integer(-3));
		matchCost.put("C-", new Integer(-1));

		matchCost.put("GA", new Integer(-2));
		matchCost.put("GC", new Integer(-1));
		matchCost.put("GG", new Integer(3));
		matchCost.put("GT", new Integer(-3));
		matchCost.put("G-", new Integer(-1));

		matchCost.put("TA", new Integer(-2));
		matchCost.put("TC", new Integer(-3));
		matchCost.put("TG", new Integer(-3));
		matchCost.put("TT", new Integer(2));
		matchCost.put("T-", new Integer(-1));

		matchCost.put("-A", new Integer(-1));
		matchCost.put("-C", new Integer(-1));
		matchCost.put("-G", new Integer(-1));
		matchCost.put("-T", new Integer(-1));

		// Create a new DNA matcher with the specified cost matrix
		DNAMatcher matcher = new DNAMatcher(matchCost);
		
		LocalAlignment alignment = matcher.findLocalAlignment(s1,s2);
		JTextField t1 = new JTextField();
		JTextField t2 = new JTextField();
		alignment.showAlignment(t1, t2);
		AlignmentDisplay tester = new AlignmentDisplay(t1,t2);
	}
}
