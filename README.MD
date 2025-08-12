# BirFatura Invoice Integration

This project, developed using Java Spring Boot and React, sends e-Invoices via the BirFatura API. It generates UBL-formatted invoice XML from user input, compresses it into a ZIP archive, converts it to base64 format, and sends it to the server. After successful submission, the user receives a link to the invoice PDF.



## Features

- Collecting and managing invoice data via React frontend, including `Show Invoices` and `Automatic Calculation` pages
- Generating UBL-TR compliant XML invoices (Spring Boot backend)
- ZIP and Base64 encoding operations
- Sending e-Invoices via the BirFatura API
- Displaying the invoice PDF link after successful submission for download
- Database integration with PostgreSQL via Spring Data JPA & Flyway
- CRUD operations for `Customer`, `Supplier`, `InvoiceItem`, and `Invoice`



## Technologies Used

### Backend
- **Java 21**
- **Spring Boot** – Backend application framework
- **JAXB (Jakarta XML Binding)** – Generating UBL XML files from Java objects
- **Jackson** – JSON serialization and deserialization
- **SpringDoc OpenAPI** – Swagger UI integration for API documentation
- **Maven** – Build and dependency management
- **Flyway** – Database version control and schema migration
- **PostgreSQL** – Relational database
### Frontend
- **React 19** – Frontend UI library

### Testing
- **JUnit 5** – Unit testing for backend logic

### Other
- **UBL 2.1 XML** – Standard format for e-Invoice generation
- **ZIP & Base64** – Compression and encoding for payload delivery



## API Endpoints

### 1. Send Direct Invoice
- **URL:** /api/v1/document/send-direct
- **Method:** POST
- **Request Body:** SendDocumentApiRequest
- **Description:** Sends an already-prepared UBL XML document (zipped and base64-encoded) to the BirFatura API.

### 2. Generate and Send Invoice
- **URL:** /api/v1/document/send-document
- **Method:** POST
- **Request Body:** SendDocumentUserRequest
- **Description:** Accepts user-friendly invoice data, generates a UBL invoice XML, compresses and encodes it, and sends it to the BirFatura API.

### 3. Customer CRUD API
- **URL Base:** /api/customers

### 4. Supplier CRUD API
- **URL Base:** /api/suppliers

### 5. Invoice Item CRUD API
- **URL Base:** /api/invoice-items

### 6. Invoice CRUD API
- **URL Base:** /api/invoices





## Screenshots


### Invoice Creation Form
![Invoice Form](screenshots/invoice-form.png)

### Submission Success
![Submission Successful](screenshots/submission-success.png)

### Example Invoice
![Submission Successful](screenshots/example-invoice.png)



## How to Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/mustafa-gunaltay/this-repo.git
   cd this-repo
   ```

2. **Backend Setup (Spring Boot):**

   - Navigate to the `BirFaturaInvoiceIntegration` directory.
   - Open the `application.properties` file.
   - Fill in your `API Key`, `Secret Key`, and `Integration Key` values provided by BirFatura.
   - Build and run the Spring Boot application.

   > **Note:** The project already contains a production build of the React app under `src/main/resources/static`.  
   > If you wish to customize the UI, you can do so from the **Frontend Setup (React)** section below and rebuild the frontend accordingly.

3. **Frontend Setup (React):**

   - Navigate to the `birfatura-frontend` directory.
   - Start the React development server using:

     ```bash
     npm start
     ```

   - Make any UI changes as needed. Thanks to React's hot-reloading feature, updates will instantly reflect in the browser.
   > **Note:** Make sure your backend is running on `http://localhost:8080` while making changes.

   - Once you are satisfied with the changes, generate a production build using:

     ```bash
     npm run build
     ```

   - This will create an optimized build in the `build/` directory.
   - Copy the contents of the `build/` folder into the `backend/src/main/resources/static/` directory to apply the new UI to the Spring Boot application.
   - Finally, run the Spring Boot backend to serve the updated UI along with the API.



## Important Note
This project uses the BirFatura API for integration testing purposes only. 
It does not include or redistribute any proprietary content or functionality of the BirFatura platform. 
Please refer to BirFatura's terms of service before using this integration in production.

