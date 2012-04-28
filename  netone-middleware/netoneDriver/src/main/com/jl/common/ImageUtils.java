package com.jl.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ͼ��������<BR>
 * ����ImageUtil.outLogo(images,out,200,100);//��ʾ200*100������ͼ
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Dec 25, 2009 create by Don
 * @history
 */
public class ImageUtils {

	protected final transient Log log = LogFactory.getLog(getClass());

	/**
	 * 
	 * @param source
	 *            ͼ��������
	 * @param out
	 *            ͼ�������
	 * @param dwidth
	 *            ͼ����
	 * @param dheight
	 *            ͼ��߶�
	 * @throws Exception
	 */
	public static void outImage(byte[] source, OutputStream out, int dwidth,
			int dheight) throws Exception {
		BufferedInputStream stream = new BufferedInputStream(
				(new ByteArrayInputStream(source)), 8092);// ��������
		Image src = javax.imageio.ImageIO.read(stream);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		int towidth, toheight;
		if (width > dwidth || height > dheight) {
			if (((float) width / dwidth) >= ((float) height / dheight)) {
				towidth = dwidth;
				toheight = (height * dwidth) / width;
			} else {
				toheight = dheight;
				towidth = (width * dheight) / height;
			}
		} else {
			towidth = width;
			toheight = height;
		}

		BufferedImage tag = new BufferedImage(towidth, toheight,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(src, 0, 0, towidth, toheight, null);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
	}

	/**
	 * �ж��ļ��Ƿ�ͼƬ��ʽ(֧��GIF,JPG,BMP,JPEG,PNG)
	 * 
	 * @param filename
	 * @return boolean
	 */
	public static boolean isImagePattern(String fileName) {
		String str = fileName.substring(fileName.lastIndexOf("."), fileName
				.length());

		String regStr = "[.][Gg][Ii][Ff]|[.][Jj][Pp][Gg]|[.][Bb][Mm][Pp]|[.][Jj][Pp][Ee][Gg]|[.][Pp][Nn][Gg]";

		Pattern pattern = Pattern.compile(regStr);

		Matcher matcher = pattern.matcher(str.toLowerCase());

		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ͼƬ����(ͼƬ�ȱ�������Ϊָ����С���հײ����԰�ɫ���)
	 * 
	 * @param srcBufferedImage
	 *            ԴͼƬ
	 * @param destFile
	 *            ���ź��ͼƬ�ļ�
	 */
	public static void zoom(BufferedImage srcBufferedImage, File destFile, int destHeight, int destWidth) {
		try {
			int imgWidth = destWidth;
			int imgHeight = destHeight;
			int srcWidth = srcBufferedImage.getWidth();
			int srcHeight = srcBufferedImage.getHeight();
			if (srcHeight >= srcWidth) {
				imgWidth = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
			} else {
				imgHeight = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
			}
			BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, destWidth, destHeight);
			graphics2D.drawImage(srcBufferedImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), (destWidth / 2) - (imgWidth / 2), (destHeight / 2) - (imgHeight / 2), null);
			graphics2D.dispose();
			ImageIO.write(destBufferedImage, "JPEG", destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡͼƬ�ļ�������.
	 * 
	 * @param imageFile
	 *            ͼƬ�ļ�����.
	 * @return ͼƬ�ļ�����
	 */
	public static String getImageFormatName(File imageFile) {
		try {
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
			if (!iterator.hasNext()) {
				return null;
			}
			ImageReader imageReader = (ImageReader) iterator.next();
			imageInputStream.close();
			return imageReader.getFormatName().toLowerCase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String filename = "fff.jpeg";
		System.out.println(ImageUtils.isImagePattern(filename));
	}
}
