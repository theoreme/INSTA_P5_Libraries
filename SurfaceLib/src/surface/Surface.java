package surface;

import java.util.Arrays;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

/**
 * This is the basic surface class all the other surface extend from.
 * 
 * @author andreaskoeberle
 * 
 */
public class Surface {

	static protected float PI = (float) Math.PI;

	static protected float TWO_PI = 2 * PI;

	static protected float HALF_PI = PI / 2f;

	static protected float tan(final float i_value) {
		return (float) Math.tan(i_value);
	}

	static protected float exp(final float i_value) {
		return (float) Math.exp(i_value);
	}

	static protected float cosh(final float i_value) {
		return (exp(i_value) + exp(-i_value)) / 2.0f;
	}

	static protected float sinh(final float i_value) {
		return (exp(i_value) - exp(-i_value)) / 2.0f;
	}

	static protected float tanh(final float i_value) {
		return sinh(i_value) / cosh(i_value);
	}

	static protected int sign(float value) {
		if (value > 0)
			return 1;
		else if (value < 0)
			return -1;
		else
			return 0;
	}

	protected final int phiSteps;

	protected final int thetaSteps;

	protected float minTheta;

	protected  float maxTheta;

	protected  float minPhi;

	protected  float maxPhi;

	protected final float[] parameter;

	protected float xScale;

	protected float yScale;

	protected float zScale;

	protected float[][][] pointScaleDifference;

	final protected PGraphics g;

	protected final float[][][] coords;

	protected final float[] bounds;

	protected int[][] colors;

	protected PImage tex;

	protected int texMode;
	
	private SurfaceMesh mesh;
	
	
	/**
	 * @param i_g
	 *            a PGraphics Object where your surface will drawn on, mainly
	 *            this is g
	 * @param i_phiSteps
	 *            the horizontal resolution of your surface
	 * @param i_thetaSteps
	 *            the vertical resolution of your surface
	 * @param i_minTheta
	 *            the minimal value of theta
	 * @param i_maxTheta
	 *            the maximal value of theta
	 * @param i_minPhi
	 *            the minimal value of phi
	 * @param i_maxPhi
	 *            the maximal value of phi
	 * @param i_parameter
	 *            some surface, like SuperShapes or the SnailSurface, can be
	 *            initialized with different parameters
	 */
	public Surface(final PGraphics i_g, final int i_phiSteps, final int i_thetaSteps, final float i_minTheta, final float i_maxTheta, final float i_minPhi, final float i_maxPhi,
			final float[] i_parameter, int[][] i_colors) {
		g = i_g;
		phiSteps = i_phiSteps;
		thetaSteps = i_thetaSteps;

		minTheta = i_minTheta;
		maxTheta = i_maxTheta;
		minPhi = i_minPhi;
		maxPhi = i_maxPhi;

		parameter = i_parameter;

		coords = new float[phiSteps][thetaSteps][3];
		bounds = new float[6];
		Arrays.fill(bounds, 0);

		pointScaleDifference = new float[phiSteps][thetaSteps][3];
		for (int i = 0; i < phiSteps; i++) {
			for (int j = 0; j < thetaSteps; j++) {
				for (int k = 0; k < 3; k++) {
					pointScaleDifference[i][j][k] = 0;
				}
			}
		}

		initValues();
		setSurface();

		if (i_colors != null) {
			if (!(i_colors[0] != null)) {
				initHorizontalColors(i_colors[1]);
			} else if (!(i_colors[1] != null)) {
				initVerticalColors(i_colors[0]);
			} else {
				initColors(i_colors[0], i_colors[1]);
			}
		}
	}

	/**
	 * @param i_surface
	 */
	public Surface(final Surface i_surface) {
		this(i_surface.g, i_surface.phiSteps, i_surface.thetaSteps, i_surface.maxTheta, i_surface.maxTheta, i_surface.minPhi, i_surface.minPhi, i_surface.parameter, null);

		for (int phiStep = 0; phiStep < phiSteps; phiStep++) {
			for (int thetaStep = 0; thetaStep < thetaSteps; thetaStep++) {
				coords[phiStep][thetaStep][0] = i_surface.coords[phiStep][thetaStep][0];
				coords[phiStep][thetaStep][1] = i_surface.coords[phiStep][thetaStep][1];
				coords[phiStep][thetaStep][2] = i_surface.coords[phiStep][thetaStep][2];
			}
		}
		if (i_surface.colors != null) {
			colors = new int[phiSteps][thetaSteps];
			for (int i = 0; i < phiSteps; i++) {
				for (int j = 0; j < thetaSteps; j++) {
					colors[i][j] = i_surface.colors[i][j];
				}
			}
		}
	}

	/**
	 * Overwrite this method to set any values befor seting up the surface
	 * 
	 */
	protected void initValues() {

	}

	/**
	 * @return int, the horizontal resolution of your surface
	 */
	public int phiSteps() {
		return phiSteps;
	}

	/**
	 * @return int, the vertical resolution of your surface
	 */
	public int thetaSteps() {
		return thetaSteps;
	}

	/**
	 * Use this method to get an array holding the coordinates of the surface.
	 * The array is organized in the form [phi][theta][x,y,z]
	 * 
	 * @return float[][][], the complete coordinate array in the form
	 *         [phi][theta][x,y,z]
	 */
	public float[][][] coords() {
		return coords;
	}

	/**
	 * Use this method to get the xyz coordinate for a given point on the
	 * surface
	 * 
	 * @param i_indexPhi
	 *            int, a value between 0 and phiSteps
	 * @param i_indexTheta
	 *            int, a value between 0 and thetaSteps
	 * @return float[], an array with the x,y,z coordinates
	 */
	public float[] getCoord(int i_indexPhi, int i_indexTheta) {
		i_indexPhi = Math.max(0, Math.min(i_indexPhi, phiSteps - 1));
		i_indexTheta = Math.max(0, Math.min(i_indexTheta, thetaSteps - 1));

		float[] r = { coords[i_indexPhi][i_indexTheta][0], coords[i_indexPhi][i_indexTheta][1], coords[i_indexPhi][i_indexTheta][2] };
		return r;
	}

	/**
	 * Sets the scale of the surface. Every coordinate will multiplied with the
	 * given value. It is also possible to use three values for a different
	 * scaling in xyz direction.
	 * 
	 * @shortdesc Sets the scale of the surface.
	 * @param i_scale
	 *            float, the value that will be multiplied with the x,y and z
	 *            coordinates
	 * @related setXScale ( )
	 * @related setYScale ( )
	 * @related setZScale ( )
	 * @related setPointScaleDifference ( )
	 * 
	 * @example setScale
	 * @applet setScale
	 */
	public void setScale(final float i_scale) {
		xScale = i_scale;
		yScale = i_scale;
		zScale = i_scale;
	}

	/**
	 * @example setScale
	 * @param i_xScale
	 *            the value that will be multiplied with the x coordinates
	 * @param i_yScale
	 *            the value that will be multiplied with the y coordinates
	 * @param i_zScale
	 *            the value that will be multiplied with the z coordinates
	 */

	public void setScale(final float i_xScale, final float i_yScale, final float i_zScale) {

		xScale = i_xScale;
		yScale = i_yScale;
		zScale = i_zScale;
	}

