package fileSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.color.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.imageio.*;
import javax.imageio.stream.*;


class ImgArea extends Canvas{ 
	Image orImg;
	BufferedImage orBufferedImage;
	BufferedImage bimg; 
	BufferedImage temp_bimg; 
	float e;
	float radian;
	Dimension ds;
	int mX;
	int mY;
	int x;
	int y;
	int w;
	int h;
	static boolean imageLoaded;
	boolean actionSlided;
	boolean actionResized;
	boolean actionCompressed;
	boolean actionClipped;
	boolean actionRotated;
	boolean actionDraw;
	boolean actionClip;
	boolean drawn;
	boolean cropping;
	MediaTracker mt;
	static Color c;
	Color colorTextDraw;
	Robot rb;
	boolean dirHor;
	String imgFileName;
	String fontName;
	int fontSize;
	String textToDraw;
	public ImgArea(){

		addMouseListener(new Mousexy()); 
		addKeyListener(new KList());
		addMouseListener(new Mouseclip());

		try{
			rb=new Robot(); 
		}catch(AWTException e){}

		ds=getToolkit().getScreenSize(); 
		mX=(int)ds.getWidth()/2; 
		mY=(int)ds.getHeight()/2;

	}

	public void paint(Graphics g){
		Graphics2D g2d=(Graphics2D)g; 
		if(imageLoaded){
			if(actionSlided || actionResized || actionClipped || actionRotated || drawn ){
				x=mX-bimg.getWidth()/2;
				y=mY-bimg.getHeight()/2;
				w=mX+bimg.getWidth()/2;
				h=mY+bimg.getHeight()/2;
				g2d.translate(x,y); 
				g2d.drawImage(bimg,0,0,null); 

			}

			else{
				x=mX-orBufferedImage.getWidth()/2;
				y=mY-orBufferedImage.getHeight()/2;
				w=mX+orBufferedImage.getWidth()/2;
				h=mY+orBufferedImage.getHeight()/2;
				g2d.translate(x,y);
				g2d.drawImage(orBufferedImage,0,0,null); 
			}
		}
		g2d.dispose();

	}



	public void setTempImage(){
		if(actionSlided || actionResized || actionClipped || actionRotated || drawn) {
			temp_bimg = bimg;
		}
		else temp_bimg = orBufferedImage;
	}

	public void prepareImage(String filename){
		initialize();
		try{

			mt=new MediaTracker(this);    
			orImg=Toolkit.getDefaultToolkit().getImage(filename); 
			mt.addImage(orImg,0);
			mt.waitForID(0); 

			int width=orImg.getWidth(null);
			int height=orImg.getHeight(null);

			orBufferedImage=createBufferedImageFromImage(orImg,width,height,false);

			bimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
			imageLoaded=true; 
		}catch(Exception e){System.exit(-1);}
	}

	class Mousexy extends MouseAdapter{

		public void mousePressed(MouseEvent e){
			Color color=rb.getPixelColor(e.getX(),e.getY()); 
			try{    
				setColor(color); 
				if(actionDraw){ 
					if(actionSlided || actionResized || actionClipped || actionRotated || drawn)
						addTextToImage(e.getX()-x,e.getY()-y, bimg);
					else  
						addTextToImage(e.getX()-x,e.getY()-y, orBufferedImage);
				}

			}catch(Exception ie){}
		}
	}
	class Mouseclip extends MouseAdapter{
		double startX, startY;
		double endX, endY;
		BufferedImage bi;

		public void mousePressed(MouseEvent e){
			this.startX = e.getX()-x;
			this.startY = e.getY()-y;
		}
		public void  mouseReleased(MouseEvent e){

			this.endX = e.getX()-x;
			this.endY = e.getY()-y;
			if(actionClip){
				if (JOptionPane.showConfirmDialog(null, "이 크기로 클립핑 하시겠습니까?", "이미지 변경 경고", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE)

						==

						JOptionPane.YES_OPTION){


					setTempImage();
					makeClipImage((int)startX, (int)startY, (int)endX, (int)endY, temp_bimg);
					repaint();
					actionClipped=true;
					cropping=false;
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			setTempImage();
			makeClipBox((int)startX, (int)startY, (int)endX, (int)endY, temp_bimg);

		}

	}

	//안되네 ㅡㅡ;
	public void makeClipBox(int x1, int y1, int x2, int y2,BufferedImage img){
		BufferedImage bi=(BufferedImage)createImage(img.getWidth(),img.getHeight());
		Graphics2D  g2d=(Graphics2D)bi.createGraphics();
		if(cropping)
		{
			g2d.setColor(Color.RED);
			g2d.drawRect(x1,y1,x2-x1,y2-y1);
			g2d.dispose();
		}
	}

	public void makeClipImage(int x1, int y1, int x2, int y2, BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D  g2d=(Graphics2D)bi.createGraphics();
		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0,img.getWidth(),img.getHeight());
		g2d.setComposite(AlphaComposite.Src);
		g2d.drawImage(img,x1,y1,x2,y2,x1,y1,x2,y2, this);
		bimg=bi;
	}


