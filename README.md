# Système de Gestion de Pharmacie (PMS)

Un système complet de gestion de pharmacie développé en Java avec interface graphique Swing et base de données MySQL.

## Description

Ce projet est une application de bureau pour la gestion complète d'une pharmacie, comprenant la gestion des médicaments, des clients, des fournisseurs, des ventes et des commandes.

## Fonctionnalités

### Gestion des Clients
- Ajout et gestion des clients
- Historique des achats
- Traitement des ventes

### Gestion des Fournisseurs
- Enregistrement des fournisseurs
- Gestion des commandes
- Analyse de performance des fournisseurs
- Rapports d'analyse

### Gestion des Médicaments
- Ajout et modification des médicaments
- Suivi des stocks
- État critique des stocks
- Alertes de stock insuffisant

### Gestion des Commandes
- Création de commandes
- Modification des commandes
- Suivi des détails de commande

### Rapports et Analyses
- Chiffre d'affaires
- Performance des fournisseurs
- Rapports d'analyse des médicaments
- Historique des ventes

### Gestion des Utilisateurs
- Authentification des employés et administrateurs
- Création de comptes
- Contrôle d'accès basé sur les rôles

## Structure du Projet

```
src/
├── app/                              # Point d'entrée de l'application
├── classes_principales/              # Entités du domaine métier
├── classes_intermediaires/           # Classes de support et DTOs
├── connexion_sql/                    # Connexion à la base de données
├── exceptions_personnalisees/        # Exceptions métier personnalisées
├── interface_client/                 # Interfaces graphiques pour la gestion des clients
├── interface_client_dao/             # Couche DAO pour les clients
├── interface_fournisseur/            # Interfaces graphiques pour la gestion des fournisseurs
├── interface_fournisseur_dao/        # Couche DAO pour les fournisseurs
├── user_interface/                   # Interfaces d'authentification et menu principal
└── user_interface_dao/               # Couche DAO pour les utilisateurs
```

## Prérequis

- Java JDK 8 ou supérieur
- MySQL Server
- Driver JDBC MySQL (Connector/J)
- IDE Java (Eclipse, IntelliJ IDEA, ou NetBeans)

## Installation

1. **Cloner le dépôt**
   ```bash
   git clone <url-du-depot>
   cd systeme_gestion_pharmacie
   ```

2. **Configurer la base de données**
   - Créer une base de données MySQL nommée `pharmacieDB`
   - Importer le schéma depuis `pharmacieDB/pharmacieDB.sql`
   ```sql
   mysql -u root -p < pharmacieDB/pharmacieDB.sql
   ```

3. **Configurer la connexion**
   - Modifier les paramètres de connexion dans `src/connexion_sql/Connexion.java`
   - Mettre à jour l'URL, le nom d'utilisateur et le mot de passe MySQL

4. **Ajouter le driver JDBC**
   - Télécharger MySQL Connector/J
   - Ajouter le fichier JAR au classpath du projet

5. **Compiler et exécuter**
   - Ouvrir le projet dans votre IDE
   - Compiler le projet
   - Exécuter la classe `Main.java` dans le package `app`

## Configuration de la Base de Données

Le fichier SQL de configuration se trouve dans `pharmacieDB/pharmacieDB.sql`. Il contient :
- Schéma de la base de données
- Tables principales (Medicament, Client, Fournisseur, Vente, Commande, etc.)
- Contraintes et relations
- Données de test (si disponibles)

## Utilisation

1. **Connexion**
   - Lancer l'application
   - Se connecter avec les identifiants d'employé ou d'administrateur

2. **Navigation**
   - Utiliser le menu principal pour accéder aux différents modules
   - Les fonctionnalités disponibles dépendent du rôle de l'utilisateur

3. **Gestion quotidienne**
   - Enregistrer les ventes
   - Vérifier les stocks
   - Créer des commandes fournisseurs
   - Consulter les rapports

## Exceptions Personnalisées

Le système utilise des exceptions métier pour gérer les cas particuliers :
- `StockInsuffisantException` : Stock insuffisant pour une vente
- `CommandeInexistanteException` : Commande introuvable
- `FournisseurInexsistantException` : Fournisseur introuvable

## Technologies Utilisées

- **Langage** : Java
- **Interface Graphique** : Java Swing
- **Base de Données** : MySQL
- **Architecture** : DAO Pattern, MVC

## Auteurs

Développé dans le cadre d'un projet universitaire de gestion de pharmacie.
