
# 🚀 Sprint 4 – API Automation (Rest Assured)

## 📌 Project Context

This sprint is part of a larger **Agile QA project** where we tested both UI and REST APIs for **Library Management** and **Hotel Room Service** systems.
While earlier sprints covered **manual testing, UI automation, and Postman API testing**, Sprint 4 focused entirely on **end-to-end API automation using Rest Assured**.

## 🎯 Sprint 4 Goals

1. Automate **all Room Service API endpoints** using a modular Java + Rest Assured framework.
2. Implement **JSON schema validation** to ensure API contract stability.
3. Verify **response performance** (< 2 seconds) for all endpoints.
4. Integrate **reporting** (Allure & Extent) for actionable insights.
5. Build **reusable framework components** to scale future automation.

## 🌐 APIs Covered in Sprint 4

| Endpoint                          | Method | Description                             | Automated Scenarios                           |
| --------------------------------- | ------ | --------------------------------------- | --------------------------------------------- |
| `/OAuthRestApi/webapi/auth/login` | POST   | Step 1 – Retrieve authorization code    | Valid/invalid login, error handling           |
| `/OAuthRestApi/webapi/auth/token` | POST   | Step 2 – Exchange code for access token | Token generation & storage                    |
| `/viewRoomList`                   | GET    | Fetch all room data                     | Status code, schema validation, response time |
| `/viewRoomById/{roomId}`          | GET    | Fetch specific room details             | Multiple IDs, negative scenarios              |
| `/viewRoomByType`                 | GET    | Filter rooms by type                    | SINGLE, DOUBLE, DELUXE validation             |
| `/addRoom`                        | POST   | Add a new room                          | Valid/invalid payload, mandatory field checks |
| `/updateRoomPrice`                | PUT    | Update room pricing                     | Positive/negative updates, boundary values    |
| `/deleteRoomById/{roomId}`        | DELETE | Remove a room                           | Valid deletion, non-existent ID error         |

## 🛠 Framework Architecture

**Key Components:**

* `apibase.java` – Centralized Base URI & headers
* `authutil.java` – Token retrieval & management
* `RoomServiceTests.java` – End-to-end test cases for room APIs
* `extentmanager.java` – HTML reporting utility
* `testlistener.java` – TestNG event listener for logs/screenshots
* `testdataloader.java` – Externalized payload & test data handling
* `room.java` – POJO for room object mapping
  
## 📂 Directory Structure

```
src/
 ├── main/java/
 │    ├── utils/
 │    │    ├── apibase.java
 │    │    ├── authutil.java
 │    │    └── extentmanager.java
 │    └── pojo/
 │         └── room.java
 ├── test/java/
 │    ├── tests/
 │    │    ├── RoomServiceTests.java
 │    │    ├── AuthTests.java
 │    └── listeners/
 │         └── testlistener.java
resources/
 ├── schema/
 │    └── roomlist_schema.json
 └── testdata/
      └── room_payload.json
```

## 📊 Reporting

### **Allure Reports**

* **Features:** Test history, detailed steps, request/response logs, attachments

### **Extent Reports**

* **Features:** Interactive HTML report, category & author filtering

## 📈 Performance & Validation

* **Response Time:** All APIs completed in **< 2s** for successful calls.
* **Schema Compliance:** Every endpoint validated against **predefined JSON schema**.
* **Negative Testing:** Covered invalid IDs, payload errors, missing parameters.

## 👥 Team Contributions

| Module              | Assignee(s)                 |
| ------------------- | --------------------------- |
| Room API Automation | Yashika R, Ritushree        |
| Auth API Automation | K.V. Surendra, Kartik Tyagi |
| Schema Validation   | Yashika R                   |
| Token Utility       | K.V. Surendra               |
| Base API Setup      | Kartik Tyagi                |


## ▶ How to Run

**1. Clone repo & install dependencies**

```bash
mvn clean install
```

**2. Run tests**

```bash
mvn test
```

**3. View Allure report**

```bash
allure serve target/allure-results
```

**4. View Extent report**

* Open `target/extent-reports/index.html` in a browser

---
