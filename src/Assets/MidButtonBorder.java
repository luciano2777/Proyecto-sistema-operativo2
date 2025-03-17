/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author Juan
 */
public class MidButtonBorder extends AbstractBorder{
    private Color color;
    private int thickness;

    public MidButtonBorder(Color color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        // Top
        g.fillRect(x, y, width, thickness);
        // Bottom
        g.fillRect(x, height - thickness, width, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(thickness, 0, thickness, thickness); // top, right, bottom
        return insets;
    }

    public Insets getBorderInsets(Component c, Insets insets, Insets rectangle) {
        return getBorderInsets(c, insets);
    }
}