	class KList extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode()==27){
				actionClip=false;
				actionDraw=false;
				textToDraw="";
				fontName="";
				fontSize=0;
			}
		}
	}

	public void addTextToImage(int x,int y, BufferedImage img){
		BufferedImage bi=(BufferedImage)createImage(img.getWidth(),img.getHeight());
		Graphics2D  g2d=(Graphics2D)bi.createGraphics();
		g2d.setFont(new Font(fontName,Font.PLAIN,fontSize));
		g2d.setPaint(colorTextDraw);
		g2d.drawImage(img,0,0,null);
		g2d.drawString(textToDraw,x,y);
		bimg=bi;
		drawn=true;
		g2d.dispose();
		repaint(); 
	}
	public void setColor(Color color){
		c=color;   
	}

	public void setImgFileName(String fname){
		imgFileName=fname;
	}

	public void initialize(){
		actionClip=false;
		imageLoaded=false; 
		actionSlided=false;
		actionResized=false;
		actionCompressed=false;
		actionClipped=false;
		actionRotated=false;
		actionDraw=false;
		drawn=false;
		dirHor=false;
		cropping=false;
		c=null;
		radian=0.0f;
		e=0.0f;
	}


	public void reset(){
		if(imageLoaded){
			prepareImage(imgFileName);
			repaint();
		}
	}



	public void makeImageRightRotate(BufferedImage image,int w,int h){

		BufferedImage bi=(BufferedImage)createImage(w,h);
		Graphics2D  g2d=(Graphics2D)bi.createGraphics(); 
		radian=(float)Math.PI/2; 
		g2d.translate(w/2,h/2); 
		g2d.rotate(radian);
		g2d.translate(-h/2,-w/2);
		g2d.drawImage(image,0,0,null); 
		bimg=bi; 
		g2d.dispose();  


	}
	public void makeImageLeftRotate(BufferedImage image,int w,int h){

		BufferedImage bi=(BufferedImage)createImage(w,h);
		Graphics2D  g2d=(Graphics2D)bi.createGraphics(); 
		radian=(float)Math.PI/2; 
		g2d.translate(w/2,h/2); 
		g2d.rotate(-radian); 
		g2d.translate(-h/2,-w/2); 
		g2d.drawImage(image,0,0,null);
		bimg=bi; 
		g2d.dispose();  


	}

	public void rotateRightImage(){
		BufferedImage bi;
		if(actionSlided || actionResized || actionClipped || actionRotated || drawn){
			bi=bimg;     
		}
		else{
			bi=orBufferedImage;
		}
		makeImageRightRotate(bi,bi.getHeight(),bi.getWidth());
		actionRotated=true; 
		repaint();
	}
	public void rotateLeftImage(){
		BufferedImage bi;

		if(actionSlided || actionResized || actionClipped || actionRotated || drawn){
			bi=bimg;     
		}
		else{
			bi=orBufferedImage;
		}
		makeImageLeftRotate(bi,bi.getHeight(),bi.getWidth());
		actionRotated=true;
		repaint();

	}

	public  void makeCompression(File outFileName){
		try{
			ImageWriter imgWriter =(ImageWriter) ImageIO.getImageWritersByFormatName("jpg").next();
			ImageOutputStream imgOutStrm = ImageIO.createImageOutputStream(outFileName);
			imgWriter.setOutput(imgOutStrm);
			IIOImage iioImg;
			if(actionSlided || actionResized){
				iioImg = new IIOImage(bimg, null,null);
			}
			else{    
				iioImg = new IIOImage(orBufferedImage, null,null);
			}

			ImageWriteParam jpgWriterParam = imgWriter.getDefaultWriteParam();
			jpgWriterParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			jpgWriterParam.setCompressionQuality(0.7f);
			imgWriter.write(null, iioImg, jpgWriterParam);
			imgOutStrm.close();
			imgWriter.dispose();
		}catch(Exception e){}

	}

	public void resizeImage(int w,int h){
		BufferedImage bi=(BufferedImage)createImage(w,h);
		Graphics2D g2d=(Graphics2D)bi.createGraphics();
		if(actionSlided || actionClipped || actionRotated ||drawn)
			g2d.drawImage(bimg,0,0,w,h,null);
		else
			g2d.drawImage(orImg,0,0,w,h,null);
		bimg=bi;
		g2d.dispose();

	}
	public void filterImage(){
		float[] elements = {0.0f, 1.0f, 0.0f, -1.0f,e,1.0f,0.0f,0.0f,0.0f}; 
		Kernel kernel = new Kernel(3, 3, elements);  
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); 
		bimg= new BufferedImage(bimg.getWidth(),bimg.getHeight(),BufferedImage.TYPE_INT_RGB);
		cop.filter(temp_bimg,bimg);

	}
	public void setValue(float value){ 
		e=value;
	}

	public void setActionSlided(boolean value ){ 
		actionSlided=value;
	} 
	public void setActionResized(boolean value ){ 
		actionResized=value;
	}    
	public void setActionCompressed(boolean value ){ 
		actionCompressed=value;
	} 
	public void setActionDraw(boolean value ){ 
		actionDraw=value;

	}

	public BufferedImage createBufferedImageFromImage(Image image, int width, int height, boolean tran)
	{ BufferedImage dest ;
	if(tran) 
		dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	else
		dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = dest.createGraphics();
	g2.drawImage(image, 0, 0, null);
	g2.dispose();
	return dest;
	}

	public void saveToFile(String filename){
		String ftype=filename.substring(filename.lastIndexOf('.')+1);
		try{

			if(actionCompressed)
				makeCompression(new File(filename));
			else if(actionSlided || actionResized || actionClipped || actionRotated || drawn)
				ImageIO.write(bimg,ftype,new File(filename));
		}catch(IOException e){System.out.println("Error in saving the file");}
	}
	public void setText(String text,String fName, int fSize, Color color){
		textToDraw=text;
		fontName=fName;
		fontSize=fSize;
		if(color==null)
			colorTextDraw=new Color(0,0,0);
		else
			colorTextDraw=color;
	}
}

