/****************************************************************************
 * StringRecursion.java
 *
 * Christopher Bartholomew
 *
 * Programming Problems for String Recursion
 *
 ***************************************************************************/

public class StringRecursion {

	/**
	 * main(String[] args)
	 * Purpose: test all the recursive string methods, includes lastIndexOf
	 */
	public static void main(String[] args) {
		// II.2.a Print with Spaces
		printWithSpaces("space");
		
		// II.2.b recursive variable weave
		System.out.println(weave("recurse","NOW"));
		
		// II.2.c get last index
		System.out.println(lastIndexOf('r',"recurse"));
		
	}
	/**
	 * printWithSpaces(String str)
	 * @param str : the string you want to print spaces
	 * Purpose: every other character in the string, should be a space - recurse.
	 */
	public static void printWithSpaces(String str){
		
		if(str == null || str.equals(""))
			return;
				
		System.out.print(str.charAt(0) + " ");
		
		printWithSpaces(str.substring(1));
		
	}
	/**
	 * String weave(String str1, String str2)
	 * @param str1 : first string
	 * @param str2 : second string
	 * @return String : the first string woven with the second string.
	 * purpose: to take in two strings - then weave them both together.
	 */
	public static String weave(String str1, String str2){
			
		// error handling so that no null values are provided
		if(str1 == null || str2 == null)
			throw new IllegalArgumentException("Parameter str1 and str2 need vaules");
		
		// temporary string to hold values
		String woven = "";
		
		// base case when to start returning
		if((str1 == null || str1.equals("")) && (str2 == null || str2.equals("")))
			return woven;
		
		// len larger than zero on str1? grab first char
		if(str1.length() > 0)
			woven = woven + str1.charAt(0); 
		
		// len larger than zero on str2? grab first char
		if(str2.length() > 0)
			woven = woven + str2.charAt(0);
		
		// only substring if we have room in the string: store in temp vars
		String str1_temp = (str1.length() > 0) ? str1.substring(1) : str1;
		String str2_temp = (str2.length() > 0) ? str2.substring(1) : str2;
		
		// recurse using the temp vars to ensure no zero bounds
		return woven + weave(str1_temp,str2_temp);
		
	}
	
	/**
	 * lastIndexOf(char ch, String str)
	 * @param ch - the char
	 * @param str - the string that you want to find this char
	 * @return: the index position of the string
	 */
	public static int lastIndexOf(char ch, String str){
		
		if(str == null || str.equals(""))
			return -1;
					
		if (str.length() == 0)
			return -1;
		
		int count = 0;
			
		if(str.charAt(0) == ch)
			return count + lastIndexOf(ch, str.substring(1));
		
		count++;
		
		return count + lastIndexOf(ch, str.substring(1));

	}
	

}
