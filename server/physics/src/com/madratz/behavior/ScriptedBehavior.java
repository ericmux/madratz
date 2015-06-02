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
    public Decision execute(Actor actor) throws Exception {
        initPython();

        ActorWrapper actorWrapper = new ActorWrapper(actor, new Decision());
        PyObject wrapped = Privileged.callPrivileged(() -> PyJavaType.wrapJavaObject(actorWrapper));
        mInterpreter.set("actor", wrapped);

        mFunction.__call__();

        return actorWrapper.getDecision();
    }

    private void initPython() throws Exception {
        if (mFunction == null) {
            mInterpreter = Privileged.callPrivileged(PythonInterpreter::new);
            mInterpreter.exec(mScript);
            mFunction = mInterpreter.get("execute",PyFunction.class);
        }
    }

    public static final String INITIALIZATION_SCRIPT = "" +
                    "import math\n" +
                    "def function():\n" +
                    "  return 0\n" +
                    "x = function()\n\n" +
                    "from java.lang import System\n" +
                    "System.getProperties()\n";

    static {
        // Just so we don't need to set -Djava.security.manager on runtime
        MadratzSecurityManager.setSystemSecurityManagerIfNeeded();

        try {
            // Create an interpreter and execute a dummy script to initialize all Jython structures and classes
            Privileged.doPrivileged(() -> {
                PythonInterpreter interp = new PythonInterpreter();
                interp.exec(INITIALIZATION_SCRIPT);
            });
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
}
