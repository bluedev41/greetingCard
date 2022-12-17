package junglee.greetings.imageSetup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import junglee.greetings.mail.SendMail;
import junglee.greetings.model.UserFormData;

@Component
public class AnniversaryImageSetup {

	@Autowired
	private SendMail sendMail;

	SimpleDateFormat yearFormatter = new SimpleDateFormat("YYYY");
	Date date = new Date();

	public void overlayImagesMutipleAnniversaryN(List<UserFormData> user) {

		int n = user.size();

		String name[] = new String[n]; // array of names of employees
		BufferedImage fgImage[] = new BufferedImage[n];// array of images
		List<String> cc = new ArrayList<>(); // email list to use in cc of email;
		String emailSubject = "Congratulations"; // Subject for email
		List<String> team = new ArrayList<>();
		List<String> work = new ArrayList<>();

		int index = 0;
		while (user.size() > 0) {
			name[index] = user.get(0).getFullname();
			fgImage[index] = readImage("src/main/resources/static/image/" + user.get(0).getEmail() + "photo.jpg");
			cc.add(user.get(0).getEmail());
			team.add(user.get(0).getDepartment());
			work.add("if anything to write about persion if anything to write about persion if anything to write about persion if anything to write about persion");
			emailSubject = emailSubject + ", " + user.get(0).getFullname();
			index++;
			user.remove(0);
		}
		BufferedImage bgImageTitle = readImage("imageTemplates/birthday_image_title.png");
		BufferedImage bgImageBase = readImage("imageTemplates/baseimage.png");
		BufferedImage bgImageEnd = readImage("imageTemplates/lower_image.png");

		int baseNumber = n / 2 + n % 2;

		BufferedImage bufferedImage = new BufferedImage(bgImageBase.getWidth(),
				bgImageTitle.getHeight() + baseNumber * bgImageBase.getHeight() + bgImageEnd.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = bufferedImage.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(bgImageTitle, 0, 0, null);

//       String team="(Engineering)";
		// String work="The function returns maximum of two numbers. The datatype will
		// be the same as that of the arguments.";
		int j = 0;
		for (int i = 0; i < baseNumber; i++) {
			g.drawImage(bgImageBase, 0, bgImageTitle.getHeight() + i * bgImageBase.getHeight(), null);
			if (n == 1) {
				g.setColor(Color.white);
				g.fillRect(bgImageBase.getWidth() / 2 - 225,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 275, 450,
						550);
				int[] a = resizeAnniversay(fgImage[j]);
				g.drawImage(fgImage[j], bgImageBase.getWidth() / 2 - a[0] / 2,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - a[1] / 2,
						a[0], a[1], null);
				g.setFont(new Font("Serif", Font.BOLD, 50));
				g.setColor(Color.red);
				g.drawString(name[j], bgImageBase.getWidth() / 2 - name[j].length() / 2 * 29,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 225);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.setColor(Color.black);
				g.drawString(team.get(j), bgImageBase.getWidth() / 2 - team.get(j).length() / 2 * 22,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 190);
				g.setFont(new Font("Serif", Font.BOLD, 20));
				g.setColor(Color.black);
				ArrayList<String> abc = stringdivide(work.get(j));
				for (int k = 0; k < abc.size(); k++) {
					g.drawString(abc.get(k), bgImageBase.getWidth() / 2 - abc.get(k).length() / 2 * 12,
							bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 + 190
									+ k * 25);
				}
				j++;
			} else {
				g.setColor(Color.white);
				g.fillRect(bgImageBase.getWidth() / 4 - 225,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 275, 450,
						550);
				int[] a = resizeAnniversay(fgImage[j]);
				g.drawImage(fgImage[j], bgImageBase.getWidth() / 4 - a[0] / 2,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - a[1] / 2,
						a[0], a[1], null);
				g.setFont(new Font("Serif", Font.BOLD, 50));
				g.setColor(Color.red);
				g.drawString(name[j], bgImageBase.getWidth() / 4 - name[j].length() / 2 * 29,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 225);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.setColor(Color.black);
				g.drawString(team.get(j), bgImageBase.getWidth() / 4 - team.get(j).length() / 2 * 22,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 190);
				g.setFont(new Font("Serif", Font.BOLD, 20));
				g.setColor(Color.black);
				ArrayList<String> abc = stringdivide(work.get(j));
				for (int k = 0; k < abc.size(); k++) {
					g.drawString(abc.get(k), bgImageBase.getWidth() / 4 - abc.get(k).length() / 2 * 12,
							bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 + 190
									+ k * 25);
				}
				j++;
				g.setColor(Color.white);
				g.fillRect(3 * bgImageBase.getWidth() / 4 - 225,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 275, 450,
						550);
				a = resizeAnniversay(fgImage[j]);
				g.drawImage(fgImage[j], 3 * bgImageBase.getWidth() / 4 - a[0] / 2,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - a[1] / 2,
						a[0], a[1], null);
				g.setFont(new Font("Serif", Font.BOLD, 50));
				g.setColor(Color.red);
				g.drawString(name[j], 3 * bgImageBase.getWidth() / 4 - name[j].length() / 2 * 29,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 225);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.setColor(Color.black);
				g.drawString(team.get(j), 3 * bgImageBase.getWidth() / 4 - team.get(j).length() / 2 * 22,
						bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 - 190);
				g.setFont(new Font("Serif", Font.BOLD, 20));
				g.setColor(Color.black);
				abc = stringdivide(work.get(j));
				for (int k = 0; k < abc.size(); k++) {
					g.drawString(abc.get(k), 3 * bgImageBase.getWidth() / 4 - abc.get(k).length() / 2 * 12,
							bgImageTitle.getHeight() + i * bgImageBase.getHeight() + bgImageBase.getHeight() / 2 + 190
									+ k * 25);
				}
				j++;
				n -= 2;
			}
		}

		g.drawImage(bgImageEnd, 0, bgImageTitle.getHeight() + baseNumber * bgImageBase.getHeight(), null);
		g.dispose();
		/*
		 * Image generated is bufferedImage converted into file type and mail function
		 * is called
		 */
		File outputfile = bufferedImageToFile(bufferedImage);
		emailSubject = emailSubject + "for completing 1 years";
		sendMail.sendWithAttachement("rohitsalwan2020@gmail.com", "pram_be16@thapar.edu", cc, emailSubject, outputfile);

		/*
		 * save image in local folder
		 */
		// writeImage(bufferedImage,
		// "src/main/resources/static/GreetingImages/anniversary.jpg", "jpg");
		// System.out.println("image saved in local folder");

	}

//    private void writeImage(BufferedImage img, String fileLocation, String extension) {
//        try {
//            BufferedImage bi = img;
//            File outputfile = new File(fileLocation);
//            ImageIO.write(bi, extension, outputfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//}

	private File bufferedImageToFile(BufferedImage bufferedImage) {
		File outputfile = new File("anniversary.jpg");
		try {
			ImageIO.write(bufferedImage, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputfile;
	}

	private ArrayList<String> stringdivide(String a) {
		ArrayList<String> ans = new ArrayList<String>();
		int start = 0;
		// int pointer=0;
		int spacepointer = 0;
		for (int i = 0; i < a.length(); i++) {
			if (i - start >= 33) {
				if (a.charAt(i) == ' ') {
					ans.add(a.substring(start, i));
					start = i;
					spacepointer = i;
				} else {
					ans.add(a.substring(start, spacepointer));
					start = spacepointer + 1;
				}
			} else if (i == a.length() - 1) {
				ans.add(a.substring(start, a.length()));
			} else {
				if (a.charAt(i) == ' ') {
					spacepointer = i;
				}
			}
		}
		return ans;
	}

	private int[] resizeAnniversay(BufferedImage img) {
		int a = Math.max(img.getHeight(), img.getWidth());
		int[] ndimention = new int[2];
		if (a == img.getHeight() && a > 350) {
			ndimention[0] = (350 * img.getWidth()) / img.getHeight();
			ndimention[1] = 350;

		} else if (a == img.getWidth() && a > 300) {
			ndimention[0] = 300;
			ndimention[1] = (300 * img.getHeight()) / img.getWidth();
		} else {
			ndimention[0] = img.getWidth();
			ndimention[1] = img.getHeight();
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