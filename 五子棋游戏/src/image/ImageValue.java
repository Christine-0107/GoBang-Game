package image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageValue {
	public static BufferedImage black=null;
	public static BufferedImage white=null;
	public static BufferedImage background=null;
	private static String path1="/image/black.png";
	private static String path2="/image/white.png";
	private static String path3="/image/background.jpg";
	
	public static void initImage(){
		try{
			System.out.println("ok");
			white=ImageIO.read(ImageValue.class.getResource(path2));
			black=ImageIO.read(ImageValue.class.getResource(path1));
			background=ImageIO.read(ImageValue.class.getResource(path3));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
