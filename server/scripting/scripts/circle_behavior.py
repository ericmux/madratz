__author__ = 'ericmuxagata'

t = 0

def execute(sensor, actor):
    global t
    if t == 50:
        actor.shoot()
        t = 0
    actor.set_velocity(1.0)
    actor.rotate(0.2)
    t += 1
