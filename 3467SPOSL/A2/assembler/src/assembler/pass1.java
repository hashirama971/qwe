package assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class pass1 {
	ArrayList<StatementASM> code;
	HashMap<String,TabEntry> symboltable,pooltab;
	ArrayList<String> lit_tab,symbol_tab;
	ArrayList<Integer> poolptr;
	int lc,poolp,litdec=1;
	public pass1(){
		code = new ArrayList<StatementASM>();
		symboltable = new HashMap<String,TabEntry>();
		pooltab = new HashMap<String, TabEntry>();
		lit_tab  = new ArrayList<String>();
		symbol_tab = new ArrayList<String>();
		lc=0;
		poolp=0;
	}
	public void readCode(){
		try {
			FileReader read = new FileReader(new File("input1.asm"));
			BufferedReader r = new BufferedReader(read);
			String line;
			while((line = r.readLine()) != null) {
				 String[] str= line.split("\\s+");
				 ArrayList<String> operands = new ArrayList<String>();
				 for(int i=2;i<str.length;i++){
					 operands.add(str[i].replace(",", ""));
					
				 }
				 code.add(new StatementASM(str[0], str[1], operands));
                
            }   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		DirTab d = new DirTab();
		pass1 p = new pass1();
		p.readCode();
		try {
			p.generateIC(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void generateIC(DirTab tabs) throws Exception {
		int i=0;
		FileWriter writer = new FileWriter(new File("IC.txt"));
		BufferedWriter bw = new BufferedWriter(writer);
		while(i<code.size()){
			String gen,label,opcode;
			gen=label=opcode= " ";
			ArrayList<String> oprands = new ArrayList<String>();
			label = code.get(i).getLabel();
			opcode = code.get(i).getOpcode();
			oprands = code.get(i).getOperand();
			if(label.length()>0){
				if(symbol_tab.contains(label)){
					symboltable.put(label,new TabEntry(label, lc));
				}else{
					symbol_tab.add(label);
					symboltable.put(label,new TabEntry(label, lc));
				}
			}
		
			if(opcode.equals("START")||opcode.equals("ORIGIN")){
				int loc=0;
				if(opcode.equals("ORIGIN")){
					if(oprands.get(0).contains("+")){
						String[] op = oprands.get(0).split("\\+");
						int k=0;
						String g = new String();
						while(k<op.length){
							if(symboltable.containsKey(op[k])){
								
								if(k==op.length-1){
									g = g + "(S,0"+(symbol_tab.indexOf(op[0])+1)+")";
								}else{
									g = g + "(S,0"+(symbol_tab.indexOf(op[0])+1)+")+";
								}
								loc = loc + symboltable.get(op[k]).getAddress();
							}else{
								g = g + Integer.parseInt(op[1].trim());
								loc = loc + Integer.parseInt(op[1].trim());
							}
							k++;
							
						}
					
						gen = lc + " (AD,0"+tabs.getAD().indexOf(opcode)+") "+g;
					}else if(oprands.get(0).contains("-")){
						String[] op = oprands.get(0).split("\\-");
						int k=0;
						String g = new String();
						while(k<op.length){
							if(symboltable.containsKey(op[k])){
								
								if(k==op.length-1){
									g = g + "(S,0"+(symbol_tab.indexOf(op[0])+1)+")";
								}else{
									g = g + "(S,0"+(symbol_tab.indexOf(op[0])+1)+")-";
								}
								if(k==0){
									loc = loc + symboltable.get(op[k]).getAddress();
								}else{
									loc = loc - symboltable.get(op[k]).getAddress();
								}
							}else{
								g = g + Integer.parseInt(op[1].trim());
								loc = loc - Integer.parseInt(op[1].trim());
							}
							k++;
							
						}
						
						gen = lc + " (AD,0"+tabs.getAD().indexOf(opcode)+") "+g;
					}else{
						loc = Integer.parseInt(oprands.get(0));
						gen = " (AD,0"+tabs.getAD().indexOf(opcode)+") "+"(C,"+oprands.get(0)+")";
					}
					System.out.println(lc + gen);
					
				}else{
					loc = Integer.parseInt(oprands.get(0));
					gen = "    (AD,0"+tabs.getAD().indexOf(opcode)+") "+"(C,"+oprands.get(0)+")";
					System.out.println(lc + gen);
					
				}
				
				
				
				lc = loc;
				
				
			}else if(opcode.equals("EQU")){
				String[] opra = oprands.get(0).split("\\+");
				TabEntry tab = symboltable.get(opra[0]);
				int loc=0;
				gen = "";
				String g = "";
				int k=0;
				while(k<opra.length){
					if(symboltable.containsKey(opra[k])){
						
						if(k==opra.length-1){
							g = g + "(S,0"+(symbol_tab.indexOf(opra[0])+1)+")";
						}else{
							g = g + "(S,0"+(symbol_tab.indexOf(opra[0])+1)+")+";
						}
						loc = loc + symboltable.get(opra[k]).getAddress();
					}else{
						g = g + Integer.parseInt(opra[1].trim());
						loc = loc + Integer.parseInt(opra[1].trim());
					}
					k++;
					
				}
				symboltable.get(label).setAddress(loc);
				//gen = lc + " (AD,0"+tabs.getAD().indexOf(opcode)+") "+g;
				//lc++;
				System.out.println(gen);
			}else if(opcode.equals("LTORG")){
				while(poolp<lit_tab.size()){
					pooltab.put(lit_tab.get(poolp),new TabEntry(lit_tab.get(poolp), lc));
					lc++;
					poolp++;
				}
				lit_tab.add("###");
				pooltab.put("###", new TabEntry(lit_tab.get(poolp), 000));
				poolp++;
				litdec=0;
				gen="    (AD,0"+tabs.getAD().indexOf(opcode)+")";
			}else if(opcode.equals("END")){
				gen = lc + " (AD,0"+tabs.getAD().indexOf("END")+")";
				System.out.println(gen);
				lc++;
				while(poolp<lit_tab.size()){
					pooltab.put(lit_tab.get(poolp),new TabEntry(lit_tab.get(poolp), lc));
					lc++;
					poolp++;
				}
			}else if(tabs.getDL().contains(opcode)){
				
				int soc = Integer.parseInt(oprands.get(0).replace("'", ""));
				
				gen = lc + " (DL,0"+tabs.getDL().indexOf(opcode)+") "+"(C,"+soc+")";
				if(opcode.equals("DS")){
					lc = lc + soc;
				}else{
					lc++;
				}
				System.out.println(gen);
			}else if(tabs.getIS().contains(opcode)){
				
				String os = "(IS,0"+(tabs.getIS().indexOf(opcode)-1)+") ";
				int j = 0;
				String oprs = "";
				while(j<oprands.size()){
					if(tabs.getREG().contains(oprands.get(j))){
						oprs = oprs + "("+tabs.getREG().indexOf(oprands.get(j))+") ";
					}else{
						if(oprands.get(j).contains("=")){
							lit_tab.add(oprands.get(j).replace("=", "").replace("'", ""));
							int l =lit_tab.indexOf(oprands.get(j).replace("=", "").replace("'", "")) +litdec;
							oprs = oprs + "(L,0"+l+") ";
						}else if(symbol_tab.contains(oprands.get(j))){
							//do nothing for now
							int l = symbol_tab.indexOf(oprands.get(j))+1;
							oprs = oprs + "(S,0"+l+") ";
						}else{
							symbol_tab.add(oprands.get(j));
							int l = symbol_tab.indexOf(oprands.get(j))+1;
							oprs = oprs + "(S,0"+l+") ";
						}
						
					}
					j++;
				}
				gen = lc+ " " +os + oprs;
				System.out.println(lc+ " " +os + oprs);
				lc++;
			}
			if(!gen.equals("")){
				bw.write(gen+"\n");
				bw.flush();
			}
			i++;
		}
		bw.close();
		
		int p=0;
		for(p=0;p<poolp;p++){
			//System.out.println(lit_tab.get(0));
			System.out.println(pooltab.get(lit_tab.get(0)).getLabel()+ "   "+pooltab.get(lit_tab.get(0)).getAddress());
		}
		int k=0;
		try{
			for(k=0;k<symbol_tab.size();k++){
				System.out.println(symboltable.get(symbol_tab.get(k)).getLabel()+ "   "+symboltable.get(symbol_tab.get(k)).getAddress());
			}
			writeSymbolTable();
			writeLiteralTable();
		}catch(NullPointerException ea){
			System.out.println("Symbol '"+ symbol_tab.get(k) +"' not defined in the code");
		}
		
		
		
	}
	
	private void writeLiteralTable() {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("Literal.txt"));
			BufferedWriter bw = new BufferedWriter(writer);
			int k;
			bw.write("LITERAL  ADDRESS\n");
			bw.flush();
			for(k=0;k<poolp;k++){
				bw.write(pooltab.get(lit_tab.get(k)).getLabel()+ "   "+pooltab.get(lit_tab.get(k)).getAddress()+"\n");
				bw.flush();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void writeSymbolTable() {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("Symbol.txt"));
			BufferedWriter bw = new BufferedWriter(writer);
			int k;
			bw.write("SYMBOL  ADDRESS\n");
			bw.flush();
			for(k=0;k<symbol_tab.size();k++){
				bw.write(symboltable.get(symbol_tab.get(k)).getLabel()+ "   "+symboltable.get(symbol_tab.get(k)).getAddress()+"\n");
				bw.flush();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
