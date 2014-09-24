package pl.mpieciukiewicz.easycsv.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ObjectConstructionUtil {

    private sun.misc.Unsafe unsafeObject = getPreparedUnsafeObject();

    /**
     * Creates instance of Array of elements of given type.
     *
     * @param elementsType type of elements
     * @param size         size of the created array
     * @return created Array object
     */
    Object[] createArrayInstance(Class<?> elementsType, int size) {
        return (Object[]) java.lang.reflect.Array.newInstance(elementsType, size);
    }


    /**
     * Creates instance of the given class.
     *
     * @param clazz type of object o create
     * @return created object
     */
    <T> T createInstance(Class<T> clazz) {
        try {
            // try to create object using default constructor
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            //otherwise create object without calling constructor
            return createInstanceWithoutCallingConstructor(clazz);
        } catch (InvocationTargetException e) {
            throw unsupportedException(e);
        } catch (InstantiationException e) {
            throw unsupportedException(e);
        } catch (IllegalAccessException e) {
            throw unsupportedException(e);
        }
    }

    private sun.misc.Unsafe getPreparedUnsafeObject() {
        try {
            Class<?> unsafeClass = sun.misc.Unsafe.class;
            Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            return (sun.misc.Unsafe) theUnsafeField.get(null);
        } catch (IllegalAccessException e) {
            throw unsupportedException(e);
        } catch (NoSuchFieldException e) {
            throw unsupportedException(e);
        }
    }

    private <T> T createInstanceWithoutCallingConstructor(Class<T> clazz) {
        try {
            return (T) unsafeObject.allocateInstance(clazz);
        } catch (Exception e) {
            throw unsupportedException(e);
        }
    }

    private IllegalStateException unsupportedException(Exception e) {
        return new IllegalStateException("Cannot create object without calling constructor\n" +
                "That's probably because JRE is not sun/oracle implementation", e);
    }


}
