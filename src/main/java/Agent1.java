public class Agent1 extends SimNode{

	public Agent1(String nodeName) {
		super(nodeName);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setOldAge(int age){
		this.oldAge = age + 1234;
	}
	@Override
	public void setAdolescentAge(int age){
		
	}

	
	public static void main(String args[]){
		Agent1 a = new Agent1("yolo");		
		Agent1 b = new Agent1("asff");
		a.setSpouseObject(b);
		System.out.println(a.getNodeName());
		System.out.println(a.getSpouseObject().getNodeName());
		a.setOldAge(10);
		System.out.println(a.getOldAge());
		a.skills.printMap(a.skills.getSkillMap());
	}
	
	
}