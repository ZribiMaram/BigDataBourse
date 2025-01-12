1. Introduction
L'application développée met en œuvre un pipeline de traitement des données boursières (prix des actions) en temps réel et par lot. Elle repose sur des technologies modernes pour gérer, traiter et analyser les flux de données de manière efficace et scalable. L'objectif principal est de fournir des informations en temps réel et des métriques agrégées via un backend performant et une interface utilisateur interactive.

2. Architecture de l'Application
L'architecture est divisée en plusieurs couches fonctionnelles : ingestion, traitement en temps réel, traitement par lot, exposition des données, et interface utilisateur.

2.1. Data Ingestion Layer
Technologie : Apache Kafka
Fonctionnalités :
Kafka est utilisé pour l'ingestion des données boursières.
Un simulateur génère les données des actions (nom de l'action, prix, timestamp).
Ces données sont publiées dans un topic Kafka pour être consommées par les couches de traitement.
2.2. Processing Layers
Les données ingérées sont traitées selon deux approches principales :

2.2.1. Speed Processing Layer (Traitement en Temps Réel)
Technologie : Apache Spark Streaming
Fonctionnalités :
Consomme les messages en temps réel depuis Kafka.
Traite et persiste les données dans :
Cassandra : Stockage des données en temps réel.
HDFS (Parquet) : Archivage des données pour des analyses futures.

2.3. Data Exposure Layer
Technologie : Spring Boot avec WebSocket
Fonctionnalités :
Accès aux données stockées dans Cassandra.
Deux flux de données sont exposés :
Données en temps réel : Dernières actions (latest actions) avec leurs prix actuels.
Moyennes calculées : Prix moyen des actions sur une période donnée.
2.4. Frontend Layer
Technologie : React.js
Fonctionnalités :
Développement d'une interface utilisateur interactive et réactive pour visualiser les données boursières.
Axios est utilisé pour la gestion des requêtes HTTP vers le backend.
3. Métriques Calculées
L'application calcule et expose deux métriques principales :

3.1. Prix Moyen des Actions
Calculé à l'aide des fichiers Parquet en traitement par lot.
Permet une analyse des tendances boursières et des mouvements globaux des actions.
3.2. Dernières Actions
Données récupérées en temps réel depuis Kafka et persistées dans Cassandra.
Fournit les informations les plus récentes sur les actions en temps réel (nom de l'action, prix actuel, timestamp).
4. Technologies et Outils Utilisés
4.1. Frontend
React.js : Pour une interface utilisateur dynamique et réactive.
Axios : Gestion des requêtes HTTP entre le frontend et le backend.
4.2. Ingestion des données
Apache Kafka : Gestion des flux de données en continu.
4.3. Traitement des données
Apache Spark Streaming : Traitement en temps réel.
4.4. Stockage des données
HDFS avec fichiers Parquet : Archivage des données.
Cassandra : Base NoSQL pour stocker les données en temps réel et les agrégats.
4.5. Backend
Spring Boot : Serveur backend avec WebSocket pour fournir des mises à jour en temps réel.
5. Déploiement
L'application fonctionne au sein d'un réseau Docker, ce qui facilite l'orchestration des services et garantit la scalabilité des composants.

6. Conclusion
Ce système offre une gestion complète et performante des données boursières. Grâce à une combinaison de traitement en temps réel et par lot, il répond aux besoins de visualisation instantanée et d'analyse agrégée. L'intégration d'une interface utilisateur développée en React.js améliore l'expérience utilisateur, permettant une visualisation claire des métriques (dernières actions et prix moyen). Cette architecture scalable peut être étendue pour gérer des volumes encore plus importants et d'autres types de données financières.