## a) Identify a couple of examples that use AssertJ expressive methods chaining.
On class C_EmployeeController_WithMockServiceTest the method given3Employees_whengetAll_thenReturn3Records() has the line:

    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());

And on class E_EmployeeRestControllerTemplateIT the method givenEmployees_whenGetEmployees_thenStatus200() has the line:

    assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");


## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).
In class B_EmployeeService_UnitTest the repository is mocked and the expected responses are written in the setup, so no database is used for consulting results.

## c) What is the difference between standard @Mock and @MockBean?
@MockBean injects mocks in the application context and in the fields, replacing the service already in the application context for the mocked one. @Mock doesn't work with the application context, it only serves to mock an object and return expected values.


## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
The role of this file is to establish a external data source for integration tests that can be used in place of the automatic one. For example, in case of D and E, we can use an external MySql database instead of the H2 auto configured one.


## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

The main key differences is the need to use an explicit HTTP client for testing the REST API.