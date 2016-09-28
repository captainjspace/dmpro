package dmpro.attributes;

public class AttributeTest extends Attribute {

	public AttributeTest() {
		// TODO Auto-generated constructor stub
		this.fieldCount=6;
	}
	
	public static void main(String[] args) {
		AttributeTest at = new AttributeTest();
		System.out.println(at.fieldCount());
		
	}

}
