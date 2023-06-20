# Evaluación JAVA

**Autor**: Francisco Yanes.

## Introducción

Este es un ejercicio de evaluación de JAVA requerido para ingresar al projecto de cierto cliente de GlobalLogic.

El ejercicio consta del desarrollo de un microservicio para la creación y consulta de usuarios, usando el stack tecnológico requerido por el cliente. Los detalles se pueden encontrar en [el enunciado](project-presentation/enunciado-ejercicio.pdf).

## Tecnologías Utilizadas

- JAVA 8
- Spring Boot / Gradle
- Spring Data JPA
- Spring Validation
- Spring Web
- Spring Test
- Spock / Groovy & Java
- Lombok
- JWT
- H2 Database

## Instrucciones de Uso

### Requerimientos

Para correr el proyecto es necesario contar con:

- Un IDE que integre las siguientes tecnologías (**IntelliJ** o **Spring Tool Suite** son recomendados):
  - JDK 1.8
  - Spring Boot
  - Gradle

- Postman

### Instrucciones de Instalación

1. Descargar el proyecto del repositorio.
2. Importar el proyecto en el IDE seleccionado.
3. Importar las dependencias de Gradle.
4. Construir el proyecto.
5. Correr el proyecto a partir del archivo `BciEjApplication`.

### Instrucciones de Ejecución

#### Comunicación con el Microservicio

La puerta de entrada a la aplicación se levanta en el puerto `8081` con el path `/bci_app/v1`. Las direcciones completas para cada funcionalidad son:

- **Sign Up**: `http://localhost:8081/bci_app/v1/sign_up`
- **Login**: `http://localhost:8081/bci_app/v1/login`

Dentro del proyecto se encuentra una [colección de Postman](franyanes-bci.postman_collection.json) lista para ser usada contra la aplicación.

#### Interacción con la Base de Datos

Existe una herramienta interactiva de la base de datos a la cual se puede ingresar usando cualquier navegador y yendo a `http://localhost:8081/bci_app/v1/h2-console`.

Las credenciales son:

- **User Name**: sa
- **Password**: password

El proyecto cuenta con una base de datos ya creada y con entradas cargadas para testear. Estas entradas son:

- `ACCOUNTS`

  |ID|CREATED|EMAIL|IS_ACTIVE|LAST_LOGIN|NAME|PASSWORD|TOKEN|
  |--|--|--|--|--|--|--|--|
  | a4edb0618d294123bdd8b204a4d981a7 | 2023-06-15 19:39:59.277 | multiple_phones@gmail.com    | TRUE      | *null*                  | Multiple Phones | HbEdQ7qxR39LDzJNEyWmvA== | eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im11bHRpcGxlX3Bob25lc0BnbWFpbC5jb20ifQ.bIQceHZdQ0UEi_YuBngMRTaQUJcklYU1vE0gQKllid8 |
  | c2d625a4dd6549a2b589f139ea28cde3 | 2023-06-15 19:41:47.671 | simple_account@gmail.com     | TRUE      | *null*                  | Simple Account  | HbEdQ7qxR39LDzJNEyWmvA== | eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNpbXBsZV9hY2NvdW50QGdtYWlsLmNvbSJ9.h5XyMrzLo-MlaTi_BWahR1vURAXHPmXxrMQNUrk-S_Q |
  | ac774db319734ade9f7e9e0590a54f58 | 2023-06-15 19:42:50.394 | no_optional_fields@gmail.com | TRUE      | 2023-06-15 19:48:46.605 | *null*          | HbEdQ7qxR39LDzJNEyWmvA== | eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vX29wdGlvbmFsX2ZpZWxkc0BnbWFpbC5jb20ifQ.eJvB4r4c0KmXKiSgWMKjBy6-ZEsXYPkMwjDysPr_DDM |

- `PHONES`

  | ID                               | CITY_CODE | COUNTRY_CODE | NUMBER     | ACCOUNT_ID                       |
  | -------------------------------- | --------- | ------------ | ---------- | -------------------------------- |
  | edbbbfaf554a45c3ad1f954d6fe7b93c | 111       | AR           | 1111111111 | a4edb0618d294123bdd8b204a4d981a7 |
  | 4a75ca8985f34b00ae192dfc49eb5f6c | 111       | CL           | 2222222222 | a4edb0618d294123bdd8b204a4d981a7 |
  | 70a572dbf9ab49e2a36a1d5a32987b5c | 111       | PE           | 3333333333 | a4edb0618d294123bdd8b204a4d981a7 |
  | 47382dd4b1ef408ebb9846c404aebc15 | 111       | AR           | 1111111111 | c2d625a4dd6549a2b589f139ea28cde3 |

## Diagramas

### Diagramas de Secuencia

#### 1. Sign Up

![signup-sequence-diagram](project-presentation/signup-sequence-diagram.png)

#### 2. Login

![login-sequence-diagram](project-presentation/login-sequence-diagram.png)

### Diagrama de Componentes

![component-diagram](project-presentation/component-diagram.png)
