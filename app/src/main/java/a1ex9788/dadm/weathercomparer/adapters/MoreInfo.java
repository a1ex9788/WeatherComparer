package a1ex9788.dadm.weathercomparer.adapters;

public class MoreInfo {

	private String title, value;
	private int image;

	public MoreInfo() {
	}

	public MoreInfo(String title, String value, int image) {
		this.title = title;
		this.value = value;
		this.image = image;
	}

	public String getMoreInfoTitle() {
		return this.title;
	}

	public String getMoreInfoValue() {
		return this.value;
	}

	public int getImage() {
		return this.image;
	}

}
