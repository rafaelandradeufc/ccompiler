package br.ufc.crateus.compilers.ccompiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

	public static void main(String[] args) {

		HashMap<String, String> table = new HashMap<>();

		try {
			FileReader arqCodes = new FileReader("src/br/ufc/crateus/compilers/ccompiler/input-codes.txt");

			BufferedReader lerArqCodes = new BufferedReader(arqCodes);

			String strings = "\".*\"";
			String primitiveTypes = "long long int|long int|short int|long double|float|double|char|int";
			String variables = "(" + primitiveTypes + ") [a-zA-Z]+[0-9]*";
			String conditionals = "if|else|switch";
			String arithmeticOperators = "\\/|\\*|\\-|\\+|%";
			String relationalOperators = "==|!=|>=|<=|>|<";
			String logicalOperators = "\\|\\||&&|!";
			String assignmentCommand = "=";
			String constants = "[0-9]+";
			String delimiters = "[;:,\\)\\(#@]";
			String commandBlocks = "\\{|\\}";
			

			Pattern patternStrings = Pattern.compile(strings, Pattern.MULTILINE);
			Pattern patternVariables = Pattern.compile(variables, Pattern.MULTILINE);
			Pattern patternConditionals = Pattern.compile(conditionals, Pattern.MULTILINE);
			Pattern patternArithmeticOperators = Pattern.compile(arithmeticOperators, Pattern.MULTILINE);
			Pattern patternRelationalOperators = Pattern.compile(relationalOperators, Pattern.MULTILINE);
			Pattern patternLogicalOperators = Pattern.compile(logicalOperators, Pattern.MULTILINE);
			Pattern patternAssignmentCommand = Pattern.compile(assignmentCommand, Pattern.MULTILINE);
			Pattern patternConstants = Pattern.compile(constants, Pattern.MULTILINE);
			Pattern patternDelimiters = Pattern.compile(delimiters, Pattern.MULTILINE);
			Pattern patternCommandBlocks = Pattern.compile(commandBlocks, Pattern.MULTILINE);
			
			
			String lineCodes = lerArqCodes.readLine();

			while (lineCodes != null) {

				FileReader arqReserveds = new FileReader("src/br/ufc/crateus/compilers/ccompiler/reserved-words.txt");

				BufferedReader lerArqReserveds = new BufferedReader(arqReserveds);

				String lineReserveds = lerArqReserveds.readLine();
				
				/** Strings **/
				Matcher matcherStrings = patternStrings.matcher(lineCodes);

				if (matcherStrings.find()) {
					//table.put(matcherStrings.group(0), "Strings");
					lineCodes = matcherStrings.replaceAll("");
				}

				/** Reserveds **/
				while (lineReserveds != null) {

					Pattern patternReserveds = Pattern.compile(lineReserveds.trim(), Pattern.MULTILINE);
					Matcher matcherReserveds = patternReserveds.matcher(lineCodes);

					if (matcherReserveds.find()) {
						 table.put(matcherReserveds.group(0), "Palavra-Chave");
					}

					lineReserveds = lerArqReserveds.readLine();
				}
				arqReserveds.close();

				/** Variables **/
				Matcher matcherVariables = patternVariables.matcher(lineCodes);

				if (matcherVariables.find()) {

					String variableAux[] = matcherVariables.group(0).trim().split(" ");

					 table.put(variableAux[variableAux.length - 1], "Identificador");

				}
				
				
				/** Conditionals **/
				Matcher matcherConditionals = patternConditionals.matcher(lineCodes);
				
				if(matcherConditionals.find()) {
					table.put(matcherConditionals.group(0).trim(), "Condicionals");	
				}
				
				/** Arithmetic Operators **/
				Matcher matcherArithmeticOperators = patternArithmeticOperators.matcher(lineCodes);
				
				if(matcherArithmeticOperators.find()) {
					table.put(matcherArithmeticOperators.group(0).trim(), "Arithmetic Operators");	
				}
				
				/** Relational Operators **/
				Matcher matcherRelationalOperators = patternRelationalOperators.matcher(lineCodes);
				
				if(matcherRelationalOperators.find()) {
					table.put(matcherRelationalOperators.group(0).trim(), "Relational Operators");	
				}
				
				/** Logical Operators **/
				Matcher matcherLogicalOperators = patternLogicalOperators.matcher(lineCodes);
				
				if(matcherLogicalOperators.find()) {
					table.put(matcherLogicalOperators.group(0).trim(), "Arithmetic Operators");	
				}
				
				/** Assignment Command **/
				Matcher matcherAssignmentCommand = patternAssignmentCommand.matcher(lineCodes);
				
				if(matcherAssignmentCommand.find()) {
					table.put(matcherAssignmentCommand.group(0).trim(), "Assignment Command");	
				}
				
				/** Constants **/
				Matcher matcherConstants = patternConstants.matcher(lineCodes);
				
				if(matcherConstants.find()) {
					table.put(matcherConstants.group(0).trim(), "Constants");	
				}
				
				/** Delimiters **/
				Matcher matcherDelimiters = patternDelimiters.matcher(lineCodes);
				
				if(matcherDelimiters.find()) {
					table.put(matcherDelimiters.group(0).trim(), "Delimiters");	
				}
				
				/** Command Blocks **/
				Matcher matcherCommandBlocks = patternCommandBlocks.matcher(lineCodes);
				
				if(matcherCommandBlocks.find()) {
					table.put(matcherCommandBlocks.group(0).trim(), "Command Blocks");	
				}
				
				
				
				lineCodes = lerArqCodes.readLine();
			}

			table.forEach((key, value) -> System.out.println(value + " : " + key));

			arqCodes.close();

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

	}

}
