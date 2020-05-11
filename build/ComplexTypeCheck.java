public class ComplexTypeCheck {
	public static void main(String args[]) {
		ComplexTypeCheck c = new SubClass();
		ComplexTypeCheck d = new OtherSubClass();
		SubClass e = new OtherSubClass();
	}
}

class SubClass extends ComplexTypeCheck {}

class OtherSubClass extends ComplexTypeCheck {}
