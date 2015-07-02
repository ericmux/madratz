__author__ = 'ericmuxagata'

def execute(sensor, actor):
    weapon = actor.weapon()
    if weapon.is_ready():
        weapon.fire()

    actor.set_velocity(1.0)
    actor.rotate(0.2)
