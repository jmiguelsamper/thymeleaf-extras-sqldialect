package org.thymeleafextras.sqldialect.rowbean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.thymeleafextras.sqldialect.rowbean.BeanNameHashCode.hashCodeFor;

/**
 * Creates a RowBean and populates it.
 */
public class RowPopulator {

	private final List<FieldInfo> fieldList;
	private final Map<String, Class<?>> fieldMap;
	private final Class<?> rowBeanType;

	public RowPopulator(FieldInfo... fields) {
		fieldList = Arrays.asList(fields);
		fieldMap = new HashMap<String, Class<?>>();
		populateFieldMap();
		String uniqueClassName = "com.thymeleafextras.sqldialect.RowBean" + hashCodeFor(fieldList);
		rowBeanType = BeanGenerator.getClass(uniqueClassName, fieldMap);
	}

	public int numberOfFields() {
		return fieldList.size();
	}

	public FieldInfo get(int index) {
		return fieldList.get(index);
	}

	private void populateFieldMap() {
		for (FieldInfo field : fieldList) {
			fieldMap.put(field.getFieldName(), field.getFieldType());
		}
	}

	public Object buildRowBean(Object... values) {
		Object rowBean = newRowBeanInstance();
		setFieldValues(rowBean, values);
		return rowBean;
	}

	private Object newRowBeanInstance() {
		try {
			return rowBeanType.newInstance();
		} catch (IllegalAccessException ex) {
			throw new IllegalArgumentException(ex);
		} catch (InstantiationException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	private void setFieldValues(Object rowBean, Object... values) {
		for (int i = 0; i < fieldList.size(); i++) {
			FieldInfo fieldInfo = fieldList.get(i);
			setField(rowBean, fieldInfo.getFieldName(), fieldInfo.getFieldType(), values[i]);
		}
	}

	private void setField(Object rowBean, String fieldName, Class<?> fieldType, Object value) {
		Method set = ReflectionSetterFinder.forClass(rowBeanType).setterLike(fieldName, fieldType);
		try {
			set.invoke(rowBean, value);
		} catch (IllegalAccessException ex) {
			throw new IllegalArgumentException(ex);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(ex);
		} catch (InvocationTargetException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