	/**
	 * Returns the x scale of the surface.
	 * 
	 * @return float, The value that will be multiplied with the x coordinates.
	 * @related setScale ( )
	 * @related setXScale ( )
	 * @related setPointScaleDifference ( )
	 * @related yScale ( )
	 * @related zScale ( )
	 */
	public float xScale() {
		return xScale;
	}

	/**
	 * Sets the scale of the x values of the surfaces coordinates.
	 * 
	 * @param i_xScale
	 *            float, This value that will be multiplied with the x
	 *            coordinates.
	 * @related setScale ( )
	 * @related setYScale ( )
	 * @related setZScale ( )
	 * @related setPointScaleDifference ( )
	 * @related xScale ( )
	 */
	public void setXScale(final float i_xScale) {
		xScale = i_xScale;
	}

	/**
	 * Returns the y scale of the surface.
	 * 
	 * @return float, The value that will be multiplied with the y coordinates.
	 * @related setScale ( )
	 * @related setYScale ( )
	 * @related setPointScaleDifference ( )
	 * @related xScale ( )
	 * @related zScale ( )
	 */
	public float yScale() {
		return yScale;
	}

	/**
	 * Sets the scale of the y values of the surfaces coordinates.
	 * 
	 * @param i_yScale
	 *            float, This value that will be multiply with the y
	 *            coordinates.
	 * @related setScale ( )
	 * @related xScale ( )
	 * @related zScale ( )
	 * @related setXScale ( )
	 * @related setZScale ( )
	 * @related setPointScaleDifference ( )
	 */
	public void setYScale(final float i_yScale) {
		yScale = i_yScale;
	}

	/**
	 * Returns the z scale of the surface.
	 * 
	 * @return float, The value that will be multiplied with the z coordinates.
	 * @related setScale ( )
	 * @related setZScale ( )
	 * @related setPointScaleDifference ( )
	 * @related xScale ( )
	 * @related yScale ( )
	 */
	public float zScale() {
		return zScale;
	}

	/**
	 * Sets the scale of the z values of the surfaces coordinates.
	 * 
	 * @param i_zScale
	 *            This value will be multiply with the z coordinates.
	 * @related setScale ( )
	 * @related zScale ( )
	 * @related setXScale ( )
	 * @related setYScale ( )
	 * @related setPointScaleDifference ( )
	 */
	public void setZScale(final float i_zScale) {
		zScale = i_zScale;
	}

	/**
	 * Use this methode to change the scale of every point individually . The
	 * value i_pointScaleDifference be will multiply with the x-, y- and zScale
	 * for the given point (i_IndexPhi,i_IndexTheta)
	 * 
	 * @param i_IndexPhi
	 * @param i_IndexTheta
	 * @param i_pointScaleDifference
	 *            This value will be multiply with x-, y- and zScale.
	 * @related setScale ( )
	 * @related setXScale ( )
	 * @related setYScale ( )
	 * @related setZScale ( )
	 * 
	 * @example setPointScaleDifference
	 * @applet setPointScaleDifference
	 * 
	 */
	public void setPointScaleDifference(final int i_IndexPhi, final int i_IndexTheta, final float i_pointScaleDifference) {
		Arrays.fill(pointScaleDifference[i_IndexPhi][i_IndexTheta], i_pointScaleDifference);
	}

	/**
	 * Use this methode to change the scale of every point individually . The
	 * values i_pointScaleDifferenceX, i_pointScaleDifferenceY and
	 * i_pointScaleDifferenceZ be will multiply with the x-, y- and zScale for
	 * the given point (i_IndexPhi,i_IndexTheta)
	 * 
	 * @param i_IndexPhi
	 * @param i_IndexTheta
	 * @param i_pointScaleDifferenceX
	 *            This value will be multiply with xScale.
	 * @param i_pointScaleDifferenceY
	 *            This value will be multiply with yScale.
	 * @param i_pointScaleDifferenceZ
	 *            This value will be multiply with zScale.
	 * @example setPointScaleDifference
	 * @related setScale ( )
	 * @related setXScale ( )
	 * @related setYScale ( )
	 * @related setZScale ( )
	 */
	public void setPointScaleDifference(final int i_IndexPhi, final int i_IndexTheta, final float i_pointScaleDifferenceX, final float i_pointScaleDifferenceY, final float i_pointScaleDifferenceZ) {
		pointScaleDifference[i_IndexPhi][i_IndexTheta][0] = i_pointScaleDifferenceX;
		pointScaleDifference[i_IndexPhi][i_IndexTheta][1] = i_pointScaleDifferenceY;
		pointScaleDifference[i_IndexPhi][i_IndexTheta][2] = i_pointScaleDifferenceZ;
	}

	/**
	 * Returns the 3 values that will be multiply with scaleX/Y/Z for the point
	 * (_IndexPhi, i_IndexTheta) as an float array (x,y,z).
	 * 
	 * @param i_IndexPhi
	 * @param i_IndexTheta
	 * @return float,
	 * @related setPointScaleDifference ( )
	 */
	public float[] getPointScaleDifference(final int i_IndexPhi, final int i_IndexTheta) {
		return pointScaleDifference[i_IndexPhi][i_IndexTheta];
	}

	/**
	 * @return an array with the 6 bounding coordinates
	 *         (maxX,minX,maxY,minY,maxZ,minZ) of the surface
	 */
	public float[] getBounds() {
		return bounds;
	}

	/**
	 * Every surface must implement this method to define how the x value is
	 * calculated for the given phi and theta step.
	 * 
	 * @param i_phiStep
	 * @param i_thetaStep
	 * @return the calculated x value
	 */
	protected float calculateX(final int i_phiStep, final int i_thetaStep) {
		return 0;
	}

	/**
	 * Every surface must implement this method to define how the y value is
	 * calculated for the given phi and theta step.
	 * 
	 * @param i_phiStep
	 * @param i_thetaStep
	 * @return the calculated y value
	 */
	protected float calculateY(final int i_phiStep, final int i_thetaStep) {
		return 0;
	}

	/**
	 * Every surface must implement this method to define how the z value is
	 * calculated for the given phi and theta step.
	 * 
	 * @param i_phiStep
	 * @param i_thetaStep
	 * @return the calculated z value
	 */
	protected float calculateZ(final int i_phiStep, final int i_thetaStep) {
		return 0;
	}

	/**
	 * Calculates the coordinates of the surface
	 * 
	 */
	protected void setSurface() {
		float x = 0;
		float y = 0;
		float z = 0;

		for (int phiStep = 0; phiStep < phiSteps; phiStep++) {
			for (int thetaStep = 0; thetaStep < thetaSteps; thetaStep++) {

				x = calculateX(phiStep, thetaStep);
				y = calculateY(phiStep, thetaStep);
				z = calculateZ(phiStep, thetaStep);

				if (x > bounds[0])
					bounds[0] = x;
				if (x < bounds[1])
					bounds[1] = x;
				if (y > bounds[2])
					bounds[2] = y;
				if (y < bounds[3])
					bounds[3] = y;
				if (z > bounds[4])
					bounds[4] = z;
				if (z < bounds[5])
					bounds[5] = z;

				coords[phiStep][thetaStep][0] = x;
				coords[phiStep][thetaStep][1] = y;
				coords[phiStep][thetaStep][2] = z;

			}
		}
		if(mesh != null){
			mesh.updateFaces();
		}
	}

