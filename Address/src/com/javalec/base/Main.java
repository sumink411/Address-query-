package com.javalec.base;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

	private JFrame frame;
	private JRadioButton rbtnInsert;
	private JRadioButton rbtnEdit;
	private JRadioButton rbtnDelete;
	private JRadioButton rbtnSearch;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox comboBox;
	private JTextField tfSearchBar;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable inner_Table;
	private JLabel lblSeqNo;
	private JTextField tfSeqNo;
	private JLabel lblName;
	private JTextField tfName;
	private JLabel lblTelNo;
	private JTextField tfTelNo;
	private JLabel lblAddress;
	private JTextField tfAddress;
	private JLabel lblEmail;
	private JTextField tfEmail;
	private JLabel lblRelation;
	private JTextField tfRelation;
	private JButton btnOk;

	
	
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
					Main window = new Main();
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
	public Main() {
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
		
		frame = new JFrame();
		frame.setTitle("주소록");
		frame.setBounds(100, 100, 429, 529);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(getRbtnInsert());
		frame.getContentPane().add(getRbtnEdit());
		frame.getContentPane().add(getRbtnDelete());
		frame.getContentPane().add(getRbtnSearch());
		frame.getContentPane().add(getComboBox());
		frame.getContentPane().add(getTfSearchBar());
		frame.getContentPane().add(getBtnSearch());
		frame.getContentPane().add(getScrollPane());
		frame.getContentPane().add(getLblSeqNo());
		frame.getContentPane().add(getTfSeqNo());
		frame.getContentPane().add(getLblName());
		frame.getContentPane().add(getTfName());
		frame.getContentPane().add(getLblTelNo());
		frame.getContentPane().add(getTfTelNo());
		frame.getContentPane().add(getLblAddress());
		frame.getContentPane().add(getTfAddress());
		frame.getContentPane().add(getLblEmail());
		frame.getContentPane().add(getTfEmail());
		frame.getContentPane().add(getLblRelation());
		frame.getContentPane().add(getTfRelation());
		frame.getContentPane().add(getBtnOk());
	}
	private JRadioButton getRbtnInsert() {
		if (rbtnInsert == null) {
			rbtnInsert = new JRadioButton("입력");
			rbtnInsert.setBounds(17, 16, 54, 23);
			buttonGroup.add(rbtnInsert);
			rbtnInsert.setSelected(true);
		}
		return rbtnInsert;
	}
	private JRadioButton getRbtnEdit() {
		if (rbtnEdit == null) {
			rbtnEdit = new JRadioButton("수정");
			rbtnEdit.setBounds(79, 16, 54, 23);
			buttonGroup.add(rbtnEdit);
		}
		return rbtnEdit;
	}
	private JRadioButton getRbtnDelete() {
		if (rbtnDelete == null) {
			rbtnDelete = new JRadioButton("삭제");
			rbtnDelete.setBounds(138, 16, 54, 23);
			buttonGroup.add(rbtnDelete);
		}
		return rbtnDelete;
	}
	private JRadioButton getRbtnSearch() {
		if (rbtnSearch == null) {
			rbtnSearch = new JRadioButton("검색");
			rbtnSearch.setBounds(201, 16, 61, 23);
			buttonGroup.add(rbtnSearch);
		}
		return rbtnSearch;
	}
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setBounds(17, 51, 75, 27);
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"이름", "주소", "관계"}));
		}
		return comboBox;
	}
	private JTextField getTfSearchBar() {
		if (tfSearchBar == null) {
			tfSearchBar = new JTextField();
			tfSearchBar.setBounds(89, 50, 201, 26);
			tfSearchBar.setColumns(10);
		}
		return tfSearchBar;
	}
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton("검색");
			btnSearch.setBounds(293, 50, 117, 29);
		}
		return btnSearch;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(17, 90, 390, 141);
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
					if(e.getButton() ==1) {
						tableClick();
					}
				}
			});
			inner_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			inner_Table.setModel(outer_Table);
		}
		return inner_Table;
	}
	private JLabel getLblSeqNo() {
		if (lblSeqNo == null) {
			lblSeqNo = new JLabel("Sequence No:");
			lblSeqNo.setBounds(10, 255, 96, 16);
		}
		return lblSeqNo;
	}
	private JTextField getTfSeqNo() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setBounds(99, 250, 191, 26);
			tfSeqNo.setColumns(10);
		}
		return tfSeqNo;
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("이름:");
			lblName.setBounds(10, 288, 96, 16);
		}
		return lblName;
	}
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setColumns(10);
			tfName.setBounds(99, 283, 130, 26);
		}
		return tfName;
	}
	private JLabel getLblTelNo() {
		if (lblTelNo == null) {
			lblTelNo = new JLabel("전화번호:");
			lblTelNo.setBounds(10, 321, 96, 16);
		}
		return lblTelNo;
	}
	private JTextField getTfTelNo() {
		if (tfTelNo == null) {
			tfTelNo = new JTextField();
			tfTelNo.setColumns(10);
			tfTelNo.setBounds(99, 316, 130, 26);
		}
		return tfTelNo;
	}
	private JLabel getLblAddress() {
		if (lblAddress == null) {
			lblAddress = new JLabel("주소:");
			lblAddress.setBounds(10, 354, 96, 16);
		}
		return lblAddress;
	}
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setColumns(10);
			tfAddress.setBounds(99, 349, 130, 26);
		}
		return tfAddress;
	}
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("전자우편:");
			lblEmail.setBounds(10, 387, 96, 16);
		}
		return lblEmail;
	}
	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setColumns(10);
			tfEmail.setBounds(99, 382, 130, 26);
		}
		return tfEmail;
	}
	private JLabel getLblRelation() {
		if (lblRelation == null) {
			lblRelation = new JLabel("관계:");
			lblRelation.setBounds(10, 420, 96, 16);
		}
		return lblRelation;
	}
	private JTextField getTfRelation() {
		if (tfRelation == null) {
			tfRelation = new JTextField();
			tfRelation.setColumns(10);
			tfRelation.setBounds(99, 415, 130, 26);
		}
		return tfRelation;
	}
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.setBounds(260, 455, 117, 29);
		}
		return btnOk;
	}
	
	
	//FUNCTIONS
	
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
				tfSeqNo.setText(Integer.toString(rs.getInt(1)));
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
