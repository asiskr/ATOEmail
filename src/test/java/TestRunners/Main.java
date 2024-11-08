package TestRunners;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Main {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestRunner.class);
        System.out.println("Test executed with result: " + result.wasSuccessful());
    }
}


/*
 package TestRunners;
 

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Main {
    public void runMethod() {
        Result result = JUnitCore.runClasses(TestRunner.class);
        System.out.println("Test executed with result: " + result.wasSuccessful());
    }
}
*/