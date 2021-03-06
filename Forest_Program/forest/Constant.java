package forest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 * プログラム全体で利用する設定をまとめたクラス
 */
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
	 * ノードのラベルの枠と文字のマージンを表す定数
	 */
	public static final Point MARGIN = new Point(2, 1);

	/**
	 * アニメーションの時間間隔を示す定数
	 */
	public static final Integer SLEEP_TIME = 8;

	/**
	 * フォントの詳細を示す定数
	 */
	public static final Font FONT = new Font("Serif", Font.PLAIN, 12);

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

	/**
	 * Nodeの名前の最大数
	 */
	public static final Integer MAX_NODE_NAME_COUNT = 100;

	/**
	 * Nodeの最大数
	 */
	public static final Integer MAX_NODE_COUNT = 10000;

}
