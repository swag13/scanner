# Code Quality Violations Report

| Type | Title | Description | File | Element | Severity | Score |
|------|-------|-------------|------|---------|----------|-------|
| OCP | Open/Closed Principle Violation | Use of instanceof or role-based conditional logic found. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\AccessChecker.java | IfStmt | MEDIUM | 20 |
| SRP | Single Responsibility Violation | Class Person has 14 methods and 7 fields. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\Person.java | Person | MEDIUM | 21 |
| ISP | Interface Segregation Violation | Interface Person has too many methods. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\Person.java | Person | LOW | 15 |
| SENSITIVE_LOG | Sensitive Data in Log Statement | Logging may expose sensitive information: `Password is required` | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\entities\Person.java | Password is required | HIGH | 40 |
| LSP | Liskov Substitution Violation | Method process throws UnsupportedOperationException. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\LegacySubClass.java | process | HIGH | 25 |
| SENSITIVE_LOG | Sensitive Data in Log Statement | Logging may expose sensitive information: `, password='` | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\models\LoginRequest.java | , password=' | HIGH | 40 |
| DIP | Dependency Inversion Violation | Direct instantiation of concrete class: EmailServiceImpl | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\models\UserHandler.java | EmailServiceImpl | HIGH | 30 |
| NO_DI | Manual Dependency Instantiation | Avoid using `new` for service instantiation inside Spring beans. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\models\UserHandler.java | EmailServiceImpl | HIGH | 30 |
| SRP | Single Responsibility Violation | Class OrderManager has 22 methods and 25 fields. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\services\OrderManager.java | OrderManager | MEDIUM | 47 |
| ISP | Interface Segregation Violation | Interface OrderManager has too many methods. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\services\OrderManager.java | OrderManager | LOW | 15 |
| NO_LOG_IN_CATCH | Missing Logging in Catch Block | Exception caught but not logged. | D:\GIT\spring-boot-rest-api-antipatterns\src\main\java\com\swag\myapp\services\OrderService.java | e | HIGH | 35 |
