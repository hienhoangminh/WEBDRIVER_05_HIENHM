package java_01_basics;

public class Java_05_String {
		
	public static void main(String[] args) {
		
		StringOperator so = new StringOperator();
		
		String automation = "Automation 01 Testing 345 Tutorials Online 123456";
		char characterToCheck = 'a';
		String substringToContains = "Testing";
		String startWithString = "Automation";
		String endWithString = "Online";
		String substringInString = "Tutorials";
        String originalSubString = "Online";
        String newSubString = "Offline";

        // From 1 to 5
		System.out.println("Number of character " + characterToCheck + " in String " + automation + " is " + so.countCharInString(characterToCheck, automation));
		System.out.println("Did the String " + automation + " contains " + substringToContains + " ? Response: " + so.isExisted(substringToContains, automation));
		System.out.println("Did the String " + automation + " start with " + startWithString + " ? Response: "  + so.isStartedWithString(startWithString, automation));
		System.out.println("Did the String " + automation + " end with " + endWithString + " ? Response: "  + so.isEndedWithString(endWithString, automation));
		System.out.println("Position of sub-string " + substringInString + " in string " + automation + " is "  + so.getPositionOfString(substringInString, automation));
		
		// 6 - Replace string
		String newString = so.replaceString(automation, originalSubString, newSubString);
		
		System.out.println("New string after replacing substring " + originalSubString + " by substring " + newSubString + " in string " + automation + " is "  + 
		    newString);
		
		// 7 - Count number of digits
		System.out.println("Number of digits in string " + automation + " is " + so.countNoOfDigits(automation));

	}
	
	
	
   
}
