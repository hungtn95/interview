package com.payit.problems;

/**
 * For this problem you should complete the method below.
 * This method should determine if a string is a palindrome or not.
 *
 */
public class Problem2 {

    public boolean isPalindrome(String value){
    	for (int i = 0; i < value.length()/2; i++) {
    		if (value.charAt(i) != value.charAt(value.length()-1-i)) {
    			return false;
    		}
    	}
        return true;
    }
}
