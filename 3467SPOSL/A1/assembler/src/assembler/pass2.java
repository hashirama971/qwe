package assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class pass2 {
	ArrayList<StatementASM> ic;
	ArrayList<TabEntry> symbol,literal;
	int poolp;
	public pass2(){
		ic = new ArrayList<StatementASM>();
		symbol = new ArrayList<TabEntry>();
		literal = new ArrayList<TabEntry>();
		poolp=0;
	}
	
	private void readTables() throws Exception {
		FileReader reader = new FileReader(new File("Symbol.txt"));
		BufferedReader r = new BufferedReader(reader);
		String line=r.readLine();
		while((line=r.readLine())!=null){
			String[] state = line.split("\\s+");
			symbol.add(new TabEntry(state[0],Integer.parseInt(state[1])));
		}
		
		reader = new FileReader(new File("Literal.txt"));
		r = new BufferedReader(reader);
		line=r.readLine();
		int i=0;
		while((line=r.readLine())!=null){
			String[] state = line.split("\\s+");
			if(state[0].equals("###")){
				poolp = i;
			}else{
				literal.add(new TabEntry(state[0],Integer.parseInt(state[1])));
				i++;
			}
			
		}
		
	}
	private void readIC() throws Exception {
		FileReader reader = new FileReader(new File("IC.txt"));
		BufferedReader r = new BufferedReader(reader);
		String line = null;
		while((line=r.readLine())!=null){
			String[] state = line.split("\\s+");
			ArrayList<String> opr = new ArrayList<String>();
			for(int i=2;i<state.length;i++){
				opr.add(state[i]);
			}
			ic.add(new StatementASM(state[0], state[1], opr));
			System.out.println(state[0] + " {}{} " + state[1]);
		}

	}
	public static void main(String[] args) throws Exception {
		pass2 p = new pass2();
		p.readTables();
		p.readIC();
		p.createOutput();
	}

	private void createOutput() throws Exception {
		
		FileWriter w = new FileWriter(new File("machinecode.txt"));
		BufferedWriter wr = new BufferedWriter(w);
		for(StatementASM asm : ic){
			String[] opsplit = asm.getOpcode().replace("(", "").replace(")", "").split("\\,");
			if(opsplit[0].equals("IS")){
				String st = "+"+String.format("%02d",Integer.parseInt(opsplit[1]))+" ";
				ArrayList<String> opr = asm.getOperand();
				if(opr.size()==0){
					st = st + "0 000"; 
				}
				else if(opr.size()==1){
					String[] osplit = opr.get(0).replace("(", "").replace(")", "").split("\\,");
					st = st + "0" + " ";
					
					if(osplit.length==1){
						st =st + osplit[0] + " ";
					}else if(osplit.length==2){
						
						int addr=0;
						if(osplit[0].equals("S")){
							addr = symbol.get(Integer.parseInt(osplit[1])-1).getAddress();
						}else if(osplit[0].equals("L")){
							addr = literal.get(Integer.parseInt(osplit[1])-1).getAddress();
						}
						
						st = st + addr+ " ";
					}
				}else{
					for(String op:asm.getOperand()){
						String[] osplit = op.replace("(", "").replace(")", "").split("\\,");
						if(osplit.length==1){
							st =st + osplit[0] + " ";
						}else if(osplit.length==2){
							int addr=0;
							if(osplit[0].equals("S")){
								addr = symbol.get(Integer.parseInt(osplit[1])-1).getAddress();
							}else if(osplit[0].equals("L")){
								addr = literal.get(Integer.parseInt(osplit[1])-1).getAddress();
							}
							
							st = st + String.format("%03d", addr)+ " ";
						}
					}
				}
				System.out.println(st);
				wr.write(st + "\n");
			}else if(opsplit[0].equals("DL")){
				String st = "+00 ";
				int code = Integer.parseInt(opsplit[1]);
				int size =0;
				int c = Integer.parseInt(asm.getOperand().get(0).replace("(", "").replace(")", "").split("\\,")[1]);
				if(code==1){
					size = c;
				}else if(code==2){
					size=1;
				}
				st = st + "0 " + String.format("%03d", c);
				System.out.println(st);
				wr.write(st + "\n");
			}else if(opsplit[0].equals("AD")){
				if(Integer.parseInt(opsplit[1])==5){
					String st = "+00 ";
					int i=0;
					while(i<poolp){
						String pt;
						pt = st + "0 " + String.format("%03d", Integer.parseInt(literal.get(i).label));
						System.out.println(pt);
						wr.write(pt + "\n");
						i++;
					}
				}
			}
		}
		wr.close();
	}
}
