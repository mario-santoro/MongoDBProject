package bean;

public class CoppiaXY {
	//Le X del grafico
	String ascissa;
	//le Y del grafico
	int ordinata;

	public CoppiaXY() {}
	public CoppiaXY(String ascissa, int ordinata) {
		this.ascissa=ascissa;
		this.ordinata=ordinata;

	}
	public String getAscissa() {
		return ascissa;
	}
	public void setAscissa(String ascissa) {
		this.ascissa = ascissa;
	}
	public int getOrdinata() {
		return ordinata;
	}
	public void setOrdinata(int ordinata) {
		this.ordinata = ordinata;
	}


}
