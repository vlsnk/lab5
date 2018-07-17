package com.jcourse.vlsnk.Exception;

public class Exception {

    public static void getException(){

        ExceptionGenerator exceptionGenerator = new ExceptionGeneratorImpl();
        try {
            exceptionGenerator.generateNullPointerException();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        try {
            exceptionGenerator.generateClassCastException();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        try {
            exceptionGenerator.generateNumberFormatException();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        try {
            exceptionGenerator.generateMyException("new MyException");
        } catch (MyException e) {
            e.printStackTrace();
        }
        try {
            exceptionGenerator.generateStackOverflowError();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        try {
            exceptionGenerator.generateOutOfMemoryError();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }
}
