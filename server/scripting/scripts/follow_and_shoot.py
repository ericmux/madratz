import math
import random

target_id = None
t = 0

def execute(sensor, actor):
    global target_id, t
    ops = sensor.opponents()
    target = None
    if target_id is not None:
        targets = filter(lambda p: p.id == target_id, ops)
        target = targets[0] if len(targets) > 0 else None
        if target != None and target.hp == 0:
            target = None
    if target is None:
        target = ops[random.randint(0, len(ops) - 1)]
        target_id = target.id

    me = sensor.self()
    op_angle = math.atan2(target.position.y - me.position.y, target.position.x - me.position.x)
    curr_angle = math.atan2(me.direction.y, me.direction.x)

    actor.rotate(5 * (op_angle - curr_angle))
    actor.set_velocity(1.0)

    t += 1
    if t % 10 == 0:
        actor.shoot()
        t = 0
