/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assets;
    import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author Juan
 */
public class TreeRender extends DefaultTreeCellRenderer{

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        // Cambiar el color del texto aquí
        setForeground(Color.WHITE); // Cambia el color a azul (puedes usar cualquier color)
        setBackground(new Color(0, 15, 36)); // Cambia el color a azul (puedes usar cualquier color)
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        
        if (sel) {
            // Cambiar el fondo de la selección (fondo del texto)
            setBackgroundSelectionColor(new Color(100, 100, 100)); // Un gris más claro para la selección
        } else {
            // Asegurar que el fondo sea el color deseado cuando no está seleccionado
            setBackgroundNonSelectionColor(new Color(0, 15, 36));
        }
        
        String sp = File.separator;
        ImageIcon dirIcon = new ImageIcon(Paths.get("src"+sp+"Assets"+sp+"file.png").normalize().toString());
        Image dirImage = dirIcon.getImage();
        Image scaledDirImage = dirImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        dirIcon = new ImageIcon(scaledDirImage);
        
        ImageIcon fileIcon = new ImageIcon(Paths.get("src"+sp+"Assets"+sp+"fileText.png").normalize().toString());
        Image fileImage = fileIcon.getImage();
        Image scaledFileImage = fileImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        fileIcon = new ImageIcon(scaledFileImage);
                
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        String path = (String) node.getUserObject(); // Obtener la ruta del userObject

        File file = new File(path); // Crear un objeto File a partir de la ruta

        if(file.getName().contains(".file")){
            System.out.println("file");
            setIcon(fileIcon);
        } 
        else{
            System.out.println("dir");
            setIcon(dirIcon);
        }
        
        return this;
    }
    
    
}
