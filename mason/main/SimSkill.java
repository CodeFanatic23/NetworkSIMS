package main;
import java.util.*;

public class SimSkill {

	private Map<String,Float> skillMap= new HashMap<String,Float>();
	public SimSkill(Map skillMap){
		this.setSkillMap(skillMap);
		}
	
	public void addSkill(String skill,Float val){
		this.skillMap.put(skill, val);
	}

	public Map<String,Float> getSkillMap() {
		return skillMap;
	}
	public void setSkillMap(Map<String,Float> skillMap) {
		this.skillMap = skillMap;
	}
}
