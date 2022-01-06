package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import color.AppColor;
import controllers.BaseController;

public abstract class BaseView extends JFrame implements ActionListener{
	protected JPanel mainPanel;
	private String headerContent;
	protected JButton backBtn;
	
	public BaseView(String headerContent) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);
		setTitle("Coffee Vibes");
		this.headerContent = headerContent;
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		initialize();
		setNorth();
		setCenter();
		setSouth();

		finalize();
	}
	
	public BaseView(String headerContent, int width, int height) {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);
		setTitle("Coffee Vibes");
		this.headerContent = headerContent;
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		initialize();
		setNorth();
		setCenter();
		setSouth();
		
		finalize();
	}
	
	protected void finalize() {
		setVisible(true);
	}
	
	protected void showFirstError(BaseController controller) {
		JOptionPane.showMessageDialog(this, controller.getFirstErrorMsg());
		controller.clearError();
	}
	
	protected void showSuccessMsg(BaseController controller, String message) {
		JOptionPane.showMessageDialog(this, message);
		controller.clearError();
	}
	
	protected void showAlert(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	protected abstract void initialize();
	
	protected void setNorth() {
		backBtn = new JButton("Back");
		backBtn.addActionListener(this);
		JLabel header = new JLabel(headerContent);
		header.setForeground(Color.WHITE);
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		topPanel.add(backBtn);
		topPanel.add(header);
		
		topPanel.setBackground(AppColor.PRIMARY);
		mainPanel.add(topPanel, BorderLayout.NORTH);
	}
	
	protected abstract void setCenter();
	protected abstract void setSouth();
}
