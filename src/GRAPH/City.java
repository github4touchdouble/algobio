package GRAPH;

public class City{
    String name;
    double lat;
    double lon;

    public City(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
    @Override
    public String toString() {
        return name + " (" + lat + ", " + lon + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof City)) return false;
        City c = (City) obj;
        return name.equals(c.name) && lat == c.lat && lon == c.lon;
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
