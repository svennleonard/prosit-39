package jpu2016.nettle.play;

import java.io.IOException;

import jpu2016.nettle.dao.DAONettleWorld;
import jpu2016.nettle.view.INettleFrame;
import jpu2016.nettle.view.NettleView;
import jpu2016.nettle.world.INettleWorld;
import jpu2016.nettle.world.NettleWorld;
import jpu2016.nettle.world.element.mobile.Hero;
import jpu2016.nettle.world.element.motionless.IDoActionOnHeroes;

public class NettlePlay implements IOrderPerformed {
	private INettleFrame nettleFrame;
	private INettleWorld nettleMeeting;
	private final INettleWorld nettleWorld;
	private int playMode;

	public NettlePlay(final INettleWorld nettleWorld) {
		this.nettleWorld = nettleWorld;
		this.nettleWorld.addMobile(new Hero(), 15, 15);
	}

	private void escapeMetting() throws IOException {
		exitMetting();
		getActuelNettleWorld().getHero().moveBack();
	}

	private void exitMetting() throws IOException {
		setPlayMode(NettleView.VIEW_MODE_MAP);
	}

	private INettleWorld getActuelNettleWorld() {
		if (getPlayMode() == NettleView.VIEW_MODE_MEETING) {
			return getNettleMeeting();
		}
		return getNettleWorld();
	}

	private INettleFrame getNettleFrame() {
		return this.nettleFrame;
	}

	private INettleWorld getNettleMeeting() {
		return this.nettleMeeting;
	}

	private INettleWorld getNettleWorld() {
		return this.nettleWorld;
	}

	private int getPlayMode() {
		return this.playMode;
	}

	private void getWorldAnswer() throws IOException {
		final IDoActionOnHeroes element = getActuelNettleWorld().getElements(getActuelNettleWorld().getHero().getX(),
				getActuelNettleWorld().getHero().getY());

		switch (element.getActionOnHeroes()) {
		case ENTER_CAMP:
			NettleView.displayMessage("You enter a camp.");
			resolveEnterCamp();
			break;
		case ENTER_TOWN:
			NettleView.displayMessage("You enter a town.");
			resolveEnterTown();
			break;
		case ENTER_MONASTERY:
			NettleView.displayMessage("You enter a monastery.");
			resolveEnterMonastery();
			break;
		case EXIT:
			NettleView.displayMessage("You leave this place.");
			exitMetting();
			break;
		case ESCAPE:
			NettleView.displayMessage("You escape this place.");
			escapeMetting();
			break;
		case NOP:
		default:
			break;
		}
	}

	@Override
	public void orderPerform(final UserOrder userOrder) throws IOException {
		switch (userOrder) {
		case UP:
			getActuelNettleWorld().getHero().moveUp();
			break;
		case RIGHT:
			getActuelNettleWorld().getHero().moveRight();
			break;
		case DOWN:
			getActuelNettleWorld().getHero().moveDown();
			break;
		case LEFT:
			getActuelNettleWorld().getHero().moveLeft();
			break;
		case NOP:
		default:
			break;
		}
		getWorldAnswer();
	}

	private void resolveEnterCamp() throws IOException {
		setNettleMeeting(new NettleWorld(DAONettleWorld.readmap("camp2")));
		resolveWorldAnswer();
	}

	private void resolveEnterMonastery() throws IOException {
		setNettleMeeting(new NettleWorld(DAONettleWorld.readmap("monastery2")));
		resolveWorldAnswer();
	}

	private void resolveEnterTown() throws IOException {
		setNettleMeeting(new NettleWorld(DAONettleWorld.readmap("town2")));
		resolveWorldAnswer();
	}

	private void resolveWorldAnswer() throws IOException {
		getNettleMeeting().addMobile(new Hero(), 0, 0);
		getNettleFrame().setMeeting(getNettleMeeting());
		setPlayMode(NettleView.VIEW_MODE_MEETING);
	}

	public void setNettleFrame(final INettleFrame nettleFrame) {
		this.nettleFrame = nettleFrame;
	}

	private void setNettleMeeting(final INettleWorld nettleMeeting) {
		this.nettleMeeting = nettleMeeting;
	}

	private void setPlayMode(final int playMode) {
		this.playMode = playMode;
		getNettleFrame().setViewMode(playMode);
	}
}
