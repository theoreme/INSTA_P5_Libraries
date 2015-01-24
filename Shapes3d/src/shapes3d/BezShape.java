/*
  Part of the Shapes 3D library for Processing 
  	http://www.lagers.org.uk

  Copyright (c) 2009 Peter Lager

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
 */

package shapes3d;

import processing.core.PApplet;
import processing.core.PVector;
import shapes3d.utils.Bezier2D;
import shapes3d.utils.MeshSection;
import shapes3d.utils.Rot;

/**
 * A 3D shape generated by a 2D bezier curve in the XY plane being 
 * rotated  360 degrees about the Y axis. <br>
 *  
 * @author Peter Lager
 * 
 */
public class BezShape extends Mesh2DCore {
	
	/**
	 *  The bezier used to generate the 3D shape
	 */
	protected Bezier2D bz;

	/**
	 * Create a 3D BezierShape object <br>
	 * 
	 * @param app the PApplet to draw this shape
	 * @param bz the bezier curve to rotate about y axis
	 * @param nbrSlices number of steps along y axis
	 * @param nbrSegments number of segments around y axis
	 */
	public BezShape(PApplet app, Bezier2D bz, int nbrSlices, int nbrSegments){
		super();
		this.app = app;
		this.bz = bz;
		nsPieces = nbrSlices;
		ewPieces = nbrSegments;
		calcShape();
	}

	/**
	 * Create a 3D BezierShape object <br>
	 * 
	 * The shape is recalculated so the [0,1,0] axis is now 'up' <br>
	 * The shape's coordinates are translated to make 'centreOfRot'
	 * the objects centre of rotation. <br>
	 * 
	 * @param app the PApplet to draw this shape
	 * @param bz the bezier curve to rotate about y axis
	 * @param nbrSlices number of steps along y axis
	 * @param nbrSegments number of segments around y axis
	 * @param up
	 * @param centreOfRot
	 */
	public BezShape(PApplet app, Bezier2D bz, int nbrSlices, int nbrSegments, 
			PVector up, PVector centreOfRot){
		super();
		this.app = app;
		this.bz = bz;
		nsPieces = nbrSlices;
		ewPieces = nbrSegments;
		if(up != null && up.mag() > 0)
			this.up.set(up);
		if(centreOfRot != null)
			this.centreRot.set(centreOfRot);
		calcShape();
	}

	/**
	 * Create a 3D BezierShape object <br>
	 * 
	 * @param app the PApplet to draw this shape
	 * @param xyPoints array of PVectors representing the control points for a 2D bezier
	 * @param nbrSlices number of steps along y axis
	 * @param nbrSegments number of segments around y axis
	 */
	public BezShape(PApplet app, PVector[] xyPoints, int nbrSlices, int nbrSegments){
		this.app = app;
		this.bz = new Bezier2D(xyPoints, xyPoints.length);
		nsPieces = nbrSlices;
		ewPieces = nbrSegments;
		calcShape();
	}

	/**
	 * Create a 3D BezierShape object <br>
	 * 
	 * The shape is recalculated so the [0,1,0] axis is now 'up' <br>
	 * The shape's coordinates are translated to make 'centreOfRot'
	 * the objects centre of rotation. <br>
	 * 
	 * @param app the PApplet to draw this shape
	 * @param xyPoints array of PVectors representing the control points for a 2D bezier
	 * @param nbrSlices number of steps along y axis
	 * @param nbrSegments number of segments around y axis
	 * @param up
	 * @param centreOfRot
	 */
	public BezShape(PApplet app, PVector[] xyPoints, int nbrSlices, int nbrSegments, 
			PVector up, PVector centreOfRot){
		this.app = app;
		this.bz = new Bezier2D(xyPoints, xyPoints.length);
		nsPieces = nbrSlices;
		ewPieces = nbrSegments;
		if(up != null && up.mag() > 0)
			this.up.set(up);
		if(centreOfRot != null)
			this.centreRot.set(centreOfRot);
		calcShape();
	}

	/**
	 * Used internally to calculate the shape.
	 */
	@Override
	protected void calcShape(){
		nsSteps = nsPieces + 1;
		ewSteps = ewPieces + 1;
		coord = new PVector[ewSteps][nsSteps];
		norm = new PVector[ewSteps][nsSteps];
		fullShape = new MeshSection(ewSteps, nsSteps);
		calcXYZ();
	}

	/**
	 * Calculate the mesh and normals
	 */
	protected void calcXYZ() {
		PVector[] pointsXY = bz.points(nsSteps);
		PVector[] normals = bz.normals(nsSteps);

		for(int i = 0; i < nsSteps; i++){
			coord[0][i] = pointsXY[i];
			norm[0][i] = normals[i];
		}
		float angFactor = TWO_PI / (ewSteps -1);
		float angle = -angFactor;
		float sinA, cosA;
		PVector orgVec;
		for(int i = 1; i < ewSteps; i++) {
			sinA = (float) Math.sin(angle);
			cosA = (float) Math.cos(angle);
			for(int j = 0; j < nsSteps ; j++){
				orgVec = coord[0][j];
				coord[i][j] = new PVector(
						orgVec.x * cosA - orgVec.z * sinA,
						orgVec.y,
						orgVec.x * sinA + orgVec.z * cosA);
				orgVec = norm[0][j];
				norm[i][j] = new PVector(
						orgVec.x * cosA - orgVec.z * sinA,
						orgVec.y,
						orgVec.x * sinA + orgVec.z * cosA);
			}
			angle -= angFactor;
		}

		// Apply new orientation and centre of rotation
		Rot orrient = new Rot(new PVector(0,1,0), up);
		for(int i = 0; i < ewSteps; i++) {
			for(int j = 0; j < nsSteps ; j++){
				orrient.applyTo(coord[i][j]);
				coord[i][j].add(this.centreRot);
				orrient.applyTo(norm[i][j]);
				norm[i][j].normalize();
			}
		}
	}

}
