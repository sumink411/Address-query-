package com.javalec.base;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class UserInsert {

	private JFrame frame;
	private JLabel lblNewLabel;
	private JTextField tfName;
	private JLabel lblNewLabel_1;
	private JTextField tfTelNo;
	private JLabel lblNewLabel_1_1;
	private JTextField tfAddress;
	private JLabel lblNewLabel_1_2;
	private JTextField tfEmail;
	private JLabel lblNewLabel_1_3;
	private JTextField tfRelation;
	private JButton btnOk;
	
	
	//Database 환경 정의
	private final String url_mysql = "jdbc:mysql://127.0.0.1/useraddress?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	private final String id_mysql = "root";
	private final String pw_mysql = "qwer1234";
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInsert window = new UserInsert();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserInsert() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("주소록 등록");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(getLblNewLabel());
		frame.getContentPane().add(getTfName());
		frame.getContentPane().add(getLblNewLabel_1());
		frame.getContentPane().add(getTfTelNo());
		frame.getContentPane().add(getLblNewLabel_1_1());
		frame.getContentPane().add(getTfAddress());
		frame.getContentPane().add(getLblNewLabel_1_2());
		frame.getContentPane().add(getTfEmail());
		frame.getContentPane().add(getLblNewLabel_1_3());
		frame.getContentPane().add(getTfRelation());
		frame.getContentPane().add(getBtnOk());
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("성명:");
			lblNewLabel.setBounds(45, 39, 61, 16);
		}
		return lblNewLabel;
	}
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(107, 34, 130, 26);
			tfName.setColumns(10);
		}
		return tfName;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("전화번호:");
			lblNewLabel_1.setBounds(45, 72, 61, 16);
		}
		return lblNewLabel_1;
	}
	private JTextField getTfTelNo() {
		if (tfTelNo == null) {
			tfTelNo = new JTextField();
			tfTelNo.setColumns(10);
			tfTelNo.setBounds(107, 67, 130, 26);
		}
		return tfTelNo;
	}
	private JLabel getLblNewLabel_1_1() {
		if (lblNewLabel_1_1 == null) {
			lblNewLabel_1_1 = new JLabel("주소: ");
			lblNewLabel_1_1.setBounds(45, 105, 61, 16);
		}
		return lblNewLabel_1_1;
	}
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setColumns(10);
			tfAddress.setBounds(107, 100, 215, 26);
		}
		return tfAddress;
	}
	private JLabel getLblNewLabel_1_2() {
		if (lblNewLabel_1_2 == null) {
			lblNewLabel_1_2 = new JLabel("전자우편:");
			lblNewLabel_1_2.setBounds(45, 138, 61, 16);
		}
		return lblNewLabel_1_2;
	}
	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setColumns(10);
			tfEmail.setBounds(107, 133, 215, 26);
		}
		return tfEmail;
	}
	private JLabel getLblNewLabel_1_3() {
		if (lblNewLabel_1_3 == null) {
			lblNewLabel_1_3 = new JLabel("관계:");
			lblNewLabel_1_3.setBounds(45, 176, 61, 16);
		}
		return lblNewLabel_1_3;
	}
	private JTextField getTfRelation() {
		if (tfRelation == null) {
			tfRelation = new JTextField();
			tfRelation.setColumns(10);
			tfRelation.setBounds(107, 171, 270, 26);
		}
		return tfRelation;
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("입력");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					insertAction(); 
				}
			});
			btnOk.setBounds(173, 220, 117, 29);
		}
		return btnOk;
	}
	
	
	//FUNCTIONS
	
	private void insertAction() {
		PreparedStatement ps = null; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			
			String query = "Insert into userinfo (name, telno, address, email, relation) ";  //include space 
			String query1 = "values (?,?,?,?,?)";
			
			ps = conn_mysql.prepareStatement(query + query1);
			ps.setString(1, tfName.getText().trim());			
			ps.setString(2, tfTelNo.getText().trim());		
			ps.setString(3, tfAddress.getText().trim());
			ps.setString(4, tfEmail.getText().trim());
			ps.setString(5, tfRelation.getText().trim());
			
			
			ps.executeUpdate();
			
			conn_mysql.close();
			JOptionPane.showMessageDialog(null, tfName.getText().trim() + "님의 정보가 입력 되었습니다.");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
