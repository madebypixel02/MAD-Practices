<!-- *********************************************************************** -->
<!--                                                                         -->
<!--                                         =@@*   +@@+                     -->
<!--                                         =@@*   +@@+ :*%@@@%*:           -->
<!--                                         =@@*   =@@+.@@@=--%@@-          -->
<!--                                         :@@%. .#@@--@@*   +@@* .+%@@@   -->
<!-- README.md                                =%@@@@@@+ =@@*   =@@+.@@@+-=   -->
<!--                                            .---:   -@@#.  *@@--@@*      -->
<!-- By: aperez-b <aperez-b@uoc.edu>                     +@@@@@@@* +@@+      -->
<!--                                                       :-==:.  -@@#      -->
<!-- Created: 2023/10/16 15:12:19 by aperez-b                       +@@@%@   -->
<!-- Updated: 2023/10/16 15:12:27 by aperez-b                                -->
<!--                                                                         -->
<!-- *********************************************************************** -->

# CAT 1

## Table of Contents

- [Exercise 1](#exercise-1)
- [Exercise 2](#exercise-2)
- [Exercise 3](#exercise-3)
- [Exercise 4](#exercise-4)
- [Exercise 5](#exercise-5)

## Exercise 1

### a)

To enable full-screen (immersive) mode on an Android application, the minimum API Level where this feature is available is API Level 19 (Android 4.4 KitKat).

### b)

According to the [Android Developers Documentation](https://developer.android.com/reference/android/database/sqlite/package-summary), the use of a local SQLite database (android.database.sqlite) has been available since API Level 1, all the way to the current latest API Level (31). The version of SQLite changes with every API Level.

## Exercise 2

### a)

```kotlin
var name : String = " hello "
name = null
```

#### Problem

`name` is not nullable, so trying to make it null will throw an error.

#### Solution

```kotlin
var name : String? = " hello "
name = null
```

Now, the `name` variable can be set to `null`.

### b)

```kotlin
val v1 : Int = 5
v1 = 10
```

#### Problem

`v1` is a **constant value**, so trying to change its value will throw an error.

#### Solution

```kotlin
var v1 : Int = 5
v1 = 10
```

Now, the `v1` variable can be reassigned.

### c)

```kotlin
var v2 : String ? = null
var v3 : Any = "cat"
v2 = v3
```

#### Problem

Cannot assign the value of `v3` of type **Any** to a variable `v2` of type **String**. In order to assign the value, a cast operation must be called to ensure that `v2` receives a **String**.

#### Solution

```kotlin
var v2 : String? = null
var v3 : Any = "cat"
v2 = v3.toString()
```

Now, the `v2` variable can be set to the **String** value of `v3`.

## Exercise 3

### a)

```kotlin
val shopping_list = ArrayList<String>()
shopping_list.add("Apples")
shopping_list.add("Bananas")
shopping_list.add("Watermelon")
shopping_list.add("Oranges")
shopping_list.add("Pineapple")
shopping_list.removeAt(0)
```

### b)

```kotlin
val dic =  HashMap<Int, String>()
dic[1] = "value1"
dic[2] = "value2"
for ((key, value) in dic) {
    Log.i("$key","$value")
}
```

## Exercise 4

### a)

```kotlin
class Seminary(pname: String, pduration: Int) {
    var _name: String = pname
    var _duration: Int = pduration
}
```

Note: to match the usual standard, private attributes are preceded by an underscore `_`.

### b)

```kotlin
class User(pid: Int) {
    var _name: String = "Unknown"
    var _id: Int = pid
    var _attendance: ArrayList<Seminary>
}
```

### c)

Here is the updated `User` class.

```kotlin
class User(pid: Int) {
    var _name: String = "Unknown"
    var _id: Int = pid
    var _attendance: ArrayList<Seminary>
    fun addSeminary(d:Seminary) {
        _attendance.add(d)
    }
}
```

## Exercise 5

### a)

```kotlin
var user1 = User(8)
```

### b)

```kotlin
var seminary1 = Seminary("AI for dummies", 50)
```

### c)

```kotlin
user1.addSeminary(seminary1)
```

October 16th, 2023
