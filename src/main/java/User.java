import java.util.Random;

public class User implements Knowledge,Health {
	public static void main(String[] arg)
	{
		User u = new User();
		SimNode.skillObject = u;
		StartSimulation.startSimulation();
		
	}

	@Override
	public float setKnowledge(int age) {
		// TODO Auto-generated method stub
		// knowledge = c*(1-pow(e,-kx))
		// c ~ N(23,1)
		// k = 0.055
		Random r = new Random();
		double randomNormal = r.nextGaussian();
		float c = (float) (randomNormal * 2 + 23);
		float knowledgeVar = (float) (c * (1 - Math.exp(-1 * (0.055) * age)));// + 2000;
		return knowledgeVar;
	}

	@Override
	public float setHealth(int age,int deathAge)
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
