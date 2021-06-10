package fr.gis.openweather.rendering.legends;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.beans.Transient;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JPanel;

import javafx.scene.layout.Pane;

public class AbstractLegendPanel extends Pane {
	Map<Float, Color> legendMap;

	private static final int LEGEND_HEIGHT = 200;
	private static final int LEGEND_WIDTH = 60;

	private double elementHeight;

	public AbstractLegendPanel() {

	}

	public void setMap(Map<Float, Color> legendMap) {
		this.legendMap = legendMap;
		elementHeight = LEGEND_HEIGHT / legendMap.size();
	}

	public Dimension getPreferredSize() {
		return new Dimension(LEGEND_WIDTH, LEGEND_HEIGHT);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		int textHeight    = g2d.getFontMetrics().getAscent() * 3 / 4;
		double textOffset = (elementHeight - textHeight) / 2;
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, (int) getWidth(), (int) getHeight());

		int width = LEGEND_WIDTH / 4;
		int index = 0;
		for (Entry<Float, Color> entry : legendMap.entrySet()) {
			g2d.setColor(entry.getValue());
			g2d.fillRect(LEGEND_WIDTH - width, (int) (index * elementHeight), width, (int) elementHeight);
			g2d.setColor(Color.BLACK);
			g2d.drawString(
					String.format("%.1f", entry.getKey()),
					10, (int) ((index + 1) * elementHeight - textOffset));
			index++;
		}

		g2d.dispose();
	}

}
