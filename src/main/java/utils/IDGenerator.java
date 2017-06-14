package utils;

import java.util.HashSet;
import java.util.Set;

public final class IDGenerator {
	private final static int 	MAX_ID 	= 2147483647;
	private static Set<Integer> ides 	= new HashSet<Integer>();
	private static int 			id;
	
	public static int getId(){
		do{
			id = (int)(Math.random() * MAX_ID);
		}while(ides.contains(id));
		
		ides.add(id);
		
		return id;
	}
}
