package com.madratz.junit.rules;

import com.madratz.junit.annotation.Repeat;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Some tests depend on not entirely reliable data like measuring
 * execution time of some functions and could fail arbitrarily. With
 * this rule users can define how many times to run a certain test
 * case and how many times are they allowed to fail in order to minimize
 * the risks of tests randomly failing.
 */
public class RepeatRule implements TestRule {
    @Override
    public Statement apply(Statement statement, Description description)
    {
        Repeat repeat = description.getAnnotation(Repeat.class);
        if (repeat == null) return statement;
        return new RepeatedStatement(statement, repeat.times(), repeat.maxFailures());
    }

    private static class RepeatedStatement extends Statement {

        private final Statement mStatement;
        private final int mTimes;
        private final int mMaxFailures;

        private RepeatedStatement(Statement statement, int times, int maxFailures) {
            assert times > 0;
            assert maxFailures >= 0;
            mTimes = times;
            mStatement = statement;
            mMaxFailures = maxFailures;
        }

        @Override
        public void evaluate() throws Throwable {
            int failCount = 0;
            Throwable last = null;
            for (int i = 0; i < mTimes; i++) {
                try {
                    mStatement.evaluate();
                } catch(Throwable e) {
                    last = e;
                    failCount++;
                }
            }
            if (failCount > mMaxFailures) {
                throw new AssertionError("Test failed more than tolerated: " + failCount + " out of " + mTimes, last);
            }
        }
    }
}
