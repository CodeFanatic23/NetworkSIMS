package Skill;
import java.util.Random;

public interface Health extends Skill {
	public default float setHealth(int age,int deathAge)
	{
		// health = a*age^2 + b*age (increase with age and then decreases)
		// k maximum health
		// t age at which it will have maximum health
		// d age at which it will die
		Random r = new Random();
		double randomNormal = r.nextGaussian();
		float k = (float) (randomNormal * 20 + 180);
		int t = (int) (randomNormal * 7 + 27);
		float a = k / (t * t - deathAge * t);
		float b = -k * deathAge / (t * t - deathAge * t);
		float healthVar = a * age * age+ b * age;
		return healthVar;
	}
}
