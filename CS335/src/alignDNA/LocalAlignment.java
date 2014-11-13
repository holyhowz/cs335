package alignDNA;

import javax.swing.JTextField;
import javax.swing.text.Caret;

public class LocalAlignment {
	private int start1;
	private int start2;
	private int length;
	private String s1;
	private String s2;
	
	public LocalAlignment(String string1, String string2,
			int string1MatchStart, int string2MatchStart, int matchLength){
		int padding = string1MatchStart - string2MatchStart;
		if(padding > 0){
			for(int i = 0;i > padding;i++){
				s1+=" ";
			}
		}else{
			if(padding > 0){
				for(int i = 0;i < padding;i--){
					s2+=" ";
				}
			}
		}
		this.start1 = string1MatchStart;
		this.start2 = string2MatchStart;
		this.length = matchLength;
		s1 = string1;
		s2 = string2;
		return;
	}

	public void showAlignment(JTextField alignedString1Field,
			JTextField alignedString2Field) {
		alignedString1Field.setText(s1);
		alignedString2Field.setText(s2);

		Caret cursor1 = alignedString1Field.getCaret();
		Caret cursor2 = alignedString2Field.getCaret();
		
		cursor1.setVisible(true);
		cursor2.setVisible(true);
		
		cursor1.setDot(start1);
		cursor2.setDot(start2);
		
		cursor1.moveDot(length + start1);
		cursor2.moveDot(length + start2);
		
	}
}
