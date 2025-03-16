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
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Juan
 */
public class MainView extends javax.swing.JFrame {
    private FileSystem fileSystem;
    private static int SDsize;
    
    //Para la terminal
    private int inputLength;
    private String currentInput;
    private List<String> inputs = new List();
    private List<String> outputs = new List();
    private Semaphore sem = new Semaphore(1);
    private final int CREATE_FILE = 0;
    private final int CREATE_DIR = 1;
    private final int DELETE = 2;
    private int instruction = -1;
    
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
        
        //Iniciar imagenes de la App
        String sp = File.separator;
        ImageIcon appIcon = new ImageIcon(Paths.get("src"+sp+"Assets"+sp+"fileSystemIcon.png").normalize().toString());
        setIconImage(appIcon.getImage());

        ImageIcon createIcon = new ImageIcon(Paths.get("src"+sp+"Assets"+sp+"plus.png").normalize().toString());
        ImageIcon deleteIcon = new ImageIcon(Paths.get("src"+sp+"Assets"+sp+"minus.png").normalize().toString());

        Image createImage = createIcon.getImage();
        Image scaledImage = createImage.getScaledInstance((int)(create.getWidth()), (int)(create.getHeight() / 1.2), Image.SCALE_SMOOTH);
        create.setIcon(new ImageIcon(scaledImage));

        Image deleteImage = deleteIcon.getImage();
        scaledImage = deleteImage.getScaledInstance((int)(delete.getWidth()), (int)(delete.getHeight() / 1.2), Image.SCALE_SMOOTH);
        delete.setIcon(new ImageIcon(scaledImage));
        
        create.setBorder(new LeftButtonBorder(Color.WHITE, 1));
        delete.setBorder(new RightButtonBorder(Color.WHITE, 1));
        
        //Iniciar el nuevo TreeRender
        JTree.setCellRenderer(new TreeRender());
        
        
        //Estado incial de la terminal
        terminal.getCaret().setVisible(false);
        terminal.setCaretColor(new Color(0, 19, 66));
        
        // Iniciar un hilo de trabajo para manejar la entrada de la terminal
        new Thread(() -> {
            try {
                while (true) {
                    sem.acquire();
                    SwingUtilities.invokeLater(() -> processInput());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void handleInput(){
        String inputStr = inputs.pop(0);
        SwingUtilities.invokeLater(() -> {
            terminal.setText(inputStr);
            terminal.setEditable(true);
            terminal.setCaretColor(Color.WHITE);
            terminal.requestFocusInWindow();
            inputLength = inputStr.length();
            currentInput = inputStr;
        });        
    }
    
    private void processInput() {
            if (!inputs.isEmpty()) {
                handleInput();
            }
            else{
                switch(instruction){
                    case CREATE_FILE -> {
                        createFile();
                    }
                    case CREATE_DIR -> {
                        createDir();
                    }
                    case DELETE -> {
                        
                    }
                        
                }
            }
    }
    
    private void createFile(){
        terminal.setText("");
        
        String name = outputs.get(0);
        String size = outputs.get(1);
        String path = pathOutput.getText();
        
        fileSystem.createFile(name, Integer.parseInt(size), path);
        drawTree();
        outputs.delete();
    }
    
    
    private void createDir(){
        terminal.setText("");
        
        String name = outputs.get(0);        
        String path = pathOutput.getText();
        
        fileSystem.createDirectory(name, path);
        drawTree();
        outputs.delete();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createMenu = new javax.swing.JPopupMenu();
        createFile = new javax.swing.JMenuItem();
        createDirectory = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTree = new javax.swing.JTree();
        pathOutput = new javax.swing.JTextField();
        create = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        terminal = new javax.swing.JTextArea();

        createMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createMenuMouseClicked(evt);
            }
        });

        createFile.setText("Crear Archivo");
        createFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFileActionPerformed(evt);
            }
        });
        createMenu.add(createFile);

        createDirectory.setText("Create Directorio");
        createDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createDirectoryActionPerformed(evt);
            }
        });
        createMenu.add(createDirectory);

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

        terminal.setEditable(false);
        terminal.setBackground(new java.awt.Color(0, 19, 66));
        terminal.setColumns(20);
        terminal.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        terminal.setForeground(new java.awt.Color(255, 255, 255));
        terminal.setRows(5);
        terminal.setText("\n");
        terminal.setCaretColor(new java.awt.Color(255, 255, 255));
        terminal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                terminalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                terminalKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(terminal);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 480));

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
        createMenu.show(create, 0, create.getHeight());
    }//GEN-LAST:event_createActionPerformed

    private void createMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createMenuMouseClicked
        createMenu.show(create, 0, create.getHeight());
    }//GEN-LAST:event_createMenuMouseClicked

    private void createFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFileActionPerformed
        instruction = CREATE_FILE;
        
        terminal.setEditable(true);
        terminal.setCaretColor(Color.WHITE);
        
        String input1 = "Ingrese el nombre del archivo: ";
        String input2 = "Ingrese el tamanio del archivo: ";
        
        inputs.append(input1);
        inputs.append(input2);
        
        handleInput();
    }//GEN-LAST:event_createFileActionPerformed

    private void terminalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_terminalKeyTyped
        //Si se presiona backspace
        if (evt.getKeyChar() == '\b') {             
            if (terminal.getCaret().getDot() <= inputLength) {
                terminal.setText(currentInput);
            }
        }
    }//GEN-LAST:event_terminalKeyTyped

    private void terminalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_terminalKeyPressed
        //Si se presiona enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            try{     
                outputs.append(terminal.getText().split(": ")[1]);                
                sem.release();
            }
            catch(Exception e){
                
            }
        }
    }//GEN-LAST:event_terminalKeyPressed

    private void createDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createDirectoryActionPerformed
        instruction = CREATE_DIR;
        
        terminal.setEditable(true);
        terminal.setCaretColor(Color.WHITE);
        
        String input1 = "Ingrese el nombre del directorio: ";        
        
        inputs.append(input1);        
        
        handleInput();
    }//GEN-LAST:event_createDirectoryActionPerformed

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
    private javax.swing.JMenuItem createDirectory;
    private javax.swing.JMenuItem createFile;
    private javax.swing.JPopupMenu createMenu;
    private javax.swing.JButton delete;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField pathOutput;
    private javax.swing.JTextArea terminal;
    // End of variables declaration//GEN-END:variables

    
}
