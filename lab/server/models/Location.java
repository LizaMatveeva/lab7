package ifmo.lab.server.models;
public class Location {
    private int id;
    private int x;
    private double y;
    private double z;
    public Location (int x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(int id, int x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location (){
    }



    public String toCSV() {
        return x + "," + y + "," + z;
    }

    public static Location fromCSV(String csv) {
        String[] parts = csv.split(",");
        Location location = new Location();
        location.x = Integer.parseInt(parts[0]);
        location.y = Double.parseDouble(parts[1]);
        location.z = Double.parseDouble(parts[2]);
        return location;
    }

    @Override
    public String toString() {
        return "Location{" +
                "\n\tx=" + x +
                ",\n\ty=" + y +
                ",\n\tz=" + z +
                "\n}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
