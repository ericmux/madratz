package com.madratz.behavior;

import com.madratz.behavior.api.ActionsInterface;
import com.madratz.behavior.api.JythonWrapper;
import com.madratz.behavior.api.SensoringInterface;
import com.madratz.decision.Decision;
import com.madratz.gamelogic.Actor;
import com.madratz.gamelogic.Player;
import com.madratz.security.MadratzSecurityManager;
import com.madratz.security.Privileged;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class ScriptedBehavior implements Behavior {
    private final String mScript;

    private PyFunction mFunction;

    public ScriptedBehavior(String script) {
        mScript = script;
    }

    @Override
    public Decision execute(Actor actor) throws Exception {
        if (!(actor instanceof Player)) throw new IllegalArgumentException("ScriptedBehavior is for players only");
        initPython();

        Player player = (Player) actor;
        Decision decision = new Decision();

        ActionsInterface actions = new ActionsInterface(player, decision);
        PyObject sensoring = JythonWrapper.wrap(new SensoringInterface(player));
        mFunction.__call__(sensoring, JythonWrapper.wrap(actions));

        return decision;
    }

    private void initPython() throws Exception {
        if (mFunction == null) {
            PythonInterpreter interpreter = Privileged.callPrivileged(PythonInterpreter::new);
            interpreter.exec(mScript);
            mFunction = interpreter.get("execute", PyFunction.class);
        }
    }

    public static final String INITIALIZATION_SCRIPT = "" +
                    "import math\n" +
                    "import random\n" +
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
