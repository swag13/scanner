package com.swag.apollo.util;

public class JavaParserSupportsRecords {
    public static boolean isSupported() {
        try {
            Class.forName("com.github.javaparser.ast.body.RecordDeclaration");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
