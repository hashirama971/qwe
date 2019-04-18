package macro;

import java.util.ArrayList;

public class StatementASM {
	String label;
	String opcode;
	ArrayList<String> operand;
	
	public StatementASM(String l,String op,ArrayList<String> o){
		label = l;
		opcode = op;
		operand = o;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public ArrayList<String> getOperand() {
		return operand;
	}

	public void setOperand(ArrayList<String> operand) {
		this.operand = operand;
	}
	public String getString(){
		String a="";
		a = label +" " +opcode+" ";
		for(int i=0;i<operand.size();i++){
			a = a+operand.get(i)+" ";
		}
		return a;
		
	}
}
