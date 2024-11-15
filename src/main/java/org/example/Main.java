package org.example;

import java.io.IOException;
import javax.script.ScriptException;

public class Main {
    public static void main(String[] args) throws ScriptException, IOException {

        Interpreter i = new Interpreter("Value from parameter invoked function in java", "/script.py");

    }
}