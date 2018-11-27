import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println("filterList (minAge=25): " + filterList(persons, 25));
        System.out.println("mapById: " + mapById(persons));
        System.out.println("groupByName: " + groupByName(persons));
        System.out.println("countByName: " + countByName(persons));
        System.out.println("numberOfCarsByName: " + numberOfCarsByName(persons));
        System.out.println("averageAgeByName: " + averageAgeByName(persons));
        System.out.println("namesByAge: " + namesByAge(persons));
        System.out.println("getOldest: " + getOldest(persons).get());
        System.out.println("oldestByName: " + oldestByName(persons));
    }

    private static List<Person> filterList(List<Person> persons, int minAge) {
        return persons.stream().filter(person -> person.getAge() >= minAge).collect(Collectors.toList());
    }

    private static Map<Long, Person> mapById(List<Person> persons) {
        return persons.stream().collect(Collectors.toMap(
                Person::getId,
                Function.identity()
        ));
    }

    private static Map<String, List<Person>> groupByName(List<Person> persons) {
        return persons.stream().collect(
                Collectors.groupingBy(Person::getName)
        );
    }

    private static Map<String, Long> countByName(List<Person> persons) {
        return persons.stream().collect(Collectors.groupingBy(
                Person::getName,
                Collectors.counting()
        ));
    }

    private static Map<String, Integer> numberOfCarsByName(List<Person> persons) {
        return persons.stream().collect(Collectors.groupingBy(
                Person::getName,
                Collectors.summingInt(Person::getNumberOfCars)
        ));
    }

    private static Map<String, Double> averageAgeByName(List<Person> persons) {
        return persons.stream().collect(Collectors.groupingBy(
                Person::getName,
                Collectors.averagingInt(Person::getAge)
        ));
    }

    private static Map<Integer, Set<String>> namesByAge(List<Person> persons) {
        return persons.stream().collect(Collectors.groupingBy(
                Person::getAge,
                Collectors.mapping(Person::getName, Collectors.toSet())
        ));
    }

    private static Optional<Person> getOldest(List<Person> persons) {
        return persons.stream().max(Comparator.comparing(Person::getAge));
    }

    private static Map<String, Person> oldestByName(List<Person> persons) {
        return persons.stream().collect(Collectors.groupingBy(
                Person::getName,
                Collectors.reducing(new Person(-1L, "nemo", -1, 0), BinaryOperator.maxBy(Comparator.comparing(Person::getAge)))
        ));
    }

    private static List<Person> persons = Collections.unmodifiableList(Arrays.asList(
            new Person(1L, "Mike", 24, 2),
            new Person(2L, "Andy", 52, 4),
            new Person(3L, "Bianca", 49, 2),
            new Person(4L, "Mike", 18, 0),
            new Person(5L, "Mike", 4, 0),
            new Person(6L, "Mike", 24, 2),
            new Person(7L, "Andy", 63, 1),
            new Person(8L, "Terry", 24, 0)
    ));
}

class Person {
    private Long id;
    private String name;
    private int age;
    private int numberOfCars;

    Person(Long id, String name, int age, int numberOfCars) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.numberOfCars = numberOfCars;
    }

    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }

    int getNumberOfCars() {
        return numberOfCars;
    }

    Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", name: " + name + ", age: " + age + ", #cars:" + numberOfCars + "}";
    }
}