-import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import javafx.stage.FileChooser;

// ~~~~JFileChooser~~~~~
// https://blog.naver.com/cracker542/40119977325

public class PanelView extends JPanel {
	private PanelController pc;
	private JPanel myPanel;
	
	private JPanel menuPanel;
	private JButton loadBtn;
	private JButton editBtn;
	private JButton saveBtn;
	private JButton saveAsBtn;
	   
	// image icon for imageBtn
	private ImageIcon load_icon;
	private ImageIcon edit_icon;
	private ImageIcon save_icon;
	private ImageIcon saveAs_icon;
	   
	//private JTextArea myTextArea;
	private JEditorPane myTextArea;
	private JScrollPane scrollPane;
	private JLabel statusBar;
	
	private JFileChooser fileChooser;
	   
	   
	public PanelView() throws Exception{
		pc = new PanelController();
		
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // 디렉토리 설정
        fileChooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리 지정 
        fileChooser.setAcceptAllFileFilterUsed(true);   // Filter 모든 파일 적용
        fileChooser.setDialogTitle("Choose File to Open"); // 창의 제목 
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 파일 선택 모드
        
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary File", "cd11"); //  filter 확장자 추가 
//        fileChooser.setFileFilter(filter); // 파일 필터를 추가
        
		
		myPanel = new JPanel();
		menuPanel = new JPanel();
		   
		// set image icon
		load_icon=new ImageIcon("res/load.png");
		edit_icon=new ImageIcon("res/edit.png");
		save_icon=new ImageIcon("res/save.png");
		saveAs_icon=new ImageIcon("res/save_as.png");
			
		// set size of image button
		Image load_img=load_icon.getImage(); load_img=load_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image edit_img=edit_icon.getImage(); edit_img=edit_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image save_img=save_icon.getImage(); save_img=save_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image saveAs_img=saveAs_icon.getImage(); saveAs_img=saveAs_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		load_icon=new ImageIcon(load_img);
		edit_icon=new ImageIcon(edit_img);
		save_icon=new ImageIcon(save_img);
		saveAs_icon=new ImageIcon(saveAs_img);
			
		// set image button
		loadBtn = new JButton(load_icon);
		editBtn = new JButton(edit_icon);
		saveBtn = new JButton(save_icon);
		saveAsBtn = new JButton(saveAs_icon);
		
		// set Button Status
		loadBtn.setEnabled(true);
		editBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		saveAsBtn.setEnabled(false);
			
		// make Image Button's border invisible
		loadBtn.setBorderPainted(false); loadBtn.setFocusPainted(false);
		editBtn.setBorderPainted(false); editBtn.setFocusPainted(false);
		saveBtn.setBorderPainted(false); saveBtn.setFocusPainted(false);
		saveAsBtn.setBorderPainted(false); saveAsBtn.setFocusPainted(false);
			
		this.add(loadBtn);
		this.add(editBtn);
		this.add(saveBtn);
		this.add(saveAsBtn);		   

		myTextArea = new JEditorPane();
		myTextArea.setEditable(false);

		// Dummy Text
		myTextArea.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n Nulla non urna congue, pellentesque lectus nec, ullamcorper tellus.\n Morbi scelerisque, magna eu porta vestibulum, enim nisi volutpat lectus, quis lacinia mauris leo et lorem. \nSuspendisse erat orci, sagittis vel eros lacinia, maximus pulvinar augue. \nEtiam pretium tortor id neque molestie luctus. \nDonec tincidunt, urna et interdum ornare, diam lectus rutrum enim, et mollis erat tortor et nisi. \nMauris congue lobortis mi, ac sollicitudin quam interdum vitae. \nPellentesque lorem augue, sagittis nec viverra at, imperdiet ac nibh. \nCras faucibus, magna in sodales condimentum, odio massa interdum lectus, molestie tincidunt justo lorem vel leo. \nDonec vestibulum egestas massa vitae feugiat. \nPhasellus tristique finibus pulvinar. \nMorbi molestie nibh sit amet nisi commodo, sit amet condimentum turpis ultrices. \nNullam ac justo ligula. \nPraesent lacinia nisi vel ex aliquam sollicitudin sit amet non leo. \nMaecenas varius feugiat rhoncus. \r\n" + 
				"Nam vel varius sem.\n Nullam nisi augue, ornare eget aliquet eu, interdum eget nisl.\n Aenean sollicitudin arcu eget tortor molestie pulvinar. \nDonec euismod imperdiet rutrum. \nPraesent commodo metus id nisi congue, et condimentum est aliquam. \nPellentesque vitae sapien in ipsum interdum molestie non nec metus. \nVivamus lectus orci, vestibulum eget luctus eget, convallis non erat. \r\n" + 
				"Vestibulum vitae placerat magna. \nVivamus tincidunt tellus vitae urna sagittis, quis mollis mauris consectetur. \nNunc vehicula tellus augue, sed sagittis dolor eleifend id. \nNullam vitae erat viverra, ornare ipsum eu, luctus nisi. \nNunc arcu justo, bibendum ut viverra et, ornare et nibh. \nDonec bibendum sed eros eu varius. \nPellentesque fringilla faucibus nulla ac gravida. \nFusce sit amet justo non sapien pulvinar ornare. \nQuisque neque enim, efficitur vitae rutrum eu, ullamcorper ut leo. \nNulla vel diam molestie mauris sagittis finibus eget sit amet nisi. \nUt sed nibh vitae erat sagittis viverra vel sit amet turpis. \nIn efficitur scelerisque congue. \nFusce eget rutrum sem, sit amet semper diam. \nUt porta ullamcorper eros vitae tempus. \nVivamus euismod auctor metus. \r\n" + 
		   		"Maecenas diam ipsum, semper at pulvinar vel, vestibulum sit amet mi. \nAliquam sagittis, quam nec placerat aliquet, neque sapien feugiat urna, eu interdum velit dui tincidunt dolor. \nAliquam eget elementum ante. \nIn laoreet odio nec vehicula rhoncus. \nPellentesque ultrices molestie fermentum. \nDonec non vulputate felis. \nDonec sollicitudin erat at felis tristique accumsan. \nFusce at augue vitae mi laoreet aliquet at id massa. \nProin convallis est sapien, et imperdiet risus feugiat non. \nIn vitae sodales orci. \nDuis a nibh ut urna lacinia feugiat. \nAenean pellentesque sodales est ac ornare. \nNam commodo diam ac quam congue imperdiet. \nSed sit amet sem accumsan, tempor nibh eget, luctus ligula. \nDonec posuere id lorem ut auctor. \r\n" + 
		   		"Duis ut augue erat. \nVestibulum porttitor, felis et pulvinar convallis, metus justo efficitur metus, rhoncus ultrices mauris nisi ac diam. \nDonec sollicitudin eros et neque ultricies posuere. \nSed rutrum tempor mollis. \nDonec aliquam mattis sodales. \nMaecenas arcu lorem, condimentum non nunc lacinia, pulvinar egestas dui. \nVivamus a velit placerat, auctor libero ut, porta leo. ");

