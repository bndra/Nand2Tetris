package JackSyntaxAnalyzer;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main 
{

	public static void main(String[] args) throws IOException 
	{
		Scanner console = new Scanner(System.in);
		String dir = console.next();
		System.out.println(dir.replace("\\", "\\\\"));
		File jackFile = new File(dir.replace("\\", "\\\\"));
		Scanner input = new Scanner(jackFile);
		File xmlFile = new File(dir.replace(".jack", ".xml"));
		BufferedWriter output = new BufferedWriter(new FileWriter(xmlFile));
		CompilationEngine eng = new CompilationEngine(input, output);
		eng.CompileClass();
		output.flush();
	}
}
