import math

time_step = 1.0/60.0

actor = None
t = 0.0

def execute():
    global actor, t, time_step
    actor.speedUp(math.sin(t))
    t = t + time_step