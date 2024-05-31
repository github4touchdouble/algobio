# Praxisblatt 2 - Algorithmische Bioinformatik I

----
**GitHub Repo:**    
https://github.com/github4touchdouble/algobio/tree/master/src/GRAPH

----

## Usage
```bash
java -jar GRAPH.jar --path PATH_TO_CITIES_TSV --task [1|2|3]
```

# Laufzeitanalyse
## Task 1
Wir erstellen zunächst ein Objekt der Klasse `CityGraph` in konstanter Laufzeit $\mathcal{O}(1)$.
Dann lesen wir eine TSV-Datei in eine Liste von `ArrayList`-Objekten ein.
Dies ermöglicht den Zugriff auf jedes Attribut eines Eintrags in konstanter Zeit $\mathcal{O}(1)$.

Anschließend iterieren wir in linearer Zeit $\mathcal{O}(n)$ über die Liste der Einträge.
Für jeden Eintrag erstellen wir ein `City`-Objekt in konstanter Zeit $\mathcal{O}(1)$.
Dieses `City`-Objekt wird zur Erstellung eines `Vertex`-Objekts verwendet, was ebenfalls in konstanter Zeit $\mathcal{O}(1)$ erfolgt.
Das `Vertex`-Objekt wird anschließend in konstanter Laufzeit $\mathcal{O}(1)$ in das `CityGraph`-Objekt eingefügt.

Nach dem Übertragen aller `Vertex`-Objekte in das `CityGraph`-Objekt werden die Kanten des Graphen erstellt.
Jeder `Vertex` ist mit jedem anderen `Vertex` verbunden, jedoch nicht mit sich selbst.
Wir iterieren über alle `Vertex`-Objekte und überprüfen für jeden `Vertex`, ob eine Kante zu einem anderen `Vertex` bereits existiert.
Falls nicht, wird eine neue Kante hinzugefügt.
Die äußere Schleife benötigt $\mathcal{O}(n)$ Zeit, die innere ebenfalls $\mathcal{O}(n)$, und die Überprüfung auf bestehende Kanten im schlimmsten Fall $\mathcal{O}(n)$.
Das Hinzufügen einer neuen Kante erfolgt in $\mathcal{O}(1)$.

Die Gesamtlaufzeit der Methode beträgt im schlimmsten Fall $\mathcal{O}(n^3)$.

## Task 2
Bevor `depthFirstSearch` aufgerufen wird, muss einiges an Vorarbeit passieren:
1. Die *Initialisierung* des Graphen (mit der **Laufzeit** von `Task 1`)
2. Die *Sortierung* der Knoten Ids in $\mathcal{O} (|V|)$.

Die ***Tiefensuche*** ist in unserem Fall eine **rekursive** Methode, welche vom
<u>Startknoten</u> (= Konten mit niedrigster `ID`) aus, alle benachbarten Knoten
besucht, um sich dort erneut **rekursiv** aufzurufen.
Zudem wird jeder besuchter Knoten als `visited` markiert, d.h. wenn in einem **Rekursionsaufruf**
ein Konten $v$ besucht wird, welcher bereits besucht wurde, wird
diese **Rekursion** abgebrochen (in $\mathcal{O} (1)$, da dies mit einem
`if-statement` überprüft wird.).
Mit dieser Eigenschaft, dass bereits besuchte Knoten nicht mehr betrachtet werden und das
in $\mathcal{O} (1)$ überprüft wird, können also maximal $|V|$ viele $Vertices$
besucht werden.

Insgesamt also $\mathcal {O}(|V| + |E|)$.
Jedoch müssen zusätzlich bei jedem Aufruf eines unbesuchten Knotens 
dessen Kanten nach Gewicht sortiert werden
(die von uns gewählte Datenstruktur [HashMap], gibt die Keys [Distanz] eben nicht geordnet zurück).
Das heißt, für jeden Konten, muss diese Sortierung genau einmal geschehen, 
also lässt sich der Gesamtaufwand für das Sortieren der Kantengewichte mit 
$|E| \cdot \log(|E|)$ beschreiben (`Collections.sort`).

Da $|E|$ oft größer als $|V|$ ist (zum Beispiel in dichten Graphen), 
dominiert der Term $|E| \log(|E|)$, was die Gesamtlaufzeit zu $\mathcal{O}(|V| + |E| \log(|E|))$ macht.

## Task 3

Die Idee des Algorithmus lässt sich folgendermaßen zusammenfassen (**Notizen aus der ADS Vorlesung von mir :)**):
- Starte mit leerer Kantenmenge.
- Kanten sind aufsteigend sortiert
- Füge sukzessive minimale Kanten bezüglich ihrer Kosten hinzu, sodass kein Zyklus entsteht.
  Es entstehen dann zuerst nicht zusammenhängende Teilgraphen,
  die dann durch Hinzufügen von Kanten verbunden werden.
- Stoppe, falls keine solche Kante mehr gefunden werden kann (die nächste Kante bildet einen Zyklus,
  alle Knoten erreichbar).

*Pseudocode:*
```
𝐸′ ←  ∅
Sortiere 𝐸 aufsteigend nach 𝑐(𝐸)
Solange 𝐸 ≠ ∅    // loop in Zeile 16 (in KruskalAlgorithmus.Java)
	𝑒 ←  min(𝐸)  // Zeile 18
	𝐸=𝐸−{𝑒}      // Zeile 18
	Falls 𝐺(𝑉, 𝐸′ ∪ {𝑒}) zyklenfrei:  // wird in der Methode createsCycle(mstEdges, minEdge) geprüft
		𝐸′ = 𝐸′ ∪ {𝑒}
```
Hier dominiert das Sortieren die Laufzeit: $\mathcal{O} (|E| \cdot \log(|E|))$,
denn auch hier wird initial `Collections.sort(sortedEdges, Comparator.comparingDouble(Edge::getWeight));`
aufgerufen, um die Kanten nach Gewicht zu sortieren.


