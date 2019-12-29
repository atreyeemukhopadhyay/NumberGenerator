package com.atreyee.processor;

import com.atreyee.processor.exception.InvalidDataException;
import com.atreyee.processor.generator.Generator;
import com.atreyee.processor.util.PropertyValues;
import org.junit.*;

import java.io.IOException;

public class NumberGeneratorComparisonTest {

    private static final int GENERATOR_FACTOR_FIRST = 16807;
    private static final int GENERATOR_FACTOR_SECOND = 48271;

    @Before
    public void setUp() throws IOException {
        PropertyValues.loadProperties("test.properties");
    }

    @Test
    public void findGeneratorsMatchingCountTest() {
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, 65);
        Generator generatorB = new Generator(GENERATOR_FACTOR_SECOND, 8921);

        Generator generatorC = new Generator(GENERATOR_FACTOR_FIRST, 635);
        Generator generatorD = new Generator(GENERATOR_FACTOR_SECOND, 12);

        Assert.assertEquals(3868, generatorA.findGeneratorsMatchingNumberCount(generatorB));
        Assert.assertEquals(3926, generatorC.findGeneratorsMatchingNumberCount(generatorD));
    }

    @Test(expected = InvalidDataException.class)
    public void findGeneratorsInvalidCounterNumberTest() throws IOException {
        PropertyValues.loadProperties("test_error.properties");
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, 65);
        Generator generatorB = new Generator(GENERATOR_FACTOR_SECOND, 8921);

        generatorA.findGeneratorsMatchingNumberCount(generatorB);
    }

    @Test(expected = InvalidDataException.class)
    public void findGeneratorsBlankCounterNumberTest() throws IOException {
        PropertyValues.loadProperties("test_error_blank.properties");
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, 65);
        Generator generatorB = new Generator(GENERATOR_FACTOR_SECOND, 8921);

        generatorA.findGeneratorsMatchingNumberCount(generatorB);
    }

    @Test
    public void generateNextValueTest() {
        //Test value after first iteration
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, 1092455);
        generatorA.generateNextIterationInputNumber();
        Assert.assertEquals(1181022009, generatorA.getIterationBaseValue());

        //Test value after third iteration
        generatorA.generateNextIterationInputNumber();
        generatorA.generateNextIterationInputNumber();
        Assert.assertEquals(1744312007, generatorA.getIterationBaseValue());
    }

    @Test(expected = InvalidDataException.class)
    public void invalidGeneratorFactorExceptionTest() throws IOException {
        //Test value after first iteration
        PropertyValues.loadProperties("test_error.properties");
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, 1092455);
        generatorA.generateNextIterationInputNumber();
    }

    @Test(expected = InvalidDataException.class)
    public void blankGeneratorFactorExceptionTest() throws IOException {
        //Test value after first iteration
        PropertyValues.loadProperties("test_error_blank.properties");
        Generator generatorA = new Generator(GENERATOR_FACTOR_FIRST, 1092455);
        generatorA.generateNextIterationInputNumber();
    }

    @Test
    public void generatorMatchingValuesTest() {
        Assert.assertTrue(Generator.isGeneratorValuesMatching(245556042, 1431495498));
    }

    @Test
    public void generatorNonMatchingValueTest() {
        Assert.assertFalse(Generator.isGeneratorValuesMatching(1181022009, 1233683848));
    }

    @Test(expected = InvalidDataException.class)
    public void compareValueInvalidBitNumberTest() throws IOException {
        PropertyValues.loadProperties("test_error.properties");
        Generator.isGeneratorValuesMatching(245556042, 1431495498);
    }

    @Test(expected = InvalidDataException.class)
    public void compareValueBlankBitNumberTest() throws IOException {
        PropertyValues.loadProperties("test_error_blank.properties");
        Generator.isGeneratorValuesMatching(245556042, 1431495498);
    }
}
