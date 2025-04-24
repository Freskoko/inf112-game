package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
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
        TextButtonStyle style = new TextButtonStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLACK;
    
        int width = 200;
        int height = 50;
        int radius = 10;
    
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(fillColor);
    
        // This is to get rounded corners, asked ChatGPT on how to do that. 
        pixmap.fillRectangle(radius, 0, width - 2 * radius, height);
        pixmap.fillRectangle(0, radius, width, height - 2 * radius);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius - 1, radius, radius);
        pixmap.fillCircle(radius, height - radius - 1, radius);
        pixmap.fillCircle(width - radius - 1, height - radius - 1, radius);
    
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
    
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(texture));
        style.up = backgroundDrawable;
        style.down = backgroundDrawable.tint(Color.GRAY);
    
        return new TextButton(text, style);
    }
    

}