		// ~~~~~~~ view mode ~~~~~~~~~~~! 
//		myTextArea.disable();
		   
		scrollPane = new JScrollPane(myTextArea);
		 
		// default
		pc.setMode(Mode.VIEW);
		statusBar = new JLabel("View Mode");
				
		loadBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Load button pressed.");
				
				// Set Mode
				pc.setMode(Mode.VIEW);
				statusBar.setText("View Mode");
				
				// Set Button Status
				loadBtn.setEnabled(false);
				editBtn.setEnabled(true);
				saveBtn.setEnabled(false);
				saveAsBtn.setEnabled(false);
				
				// Load file via fileChooser
				int returnVal = fileChooser.showOpenDialog(null);
	            if( returnVal == JFileChooser.APPROVE_OPTION) {
	                pc.load(fileChooser.getSelectedFile().toString());
	            } else {
	                System.out.println("File load canceled.");
				}
	            
	            // Set the text in view
	            myTextArea.setText(pc.getFileContent());
	            
	            System.out.println("Load Completed.");
			}
		});
		   
		editBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Edit button pressed.");
				
				// Set Mode
				pc.setMode(Mode.EDIT);
				statusBar.setText("Edit Mode");
				
				// Set Button Status
				loadBtn.setEnabled(false);
				editBtn.setEnabled(false);
				saveBtn.setEnabled(true);
				saveAsBtn.setEnabled(true);
				
				myTextArea.setEditable(true);
				
				System.out.println("Edit Completed.");
				}
				
			});
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		// TODO Auto-generated method stub
		  		System.out.println("Save button pressed.");
		  		
		  		// Set Mode
		  		pc.setMode(Mode.VIEW);
				statusBar.setText("View Mode");
			
				/*TODO
		  		 * Where to put "setFileContent(String)"??
		  		 * in Panel View 
		  		 * or pass editedContent as parameter of save() function?? 
		  		 */
		  		String editedContent = myTextArea.getText();
				pc.setFileContent(editedContent);
		  		pc.save();
		  		
		  		myTextArea.setEditable(false);
		  		
		  		System.out.println("Save Completed.");
		  	}
				
		});
		   
		saveAsBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Save As button pressed.");
				
				pc.setMode(Mode.VIEW);
				statusBar.setText("View Mode");
					
				int returnVal = fileChooser.showSaveDialog(null);
				if( returnVal == JFileChooser.APPROVE_OPTION) {
					String editedContent = myTextArea.getText();
					pc.setFileContent(editedContent);
	                pc.saveAs(fileChooser.getSelectedFile().toString());
	            } else {
	                System.out.println("File load canceled.");
				}
				
				myTextArea.setEditable(false);
				
				System.out.println("Save As Completed.");
			}
			
		});
		   
		menuPanel.setLayout(new GridLayout(1, 3));
		   
		menuPanel.add(loadBtn);
		menuPanel.add(editBtn);
		menuPanel.add(saveBtn);
		menuPanel.add(saveAsBtn);
		   
		myPanel.setLayout(new BorderLayout());
		myPanel.add(menuPanel, BorderLayout.NORTH);
		myPanel.add(scrollPane, BorderLayout.CENTER);
		myPanel.add(statusBar, BorderLayout.SOUTH);
		   
		this.setLayout(new BorderLayout());
		this.add(myPanel);
		this.setVisible(true);
	}
}
