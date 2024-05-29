# Praxisblatt 2 - Algorithmische Bioinformatik I
## Task 1
Wir erstellen zunächst ein Objekt der Klasse `CityGraph` in konstanter Laufzeit O(1). Dann lesen wir eine TSV-Datei in eine Liste von `ArrayList`-Objekten ein. Dies ermöglicht den Zugriff auf jedes Attribut eines Eintrags in konstanter Zeit O(1). 

Anschließend iterieren wir in linearer Zeit O(n) über die Liste der Einträge. Für jeden Eintrag erstellen wir ein `City`-Objekt in konstanter Zeit O(1). Dieses `City`-Objekt wird zur Erstellung eines `Vertex`-Objekts verwendet, was ebenfalls in konstanter Zeit O(1) erfolgt. Das `Vertex`-Objekt wird anschließend in konstanter Laufzeit O(1) in das `CityGraph`-Objekt eingefügt.

Nach dem Übertragen aller `Vertex`-Objekte in das `CityGraph`-Objekt werden die Kanten des Graphen erstellt. Jeder `Vertex` ist mit jedem anderen `Vertex` verbunden, jedoch nicht mit sich selbst. Wir iterieren über alle `Vertex`-Objekte und überprüfen für jeden `Vertex`, ob eine Kante zu einem anderen `Vertex` bereits existiert. Falls nicht, wird eine neue Kante hinzugefügt. Die äußere Schleife benötigt O(n) Zeit, die innere ebenfalls O(n), und die Überprüfung auf bestehende Kanten im schlimmsten Fall O(n). Das Hinzufügen einer neuen Kante erfolgt in O(1).

Die Gesamtlaufzeit der Methode beträgt im schlimmsten Fall O(n^3).
## Task 2
Die Tiefensuche ist in unserem Fall eine rekursive
## Task 3
