-- Données initiales pour tests (chargées au démarrage)
-- Tables créées par Hibernate: station, carburant, histo_carb

-- Stations
INSERT INTO station (id, nom, ville, adresse) VALUES
  (1, 'Station Total', 'Casablanca', 'Boulevard Mohammed V, 123'),
  (2, 'Station Afriquia', 'Rabat', 'Avenue Hassan II, 456');

-- Carburants
INSERT INTO carburant (id, nom, description) VALUES
  (1, 'Essence', 'Essence sans plomb 95'),
  (2, 'Diesel', 'Gazole');

-- Prix journaliers (HistoCarb)
-- Clé composite: (carburant_id, date, station_id)
INSERT INTO histo_carb (carburant_id, station_id, date, prix) VALUES
  (1, 1, '2025-11-03', 12.50),
  (2, 1, '2025-11-03', 11.20),
  (1, 2, '2025-11-03', 12.40);

