
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author nathan
 *
 */
public class Inspector {

	private Object objToInspect;
	private String superClassName, interfaceName;
	private boolean recursiveFlag;
	private boolean isArray;
	private boolean isInterface;
	private boolean isPrimitive;


	public void inspect(Object obj, boolean recursive) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {

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
		getFields();

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
		
		this.superClassName = objToInspect.getClass().getSuperclass().getSimpleName();
		System.out.println("Superclass Name: \t" + superClassName);
	}


	/**
	 * Prints the interface name (if applicable) of objToInspect
	 */
	private void getInterface() {

		
		
		if(objToInspect.getClass().isInterface()) {
			
			
			System.out.println("Interface Name: \tThis class is an interface");
			

		}
		else {
			
			Class[] interfaces = objToInspect.getClass().getInterfaces();
			System.out.print("Interface Name: \t");
			
			int numInterface = interfaces.length;
			
			// Prints out all interface names
			for(int count = 0; count < interfaces.length; count++) {
				
				System.out.print(interfaces[count].getName());
				
				// Prints out a comma if there are more interfaces
				if(count < (numInterface-1)) System.out.print(", ");
				
			}
			
			System.out.println("\n");

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
			else System.out.print("No parameters");

			System.out.println("\n\t\t\tModifiers: \t\t" + Modifier.toString(construc[count].getModifiers()));

			System.out.println("\n");

		}
	}

	private void getFields() throws IllegalArgumentException, IllegalAccessException {

		
		Field[] fields = objToInspect.getClass().getDeclaredFields();

		System.out.println("______________________________________________\nFields:");

		for(int count = 0; count < fields.length; count++) {

			fields[count].setAccessible(true);
			String fieldName = fields[count].getName();
			Object value = fields[count].get(objToInspect);

			if(value.getClass().isArray()) {

				System.out.println("Type ARAAAAAAAAAAAAAAAAAAAAAY");
				/*
				 * Print out contents of array, other stuff
				 */
				
			}
			else {
				
				System.out.println("\t\t\t\t- " + fieldName + " -");
				System.out.print("\t\t\tType: \t\t\t" + fields[count].getType());
				System.out.print("\n\t\t\tModifiers: \t\t" + Modifier.toString(fields[count].getModifiers()));


				/*
				 * If recursiveFlag is true, perform recursive check on fields which are objects
				 * else prints out information on object
				 */
				if(recursiveFlag) {
					/*
					 * Inspect object if it is a field
					 */

				}
				else {

					System.out.print("\n\t\t\tValue: \t\t\t" + fields[count].get(objToInspect));
					System.out.print("\n\t\t\tReference Value: \t" + fields[count].getDeclaringClass() + " " + System.identityHashCode(fields[count].get(objToInspect)));

				}
			}



			System.out.println("\n");

		}




	}
}
