package assembler;//
import java.lang.Integer;
public class Code {
	public String dest(String d) {
		String binaryCode="";
		if (d.contains("A"))
			binaryCode+="1";
		else
			binaryCode+="0";
		if (d.contains("M"))
			binaryCode+="1";
		else
			binaryCode+="0";
		if (d.contains("D"))
			binaryCode+="1";
		else
			binaryCode+="0";
		return binaryCode;
	}
	public String comp(String c) {
		if (c=="0")
			return "0101010";
		else if (c=="1")
			return "0111111";
		else if (c=="-1")
			return "0111010";
		else if (c=="D")
			return "0001100";
		else if (c=="A")
			return "0110000";
		else if (c=="!D")
			return "0001101";
		else if (c=="!A")
			return "0110001";
		else if (c=="-D")
			return "0001111";
		else if (c=="-A")
			return "0110011";
		else if (c=="D+1")
			return "0011111";
		else if (c=="A+1")
			return "0110111";
		else if (c=="D-1")
			return "0001110";
		else if (c=="A-1")
			return "0110010";
		else if (c=="D+A")
			return "0000010";
		else if (c=="D-A")
			return "0010011";
		else if (c=="A-D")
			return "0000111";
		else if (c=="D&A")
			return "0000000";
		else if (c=="D|A")
			return "0010101";
		else if (c=="M")
			return "1110000";
		else if (c=="!M")
			return "1110001";
		else if (c=="-M")
			return "1110011";
		else if (c=="M+1")
			return "1110111";
		else if (c=="M-1")
			return "1110010";
		else if (c=="D+M")
			return "1000010";
		else if (c=="D-M")
			return "1010011";
		else if (c=="M-D")
			return "1000111";
		else if (c=="D&M")
			return "1000000";
		else if (c=="D|M")
			return "1010101";
		else
			return "0000000";
	}
	public String jump(String j) {
		if (j=="JGT")
			return "001";
		else if (j=="JEQ")
			return "010";
		else if (j=="JGE")
			return "011";
		else if (j=="JLT")
			return "100";
		else if (j=="JNE")
			return "101";
		else if (j=="JLE")
			return "110";
		else if (j=="JMP")
			return "111";
		else 
			return "000";
	}
	public String cInstruction(String d, String c, String j) {
		String machineInstruction = "111";
		machineInstruction += comp(c) + dest(d) + jump(j);
		return machineInstruction;
	}
	public String binaryCalc(String literal) {
		int dec = Integer.parseInt(literal);
		String bin = "";
		for (int i=0; i < 16; i++) {
			if (dec%2 == 0)
				bin = "0" + bin;
			else
				bin = "1" + bin;
			dec = dec/2;
		}
		return bin;
	}
}
