import turtle
import math
from typing import *

def slope(p1: Tuple[float, float], p2: Tuple[float, float]) -> float:
    if math.isclose(p1[0]-p2[0], 0.0):
        return math.inf
    if math.isclose(p1[1]-p2[1], 0.0):
        return 0.0
    return (p1[1] - p2[1]) / (p1[0] - p2[0])

turtle.screensize(1000, 5000)

def render(n: int):
    angle = 60
    jmp = 30
    dsize = 20

    turtle.hideturtle()
    turtle.penup()

    turtle.left(90)
    turtle.forward(800)
    turtle.right(90)

    init_pos = turtle.position()
    turtle.speed(math.inf)
    turtle.dot(dsize, "black")
    
    turtle.right(120)
    slopes = []

    # draw each row
    for i in range(n):
        turtle.forward(jmp)
        turtle.left(120)

        for j in range(i+1):
            turtle.forward(jmp)
            cur_pos = turtle.position()
            color = "black"

            # compute line between initial point and current
            m = slope(init_pos,  cur_pos)

            # check if this is equal to any others --> they intersect
            for s in slopes:
                if math.isclose(s, m):
                    color = "pink"
                    break

            slopes.append(m)
            turtle.dot(dsize, color)

        turtle.backward((i+1)*jmp)
        turtle.dot(dsize, color)
        turtle.right(120)



    #turtle.dot(dsize, "green")
    turtle.done()

render(50)