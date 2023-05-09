# QuickPopDataStructures And QuickPushDataStructures


## Description: 
QuickPopDataStructures and QuickPushDataStructures is a thread-safe data structures that allows you to add elements and pop the highest element. It uses a doubly-linked list to store elements and a comparator to compare them. This class is useful when you need to quickly retrieve the highest element in a collection.


#### QuickPopDataStructures
- Time complexity Push: O(n)
- Time complexity Pop: O(1)

#### QuickPopDataStructures
- Time complexity Push: O(1)
- Time complexity Pop: O(n)


## Usage
To use the QuickPopDataStructures class, first create an instance with a comparator:


``` java
QuickPopDataStructures<Integer> IntegerList = new QuickPopDataStructures<>(new IntegerComparator());

```

Then, you can add elements to the data structure using the push method:
```java
IntegerList.push(3);
IntegerList.push(1);
IntegerList.push(5);
```

You can retrieve the highest element in the data structure using the pop method:

```java
int highest = IntegerList.pop(); // returns 5
```

You can also iterate over the elements in the data structure using a for-each loop:
```java
for (int element : IntegerList) {
    System.out.println(element);
}
```
This will output:
```java
5
3
1
```
## Thread Safety
The QuickPopDataStructures class is thread-safe. The push and pop methods are synchronized to ensure that only one thread can access the data structure at a time.

