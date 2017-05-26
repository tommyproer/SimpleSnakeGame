import java.util.ArrayList;
import java.awt.Color;

public class DataOfSquare {

	public enum GameColor {
		SNAKE, FOOD, BACKGROUND
	}

	//ArrayList that'll contain the colors
	ArrayList<Color> C =new ArrayList<Color>();
	SquarePanel square;
	public DataOfSquare(final GameColor gameColor){
		square = new SquarePanel(convertToColor(gameColor));
	}
	public void lightMeUp(final GameColor gameColor){
		square.ChangeColor(convertToColor(gameColor));
	}

	private Color convertToColor(GameColor gameColor) {
		switch (gameColor) {
			case SNAKE:
				return Color.DARK_GRAY;
			case FOOD:
				return Color.BLUE;
			case BACKGROUND:
			default:
				return Color.WHITE;
		}
	}
}
