import java.lang.ref.WeakReference;

public class ReferenceTypesDemo {
    public static void main(String[] args) {
        Person personStrongRef1 = new Person();
        WeakReference<Person> personWeakRef = new WeakReference<>(personStrongRef1);

        System.out.println(personStrongRef1);

        personStrongRef1 = null;

        Person personStrongRef2 = personWeakRef.get();
        System.out.println(personStrongRef2);

        personStrongRef2 = null;
        System.gc();

        Person personStrongRef3 = personWeakRef.get();
        System.out.println(personStrongRef3);


    }

}

class Person{

}
