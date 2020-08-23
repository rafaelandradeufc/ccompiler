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

			String prints = "\".*\"";
			String primitiveTypes = "(long long int|long int|short int|long double|float|double|char|int)";
			String variables = primitiveTypes + " [a-zA-Z]+[0-9]*";

			Pattern patternPrints = Pattern.compile(prints, Pattern.MULTILINE);
			Pattern patternVariables = Pattern.compile(variables, Pattern.MULTILINE);

			String lineCodes = lerArqCodes.readLine();

			while (lineCodes != null) {

				FileReader arqReserveds = new FileReader("src/br/ufc/crateus/compilers/ccompiler/reserved-words.txt");

				BufferedReader lerArqReserveds = new BufferedReader(arqReserveds);

				String lineReserveds = lerArqReserveds.readLine();

				while (lineReserveds != null) {

					Pattern patternReserveds = Pattern.compile(lineReserveds.trim(), Pattern.MULTILINE);
					Matcher matcherReserveds = patternReserveds.matcher(lineCodes);

					/** Reserveds **/
					if (matcherReserveds.find()) {
						table.put(matcherReserveds.group(0), "Reserved");
					}

					lineReserveds = lerArqReserveds.readLine();
				}
				arqReserveds.close();

				Matcher matcherVariables = patternVariables.matcher(lineCodes);

				/** Variables **/
				if (matcherVariables.find()) {

					String variableAux[] = matcherVariables.group(0).trim().split(" ");

					table.put(variableAux[variableAux.length - 1], "Variable");

				}

				Matcher matcherPrints = patternPrints.matcher(lineCodes);

				/** Prints **/
				if (matcherPrints.find()) {
					table.put(matcherPrints.group(0), "Prints");
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
