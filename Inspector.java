
/**
 * @author nathan
 *
 */
public class Inspector {

	private Object objToInspect;
	private boolean recursiveFlag;
	private boolean isArray;
	private boolean isInterface;


	public void inspect(Object obj, boolean recursive) {

		this.objToInspect = obj;
		this.isArray = objToInspect.getClass().isArray();
		this.isInterface = objToInspect.getClass().isInterface();


		getClassName();
		getSuperClass();
		getInterface();

	}




	/**
	 * Prints the class name of objToInspect (changes depending on if the object is an array)
	 */
	public void getClassName() {

		if(isArray) {
			System.out.println("Class Name (Array): " + this.objToInspect.getClass().getComponentType());
		}
		else {
			System.out.println("Class Name: " + objToInspect.getClass().getSimpleName());
		}
	}


	/**
	 * Prints the superclass name of objToInspect
	 */
	public void getSuperClass() {
		System.out.println("Superclass Name: " + objToInspect.getClass().getSuperclass().getSimpleName());
	}


	/**
	 * Prints the interface name (if applicable) of objToInspect
	 */
	public void getInterface() {

		if(isInterface) {
			System.out.println("Interface Name: " + objToInspect.getClass().isInterface());
		}
		else {
			System.out.println("Interface Name: No interface implemented");
		}
	}


}
