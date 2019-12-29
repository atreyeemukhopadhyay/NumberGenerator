package com.atreyee.processor.generator;

import com.atreyee.processor.exception.InvalidDataException;
import com.atreyee.processor.util.ApplicationMessage;
import com.atreyee.processor.util.PropertyValues;

public class Generator {
    private int matchingNumberCount = 0;
    private int generatorFactor;
    private long iterationBaseValue;

    public long getIterationBaseValue() {
        return iterationBaseValue;
    }

    public Generator(int generatorFactor, int initialValue) {
        this.generatorFactor = generatorFactor;
        this.iterationBaseValue = initialValue;
    }

    public void generateNextIterationInputNumber() {
        long multiplication = this.iterationBaseValue * generatorFactor;
        String divisorFactor = PropertyValues.getPropertyValue("divisor.factor");
        try {
            if (!divisorFactor.trim().equals("")) {
                int factor = Integer.parseInt(divisorFactor);
                this.iterationBaseValue = multiplication % factor;
            } else {
                throw new InvalidDataException(ApplicationMessage.DIVISOR_FACTOR_NOT_FOUND_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            throw new InvalidDataException(ApplicationMessage.DIVISOR_FACTOR_ERROR_MESSAGE);
        }
    }

    public int generateAndCompareValues(Generator generatorB) {
        int comparisonCounter;
        try {
            String iterationCount = PropertyValues.getPropertyValue("iteration.count");
            if (!iterationCount.trim().equals("")) {
                comparisonCounter = Integer.parseInt(iterationCount);
            } else {
                throw new InvalidDataException(ApplicationMessage.ITERATION_COUNTER_NOT_FOUND_MESSAGE);
            }
            for (int iteration = 1; iteration <= comparisonCounter; iteration++) {
                this.generateNextIterationInputNumber();
                generatorB.generateNextIterationInputNumber();

                if (isGeneratorValuesMatching(this.getIterationBaseValue(), generatorB.getIterationBaseValue())) {
                    matchingNumberCount++;
                }
            }
        } catch (NumberFormatException ex) {
            throw new InvalidDataException(ApplicationMessage.ITERATION_COUNTER_ERROR_MESSAGE);
        }

        return matchingNumberCount;
    }

    public static boolean isGeneratorValuesMatching(long generatedNumberA, long generatedNumberB) {
        boolean isValuesMatching = false;
        String bitsCompare = PropertyValues.getPropertyValue("bit.comparison");
        try {
            if (!bitsCompare.trim().equals("")) {
                int compareBits = Integer.parseInt(bitsCompare);
                Integer lastBitsA = (int) (((1 << compareBits) - 1) & generatedNumberA);
                Integer lastBitsB = (int) (((1 << compareBits) - 1) & generatedNumberB);

                if (lastBitsA.byteValue() == lastBitsB.byteValue()) {
                    isValuesMatching = true;
                }
            } else {
                throw new InvalidDataException(ApplicationMessage.BIT_NUMBER_NOT_FOUND_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            throw new InvalidDataException(ApplicationMessage.BIT_NUMBER_ERROR_MESSAGE);
        }
        return isValuesMatching;
    }

    public int findGeneratorsMatchingNumberCount(Generator generatorB) {
        return this.generateAndCompareValues(generatorB);
    }
}
