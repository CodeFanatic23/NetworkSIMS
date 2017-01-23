import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.network.Edge;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class SimNode implements Steppable {

	// To maintain uniqueness of labels
	public HashSet<String> label;
	public SimSkill skills;
	public int sharmaVariable;
	public static int livingNode = 0;
	private boolean test = true;
	private static final long serialVersionUID = 1L;
	private String nodeName;
	private SimNode spouseObject;
	private SimNode mentorObject = null;
	private ArrayList<SimNode> menteeObjects;
	protected int numberOfChildren;
	private int generationFlag;
	private String profession;
	protected Integer professionValue;
	protected int professionAge;
	private Random r;
	protected double randomNormal;
	private int totalMentee;
	protected Integer maxProfessionalValue;
	protected String Gender;
	private static int logFlag;

	protected int toddlerAge;
	protected int adolescentAge;
	protected int teenagerAge;
	protected int adultAge;
	protected int middleAge;
	protected int oldAge;
	protected int deathAge;
	protected long previousAge;
	
	
	public static Skill skillObject;
	
	protected SimManager simMan = SimManager.createObj();

	// private static int flag1 = 0;

	public void addLabel(String label) {
		this.label.add(label);
	}
	public SimNode(){
	}

	
	public SimNode(String nodeName, SimSkill skills) {
		this.skills = skills;
		setNumberOfChildren(1);
		this.setNodeName(nodeName);
		sharmaVariable = 0;
		profession = "";
		generationFlag = 0;
		setProfessionValue(null);
		setMenteeObjects(new ArrayList<SimNode>());
		this.label = new HashSet<String>();
		r = new Random();
		randomNormal = r.nextGaussian();
		setProfessionAge((int) (randomNormal * 1 + 16));
		setTotalMentee((int) (randomNormal * 1 + 3));
		spouseObject = null;
		setMaxProfessionalValue((int) (randomNormal * 5 + 20));

		setToddlerAge(1);
		setAdolescentAge(((int) (randomNormal * 1 + 9)) > 10 ? 10 : (int) (randomNormal * 1 + 9)); // at max it can be 10

		setTeenagerAge(((int) (randomNormal * 2 + 12)) > 14 ? 14 : (int) (randomNormal * 2 + 12)); // at max it can be 14
																								
		setTeenagerAge(getTeenagerAge() <= 10 ? 11 : getTeenagerAge()); // minimum it cab be 11
																	
		setAdultAge((int) (r.nextGaussian() * 2 + 22) > 24 ? 24 : (int) (r.nextGaussian() * 2 + 22)); // at max it can be 24
																									
		setMiddleAge((int) (r.nextGaussian() * 2 + 34));
		setOldAge((int) (r.nextGaussian() * 2 + 55));
		setDeathAge((int) (r.nextGaussian() * 2 + 75));
		this.previousAge = -1;
		this.logFlag = 0;

	}

	public SimNode(String nodeName) {
		this.setNodeName(nodeName);
		setNumberOfChildren(1);
		sharmaVariable = 0;
		generationFlag = 0;
		setProfessionValue(null);
		profession = "";
		this.label = new HashSet<String>();
		setMenteeObjects(new ArrayList<SimNode>());
		this.skills = new SimSkill();
		r = new Random();
		randomNormal = r.nextGaussian();
		setProfessionAge((int) (randomNormal * 1 + 16));
		setTotalMentee((int) (randomNormal * 1 + 3));
		spouseObject = null;
		setMaxProfessionalValue((int) (randomNormal * 5 + 20));

		setToddlerAge(1);
		setAdolescentAge(((int) (randomNormal * 1 + 9)) > 10 ? 10 : (int) (randomNormal * 1 + 9)); // at max it can be 10
																								
		setTeenagerAge(((int) (randomNormal * 2 + 12)) > 14 ? 14 : (int) (randomNormal * 2 + 12)); // at max it can be 14
																								
		setTeenagerAge(getTeenagerAge() <= 10 ? 11 : getTeenagerAge()); // minimum it can be 11
																		
		setAdultAge((int) (r.nextGaussian() * 2 + 22) > 24 ? 24 : (int) (r.nextGaussian() * 2 + 22)); // at max it can be 24
																									
		setMiddleAge((int) (r.nextGaussian() * 2 + 34));
		setOldAge((int) (r.nextGaussian() * 2 + 55));
		setDeathAge((int) (r.nextGaussian() * 2 + 75));
		this.previousAge = -1;
		this.logFlag = 0;
	}

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			SimNode tiger = (SimNode) object;
			if (this.nodeName.equals(tiger.getNodeName())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 37 * hash + this.nodeName.hashCode();
		return hash;
	}

//	protected void setHealth() {
//		// health = a*age^2 + b*age (increase with age and then decreases)
//		// k maximum health
//		// t age at which it will have maximum health
//		// d age at which it will die
//		
//		float k = (float) (randomNormal * 20 + 180);
//		int t = (int) (randomNormal * 7 + 27);
//		int d = getDeathAge();
//		float a = k / (t * t - d * t);
//		float b = -k * d / (t * t - d * t);
//		float healthVar = a * sharmaVariable * sharmaVariable + b * sharmaVariable;
//		this.skills.changeSkillVal("health", healthVar);
////		 System.out.println("health of " + this.nodeName + " is " + healthVar);
//		// this.skills.getSkillMap().get("health"));
//	}
//
//	protected void setknowledge() {
//		// knowledge = c*(1-pow(e,-kx))
//		// c ~ N(23,1)
//		// k = 0.055
//		float c = (float) (randomNormal * 2 + 23);
//		float knowledgeVar = (float) (c * (1 - Math.exp(-1 * (0.055) * sharmaVariable)));// + 2000;
//		this.skills.addSkill("knowledge", knowledgeVar);
////		 System.out.println("knowledge of " + this.nodeName + " is " + knowledgeVar);
//
//
//	}
	
	
	
/** Will be used when file input is supported*/
//	private void findAndMarryFromFile() {
//		for (String line : SimNetwork.relation) {
//			String[] n = line.split(" ");
//			if (this.getNodeName().equals(n[0]) || this.getNodeName().equals(n[1])) {
//				Bag out = SimNetwork.buddies.getEdges(SimNetwork.returnNode(n[0]), null);
//				int flag = 0;
//				for (int buddy = 0; buddy < out.size(); buddy++) {
//					// checking if already married
//					if (((String) (((Edge) out.get(buddy)).getInfo())).equals("marriage")) {
//						System.out.println(
//								this.getNodeName() + " " + this.getSpouseObject().getNodeName() + " already married");
//						flag = 1;
//						break;
//					}
//				}
//				if (flag == 0) {
//					if (this.getNodeName().equals(n[0])) {
//						// marring the node v0 v1
//						SimNode n1 = SimNetwork.returnNode(n[1]);
//						if (n1 != null) {
//							System.out.println(this.getNodeName() + " " + n1.getNodeName() + " happily married");
//
//							SimNetwork.buddies.addEdge(this, n1, n[2]);
//							SimNetwork.addEdgeLabel(this, n1, n[3]);
//							this.spouseObject = n1;
//							n1.setSpouseObject(this);
//						} else {
//							System.out.println("spouse already dead or not born");
//						}
//
//					} else {
//						// marring the node v1 v0
//						SimNode n0 = SimNetwork.returnNode(n[0]);
//						if (n0 != null) {
//							System.out.println(this.getNodeName() + " " + n0.getNodeName() + " happily married");
//							SimNetwork.buddies.addEdge(this, n0, n[2]);
//							SimNetwork.addEdgeLabel(this, n0, n[3]);
//							this.spouseObject = n0;
//							n0.setSpouseObject(this);
//						} else {
//							// spouse not born or dead
//							System.out.println("spouse already dead or not born");
//						}
//					}
//
//				}
//
//			}
//		}
//	}

	protected void findSimilarAndMarry() {
		if (this.spouseObject == null) {
			Bag n = SimNetwork.buddies.getAllNodes();
			float diff = (float) 1000.0;
			float diff1;
			SimNode spouse = null;
			for (int i = 0; i < n.size(); i++) {
				// node must have different gender and node must be unmarried
				if ((((SimNode) n.get(i)).spouseObject == null)
						&& (Gender.equals("male") && (((SimNode) n.get(i)).Gender).equals("female")
								|| Gender.equals("female") && (((SimNode) n.get(i)).Gender).equals("male"))) {

					diff1 = Math.abs(((SimNode) n.get(i)).skills.getSkillMap().get("knowledge")
							- this.skills.getSkillMap().get("knowledge"));
					if (diff1 < diff) {
						diff = diff1;
						spouse = (SimNode) n.get(i);
					}
				}
			}
			if (spouse != null) {
				System.out.println(this.getNodeName() + " " + spouse.getNodeName() + " happily married");

				SimNetwork.buddies.addEdge(this, spouse, 100);
				SimNetwork.addEdgeLabel(this, spouse, "marriage");
				this.spouseObject = spouse;
				spouse.setSpouseObject(this);
			} else {
				System.out.println("no spouse found for " + nodeName);
			}
		}
	}

	
	public void step(SimState state) {

		if (state.schedule.getSteps() == 0 && logFlag == 0) {
			// delete all log files containing degree of nodes at different
			// stage
			simMan.deleteLogs();
			logFlag = 1;
		}
		
		SimNetwork net = (SimNetwork) state;
		if(!test){
		Double2D me = net.yard.getObjectLocation(this);
		MutableDouble2D sumForces = new MutableDouble2D();
		sumForces.addIn(me);
		net.yard.setObjectLocation(this, new Double2D(sumForces));
		}

		// set health skill of node
		//setHealth();
		//setknowledge();
		
		float knowledgeVar = skillObject.setKnowledge(sharmaVariable);
		this.skills.addSkill("knowledge", knowledgeVar);
		
		float healthVar = skillObject.setHealth(sharmaVariable,deathAge);
		this.skills.addSkill("health", healthVar);
		
		/*
		 * approximate life stages 
		 * 1) Born 0 
		 * 2)Toddler 1 
		 * 3)Adolescent 8
		 * 4)Teenager 13 
		 * 5)Adult 21 |------>Marriage 25
		 * 						 |------->Children 27		 * 
		 * 6)Middle age 35 
		 * 7)Old age 50 
		 * 8)Death 75
		 */

		if (getProfessionValue() != null && generationFlag != 0) {
			// increment value with time and saturate it till it reaches value
			// of mentor
			if (mentorObject != null) {
				setProfessionValue(getProfessionValue() + 1);
			}

			if (mentorObject != null && getProfessionValue() >= mentorObject.getProfessionValue()) {
				Bag e = SimNetwork.buddies.getEdges(this, mentorObject, null);
				int i;
				for (i = 0; i < e.size(); i++) {
					if (((Edge) (e.get(i))).getInfo().equals("student-mentor")) {
						break;
					}
				}
				if (i < e.size()) {
					SimNetwork.buddies.removeEdge((Edge) e.get(i));
					System.out.println(nodeName + " no longer mentee of " + mentorObject.nodeName);
					mentorObject = null;
				}
			}
		}
		if (generationFlag == 0) {
			// professional value must be at max maxProfessioanlValue
			if (getProfessionValue() != null && getProfessionValue() <= getMaxProfessionalValue()) {
				// increase professional value but no mentor
				setProfessionValue(getProfessionValue() + 1);
			} else if (getProfessionValue() == null) {
				setProfessionValue(new Integer(0));
			}
		}
		// sharmaVariabe is age of the node
		if (sharmaVariable == getProfessionAge()) {
			// select profession
			this.profession = SimNetwork.EquiLikelyProfession();
			System.out.println(this.nodeName + " becomes " + this.profession);
			if (this.generationFlag != 0) {
				// not generation zero assign a mentor
				Bag out = SimNetwork.buddies.getAllNodes();
				for (int buddy = 0; buddy < out.size(); buddy++) {
					SimNode e = (SimNode) out.get(buddy);

					if (e.getProfession().equals(this.profession) && (!(this.nodeName.equals(e.nodeName)))
							&& (e.getProfessionValue() != null)) {

						if (e.getTotalMentee() > 0) {
							// creating mentor
							SimNetwork.buddies.addEdge(this, e, "100");
							SimNetwork.addEdgeLabel(this, e, "student-mentor");
							e.setTotalMentee(e.getTotalMentee() - 1);
							setProfessionValue(new Integer(0));
							System.out.println(e.nodeName + " mentor of " + nodeName);
							mentorObject = e;
							e.getMenteeObjects().add(this);
							break;
						}
					}

				}
				if (mentorObject == null) {
					// no mentor found
					System.out.println("no mentor found for " + nodeName);
				}

			}

		}

		if (sharmaVariable == 0) {
			// born
			this.skills.changeSkillVal("eat", (float) 0);
		} else if (sharmaVariable == getToddlerAge()) {
			// todler
			System.out.println(this.getNodeName() + " entered todler stage");
			this.skills.changeSkillVal("eat", (float) 20);
		} else if (sharmaVariable == getAdolescentAge()) {
			// adolescent
			System.out.println(this.getNodeName() + " entered adolescent stage");
			this.skills.changeSkillVal("eat", (float) 70);
		} else if (sharmaVariable == getTeenagerAge()) {
			// teenager
			System.out.println(this.getNodeName() + " entered teenager stage");
			this.skills.changeSkillVal("eat", (float) 75);
		} else if (sharmaVariable == getAdultAge()) {
			// adult
			System.out.println(this.getNodeName() + " entered adult stage");
			this.skills.changeSkillVal("eat", (float) 80);
		} else if (sharmaVariable == 25) {
			// marriage age
			// findAndMarryFromFile();
			findSimilarAndMarry();

		}

		else if (sharmaVariable == 27) {
			// creating child
			if (this.spouseObject != null) {
				// if spouse present then create child named lexicographically

				if ((0 + (int) (Math.random() * 100)) <= 50) {
					if (this.getNumberOfChildren() <= this.spouseObject.getNumberOfChildren()) {
						
						//TODO: Remove this,irrelevant as of now as child name does not matter
						if (this.nodeName.compareToIgnoreCase(this.getSpouseObject().getNodeName()) < 0)
							SimNetwork.createChildNode(this, this.getSpouseObject());
						else
							SimNetwork.createChildNode(this.getSpouseObject(), this);
						this.setNumberOfChildren(this.getNumberOfChildren() - 1);
						System.out.println(
								this.getNodeName() + " " + this.getSpouseObject().getNodeName() + " gave birth");
						SimNode.livingNode++;

					}
				} else {
					System.out.println("probability less");
					this.setNumberOfChildren(this.getNumberOfChildren() - 1);
				}

			}
		} else if (sharmaVariable == getMiddleAge()) {
			// middle age
			System.out.println(this.getNodeName() + " entered middle age");
			this.skills.changeSkillVal("eat", (float) 60);
		} else if (sharmaVariable == getOldAge()) {
			// old age
			System.out.println(this.getNodeName() + " entered old age");
			this.skills.changeSkillVal("eat", (float) 50);
		} else if (sharmaVariable == getDeathAge()) {
			// dead
			this.skills.changeSkillVal("eat", (float) 0);
			SimNode n = (SimNode) SimNetwork.buddies.removeNode(this);


			System.out.println(n.getNodeName() + " died");
			SimNode.livingNode--;
			SimNetwork.stopper.get(this).stop();
			// removing node from set containing all the nodes
			SimNetwork.nodes.remove(this);
			// stopping scheduler to call step method of dead state
			SimNetwork.stopper.remove(this);
			// remove link to mentee object
			if (getMenteeObjects() != null) {
				for (SimNode i : getMenteeObjects()) {
					i.mentorObject = null;
				}

			}
			if (mentorObject != null) {
				for (int i = 0; i < mentorObject.getMenteeObjects().size(); i++) {
					if ((mentorObject.getMenteeObjects().get(i).equals(this))) {
						mentorObject.getMenteeObjects().remove(i);
					}
				}
			}
			if (this.getSpouseObject() != null) {
				this.getSpouseObject().setSpouseObject(null);
			}

			if (SimNode.livingNode == 0) {
				// finishing simulation
				System.out.println("starting gui");
				System.out.println(Thread.currentThread());
//				PlotGUI.totalTime = Long.toString(state.schedule.getSteps());
//				PlotGUI p = new PlotGUI();
//				p.start();
				SimManager.writeIntoFile(Long.toString(state.schedule.getSteps()), "src/main/resources/", "data", false);
				System.out.println("DONE!");
				state.finish();

			}
			return;
		}
//		System.out.println("living node" + livingNode);
		// increase age
//		System.out.println("age - " + this.nodeName + " " + sharmaVariable);
		sharmaVariable++;
		//System.out.println(this.label);
		long degree = SimNetwork.buddies.getEdges(this, null).size();
		SimNetwork.logDegreeToFile(Long.toString(state.schedule.getSteps()), degree);

	}


	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public SimNode getSpouseObject() {
		return spouseObject;
	}

	public void setSpouseObject(SimNode spouseObject) {
		this.spouseObject = spouseObject;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public int getGenerationFlag() {
		return generationFlag;
	}

	public void setGenerationFlag(int generationFlag) {
		this.generationFlag = generationFlag;
	}

	public SimNode getMentorObject() {
		return mentorObject;
	}

	public void setMentorObject(SimNode mentorObject) {
		this.mentorObject = mentorObject;
	}

	public ArrayList<SimNode> getMenteeObjects() {
		return menteeObjects;
	}

	public void setMenteeObjects(ArrayList<SimNode> menteeObjects) {
		this.menteeObjects = menteeObjects;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public Integer getProfessionValue() {
		return professionValue;
	}

	public void setProfessionValue(Integer professionValue) {
		this.professionValue = professionValue;
	}

	public int getProfessionAge() {
		return professionAge;
	}

	public void setProfessionAge(int professionAge) {
		this.professionAge = professionAge;
	}

	public int getTotalMentee() {
		return totalMentee;
	}

	public void setTotalMentee(int totalMentee) {
		this.totalMentee = totalMentee;
	}

	public Integer getMaxProfessionalValue() {
		return maxProfessionalValue;
	}

	public void setMaxProfessionalValue(Integer maxProfessionalValue) {
		this.maxProfessionalValue = maxProfessionalValue;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public int getToddlerAge() {
		return toddlerAge;
	}

	public void setToddlerAge(int toddlerAge) {
		this.toddlerAge = toddlerAge;
	}

	public int getAdolescentAge() {
		return adolescentAge;
	}

	public void setAdolescentAge(int adolescentAge) {
		this.adolescentAge = adolescentAge;
	}

	public int getTeenagerAge() {
		return teenagerAge;
	}

	public void setTeenagerAge(int teenagerAge) {
		this.teenagerAge = teenagerAge;
	}

	public int getAdultAge() {
		return adultAge;
	}

	public void setAdultAge(int adultAge) {
		this.adultAge = adultAge;
	}

	public int getMiddleAge() {
		return middleAge;
	}

	public void setMiddleAge(int middleAge) {
		this.middleAge = middleAge;
	}

	public int getOldAge() {
		return oldAge;
	}

	public void setOldAge(int oldAge) {
		this.oldAge = oldAge;
	}

	public int getDeathAge() {
		return deathAge;
	}

	public void setDeathAge(int deathAge) {
		this.deathAge = deathAge;
	}


}