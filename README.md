### Setup
Note:- If You have already setup JAVA_17 then skip this step
1. Java 17 Installation on macOS
   ```
   brew tap homebrew/cask-versions
   brew install --cask temurin17
   ```
2. Set JAVA_17 Env value. Follow step 2 [here](https://www.java.com/en/download/help/path.html)

How build & run this project?
### Running locally
Install and run DynamoDB on your local machine:

1. Install AWS CLI  and Configure
    1. Install AWS CLI Run following commands
        * `curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"`
        * `sudo installer -pkg ./AWSCLIV2.pkg -target /`
    2. To verify AWS CLI Run following commands
        * `which aws`
        * `aws --version`
    3. Configure AWS CLI
        * `aws configure`
            - Enter AccessKey : "local"
            - Enter SecretKey : "12345"
            - Enter region : "us-west-2"
            - Enter output : "json"
    4. [Reference](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)

2. Install DynamoDB

    1. [Download DynamoDB](https://s3.us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.tar.gz)
    2. Extract downloaded file
    3. navigate to the directory where you extracted DynamoDBLocal.jar, and enter the following command.
        * `java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`
    4. Verify DynamoDB
        * `aws dynamodb list-tables --endpoint-url http://localhost:8000`
    5. Create table for Ad
        * `aws dynamodb create-table --cli-input-json file://ads-table-definition.json --endpoint-url http://localhost:8000`
    6. [Reference](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html)

3. Visualizing the DynamoDB Table data

    1. Download and Install NoSQL Workbench
        * [Click_here](https://s3.amazonaws.com/nosql-workbench/NoSQL%20Workbench-mac-3.3.0.dmg)
    2. Open NoSQL Workbench and Setup DynamoDB local connection
        * Click on `operation builder`
        * Click on `Add connection`
        * Click on `DynamoDB Local`
    3. [Reference](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/workbench.settingup.html)

There are a few options to run the application (from more flexible to least)

1. You can just use the SpringBoot run functionality that is default in IntelliJ or SpringTools in Eclipse.

2. Start the service with Maven.
    ```bash
    mvn spring-boot:run -Dspring.profiles.active=local
    ```
   
3. Package the jar file and run it

  ```bash
  mvn clean package
  java -jar ./target/ads-service-exec.jar --spring.profiles.active=local
  ```

### Invoking Ads Service

You can use your favourite gRPC client (e.g. [BloomRPC](https://formulae.brew.sh/cask/bloomrpc))

Import path in BloomRPC

![img.png](src-images/img.png)

Then import .proto file

![img.png](src-images/proto-img.png)

Sample Ad Data
```json
{
  "title": "Car sell",
  "description": "TOYOTA",
  "price": 300000.0,
  "owner": "Hello",
  "status": "LIVE"
}
```

### JWT token algo

```json
{
  "alg": "RS256",
  "typ": "JWT"
}
```

Use local-ads-service-public.pem to encode the token on local machine. 

Use Authorization in Metadata
```json
{"Authorization": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTczOTkyMDUsImlhdCI6MTY5NzM5NTYwNSwiaXNzIjoid3d3LmFjbWUuY29tIiwic3ViIjoiZjFlMzNhYjMtMDI3Zi00N2M1LWJiMDctOGRkOGFiMzdhMmQzIn0.cAP9Tav4VxO_JDxh7gL4AIoFVUZbeC27wwz5NYb-AY2OLss_mmFZGzgDPazmNYrw2_OyjDJqrTicffxF3kvJQiRHuljg4YOC0XgTwDo7aaLvw9DJFSPU8UAdVYEHZsD_CicOBkizNZlWeeIg8BwmxD6_KQdd454v7Kefr_uagMw6JUWHcyn8YZ68JexNtAyVv4ImEGd4QHPKoli5a2iWDRiAJKHG5LpKPE2DZw0avH4b6CJISz5jmTjRkN2357MS6Zudj9Hee35lJGEDxMND9wXIlAcju6vgMCm02KnY56JcnbD5StvZhE5nKcoU7tA0Rh6XKAI73Du4pn-6W11trQ"}
```
Or you can use client id test to bypass authentication on local machine 
```json
{"client-id": "test"}
```

