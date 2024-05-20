extends KinematicBody2D

export var gravity = 1500
export var moveAcceleration = 2500
export var moveDeceleration = 4000
export var maxMoveSpeed = 320
export var jumpVelocity = 300
export var maxJumpHeight = 50

export var grappleAcceleration = 20000
export var maxGrappleSpeed = 450

const GRAPPLE_BOUND = 150
const GRAPPLE_BOUND_SQ = GRAPPLE_BOUND * GRAPPLE_BOUND

var velocity : Vector2
var onGround : bool
var jumping : bool
var sideCollision: int = 0
var keyPressed : int = 0

func _input(event): 
	if event is InputEventMouseButton:
		if event.pressed:
			$Grabber.shoot(event.position - get_viewport().size * 0.5)
		else:
			$Grabber.release()

func _ready():
	velocity = Vector2(0, 0)
	onGround = false
	jumping = false

func _physics_process(delta):
	var jump_pressed: bool = Input.is_action_pressed("jump")
	var up_pressed: bool = Input.is_action_pressed("move_up")
	var down_pressed: bool = Input.is_action_pressed("move_down")
	var left_pressed: bool = Input.is_action_pressed("move_left")
	var right_pressed: bool = Input.is_action_pressed("move_right")

	keyPressed = int(right_pressed) - int(left_pressed)

	#FALLING
	if (!onGround):
		velocity.y += gravity * delta
		
	if ($Grabber.hooked):
		# GRAPPLE HOOK MOVEMENT
		var tip_local = to_local($Grabber.tip)
		var pull_direction = tip_local.normalized()
		
		var hookAcceleration = grappleAcceleration * Vector2(0.7 * pull_direction.x, pull_direction.y)
		var keyAcceleration = Vector2(int(right_pressed) - int(left_pressed), int(down_pressed) - int(up_pressed)).normalized() * moveAcceleration
		
		var dot_product = velocity.normalized().dot(tip_local.normalized())
		if(dot_product < 0 or velocity.length() < maxGrappleSpeed):
			var nearness_factor = 1;
			if tip_local.length_squared() < GRAPPLE_BOUND_SQ:
				nearness_factor *= (tip_local.length_squared() + 0.8) / (GRAPPLE_BOUND_SQ + 0.8)
#			print(nearness_factor)
			velocity += (hookAcceleration * nearness_factor + keyAcceleration * dot_product) * delta
	else:
		#LEFT/RIGHT MOVEMENT
		if (keyPressed != 0):
			if (sign(velocity.x) != keyPressed): velocity.x += moveDeceleration * keyPressed * delta
			else: velocity.x += moveAcceleration * keyPressed * delta
			if (abs(velocity.x) > maxMoveSpeed): velocity.x = maxMoveSpeed * sign(velocity.x)
			if (keyPressed != sideCollision):
				sideCollision = 0
		else:
			var decelActual = moveDeceleration if onGround else moveDeceleration * 0.1
			if (decelActual * delta <= abs(velocity.x)):
				velocity.x -= decelActual * sign(velocity.x) * delta
			else: velocity.x = 0

	#JUMPING
	if jump_pressed && onGround:
		velocity.y -= jumpVelocity 
	
	if (sideCollision != 0):
		velocity.x = 0

	check_collision(move_and_collide(velocity * delta))

func check_collision(collision):
	sideCollision = 0
	onGround = false
	if (collision != null):
		if (collision.normal.y < -0.5):
			onGround = true
			velocity.y = 0
	
	var space_state = get_world_2d().get_direct_space_state()
	var leftResult = space_state.intersect_ray(global_position, global_position + Vector2(-7, 11), [self]) || space_state.intersect_ray(global_position, global_position + Vector2(-7, -11), [self])
	var rightResult = space_state.intersect_ray(global_position, global_position + Vector2(7, 11), [self]) || space_state.intersect_ray(global_position, global_position + Vector2(7, -11), [self])
	if (leftResult):
		sideCollision = -1
	elif (rightResult):
		sideCollision = 1
	
	var upResult = space_state.intersect_ray(global_position, global_position + Vector2(-6, -12), [self]) || space_state.intersect_ray(global_position, global_position + Vector2(6, -12), [self])
	if (upResult):
		velocity.y = 0
	
	if ($GroundCollision.get_collider() != null): 
		onGround = true
		velocity.y = 0