	/**
	 * This will create a color gradient for the surface. You can use as many
	 * colors as you like. If you wanna have an end-to-end gradient the start an
	 * end color must be the same. Note that this methode creates horizontal and
	 * vertical gradients and blend them. If you only need horizontal OR
	 * vertical gradients use initVerticalColors or initHorizontalColors
	 * instead. The previous gradient will be overwritten. You can also use an
	 * also use an 2 dimesional int array (int[phiSteps][thetaSteps]) which will
	 * copy to the surface colors array. Note this will throw an error if the
	 * size not match to the surface color array.
	 * 
	 * @param i_horizontalColors
	 *            An array which holds the colors for the horizontal gradient.
	 * @param i_verticalColors
	 *            An array which holds the colors for the vertical gradient.
	 * @related initVerticalColors ( )
	 * @related initHorizontalColors ( )
	 */
	public void initColors(int[] i_horizontalColors, int[] i_verticalColors) {

		int[] horizontalColors = calcColorArray(i_horizontalColors, thetaSteps);

		int[] verticalColors = calcColorArray(i_verticalColors, phiSteps);

		colors = new int[phiSteps][thetaSteps];
		for (int i = 0; i < phiSteps; i++) {
			for (int j = 0; j < thetaSteps; j++) {
				colors[i][j] = colorBetween(verticalColors[i], horizontalColors[j], 0.5f);
			}
		}
	}

	public void initColors(int[][] c) {
		try {
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < c[i].length; j++) {
					colors[i][j] = c[i][j];
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			System.out.println("It seems that the size of our array not match the size of the color array");
		}
	}

	/**
	 * Set the colors array to null.
	 * 
	 * @related initColors ( )
	 */
	public void clearColors() {
		colors = null;
	}

	/**
	 * This will create a vertical color gradient for the surface. If you wanna
	 * have an end-to-end gradient the start an end color must be the same. The
	 * previous gradient will be overwritten.
	 * 
	 * @param i_colors
	 *            An array which holds the colors for the vertical gradient.
	 * @related initColors ( )
	 * @related initHorizontalColors ( )
	 */
	public void initVerticalColors(int[] i_colors) {
		int[] verticalColors = calcColorArray(i_colors, phiSteps);
		colors = new int[phiSteps][thetaSteps];
		for (int i = 0; i < phiSteps; i++) {
			Arrays.fill(colors[i], verticalColors[i]);
		}
	}

	/**
	 * This will create a horizontal color gradient for the surface. If you
	 * wanna have an end-to-end gradient the start an end color must be the
	 * same. The previous gradient will be overwritten.
	 * 
	 * @param i_colors
	 *            An array which holds the colors for the vertical gradient.
	 * @related initColors ( )
	 * @related initVerticalColors ( )
	 */
	public void initHorizontalColors(int[] i_colors) {
		int[] horizontalColors = calcColorArray(i_colors, thetaSteps);
		colors = new int[phiSteps][thetaSteps];
		for (int i = 0; i < phiSteps; i++) {
			for (int j = 0; j < thetaSteps; j++) {
				colors[i][j] = horizontalColors[j];
			}
		}

	}

	/**
	 * If you has set colors for your surface this function gives you an array
	 * which hold the color for every point of the surface. It's usefull to
	 * manipulate the colors and add them after this back to the surface by call
	 * the initColors methode.
	 * 
	 * @return the array which holds the colors for every point
	 * @related initColors ( )
	 */

	public int[][] colors() {
		return colors;
	}

	private int[] calcColorArray(int[] i_colors, int stepSize) {
		int[] r_colors = new int[stepSize];
		for (int i = 0; i < stepSize; i++) {
			float step = (float) i / stepSize;
			r_colors[i] = colorsBetween(i_colors, step);
		}
		return r_colors;
	}

	private int colorBetween(int startColor, int endColor, float step) {
		int startAlpha = startColor >> 24 & 0xFF;
		int startRed = startColor >> 16 & 0xFF;
		int startGreen = startColor >> 8 & 0xFF;
		int startBlue = startColor & 0xFF;

		int endAlpha = endColor >> 24 & 0xFF;
		int endRed = endColor >> 16 & 0xFF;
		int endGreen = endColor >> 8 & 0xFF;
		int endBlue = endColor & 0xFF;

		int returnAlpha = (int) (startAlpha + (endAlpha - startAlpha) * step);
		int returnRed = (int) (startRed + (endRed - startRed) * step);
		int returnGreen = (int) (startGreen + (endGreen - startGreen) * step);
		int returnBlue = (int) (startBlue + (endBlue - startBlue) * step);

		int returnColor = (returnAlpha << 24) + (returnRed << 16) + (returnGreen << 8) + (returnBlue);
		return returnColor;
	}

	private int colorsBetween(int[] i_colors, float step) {
		if (step <= 0)
			return i_colors[0];
		if (step >= 1)
			return i_colors[i_colors.length - 1];
		int a = (int) Math.floor(i_colors.length * step);
		float f = 1f / (i_colors.length);
		float newStep = (step - (a * f)) / f;
		int nextA = a + 1;
		if (nextA >= i_colors.length)
			nextA = 0;
		return colorBetween(i_colors[a], i_colors[nextA], newStep);
	}

	/**
	 * Set a texture for your surface. The texture will be drawn as patter on
	 * every quad of the surface.
	 * 
	 * @param i_tex
	 *            a PImage Image used as texture.
	 * @related clearTexture ( )
	 */
	public void setTexture(PImage i_tex) {
		setTexture(i_tex, "TILE");
	}

	/**
	 * @param i_texMode
	 *            The way the texture is used by the surface. "TILE" let the
	 *            texture draw on every quad of the surface. "WHOLE" will
	 *            spreading the texture over the entire surface.
	 */
	public void setTexture(PImage i_tex, String i_texMode) {
		tex = i_tex;
		setTextureMode(i_texMode);
	}

	/**
	 * Set the way the texture is used by the surface.
	 * 
	 * @param i_texMode
	 *            The way the texture is used by the surface. "TILE" let the
	 *            texture draw on every quad of the surface. "WHOLE" will
	 *            spreading the texture over the entire surface.
	 * @related setTexture ( )
	 * @related clearTexture ( )
	 */
	public void setTextureMode(String i_texMode) {
		if (i_texMode.equals("TILE")) {
			texMode = 0;
		} else if (i_texMode.equals("WHOLE")) {
			texMode = 1;
		}
	}

	/**
	 * This will clear the texture that you have set with the setTexture method.
	 * 
	 * @related setTexture ( )
	 */
	public void clearTexture() {
		tex = null;
	}
	
	/**
	 * Turn on or off vertex normals. With vertex normals the surface will look much smoother. It doesen't works with pipes at the moment.
	 * @param b
	 * @example vertexNormals
	 */
	public void useVertexNormals(boolean b){
		mesh = b ? new SurfaceMesh(this) : null;
	}
	
	
	/**
	 * Updates the mesh, that is needed to calculate the vertex normals. Used after you change the coords or using getPointScaleDifference(). 
	 */
	public void updateMesh(){
		mesh.updateFaces();
	}
	
	

	// ////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////
	//
	// DRAWING METHODS
	//
	// ////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////

