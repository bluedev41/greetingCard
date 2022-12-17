package junglee.greetings.imageSetup;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import junglee.greetings.mail.SendMail;
import junglee.greetings.model.UserFormData;

@Component
public class BirthdayImageSetup {
	

	@Autowired
	private SendMail sendMail;
	
	public void overlayImagesMutipleBDayN(List<UserFormData> user) {
		
		int n=user.size(); //Number of employees having birthday today
		
	    String name[] = new String[n]; // all names	    
	    BufferedImage fgImage[] = new BufferedImage[n]; //all images
	    List<String> cc = new ArrayList<>(); // email list to use in cc of email;
		String emailSubject = "Happy Birthday"; //Subject for email
	    /*
	     * finding all employee name and photo into array for future use
	     */
	    int index = 0;
	    while(user.size() > 0) {
	    	name[index] = user.get(0).getFullname();
	    	fgImage[index] = readImage("src/main/resources/static/image/"+user.get(0).getEmail()+"photo.jpg");
	    	cc.add(user.get(0).getEmail());
	    	emailSubject = emailSubject +", "+user.get(0).getFullname();
	    	index++;
	    	user.remove(0);
	    }
		    BufferedImage bgImageTitle = readImage("imageTemplates/birthday_image_title.png");
		    BufferedImage bgImageBase = readImage("imageTemplates/baseimage.png");
		    BufferedImage bgImageEnd = readImage("imageTemplates/lower_image.png");
		    int baseNumber=n/2+n%2;
		    
		    BufferedImage bufferedImage = new BufferedImage(bgImageBase.getWidth(), bgImageTitle.getHeight()+baseNumber*bgImageBase.getHeight()+bgImageEnd.getHeight(), BufferedImage.TYPE_INT_RGB);
		   
		        Graphics2D g = bufferedImage.createGraphics();
		        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);    
		        g.drawImage(bgImageTitle, 0, 0, null);    
		        int j=0;
		        for(int i=0;i<baseNumber;i++) {
		        g.drawImage(bgImageBase, 0, bgImageTitle.getHeight()+i*bgImageBase.getHeight(), null);
		        if(n==1) {
		        int[] a=resizeBday(fgImage[j]);
		        g.drawImage(fgImage[j], bgImageBase.getWidth()/2-a[0]/2, bgImageTitle.getHeight()+i*bgImageBase.getHeight()+bgImageBase.getHeight()/2-a[1]/2,a[0],a[1], null);
		        g.setFont(new Font("Serif", Font.BOLD, 50));
		        g.drawString(name[j], bgImageBase.getWidth()/2-name[j].length()/2*29, bgImageTitle.getHeight()+i*bgImageBase.getHeight()+bgImageBase.getHeight()/2+245);
		        j++;
		        }else {
		        int[] a=resizeBday(fgImage[j]);
		        g.drawImage(fgImage[j], bgImageBase.getWidth()/4-a[0]/2, bgImageTitle.getHeight()+i*bgImageBase.getHeight()+bgImageBase.getHeight()/2-a[1]/2,a[0],a[1], null);
		        g.setFont(new Font("Serif", Font.BOLD, 50));
		        g.drawString(name[j], bgImageBase.getWidth()/4-name[j].length()/2*29, bgImageTitle.getHeight()+i*bgImageBase.getHeight()+bgImageBase.getHeight()/2+245);
		        j++;
		        a=resizeBday(fgImage[j]);
		        g.drawImage(fgImage[j], 3*bgImageBase.getWidth()/4-a[0]/2, bgImageTitle.getHeight()+i*bgImageBase.getHeight()+bgImageBase.getHeight()/2-a[1]/2,a[0],a[1], null);
		        g.drawString(name[j], 3*bgImageBase.getWidth()/4-name[j].length()/2*29, bgImageTitle.getHeight()+i*bgImageBase.getHeight()+bgImageBase.getHeight()/2+245);
		        j++;
		        n-=2;
		        }
		        }
		        g.drawImage(bgImageEnd, 0, bgImageTitle.getHeight()+baseNumber*bgImageBase.getHeight(), null);
		        g.dispose();
               
		        //writeImage(bufferedImage, "src/main/resources/static/GreetingImages/birthday.jpg", "jpg");
		        //System.out.println("image saved in local folder");
		        
		        File outputfile = bufferedImageToFile(bufferedImage);
				emailSubject = emailSubject+" :)";
				sendMail.sendWithAttachement("rohitsalwan2020@gmail.com","pram_be16@thapar.edu", cc, emailSubject, outputfile);	        
		        
	}
	
//	private void writeImage(BufferedImage img, String fileLocation, String extension) {
//        try {
//            BufferedImage bi = img;
//            File outputfile = new File(fileLocation);
//            ImageIO.write(bi, extension, outputfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

	private File bufferedImageToFile(BufferedImage bufferedImage) {
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputfile;
	}

	private int[] resizeBday(BufferedImage img) {
		int a=Math.max(img.getHeight(), img.getWidth());  
	    int[] ndimention=new int[2];
	    if(a==img.getHeight() && a>400){
	    ndimention[0]=(400*img.getWidth())/img.getHeight();
	    ndimention[1]=400;
	   
	    }else if(a==img.getWidth() && a>400) {
	    ndimention[0]=400;
	    ndimention[1]=(400*img.getHeight())/img.getWidth();
	    }else {
	    ndimention[0]=img.getWidth();
	    ndimention[1]=img.getHeight();
	    }
	    return ndimention;
	}

	private BufferedImage readImage(String fileLocation) {
		BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
	}

}
