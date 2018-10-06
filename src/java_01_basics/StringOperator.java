package java_01_basics;

public class StringOperator {

	public int countCharInString(char c,String s) {
		int count = 0;
		for(int i=0; i< s.length(); i++) {
			if(Character.toLowerCase(s.charAt(i)) == c) {
				count = count + 1;
			}
		}
		return count;
	}
	
	public boolean isExisted(String substring,String s) {
		boolean isExisted = false;
		
		if(s.contains(substring)) {
			isExisted = true;
		}
		  
		return isExisted;
	}

	public boolean isStartedWithString(String substring,String s) {
		boolean isStartedWith = false;
		
		if(s.startsWith(substring)) {
			isStartedWith = true;
		}
		  
		return isStartedWith;
	}
	
	public boolean isEndedWithString(String substring,String s) {
		boolean isEndedWith = false;
		
		if(s.endsWith(substring)) {
			isEndedWith = true;
		}
		  
		return isEndedWith;
	}
	
	public int getPositionOfString(String substring,String s) {
		int position = 0;
	
		position = s.indexOf(substring);
		
		return position;
	}

	
	public String replaceString(String s,String orginal,String nw) {
		String nwString = "";
		nwString = s.replace(orginal, nw);
		return nwString;
	}
	
	public int countNoOfDigits(String s) {
		int counter = 0;
		for(int i = 0; i < s.length() ; i++) {
			if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
				counter++;
			}
		}
		return counter;
	}
	
}
