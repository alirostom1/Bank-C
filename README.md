# Bankc – Analyse et Détection des Transactions Bancaires

## Description du projet

**Bankc** est une application console Java 17 destinée aux institutions financières pour centraliser la gestion des clients, comptes et transactions.
Elle permet de détecter automatiquement les anomalies financières (transactions suspectes, comptes inactifs, comportements inhabituels) et de générer des rapports clairs pour faciliter l’analyse et la prise de décision

## Objectifs

- Centraliser toutes les données clients, comptes et transactions
- Suivre et analyser les flux financiers en temps réel
- Détecter automatiquement les anomalies et opérations suspectes
- Identifier les comptes inactifs ou présentant des risques
- Générer des rapports mensuels, annuels et personnalisés
- Aider les gestionnaires à prendre des décisions éclairées

## Technologies utilisées

- Java 17 (record, sealed, switch expressions, var)
- JDBC MySQL
- Java Time API
- UUID
- Programmation fonctionnelle (Stream API, Collectors, Optional, lambda)



## Structure du projet

- `src/io/github/alirostom1/bankc/model` : Entités métier (`Client`, `Account`, `Transaction`, `CheckingAccount`)
- `src/io/github/alirostom1/payflow/service` : Logique métier (`ClientService`, `AccountService`)
- `src/io/github/alirostom1/payflow/ui` : Interface console (menus, navigation)
- `src/io/github/alirostom1/payflow/repository` : DAO/JDBC pour la persistance (`ClientRepository`, `AccountRepository`)
- `src/io/github/alirostom1/payflow/util` : Utilitaires (gestion des dates, validations, scheduler)

## Fonctionnalités principales

### Gestion des clients
- Créer, modifier, supprimer un client  
- Rechercher un client par id ou nom  
- Lister tous les clients avec leurs informations financières  

### Gestion des comptes
- Créer un compte courant ou épargne pour un client  
- Mettre à jour les soldes et paramètres (découvert, taux d’intérêt)  
- Rechercher par client ou numéro de compte  
- Identifier les comptes avec solde maximum/minimum  

### Gestion des transactions
- Enregistrer un versement, retrait ou virement  
- Lister les transactions d’un compte, triées par date  
- Filtrer par montant, type, date ou lieu  
- Regrouper par type ou période  
- Calculer les totaux et moyennes des transactions  

### Analyse et rapports
- Détecter les transactions suspectes :  
  - Montant supérieur à un seuil défini  
  - Transactions dans un lieu inhabituel  
  - Opérations trop fréquentes en un court laps de temps  
- Identifier les comptes inactifs depuis une période donnée  
- Générer :  
  - **Top 5 des clients par solde**  
  - **Rapport mensuel des transactions (par type et volume)**  
  - **Rapport annuel global**  

### Interface utilisateur (console)
- Navigation par menus interactifs  
- Création de clients et comptes  
- Saisie des transactions  
- Consultation des historiques  
- Lancement des analyses (comptes inactifs, transactions suspectes, top 5 clients)  
- Alertes (solde bas, inactivité prolongée)  

## Exigences techniques

- Java 17  
- Persistance via JDBC (PostgreSQL ou MySQL)  
- Architecture en couches (Entity, DAO, Service, UI, Utilitaires)  
- Gestion robuste des exceptions avec messages clairs  
- Utilisation des streams et lambdas pour la manipulation des données  
- Git pour le suivi de version  

## Prérequis

- JDK 17 installé
- MySQL opérationnel
- Git

## Exemple d’exécution

```bash
java -cp "dist/cbank.jar:src/io/github/alirostom1/cbank/drivers/mysql-connector-j-9.3.0.jar" io.github.alirostom1.cbank.Main
```

---

**Diagramme de classe**
![class diagram](https://github.com/alirostom1/Bank-C/blob/develop/docs/diagrams/bankC.png)
---
