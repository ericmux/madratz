package com.madratz.behavior;

import com.madratz.behavior.wrappers.ActorWrapper;
import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;
import com.madratz.security.MadratzSecurityManager;
import com.madratz.security.Privileged;
import org.python.core.PyFunction;
import org.python.core.PyJavaType;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class ScriptedBehavior implements Behavior {
    private final String mScript;

    private PythonInterpreter mInterpreter;
    private PyFunction mFunction;

    public ScriptedBehavior(String script) {
        mScript = script;
    }

    @Override
    public Decision execute(Actor actor) {
        try {
            initPython();

            ActorWrapper actorWrapper = new ActorWrapper(actor, new Decision());
            PyObject wrapped = Privileged.callPrivileged(() -> PyJavaType.wrapJavaObject(actorWrapper));
            mInterpreter.set("actor", wrapped);

            mFunction.__call__();

            return actorWrapper.getDecision();
        } catch (Throwable t) {
            // continue
            t.printStackTrace();
        }
        return new Decision();
    }

    private void initPython() throws Exception {
        if (mFunction == null) {
            mInterpreter = Privileged.callPrivileged(PythonInterpreter::new);
            mInterpreter.exec(mScript);
            mFunction = mInterpreter.get("execute",PyFunction.class);
        }
    }

    static {
        // Just so we don't need to set -Djava.security.manager on runtime
        MadratzSecurityManager.setSystemSecurityManagerIfNeeded();
    }
}
