package com.javalec.base;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserQuery {

	private JFrame frame;
	private JComboBox cbSelection;
	private JTextField tfSelection;
	private JButton btnQuery;
	private JScrollPane scrollPane;
	private JTable inner_Table;
	private JLabel lblNewLabel;
	private JTextField tfSequenceNo;
	private JTextField tfName;
	private JLabel lblNewLabel_1;
	private JTextField tfTelNo;
	private JLabel lblNewLabel_2;
	private JTextField tfAddress;
	private JLabel lblNewLabel_3;
	private JTextField tfEmail;
	private JLabel lblNewLabel_4;
	private JTextField tfRelation;
	private JLabel lblNewLabel_5;

	//***********************************************************************************
	// Database & Table
	private final DefaultTableModel outer_Table = new DefaultTableModel();
	private final String url_mysql = "jdbc:mysql://127.0.0.1/useraddress?serverTimezone=UTC&characterEncoding=utf8&useSSL=FALSE";
	private final String id_mysql = "root";
	private final String pw_mysql = "qwer1234";
	private JButton btnUpdate;
	private JButton btnDelete;
	

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserQuery window = new UserQuery();
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
	public UserQuery() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				tableInit(); 
				searchAction();
			}
		});
		frame.getContentPane().setBackground(new Color(252, 210, 245));
		frame.setTitle("주소록 검색/수정/삭제");
		frame.setBounds(100, 100, 407, 524);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(getCbSelection());
		frame.getContentPane().add(getTfSelection());
		frame.getContentPane().add(getBtnQuery());
		frame.getContentPane().add(getScrollPane());
		frame.getContentPane().add(getLblNewLabel());
		frame.getContentPane().add(getTfSequenceNo());
		frame.getContentPane().add(getTfName());
		frame.getContentPane().add(getLblNewLabel_1());
		frame.getContentPane().add(getTfTelNo());
		frame.getContentPane().add(getLblNewLabel_2());
		frame.getContentPane().add(getTfAddress());
		frame.getContentPane().add(getLblNewLabel_3());
		frame.getContentPane().add(getTfEmail());
		frame.getContentPane().add(getLblNewLabel_4());
		frame.getContentPane().add(getTfRelation());
		frame.getContentPane().add(getLblNewLabel_5());
		frame.getContentPane().add(getBtnUpdate());
		frame.getContentPane().add(getBtnDelete());
	}
	private JComboBox getCbSelection() {
		if (cbSelection == null) {
			cbSelection = new JComboBox();
			cbSelection.setModel(new DefaultComboBoxModel(new String[] {"이름", "주소", "관계"}));
			cbSelection.setBounds(16, 21, 74, 27);
		}
		return cbSelection;
	}
	private JTextField getTfSelection() {
		if (tfSelection == null) {
			tfSelection = new JTextField();
			tfSelection.setBounds(92, 20, 148, 26);
			tfSelection.setColumns(10);
		}
		return tfSelection;
	}
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton("검색");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					conditionQuery(); 
				}
			});
			btnQuery.setBounds(244, 20, 117, 29);
		}
		return btnQuery;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(26, 60, 331, 104);
			scrollPane.setViewportView(getInner_Table());
		}
		return scrollPane;
	}
	private JTable getInner_Table() {
		if (inner_Table == null) {
			inner_Table = new JTable();
			inner_Table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == 1) {
						tableClick(); 
					}
				}
			});
			inner_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//**************************************************************
			inner_Table.setModel(outer_Table); //***************************
			//**************************************************************
			
		}
		return inner_Table;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Sequence No");
			lblNewLabel.setBounds(29, 188, 88, 16);
		}
		return lblNewLabel;
	}
	private JTextField getTfSequenceNo() {
		if (tfSequenceNo == null) {
			tfSequenceNo = new JTextField();
			tfSequenceNo.setHorizontalAlignment(SwingConstants.TRAILING);
			tfSequenceNo.setBounds(123, 183, 44, 26);
			tfSequenceNo.setColumns(10);
		}
		return tfSequenceNo;
	}
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
			tfName.setBounds(123, 216, 74, 26);
		}
		return tfName;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("이름");
			lblNewLabel_1.setBounds(29, 221, 88, 16);
		}
		return lblNewLabel_1;
	}
	private JTextField getTfTelNo() {
		if (tfTelNo == null) {
			tfTelNo = new JTextField();
			tfTelNo.setColumns(10);
			tfTelNo.setBounds(123, 250, 130, 26);
		}
		return tfTelNo;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("전화번호");
			lblNewLabel_2.setBounds(29, 255, 88, 16);
		}
		return lblNewLabel_2;
	}
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setColumns(10);
			tfAddress.setBounds(123, 281, 238, 26);
		}
		return tfAddress;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("주소");
			lblNewLabel_3.setBounds(29, 286, 88, 16);
		}
		return lblNewLabel_3;
	}
	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setColumns(10);
			tfEmail.setBounds(123, 314, 238, 26);
		}
		return tfEmail;
	}
	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("전자우편");
			lblNewLabel_4.setBounds(29, 319, 88, 16);
		}
		return lblNewLabel_4;
	}
	private JTextField getTfRelation() {
		if (tfRelation == null) {
			tfRelation = new JTextField();
			tfRelation.setColumns(10);
			tfRelation.setBounds(123, 345, 130, 26);
		}
		return tfRelation;
	}
	private JLabel getLblNewLabel_5() {
		if (lblNewLabel_5 == null) {
			lblNewLabel_5 = new JLabel("관계");
			lblNewLabel_5.setBounds(29, 350, 88, 16);
		}
		return lblNewLabel_5;
	}
	
	//FUNCTIONS
	
	//Table의 컬럼을 정의하고 Data내용을 초기화 시킨다.
	private void tableInit() {
		outer_Table.addColumn("순서");
		outer_Table.addColumn("이름");
		outer_Table.addColumn("전화번호");
		outer_Table.addColumn("관계");
		outer_Table.setColumnCount(4);
		
		
		
		//순서
		int colNo = 0; 
		TableColumn col = inner_Table.getColumnModel().getColumn(colNo);
		int width = 30; 
		col.setPreferredWidth(width);
		
		//이름 
		colNo = 1; 
		col = inner_Table.getColumnModel().getColumn(colNo);
		width = 80; 
		col.setPreferredWidth(width);
		
		
		//전화번호  
		colNo = 2; 
		col = inner_Table.getColumnModel().getColumn(colNo);
		width = 130; 
		col.setPreferredWidth(width);
		
		//관계   
		colNo = 3; 
		col = inner_Table.getColumnModel().getColumn(colNo);
		width = 150; 
		col.setPreferredWidth(width);
		
		
		//초기화 시키기 
		int i = outer_Table.getRowCount(); 
		for(int j=0; j<i; j++) {
			outer_Table.removeRow(0);
		}
		
		
		inner_Table.setAutoResizeMode(inner_Table.AUTO_RESIZE_OFF);
	}
	
	
	private void searchAction() {
		String query = "select seqno, name, telno, relation from userinfo";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			
			ResultSet rs = stmt_mysql.executeQuery(query);
			while(rs.next()) {
				String[] qTxt = {Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4)};
				outer_Table.addRow(qTxt);
			}
			
			
			conn_mysql.close();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private void tableClick() {
		int i = inner_Table.getSelectedRow(); 
		String seqno = (String) inner_Table.getValueAt(i, 0);
		
		String query1 = "select seqno, name, telno, address, email, relation from userinfo ";
		String query2 = "where seqno=" + Integer.parseInt(seqno); 
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			
			ResultSet rs = stmt_mysql.executeQuery(query1+query2);
			while(rs.next()) {
				tfSequenceNo.setText(Integer.toString(rs.getInt(1)));
				tfName.setText(rs.getString(2));
				tfTelNo.setText(rs.getString(3));
				tfAddress.setText(rs.getString(4));
				tfEmail.setText(rs.getString(5));
				tfRelation.setText(rs.getString(6));
				
			}
			
			
			conn_mysql.close();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	private void conditionQuery() {
		int i = cbSelection.getSelectedIndex();
		String conditionQueryName = "";
		
		switch(i) {
		case 0:
			conditionQueryName = "name";
			break;
		case 1: 
			conditionQueryName = "address";
			break;
		case 2: 
			conditionQueryName = "relation";
			break;
		default:
			break;
		}
		
		
		tableInit(); 
		clearColumn(); 
		conditionQueryAction(conditionQueryName); 
		
		
		
	}
	
	private void clearColumn() {
		tfSequenceNo.setText("");
		tfName.setText("");
		tfTelNo.setText("");
		tfAddress.setText("");
		tfEmail.setText("");
		tfRelation.setText("");
	}
	
	
	private void conditionQueryAction(String conditionQueryName) {
		String query1 = "select seqno, name, telno, relation from userinfo where " + conditionQueryName;
		String query2 = " like '%" + tfSelection.getText().trim() + "%'"; 
				
				
				
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			
			ResultSet rs = stmt_mysql.executeQuery(query1 + query2);
			while(rs.next()) {
				String[] qTxt = {Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4)};
				outer_Table.addRow(qTxt);
			}
			
			
			conn_mysql.close();
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("수정");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					update();
				}
			});
			btnUpdate.setBounds(92, 433, 117, 29);
		}
		return btnUpdate;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("삭제");
			btnDelete.setForeground(new Color(255, 7, 0));
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					delete();
				}
			});
			btnDelete.setBounds(212, 399, 117, 29);
		}
		return btnDelete;
	}
	
	
	
	private void update() {
		updateAction();
		tableInit(); 
		clearColumn();
		searchAction();
	}
	
	private void updateAction() {
		PreparedStatement ps = null; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			
			String query = "update userinfo set name=?, telno=?, address=?, email=?, relation=? ";  //include space 
			String query1 = "where seqno=?";
			
			ps = conn_mysql.prepareStatement(query + query1);
			ps.setString(1, tfName.getText().trim());			
			ps.setString(2, tfTelNo.getText().trim());		
			ps.setString(3, tfAddress.getText().trim());
			ps.setString(4, tfEmail.getText().trim());
			ps.setString(5, tfRelation.getText().trim());
			ps.setInt(6, Integer.parseInt(tfSequenceNo.getText()));
			
			
			ps.executeUpdate();
			
			conn_mysql.close();
			JOptionPane.showMessageDialog(null, tfName.getText().trim() + "님의 정보가 수정 되었습니다.");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void delete() {
		deleteAction();
		tableInit(); 
		clearColumn();
		searchAction();
		
	}
	
	private void deleteAction() {
		
		PreparedStatement ps = null; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
			Statement stmt_mysql = conn_mysql.createStatement();
			
			
			String query = "delete from userinfo ";  //include space 
			String query1 = "where seqno=?";
			
			ps = conn_mysql.prepareStatement(query + query1);
			ps.setInt(1,Integer.parseInt(tfSequenceNo.getText()));
			
			
			ps.executeUpdate();
			
			conn_mysql.close();
			JOptionPane.showMessageDialog(null, tfName.getText().trim() + "님의 정보가 삭제 되었습니다.");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
