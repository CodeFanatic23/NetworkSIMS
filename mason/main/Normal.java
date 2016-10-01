package main;

import java.util.Random;

public class Normal {
	public static void main(String[] args)
	{
		Random r = new Random();
		double a = (r.nextGaussian() + 15)/2;
		for (int i = 0; i < 100; i++) {
			a = (int) (r.nextGaussian()*1 + 3);
			System.out.println((int)(a)); 
		}
		
	}
}
