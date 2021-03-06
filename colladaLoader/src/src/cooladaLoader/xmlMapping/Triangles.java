package cooladaLoader.xmlMapping;
import java.util.HashMap;

import cooladaLoader.ColladaLoaderException;
import processing.data.XML;
/**
 * <p>Lucerne University of Applied Sciences and Arts <a href="http://www.hslu.ch">http://www.hslu.ch</a></p>
 *
 * <p>This source is free; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License and by nameing of the originally author</p>
 * 
 *<p> this class is a Helperclass for triangles-tag inside Geometry-tag (contains one or more triangles). It maps all points and their orders</p>
 * 
 * @author Markus Zimmermann <a href="http://www.die-seite.ch">http://www.die-seite.ch</a>
 * @version 1.2
 */
class Triangles extends SubTag{
        private String materialSymbol;
        private Source vertexSource, textureSource; //pointmatrice
        private int offsetVertex = -8; int offsetTextures = -8; //dummy defaultvalue
        private int[][] pointIndexMatrix;// format: [index][offset]
        private Material material;
        private XML triangles;


        Triangles(XML triangles, HashMap<String,SubTag> sources)
        {
            this.triangles = triangles;
            materialSymbol = triangles.getString("material");
            if (materialSymbol==null)throw new ColladaLoaderException("triangles tag not parsed (material attribute)");
           
            //extract p-Tag to matrix
            XML[] triangle_inputs = triangles.getChildren("input");
            int stride = triangle_inputs.length;
            int count = triangles.getInt("count");
            if ((stride*count)<=0) throw new ColladaLoaderException("triangles tag not parsed (p subtag)");
            String[] p_array = triangles.getChild("p").getContent().split(" ");

            pointIndexMatrix = new int[count*3][stride];

            for (int i = 0, k = 0; i<count*3; i++)
            {
                for (int j = 0; j < stride; j++, k++)
                pointIndexMatrix[i][j] = Integer.parseInt(p_array[k]);
            }

            //analyze the triangles-Tag, search for semantic VERTEX or TEXCOORD
            for (XML triangles_input : triangle_inputs)
            {
                if (triangles_input.getString("semantic").equals("VERTEX"))
                {
                    offsetVertex = triangles_input.getInt("offset",-1);
                    vertexSource = (Source)sources.get(sources.get(triangles_input.getString("source").substring(1)).getNextSource());
                    if (offsetVertex==-1 || vertexSource==null) 
                    	throw new ColladaLoaderException("triangles tag not parsed (VERTEX subtag)");               
                }
                if (triangles_input.getString("semantic").equals("TEXCOORD"))
                {
                    offsetTextures = triangles_input.getInt("offset",-1);
                    textureSource = (Source)sources.get(sources.get(triangles_input.getString("source").substring(1)).getNextSource());
                    if (offsetTextures==-1 || textureSource==null) 
                    	throw new ColladaLoaderException("triangles tag not parsed (TEXCOORD subtag)");

                }
            }

        }
    /**
     * invoke that methode after the Object is initiated. Because of cyclic depencies at runtime its not possible to set Material at initiating-time
     * @param instance of Material
     */
        void setMaterial(Material m)
        {
            material = m;
        }
    /**
     * gives the answer if the triangles contains a color or an image
     * @return false on color, true on image
     */
        boolean hasTexture()
        {
            return getMaterial().getEffect().hasTexture();
        }

        @Override
        public String toString()
        {
            String s = "";
            s+="Triangles from Geometry "+triangles.getParent().getParent().getString("id")+":\n";
            s+="uses Material symbol alias '"+getNextSource()+"'\n";
            s+= (material == null)? "material (still) not mapped \n": "uses material-ID"+material.getID()+"" +
                    " and has "+((material.getEffect().hasTexture())?"":"no ")+"Texture\n";
            s+= "has vertex-Points: \n";
            float[][] points = getVertexSource().getPointMatrix();
            int[][] indexes = getPointIndexMatrix();
            for (int i = 0, j = 0; j < indexes.length; i++ )
            {
                s+="Triangle_"+i+"\n";
                for (int k = 0; k < 3; k++, j++)
                {
                    int index = indexes[j][getOffsetVertex()];
                    s += "Point "+k+": x="+points[index][0]+",y="+points[index][1]+",z="+points[index][2]+"\n";

                }
            }

            if (getTextureSource() != null)
            {
                s+= "has texture-Points: \n";
                points = getTextureSource().getPointMatrix();

                for (int i = 0, j = 0; j < indexes.length; i++ )
                {
                    s+="Triangle_"+i+"\n";
                    for (int k = 0; k < 3; k++, j++)
                    {
                        int index = indexes[j][getOffsetTextures()];
                        s += "Point "+k+": x="+points[index][0]+",y="+points[index][1]+"\n";

                    }
                }
            }

            return s;
        }

        /**
         * @return the source of the vertices Position-Points (in 3D)
         */
        Source getVertexSource() {
            return vertexSource;
        }

        /**
         * @return the source of the Texture Position-Points (in 2D)
         */
        Source getTextureSource() {
            return textureSource;
        }

        /**
         * @return the offset for vertice in the indexmatrix
         */
        int getOffsetVertex() {
            return offsetVertex;
        }

        /**
         * @return the offset for Textures in the indexmatrix
         */
        int getOffsetTextures() {
            return offsetTextures;
        }

        /**
         * @return the pointIndexMatrix in the format: [index][offset]
         */
        int[][] getPointIndexMatrix() {
            return pointIndexMatrix;
        }

        /**
         * @return the material. It depends of the Runtime if it is null or not
         */
        Material getMaterial() {
            return material;
        }
        /**
         *
         * @return the Alias-Name of Material-ID (the Material-symbol)
         */
        String getNextSource()
        {
            return materialSymbol;
        }
        /**
         *
         * @return generates a Random-ID because the xml-Tag does'nt contain a defined ID
         */
        String getID()
        {
            return this.hashCode()+"_"+materialSymbol;
        }

    }