package br.com.tracevia.webapp.model.dms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvailablePictures {
	
	private static AvailablePictures instance = null;

	private final HashMap<Integer, Picture> picturesAsMap;
	
	private final List<Picture> pictures;

	public AvailablePictures () {
				 
		final List<Integer> picturesID = new ArrayList<Integer>();

		picturesID.add(2);
		/*picturesID.add(2);
		picturesID.add(3);
		picturesID.add(4);
		picturesID.add(5);
		picturesID.add(6);*/
		
		picturesAsMap = new HashMap<Integer, Picture>();
		pictures = new ArrayList<Picture>();

		for (int pictureId : picturesID) {
			 
			Picture picture = new Picture();
			
			picture.setId(pictureId);			
			picture.setImage("/RESOURCES/images/pictures/" + pictureId+ ".png");

			pictures.add(picture);
			
			picturesAsMap.put(picture.getId(), picture);		
			
		}
	}

	public static AvailablePictures getInstance() {
		if (instance == null) {
			instance = new AvailablePictures();
		}

		return instance;
	}

	public final List<Picture> getPictures() {
		return pictures;
	}

	public Picture getPictureForId(int id) {
		return picturesAsMap.get(id);
	}

}
