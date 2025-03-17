/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Assets.LeftButtonBorder;
import Assets.MidButtonBorder;
import Assets.RightButtonBorder;
import Assets.TreeRender;
import Classes.Directory;
import Classes.FileSystem;
import Classes.JFile;
import Classes.Util;
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
    private final int DELETE_FILE = 2;
    private final int DELETE_DIR = 3;
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
            Directory currentDirectory = dirQueue.dequeue();
            
            List<Directory> currentDirectories = currentDirectory.getDirectories();
            List<JFile> currentFiles = currentDirectory.getFiles();
            DefaultMutableTreeNode currentNode = nodeQueue.dequeue();
            
            for (int i = 0; i < currentDirectories.getSize(); i++) {
                Directory directory = currentDirectories.get(i);
                
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(directory.getName());
                currentNode.add(newNode);
                
                dirQueue.enqueue(directory);
                nodeQueue.enqueue(newNode);
            }  
            
            for (int i = 0; i < currentFiles.getSize(); i++) {
                JFile file = currentFiles.get(i);                                
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName());
                currentNode.add(newNode);
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
        ImageIcon editIcon = new ImageIcon(Paths.get("src"+sp+"Assets"+sp+"edit.png").normalize().toString());

        Image createImage = createIcon.getImage();
        Image scaledImage = createImage.getScaledInstance((int)(create.getWidth()), (int)(create.getHeight() / 1.2), Image.SCALE_SMOOTH);
        create.setIcon(new ImageIcon(scaledImage));

        Image deleteImage = deleteIcon.getImage();
        scaledImage = deleteImage.getScaledInstance((int)(delete.getWidth()), (int)(delete.getHeight() / 1.2), Image.SCALE_SMOOTH);
        delete.setIcon(new ImageIcon(scaledImage));
        
        Image editImage = editIcon.getImage();
        scaledImage = editImage.getScaledInstance((int)(create.getWidth() / 1.2), (int)(create.getHeight() / 1.2), Image.SCALE_SMOOTH);
        edit.setIcon(new ImageIcon(scaledImage));
        
        delete.setBorder(new RightButtonBorder(Color.WHITE, 1));
        create.setBorder(new MidButtonBorder(Color.WHITE, 1));
        edit.setBorder(new LeftButtonBorder(Color.WHITE, 1));
        
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
                    case DELETE_FILE -> {
                        deleteFile();
                    }
                    case DELETE_DIR -> {
                        deleteDir();
                    }
                        
                }
            }
    }
    
    private void createFile(){        
        terminal.setEditable(false);
        
        String name = outputs.get(0);
        String size = outputs.get(1);
        String path = pathOutput.getText();
        outputs.delete();
        
        if(name.contains(".")){
            terminal.setText("Nombre no valido para un archivo");            
            return;
        }
        
        if(!Util.isNumeric(size)){
            terminal.setText("Tamaño ingresado no valido");            
            return;
        }
        
        String result = fileSystem.createFile(name, Integer.parseInt(size), path);
        drawTree();        
        
        terminal.setText(result);        
    }
    
    
    private void createDir(){
        terminal.setEditable(false);
        
        terminal.setText("");                
        String name = outputs.get(0);        
        String path = pathOutput.getText();
        outputs.delete();
        
        if(name.contains(".")){
            terminal.setText("Nombre no valido para un directorio");
            return;
        }                
        
        String result = fileSystem.createDirectory(name, path);
        drawTree();        
                
        terminal.setText(result);                                   
    }
    
    
    private void deleteFile(){
        terminal.setEditable(false);
        
        terminal.setText("");                
        String option = outputs.get(0);  
        String path = pathOutput.getText();
        outputs.delete();
        
        if(!option.toLowerCase().equals("y") && !option.toLowerCase().equals("n")){
            terminal.setText("Entrada no valida");
            return;
        }
        
        if(option.toLowerCase().equals("n")){
            terminal.setText("Operacion abortada");
            return;
        }
        
        String result = fileSystem.deleteFile(path);
        drawTree();        
        
        terminal.setText(result);
    }
    
    
    public void deleteDir(){
        terminal.setEditable(false);
        
        terminal.setText("");                
        String option = outputs.get(0);  
        String path = pathOutput.getText();
        outputs.delete();
        
        if(!option.toLowerCase().equals("y") && !option.toLowerCase().equals("n")){
            terminal.setText("Entrada no valida");
            return;
        }
        
        if(option.toLowerCase().equals("n")){
            terminal.setText("Operacion abortada");
            return;
        }
        
        String result = fileSystem.deleteDirectory(path);
        drawTree();        
        
        terminal.setText(result);
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
        createDir = new javax.swing.JMenuItem();
        deleteMenu = new javax.swing.JPopupMenu();
        deleteFile = new javax.swing.JMenuItem();
        deleteDir = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTree = new javax.swing.JTree();
        pathOutput = new javax.swing.JTextField();
        create = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        terminal = new javax.swing.JTextArea();
        leftPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        edit = new javax.swing.JButton();

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

        createDir.setText("Create Directorio");
        createDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createDirActionPerformed(evt);
            }
        });
        createMenu.add(createDir);

        deleteFile.setText("Eliminar archivo");
        deleteFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFileActionPerformed(evt);
            }
        });
        deleteMenu.add(deleteFile);

        deleteDir.setText("Eliminar Directorio");
        deleteDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDirActionPerformed(evt);
            }
        });
        deleteMenu.add(deleteDir);

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 680, 330));

        pathOutput.setEditable(false);
        pathOutput.setBackground(new java.awt.Color(51, 51, 51));
        pathOutput.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        pathOutput.setForeground(new java.awt.Color(255, 255, 255));
        pathOutput.setText("root/");
        pathOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathOutputActionPerformed(evt);
            }
        });
        jPanel1.add(pathOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 590, 30));

        create.setBackground(new java.awt.Color(0, 0, 0));
        create.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        create.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });
        jPanel1.add(create, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 30, 30));

        delete.setBackground(new java.awt.Color(0, 0, 0));
        delete.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        delete.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
        });
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel1.add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 30, 30));

        terminal.setEditable(false);
        terminal.setBackground(new java.awt.Color(0, 12, 44));
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

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 680, 180));

        leftPanel.setBackground(new java.awt.Color(0, 19, 66));
        leftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(243, 243, 243));
        jButton1.setText("Guardar Cambios");
        leftPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 130, 40));

        jPanel1.add(leftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 540));

        edit.setBackground(new java.awt.Color(0, 0, 0));
        edit.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        edit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        jPanel1.add(edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 30, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        if(pathOutput.getText().contains(".file")) {
            terminal.setText("No se puede crear un archivo en esta ruta");
            return;
        }
        
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
            if ((terminal.getCaret().getDot() <= inputLength) && (terminal.isEditable())) {
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

    private void createDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createDirActionPerformed
        if(pathOutput.getText().contains(".file")){
            terminal.setText("No se puede crear un directorio en esta ruta");
            return;
        }
        
        instruction = CREATE_DIR;
        
        terminal.setEditable(true);
        terminal.setCaretColor(Color.WHITE);
        
        String input = "Ingrese el nombre del directorio: ";        
        
        inputs.append(input);        
        
        handleInput();
    }//GEN-LAST:event_createDirActionPerformed

    private void deleteFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFileActionPerformed
        if(!pathOutput.getText().contains(".file")){
            terminal.setText("No se puede borrar en esta ruta");
            return;
        }
        
        instruction = DELETE_FILE;
        
        terminal.setEditable(true);
        terminal.setCaretColor(Color.WHITE);
        
        String input = "Desea borrar el archivo? (y/n): ";        
        
        inputs.append(input);        
        
        handleInput();
    }//GEN-LAST:event_deleteFileActionPerformed

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
        terminal.setEditable(true);
        terminal.setCaretColor(Color.WHITE);
        
        String input;
        if(pathOutput.getText().contains(".file")){
            instruction = DELETE_FILE;
            
            input = "Desea borrar el archivo? (y/n): ";        
        }
        else{
            instruction = DELETE_DIR;
                              
            input = "Desea borrar el directorio (Todos los archivos dentro del direcotiro seran borrados)? (y/n): ";        
        }
        
        inputs.append(input);                            
        handleInput();                        
    }//GEN-LAST:event_deleteMouseClicked

    private void deleteDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDirActionPerformed
        if(pathOutput.getText().contains(".file")){
            terminal.setText("No se puede borrar en esta ruta");
            return;
        }
        
        instruction = DELETE_DIR;
        
        terminal.setEditable(true);
        terminal.setCaretColor(Color.WHITE);
        
        String input = "Desea borrar el directorio (Todos los archivos dentro del direcotiro seran borrados)? (y/n): ";        
        
        inputs.append(input);        
        
        handleInput();
    }//GEN-LAST:event_deleteDirActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteActionPerformed

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
    private javax.swing.JMenuItem createDir;
    private javax.swing.JMenuItem createFile;
    private javax.swing.JPopupMenu createMenu;
    private javax.swing.JButton delete;
    private javax.swing.JMenuItem deleteDir;
    private javax.swing.JMenuItem deleteFile;
    private javax.swing.JPopupMenu deleteMenu;
    private javax.swing.JButton edit;
    private javax.swing.JPopupMenu editMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTextField pathOutput;
    private javax.swing.JTextArea terminal;
    // End of variables declaration//GEN-END:variables

    
}
