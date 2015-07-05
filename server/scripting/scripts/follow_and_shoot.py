import math
import random

target_id = None

def execute(sensor, actor):
    global target_id
    ops = sensor.opponents()
    target = None
    if target_id is not None:
        targets = filter(lambda p: p.id == target_id, ops)
        target = targets[0] if len(targets) > 0 else None
    if target is None:
        target = ops[random.randint(0, len(ops) - 1)]
        target_id = target.id

    me = sensor.self()
    op_angle = math.atan2(target.position.y - me.position.y, target.position.x - me.position.x)
    curr_angle = math.atan2(me.direction.y, me.direction.x)

    actor.rotate(5 * lower_angle(op_angle - curr_angle))
    actor.set_velocity(1.0)

    weapon = actor.weapon()
    if weapon.is_ready():
        weapon.fire()

def lower_angle(angle):
    while angle > math.pi:
        angle -= 2 * math.pi
    while angle < -math.pi:
        angle += 2 * math.pi
    return angle
