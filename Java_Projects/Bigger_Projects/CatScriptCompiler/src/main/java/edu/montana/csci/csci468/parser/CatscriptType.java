package edu.montana.csci.csci468.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CatscriptType {

    public static final CatscriptType INT = new CatscriptType("int", Integer.class);
    public static final CatscriptType STRING = new CatscriptType("string", String.class);
    public static final CatscriptType BOOLEAN = new CatscriptType("bool", Boolean.class);
    public static final CatscriptType OBJECT = new CatscriptType("object", Object.class);
    public static final CatscriptType NULL = new CatscriptType("null", Object.class);
    public static final CatscriptType VOID = new CatscriptType("void", Object.class);

    private static final Map<CatscriptType, CatscriptType> listTypeCache = new HashMap<>();

    private final String name;
    private final Class javaClass;

    public CatscriptType(String name, Class javaClass) {
        this.name = name;
        this.javaClass = javaClass;
    }

    public boolean isAssignableFrom(CatscriptType type) {
        if (this == VOID) {
            return type == VOID;
        } else if (type == NULL) {
            return true;
        } else if (this instanceof ListType && type instanceof ListType) {
            // Compare inner component types
            return ((ListType) this).componentType.isAssignableFrom(((ListType) type).componentType);
        } else if (this.javaClass.isAssignableFrom(type.javaClass)) {
            return true;
        }
        return false;
    }

    // TODO memoize this call
    public static CatscriptType getListType(CatscriptType type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot create a list type for null.");
        }
        // Use cache to ensure only one instance for each component type
        return listTypeCache.computeIfAbsent(type, ListType::new);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }else if (o == null || getClass() != o.getClass()){
            return false;
        }
        CatscriptType that = (CatscriptType) o;
        return Objects.equals(name, that.name);
    }



    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Class getJavaType() {
        return javaClass;
    }

    public static class ListType extends CatscriptType {
        private final CatscriptType componentType;
        public ListType(CatscriptType componentType) {
            super("list<" + componentType.toString() + ">", List.class);
            this.componentType = componentType;
        }

        @Override
        public boolean isAssignableFrom(CatscriptType type) {
            if (type == NULL) {
                return true;
            } else if (type instanceof ListType) {
                return this.componentType.isAssignableFrom(((ListType) type).componentType);
            }
            return false;
        }

        public CatscriptType getComponentType() {
            return componentType;
        }

        @Override
        public String toString() {
            return super.toString() + "<" + componentType.toString() + ">";
        }
    }

}
