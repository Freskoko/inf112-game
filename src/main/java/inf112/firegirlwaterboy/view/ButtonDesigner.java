package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * ButtonFactory creates reusable buttons with consistent styling.
 */
public class ButtonDesigner {

    /**
     * Creates a TextButton with custom text and fill color.
     * Button has a gray tint when pressed.
     *
     * @param text      The text shown on the button
     * @param fillColor The inner fill color of the button
     * @return A styled TextButton
     */
    public static Button createButton(String text, Color fillColor) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLACK;

        int width = 150;
        int height = 50;
        int borderWidth = 3;
        int radius = 10;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(radius, 0, width - 2 * radius, height);
        pixmap.fillRectangle(0, radius, width, height - 2 * radius);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius - 1, radius, radius);
        pixmap.fillCircle(radius, height - radius - 1, radius);
        pixmap.fillCircle(width - radius - 1, height - radius - 1, radius);

        // Fill the inner area with the specified fill color and round of the corners,
        // asked ChatGPT with help to do this
        pixmap.setColor(fillColor);
        int innerX = borderWidth;
        int innerY = borderWidth;
        int innerWidth = width - 2 * borderWidth;
        int innerHeight = height - 2 * borderWidth;

        pixmap.fillRectangle(innerX + radius, innerY, innerWidth - 2 * radius, innerHeight);
        pixmap.fillRectangle(innerX, innerY + radius, innerWidth, innerHeight - 2 * radius);
        pixmap.fillCircle(innerX + radius, innerY + radius, radius);
        pixmap.fillCircle(innerX + innerWidth - radius - 1, innerY + radius, radius);
        pixmap.fillCircle(innerX + radius, innerY + innerHeight - radius - 1, radius);
        pixmap.fillCircle(innerX + innerWidth - radius - 1, innerY + innerHeight - radius - 1, radius);

        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();

        style.up = backgroundDrawable;
        style.down = backgroundDrawable.tint(Color.GRAY);

        return new TextButton(text, style);
    }

}
