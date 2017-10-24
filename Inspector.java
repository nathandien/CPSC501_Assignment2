
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author nathan
 *
 */
public class Inspector {

	private Object objToInspect;
	private boolean recursiveFlag;
	private boolean isArray;
	private boolean isInterface;
	private boolean isPrimitive;


	public void inspect(Object obj, boolean recursive) throws ClassNotFoundException {

		this.objToInspect = obj;



		/* REMOVE LATER
		this.isArray = objToInspect.getClass().isArray();
		this.isInterface = objToInspect.getClass().isInterface();
		this.isPrimitive = objToInspect.getClass().isPrimitive();


		System.out.println(objToInspect.getClass().getTypeName());
		String temp = objToInspect.getClass().getName();
		Class tempClass = Class.forName(temp);
		System.out.println(tempClass.getDeclaringClass());
		//Field f = tempClass.getDeclaredFields()[0];
		//System.out.println(f.getType());
		 * *
		 */

		getClassName();
		getSuperClass();
		getInterface();
		getMethods();
		getConstruc();

	}




	/**
	 * Prints the class name of objToInspect (changes depending on if the object is an array and/or primitive)
	 */
	public void getClassName() {

		System.out.println("Class Name: \t\t" + objToInspect.getClass().getName());

		/*
		if(!isPrimitive) {
			if(isArray) {
				System.out.println("Class Name (Array): \t" + this.objToInspect.getClass().getComponentType());
			}
			else {
				System.out.println("Class Name: \t\t" + objToInspect.getClass().getSimpleName());
			}
		}
		/*
		else {
			System.out.println("Ayyyyy \t" + this.objToInspect.getClass().getDeclaringClass());
		}*/
	}


	/**
	 * Prints the superclass name of objToInspect
	 */
	private void getSuperClass() {
		System.out.println("Superclass Name: \t" + objToInspect.getClass().getSuperclass().getSimpleName());
	}


	/**
	 * Prints the interface name (if applicable) of objToInspect
	 */
	private void getInterface() {

		if(isInterface) {
			System.out.println("Interface Name: \t" + objToInspect.getClass().isInterface());
		}
		else {
			System.out.println("Interface Name: \tNo interface implemented");
		}
	}

	/**
	 * Prints the name and details (exceptions, parameter type, return type, modifiers)
	 * of all the methods of objToInspect
	 */
	private void getMethods() {

		Method[] methods = objToInspect.getClass().getDeclaredMethods();
		System.out.println("______________________________________________\nMethods:");
		for(int count = 0; count < methods.length; count++) {

			System.out.println("\t\t\t< " + methods[count].getName() + " >");


			int numParam = methods[count].getParameterCount();
			System.out.print("\t\t\tParameter Types: \t");
			// Prints out all parameter types (if any)
			if(numParam != 0) {
				for(int count2 = 0; count2 < numParam; count2++) {
					Class[] parameters = methods[count].getParameterTypes();
					System.out.print(parameters[count2].getSimpleName());
					// Prints a comma for formatting purposes
					if(count2 != (numParam-1)) System.out.print(", ");
				}
			}
			else System.out.print("No parameters");


			System.out.print("\n\t\t\tException Types: \t");
			Class[] exceptions = methods[count].getExceptionTypes();
			int numExcep = exceptions.length;
			// Prints out all exception types (if any)
			if(numExcep != 0) {
				for(int count3 = 0; count3 < numExcep; count3++) {
					System.out.print(exceptions[count3].getSimpleName());
					if(count3 != (numExcep-1)) System.out.print(", ");
				}
			}
			else System.out.print("No exceptions thrown");


			String returnType = methods[count].getReturnType().getSimpleName();
			// Prints out return type (if it does return anything)
			if(returnType.equals("void")) System.out.println("\n\t\t\tReturn Type: \t\tNo return (void)");
			else System.out.println("\n\t\t\tReturn Type: \t\t" + methods[count].getReturnType().getSimpleName());


			System.out.println("\t\t\tModifiers: \t\t" + Modifier.toString(methods[count].getModifiers()));

			System.out.println("\n");

		}


	}

	/**
	 * Prints the name and the parameter type and modifiers of
	 * the constructors of objToInspect
	 */
	private void getConstruc() {

		Constructor[] construc = objToInspect.getClass().getDeclaredConstructors();

		System.out.println("______________________________________________\nConstructors:");
		for(int count = 0; count < construc.length; count++) {

			System.out.println("\t\t\t* " + construc[count].getName() + " *");

			int numParam = construc[count].getParameterCount();
			System.out.print("\t\t\tParameter Types: \t");

			if(numParam != 0) {
				for(int count2 = 0; count2 < numParam; count2++) {
					Class[] parameters = construc[count].getParameterTypes();
					System.out.print(parameters[count2].getSimpleName());
					
					if(count2 != (numParam-1)) System.out.print(", ");
				}
			}
			
			System.out.println("\n\t\t\tModifiers: \t\t" + Modifier.toString(construc[count].getModifiers()));

			System.out.println("\n");
			
		}
	}

}
