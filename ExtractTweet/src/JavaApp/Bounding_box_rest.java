package JavaApp;

public class Bounding_box_rest {

	private int id;
	private double center_longitude;
	private double center_latitude;
	private double radius;
	private String unit;
	public Bounding_box_rest() {

		unit = "km";
	}
	public Bounding_box_rest(int id,double center_longitude, double center_latitude,
			double radius, String unit) {
		super();
		this.setId(id);
		this.center_longitude = center_longitude;
		this.center_latitude = center_latitude;
		this.radius = radius;
		this.unit = unit;
	}
	public double getCenter_longitude() {
		return center_longitude;
	}
	public void setCenter_longitude(double center_longitude) {
		this.center_longitude = center_longitude;
	}
	public double getCenter_latitude() {
		return center_latitude;
	}
	public void setCenter_latitude(double center_latitude) {
		this.center_latitude = center_latitude;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
