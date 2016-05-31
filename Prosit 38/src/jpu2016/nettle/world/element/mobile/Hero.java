package jpu2016.nettle.world.element.mobile;

import java.awt.Point;

import jpu2016.nettle.world.element.Sprite;

public class Hero extends Mobile {
	private final Point lastPosition;

	public Hero() {
		super(new Sprite("☺!", "heroes.png"));
		this.lastPosition = new Point();
		this.lastPosition.setLocation(getPosition().x, getPosition().y);
	}

	public void moveBack() {
		setX(this.lastPosition.x);
		setY(this.lastPosition.y);
	}

	@Override
	public void moveDown() {
		saveLastPosition();
		super.moveDown();
	}

	@Override
	public void moveLeft() {
		saveLastPosition();
		super.moveLeft();
	}

	@Override
	public void moveRight() {
		saveLastPosition();
		super.moveRight();
	}

	@Override
	public void moveUp() {
		saveLastPosition();
		super.moveUp();
	}

	private void saveLastPosition() {
		if ((this.lastPosition.getX() != getPosition().getX()) || (this.lastPosition.getY() != getPosition().getY())) {
			this.lastPosition.setLocation(getPosition().x, getPosition().y);
		}
	}

}
