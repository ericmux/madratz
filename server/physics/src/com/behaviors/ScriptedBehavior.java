package com.behaviors;

import com.simulation.DecisionResult;
import org.python.core.Py;
import org.python.util.PythonInterpreter;

public class ScriptedBehavior extends Behavior {

    private final String mScript;
    private final PythonInterpreter mInterpreter;


    public ScriptedBehavior(String script) {
        mScript = script;

        mInterpreter = new PythonInterpreter();
        mInterpreter.set("m_world", Py.java2py(mMadratzWorld));
        mInterpreter.set("m_actor", Py.java2py(mActor));
    }


    @Override
    public DecisionResult call() throws Exception {

        mInterpreter.exec(mScript);

        return super.call();
    }
}
