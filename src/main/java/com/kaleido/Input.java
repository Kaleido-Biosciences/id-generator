package com.kaleido;

import java.util.Arrays;

public class Input {
    private String[] codices = new String[]{IdGenerator.CODEX_upperCaseCharsNoVowels};
    private int number = 0;
    private boolean padStrings = true;

    public String[] getCodices() {
        return codices;
    }

    public void setCodices(String[] codices) {
        if(codices != null)
            this.codices = codices;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
            this.number = number;
    }

    public Boolean getPadStrings() {
        return padStrings;
    }

    public void setPadStrings(boolean padStrings) {
        this.padStrings = padStrings;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Input{");
        sb.append("codices=").append(Arrays.toString(codices));
        sb.append(", number=").append(number);
        sb.append(", padStrings=").append(padStrings);
        sb.append('}');
        return sb.toString();
    }
}
