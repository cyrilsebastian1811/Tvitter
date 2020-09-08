## Tvitter Clone

# Packaging Application on Dev Profile
```$xslt
$ ./mvnw clean package spring-boot:repackage -Dprofile=dev -Dmysql_url='jdbc:h2:~/test;AUTO_SERVER=TRUE' -Dredis_url='localhost'
```

# Running Tests on Dev Profile
```$xslt
$ ./mvnw test -Dprofile=dev -Dmysql_url='jdbc:h2:~/test;AUTO_SERVER=TRUE' -Dredis_url='localhost'
```

# Running Application on Prod Profile
```$xslt
$ ./mvnw spring-boot:run -Dprofile=prod -Dmysql_url='jdbc:mysql://localhost:3306/dev?serverTimezone=UTC' -Dmysql_username='' -Dmysql_password='' -Dredis_url='localhost' -Dredis_password=''
```
