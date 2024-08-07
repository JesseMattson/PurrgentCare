# 🃏 Fake Data Generation

---

### Description

Research open source fake data generators


---

## Options

1. [JavaFaker]
2. [Datafaker]
3. [Mockito]
4. [Instancio]

---

### [JavaFaker] & [Datafaker]

Mainly function as a libraries that provides a wide array of data for different categories/types.
Seem to be missing a straightforward way to create fake custom classes/nested classes.

Though they could be used to create custom classes that have default properties assigned,
it could be difficult to navigate fields without setters,etc.

---

### [Mockito]

Provides the mock() method which can be used in conjunction
with when()/given() to control what the mock returns.
However, it may be a bit clunky to set up for our classes.

---

### [Instancio]

This seems like the most promising open source option.
It provides methods for creating objects using Models which function like templates for objects.
The Models can be made to provide default values for each object property, including lists of other
classes, that can be overridden in the test during the creation of the model.
This should make it a little easier to setup Models for each of our custom classes which should
streamline the code for tests and cut down on repeated code.

See [Using Models](https://www.instancio.org/user-guide/#using-models] section) of the User Guide

---

[Javafaker]: https://github.com/DiUS/java-faker

[Datafaker]: https://www.datafaker.net/

[Mockito]: https://site.mockito.org/

[Instancio]: https://www.instancio.org/user-guide/