	/**
	 * The methode draws the full surface. If you have initialize your surface
	 * with colors, this colors will be used for stroke and fill otherwise the
	 * latest fill and stroke settings will be applied. Use noStroke or noFill
	 * before this methode to hide fills or strokes.
	 * 
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 * 
	 * @example draw
	 * @applet draw
	 */
	public void draw() {
		for (int i = 0; i < phiSteps; i++) {
			drawVerticalStrip(i);
		}
	}

	/**
	 * Draws the part of the surface between the passing bounding. If you wanna
	 * draw a hemisphere of your sphere surface for example you have to call
	 * drawPart(0,phiSteps,0,thetaSteps/2).
	 * 
	 * @param i_startIndexPhi
	 *            a value between 0 and the phiSteps your surface was
	 *            initialized with
	 * @param i_endIndexPhi
	 *            a value between i_startIndexPhi and the phiSteps your surface
	 *            was initialized with
	 * @param i_startIndexTheta
	 *            a value between 0 and the thetaSteps your surface was
	 *            initialized with
	 * @param i_endIndexTheta
	 *            a value between i_startIndexTheta and the thetaSteps your
	 *            surface was initialized with
	 * 
	 * @example drawPart
	 * @applet drawPart
	 * 
	 * @related draw ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawPart(final int i_startIndexPhi, final int i_endIndexPhi, final int i_startIndexTheta, final int i_endIndexTheta) {
		drawPart(i_startIndexPhi, i_endIndexPhi, i_startIndexTheta, i_endIndexTheta, 1, 1);
	}

	/**
	 * @param i_startIndexPhi
	 *            a value between 0 and the phiSteps your surface was
	 *            initialized with
	 * @param i_endIndexPhi
	 *            a value between i_startIndexPhi and the phiSteps your surface
	 *            was initialized with
	 * @param i_startIndexTheta
	 *            a value between 0 and the thetaSteps your surface was
	 *            initialized with
	 * @param i_endIndexTheta
	 *            a value between i_startIndexTheta and the thetaSteps your
	 *            surface was initialized with
	 * @param i_stepPhi
	 *            the steps size for phi; if i is 1 every segment will be drawn
	 *            if its 2 only every second an so on
	 * @param i_stepTheta
	 *            the steps size for theta; if j is 1 every segment will be
	 *            drawn if its 2 only every second an so on
	 */
	public void drawPart(final int i_startIndexPhi, final int i_endIndexPhi, final int i_startIndexTheta, final int i_endIndexTheta, final int i_stepPhi, final int i_stepTheta) {
		for (int i = i_startIndexPhi; i < i_endIndexPhi; i += i_stepPhi) {
			for (int j = i_startIndexTheta; j < i_endIndexTheta; j += i_stepTheta) {
				drawHorizontalSegment(i, j, i + 2, j + 2);
			}
		}
	}

	/**
	 * This draws a horizontal strip of the surface.
	 * 
	 * @param i_indexTheta
	 *            The number of the Strip you wanna draw. The number must lie in
	 *            between 0 and the theatSteps your surface was initialized
	 *            with.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalSegment ( )
	 * 
	 * @example drawHorizontalStrip
	 * @applet drawHorizontalStrip
	 */
	public void drawHorizontalStrip(final int i_indexTheta) {
		drawHorizontalSegment(0, i_indexTheta, phiSteps, i_indexTheta + 2);
	}

	/**
	 * Draws a horizontal strip at j between i_startIndexPhi and i_endIndexPhi
	 * 
	 * @param i_indexTheta
	 *            The number of the Strip you wanna draw. The number must lie in
	 *            between 0 and the theatSteps your surface was initialized
	 *            with.
	 * @param i_startIndexPhi
	 *            Number of the first vertical element. The number must lie in
	 *            between 0 and the phiSteps your surface was initialized with.
	 * @param i_endIndexPhi
	 *            Number of the last vertical element. The number must lie in
	 *            between i_startIndexPhi and the phiSteps your surface was
	 *            initialized with.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawHorizontalStrip(final int i_indexTheta, final int i_startIndexPhi, final int i_endIndexPhi) {
		drawHorizontalSegment(i_startIndexPhi, i_indexTheta, i_endIndexPhi, i_indexTheta + 2);
	}

	/**
	 * Draws a horizontal strip between two surfaces with different radiuses.
	 * 
	 * @param i_indexTheta
	 *            The number of the Strip you wanna draw. The number must lie in
	 *            between 0 and the theatSteps your surface was initialized
	 *            with.
	 * @param i_radius
	 *            The radius of the inner surface.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalSegment ( )
	 * @example drawHorizontalStrip
	 * @applet drawHorizontalStrip
	 */
	public void drawHorizontalStrip(final int i_indexTheta, final float i_radius) {
		drawHorizontalSegment(i_indexTheta, 0, phiSteps, i_radius);
	}

	/**
	 * Draws a vertical strip between two surfaces with different radiuses.
	 * 
	 * @param i_indexTheta
	 *            The number of the Strip you wanna draw. The number must lie in
	 *            between 0 and the theatSteps your surface was initialized
	 *            with.
	 * @param i_startIndexPhi
	 *            Number of the first vertical element. The number must lie in
	 *            between 0 and the phiSteps your surface was initialized with.
	 * @param i_endIndexPhi
	 *            Number of the last vertical element. The number must lie in
	 *            between i_startIndexPhi and the phiSteps your surface was
	 *            initialized with.
	 * @param i_radius
	 *            Width of the strip.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawHorizontalStrip(final int i_indexTheta, final int i_startIndexPhi, final int i_endIndexPhi, final float i_radius) {
		drawHorizontalSegment(i_indexTheta, i_startIndexPhi, i_endIndexPhi, i_radius);
	}

	/**
	 * Draws horizontal Segments between the given parameters. This is called by
	 * drawPart() and drawHorizontalStrip().
	 * 
	 * @param i_startIndexPhi
	 *            Number of the first vertical element. The number must lie in
	 *            between 0 and the phiSteps your surface was initialized with.
	 * @param i_startIndexTheta
	 *            Number of the first horizontal element. The number must lie in
	 *            between 0 and the thetaSteps your surface was initialized
	 *            with.
	 * @param i_endIndexPhi
	 *            Number of the last vertical element. The number must lie in
	 *            between i_startIndexPhi and the phiSteps your surface was
	 *            initialized with.
	 * @param i_endIndexTheta
	 *            Number of the last horizontal element. The number must lie in
	 *            between i_startIndexTheta and the thetaSteps your surface was
	 *            initialized with.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 */
	public void drawHorizontalSegment(int i_startIndexPhi, int i_startIndexTheta, int i_endIndexPhi, int i_endIndexTheta) {
		i_startIndexPhi = Math.max(0, i_startIndexPhi);
		i_startIndexTheta = Math.max(0, i_startIndexTheta);
		i_endIndexPhi = Math.min(i_endIndexPhi, phiSteps);
		i_endIndexTheta = Math.min(i_endIndexTheta, thetaSteps);

		g.beginShape(PConstants.QUAD_STRIP);
		if (tex != null)
			g.texture(tex);
		
		PVector[][] normals = null;
		if(mesh != null){
			 normals = mesh.normals;
		}
		for (int i = i_startIndexPhi; i < i_endIndexPhi; i++) {
			for (int j = i_startIndexTheta; j < i_endIndexTheta - 1; j++) {

				float x1 = coords[i][j][0] * (xScale + pointScaleDifference[i][j][0]);
				float y1 = coords[i][j][1] * (yScale + pointScaleDifference[i][j][1]);
				float z1 = coords[i][j][2] * (zScale + pointScaleDifference[i][j][2]);

				float x2 = coords[i][j + 1][0] * (xScale + pointScaleDifference[i][j + 1][0]);
				float y2 = coords[i][j + 1][1] * (yScale + pointScaleDifference[i][j + 1][1]);
				float z2 = coords[i][j + 1][2] * (zScale + pointScaleDifference[i][j + 1][2]);
				if (colors != null) {
					if (g.fill)
						g.fill(colors[i][j]);
					if (g.stroke)
						g.stroke(colors[i][j]);
				}
				if(normals != null){
					 PVector n  = normals[i][j];
					g.normal(n.x,n.y,n.z);
				}
				if (tex == null) {
					g.vertex(x1, y1, z1);
				} else {
					if (texMode == 0) {
						g.vertex(x1, y1, z1, (tex.width - 2) * (i % 2) + 1, 1);
					} else {
						g.vertex(x1, y1, z1, (tex.width / ((float) phiSteps - 1)) * i, (tex.height / ((float) thetaSteps - 1)) * j);
					}
				}

				if (colors != null) {
					if (g.fill)
						g.fill(colors[i][j + 1]);
					if (g.stroke)
						g.stroke(colors[i][j + 1]);
				}
				if(normals != null){
					 PVector n  = normals[i][j+1];
					g.normal(n.x,n.y,n.z);
				}
				if (tex == null) {
					g.vertex(x2, y2, z2);
				} else {
					if (texMode == 0) {
						g.vertex(x2, y2, z2, (tex.width - 2) * (i % 2) - 2, tex.height - 2);
					} else {
						g.vertex(x2, y2, z2, (tex.width / ((float) phiSteps - 1)) * i, (tex.height / ((float) thetaSteps - 1)) * (j + 1));
					}
				}
				
			}
		}
		g.endShape();
	}

