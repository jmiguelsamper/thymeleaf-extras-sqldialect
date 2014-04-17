package org.thymeleafextras.sqldialect.rowbean;

import java.util.Map;
import java.util.Map.Entry;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import static org.thymeleafextras.sqldialect.rowbean.GetterSetterUtil.getterNameFor;
import static org.thymeleafextras.sqldialect.rowbean.GetterSetterUtil.setterNameFor;

/**
 * Using Javassist bytecode library, creates a Java class with some fields and getters and setters for those fields.
 */
public class BeanGenerator {

    /**
     * Returns existing Class or creates it.
     */
    public static Class getClass(String className, Map<String, Class<?>> properties) {
        try {
            // FIXME: is there a way of checking for existence without using Exception throwing?
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return generate(className, properties);
        }
    }

    /**
     * Creates a class using Javassist.
     */
    private static Class generate(String className, Map<String, Class<?>> fields) {
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(className);
            for (Entry<String, Class<?>> entry : fields.entrySet()) {
                String fieldName = entry.getKey();
                Class<?> fieldType = entry.getValue();
                CtClass fieldCtClass = classPool.get(fieldType.getName());
                ctClass.addField(new CtField(fieldCtClass, fieldName, ctClass));
                ctClass.addMethod(generateGetter(ctClass, fieldName, fieldType));
                ctClass.addMethod(generateSetter(ctClass, fieldName, fieldType));
            }
            return ctClass.toClass();
        } catch (CannotCompileException ex) {
            throw new IllegalArgumentException("Couldn't generate class", ex);
        } catch (NotFoundException ex) {
            throw new IllegalArgumentException("Couldn't generate class", ex);
        }
    }

    /**
     * Creates a setter.
     */
    private static CtMethod generateGetter(CtClass declaringClass, String fieldName, Class fieldClass)
        throws CannotCompileException {
        String methodName = getterNameFor(fieldName);
        String returnType = fieldClass.getName();
        String methodCode =
              "public " + returnType + " " + methodName + "() {"
            + "   return this." + fieldName + ";"
            + "}";
        return CtMethod.make(methodCode, declaringClass);
    }

    /**
     * Creates a getter.
     */
    private static CtMethod generateSetter(CtClass declaringClass, String fieldName, Class fieldClass)
        throws CannotCompileException {
        String methodName = setterNameFor(fieldName);
        String argType = fieldClass.getName();
        String methodCode =
              "public void " + methodName + "(" + argType + " " + fieldName + ") {"
            + "   this." + fieldName + " = " + fieldName + ";"
            + "}";
        return CtMethod.make(methodCode, declaringClass);
    }
}
