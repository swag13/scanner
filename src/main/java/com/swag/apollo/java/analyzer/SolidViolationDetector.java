
package com.swag.apollo.java.analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.swag.apollo.util.RuleViolation;


public class SolidViolationDetector {

    private final List<RuleViolation> violations = new ArrayList<>();

    public List<RuleViolation> run(File projectDir) throws IOException {
        scanDirectory(projectDir);
        return violations;
    }

    private void scanDirectory(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file);
            } else if (file.getName().endsWith(".java")) {
                String content = Files.readString(file.toPath());

                CompilationUnit cu;
                try {
                    cu = StaticJavaParser.parse(content);
                } catch (Exception e) {
                    continue;
                }

                cu.accept(new SRPVisitor(file.getPath()), null);
                cu.accept(new OCPVisitor(file.getPath()), null);
                cu.accept(new LSPVisitor(file.getPath()), null);
                cu.accept(new ISPVisitor(file.getPath()), null);
                cu.accept(new DIPVisitor(file.getPath()), null);
                
                // Add more visitors for Design Patterns anomaly as needed
				cu.accept(new ManualDIVisitor(file.getPath()), null);
				cu.accept(new CatchBlockLoggingVisitor(file.getPath()), null);
				cu.accept(new SensitiveDataLoggingVisitor(file.getPath()), null);
			
            }
        }
    }

    class SRPVisitor extends VoidVisitorAdapter<Void> {
        private final String file;

        SRPVisitor(String file) {
            this.file = file;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration c, Void arg) {
            int methodCount = c.getMethods().size();
            int fieldCount = c.getFields().size();

            if (methodCount > 10 || fieldCount > 8) {
                violations.add(new RuleViolation("SRP", "Single Responsibility Violation",
                    "Class " + c.getNameAsString() + " has " + methodCount + " methods and " + fieldCount + " fields.",
                    file, c.getNameAsString(), "MEDIUM", methodCount + fieldCount, "Design Pattern"));
            }
        }
    }

    class OCPVisitor extends VoidVisitorAdapter<Void> {
        private final String file;

        OCPVisitor(String file) {
            this.file = file;
        }

        @Override
        public void visit(IfStmt stmt, Void arg) {
            if (stmt.toString().contains("instanceof") || stmt.toString().contains("getRole()")) {
                violations.add(new RuleViolation("OCP", "Open/Closed Principle Violation",
                    "Use of instanceof or role-based conditional logic found.",
                    file, "IfStmt", "MEDIUM", 20, "Design Pattern"));
            }
        }
    }

    class LSPVisitor extends VoidVisitorAdapter<Void> {
        private final String file;

        LSPVisitor(String file) {
            this.file = file;
        }

        @Override
        public void visit(MethodDeclaration method, Void arg) {
            method.getBody().ifPresent(body -> {
                if (body.toString().contains("UnsupportedOperationException")) {
                    violations.add(new RuleViolation("LSP", "Liskov Substitution Violation",
                        "Method " + method.getNameAsString() + " throws UnsupportedOperationException.",
                        file, method.getNameAsString(), "HIGH", 25, "Design Pattern"));
                }
            });
        }
    }

    class ISPVisitor extends VoidVisitorAdapter<Void> {
        private final String file;

        ISPVisitor(String file) {
            this.file = file;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration iface, Void arg) {
            if (iface.getMethods().size() > 8) {
                violations.add(new RuleViolation("ISP", "Interface Segregation Violation",
                    "Interface " + iface.getNameAsString() + " has too many methods.",
                    file, iface.getNameAsString(), "LOW", 15, "Design Pattern"));
            }
        }
    }

    class DIPVisitor extends VoidVisitorAdapter<Void> {
        private final String file;

        DIPVisitor(String file) {
            this.file = file;
        }

        @Override
        public void visit(ObjectCreationExpr expr, Void arg) {
            String type = expr.getType().getNameAsString();
            if (type.endsWith("Impl")) {
                violations.add(new RuleViolation("DIP", "Dependency Inversion Violation",
                    "Direct instantiation of concrete class: " + type,
                    file, type, "HIGH", 30, "Design Pattern"));
            }
        }
    }
    
    class ManualDIVisitor extends com.github.javaparser.ast.visitor.VoidVisitorAdapter<Void> {
		private final String file;
		ManualDIVisitor(String file) { this.file = file; }

		@Override
		public void visit(ObjectCreationExpr expr, Void arg) {
			if (expr.getType().getNameAsString().endsWith("ServiceImpl")) {
				violations.add(new RuleViolation(
						"NO_DI",
						"Manual Dependency Instantiation",
						"Avoid using `new` for service instantiation inside Spring beans.",
						file,
						expr.getType().asString(),
						"HIGH",
						30, "Design Pattern"
						));
			}
		}
	}

	class CatchBlockLoggingVisitor extends com.github.javaparser.ast.visitor.VoidVisitorAdapter<Void> {
		private final String file;
		CatchBlockLoggingVisitor(String file) { this.file = file; }

		@Override
		public void visit(CatchClause catchClause, Void arg) {
			if (!catchClause.getBody().toString().contains("log")) {
				violations.add(new RuleViolation(
						"NO_LOG_IN_CATCH",
						"Missing Logging in Catch Block",
						"Exception caught but not logged.",
						file,
						catchClause.getParameter().getNameAsString(),
						"HIGH",
						35, "Observability"
						));
			}
		}
	}

	class SensitiveDataLoggingVisitor extends com.github.javaparser.ast.visitor.VoidVisitorAdapter<Void> {
		private final String file;
		SensitiveDataLoggingVisitor(String file) { this.file = file; }

		@Override
		public void visit(StringLiteralExpr expr, Void arg) {
			String value = expr.asString().toLowerCase();
			if (value.contains("password") || value.contains("secret") || value.contains("token")) {
				violations.add(new RuleViolation(
						"SENSITIVE_LOG",
						"Sensitive Data in Log Statement",
						"Logging may expose sensitive information: `" + expr.asString() + "`",
						file,
						expr.asString(),
						"HIGH",
						40, "Security"
						));
			}
		}
	}
}
