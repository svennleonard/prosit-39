package jpu2016.nettle.world;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

import jpu2016.nettle.world.element.Element;
import jpu2016.nettle.world.element.mobile.Hero;
import jpu2016.nettle.world.element.mobile.Mobile;
import jpu2016.nettle.world.element.motionless.MotionlessElement;
import jpu2016.nettle.world.element.motionless.MotionlessElements;

public class NettleWorld extends Observable implements INettleWorld {
	public MotionlessElement elements[][];
	private int height;
	private Hero hero;
	public final ArrayList<Mobile> mobiles;
	private int width;

	private NettleWorld() {
		this.mobiles = new ArrayList<Mobile>();
	}

	public NettleWorld(final String fileName) throws IOException {
		this();
		loadFile(fileName);
	}

	private void addElement(final MotionlessElement element, final int x, final int y) {
		this.elements[x][y] = element;
		if (element != null) {
			element.setNettleWorld(this);
		}
		setChanged();
	}

	@Override
	public void addMobile(final Hero hero, final int x, final int y) {
		setHero(hero);
		this.addMobile((Mobile) hero, x, y);
	}

	@Override
	public void addMobile(final Mobile mobile, final int x, final int y) {
		this.mobiles.add(mobile);
		mobile.setNettleWorld(this, x, y);
		setChanged();
		this.notifyObservers();
	}

	@Override
	public Element[][] getElements() {
		return this.elements;
	}

	@Override
	public MotionlessElement getElements(final int x, final int y) {
		if ((x < 0) || (y < 0) || (x >= getWidth()) || (y >= getHeight())) {
			return null;
		}
		return this.elements[x][y];
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public Hero getHero() {
		return this.hero;
	}

	@Override
	public ArrayList<Mobile> getMobiles() {
		return this.mobiles;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	private void loadFile(final String fileName) throws IOException {
		final BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String line;
		int numLine = 0;
		line = buffer.readLine();
		this.width = Integer.parseInt(line);
		line = buffer.readLine();
		this.height = Integer.parseInt(line);
		this.elements = new MotionlessElement[getWidth()][getHeight()];
		while ((line = buffer.readLine()) != null) {
			for (int x = 0; x < line.toCharArray().length; x++) {
				addElement(MotionlessElements.getFromFileSymbol(line.toCharArray()[x]), x, numLine);
			}
			numLine++;
		}
		buffer.close();
		setChanged();
	}

	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}

	private void setHero(final Hero hero) {
		this.hero = hero;
		setChanged();
	}

	@Override
	public void setMobileHasChanged() {
		setChanged();
		this.notifyObservers();
	}
}