	/**
	 * @param i_stripNumber
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_endIndexPhi
	 *            The number must lie in between i_startIndexPhi and the
	 *            phiSteps your surface was initialized with.
	 * @param i_radius
	 *            The radius of the inner surface.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 */
	public void drawHorizontalSegment(int i_stripNumber, int i_startIndexPhi, int i_endIndexPhi, final float i_radius) {
		i_stripNumber = Math.min(Math.max(0, i_stripNumber), thetaSteps - 1);
		i_startIndexPhi = Math.max(0, i_startIndexPhi);
		i_endIndexPhi = Math.min(i_endIndexPhi, phiSteps);

		g.beginShape(PConstants.QUAD_STRIP);
		if (tex != null)
			g.texture(tex);
		
		for (int i = i_startIndexPhi; i < i_endIndexPhi; i++) {

			float x1 = coords[i][i_stripNumber][0] * (xScale + pointScaleDifference[i][i_stripNumber][0]);
			float y1 = coords[i][i_stripNumber][1] * (yScale + pointScaleDifference[i][i_stripNumber][1]);
			float z1 = coords[i][i_stripNumber][2] * (zScale + pointScaleDifference[i][i_stripNumber][2]);

			float x2 = coords[i][i_stripNumber][0] * (xScale + pointScaleDifference[i][i_stripNumber][0] + i_radius);
			float y2 = coords[i][i_stripNumber][1] * (yScale + pointScaleDifference[i][i_stripNumber][1] + i_radius);
			float z2 = coords[i][i_stripNumber][2] * (zScale + pointScaleDifference[i][i_stripNumber][2] + i_radius);
			if (colors != null) {
				if (g.fill)
					g.fill(colors[i][i_stripNumber]);
				if (g.stroke)
					g.stroke(colors[i][i_stripNumber]);
			}
			if (tex == null) {
				g.vertex(x1, y1, z1);
			} else {
				if (texMode == 0) {
					g.vertex(x1, y1, z1, 1, 1);
				} else {
					g.vertex(x1, y1, z1, (tex.width / phiSteps) * i, (tex.height / thetaSteps) * i_stripNumber);
				}
			}

			if (tex == null) {
				g.vertex(x2, y2, z2);
			} else {
				if (texMode == 0) {
					g.vertex(x2, y2, z2, (tex.width - 2) - 2, tex.height - 2);
				} else {
					g.vertex(x2, y2, z2, (tex.width / phiSteps) * i, (tex.height / thetaSteps) * (i_stripNumber + 1));
				}
			}
		}
		g.endShape();
	}

	/**
	 * This draws a vertical strip of the surface.
	 * 
	 * @param i_stripNumber
	 *            The number of the Strip you wanna draw. The number must lie in
	 *            between 0 and the phiSteps your surface was initialized with.
	 * @example drawVerticalStrip1
	 * @applet drawVerticalStrip1
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawVerticalStrip(final int i_stripNumber) {
		drawVerticalSegment(i_stripNumber, 0, i_stripNumber + 1, thetaSteps);
	}

	/**
	 * @param i_stripNumber
	 *            The number of the Strip you wanna draw. The number must lie in
	 *            between 0 and the phiSteps your surface was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 */
	public void drawVerticalStrip(final int i_stripNumber, final int i_startIndexTheta, final int i_endIndexTheta) {
		drawVerticalSegment(i_stripNumber, i_startIndexTheta, i_stripNumber + 1, i_endIndexTheta);
	}

