## Proiect PAO - Adăpost de Animale

 Descriere generală

Acest proiect reprezintă un sistem de gestiune a unui adăpost de animale, dezvoltat în Java, împărțit în două etape:

Etapa I: Modelarea obiectelor și funcționalităților folosind programare orientată pe obiect.

Etapa II: Persistența datelor în baza de date (SQLite) și auditarea acțiunilor în fișier CSV.

# Etapa I – Definirea și modelarea sistemului

  Obiecte definite (cel puțin 8):

Animal (abstract)

Caine (extinde Animal)

Pisica (extinde Animal)

Adoptant (extinde Persoana)

Angajat (extinde Persoana)

Donatie

Adoptie

Persoana (abstract)

  Acțiuni / Interogări posibile (minim 10):

Adăugare animal

Listare animale

Filtrare animale după specie

Filtrare animale nevaccinate

Adăugare angajați

Sortare angajați după nume

Înregistrare adopție

Istoric adopții

Înregistrare donații

Listare donații

   Specificații tehnice:

Clase cu atribute private/protected și metode getter/setter

Moștenire (ex: Caine, Pisica din Animal)

Cel puțin două colecții: List, Set, Map (ex: animale, angajați, adopții)

Clasă AdapostService care gestionează logica aplicației

Clasă Main cu apeluri către serviciu și simularea acțiunilor

# Etapa II – Persistență și Audit

  Bază de date relațională:

Tehnologie: SQLite, fișier: adapost.db

Persistență realizată prin JDBC

Operații CRUD pentru 4+ clase: Animal, Adoptant, Adoptie, Donatie, Angajat

DAO-uri (Data Access Object) pentru fiecare entitate: AnimalDAO, AdoptantDAO, etc.

   Servicii singleton:

Serviciile de acces la bază de date sunt implementate ca singleton

ConnectionManager pentru conexiune unică și reutilizabilă

` Audit:

Fiecare acțiune relevantă (ex: adăugare, filtrare, adopție) este logată într-un fișier audit.csv

Format CSV: actiune, timestamp

Exemplu: adaugaAnimal, 2025-05-29 15:30:10
