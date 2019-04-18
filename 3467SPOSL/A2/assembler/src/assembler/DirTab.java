package assembler;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class DirTab {
	java.util.List<String> AD;
	java.util.List<String> IS;
	java.util.List<String> DL;
	java.util.List<String> REG;
	
	public DirTab(){
		try {
			FileReader read = new FileReader(new File("assembler_directive.txt"));
			BufferedReader r = new BufferedReader(read);
			String line;
			while((line = r.readLine()) != null) {
                System.out.println(line);
                String[] str= line.split("\\s+");
                if(str[0].equals("AD")){
                	line.replace("AD ", "").trim();
                	str = line.split("\\s+");
                	AD =  Arrays.asList(str);
                }else if(str[0].equals("IS")){
                	line.replace("IS ", "").trim();
                	str = line.split("\\s+");
                	IS =  Arrays.asList(str);
                }else if(str[0].equals("DL")){
                	line.replace("DL ", "").trim();
                	str = line.split("\\s+");
                	DL =  Arrays.asList(str);
                }else if(str[0].equals("REG")){
                	line.replace("REG ", "").trim();
                	str = line.split("\\s+");
                	REG =  Arrays.asList(str);
                }
                
            }   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getAD() {
		return AD;
	}

	public List<String> getIS() {
		return IS;
	}

	public List<String> getDL() {
		return DL;
	}

	public List<String> getREG() {
		return REG;
	}

	
}
