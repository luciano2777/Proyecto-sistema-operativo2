/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Assets.LeftButtonBorder;
import Assets.RightButtonBorder;
import Assets.TreeRender;
import Classes.Directory;
import Classes.FileSystem;
import DataStructures.List;
import DataStructures.Queue;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Juan
 */
public class MainView extends javax.swing.JFrame {
    private FileSystem fileSystem;
    private static int SDsize;
    
    public MainView(int SDsize) {
        this.fileSystem = new FileSystem(SDsize);
        
        init();        
        drawTree();
    }
    
    
    public void drawTree(){
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
        
        Queue<Directory> dirQueue = new Queue();
        Queue<DefaultMutableTreeNode> nodeQueue = new Queue();
        
        //Recorrer el arbol con BFS para agregar al JTree
        dirQueue.enqueue(fileSystem.getRoot());
        nodeQueue.enqueue(rootNode);
                
        while(!dirQueue.isEmpty()){
            List<Directory> currentDirectories = dirQueue.dequeue().getDirectories();
            DefaultMutableTreeNode currentNode = nodeQueue.dequeue();
            
            for (int i = 0; i < currentDirectories.getSize(); i++) {
                Directory directory = currentDirectories.get(i);
                
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(directory.getName());
                currentNode.add(newNode);
                
                dirQueue.enqueue(directory);
                nodeQueue.enqueue(newNode);
            }            
        }
        
        
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        JTree.setModel(treeModel);
    }
    
    
    private void init(){
        initComponents();
        
        ImageIcon appIcon = new ImageIcon("./src/Assets/fileSystemIcon.png");
        setIconImage(appIcon.getImage());

        ImageIcon createIcon = new ImageIcon("./src/Assets/plus.png");
        ImageIcon deleteIcon = new ImageIcon("./src/Assets/minus.png");

        Image createImage = createIcon.getImage();
        Image scaledImage = createImage.getScaledInstance((int)(create.getWidth()), (int)(create.getHeight() / 1.2), Image.SCALE_SMOOTH);
        create.setIcon(new ImageIcon(scaledImage));

        Image deleteImage = deleteIcon.getImage();
        scaledImage = deleteImage.getScaledInstance((int)(delete.getWidth()), (int)(delete.getHeight() / 1.2), Image.SCALE_SMOOTH);
        delete.setIcon(new ImageIcon(scaledImage));
        
        create.setBorder(new LeftButtonBorder(Color.WHITE, 1));
        delete.setBorder(new RightButtonBorder(Color.WHITE, 1));
        
        JTree.setCellRenderer(new TreeRender());
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTree = new javax.swing.JTree();
        pathOutput = new javax.swing.JTextField();
        create = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JTree.setBackground(new java.awt.Color(0, 15, 36));
        JTree.setForeground(new java.awt.Color(255, 255, 255));
        JTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTreeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTree);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 540, 450));

        pathOutput.setBackground(new java.awt.Color(51, 51, 51));
        pathOutput.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        pathOutput.setForeground(new java.awt.Color(255, 255, 255));
        pathOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathOutputActionPerformed(evt);
            }
        });
        jPanel1.add(pathOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 480, 30));

        create.setBackground(new java.awt.Color(0, 0, 0));
        create.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        create.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });
        jPanel1.add(create, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 30, 30));

        delete.setBackground(new java.awt.Color(0, 0, 0));
        delete.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        delete.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 30, 30));

        jTextField1.setBackground(new java.awt.Color(0, 20, 50));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 480));

        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTreeMouseClicked
        try{
            TreePath path = JTree.getPathForLocation(evt.getX(), evt.getY());
            
            StringBuilder pathStr = new StringBuilder();                        
            while(path != null){
                pathStr.insert(0, path.getLastPathComponent() + "/");
                path = path.getParentPath();
            }
            pathOutput.setText(pathStr.toString());                  
        }
        catch(Exception e){
        }

    }//GEN-LAST:event_JTreeMouseClicked

    private void pathOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathOutputActionPerformed

    }//GEN-LAST:event_pathOutputActionPerformed

    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView(SDsize).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree JTree;
    private javax.swing.JButton create;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField pathOutput;
    // End of variables declaration//GEN-END:variables
}
