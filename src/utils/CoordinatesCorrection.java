package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Class to change the image coordinate in genome coordinate, with the name of the image, the resolution, the size of the image,
 * the step and the size of the diagonal.
 * 
 * Loops detected with white strips are removed. Furthermore the corner of the images are not take account. We test also the proximal loops to avoid overlaping loops.
 * 
 * @author axel poulet
 *
 */
public class CoordinatesCorrection {

	/** HashMap of the loops with new coordinates*/
	private HashMap<String,Loop> _data = new HashMap<String,Loop>();
	
	/**
	 * 
	 */
	public CoordinatesCorrection(){	}	
	
	/**
	 * change loop image coordinates in loop genomes coordinates,
	 *  
	 * @param temp: HashMap of loops with the image coordinates
	 * @param index: index of the image
	 * @return HashMap: key is a string  with the name of the chr start end. And value is Loop class, to stock 
	 * the loop characteristics.
	 */
	public HashMap<String,Loop> imageToGenomeCoordinate(HashMap<String,Loop> temp, int index){
		int x;
		int y;
		Set<String> key = temp.keySet();
		Iterator<String> it = key.iterator();
		while (it.hasNext()){
			Loop loop = temp.get(it.next());
			x = loop.getX();
			y = loop.getY();
			int resolution = loop.getResolution(); 
			int diagSize = loop.getDiagSize()*resolution;
			int imageSize = loop.getMatrixSize();
			int step = imageSize/2;
			int a = (x+(index*step))*resolution;
			int a_end = a+resolution;
			int b = (y+(index*step))*resolution;
			int b_end =b+resolution;
			String newName = loop.getChr()+"\t"+a+"\t"+b;
			if(a!=b && Math.abs(a-b) > diagSize){
				if (x > 1 && y > 1 && y < imageSize-2 && x < imageSize-2){
					if(this._data.containsKey(newName) == false){
						loop.setCoordinates(a, a_end, b, b_end);
						loop.setName(newName);
						this._data.put(newName, loop);
					}else{
						if(this._data.get(newName).getResolution() > resolution){
							loop.setCoordinates(a, a_end, b, b_end);
							loop.setName(newName);
							this._data.put(newName, loop);
						}else if(loop.getPaScoreAvg()> this._data.get(newName).getPaScoreAvg()){
							loop.setCoordinates(a, a_end, b, b_end);
							loop.setName(newName);
							this._data.put(newName, loop);
						}
					}
				}
			}	 
		}
		return this._data;
	}

	/**
	 * getter of m_data
	 * @return HashMap of loops
	 */
	public HashMap<String,Loop> getData(){ return this._data;}
	
	/**
	 * setter of m_data
	 * @param data 
	 */
	public void setData(HashMap<String,Loop> data){ this._data = data;}
	
	
	
	/*
	 * Strip idea
	 * 
	 * Strip stripY = loop.getStripY();
	
	if(stripY != null){
		stripY.setName("Y_"+a);
		stripY.setX((stripY.getX()+(index*step))*resolution);
		stripY.setXEnd((stripY.getXEnd()+(index*step))*resolution);					
		stripY.setYEnd((stripY.getYEnd()+(index*step))*resolution);
		stripY.setY((stripY.getY()+(index*step))*resolution);
	}*/
}