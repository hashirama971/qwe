package assembler;

public class TabEntry {
	String label;
	int address;
	
	public TabEntry(String lol,int add) {
		label = lol;
		address = add;
		
	}

	public String getLabel() {
		return label;
	}

	public void setLaborlit(String label) {
		this.label = label;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}
	
}
