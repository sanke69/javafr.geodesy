package eu.hansolo.fx.world;

import javafx.scene.CacheHint;
import javafx.scene.layout.Region;

public class WorldRegion {}/*extends Region {

    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (ASPECT_RATIO * width > height) {
            width = 1 / (ASPECT_RATIO / height);
        } else if (1 / (ASPECT_RATIO / height) > width) {
            height = ASPECT_RATIO * width;
        }

        if (width > 0 && height > 0) {
            if (isZoomEnabled()) resetZoom();

            pane.setCache(true);
            pane.setCacheHint(CacheHint.SCALE);

            pane.setScaleX(width / PREFERRED_WIDTH);
            pane.setScaleY(height / PREFERRED_HEIGHT);

            group.resize(width, height);
            group.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            pane.setCache(false);
        }
    }
}*/
