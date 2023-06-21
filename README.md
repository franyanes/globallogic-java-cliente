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

Para correr el proyecto es necesario contar con un IDE que integre las siguientes tecnologías:

- JDK 1.8
- Spring Boot
- Gradle

**IntelliJ** y **Spring Tool Suite** son recomendados.

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
  | 551e84f3d6da448e886f73070ceb6bec | 2023-06-20 21:02:55.449 | simple_account@gmail.com     | TRUE      | *null*     | Simple Account  | HbEdQ7qxR39LDzJNEyWmvA== | eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNpbXBsZV9hY2NvdW50QGdtYWlsLmNvbSJ9.jV6jrT_Z6ggh1dS1UBdLM3lMrLabWMnbUHPq2t241gs |
  | 0c06e88fec1b458aa007d25d287ddf69 | 2023-06-20 21:03:06.024 | multiple_phones@gmail.com    | TRUE      | *null*     | Multiple Phones | HbEdQ7qxR39LDzJNEyWmvA== | eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im11bHRpcGxlX3Bob25lc0BnbWFpbC5jb20ifQ.7jerFl1_i9W7FDBnZLDppL5yjxK0tm1Qe_dwy-84z7w |
  | 288b7faee97b45f6bb7c9ddb98ebfb1a | 2023-06-20 21:03:12.238 | no_optional_fields@gmail.com | TRUE      | *null*     | *null*          | HbEdQ7qxR39LDzJNEyWmvA== | eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vX29wdGlvbmFsX2ZpZWxkc0BnbWFpbC5jb20ifQ.FMnHv9ReanUNgxri9n7U2ErtCon8L-MIUPJ_UUWhXrc |

- `PHONES`

  | ID                               | CITY_CODE | COUNTRY_CODE | NUMBER     | ACCOUNT_ID                       |
  | -------------------------------- | --------- | ------------ | ---------- | -------------------------------- |
  | 8af9d70cb6f94408b9675e4bcbf2371d | 111       | AR           | 1111111111 | 551e84f3d6da448e886f73070ceb6bec |
  | 825b0b7b6ccc4f0388d5ae1a12e0b522 | 111       | AR           | 1111111111 | 0c06e88fec1b458aa007d25d287ddf69 |
  | d4fd632da78b4fefb5e5c1fa28fd8ba4 | 111       | CL           | 2222222222 | 0c06e88fec1b458aa007d25d287ddf69 |
  | f7de5ce68f2c43f891c32948418617d0 | 111       | PE           | 3333333333 | 0c06e88fec1b458aa007d25d287ddf69 |

## Diagramas

### Diagramas de Secuencia

#### 1. Sign Up

![signup-sequence-diagram](project-presentation/signup-sequence-diagram.png)

#### 2. Login

![login-sequence-diagram](project-presentation/login-sequence-diagram.png)

### Diagrama de Componentes

![component-diagram](project-presentation/component-diagram.png)
