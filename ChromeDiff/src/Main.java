import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		String path = "C:\\Users\\guga\\Documents\\Pesquisa\\Analise\\diffs chrome";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		String cve = "";
		List<String> diff;
		List<Structure> structures = new ArrayList<>();
		
		for (File file : listOfFiles){
			cve = file.getName().replace(".txt", "");
            diff = readFile(path + "\\" + file.getName());
            getStructures(diff, structures, cve);
            
        }
		
		//System.out.println(structures.get(0).getCve() + " | " + structures.get(0).getStructure());
		for (Structure s : structures){
			System.out.println(s.getStructure() + " | " + s.getCve() + " | " + s.getCommit() + " | " + s.getFile());
		}

	}
	
	public static List<String> readFile(String name){
		List<String> file = new ArrayList<>();
		
		try {
			FileReader reader = new FileReader(name);
			BufferedReader leitor = new BufferedReader(reader);
			String linha = null;
			while(leitor.ready()) { 
				linha = leitor.readLine();
			    file.add(linha);
			}
			leitor.close();  
			reader.close();
		} catch (IOException e) {
			System.out.println(name + " not found!\n");
			file = null;
		}
		
		return file;
	}
	
	public static void getStructures(List<String> diff, List<Structure> structures, String cve){
		Structure structure;
		String line = "";
		String commit = "";
		String file = "";
		String result = "";
		
		Pattern patternFile1 = Pattern.compile("(diff .*)");
		Pattern patternFile2 = Pattern.compile("--- .*	");
		Pattern patternCommit = Pattern.compile("(commit .*)");
		Pattern patternStructure = Pattern.compile("(@@ .* @@.*)");
		Matcher matcher;
		
		for(int i = 0; i < diff.size(); i++){
			line = diff.get(i);
			
			matcher = patternCommit.matcher(line);
			if(matcher.find()){
				commit = matcher.group().split(" ")[1];
			}
			
			matcher = patternFile1.matcher(line);
			if(matcher.find()){
				file = matcher.group().split(" b")[1];
				//System.out.println("\t" + file);
			}else{
				matcher = patternFile2.matcher(line);
				if(matcher.find()){
					file = matcher.group().replaceFirst("---.*branches", "").replaceFirst("	.*	", "");
					//System.out.println("\t" + file);
				}
			}
			
			matcher = patternStructure.matcher(line);
            if(matcher.find()) {
            	structure = new Structure();
            	result = matcher.group().replaceFirst("@@.*@@ ", "");
            	//System.out.println(cve);
            	//System.out.println("\t\t" + result);
            	structure.setStructure(result);
            	structure.setCommit(commit);
            	structure.setFile(file);
            	structure.setCve(cve);
            	structures.add(structure);
            }
		}
	}

}
