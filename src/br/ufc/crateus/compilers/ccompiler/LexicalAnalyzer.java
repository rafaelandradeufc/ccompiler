package br.ufc.crateus.compilers.ccompiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

	public HashMap<Token, String> init() {

		HashMap<Token, String> table = new HashMap<Token, String>();

		try {
			FileReader arqCodes = new FileReader("src/br/ufc/crateus/compilers/ccompiler/input-codes.txt");

			BufferedReader lerArqCodes = new BufferedReader(arqCodes);

			String strings = "\".*\"";
			String primitiveTypes = "long long int|long int|short int|long double|float|double|char|int";
			String variables = "(" + primitiveTypes + ") [a-zA-Z]+[0-9]*";
			String conditionals = "if|else|switch";
			String arithmeticOperators = "\\/|\\*|\\-|\\+|\\%";
			String relationalOperators = "==|!=|>=|<=|>|<";
			String logicalOperators = "\\|\\||&&|!";
			String assignmentCommand = " = ";
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

			int lineCounts = 1;
			
			while (lineCodes != null) {

				FileReader arqReserveds = new FileReader("src/br/ufc/crateus/compilers/ccompiler/reserved-words.txt");

				BufferedReader lerArqReserveds = new BufferedReader(arqReserveds);

				String lineReserveds = lerArqReserveds.readLine();

				/** Strings **/
				Matcher matcherStrings = patternStrings.matcher(lineCodes);

				while (matcherStrings.find()) {
					table.put(new Token(matcherStrings.group(0),lineCounts), "String");
					lineCodes = matcherStrings.replaceAll("");
				}

				/** Reserveds **/
				while (lineReserveds != null) {

					Pattern patternReserveds = Pattern.compile(lineReserveds, Pattern.MULTILINE);
					Matcher matcherReserveds = patternReserveds.matcher(lineCodes);

					while (matcherReserveds.find()) {
						table.put(new Token(matcherReserveds.group(0), lineCounts), "Keyword");
					}

					lineReserveds = lerArqReserveds.readLine();
				}
				arqReserveds.close();

				/** Variables **/
				Matcher matcherVariables = patternVariables.matcher(lineCodes);

				while (matcherVariables.find()) {

					String variableAux[] = matcherVariables.group(0).trim().split(" ");

					table.put(new Token(variableAux[variableAux.length - 1], lineCounts), "Identificador");

				}

				/** Conditionals **/
				Matcher matcherConditionals = patternConditionals.matcher(lineCodes);

				while (matcherConditionals.find()) {
					table.put(new Token(matcherConditionals.group(0).trim(), lineCounts), "Condicional" );
				}

				/** Arithmetic Operators **/

				Matcher matcherArithmeticOperators = patternArithmeticOperators.matcher(lineCodes);

				while (matcherArithmeticOperators.find()) {
					table.put(new Token(matcherArithmeticOperators.group(0).trim(), lineCounts), "Arithmetic Operator");
				}

				/** Relational Operators **/

				Matcher matcherRelationalOperators = patternRelationalOperators.matcher(lineCodes);

				while (matcherRelationalOperators.find()) {
					table.put(new Token(matcherRelationalOperators.group(0).trim(),lineCounts), "Relational Operator");
				}

				/** Logical Operators **/
				Matcher matcherLogicalOperators = patternLogicalOperators.matcher(lineCodes);

				while (matcherLogicalOperators.find()) {
					table.put(new Token(matcherLogicalOperators.group(0).trim(),lineCounts), "Logical Operator" );
				}

				/** Assignment Command **/
				Matcher matcherAssignmentCommand = patternAssignmentCommand.matcher(lineCodes);

				while (matcherAssignmentCommand.find()) {
					table.put(new Token(matcherAssignmentCommand.group(0), lineCounts), "Assignment Command");
				}

				/** Constants **/

				Matcher matcherConstants = patternConstants.matcher(lineCodes);

				if (matcherConstants.find()) {
					table.put(new Token(matcherConstants.group(0).trim(), lineCounts), "Constant");
				}

				/** Delimiters **/

				Matcher matcherDelimiters = patternDelimiters.matcher(lineCodes);

				if (matcherDelimiters.find()) {
					table.put(new Token(matcherDelimiters.group(0).trim(), lineCounts), "Delimiter");
				}

				/** Command Blocks **/
				Matcher matcherCommandBlocks = patternCommandBlocks.matcher(lineCodes);

				while (matcherCommandBlocks.find()) {
					table.put(new Token(matcherCommandBlocks.group(0).trim(), lineCounts), "Command Block");
				}

				lineCodes = lerArqCodes.readLine();
				lineCounts++;
			}

			arqCodes.close();

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

		return table;
	}

}
