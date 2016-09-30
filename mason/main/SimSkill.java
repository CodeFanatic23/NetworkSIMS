package main;
import java.util.*;

public class SimSkill {

	private Map<String,Float> skillMap= new HashMap<String,Float>();
	public SimSkill(){
		
	}
	public SimSkill(String skill, Float val){
		this.addSkill(skill,val);
		
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
	public void changeSkillVal(String key, Float val){
		this.skillMap.put(key, val);
	}
	public void printMap(Map<String,Float> skillMap){
		for(Map.Entry<String, Float> e: skillMap.entrySet()){
			System.out.println(e+"--"+skillMap.size());
		}
	}
}
