package com.kaleido;

import java.math.BigInteger;

public class IdGenerator {


    public static final String CODEX_upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CODEX_upperCaseCharsNoVowels = "BCDFGHJKLMNPQRSTVWXYZ"; //no vowels
    public static final String CODEX_digits = "0123456789";
    public static final String CODEX_digitsNoZero = "123456789";
    public static final String CODEX_base36 = CODEX_digits + CODEX_upperCaseChars;


    /**
     * Builds a code using multiple codices allowing any combination of alphabetical and numeric characters. The first
     * codex ({@code codices[0]}) is used for the first placeholder (i.e the "ones"), the next is used for the second
     * (the "tens") etc. If there are more columns required than codices then the last codex in the array is used for
     * all subsequent columns.
     * <p>
     * For example, the following code will use numbers for the "ones" column and upper case characters for all
     * other columns.
     * <pre>
     *         String code = convertFromNumber(numberToConvert, false, CODEX_digits, CODEX_upperCaseChars);
     *     </pre>
     * In this example 0 would be encoded to 0, 10 would be B0 and 400 would be BO0
     * </p>
     * <p>
     * Padding is possible so that codes that don't use all codices are padded with the first characters of any
     * remaining codexes. For example:
     * <pre>
     *         String code = convertFromNumber(
     * 0,
     * true,
     * CODEX_digits,
     * CODEX_upperCaseChars,
     * CODEX_upperCaseCharsNoVowels,
     * CODEX_digitsNoZero);
     *     </pre>
     * will return {@code 1BA0}
     * </p>
     *
     * @param i         the number to be converted. Must be greater than 0.
     * @param codices   the codices to use for encoding
     * @param padString if true the resulting code will be made as long as the list of codices by padding with the first
     *                  character in each appropriate codex.
     * @return an encoded {@code String}
     */
    public String convertFromNumber(int i, boolean padString, String... codices) {

        return this.convertFromBigInteger(BigInteger.valueOf(i),padString,codices);

    }

    public String convertFromBigInteger(BigInteger i, boolean padString, String... codices){
        if( i.compareTo(BigInteger.ZERO) < 0) throw new IllegalArgumentException("value to encode must not be < 0, got " + i);

        int currentCodexIndex = 0;

        StringBuffer sb = new StringBuffer();

        if (i.compareTo(BigInteger.ZERO) == 0) { //short cut for the value of 0
            sb.insert(0, codices[0].charAt(0));
        }

        while (i.compareTo(BigInteger.ZERO) > 0) {
            String codex = codices[currentCodexIndex];

            BigInteger z = BigInteger.valueOf(codex.length());

            /*
             * safe to get as an int because the codex length cannot be bigger than a max int
             * but will throw an ArithmeticException if it can't be converted without loosing information
             */
            int remainder = i.mod(z).intValueExact();

            i = i.divide(z);

            sb.insert(0, codex.charAt(remainder));

            currentCodexIndex = Math.min(codices.length - 1, currentCodexIndex + 1); //never get past the last codex
        }


        if (padString && sb.length() < codices.length) { //we got some padding to do
            for (int padIndex = Math.max(0, sb.length()); padIndex < codices.length; padIndex++) {
                sb.insert(0, codices[padIndex].charAt(0));
            }
        }


        return sb.toString();
    }

    /**
     * Generates a Kaleido chemical Batch ID from a number
     * @param i a number greater than or equal to 0
     * @return the matching batch id for the integer.
     */
    public String chemicalBatchIdFromNumber(int i){
        return this.chemicalBatchIdFromBigInteger(BigInteger.valueOf(i));
    }

    /**
     * Generates a Kaleido chemical Batch ID from a number
     * @param i a number greater than or equal to 0
     * @return the matching batch id for the integer.
     */
    public String chemicalBatchIdFromBigInteger(BigInteger i){
        return "CB-"+ this.convertFromBigInteger(
                i,
                true,
                CODEX_upperCaseCharsNoVowels,
                CODEX_upperCaseCharsNoVowels,
                CODEX_upperCaseCharsNoVowels,
                CODEX_digits,
                CODEX_digits,
                CODEX_digitsNoZero);
    }

    /**
     * Generates a Kaleido chemical Concept ID from a number
     * @param i a number greater than or equal to 0
     * @return the matching batch id for the integer.
     */
    public String chemicalConceptIdFromNumber(int i){
        return this.chemicalConceptIdFromBigInteger(BigInteger.valueOf(i));
    }

    public String chemicalConceptIdFromBigInteger(BigInteger i){
        return "CC-"+ this.convertFromBigInteger(
                i,
                true,
                CODEX_digits,
                CODEX_digits,
                CODEX_digits,
                CODEX_upperCaseCharsNoVowels,
                CODEX_upperCaseCharsNoVowels,
                CODEX_upperCaseCharsNoVowels
        );
    }

}
