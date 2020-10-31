package br.ufc.crateus.compilers.ccompiler;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Main {

	public static void main(String[] args) {

		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
		SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer();

		HashMap<Token, String> table = new LinkedHashMap<Token, String>();

		table = lexicalAnalyzer.init();
		table.forEach((key, value) -> System.out.println(value + " ----> " + key.getKey()));
		//System.out.println(table.values());
		
		
		//syntacticAnalyzer.init(table);
		
		

	}

}
