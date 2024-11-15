package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.*;

public class Interpreter<T> {

    private final String SCRIPT_ENGINE = "python";
    private final String SCRIPT_FILE;
    private String code;
    private ScriptEngine scriptEngine;

    public Interpreter(T input, String program) throws IOException, ScriptException {
        this.SCRIPT_FILE = program;
        ScriptContext context = new SimpleScriptContext();

        ScriptEngineManager sem = new ScriptEngineManager();
        scriptEngine = sem.getEngineByName(SCRIPT_ENGINE);

        installCode(loadSource(SCRIPT_FILE), context);

        executeFunction(input, context);
    }

    public void executeFunction(T input, ScriptContext context) throws ScriptException {

        try {

            scriptEngine.setContext(context);
            Invocable invocable = (Invocable) scriptEngine;
            invocable.invokeFunction("run_script", input);

            Object retValue = scriptEngine.get("ret_val");
            System.out.println(retValue);

            int a = (int)retValue + 100;
            System.out.println(a);


        } catch (NoSuchMethodException | ScriptException e) {
            e.printStackTrace();
        }
    }

    private String loadSource(String fileName) throws IOException {
        StringBuilder codeBuf = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(this.getClass().getResource(fileName).openStream()))) {
            while (rd.ready()) {
                codeBuf.append(rd.readLine()).append("\n");
            }
        }
        return codeBuf.toString();
    }

    private void installCode(String code, ScriptContext context) {
        this.code = code;
        try {
            scriptEngine.eval(code, context);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
