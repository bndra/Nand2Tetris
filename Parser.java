package assembler;
import java.util.Scanner;

enum commands {
	A_COMMAND, C_COMMAND, L_COMMAND, INVALID;
}

public class Parser {
	private Scanner asm;
	private String currentCommand;
	
	public Parser(Scanner asmScanner) {
		asm = asmScanner;
	}
	public boolean hasMoreCommands() {
		if (asm.hasNext())
			return true;
		else
			return false;
	}
	public void advance() {
		if (hasMoreCommands()) {
			currentCommand = asm.next();
			if (currentCommand.contains("//")) {
				asm.nextLine();
				currentCommand = asm.next();
			}
		}
	}
	public commands commandType() {
		if (currentCommand.contains("@"))
			return commands.A_COMMAND;
		else if (currentCommand.contains("("))
			return commands.L_COMMAND;
		else if (currentCommand.contains("=") || currentCommand.contains(";"))
			return commands.C_COMMAND;
		else
			return commands.INVALID;
	}
	public String symbol() {
		if (commandType()==commands.A_COMMAND) {
			return currentCommand.substring(1);
		}
		else if (commandType()==commands.L_COMMAND) {
			String temp = currentCommand.substring(1);
			return temp.replace(")", "");
		}
		else
			return "bad command type";
	}
	public String dest() {
		if (commandType()==commands.C_COMMAND) {
			if (currentCommand.contains("=")) {
				int equalsIndex = currentCommand.indexOf("=");
				return currentCommand.substring(0, equalsIndex);
			}
			else
				return "null";
		}
		else
			return "bad command type";
	}
	public String comp() {
		if (commandType()==commands.C_COMMAND) {
			if (currentCommand.contains(";")) {
				int colonIndex = currentCommand.indexOf(";");
				return currentCommand.substring(0, colonIndex);
			}
			else if (currentCommand.contains("=")) {
				int equalsIndex = currentCommand.indexOf("=");
				return currentCommand.substring(equalsIndex+1);
			}
			else
				return "null";
		}
		else
			return "bad command type";
	}
	public String jump() {
		if (commandType()==commands.C_COMMAND) {
			if (currentCommand.contains(";")) {
				int colonIndex = currentCommand.indexOf(";");
				return currentCommand.substring(colonIndex+1);
			}
			else
				return "null";
		}
		else
			return "bad command type";
	}
	public String getCommand() {
		return currentCommand;
	}
}