class  PhotoEditor extends JFrame implements ActionListener{

	ImgArea ia;
	JFileChooser chooser; 

	JButton mopen;
	JButton msaveas;
	JButton msave;
	JButton mexit; 
	JButton mbright; 
	JButton mcompress; 
	JButton mresize;
	JButton mrightrotate;
	JButton mtransparent;
	JButton maddtext;
	JButton mcancel;
	JButton mleftrotate;

	ImageIcon icon_saveas;
	ImageIcon icon_bright;
	ImageIcon  icon_resize;
	ImageIcon icon_right90d;
	ImageIcon icon_left90d;
	ImageIcon icon_transparent;
	ImageIcon icon_cancel;
	ImageIcon icon_text;

	JLabel blank1;
	JLabel blank2;
	JLabel blank3;
	JLabel blank4;

	String filename;

	PhotoEditor(String path){

		ia=new ImgArea();
		Container cont=getContentPane();
		cont.add(ia,BorderLayout.CENTER );  
		setImage(path);

		mopen=new JButton("사진열기");
		mopen.addActionListener(this);

		msaveas=new JButton("사진저장");
		msaveas.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_saveas = new ImageIcon("img/saveas.png");
		msaveas.setIcon(icon_saveas);
		msaveas.setBorderPainted(false);
		msaveas.addActionListener(this);

		mbright=new JButton("사진 밝기조절");
		mbright.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_bright = new ImageIcon("img/brightness.png");
		mbright.setIcon(icon_bright);
		mbright.setBorderPainted(false);
		mbright.addActionListener(this);

		maddtext=new JButton("사진에 글자넣기");
		maddtext.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_text = new ImageIcon("img/text.png");
		maddtext.setIcon(icon_text);
		maddtext.setBorderPainted(false);
		maddtext.addActionListener(this);  

		mresize=new JButton("사진 크기조절");
		mresize.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_resize = new ImageIcon("img/resize.png");
		mresize.setIcon(icon_resize);
		mresize.setBorderPainted(false);
		mresize.addActionListener(this);

		mrightrotate=new JButton("사진 오른쪽 회전");
		mrightrotate.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_right90d = new ImageIcon("img/right90d.png");
		mrightrotate.setIcon(icon_right90d);
		mrightrotate.setBorderPainted(false);
		mrightrotate.addActionListener(this);

		mleftrotate=new JButton("사진 왼쪽 회전");
		mleftrotate.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_left90d = new ImageIcon("img/left90d.png");
		mleftrotate.setIcon(icon_left90d);
		mleftrotate.setBorderPainted(false);
		mleftrotate.addActionListener(this);		

		mtransparent=new JButton("사진 자르기");
		mtransparent.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_transparent = new ImageIcon("img/clip.png");
		mtransparent.setIcon(icon_transparent);
		mtransparent.setBorderPainted(false);
		mtransparent.addActionListener(this);

		mcancel=new JButton("처음상태로");
		mcancel.setFont(new Font("휴먼매직체", Font.PLAIN, 20));
		icon_cancel = new ImageIcon("img/cancel.png");
		mcancel.setIcon(icon_cancel);
		mcancel.setBorderPainted(false);
		mcancel.addActionListener(this);


		JPanel editpanel = new JPanel();
		blank1 = new JLabel("           ");
		blank2 = new JLabel("           ");
		blank3 = new JLabel("           ");
		blank4 = new JLabel("           ");

		cont.add(editpanel, BorderLayout.NORTH);
		editpanel.add(msaveas);
		editpanel.add(blank1);
		editpanel.add(maddtext);
		editpanel.add(blank2);
		editpanel.add(mleftrotate);
		editpanel.add(mrightrotate);
		editpanel.add(blank3);
		editpanel.add(mbright);
		editpanel.add(mresize);
		editpanel.add(mtransparent);
		editpanel.add(blank4);
		editpanel.add(mcancel);

		editpanel.setBackground(new Color(255,255,0));

		setTitle("사진 편집기");
		setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
		setVisible(true); 

		chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "gif","bmp","png");
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);

		enableSaving(false);
		ia.requestFocus();
	}

	public class ImageBrightness extends JFrame implements ChangeListener{
		JSlider slider;

		ImageBrightness(){
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					dispose();

				}
			});
			Container cont=getContentPane();  
			slider=new JSlider(-10,10,0); 
			slider.setEnabled(false);
			slider.addChangeListener(this);
			cont.add(slider,BorderLayout.CENTER); 
			slider.setEnabled(true);
			setTitle("사진 밝기 조절");
			setPreferredSize(new Dimension(300,100));
			setVisible(true);
			pack();
			enableSlider(false);
		}
		public void enableSlider(boolean enabled){
			slider.setEnabled(enabled);
		}
		public void stateChanged(ChangeEvent e){
			ia.setValue(slider.getValue()/10.0f);
			ia.setActionSlided(true);   
			ia.filterImage();
			ia.repaint();
			enableSaving(true);

		}

	}

	public class ImageResize extends JFrame implements ActionListener {
		JPanel panel;
		JTextField txtWidth;
		JTextField txtHeight;
		JButton btOK;
		ImageResize(){
			setTitle("사진 크기조절");
			setPreferredSize(new Dimension(400,100));

			btOK=new JButton("OK");
			btOK.setBackground(Color.BLACK);
			btOK.setForeground(Color.WHITE);  
			btOK.addActionListener(this);

			txtWidth=new JTextField(4);
			txtWidth.addKeyListener(new KeyList());
			txtHeight=new JTextField(4);
			txtHeight.addKeyListener(new KeyList());
			panel=new JPanel();
			panel.setLayout(new FlowLayout());
			panel.add(new JLabel("너비:"));
			panel.add(txtWidth);
			panel.add(new JLabel("높이:"));

			panel.add(txtHeight);
			panel.add(btOK);
			panel.setBackground(Color.GRAY);
			add(panel, BorderLayout.CENTER);
			setVisible(true);
			pack();
			enableComponents(false);
		}

		public void enableComponents(boolean enabled){
			txtWidth.setEnabled(enabled);
			txtHeight.setEnabled(enabled);
			btOK.setEnabled(enabled);
		}

		public void actionPerformed(ActionEvent e){
			if(e.getSource()==btOK){
				ia.setActionResized(true);     
				ia.resizeImage(Integer.parseInt(txtWidth.getText()),Integer.parseInt(txtHeight.getText()));
				enableSaving(true);
				ia.repaint();
				dispose();
			}
		}

		public class KeyList extends KeyAdapter{
			public void keyTyped(KeyEvent ke){

				char c = ke.getKeyChar(); 
				int intkey=(int)c;
				if(!(intkey>=48 && intkey<=57 || intkey==8 || intkey==127))
				{
					ke.consume(); 

				}  

			}

		} 
	}
	public class TextAdd extends JFrame implements ActionListener {
		JPanel panel;
		JTextArea txtText;
		JComboBox cbFontNames;
		JComboBox cbFontSizes;
		JButton btOK;
		JButton btSetColor;
		String seFontName;
		Color colorText;
		int seFontSize;
		TextAdd(){
			colorText=null;
			setTitle("텍스트 추가하기");
			setPreferredSize(new Dimension(400,150));

			btOK=new JButton("OK");
			btOK.setBackground(Color.BLACK);
			btOK.setForeground(Color.WHITE);  
			btOK.addActionListener(this);

			btSetColor=new JButton("글씨 색깔 바꾸기");
			btSetColor.setBackground(Color.BLACK);
			btSetColor.setForeground(Color.WHITE);  
			btSetColor.addActionListener(this);

			txtText=new JTextArea(1,30);
			cbFontNames=new JComboBox();
			cbFontSizes=new JComboBox();
			panel=new JPanel();
			panel.setLayout(new GridLayout(4,1));
			panel.add(new JLabel("텍스트:"));
			panel.add(txtText);
			panel.add(new JLabel("폰트:"));  
			panel.add(cbFontNames);
			panel.add(new JLabel("글자크기:"));  
			panel.add(cbFontSizes);
			panel.add(btSetColor);
			panel.add(btOK);
			panel.setBackground(Color.GRAY);
			add(panel, BorderLayout.CENTER);
			setVisible(true);
			pack();
			listFonts();

		}


		public void actionPerformed(ActionEvent e){
			if(e.getSource()==btOK){ 
				ia.setActionDraw(true); 
				String textDraw=txtText.getText();
				String fontName=cbFontNames.getSelectedItem().toString();

				int fontSize=Integer.parseInt(cbFontSizes.getSelectedItem().toString());
				ia.setText(textDraw,fontName,fontSize,colorText);
				dispose();
				enableSaving(true);
			}
			else if(e.getSource()==btSetColor){ 
				JColorChooser jser=new JColorChooser();   
				colorText=jser.showDialog(this,"색깔 선택",Color.BLACK);

			}
		}


		public void listFonts(){

			GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment(); 
			String[] fonts=ge.getAvailableFontFamilyNames();
			for(String f:fonts)
				cbFontNames.addItem(f);

			for(int i=8;i<50;i++)
				cbFontSizes.addItem(i);

		}
	}





	public void actionPerformed(ActionEvent e){

		JButton source = (JButton)(e.getSource());
		if(source.getText().compareTo("사진열기")==0)
		{
			ia.repaint();
			validate();

		}
		else if(source.getText().compareTo("사진저장")==0)
		{
			showSaveFileDialog(); 

		}

		else if(source.getText().compareTo("사진에 글자넣기")==0)
		{
			TextAdd textadd = new TextAdd();
			textadd.setLocationRelativeTo(null);
			enableSaving(true);
		}

		else if(source.getText().compareTo("사진 밝기조절")==0)
		{

			ImageBrightness ib=new ImageBrightness(); 
			if(ImgArea.imageLoaded)
				ib.setLocationRelativeTo(null);
			ia.setTempImage();
			ib.enableSlider(true);
			enableSaving(true);
		}
		else if(source.getText().compareTo("사진 크기조절")==0)
		{

			ImageResize ir=new ImageResize();
			if(ImgArea.imageLoaded)
				ir.setLocationRelativeTo(null);
			ir.enableComponents(true);  
			enableSaving(true);

		}
		else if(source.getText().compareTo("사진 오른쪽 회전")==0)
		{
			if(ImgArea.imageLoaded){
				ia.rotateRightImage();
				enableSaving(true);
			} 
		}
		else if(source.getText().compareTo("사진 왼쪽 회전")==0)
		{
			if(ImgArea.imageLoaded){
				ia.rotateLeftImage();
				enableSaving(true);
			} 
		}

		else if(source.getText().compareTo("사진 자르기")==0){
			if(ImgArea.imageLoaded){
				ia.actionClip = true;
				ia.cropping = true;
				enableSaving(true);

			}
		} 

		else if(source.getText().compareTo("처음상태로")==0) {
			ia.setImgFileName(filename);
			ia.reset();
		}
	} 


	public void setImage(String path){
		ImageIcon example = new ImageIcon(path);
		filename=example.toString();
		ia.prepareImage(filename);

	}

	public void showSaveFileDialog(){
		int returnVal = chooser.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			String filen=chooser.getSelectedFile().toString(); 
			ia.saveToFile(filen);  
		}
	}

	public void enableSaving(boolean f){
		msaveas.setEnabled(f);
	}
}

