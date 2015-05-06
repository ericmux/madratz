package com.madratz.behavior;

import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;
import com.madratz.jython.ScriptHandler;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.util.PythonInterpreter;
import com.madratz.wrappers.ActorWrapper;

public class ScriptedBehavior implements Behavior {

    private final PythonInterpreter mInterpreter = new PythonInterpreter();
    private PyFunction mFunction;
    private final String mScript;

    public ScriptedBehavior(String scriptPath) {
        mScript = ScriptHandler.readScript(scriptPath);
        mInterpreter.exec(mScript);
        mFunction = mInterpreter.get("execute",PyFunction.class);
    }

    @Override
    public Decision execute(Actor actor) {
        ActorWrapper actorWrapper = new ActorWrapper(actor,new Decision());
        mInterpreter.set("actor", Py.java2py(actorWrapper));
        
        mFunction.__call__();

        return actorWrapper.getDecision();
    }
}
