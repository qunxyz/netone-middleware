package com.jl.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ͼƬ�������ࣺ<br>
 * ���ܣ�����ͼ���и�ͼ��ͼ������ת������ɫת�ڰס�����ˮӡ��ͼƬˮӡ��
 * 
 * @author Administrator
 */
public class ImageUtils {

	/**
	 * ���ֳ�����ͼƬ��ʽ
	 */
	public static String IMAGE_TYPE_GIF = "gif";// ͼ�ν�����ʽ
	public static String IMAGE_TYPE_JPG = "jpg";// ������Ƭר����
	public static String IMAGE_TYPE_JPEG = "jpeg";// ������Ƭר����
	public static String IMAGE_TYPE_BMP = "bmp";// Ӣ��Bitmap��λͼ���ļ�д������Windows����ϵͳ�еı�׼ͼ���ļ���ʽ
	public static String IMAGE_TYPE_PNG = "png";// ����ֲ����ͼ��
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop��ר�ø�ʽPhotoshop

	/**
	 * ������ڣ����ڲ���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1-����ͼ��
		// ����һ������������
		ImageUtils.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);// ����OK
		// �����������߶ȺͿ������
		ImageUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300, true);// ����OK

		// 2-�и�ͼ��
		// ����һ����ָ���������Ϳ���и�
		ImageUtils.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400);// ����OK
		// ��������ָ����Ƭ������������
		ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2);// ����OK
		// ��������ָ����Ƭ�Ŀ�Ⱥ͸߶�
		ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300);// ����OK

		// 3-ͼ������ת����
		ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");// ����OK

		// 4-��ɫת�ڰף�
		ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");// ����OK

		// 5-��ͼƬ�������ˮӡ��
		// ����һ��
		ImageUtils.pressText("����ˮӡ����", "e:/abc.jpg", "e:/abc_pressText.jpg",
				"����", Font.BOLD, Color.white, 80, 0, 0, 0.5f);// ����OK
		// ��������
		ImageUtils.pressText2("��Ҳ��ˮӡ����", "e:/abc.jpg", "e:/abc_pressText2.jpg",
				"����", 36, Color.white, 80, 0, 0, 0.5f);// ����OK

		// 6-��ͼƬ���ͼƬˮӡ��
		ImageUtils.pressImage("e:/abc2.jpg", "e:/abc.jpg",
				"e:/abc_pressImage.jpg", 0, 0, 0.5f);// ����OK
	}

	/**
	 * ����ͼ�񣨰��������ţ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ļ���ַ
	 * @param result
	 *            ���ź��ͼ���ַ
	 * @param scale
	 *            ���ű���
	 * @param flag
	 *            ����ѡ��:true �Ŵ�; false ��С;
	 */
	public final static void scale(String srcImageFile, String result,
			int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // �����ļ�
			int width = src.getWidth(); // �õ�Դͼ��
			int height = src.getHeight(); // �õ�Դͼ��
			if (flag) {// �Ŵ�
				width = width * scale;
				height = height * scale;
			} else {// ��С
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // ������С���ͼ
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// ������ļ���
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͼ�񣨰��߶ȺͿ�����ţ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ļ���ַ
	 * @param result
	 *            ���ź��ͼ���ַ
	 * @param height
	 *            ���ź�ĸ߶�
	 * @param width
	 *            ���ź�Ŀ��
	 * @param bb
	 *            ��������ʱ�Ƿ���Ҫ���ף�trueΪ����; falseΪ������;
	 */
	public final static void scale2(String srcImageFile, String result,
			int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // ���ű���
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			// �������
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform
						.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// ����
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼ���и�(��ָ���������Ϳ���и�)
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param result
	 *            ��Ƭ���ͼ���ַ
	 * @param x
	 *            Ŀ����Ƭ�������X
	 * @param y
	 *            Ŀ����Ƭ�������Y
	 * @param width
	 *            Ŀ����Ƭ���
	 * @param height
	 *            Ŀ����Ƭ�߶�
	 */
	public final static void cut(String srcImageFile, String result, int x,
			int y, int width, int height) {
		try {
			// ��ȡԴͼ��
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // Դͼ���
			int srcHeight = bi.getWidth(); // Դͼ�߶�
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// �ĸ������ֱ�Ϊͼ���������Ϳ��
				// ��: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // �����и���ͼ
				g.dispose();
				// ���Ϊ�ļ�
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼ���иָ����Ƭ��������������
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param descDir
	 *            ��ƬĿ���ļ���
	 * @param rows
	 *            Ŀ����Ƭ������Ĭ��2�������Ƿ�Χ [1, 20] ֮��
	 * @param cols
	 *            Ŀ����Ƭ������Ĭ��2�������Ƿ�Χ [1, 20] ֮��
	 */
	public final static void cut2(String srcImageFile, String descDir,
			int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20)
				rows = 2; // ��Ƭ����
			if (cols <= 0 || cols > 20)
				cols = 2; // ��Ƭ����
			// ��ȡԴͼ��
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // Դͼ���
			int srcHeight = bi.getWidth(); // Դͼ�߶�
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // ÿ����Ƭ�Ŀ��
				int destHeight = srcHeight; // ÿ����Ƭ�ĸ߶�
				// ������Ƭ�Ŀ�Ⱥ͸߶�
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// ѭ��������Ƭ
				// �Ľ����뷨:�Ƿ���ö��̼߳ӿ��и��ٶ�
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// �ĸ������ֱ�Ϊͼ���������Ϳ��
						// ��: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // ������С���ͼ
						g.dispose();
						// ���Ϊ�ļ�
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼ���иָ����Ƭ�Ŀ�Ⱥ͸߶ȣ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param descDir
	 *            ��ƬĿ���ļ���
	 * @param destWidth
	 *            Ŀ����Ƭ��ȡ�Ĭ��200
	 * @param destHeight
	 *            Ŀ����Ƭ�߶ȡ�Ĭ��150
	 */
	public final static void cut3(String srcImageFile, String descDir,
			int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // ��Ƭ���
			if (destHeight <= 0)
				destHeight = 150; // ��Ƭ�߶�
			// ��ȡԴͼ��
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // Դͼ���
			int srcHeight = bi.getWidth(); // Դͼ�߶�
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int cols = 0; // ��Ƭ��������
				int rows = 0; // ��Ƭ��������
				// ������Ƭ�ĺ������������
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// ѭ��������Ƭ
				// �Ľ����뷨:�Ƿ���ö��̼߳ӿ��и��ٶ�
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// �ĸ������ֱ�Ϊͼ���������Ϳ��
						// ��: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // ������С���ͼ
						g.dispose();
						// ���Ϊ�ļ�
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼ������ת����GIF->JPG��GIF->PNG��PNG->JPG��PNG->GIF(X)��BMP->PNG
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param formatName
	 *            ������ʽ����ʽ���Ƶ� String����JPG��JPEG��GIF��
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ɫתΪ�ڰ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼƬ�������ˮӡ
	 * 
	 * @param pressText
	 *            ˮӡ����
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param fontName
	 *            ˮӡ����������
	 * @param fontStyle
	 *            ˮӡ��������ʽ
	 * @param color
	 *            ˮӡ��������ɫ
	 * @param fontSize
	 *            ˮӡ�������С
	 * @param x
	 *            ����ֵ
	 * @param y
	 *            ����ֵ
	 * @param alpha
	 *            ͸���ȣ�alpha �����Ƿ�Χ [0.0, 1.0] ֮�ڣ������߽�ֵ����һ����������
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// ��ָ���������ˮӡ����
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));// ������ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼƬ�������ˮӡ
	 * 
	 * @param pressText
	 *            ˮӡ����
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param fontName
	 *            ��������
	 * @param fontStyle
	 *            ������ʽ
	 * @param color
	 *            ������ɫ
	 * @param fontSize
	 *            �����С
	 * @param x
	 *            ����ֵ
	 * @param y
	 *            ����ֵ
	 * @param alpha
	 *            ͸���ȣ�alpha �����Ƿ�Χ [0.0, 1.0] ֮�ڣ������߽�ֵ����һ����������
	 */
	public final static void pressText2(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// ��ָ���������ˮӡ����
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼƬ���ͼƬˮӡ
	 * 
	 * @param pressImg
	 *            ˮӡͼƬ
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param x
	 *            ����ֵ�� Ĭ�����м�
	 * @param y
	 *            ����ֵ�� Ĭ�����м�
	 * @param alpha
	 *            ͸���ȣ�alpha �����Ƿ�Χ [0.0, 1.0] ֮�ڣ������߽�ֵ����һ����������
	 */
	public final static void pressImage(String pressImg, String srcImageFile,
			String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// ˮӡ�ļ�
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);
			// ˮӡ�ļ�����
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����text�ĳ��ȣ�һ�������������ַ���
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	

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
}