# Praxisblatt 2 - Algorithmische Bioinformatik I
## Task 1
Wir erstellen zunächst ein Objekt der Klasse `CityGraph` in konstanter Laufzeit O(1). Dann lesen wir eine TSV-Datei in eine Liste von `ArrayList`-Objekten ein. Dies ermöglicht den Zugriff auf jedes Attribut eines Eintrags in konstanter Zeit O(1). 

Anschließend iterieren wir in linearer Zeit O(n) über die Liste der Einträge. Für jeden Eintrag erstellen wir ein `City`-Objekt in konstanter Zeit O(1). Dieses `City`-Objekt wird zur Erstellung eines `Vertex`-Objekts verwendet, was ebenfalls in konstanter Zeit O(1) erfolgt. Das `Vertex`-Objekt wird anschließend in konstanter Laufzeit O(1) in das `CityGraph`-Objekt eingefügt.

Nach dem Übertragen aller `Vertex`-Objekte in das `CityGraph`-Objekt werden die Kanten des Graphen erstellt. Jeder `Vertex` ist mit jedem anderen `Vertex` verbunden, jedoch nicht mit sich selbst. Wir iterieren über alle `Vertex`-Objekte und überprüfen für jeden `Vertex`, ob eine Kante zu einem anderen `Vertex` bereits existiert. Falls nicht, wird eine neue Kante hinzugefügt. Die äußere Schleife benötigt O(n) Zeit, die innere ebenfalls O(n), und die Überprüfung auf bestehende Kanten im schlimmsten Fall O(n). Das Hinzufügen einer neuen Kante erfolgt in O(1).

Die Gesamtlaufzeit der Methode beträgt im schlimmsten Fall O(n^3).
## Task 2
Bevor `depthFirstSearch` aufgerufen wird, muss einiges an Vorarbeit passieren:
1. Die *Initialisierung* des Graphen (mit der **Laufzeit** von `Task 1`)
2. Die *Sortierung* der Knoten Ids in $\mathcal{O} (|V|)$.

Die ***Tiefensuche*** ist in unserem Fall eine **rekursive** Methode, welche vom
<u>Startkonten</u> (= Konten mit niedrigster `ID`) aus, alle benachbarten Knoten
besucht, um sich dort erneut **rekursiv** aufzurufen.
Zudem wird jeder besuchter Knoten als `visited` markiert, d.h. wenn in einem **Rekursionsaufruf**
ein Konten $v$ besucht wird, welcher bereits besucht wurde, wird
diese **Rekursion** abgebrochen (in $\mathcal{O} (1)$, da dies mit einem
`if-statement` überprüft wird.).
Mit dieser Eigenschaft, dass bereits besuchte Knoten nicht mehr betrachtet werden und das
in $\mathcal{O} (1)$ überprüft wird, können also maximal $|V|$ viele $Vertices$
besucht werden.

Zudem wissen wir auch, dass alle $E_{i} \in V_E$ pro Knoten betrachtet werden.
Da der Graph **ungerichtet** ist, gibt es also jedes $E_{i} \in E$ doppelt
(einmal hin und einmal zurück). Also werden insgesamt maximal $2 * |E|$ viele
$Edges$ besucht.

Insgesamt also $\mathcal {O}(|V| + 2 |E|) = \mathcal {O}(|V| + |E|)$

## Task 3
