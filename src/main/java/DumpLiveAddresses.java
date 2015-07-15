import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.util.LinkedList;

public class DumpLiveAddresses {

    private static Unsafe unsafe;

    private static Unsafe getUnsafe() {
        if(unsafe != null)return unsafe;

        try{

            final Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            unsafe = constructor.newInstance();

        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return unsafe;
    }

    private static long addressOf(Object o){

        final Object[]array = new Object[]{o};

/*
    from http://is.gd/4h8ziq

    Report the offset of the first element in the storage allocation of a given array class.
    If arrayIndexScale(java.lang.Class) returns a non-zero value for the same class,
    you may use that scale factor, together with this base offset,
    to form new offsets to access elements of arrays of the given class.
*/
        long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);

/*
    from http://is.gd/3OHOvj

    Report the size in bytes of a native pointer, as stored via putAddress(long,long).
    This value will be either 4 or 8. Note that the sizes of other primitive types
    (as stored in native memory blocks) is determined fully by their information content.
*/
        int addressSize = getUnsafe().addressSize();
        long objectAddress;

        switch (addressSize){
            case 4:
/*
    from http://is.gd/i2DBF2

    Fetches a value from a given Java variable.
    More specifically, fetches a field or array element within the given object o at the given offset, or (if o is null)
    from the memory address whose numerical value is the given offset.

    The results are undefined unless one of the following cases is true:

        The offset was obtained from objectFieldOffset(java.lang.reflect.Field) on the java.lang.reflect.
            Field of some Java field and the object referred to by o is of a class compatible with that field's class.
        The offset and object reference o (either null or non-null) were both obtained via
            staticFieldOffset(java.lang.reflect.Field) and staticFieldBase(java.lang.Class) (respectively)
            from the reflective java.lang.reflect.Field representation of some Java field.
        The object referred to by o is an array, and the offset is an integer of the form B+N*S,
            where N is a valid index into the array, and B and S are the values obtained by arrayBaseOffset(java.lang.Class)
            and arrayIndexScale(java.lang.Class) (respectively) from the array's class.
            The value referred to is the Nth element of the array.

    If one of the above cases is true, the call references a specific Java variable (field or array element).
    However, the results are undefined if that variable is not in fact of the type returned by this method.

    This method refers to a variable by means of two parameters, and so it provides (in effect)
    a double-register addressing mode for Java variables. When the object reference is null,
    this method uses its offset as an absolute address. This is similar in operation to methods such as getInt(long),
    which provide (in effect) a single-register addressing mode for non-Java variables.
    However, because Java variables may have a different layout in memory from non-Java variables,
    programmers should not assume that these two addressing modes are ever equivalent.
    Also, programmers should remember that offsets from the double-register addressing mode
    cannot be portably confused with longs used in the single-register addressing mode.
*/
                objectAddress = getUnsafe().getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = getUnsafe().getLong(array, baseOffset);
                break;
            default:
                throw new RuntimeException("Unsupported address size: " + addressSize);
        }

        return objectAddress;

    }

    public static void main(String[] args) {
        for (int i = 0; i < 3200000; i++){
            GCMe gcMe = new GCMe();
            long address = addressOf(gcMe);
            System.out.println(address);
        }
    }

    private static class GCMe{
        long data;
        long a;
        long aa;
        long aaa;
        long aaaa;
        long aaaaa;
        long aaaaaa;
        long aaaaaaa;
        long aaaaaaaa;
        long aaaaaaaaa;
        long aaaaaaaaaa;
        long aaaaaaaaaaa;
        long aaaaaaaaaaaa;
        long aaaaaaaaaaaaa;
        long aaaaaaaaaaaaaa;
        long aaaaaaaaaaaaaaa;
        long aaaaaaaaaaaaaaaa;
        long aaaaaaaaaaaaaaaaa;
    }
}
