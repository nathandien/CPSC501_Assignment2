
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @author nathan
 *
 */
public class Inspector {

	private Object objToInspect;
	private Class superClass;
	private Class[] interfaces;
	private boolean recursiveFlag;
	private Class currClass;


	public void inspect(Object obj, boolean recursive) throws Exception {

		this.objToInspect = obj;
		this.recursiveFlag = recursive;
		this.currClass = obj.getClass();


		// Method calls for inspection of particular details of objToInspect
		getClassName(objToInspect);
		getSupName(objToInspect, null);
		getInterfName(objToInspect, null);
		Method[] methods = objToInspect.getClass().getDeclaredMethods();
		getMethods(methods);
		Constructor[] construc = objToInspect.getClass().getDeclaredConstructors();
		getConstruc(construc);
		Field[] fields = objToInspect.getClass().getDeclaredFields();
		getFields(fields);
		travInher();

	}




	/**
	 * Prints the class name of objToInspect (changes depending on if the object is an array and/or primitive)
	 */
	public void getClassName(Object obj) {
		System.out.println("Class Name: \t\t" + obj.getClass().getName());
	}


	/**
	 * Prints the superclass name of objToInspect
	 */
	public void getSupName(Object obj, Class cls) {

		// Checks which arguments were passed
		if(obj != null) {
			this.superClass = obj.getClass().getSuperclass();
		}
		else if(cls != null) {
			this.superClass = cls.getSuperclass();

		}
		
		// Prints superclass name (if applicable)
		if(superClass.getSimpleName().equals("Object")) {
			System.out.println("Superclass Name: \tNo superclass");
		}
		else {
			System.out.println("Superclass Name: \t" + superClass.getSimpleName());
		}
	}


	/**
	 * Prints the interface name (if applicable) of obj or cls
	 */
	public void getInterfName(Object obj, Class cls) {

		boolean isInterface = false;
		// Checks which arguments were passed
		if(obj != null) {
			this.interfaces = obj.getClass().getInterfaces();
			if(obj.getClass().isInterface()) isInterface = true;
		}
		else if(cls != null) {
			this.interfaces = cls.getInterfaces();
			if(cls.isInterface()) isInterface = true;
		}

		int numInterface = interfaces.length;

		if(isInterface) {
			System.out.println("Interface Name: \tThis class is an interface");
		}
		else if(numInterface > 0) {

			System.out.print("Interface Name: \t");

			// Prints out all interface names
			for(int count = 0; count < interfaces.length; count++) {

				System.out.print(interfaces[count].getName());

				// Prints out a comma if there are more interfaces
				if(count < (numInterface-1)) System.out.print(", ");

			}

			System.out.println("\n");

		}
		else {
			System.out.println("Interface Name: \tNo interface");
		}
	}

	/**
	 * Prints the name and details (exceptions, parameter type, return type, modifiers)
	 * of all the methods of objToInspectbj.getClass().isInterface()
	 */
	public void getMethods(Method[] methods) {


		System.out.print("______________________________________________\nMethods:");
		// Checks to see if there is any methods to print
		if(methods.length == 0) System.out.print("\t\tNo methods");
		else {
			System.out.print("\n");
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
						if(count2 < (numParam-1)) System.out.print(", ");
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
						if(count3 < (numExcep-1)) System.out.print(", ");
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
	}

	/**
	 * Prints the name and the parameter type and modifiers of
	 * the constructors of objToInspect
	 */
	public void getConstruc(Constructor[] construc) {

		System.out.println("______________________________________________\nConstructors:");

		for(int count = 0; count < construc.length; count++) {

			System.out.println("\t\t\t* " + construc[count].getName() + " *");

			int numParam = construc[count].getParameterCount();
			System.out.print("\t\t\tParameter Types: \t");

			if(numParam != 0) {
				for(int count2 = 0; count2 < numParam; count2++) {
					Class[] parameters = construc[count].getParameterTypes();
					System.out.print(parameters[count2].getSimpleName());
					// Prints a comma for formatting purposes
					if(count2 < (numParam-1)) System.out.print(", ");
				}
			}
			else System.out.print("No parameters");

			System.out.println("\n\t\t\tModifiers: \t\t" + Modifier.toString(construc[count].getModifiers()));

			System.out.println("\n");

		}
	}

	public void getFields(Field[] fields) throws IllegalArgumentException, IllegalAccessException {

		System.out.println("______________________________________________\nFields:");

		for(int count = 0; count < fields.length; count++) {
			//obj.getClass().getSuperclass();
			fields[count].setAccessible(true);
			String fieldName = fields[count].getName();
			Object value = fields[count].get(objToInspect);
			boolean fieldArray = false;

			// Checks if the value is null before checking if it is an array
			if(value != null) {
				if(value.getClass().isArray())
					fieldArray = true;
			}
			if(fieldArray) {

				System.out.println("\t\t\t\t- " + fieldName + " -");
				System.out.print("\t\t\tType: \t\t\t" + value.getClass().getComponentType());
				// Gets the length of the array
				int arrayLen = Array.getLength(value);
				System.out.print("\n\t\t\tLength: \t\t" + arrayLen);

				// Prints out the contents of the array
				System.out.print("\n\t\t\tContents: \t\t");
				int splitCount = 0;
				for(int countArr = 0; countArr < arrayLen; countArr++) {

					splitCount++;
					System.out.print(Array.get(value, countArr));

					// Prints a comma for formatting purposes
					if(countArr < (arrayLen-1)) System.out.print(", ");
					// Prints a newline for formatting purposes
					if(splitCount == 10) {
						splitCount = 0;
						System.out.print("\n\t\t\t\t\t\t");
					}

				}

			}
			else {

				System.out.println("\t\t\t\t- " + fieldName + " -");
				System.out.print("\t\t\tType: \t\t\t" + fields[count].getType().getTypeName());
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

	/*
	 * Traverses class and interface hierarchy to be inspected
	 */
	private void travInher() throws Exception {

		if(!superClass.getSimpleName().equals("Object")) {
			
			Class supClass = null;
			System.out.println("*****Superclass: " + superClass + "*****");

			supClass = Class.forName(superClass.getName());
			this.currClass = supClass;
			
			getSupName(null, supClass);
			getInterfName(null, supClass);
			Method[] methods = supClass.getDeclaredMethods();
			getMethods(methods);
			Constructor[] construc = supClass.getDeclaredConstructors();
			getConstruc(construc);
			getFields(supClass.getDeclaredFields());
			travInher();


		}
		// Checks that there the class has an interface to inspect
		if(interfaces.length != 0) {
			// Prints the methods of the interface
			for(int count = 0; count < interfaces.length; count++) {
				System.out.print("\n++++++++++++++++++++++++++++++++++++++++++++++\n\n");
				Method[] methods2 = interfaces[count].getDeclaredMethods();
				System.out.print("Interface Name: \t" + interfaces[count].getName() + "\n");
				getMethods(methods2);
			}


		}


	}
}
