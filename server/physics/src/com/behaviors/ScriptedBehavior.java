package com.behaviors;

import com.gamelogic.Actor;
import com.simulation.DecisionResult;
import org.python.core.Py;
import org.python.util.PythonInterpreter;

public class ScriptedBehavior implements Behavior {

    private final PythonInterpreter mInterpreter = new PythonInterpreter();
    private final String mScript;

    public ScriptedBehavior(String script) {
        mScript = script;
    }

    @Override
    public DecisionResult execute(Actor actor) {
        mInterpreter.set("m_world", Py.java2py(actor.getWorld()));
        mInterpreter.set("m_actor", Py.java2py(actor));

        mInterpreter.exec(mScript);
        return new DecisionResult(0);
    }
}
