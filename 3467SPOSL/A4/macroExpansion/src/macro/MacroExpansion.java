package macro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MacroExpansion {
	ArrayList<StatementASM> code;
	ArrayList<mntf> mnt;
	ArrayList<StatementASM> mdt;
	ArrayList<String> kpdt;
	HashMap<String, ArrayList<String>> pdt,apt;
	ArrayList<String> exp;
	
	public MacroExpansion() {
		code = new ArrayList<StatementASM>();
		mnt = new ArrayList<mntf>();
		mdt = new ArrayList<StatementASM>();
		pdt = new HashMap<String, ArrayList<String>>();
		apt = new HashMap<String, ArrayList<String>>();
		kpdt = new ArrayList<String>();
		exp = new ArrayList<String>();
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
	private int MacroExp(int macroindex) {
		String mn = code.get(macroindex).getOpcode();
		int pp=0,kp=0;
		ArrayList<String> p=new ArrayList<String>();
		ArrayList<String> a=new ArrayList<String>();
		ArrayList<String> mp = code.get(macroindex).getOperand(); 
		
		mntf m = new mntf();
		m.macro_name = mn;
		
		m.kpdtp = kpdt.size();
		m.mdtp = mdt.size();
		for(int i=0;i<mp.size();i++){
			if(mp.get(i).contains("=")){
				kp++;
				kpdt.add(mp.get(i).substring(mp.get(i).indexOf("&")+1,mp.get(i).indexOf("=") ));
				p.add(mp.get(i).substring(mp.get(i).indexOf("&")+1,mp.get(i).indexOf("=") ));
				String acp = mp.get(i).substring(mp.get(i).indexOf("=")+1,mp.get(i).length());
				if(acp.length()!=0){
					a.add(acp);
				}
				else{
					a.add("#");
				}
			}else{
				pp++;
				p.add(mp.get(i).replace(",", "").replace("&", ""));
				a.add("#");
				
			}
		}
		pdt.put(m.macro_name, p);
		apt.put(mn, a);
		m.pp = pp;
		m.kp = kp;
		mnt.add(m);
		macroindex++;
		int mcount=1;
		String op = code.get(macroindex).getOpcode();
		while(true){
			System.out.println("lakak");
			op = code.get(macroindex).getOpcode();
			if(op.equals("MEND")){
				mdt.add(code.get(macroindex));
				mcount--;
				if(mcount==0){
					break;
				}
			}else if(op.equals("MACRO")){
				mdt.add(code.get(macroindex));
				mcount++;
			}else{
				StatementASM as = code.get(macroindex);
				ArrayList<String> o = as.getOperand();
				ArrayList<String> newo = new ArrayList<String>();
				System.out.println(o.size());
				int l = o.size();
				try {
					Thread.sleep(0000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i=0;i<l;i++){
					System.out.println("alal");
					if(o.get(i).contains("&")){
						int index = pdt.get(mn).indexOf(o.get(i).replace("&", ""));
						newo.add("(P,"+index+")");
						
					}else{
						newo.add(o.get(i));
					}
					
				}
				as.setOperand(newo);
				mdt.add(as);
			}
			macroindex++;
		}
		 
		return ++macroindex;
	}
	private void expandMacro(StatementASM asm) {
		ArrayList<String> par = asm.getOperand();
		ArrayList<String> ap = apt.get(asm.getOpcode());
		ArrayList<String> pd = pdt.get(asm.getOpcode());
		
		int mnti = isMacroName(asm.getOpcode());
		if(mnti!=-1){
			mntf mi = mnt.get(mnti);
			int j=0;
			for(j=0;j<mi.pp;j++){
				ap.set(j, par.get(j));
			}
			while(j<par.size()){
				String[] kp = par.get(j).split("=");
				kp[0] = kp[0].replace("&", "");
				int k = pd.indexOf(kp[0]);
				System.out.println(kp[0]+ " "+kp[1]);
				
				ap.set(k, kp[1]);
				j++;
			}
			System.out.println("::::::::::::::::::::::::::");
			for(int m=0;m<ap.size();m++){
				System.out.println(ap.get(m));
			}
			j=mi.mdtp;
			while(!mdt.get(j).getOpcode().equals("MEND")){
				if(isMacroName(mdt.get(j).getOpcode())!=-1){
					ArrayList<String> op = mdt.get(j).getOperand();
					for(int m=0;m<op.size();m++){
						if(op.get(m).contains("P,")){
							String o = op.get(m).replace("(","").replace(")", "").replace("P,", "");
							op.set(m, ap.get(Integer.parseInt(o)));
						}
					}
					mdt.get(j).setOperand(op);
					expandMacro(mdt.get(j));
				}else{
					String f = "+"+mdt.get(j).getLabel() +" "+ mdt.get(j).getOpcode()+" ";
					ArrayList<String> op = mdt.get(j).getOperand();
					for(int m=0;m<op.size();m++){
						if(op.get(m).contains("P,")){
							String o = op.get(m).replace("(","").replace(")", "").replace("P,", "");
							f = f + ap.get(Integer.parseInt(o))+" ";
						}else{
							f = f + op.get(m)+ " ";
						}
						
					}
					System.out.println(f);
					exp.add(f);
				}
				j++;
			}
		}
	}

	
	public static void main(String[] args) {
		MacroExpansion exp = new MacroExpansion();
		exp.readCode();
		exp.createExp();
	}
	private void createExp() {
		int i=0;
		exp= new ArrayList<String>();
		int flag=0;
		while(i<code.size()){
			if(flag==0){
				if(code.get(i).getOpcode().equals("MACRO")){
					i = MacroExp(++i);
				}else if(code.get(i).getOpcode().equals("START")){
					flag=1;
					exp.add(code.get(i).getString());
					i++;
				}
			}else{
				if(isMacroName(code.get(i).getOpcode())!=-1){
					expandMacro(code.get(i));
				}else{
					exp.add(code.get(i).getString());
				}
				i++;
			}
			
		}
		System.out.println("MNT table:");
		for(int j=0;j<mnt.size();j++){
			mntf a =mnt.get(j);
			System.out.println(a.macro_name+" "+a.pp+" "+a.kp+" "+a.kpdtp+" "+a.mdtp+" ");

		}
		for(int j=0;j<mnt.size();j++){
			mntf a =mnt.get(j);
			System.out.println("PDTAB for "+a.macro_name);
			for(int k=0;k<pdt.get(a.macro_name).size();k++){
				System.out.println(pdt.get(a.macro_name).get(k));
				//System.out.println(apt.get(a.macro_name).get(k));
			}
		}
		System.out.println("MDT table:");
		for(int j=0;j<mdt.size();j++){
			System.out.println(mdt.get(j).getString());
		}
		System.out.println("KPDT table:");
		for(int j=0;j<kpdt.size();j++){
			System.out.println(kpdt.get(j));
		}
		System.out.println("EXPANDED CODE:");
		for(int j=0;j<exp.size();j++){
			System.out.println(exp.get(j));
		}
		
	}
	private int isMacroName(String opcode) {
		for(int j=0;j<mnt.size();j++){
			mntf a =mnt.get(j);
			if(a.macro_name.equals(opcode)){
				return j;
			}
		}
		return -1;
	}
}