# How to Order Versioned File Names Semantically in Java

In most cases, natural sorting by sorting lexicographically is useful as a default in Java. 
This includes sorting file names, which are sorted lexicographically as well.

However, when we have version numbers in our files (such as a set of SQL migration scripts), 
then we prefer the files to be sorted in a more intuitive ordering, where the version numbers 
contained in the string become "semantic". 

In the following example, we have a set of file names with versions, one sorted "naturally", and one 
"semantically" sorted:

### Natural sorting:

- version-1
- version-10
- version-10.1
- version-2
- version-21

### Semantic sorting:

- version-1
- version-2
- version-10
- version-10.1
- version-21

The JDK doesn’t seem to have a built-in `Comparator` that implements this ordering, but we can easily roll our own. 
The idea is simple. 
We want to split a file name into several chunks, where a chunk is either a string (sorted lexicographically), 
or an integer number (sorted numerically). We split that file name using a regular expression:

```java
Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
```

This expression matches the boundary between string and number, without actually capturing anything, 
so we can use it for `split()` operations.

The algorithm is rather simple as it does not distinguish between file endings and "segments", 
so (1) is compared with (sql), which might not be the desired behaviour. 
This can be easily fixed by recognising actual file endings and excluding them from the 
comparison logic – at the price of not being able to sort files without file endings.

## Software Used

Written and tested with:

- Java 9
- Gradle 4
- Junit 5
- AssertJ 3