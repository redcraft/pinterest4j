package ru.redcraft.pinterest4j;

import java.io.File;

public interface NewPin {

	 String getDescription();
	
	 double getPrice();

	 String getLink();

	 String getImageURL();

	 File getImageFile();
}
