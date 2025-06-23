# Code Quality Violations Report

| Type | Title | Description | File | Element | Severity | Score |
|------|-------|-------------|------|---------|----------|-------|
| OCP | Open/Closed Principle Violation | Use of instanceof or role-based conditional logic found. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\AccessChecker.java | IfStmt | MEDIUM | 20 |
| SRP | Single Responsibility Violation | Class Person has 14 methods and 7 fields. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\Person.java | Person | MEDIUM | 21 |
| ISP | Interface Segregation Violation | Interface Person has too many methods. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\Person.java | Person | LOW | 15 |
| LSP | Liskov Substitution Violation | Method process throws UnsupportedOperationException. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\LegacySubClass.java | process | HIGH | 25 |
| DIP | Dependency Inversion Violation | Direct instantiation of concrete class: EmailServiceImpl | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\models\UserHandler.java | EmailServiceImpl | HIGH | 30 |
| SRP | Single Responsibility Violation | Class OrderManager has 22 methods and 25 fields. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\services\OrderManager.java | OrderManager | MEDIUM | 47 |
| ISP | Interface Segregation Violation | Interface OrderManager has too many methods. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\services\OrderManager.java | OrderManager | LOW | 15 |
