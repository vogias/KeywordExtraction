package ke.entry;

public class ClassObjectCreation {

	private ClassObjectCreation() {

	}

	public static Object createClassObject(String classname)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Class<?> class2Load;

		class2Load = classLoader.loadClass(classname);
		return class2Load.newInstance();

	}

}
