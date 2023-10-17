# Projeto Final Santander Bootcamp 2023 em Parceria com a Dio.
 JAVA API RESTFUL 

 ## Diagrama de classes

 ```mermaid
classDiagram
  class User {
    - name: String
    - account: Account
    - card: Card
    - features: Feature[]
    - news: News[]
  }
  class Account {
    - id: Number
    - number: String
    - agency: String
    - balance: Number
    - limit: Number
  }
  class Card {
    - number: String
    - limit: Number
  }
  class Feature {
    - icon: String
    - description: String
  }
  class News {
    - icon: String
    - description: String
  }
  User "1" *-- "1" Account
  User "1" *-- "1" Card
  User "1" *-- "N" Feature
  User "1" *-- "N*" News
```
