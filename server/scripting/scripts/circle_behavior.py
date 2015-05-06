__author__ = 'ericmuxagata'


actor = None
t = 0

def execute():
    global t
    if t == 10:
        actor.shoot()
        t = 0
    actor.speedUp(1.0)
    actor.rotate(0.1)
    t += 1
