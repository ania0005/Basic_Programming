package task_5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Alex", "Developer", 1500));
        employees.add(new Employee("Thomas", "Manager", 680.50));
        employees.add(new Employee("Thea", "CEO", 2000));
        employees.add(new Employee("Mary", "Manager", 700));
        employees.add(new Employee("Ken", "Developer", 900));

        //        0. Создать класс Employee
//        1. Вывести всех сотрудников без высшего руководителя.
//        2. Сгруппировать сотрудников по должности // map: position - list name
//        3. Вычислить среднюю зарплату всех сотрудников (двумя способами)
//        4. Получить список сотрудников с зп > 1000
//        5. Получить список сотрудников из отдела маркетинг и увеличить зп на 15%
//        6. Получить сотрудника с самой низкой зп
//        7. Получите сотрудников из всех отделов с максимальной зп

        //        1. Вывести всех сотрудников без высшего руководителя
List<Employee> result1 = employees.stream()
                .filter(x -> !x.getPosition().equals("CEO"))
                .toList();
        System.out.println("Task 1");
        result1.forEach(System.out::println);

        //        2. Сгруппировать сотрудников по должности // map: position - list name
        Map<String, List<Employee>> result2 = employees.stream()
                .collect(Collectors.groupingBy(x -> x.getPosition()));

        // print variant1
        //System.out.println();
        //System.out.println("Task 2");
        //result2.forEach((x, y) -> System.out.println(x + ": " + y));
        //System.out.println(result2);

        // print variant2
        System.out.println();
        System.out.println("Task 2");
        result2.forEach((x, y) -> {
            System.out.println(x);
            y.forEach(System.out::println);
        });

        //        3. Вычислить среднюю зарплату всех сотрудников (двумя способами).

        double averagSalary = employees.stream()
                .mapToDouble(x -> x.getSalary())
                .average()
                .orElse(0.0);

        System.out.println();
        System.out.println("Task 3");
        System.out.println("Cредняя зарплата всех сотрудников - " + averagSalary);

        //        4. Получить список сотрудников с зп > 1000

        List<Employee> salaryMore100List = employees.stream()
                .filter(x ->x.getSalary() > 1000)
                .collect(Collectors.toList());

        System.out.println();
        System.out.println("Task 4");
        System.out.println("Cписок сотрудников с зп > 1000");
        salaryMore100List.forEach(System.out::println);

        //        5. Получить список сотрудников из отдела маркетинг и увеличить зп на 15%

List<Employee> salaryManager = employees.stream()
        .filter(x -> x.getPosition().equals("Manager"))
        .peek((x) -> x.setSalary(x.getSalary() + (x.getSalary() * 0.15)))
        .toList();

        System.out.println();
        System.out.println("Task 5");
        System.out.println("Cписок сотрудников из отдела маркетинг c зп увеличеной на 15%");
        salaryManager.forEach(System.out::println);

//        6. Получить сотрудника с самой низкой зп

        Employee employeeWithMinSalary = employees.stream()
                .min((x, y) -> Double.compare(x.getSalary(), y.getSalary()))
                .get();

        System.out.println();
        System.out.println("Task 6");
        System.out.println("Cотрудник с самой низкой зп:");
        System.out.println(employeeWithMinSalary);

//        7. Получите сотрудников из всех отделов с максимальной зп
        
       //  а) Находим максимальную зарплату в каждом отделе
        Map<String, Double> maxSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition,
                        Collectors.mapping(Employee::getSalary, Collectors.collectingAndThen(
                                Collectors.maxBy(Double::compare), max -> max.orElse(0.0)))));
        
        // b) Находим сотрудников с максимальной зарплатой в каждом отделе
        List<Employee> employeesWithMaxSalary = employees.stream()
                .filter(employee -> employee.getSalary() == maxSalary.get(employee.getPosition()))
                .collect(Collectors.toList());

        System.out.println();
        System.out.println("Task 7");
        System.out.println("Сотрудники из всех отделов с максимальной зарплатой:");
        employeesWithMaxSalary.forEach(System.out::println);
    }
}