	/**
	 * Draws a vertical strip between two surfaces with different radiuses.
	 * 
	 * @param i_stripNumber
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_radius
	 *            The radius of the inner surface.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawVerticalStrip(final int i_stripNumber, final float i_radius) {
		drawVerticalSegment(i_stripNumber, 0, thetaSteps, i_radius);
	}

	/**
	 * Draws a vertical strip between two surfaces with different radiuses.
	 * 
	 * @param i_stripeNumber
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 * @param i_radius
	 *            The radius of the inner surface.
	 * @example drawVerticalStrip2
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalSegment ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawVerticalStrip(final int i_stripeNumber, final int i_startIndexTheta, final int i_endIndexTheta, final float i_radius) {
		drawVerticalSegment(i_stripeNumber, i_startIndexTheta, i_endIndexTheta, i_radius);
	}

	/**
	 * Draws a vertical segment between the given parameters
	 * 
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_endIndexPhi
	 *            The number must lie in between i_startIndexPhi and the
	 *            phiSteps your surface was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawVerticalSegment(int i_startIndexPhi, int i_startIndexTheta, int i_endIndexPhi, int i_endIndexTheta) {
		i_startIndexPhi = Math.max(0, i_startIndexPhi);
		i_startIndexTheta = Math.max(0, i_startIndexTheta);
		i_endIndexPhi = Math.min(i_endIndexPhi, phiSteps - phiSub);
		i_endIndexTheta = Math.min(i_endIndexTheta, thetaSteps);

		g.beginShape(PConstants.QUAD_STRIP);
		if (tex != null)
			g.texture(tex);
		PVector[][] normals = null;
		if(mesh != null){
			 normals = mesh.normals;
		}
		for (int i = i_startIndexPhi; i < i_endIndexPhi; i++) {
			for (int j = i_startIndexTheta; j < i_endIndexTheta; j++) {

				int nextI = i + 1;
				if (nextI > phiSteps - 2)
					nextI = 0;

				float x1 = coords[i][j][0] * (xScale + pointScaleDifference[i][j][0]);
				float y1 = coords[i][j][1] * (yScale + pointScaleDifference[i][j][1]);
				float z1 = coords[i][j][2] * (zScale + pointScaleDifference[i][j][2]);

				float x2 = coords[nextI][j][0] * (xScale + pointScaleDifference[nextI][j][0]);
				float y2 = coords[nextI][j][1] * (yScale + pointScaleDifference[nextI][j][1]);
				float z2 = coords[nextI][j][2] * (zScale + pointScaleDifference[nextI][j][2]);

				float tx, ty;
				if (colors != null) {
					if (g.fill)
						g.fill(colors[i][j]);
					if (g.stroke)
						g.stroke(colors[i][j]);
					if (tex != null) {
						g.tint(colors[i][j]);
					}
				}
				
				if(normals != null){
					 PVector n  = normals[i][j];
					g.normal(n.x,n.y,n.z);
				}
				if (tex == null) {
					g.vertex(x1, y1, z1);
				} else {
					tx = tex.width / ((float) phiSteps) * i;
					ty = tex.height / ((float) thetaSteps) * j;
					if (texMode == 0) {
						g.vertex(x1, y1, z1, 1, 1);
					} else {
						g.vertex(x1, y1, z1, tx, ty);
					}
				}

				if (colors != null) {
					if (g.fill)
						g.fill(colors[nextI][j]);
					if (g.stroke)
						g.stroke(colors[nextI][j]);
					if (tex != null) {
						g.tint(colors[i][j]);
					}
				}
				
				if(normals != null){
					 PVector n  = normals[nextI][j];
					g.normal(n.x,n.y,n.z);
				}
				
				if (tex == null) {
					g.vertex(x2, y2, z2);
				} else {
					if (texMode == 0) {
						g.vertex(x2, y2, z2, tex.width - 2, tex.height - 2);
					} else {
						ty = tex.height / ((float) thetaSteps - 1) * j;
						if (i == phiSteps - 2) {
							tx = tex.width;
						} else {
							tx = tex.width / ((float) phiSteps - 1) * (i + 1);
						}
						g.vertex(x2, y2, z2, tx, ty);
					}
				}
			}
		}
		g.endShape();
	}

	/**
	 * Draws a vertical segment between two surfaces with different radiuses.
	 * 
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 * @param i_radius
	 *            inner radius
	 * @related draw ( )
	 * @related drawPart ( )
	 * @related drawVerticalStrip ( )
	 * @related drawHorizontalStrip ( )
	 * @related drawHorizontalSegment ( )
	 */
	public void drawVerticalSegment(final int i_startIndexPhi, int i_startIndexTheta, int i_endIndexTheta, final float i_radius) {
		int i = Math.min(Math.max(0, i_startIndexPhi), phiSteps - 1);
		i_startIndexTheta = Math.max(0, i_startIndexTheta);
		i_endIndexTheta = Math.min(i_endIndexTheta, thetaSteps);

		g.beginShape(PConstants.QUAD_STRIP);
		if (tex != null)
			g.texture(tex);
		for (int j = i_startIndexTheta; j < i_endIndexTheta; j++) {

			float x1 = coords[i][j][0] * (xScale + pointScaleDifference[i][j][0]);
			float y1 = coords[i][j][1] * (yScale + pointScaleDifference[i][j][1]);
			float z1 = coords[i][j][2] * (zScale + pointScaleDifference[i][j][2]);

			float x2 = coords[i][j][0] * (xScale + pointScaleDifference[i][j][0] + i_radius);
			float y2 = coords[i][j][1] * (yScale + pointScaleDifference[i][j][1] + i_radius);
			float z2 = coords[i][j][2] * (zScale + pointScaleDifference[i][j][2] + i_radius);

			float tx, ty;
			if (colors != null) {
				if (g.fill)
					g.fill(colors[i][j]);
				if (g.stroke)
					g.stroke(colors[i][j]);
				if (tex != null) {
					g.tint(colors[i][j]);
				}

			}
			if (tex == null) {
				g.vertex(x1, y1, z1);
			} else {
				if (texMode == 0) {
					g.vertex(x1, y1, z1, 1, tex.height * (j % 2) + 1);
				} else {
					tx = tex.width / ((float) phiSteps - 1) * i;
					ty = tex.height / ((float) thetaSteps - 1) * j;
					g.vertex(x1, y1, z1, tx, ty);
				}
			}

			if (tex == null) {
				g.vertex(x2, y2, z2);
			} else {
				if (texMode == 0) {
					g.vertex(x2, y2, z2, tex.width - 2, tex.height * (j % 2) - 2);
				} else {
					ty = tex.height / ((float) thetaSteps - 1) * j;
					if (i == phiSteps - 2) {
						tx = tex.width;
					} else {
						tx = tex.width / ((float) phiSteps - 1) * (i + 1);
					}
					g.vertex(x2, y2, z2, tx, ty);
				}

			}
		}
		g.endShape();
	}

	/**
	 * Draws a horizontal pipe
	 * 
	 * @param i_stripNumber
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_radius
	 *            inner radius
	 */
	public void drawHorizontalPipe(final int i_stripNumber, final float i_radius) {
		drawHorizontalPipeSegment(0, i_stripNumber, phiSteps, i_stripNumber + 1, i_radius);
	}

	/**
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_endIndexPhi
	 *            The number must lie in between i_startIndexPhi and and the
	 *            phiSteps your surface was initialized with.
	 * @param i_radius
	 *            inner radiuss
	 */
	public void drawHorizontalPipe(final int i_startIndexTheta, final int i_startIndexPhi, final int i_endIndexPhi, final float i_radius) {
		drawHorizontalPipeSegment(i_startIndexPhi, i_startIndexTheta, i_endIndexPhi, i_startIndexTheta + 2, i_radius);
	}

	/**
	 * Draws a vertical pipe
	 * 
	 * @param i_stripNumber
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_radius
	 *            inner radius
	 */
	public void drawVerticalPipe(final int i_stripNumber, final float i_radius) {
		drawVerticalPipeSegment(i_stripNumber, 0, i_stripNumber + 1, thetaSteps, i_radius);
	}

	/**
	 * Draws a vertical pipe at i between i_startIndexTheta and i_endIndexTheta
	 * 
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 * @param i_radius
	 *            inner radiuss
	 */
	public void drawVerticalPipe(final int i_startIndexPhi, final int i_startIndexTheta, final int i_endIndexTheta, final float i_radius) {
		drawVerticalPipeSegment(i_startIndexPhi, i_startIndexTheta, i_startIndexPhi + 1, i_endIndexTheta, i_radius);
	}

