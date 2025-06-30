
package com.swag.apollo.java.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.swag.apollo.util.RuleViolation;

public class JavaSourceArchitectureScanner {

	private final List<RuleViolation> violations = new ArrayList<>();

    public List<RuleViolation> run(String projectDir) throws IOException {
        scanDirectory(projectDir);
        return violations;
    }

    public static List<RuleViolation> scanDirectory(String baseDir) throws IOException {
        List<RuleViolation> violations = new ArrayList<>();

        Files.walk(Paths.get(baseDir))
            .filter(p -> p.toString().endsWith(".java"))
            .forEach(path -> {
                try {
                    CompilationUnit cu = StaticJavaParser.parse(path);
                    cu.accept(new VoidVisitorAdapter<Void>() {
                        @Override
                        public void visit(ClassOrInterfaceDeclaration clazz, Void arg) {
                            super.visit(clazz, arg);

                            String className = clazz.getNameAsString();
                            String filePath = path.toString();
                            List<String> imports = cu.getImports().stream()
                                .map(ImportDeclaration::getNameAsString)
                                .toList();

                            boolean isController = className.toLowerCase().contains("controller");
                            boolean isService = className.toLowerCase().contains("service");
                            boolean accessesRepo = imports.stream().anyMatch(i -> i.contains(".repository.") || i.endsWith("Repository"));
                            boolean accessesJdbc = imports.stream().anyMatch(i -> i.contains("java.sql") || i.contains("javax.sql") || i.contains(".jdbc."));

                            if (isController && accessesRepo) {
                                violations.add(new RuleViolation(
                                    "ARCH_CTRL_REPO",
                                    "Controller-Repository Coupling",
                                    "Controller should not access Repository directly",
                                    filePath, className, "HIGH", 25
                                ));
                            }

                            if (isService && (accessesRepo || accessesJdbc)) {
                                violations.add(new RuleViolation(
                                    "ARCH_SRVC_DB",
                                    "Service-DB Coupling",
                                    "Service should access DB via DAO layer, not directly",
                                    filePath, className, "MEDIUM", 20
                                ));
                            }
                        }
                    }, null);
                } catch (IOException e) {
                    System.err.println("Error reading: " + path + " - " + e.getMessage());
                }
            });

        return violations;
    }
}
