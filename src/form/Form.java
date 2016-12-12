/*
 * Created by JFormDesigner on Sun Dec 11 13:01:47 MSK 2016
 */

package form;

import dao.EntryDAO;
import dao.HibernateUtil;
import entities.ArhiveEntity;
import entities.EntryEntity;
import service.Arhivator;
import sun.reflect.generics.tree.Tree;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.*;

public class Form extends JFrame {
    public Form() {
        initComponents();
    }



    public static void main(String[] args){
        Form form = new Form();
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setVisible(true);

    }

    private void buttonCreateAction(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();//создаем архив
        String filename = "";
        int dialog = fileChooser.showSaveDialog(Form.this);

        if (dialog != JFileChooser.APPROVE_OPTION)return;

        filename = fileChooser.getCurrentDirectory().toString()+ "\\"+fileChooser.getSelectedFile().getName();


        FileDialog fd = new FileDialog(Form.this);//помещаем в него файлы
        fd.setTitle("Choose files");
        fd.setMultipleMode(true);
        fd.setVisible(true);
        File[] files =fd.getFiles();
        String[] entries = new String[files.length];
        for(int i = 0;i<files.length;i++)
            entries[i] = files[i].getPath();//+files[i].getName();

        try {
            Arhivator.createArhive(filename,entries);
        } catch (IOException e1) {
            e1.printStackTrace();
            JOptionPane.showConfirmDialog(Form.this,e1);
        }
        refreshData();
    }

    private void buttonDeleteAction(ActionEvent e) {
        try {
            Arhivator.deleteArhive((ArhiveEntity) comboBox1.getSelectedItem());
        } catch (IOException e1) {
            e1.printStackTrace();
            JOptionPane.showConfirmDialog(Form.this,e1);
        }
    }

    private void comboBox1ActionPerformed(ActionEvent e) {
        // TODO add your code here

    }

    private void buttonOpebAction(ActionEvent e) {
        refreshData();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Vadim Lygin
        scrollPane1 = new JScrollPane();
        tree1 = new JTree();
        toolBar1 = new JToolBar();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        comboBox1 = new JComboBox();

        //======== this ========
        setName("frame");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tree1);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);

        //======== toolBar1 ========
        {

            //---- button1 ----
            button1.setText("create");
            button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonCreateAction(e);
                }
            });
            toolBar1.add(button1);

            //---- button2 ----
            button2.setText("delete");
            button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonDeleteAction(e);
                }
            });
            toolBar1.add(button2);

            //---- button3 ----
            button3.setText("open");
            button3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonOpebAction(e);
                }
            });
            toolBar1.add(button3);

            //---- comboBox1 ----
            comboBox1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    comboBox1ActionPerformed(e);
                }
            });
            toolBar1.add(comboBox1);
        }
        contentPane.add(toolBar1, BorderLayout.SOUTH);
        setSize(690, 635);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        tree1.setModel(null);

        refreshData();

    }
    public void refreshData(){
        for (ArhiveEntity arhive : HibernateUtil.getArhivesDAO().getAll()) {
            comboBox1.addItem(arhive);
        }
        if(comboBox1.getSelectedItem()==null)return;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(comboBox1.getSelectedItem());
        int i = 0;
        for (EntryEntity entry : HibernateUtil.getEntryDAO().getAllByArhive((ArhiveEntity)comboBox1.getSelectedItem()))
            root.insert(new DefaultMutableTreeNode(entry.getName()),i++);

        TreeModel treeModel = new DefaultTreeModel(root);
        tree1.setModel(treeModel);
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Vadim Lygin
    private JScrollPane scrollPane1;
    private JTree tree1;
    private JToolBar toolBar1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JComboBox comboBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
