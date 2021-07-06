package forest;

import java.awt.Color;
import java.awt.Font;

public class Constant extends Object {
	/**
	 * 初期のウィンドウサイズの幅を示す定数
	 */
	public static final Integer WINDOW_WIDTH = 800;

	/**
	 * 初期のウィンドウサイズの高さを示す定数
	 */
	public static final Integer WINDOW_HEIGHT = 700;

	/**
	 * ウィンドウサイズの幅が縮小できる最小値を示す定数
	 */
	public static final Integer WINDOW_WIDTH_MINIMUM = 200;

	/**
	 * ウィンドウサイズの高さが縮小できる最小値を示す定数
	 */
	public static final Integer WINDOW_HEIGHT_MINIMUM = 200;

	/**
	 * ノードなどが縦に移動する量を示す定数
	 */
	public static final Integer VERTICAL_MOVE = 2;

	/**
	 * ノードなどが横に移動する量を示す定数
	 */
	public static final Integer HORIZONTAL_MOVE = 25;

	/**
	 * アニメーションの時間間隔を示す定数
	 */
	public static final Integer SLEEP_TIME = 50;

	/**
	 * フォントの詳細を示す定数
	 */
	public static final Font DETAIL_FONT = new Font("Serif",Font.PLAIN,12);

	/**
	 * ノード文字色を示す定数
	 */
	public static final Color NODE_COLOR = Color.black;

	/**
	 * ブランチの色を示す定数
	 */
	public static final Color BRANCH_COLOR = Color.black;

	/**
	 * 背景の色を示す定数
	 */
	public static final Color BACKGROUND_COLOR = Color.white;

}
