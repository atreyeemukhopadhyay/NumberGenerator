package com.atreyee.processor;

import com.atreyee.processor.generator.Generator;
import com.atreyee.processor.util.ApplicationMessage;
import com.atreyee.processor.util.PropertyValues;
import com.atreyee.processor.exception.InvalidDataException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NumberProcessor {
    public static final int GENERATOR_FACTOR_FIRST = 16807;
    public static final int GENERATOR_FACTOR_SECOND = 48271;

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        int generatorInitialValue = getNumberGeneratorInitialValue(inputScanner, "Generator A");
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, generatorInitialValue);

        generatorInitialValue = getNumberGeneratorInitialValue(inputScanner, "Generator B");
        Generator generatorB = new Generator(GENERATOR_FACTOR_SECOND, generatorInitialValue);

        try {
            PropertyValues.loadProperties("application.properties");
            int matchingNumberCount = generatorA.findGeneratorsMatchingNumberCount(generatorB);
            System.out.println("Matching count : " + matchingNumberCount);
        }catch (InvalidDataException ex){
            System.err.println(ex.getMessage());
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
        inputScanner.close();
    }

    public static int getNumberGeneratorInitialValue(Scanner inputScanner, String generatorName) {
        while (true) {
            System.out.println("Initial number for " + generatorName);
            try {
                int generatorInitialValue = inputScanner.nextInt();
                if (generatorInitialValue > 0) {
                    return generatorInitialValue;
                } else {
                    System.err.println(ApplicationMessage.GENERATOR_INITIAL_VALUE_MESSAGE + generatorName);
                }
            } catch (InputMismatchException ex) {
                System.err.println(ApplicationMessage.GENERATOR_INVALID_INITIAL_VALUE + generatorName);
                inputScanner.nextLine();
            }
        }
    }
}
