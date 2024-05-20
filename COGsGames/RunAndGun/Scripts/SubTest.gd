extends Node2D

var tip := Vector2(300, 170)

func _physics_process(delta):
	self.global_position = tip
