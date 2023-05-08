package il.co.lird.FS133.Projects.ZemingoProject;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;

class QuickPopDataStructuresTest {

    QuickPopDataStructures<Person> queue1 = null;
    QuickPushDataStructures<Person> queue2 = null;

    @Test
    void push() throws InterruptedException {
        queue1 = new QuickPopDataStructures<>(new PersonComparator());

        queue1.push(new Person("shani", 31));
        queue1.push(new Person("moshe", 27));
        queue1.push(new Person("lin", 29));

        Iterator<Person> iter = queue1.iterator();

        while (iter.hasNext())
        {
            System.out.println(iter.next().age);
        }

        queue1.pop();

        iter = queue1.iterator();
        while (iter.hasNext())
        {
            System.out.println(iter.next().age);
        }
    }

    @Test
    void iterator() throws InterruptedException {
        queue2 = new QuickPushDataStructures<>(new PersonComparator());

        queue2.push(new Person("shani", 31));
        queue2.push(new Person("moshe", 27));
        queue2.push(new Person("lin", 29));


        System.out.println(queue2.pop().age);
        System.out.println(queue2.pop().age);
        System.out.println(queue2.pop().age);


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