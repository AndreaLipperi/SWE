# Sistema di Gestione Ordini - Clienti e Fornitori

## Descrizione del Progetto
Questo progetto rappresenta un sistema software per automatizzare e semplificare la gestione degli ordini tra **ristoranti (Client)** e **fornitori (Provider)**. L'obiettivo principale è ottimizzare il processo di acquisto e fornitura di prodotti, migliorando l'efficienza e riducendo gli errori umani.

## Funzionalità Principali

### Modulo Clienti
- Registrazione degli utenti con email, username e password.
- Esplorazione del catalogo dei prodotti offerti dai fornitori.
- Gestione di un carrello virtuale per aggiungere o rimuovere prodotti.
- Invio degli ordini e consultazione dello storico ordini effettuati.

### Modulo Fornitori
- Registrazione degli utenti con gestione del proprio magazzino.
- Inserimento, modifica e rimozione dei prodotti disponibili.
- Gestione degli ordini ricevuti dai clienti, con possibilità di accettare o rifiutare singoli prodotti.
- Visualizzazione dello storico degli ordini ricevuti.

## Progettazione

### Use-Case Diagram
Il sistema coinvolge tre attori principali:
1. **User**: Autenticazione, gestione del profilo e credenziali.
2. **Client**: Gestione del carrello, invio ordini e controllo dello storico.
3. **Provider**: Gestione del magazzino, gestione degli ordini ricevuti.

### Mockups
Sono stati sviluppati mockup per illustrare le interazioni degli utenti con il sistema, inclusi:
- Homepage Clienti.
- Homepage Fornitori.
- Pagine di gestione del carrello, storico ordini e magazzino.

### Database
Il sistema utilizza un database PostgreSQL per la gestione della persistenza dei dati.  
**Relazioni principali:**
- **Placed By**: Ogni ordine è effettuato da un solo utente.
- **Belongs To**: Un prodotto appartiene a una categoria e sottocategoria.
- **Managed By**: I fornitori gestiscono i loro prodotti.

### Diagramma delle Classi
Le classi principali sono suddivise in:
- **Model**: Contiene le entità come `User`, `Order`, `Cart`, `Store`.
- **View**: Componenti grafiche per l'interfaccia utente.
- **Controller**: Logica di gestione delle operazioni del sistema.

## Struttura del Progetto


```plaintext
main
├── java
│   └── com
│       └── example
│           ├── StoreApplication.java
│           ├── controllers
│           │   ├── CartController.java
│           │   ├── ForgotPasswordController.java
│           │   ├── HomeController.java
│           │   ├── HomePageController.java
│           │   ├── LoginController.java
│           │   ├── OrderDetailsController.java
│           │   ├── OrdersController.java
│           │   ├── PageController.java
│           │   ├── StoreController.java
│           │   ├── SubcategoryController.java
│           │   ├── UpdatePswController.java
│           │   └── UserController.java
│           ├── models
│           │   ├── Cart.java
│           │   ├── Category.java
│           │   ├── Measure_Unit.java
│           │   ├── Order.java
│           │   ├── Order_Details.java
│           │   ├── Store.java
│           │   ├── Subcategory.java
│           │   └── User.java
│           ├── repositories
│           │   ├── CartRepository.java
│           │   ├── CategoryRepository.java
│           │   ├── MeasureUnitsRepository.java
│           │   ├── OrderDetailsRepository.java
│           │   ├── OrderRepository.java
│           │   ├── StoreRepository.java
│           │   ├── SubcategoryRepository.java
│           │   └── UserRepository.java
│           └── services
│               ├── CartService.java
│               ├── CategoryService.java
│               ├── OrderDetailsService.java
│               ├── OrderService.java
│               ├── StoreService.java
│               ├── SubcategoryService.java
│               └── UserService.java
└── resources
    ├── application.properties
    └── templates
        ├── account_pages
        │   ├── delete_account.html
        │   ├── forgot_password.html
        │   ├── index.html
        │   ├── login_page.html
        │   ├── modify_data_account_page.html
        │   ├── registration_page.html
        │   └── update_psw.html
        ├── client
        │   ├── cart_page.html
        │   ├── homepage_client.html
        │   ├── order_details_page_client.html
        │   └── orders_page_client.html
        └── provider
            ├── homepage_provider.html
            ├── insert_product_page.html
            ├── modify_product_page.html
            ├── orders_details_page_provider.html
            └── orders_page_provider.html
```


## Tecnologie Utilizzate
- **Java**: Backend e logica applicativa.
- **PostgreSQL**: Database per la persistenza dei dati.
- **HTML/CSS**: Template grafici per l'interfaccia utente.
- **Spring Framework**: Gestione del backend e dei servizi.

## Come Utilizzare il Progetto
1. **Configurazione del Database**:
   - Configurare il file `application.properties` con i dettagli del database PostgreSQL.
2. **Esecuzione**:
   - Avviare l'applicazione tramite `StoreApplication.java`.
3. **Accesso**:
   - I clienti e i fornitori possono accedere ai loro rispettivi moduli per gestire ordini e magazzini.
