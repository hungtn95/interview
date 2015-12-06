package com.payit.problems;

import java.util.List;

/**
 * Complete the method below.
 * This method will receive a list (as you can see below).
 * The method should iterate over the list.
 * For most indexes you should put the index value into the list at that index.
 * For indexes that are multiples of three, the value should be "Fizz".
 * For indexes that are multiples of five, the value should be "Buzz".
 * For indexes that are multiples of both three and five, the value should be "FizzBuzz".
 */
public class Problem1 {


    public List<String> fizzBuzz(List<String> fizBuzz){
    	for (int i = 0; i < fizBuzz.size(); i++) {
    		if (i % 15 == 0) {
    			fizBuzz.set(i, "FizzBuzz");
    		} else if (i % 3 == 0) {
    			fizBuzz.set(i, "Fizz");
    		} else if (i % 5 == 0) {
    			fizBuzz.set(i, "Buzz");
    		}
    	}
        return fizBuzz;
    }

}