	/**
	 * Draws a segment of a pipe
	 * 
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_endIndexPhi
	 *            The number must lie in between i_startIndexPhi and the
	 *            phiSteps your surface was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 * @param i_radius
	 *            inner radius
	 */
	public void drawHorizontalPipeSegment(int i_startIndexPhi, int i_startIndexTheta, int i_endIndexPhi, int i_endIndexTheta, final float i_radius) {
		i_startIndexPhi = Math.max(0, i_startIndexPhi);
		i_startIndexTheta = Math.max(0, i_startIndexTheta);
		i_endIndexPhi = Math.min(i_endIndexPhi, phiSteps);
		i_endIndexTheta = Math.min(i_endIndexTheta, thetaSteps);
		drawVerticalStrip(i_startIndexPhi, i_startIndexTheta, i_endIndexTheta, i_radius);
		drawVerticalStrip(i_endIndexPhi, i_startIndexTheta, i_endIndexTheta, i_radius);
		drawHorizontalStrip(i_startIndexTheta, i_startIndexPhi, i_endIndexPhi, i_radius);
		drawHorizontalStrip(i_endIndexTheta - 1, i_startIndexPhi, i_endIndexPhi + 1, i_radius);
		drawPart(i_startIndexPhi, i_endIndexPhi, i_startIndexTheta, i_endIndexTheta - 1);
		xScale += i_radius;
		yScale += i_radius;
		zScale += i_radius;
		drawPart(i_startIndexPhi, i_endIndexPhi, i_startIndexTheta, Math.max(i_startIndexTheta, i_endIndexTheta - 1));
		xScale -= i_radius;
		yScale -= i_radius;
		zScale -= i_radius;

	}

	public void drawVerticalPipeSegment(int i_startIndexPhi, int i_startIndexTheta, int i_endIndexPhi, int i_endIndexTheta, final float i_radius) {
		i_startIndexPhi = Math.max(0, i_startIndexPhi);
		i_startIndexTheta = Math.max(0, i_startIndexTheta);
		i_endIndexPhi = Math.min(i_endIndexPhi, phiSteps);
		i_endIndexTheta = Math.min(i_endIndexTheta, thetaSteps);
		drawVerticalStrip(i_startIndexPhi, i_startIndexTheta, i_endIndexTheta, i_radius);
		drawVerticalStrip(i_endIndexPhi, i_startIndexTheta, i_endIndexTheta, i_radius);
		drawHorizontalStrip(i_startIndexTheta, i_startIndexPhi, i_endIndexPhi + 1, i_radius);
		drawHorizontalStrip(i_endIndexTheta - 1, i_startIndexPhi, i_endIndexPhi + 1, i_radius);
		drawPart(i_startIndexPhi, i_endIndexPhi, i_startIndexTheta, i_endIndexTheta - 1);
		xScale += i_radius;
		yScale += i_radius;
		zScale += i_radius;
		drawPart(i_startIndexPhi, i_endIndexPhi, i_startIndexTheta, Math.max(i_startIndexTheta, i_endIndexTheta - 1));
		xScale -= i_radius;
		yScale -= i_radius;
		zScale -= i_radius;

	}

	int color(int r, int g, int b) {
		int i = (125 << 24) + (r << 16) + (g << 8) + (b);
		return i;
	}

	/*
	 * public void drawHorizontalPipeSegment(int i_startIndexPhi, int
	 * i_startIndexTheta, int i_endIndexPhi, int i_endIndexTheta, final float
	 * i_radius) {
	 * 
	 * i_startIndexPhi = Math.max(0, i_startIndexPhi); i_startIndexTheta =
	 * Math.max(0, i_startIndexTheta); i_endIndexPhi = Math.min(i_endIndexPhi,
	 * phiSteps); i_endIndexTheta = Math.min(i_endIndexTheta, thetaSteps);
	 * 
	 * drawHorizontalStrip(i_endIndexTheta, i_startIndexPhi, i_startIndexTheta,
	 * i_radius); drawVerticalStrip(i_startIndexPhi + 1, i_startIndexTheta,
	 * i_endIndexTheta, i_radius);
	 * 
	 * drawHorizontalSegment(i_startIndexTheta, i_startIndexPhi, i_startIndexPhi +
	 * 2, i_radius); drawHorizontalSegment(i_endIndexTheta - 1, i_startIndexPhi,
	 * i_startIndexPhi + 2, i_radius);
	 * 
	 * drawVerticalStrip(i_startIndexPhi, i_startIndexTheta, i_endIndexTheta);
	 * xScale += i_radius; yScale += i_radius; zScale += i_radius;
	 * drawVerticalStrip(i_startIndexPhi, i_startIndexTheta, i_endIndexTheta);
	 * xScale -= i_radius; yScale -= i_radius; zScale -= i_radius; }
	 */
	/**
	 * Draws all horizontal lines on your surface.
	 * 
	 * @example drawHorizontalLines
	 * @applet drawHorizontalLines
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineStrip ( )
	 * @related drawHorizontalLineSegment ( )
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLineStrip ( )
	 * @related drawVerticalLineSegment ( )
	 */
	public void drawHorizontalLines() {
		for (int i = 0; i < phiSteps - 1; i++) {
			for (int j = 0; j < thetaSteps - 1; j++) {
				drawHorizontalLineSegment(i, j);
			}
		}
	}

	/**
	 * Draws a part of horizontal line.
	 * 
	 * @param i_startIndexPhi
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_endIndexPhi
	 *            The number must lie in between i_startIndexPhi and the
	 *            phiSteps your surface was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and and the
	 *            thetaSteps your surface was initialized with.
	 * @param i_stepPhi
	 *            The steps size for phi; if i is 1 every segment will be drawn
	 *            if its 2 only every second an so on
	 * @param i_stepTheta
	 *            The steps size for theta; if j is 1 every segment will be
	 *            drawn if its 2 only every second an so on
	 */
	public void drawHorizontalLinePart(final int i_startIndexPhi, final int i_endIndexPhi, final int i_startIndexTheta, final int i_endIndexTheta, final int i_stepPhi, final int i_stepTheta) {
		for (int i = i_startIndexPhi; i < i_endIndexPhi; i += i_stepPhi) {
			for (int j = i_startIndexTheta; j < i_endIndexTheta; j += i_stepTheta) {
				drawHorizontalLineSegment(i, j);
			}
		}
	}

	/**
	 * Draws a horizontal line between the points p1(i_phiIndex, 0) and
	 * p2(i_phiIndex, thetaSteps).
	 * 
	 * @param i_phiIndex
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @example drawHorizontalLineStrip
	 * @applet drawHorizontalLineStrip
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLineStrip ( )
	 * @related drawVerticalLineSegment ( )
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineSegment ( )
	 */
	public void drawHorizontalLineStrip(final int i_phiIndex) {
		for (int j = 0; j < thetaSteps - 1; j++) {
			drawHorizontalLineSegment(i_phiIndex, j);
		}
	}

	/**
	 * Draws a horizontal line between the points p1(i_phiIndex,
	 * i_startIndexTheta) and p2(i_phiIndex, i_endIndexTheta).
	 * 
	 * @param i_phiIndex
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_startIndexTheta
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @param i_endIndexTheta
	 *            The number must lie in between i_startIndexTheta and the
	 *            thetaSteps your surface was initialized with.
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLinePart ( )
	 * @related drawVerticalLineStrip ( )
	 * @related drawVerticalLineSegment ( )
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineSegment ( )
	 */
	public void drawHorizontalLineStrip(final int i_phiIndex, final int i_startIndexTheta, final int i_endIndexTheta) {
		for (int j = i_startIndexTheta; j < i_endIndexTheta; j++) {
			drawHorizontalLineSegment(i_phiIndex - 1, j);
		}
	}

