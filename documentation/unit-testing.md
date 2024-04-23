# 🧪 Unit Testing

___

## Description

The main objective of unit testing is to isolate written code to test and determine if it works as intended. Unit testing 
is an important step in the development process. If done correctly, unit tests can detect early flaws in code which may 
be more difficult to find in later testing stages.

___

## Best Practices

This project abides by majority of best practices presented [here](https://www.baeldung.com/java-unit-testing-best-practices)

___

## Naming conventions

See example below as a template for testing creating test methods

```
    @ANNOTATIONS
    public void {SETUP}_{CONDITION}_{RESULT}() {
        // given
        SETUP DEPENDENCIES & MOCKING

        // when
        PERFORM EXECUTION

        // then
        MAKE ASSERTION(S)
    }
```

## Preferred Test Annotations

Refer to full documentation [here](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)

```
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Mock
@Before
@BeforeEach
@Test
@ParameterizedTest
@MethodSource
```

## Mocking

This project leverages Mockito as its Mocking framework. Mockito is an open source testing framework for Java released 
under the MIT License. The framework allows the creation of test double objects in automated unit tests for the purpose 
of test-driven development or behavior-driven development.

Full Mockito documentation [here](https://javadoc.io/doc/org.mockito/mockito-core/5.7.0/org/mockito/Mockito.html)

Mockito Tutorials [here](https://www.baeldung.com/mockito-series)

## Assertions

This project leverages AssertJ to provide the advantages of fluent assertions. See more info on comparison provided 
[here](https://medium.com/nerd-for-tech/junit-vs-assertj-choosing-the-right-testing-framework-for-your-java-project-84d2664736dc)
