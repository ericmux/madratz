import math

time_step = 1.0/60.0

t = 0.0

def execute(sensor, actor):
    global t
    actor.speedUp(math.sin(t))
    actor.rotate(0.01)
    t += time_step
