/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-3-8 下午12:43:09
 */
package com.absir.core.dyna;

import com.absir.core.kernel.*;
import com.absir.core.kernel.KernelArray.ArrayAccessor;
import com.absir.core.kernel.KernelLang.BreakException;

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DynaBinder {

    public static final DynaBinder INSTANCE = new DynaBinder();

    protected final List<? extends DynaConvert> converts = new ArrayList<DynaConvert>();

    public static <T> T to(Object obj, Class<T> toClass) {
        return to(obj, null, toClass);
    }

    public static <T> T to(Object obj, String name, Class<T> toClass) {
        return INSTANCE.bind(obj, name, toClass);
    }

    public static void to(Object[] parameters, Class<?>[] parameterTypes) {
        int length = parameterTypes.length;
        for (int i = 0; i < length; i++) {
            parameters[i] = to(parameters[i], parameterTypes[i]);
        }
    }

    public static <T> T mapTo(Map map, Class<T> toClass) {
        return mapTo(map, null, toClass);
    }

    public static <T> T mapTo(Map map, String name, Class<T> toClass) {
        return (T) INSTANCE.mapBind(map, name, toClass);
    }

    public static void mapTo(Map map, Object toObject) {
        INSTANCE.mapBind(map, toObject);
    }

    public static <T extends Collection> Class<T> toCollectionClass(Class<T> toClass) {
        if (toClass.isAssignableFrom(ArrayList.class)) {
            toClass = (Class<T>) ArrayList.class;

        } else if (toClass.isAssignableFrom(HashSet.class)) {
            toClass = (Class<T>) HashSet.class;
        }

        return toClass;
    }

    public static <T extends Map> Class<T> toMapClass(Class<T> toClass) {
        if (toClass.isAssignableFrom(HashMap.class)) {
            toClass = (Class<T>) HashMap.class;
        }

        return toClass;
    }

    public List<? extends DynaConvert> getConverts() {
        return converts;
    }

    public void addConvert(DynaConvert dynaConvert) {
        KernelList.addOrderObject(converts, dynaConvert);
    }

    public void removeConvert(DynaConvert dynaConvert) {
        converts.remove(dynaConvert);
    }

    public Object bind(Object obj, String name, Type toType) {
        return bind(obj, name, toType, null);
    }

    public <T> T bind(Object obj, String name, Type toType, T toObject) {
        if (toType == null || toType instanceof TypeVariable) {
            return (T) obj;
        }

        if (toObject == null && toType != null) {
            toObject = (T) bindTo(obj, name, toType);
            if (toObject != null) {
                return toObject;
            }
        }

        if (toType instanceof Class) {
            if (toObject == null || !(obj instanceof Map)) {
                return (T) bind(obj, name, (Class) toType);
            }

            mapBind((Map) obj, toObject);
            return toObject;
        }

        if (toType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) toType;
            toType = type.getRawType();
            if (toType instanceof Class) {
                Class toClass = (Class) toType;
                if (Collection.class.isAssignableFrom(toClass)) {
                    return (T) bindCollection(obj, name, toClass, KernelArray.get(type.getActualTypeArguments(), 0),
                            (Collection) toObject);

                } else if (Map.class.isAssignableFrom(toClass)) {
                    if (obj instanceof Map) {
                        Type[] typeArguments = type.getActualTypeArguments();
                        return (T) bindMap(obj, name, null, toClass, KernelArray.get(typeArguments, 1),
                                KernelArray.get(typeArguments, 0), (Map) toObject);
                    }
                }
            }

        } else if (toType instanceof GenericArrayType) {
            GenericArrayType type = (GenericArrayType) toType;
            type.getGenericComponentType();
        }

        return null;
    }

    protected Object bindTo(Object obj, String name, Type toType) {
        return null;
    }

    protected <T> T newInstance(Class<T> toClass) {
        return KernelClass.declaredNew(toClass);
    }

    protected <T> T nullTo(Class<T> toClass, Object obj) {
        T toObject = (T) KernelClass.instanceOf(toClass, obj);
        if (toObject == null) {
            return KernelDyna.nullTo(toClass);
        }

        return toObject;
    }

    public <T> T bind(Object obj, String name, Class<T> toClass) {
        if (obj == null) {
            return KernelDyna.nullTo(toClass);
        }

        if (toClass.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        }

        return bindTo(obj, name, toClass);
    }

    protected <T> T bindTo(Object obj, String name, Class<T> toClass) {
        T toObject = null;
        if (obj instanceof String) {
            toObject = KernelDyna.stringTo((String) obj, toClass, KernelDyna.DYNAS_NULL);
            if (toObject == KernelLang.NULL_OBJECT) {
                toObject = null;

            } else if (toObject == null) {
                return null;
            }

        } else if (KernelClass.isBasicClass(obj.getClass())) {
            toObject = KernelDyna.to(obj, toClass);
        }

        if (toObject == null) {
            if (toClass.isArray()) {
                toObject = (T) bindArray(obj, name, toClass, toClass.getComponentType());

            } else if (Collection.class.isAssignableFrom(toClass)) {
                Type[] types = KernelClass.argumentTypes(toClass, true);
                int length = types.length;
                toObject = (T) bindCollection(obj, name, (Class<? extends Collection>) toClass,
                        length > 0 ? types[0] : null, null);

            } else if (Map.class.isAssignableFrom(toClass)) {
                Type[] types = KernelClass.argumentTypes(toClass, true);
                int length = types.length;
                toObject = (T) bindMap(obj, name, null, (Class<? extends Map>) toClass, length > 0 ? types[0] : null,
                        length > 1 ? types[1] : null, null);

            } else if (obj instanceof Map) {
                toObject = (T) mapBind((Map) obj, name, toClass);
            }

            if (toObject == null) {
                toObject = bindConvert(obj, name, toClass, KernelDyna.DYNAS_NULL);
                if (toObject == KernelLang.NULL_OBJECT) {
                    toObject = null;

                } else if (toObject == null) {
                    return null;
                }

                if (toObject == null) {
                    if (CharSequence.class.isAssignableFrom(toClass)) {
                        return (T) obj.toString();
                    }

                    toObject = nullTo(toClass, obj);
                }
            }
        }

        return toObject;
    }

    protected <T> T bindConvert(Object obj, String name, Class<T> toClass, boolean[] dynas) {
        return bindConvert(obj, name, toClass, converts, dynas);
    }

    protected <T> T bindConvert(Object obj, String name, Class<T> toClass, List<? extends DynaConvert> converts,
                                boolean[] dynas) {
        T toObject = null;
        BreakException breakException = null;
        for (DynaConvert convert : converts) {
            try {
                toObject = (T) convert.to(obj, name, toClass, breakException);
                if (toObject != null) {
                    return toObject;
                }

            } catch (BreakException e) {
                breakException = e;

            } catch (Exception e) {
                break;
            }
        }

        if (breakException == null) {
            if (dynas == KernelDyna.DYNAS_NULL) {
                return (T) KernelLang.NULL_OBJECT;
            }

            if (dynas != null && dynas.length > 0) {
                dynas[0] = !dynas[0];
            }
        }

        return toObject;
    }

    public void mapBind(Map<?, ?> map, Object toObject) {
        if (toObject != null) {
            Class toClass = toObject.getClass();
            while (toClass != null) {
                List<Field> fields = KernelReflect.declaredFields(toClass);
                for (Field field : fields) {
                    Object value = mapField(map, field);
                    if (value != KernelLang.NULL_OBJECT) {
                        if (KernelReflect.memberAccessor(field) != null) {
                            Method method = KernelClass.setter(toObject.getClass(), field);
                            if (method != null || Modifier.isPublic(field.getModifiers())) {
                                value = bind(value, field, KernelObject.publicGetter(toObject, field), toObject);
                                if (method == null) {
                                    KernelReflect.set(toObject, field, value);

                                } else {
                                    KernelReflect.invoke(toObject, method, value);
                                }
                            }
                        }
                    }
                }

                toClass = toClass.getSuperclass();
            }
        }
    }

    protected Object mapField(Map<?, ?> map, Field field) {
        Object value = map.get(field.getName());
        if (value == null) {
            if (!map.containsKey(field.getName())) {
                value = KernelLang.NULL_OBJECT;
            }
        }

        return value;
    }

    public <T> T mapBind(Map<?, ?> map, String name, Class<T> toClass) {
        T toObject = mapBindConvert(map, name, toClass);
        if (toObject == null) {
            toObject = newInstance(toClass);
        }

        mapBind(map, toObject);
        return toObject;
    }

    protected <T> T mapBindConvert(Map<?, ?> map, String name, Class<T> toClass) {
        return mapBindConvert(map, name, toClass, converts, new BreakException[1]);
    }

    protected <T> T mapBindConvert(Map<?, ?> map, String name, Class<T> toClass, List<? extends DynaConvert> converts,
                                   BreakException[] breakExceptions) {
        T toObject = null;
        BreakException breakException = breakExceptions[0];
        for (DynaConvert convert : converts) {
            try {
                toObject = (T) convert.mapTo(map, name, toClass, breakException);
                if (toObject != null) {
                    break;
                }

            } catch (BreakException e) {
                breakException = e;

            } catch (Exception e) {
                break;
            }
        }

        breakExceptions[0] = breakException;
        return toObject;
    }

    protected Object bind(Object obj, Field field, Object toObject, Object object) {
        String name = null;
        return bind(obj, name, field.getGenericType(), toObject);
    }

    protected String[] splitString(String str) {
        return str.split(",");
    }

    protected <T> T bindArray(Object obj, String name, Class<T> toClass, Type toType) {
        ArrayAccessor array = KernelArray.forClass(toClass);
        if (obj instanceof Collection) {
            Object toObject = array.newInstance(((Collection) obj).size());
            if (toObject != null) {
                int index = 0;
                for (Object o : (Collection) obj) {
                    bindArrayTo(index);
                    array.set(toObject, index++, bind(o, name, toType));
                }
            }

            return (T) toObject;

        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            Object toObject = array.newInstance(length);
            if (toObject != null) {
                ArrayAccessor ary = KernelArray.forClass(obj.getClass());
                for (int i = 0; i < length; i++) {
                    bindArrayTo(i);
                    array.set(toObject, i, bind(ary.get(obj, i), name, toType));
                }
            }

            return (T) toObject;

        } else {
            if (obj.getClass() == String.class) {
                return to(splitString((String) obj), name, toClass);
            }

            Object toObject = array.newInstance(1);
            if (toObject != null) {
                bindArrayTo(0);
                array.set(toObject, 0, bind(obj, name, toType));
            }

            return (T) toObject;
        }
    }

    protected Collection toCollection(Class<? extends Collection> toClass, Collection toObject) {
        if (toObject != null) {
            try {
                toObject.clear();

            } catch (Exception e) {
                toObject = null;
            }
        }

        if (toObject == null) {
            toObject = newInstance(toCollectionClass(toClass));
        }

        return toObject;
    }

    protected <T extends Collection> T bindCollection(Object obj, String name, Class<T> toClass, Type toType,
                                                      Collection toObject) {
        toObject = toCollection(toClass, toObject);
        if (toObject != null) {
            if (obj instanceof Collection) {
                int index = 0;
                for (Object ob : (Collection) obj) {
                    bindArrayTo(index++);
                    toObject.add(bind(ob, name, toType));
                }

            } else if (obj.getClass().isArray()) {
                ArrayAccessor array = KernelArray.forClass(obj.getClass());
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    bindArrayTo(i);
                    toObject.add(bind(array.get(obj, i), name, toType));
                }

            } else {
                bindArrayTo(0);
                toObject.add(bind(obj, name, toType));
            }
        }

        return (T) toObject;
    }

    protected <T extends Map> T bindMap(Object obj, String name, String keyName, Class<T> toClass, Type toType,
                                        Type keyType, Map toObject) {
        if (obj instanceof Map) {
            return bindMap((Map) obj, name, keyName, toClass, toType, keyType, toObject);
        }

        return null;
    }

    protected <T extends Map> T bindMap(Map obj, String name, String keyName, Class<T> toClass, Type toType,
                                        Type keyType, Map toObject) {
        if (toObject != null) {
            try {
                toObject.clear();

            } catch (Exception e) {
                toObject = null;
            }
        }

        if (toObject == null) {
            toObject = newInstance(toMapClass(toClass));
        }

        if (toObject != null) {
            toObject.clear();
            for (Iterator<Entry> it = obj.entrySet().iterator(); it.hasNext(); ) {
                Entry entry = it.next();
                Object key = bind(entry.getKey(), keyName, keyType);
                if (key != null) {
                    bindMapTo(key);
                    toObject.put(key, bind(entry.getValue(), name, toType));
                }
            }
        }

        return (T) toObject;
    }

    protected void bindArrayTo(int index) {
    }

    protected void bindMapTo(Object key) {
    }
}