	/**
	 * Draws a horizontal line between the points p1(i_phiIndex, i_thetaIndex)
	 * and p2(i_phiIndex, i_thetaIndex + 1). This methode is called by
	 * drawHoritontalLines(), drawHorizontalLinePart(),
	 * drawHorizontalLineStrip().
	 * 
	 * @param i_phiIndex
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_thetaIndex
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLineStrip ( )
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLineSegment ( )
	 * @related drawVerticalLineStrip ( )
	 */
	public void drawHorizontalLineSegment(int i_phiIndex, int i_thetaIndex) {
		i_phiIndex = Math.max(0, Math.min(i_phiIndex, phiSteps - phiSub));
		i_thetaIndex = Math.max(0, Math.min(i_thetaIndex, thetaSteps - 1));
		int saveFill = g.fillColor;
		g.beginShape();
		g.noFill();
		int nextJ;

		if (i_thetaIndex >= thetaSteps - 1) {
			nextJ = i_thetaIndex;
		} else {
			nextJ = i_thetaIndex + 1;
		}

		float x1 = coords[i_phiIndex][i_thetaIndex][0] * (xScale + pointScaleDifference[i_phiIndex][i_thetaIndex][0]);
		float y1 = coords[i_phiIndex][i_thetaIndex][1] * (yScale + pointScaleDifference[i_phiIndex][i_thetaIndex][1]);
		float z1 = coords[i_phiIndex][i_thetaIndex][2] * (zScale + pointScaleDifference[i_phiIndex][i_thetaIndex][2]);

		float x2 = coords[i_phiIndex][nextJ][0] * (xScale + pointScaleDifference[i_phiIndex][nextJ][0]);
		float y2 = coords[i_phiIndex][nextJ][1] * (yScale + pointScaleDifference[i_phiIndex][nextJ][1]);
		float z2 = coords[i_phiIndex][nextJ][2] * (zScale + pointScaleDifference[i_phiIndex][nextJ][2]);

		if (colors != null) {
			if (g.stroke)
				g.stroke(colors[i_phiIndex][i_thetaIndex]);
		}
		g.vertex(x1, y1, z1);
		if (colors != null) {
			if (g.stroke)
				g.stroke(colors[i_phiIndex][nextJ]);
		}
		g.vertex(x2, y2, z2);

		g.endShape();
		g.fill(saveFill);
	}

	/**
	 * Draws all vertical lines on your surface.
	 * 
	 * @example drawVerticalLines
	 * @applet drawVerticalLines
	 * 
	 * @related drawVerticalLineStrip ( )
	 * @related drawVerticalLineSegment ( )
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineStrip ( )
	 * @related drawHorizontalLineSegment ( )
	 */
	public void drawVerticalLines() {
		for (int i = 0; i < phiSteps - 1; i++) {
			for (int j = 0; j < thetaSteps - 1; j++) {
				drawVerticalLineSegment(i, j);
			}
		}
	}

	/**
	 * Draws a vertical line between the points p1(0, i_thetaIndex) and
	 * p2(phiSteps, i_thetaIndex).
	 * 
	 * @example drawVerticalLineStrip
	 * @applet drawVerticalLineStrip
	 * @param i_thetaIndex
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLineSegment ( )
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineStrip ( )
	 * @related drawHorizontalLineSegment ( )
	 */
	public void drawVerticalLineStrip(final int i_thetaIndex) {
		for (int i = 0; i < phiSteps - 1; i++) {
			drawVerticalLineSegment(i, i_thetaIndex);
		}
	}

	/**
	 * Draws a vertical line between the points p1(i_startPhiIndex,
	 * i_thetaIndex) and p2(i_endPhiIndex, i_thetaIndex).
	 * 
	 * @param i_startPhiIndex
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_endPhiIndex
	 *            The number must lie in between i_startPhiIndex and the
	 *            phiSteps your surface was initialized with.
	 * @param i_thetaIndex
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLineSegment ( )
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineStrip ( )
	 * @related drawHorizontalLineSegment ( )
	 */
	public void drawVerticalLineStrip(final int i_thetaIndex, final int i_startPhiIndex, final int i_endPhiIndex) {
		for (int i = i_startPhiIndex; i < i_endPhiIndex; i++) {
			drawVerticalLineSegment(i, i_thetaIndex);
		}
	}

	/**
	 * Draws a vertical line between the points p1(i_phiIndex, i_thetaIndex) and
	 * p2(i_phiIndex + 1 or 0 if i_phiIndex < phiStep-1, i_thetaIndex). This
	 * methode is called by drawVerticalLines(), drawVerticalLinePart(),
	 * drawVerticalLineStrip().
	 * 
	 * @param i_phiIndex
	 *            The number must lie in between 0 and the phiSteps your surface
	 *            was initialized with.
	 * @param i_thetaIndex
	 *            The number must lie in between 0 and the thetaSteps your
	 *            surface was initialized with.
	 * @related drawVerticalLines ( )
	 * @related drawVerticalLineStrip ( )
	 * @related drawHorizontalLines ( )
	 * @related drawHorizontalLinePart ( )
	 * @related drawHorizontalLineStrip ( )
	 * @related drawHorizontalLineSegment ( )
	 */
	public void drawVerticalLineSegment(int i_phiIndex, int i_thetaIndex) {
		i_phiIndex = Math.max(0, Math.min(i_phiIndex, phiSteps - phiSub));
		i_thetaIndex = Math.max(0, Math.min(i_thetaIndex, thetaSteps - 1));
		int saveFill = g.fillColor;
		g.noFill();
		g.beginShape();
		int nextI;
		// int nextJ;
		if (i_phiIndex >= phiSteps - 1) {
			nextI = 0;
		} else {
			nextI = i_phiIndex + 1;
		}

		float x1 = coords[i_phiIndex][i_thetaIndex][0] * (xScale + pointScaleDifference[i_phiIndex][i_thetaIndex][0]);
		float y1 = coords[i_phiIndex][i_thetaIndex][1] * (yScale + pointScaleDifference[i_phiIndex][i_thetaIndex][1]);
		float z1 = coords[i_phiIndex][i_thetaIndex][2] * (zScale + pointScaleDifference[i_phiIndex][i_thetaIndex][2]);

		float x2 = coords[nextI][i_thetaIndex][0] * (xScale + pointScaleDifference[nextI][i_thetaIndex][0]);
		float y2 = coords[nextI][i_thetaIndex][1] * (yScale + pointScaleDifference[nextI][i_thetaIndex][1]);
		float z2 = coords[nextI][i_thetaIndex][2] * (zScale + pointScaleDifference[nextI][i_thetaIndex][2]);

		if (colors != null) {
			if (g.stroke)
				g.stroke(colors[i_phiIndex][i_thetaIndex]);
		}
		g.vertex(x1, y1, z1);
		if (colors != null) {
			if (g.stroke)
				g.stroke(colors[nextI][i_thetaIndex]);
		}
		g.vertex(x2, y2, z2);
		g.endShape();
		
		g.fill(saveFill);
	}

	protected int phiSub = 1;

}
