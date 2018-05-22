package miniNet;

public class Connection {
	
	private User relationPerson1;
	private User relationPerson2;
	private String relation;
	
	
	public User getRelationPerson1() {
		return relationPerson1;
	}
	public void setRelationPerson1(User relationPerson1) {
		this.relationPerson1 = relationPerson1;
	}
	public User getRelationPerson2() {
		return relationPerson2;
	}
	public void setRelationPerson2(User relationPerson2) {
		this.relationPerson2 = relationPerson2;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relationship) {
		this.relation = relationship;
	}
	

	/**
	 * @param relationPerson1
	 * @param relationPerson2
	 * @param relationship
	 */
	public Connection(User relationPerson1, User relationPerson2, String relation) {
		super();
		this.relationPerson1 = relationPerson1;
		this.relationPerson2 = relationPerson2;
		this.relation = relation;
	}
	
	@Override
	public String toString() {
		return relationPerson1.getName() + " " + relationPerson2.getName() + " "
				+ relation;
	}
	
	

}
