package org.mozilla.javascript.tools.debugger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

class FileHeader extends JPanel implements MouseListener {
    private static final long serialVersionUID = -2858905404778259127L;
    private FileWindow fileWindow;
    private int pressLine = -1;

    public FileHeader(FileWindow fileWindow2) {
        this.fileWindow = fileWindow2;
        addMouseListener(this);
        update();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        this.pressLine = mouseEvent.getY() / getFontMetrics(this.fileWindow.textArea.getFont()).getHeight();
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getComponent() == this && (mouseEvent.getModifiers() & 16) != 0) {
            int y = mouseEvent.getY() / getFontMetrics(this.fileWindow.textArea.getFont()).getHeight();
            if (y == this.pressLine) {
                this.fileWindow.toggleBreakPoint(y + 1);
            } else {
                this.pressLine = -1;
            }
        }
    }

    public void paint(Graphics graphics) {
        int i;
        FileHeader.super.paint(graphics);
        FileTextArea fileTextArea = this.fileWindow.textArea;
        Font font = fileTextArea.getFont();
        graphics.setFont(font);
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle clipBounds = graphics.getClipBounds();
        graphics.setColor(getBackground());
        graphics.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
        int maxAscent = fontMetrics.getMaxAscent();
        int height = fontMetrics.getHeight();
        int lineCount = fileTextArea.getLineCount() + 1;
        Integer.toString(lineCount).length();
        int i2 = clipBounds.y / height;
        int i3 = ((clipBounds.y + clipBounds.height) / height) + 1;
        int width = getWidth();
        if (i3 <= lineCount) {
            lineCount = i3;
        }
        while (i2 < lineCount) {
            try {
                i = fileTextArea.getLineStartOffset(i2);
            } catch (BadLocationException unused) {
                i = -2;
            }
            int i4 = i2 + 1;
            boolean isBreakPoint = this.fileWindow.isBreakPoint(i4);
            int i5 = i2 * height;
            graphics.setColor(Color.blue);
            int i6 = i5 + maxAscent;
            graphics.drawString(Integer.toString(i4) + " ", 0, i6);
            int i7 = width - maxAscent;
            if (isBreakPoint) {
                graphics.setColor(new Color(128, 0, 0));
                int i8 = i6 - 9;
                graphics.fillOval(i7, i8, 9, 9);
                graphics.drawOval(i7, i8, 8, 8);
                graphics.drawOval(i7, i8, 9, 9);
            }
            if (i == this.fileWindow.currentPos) {
                Polygon polygon = new Polygon();
                int i9 = (maxAscent - 10) + i5;
                int i10 = i9 + 3;
                polygon.addPoint(i7, i10);
                int i11 = i7 + 5;
                polygon.addPoint(i11, i10);
                int i12 = i9;
                int i13 = i11;
                while (i13 <= i7 + 10) {
                    polygon.addPoint(i13, i12);
                    i13++;
                    i12++;
                }
                int i14 = i7 + 9;
                while (i14 >= i11) {
                    polygon.addPoint(i14, i12);
                    i14--;
                    i12++;
                }
                int i15 = i9 + 7;
                polygon.addPoint(i11, i15);
                polygon.addPoint(i7, i15);
                graphics.setColor(Color.yellow);
                graphics.fillPolygon(polygon);
                graphics.setColor(Color.black);
                graphics.drawPolygon(polygon);
            }
            i2 = i4;
        }
    }

    public void update() {
        FileTextArea fileTextArea = this.fileWindow.textArea;
        Font font = fileTextArea.getFont();
        setFont(font);
        FontMetrics fontMetrics = getFontMetrics(font);
        int height = fontMetrics.getHeight();
        int lineCount = fileTextArea.getLineCount() + 1;
        String num = Integer.toString(lineCount);
        if (num.length() < 2) {
            num = "99";
        }
        Dimension dimension = new Dimension();
        dimension.width = fontMetrics.stringWidth(num) + 16;
        dimension.height = (lineCount * height) + 100;
        setPreferredSize(dimension);
        setSize(dimension);
    }
}
