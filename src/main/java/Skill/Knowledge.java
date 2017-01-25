package Skill;
import java.util.Random;

public interface Knowledge extends Skill {
	public default float setKnowledge(int age) {
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
}
