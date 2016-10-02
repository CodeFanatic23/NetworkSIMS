package main;

import java.util.Random;

//1) Born		0
//2)Toddler		1
//3)Adolescent	8
//4)Teenager	13
//5)Adult		21------>Marriage	25
//		|------->Children	27
//
//6)Middle age	35
//7)Old age	50
//8)Death		75

public class Normal {
	public static void main(String[] args)
	{
		Random r = new Random();
		double a = (r.nextGaussian() + 15)/2;
//		for (int i = 0; i < 1000; i++) 
//		{
//			a =  (int)(r.nextGaussian()*7 + 27) ;
//			System.out.println((int)(a)); 
//		}
		
//		a =  (float)(r.nextGaussian()*2 + 23) ;
//		for (int i = 0; i < 100; i++) {
//			float knowledge = (float)(a*(1-Math.exp(-1*(0.055)*i)));
//			if(i == 25)
//				System.out.println(knowledge);
//			//System.out.println(i);
//		}
		
		
		System.out.println( 0 + (int)(Math.random() * 100));
		
	}
}
