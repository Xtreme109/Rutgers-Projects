extends Camera2D

func _input(event: InputEvent) -> void:
	if event is InputEventMouseMotion:
		var _target = event.position - get_viewport().size * 0.5
		self.position = _target.normalized() * _target.length() * 0.5
