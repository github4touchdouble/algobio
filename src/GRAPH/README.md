# Praxisblatt 2 - Algorithmische Bioinformatik I
## Task 1
Wir erstellen zunächst ein Objekt der Klasse `CityGraph` in konstanter Laufzeit \(O(1)\). Dann lesen wir eine TSV-Datei in eine Liste von `ArrayList`s ein, wobei jeder Eintrag als `ArrayList` dargestellt wird. Dies ermöglicht den Zugriff auf jedes Attribut eines Eintrags in konstanter Zeit \(O(1)\). Anschließend iterieren wir in linearer Zeit \(O(n)\) über die Liste der Einträge.

Für jeden Eintrag erstellen wir zunächst ein `City`-Objekt in konstanter Zeit \(O(1)\). Dieses `City`-Objekt wird dann als Typ für die Erstellung eines `Vertex`-Objekts verwendet, was ebenfalls in konstanter Zeit \(O(1)\) erfolgt. Das `Vertex`-Objekt wird nun in konstanter Laufzeit \(O(1)\) in das `CityGraph`-Objekt eingefügt.

Nach dem vollständigen Übertragen aller `Vertex`-Objekte in das `CityGraph`-Objekt werden die Kanten des Graphen erstellt. In dieser Aufgabe handelt es sich um einen Graphen mit gewichteten, ungerichteten Kanten. Jeder Knoten des Graphen ist mit jedem anderen Knoten des Graphen verbunden, jedoch nicht mit sich selbst.  Wir iterieren über alle Knoten des Graphen und überprüfen für jeden Knoten, ob eine Kante zu einem anderen Knoten bereits existiert. Falls nicht, wird eine neue Kante hinzugefügt. Diese äußere Schleife iteriert dabei über alle Knoten des Graphen, was \(O(n)\) Zeit benötigt. Die innere Schleife iteriert ebenfalls über alle Knoten, was nochmals \(O(n)\) Zeit in Anspruch nimmt. Innerhalb der inneren Schleife wird überprüft, ob eine Kante bereits existiert, was im schlimmsten Fall \(O(n)\) dauert, falls jeder Knoten mit allen anderen Knoten verbunden ist. Das Hinzufügen einer neuen Kante erfolgt in konstanter Zeit \(O(1)\).

Die Gesamtlaufzeit der Methode lässt sich daher durch die Verschachtelung der Schleifen und die darin enthaltenen Operationen abschätzen was im schlimmsten Fall eine theoretische Laufzeit von \(O(n^3)\) ergibt.

## Task 2
Die Tiefensuche ist in unserem Fall eine rekursive
## Task 3
