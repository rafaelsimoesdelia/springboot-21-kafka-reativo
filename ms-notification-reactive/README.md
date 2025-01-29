# **A.R. Phoenix -- Microservice ms-notification-reactive**

## **📌 1. Tecnologias Utilizadas**
- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.4.2
- **Banco de Dados:** Microsoft SQL Server (Driver oficial da Microsoft)
- **ORM:** JPA/Hibernate
- **Programação Reativa:** Spring WebFlux, Reactor Kafka
- **Mensageria:** Apache Kafka com suporte a Kafka Reativo
- **Monitoramento:** Spring Actuator, OpenTelemetry
- **Documentação:** Swagger/OpenAPI 2.6.0
- **Utilitários:** Lombok 1.18.26, ModelMapper 3.2.1
- **Ferramentas de Qualidade de Código:** SonarLint
- **Mecanismo de Retry:** Spring Retry
- **Log:** SLF4J, Logback
- **Build:** Maven

---

## **📌 2. Configuração Inicial**
### **2.1 Instalar as Ferramentas**
Antes de rodar o projeto, é necessário instalar:
- **[Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)**
- **[DBeaver](https://dbeaver.io/download/)**
- **[Git](https://git-scm.com/)**
- **[Postman](https://www.postman.com/downloads/)**
- **[SonarLint](https://www.sonarlint.org/) (instalar na IDE para análise de código)**

---

## **📌 3. Build e Deploy**
### **3.1 Spring Boot**
1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd ms-notification-reactive
   ```
2. Importe o projeto na sua IDE (IntelliJ, VS Code ou Eclipse).
3. Para compilar o projeto:
   ```bash
   mvn package -U
   ```
4. Para rodar a aplicação:
   ```bash
   mvn spring-boot:run
   ```
   ou execute a classe principal na sua IDE.

---

### **3.2 SonarLint**
1. Instale o **SonarLint** na sua IDE.
2. Selecione a pasta `ms-notification-reactive` e pressione `Ctrl+Shift+S`.
3. Verifique os erros indicados pelo SonarLint e corrija-os.

---

### **3.3 Kafka**
Se estiver usando o Kafka localmente, suba os containers com Docker:

1. Verifique o `docker-compose.yml` e inicie os serviços:
   ```bash
   docker compose up -d
   ```
2. Confirme se os containers estão rodando:
   ```bash
   docker ps
   ```
3. Para testar envio de mensagens para Kafka:
   ```bash
   kcat -P -b localhost:9092 -t notification-topic
   ```

---

## **📌 4. Configuração do Banco de Dados**
O projeto utiliza **SQL Server**. Defina a conexão no `application.yml`:

```yaml
spring:
  datasource:
    url: ${DB_URL:jdbc:sqlserver://X.X.X.X:PORTA;databaseName=XXX;encrypt=true;trustServerCertificate=true}
    username: ${DB_USERNAME:XXX}
    password: ${DB_PASSWORD:XXXXXXX}
```
Caso esteja rodando localmente, ajuste os valores conforme necessário.

---

## **📌 5. Documentação Swagger**
Após iniciar a aplicação, acesse o **Swagger** no navegador:
📌 [Swagger UI](http://localhost:8080/ms-notification-reactive/swagger-ui/index.html)

---

## **📌 6. Teste de Performance com JMeter**
Se deseja testar a aplicação em carga, siga os passos:

1. Abra o **Apache JMeter**.
2. Configure um **Thread Group** com:
   - **Threads (Usuários Simultâneos):** `500`
   - **Ramp-up Period:** `10` segundos
   - **Loop Count:** `100`
3. Adicione um **HTTP Request**:
   - **Method:** `POST`
   - **Path:** `/ms-notification-reactive/notifications`
   - **Body:**
     ```json
     {
       "userId": "12345",
       "type": "SMS",
       "message": "Seu pedido foi aprovado!"
     }
     ```
4. Execute o teste e monitore os tempos de resposta.

---

## **📌 7. Estrutura do Projeto**
📂 `src/main/java/com/portalsat/api`
- **`controller/NotificationController.java`** → Exposição de endpoints REST.
- **`service/NotificationService.java`** → Processamento de notificações com WebFlux.
- **`consumer/ReactiveNotificationConsumer.java`** → Consumo de mensagens Kafka.
- **`config/ReactiveKafkaConfig.java`** → Configuração do Kafka Reativo.

📂 `src/test/java/com/portalsat/api`
- **Testes unitários e integração.**

---

## **📌 8. Contribuição**
1. Faça um `fork` do repositório.
2. Crie uma `feature branch`:  
   ```bash
   git checkout -b feature/nova-feature
   ```
3. Faça as alterações e `commit`:  
   ```bash
   git commit -m "Descrição das alterações"
   ```
4. Envie para o repositório:  
   ```bash
   git push origin feature/nova-feature
   ```
5. Abra um **Pull Request**.

---

## **📌 9. Contato**
Caso precise de suporte ou queira contribuir, entre em contato via **email** ou abra uma **issue** no repositório.

🚀 **Projeto desenvolvido para envio eficiente e escalável de notificações em tempo real!** 🎯
