# 🚀 RL ARENAs API

API REST sécurisée permettant de gérer une ligue amateur de Rocket League : joueurs, équipes et matchs.

Le projet est réalisé avec **Spring Boot** et met en œuvre une architecture en couches :

- Controller
- Service
- Repository
- DTO / Assembler
- Spring Security
- JWT
- Bean Validation
- Gestion centralisée des erreurs

---

## 📋 Fonctionnalités

### 👤 Utilisateurs

L'API permet de :

- créer un utilisateur
- consulter les utilisateurs
- consulter un utilisateur
- modifier un utilisateur
- désactiver un utilisateur
- modifier le rôle d'un utilisateur
- gérer les administrateurs

Règles métier principales :

- l'email doit être unique
- le pseudo doit être unique
- le pseudo doit respecter les contraintes définies
- le mot de passe doit contenir au minimum 8 caractères
- un utilisateur inactif ne peut plus se connecter
- un ADMIN ne peut pas être désactivé
- un utilisateur ne peut pas modifier son propre rôle
- il est impossible d'avoir zéro ADMIN dans le système

---

### 🏆 Équipes

L'API permet de :

- créer une équipe
- consulter les équipes
- consulter une équipe
- modifier une équipe
- désactiver une équipe

Règles métier principales :

- le nom d'une équipe doit être unique
- le tag doit être unique
- le tag contient entre 2 et 5 caractères majuscules
- la création d'une équipe nécessite au minimum le rôle `CAPITAINE`
- une équipe ne peut pas être désactivée si elle participe à un match `PROGRAMME` ou `EN_COURS`
- la suppression est réalisée par **soft delete**

---

### ⚽ Matchs

L'API permet de :

- créer un match
- consulter les matchs
- consulter un match
- démarrer un match
- terminer un match
- annuler un match

Statuts disponibles :

```text
PROGRAMME
EN_COURS
TERMINE
ANNULE