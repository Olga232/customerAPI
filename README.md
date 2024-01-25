# Customer API Application


## Customer endpoints
| Path                 | Method | Body           | Search parameters   | Response                       | Description                                |
|----------------------|--------|----------------|---------------------|--------------------------------|--------------------------------------------|
| /api/customers       | POST   | CustomerDto    | ---                 | Code: 201. CustomerDto         | Create new customer                        |
| /api/customers       | GET    | ---            | ---                 | Code: 200. `List<CustomerDto>` | Get all existing customers                 |
| /api/customers/{id}  | GET    | ---            | String id           | Code: 200. CustomerDto         | Get customer by id                         |
| /api/customers/{id}  | PATCH  | JsonPatch      | String id           | Code: 200. CustomerDto         | Update full name and phone of the customer |
| /api/customers/{id}  | DELETE | ---            | String id           | Code: 200. CustomerDto         | Delete customer by id                      |


## API models:
- CustomerDto
- ErrorInfoApi

### CustomerDto:
| Property name |Property type       | Example              |Description                                                    |
|---------------|--------------------|----------------------|---------------------------------------------------------------|
| id            | Long               | 1                    | Customer ID, unique                                           |
| fullName      | String             | "Petro Petrenko"     | 2-50 chars including whitespaces                              |
| email         | String             | "mymail@mail.com"    | 2-100 chars, unique, should include exactly one @             |
| phone         | String             | "+12345678"          | 6..14 chars, only digits, should start from +, optional field |

### ErrorInfoApi:
| Property name   |Property type | Example                                           |Description                      | 
|-----------------|--------------|---------------------------------------------------|---------------------------------|
|url              | String       | `http://localhost:8080/api/customers`             | Url, where the error occurred.  |
|exceptionMessage | String       | "Validation failed: fullName：must be 2-50 chars including whitespaces" | A message describing the error. |

## JSON body examples:
- PATCH /api/customers/{id}:
  [ 
    { "op": "replace", "path": "/fullName", "value": "Petro Petrenko" },
    { "op": "replace", "path": "/phone", "value": "+12345678" } 
  ]
- POST /api/customers:
  {
    "fullName": "Petro Petrenko",
    "email": "mymail@mail.com",
    "phone": "+12345678"
  }
  
  
