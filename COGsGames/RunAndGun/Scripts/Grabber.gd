extends Node2D

onready var links = $Links
var direction := Vector2(0, 0)
var tip := Vector2(0, 0)

export var SPEED = 1000
export var MAX_DIST = 300

var flying = false
var hooked = false

func shoot(dir: Vector2) -> void:
	direction = dir.normalized()
	flying = true;
	tip = self.global_position
	
func release() -> void:
	flying = false
	hooked = false

# TODO: Make an AttachmentPoint emit some signal that sets field flying and hooked
# instead of just primitively checking whether or not the tip has collided with
# something.

func _process(delta: float) -> void:
	self.visible = flying or hooked
	if not self.visible:
		return
	var tip_loc = to_local(tip)
	
	$Links.rotation = self.position.angle_to_point(tip_loc) #- deg2rad(180)
	$Tip.rotation = self.position.angle_to_point(tip_loc) - deg2rad(90)
	links.position = tip_loc
	links.region_rect.size.x = tip_loc.length() * 1.414
	
func _physics_process(delta: float) -> void:
	$Tip.global_position = tip
	if flying:
		if to_local(tip).length() > MAX_DIST:
			release()
		elif $Tip.move_and_collide(direction * SPEED * delta):
			hooked = true
			flying = false
	tip = $Tip.global_position
