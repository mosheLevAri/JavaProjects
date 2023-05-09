package il.co.lird.FS133.Projects.ZemingoProject;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuickPopDataStructuresTest {


    @Test
    void pushAndPop() throws InterruptedException {
        QuickPopDataStructures<Person> popQueue = new QuickPopDataStructures<>(new PersonComparator());
        QuickPushDataStructures<Person> pushQueue = new QuickPushDataStructures<>(new PersonComparator());

        pushQueue.push(new Person("Alice", 25));
        pushQueue.push(new Person("Bob", 30));
        pushQueue.push(new Person("Charlie", 20));

        assertEquals(30, pushQueue.pop().age);
        assertEquals(25, pushQueue.pop().age);
        assertEquals(20, pushQueue.pop().age);


    }

    @Test
    void pushAndPopConcurrently() throws InterruptedException {
        final QuickPopDataStructures<Person> popQueue = new QuickPopDataStructures<>(new PersonComparator());
        final QuickPushDataStructures<Person> pushQueue = new QuickPushDataStructures<>(new PersonComparator());
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            pushQueue.push(new Person("Alice", 25));
            pushQueue.push(new Person("Bob", 30));
            pushQueue.push(new Person("Charlie", 20));
        });

        executor.execute(() -> {
            assertEquals(new Person("Charlie", 20), popQueue.pop());
            assertEquals(new Person("Alice", 25), popQueue.pop());
            assertEquals(new Person("Bob", 30), popQueue.pop());
            assertNull(popQueue.pop());

        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void pushAndPopConcurrentlyWithMultipleThreads() throws InterruptedException {
        final QuickPopDataStructures<Person> popQueue = new QuickPopDataStructures<>(new PersonComparator());
        final QuickPushDataStructures<Person> pushQueue = new QuickPushDataStructures<>(new PersonComparator());
        final ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.execute(() -> {
            pushQueue.push(new Person("Alice", 25));
            pushQueue.push(new Person("Bob", 30));
            pushQueue.push(new Person("Charlie", 20));
            pushQueue.push(new Person("David", 35));
            pushQueue.push(new Person("Eve", 22));
        });

        executor.execute(() -> {

            assertEquals(new Person("Charlie", 20), popQueue.pop());
            assertEquals(new Person("Eve", 22), popQueue.pop());

        });

        executor.execute(() -> {
            assertEquals(new Person("Alice", 25), popQueue.pop());
            assertEquals(new Person("Bob", 30), popQueue.pop());
            assertEquals(new Person("David", 35), popQueue.pop());
            assertNull(popQueue.pop());

        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }


    class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return p1.getAge() - (p2.getAge());
        }
    }

}