package com.jcourse.vlsnk.Exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionGeneratorImpl implements ExceptionGenerator {

    public void generateNullPointerException() {
        String s = null;
        s.length();
    }

    public void generateClassCastException() {
        List<String> list = new ArrayList<String>();
        list.add((String) new Object());
    }

    public void generateNumberFormatException() {
        new Integer("q");
    }

    public void generateStackOverflowError() {
        generateStackOverflowError();
    }

    public void generateOutOfMemoryError() {
        new ArrayList<String>(99999999);
    }

    public void generateMyException(String message) throws MyException {
        throw new MyException(message);
    }

}
