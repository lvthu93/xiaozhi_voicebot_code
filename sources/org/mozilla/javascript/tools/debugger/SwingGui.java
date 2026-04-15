package org.mozilla.javascript.tools.debugger;

import j$.util.DesugarCollections;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MenuComponent;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import org.mozilla.javascript.SecurityUtilities;
import org.mozilla.javascript.tools.debugger.Dim;

public class SwingGui extends JFrame implements GuiCallback {
    private static final long serialVersionUID = -8217029773456711621L;
    private EventQueue awtEventQueue;
    private JSInternalConsole console;
    private ContextWindow context;
    private FileWindow currentWindow;
    private JDesktopPane desk;
    Dim dim;
    JFileChooser dlg;
    private Runnable exitAction;
    private final Map<String, FileWindow> fileWindows = DesugarCollections.synchronizedMap(new TreeMap());
    private Menubar menubar;
    private JSplitPane split1;
    private JLabel statusBar;
    private JToolBar toolBar;
    private final Map<String, JFrame> toplevels = DesugarCollections.synchronizedMap(new HashMap());

    public SwingGui(Dim dim2, String str) {
        super(str);
        this.dim = dim2;
        init();
        dim2.setGuiCallback(this);
    }

    private String chooseFile(String str) {
        File file;
        this.dlg.setDialogTitle(str);
        String systemProperty = SecurityUtilities.getSystemProperty("user.dir");
        if (systemProperty != null) {
            file = new File(systemProperty);
        } else {
            file = null;
        }
        if (file != null) {
            this.dlg.setCurrentDirectory(file);
        }
        if (this.dlg.showOpenDialog(this) == 0) {
            try {
                String canonicalPath = this.dlg.getSelectedFile().getCanonicalPath();
                File parentFile = this.dlg.getSelectedFile().getParentFile();
                Properties properties = System.getProperties();
                properties.put("user.dir", parentFile.getPath());
                System.setProperties(properties);
                return canonicalPath;
            } catch (IOException | SecurityException unused) {
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void exit() {
        Runnable runnable = this.exitAction;
        if (runnable != null) {
            SwingUtilities.invokeLater(runnable);
        }
        this.dim.setReturnValue(5);
    }

    private JInternalFrame getSelectedFrame() {
        JInternalFrame[] allFrames = this.desk.getAllFrames();
        for (int i = 0; i < allFrames.length; i++) {
            if (allFrames[i].isShowing()) {
                return allFrames[i];
            }
        }
        return allFrames[allFrames.length - 1];
    }

    public static String getShortName(String str) {
        int i;
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf < 0) {
            lastIndexOf = str.lastIndexOf(92);
        }
        if (lastIndexOf < 0 || (i = lastIndexOf + 1) >= str.length()) {
            return str;
        }
        return str.substring(i);
    }

    private JMenu getWindowMenu() {
        return this.menubar.getMenu(3);
    }

    private void init() {
        Menubar menubar2 = new Menubar(this);
        this.menubar = menubar2;
        setJMenuBar(menubar2);
        this.toolBar = new JToolBar();
        String[] strArr = {"Break (Pause)", "Go (F5)", "Step Into (F11)", "Step Over (F7)", "Step Out (F8)"};
        JButton jButton = new JButton("Break");
        jButton.setToolTipText("Break");
        jButton.setActionCommand("Break");
        jButton.addActionListener(this.menubar);
        jButton.setEnabled(true);
        jButton.setToolTipText(strArr[0]);
        JButton jButton2 = new JButton("Go");
        jButton2.setToolTipText("Go");
        jButton2.setActionCommand("Go");
        jButton2.addActionListener(this.menubar);
        jButton2.setEnabled(false);
        jButton2.setToolTipText(strArr[1]);
        JButton jButton3 = new JButton("Step Into");
        jButton3.setToolTipText("Step Into");
        jButton3.setActionCommand("Step Into");
        jButton3.addActionListener(this.menubar);
        jButton3.setEnabled(false);
        jButton3.setToolTipText(strArr[2]);
        JButton jButton4 = new JButton("Step Over");
        jButton4.setToolTipText("Step Over");
        jButton4.setActionCommand("Step Over");
        jButton4.setEnabled(false);
        jButton4.addActionListener(this.menubar);
        jButton4.setToolTipText(strArr[3]);
        JButton jButton5 = new JButton("Step Out");
        jButton5.setToolTipText("Step Out");
        jButton5.setActionCommand("Step Out");
        jButton5.setEnabled(false);
        jButton5.addActionListener(this.menubar);
        jButton5.setToolTipText(strArr[4]);
        Dimension preferredSize = jButton4.getPreferredSize();
        jButton.setPreferredSize(preferredSize);
        jButton.setMinimumSize(preferredSize);
        jButton.setMaximumSize(preferredSize);
        jButton.setSize(preferredSize);
        jButton2.setPreferredSize(preferredSize);
        jButton2.setMinimumSize(preferredSize);
        jButton2.setMaximumSize(preferredSize);
        jButton3.setPreferredSize(preferredSize);
        jButton3.setMinimumSize(preferredSize);
        jButton3.setMaximumSize(preferredSize);
        jButton4.setPreferredSize(preferredSize);
        jButton4.setMinimumSize(preferredSize);
        jButton4.setMaximumSize(preferredSize);
        jButton5.setPreferredSize(preferredSize);
        jButton5.setMinimumSize(preferredSize);
        jButton5.setMaximumSize(preferredSize);
        this.toolBar.add(jButton);
        this.toolBar.add(jButton2);
        this.toolBar.add(jButton3);
        this.toolBar.add(jButton4);
        this.toolBar.add(jButton5);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        getContentPane().add(this.toolBar, "North");
        getContentPane().add(jPanel, "Center");
        JDesktopPane jDesktopPane = new JDesktopPane();
        this.desk = jDesktopPane;
        jDesktopPane.setPreferredSize(new Dimension(600, 300));
        this.desk.setMinimumSize(new Dimension(150, 50));
        JDesktopPane jDesktopPane2 = this.desk;
        JSInternalConsole jSInternalConsole = new JSInternalConsole("JavaScript Console");
        this.console = jSInternalConsole;
        jDesktopPane2.add(jSInternalConsole);
        ContextWindow contextWindow = new ContextWindow(this);
        this.context = contextWindow;
        contextWindow.setPreferredSize(new Dimension(600, 120));
        this.context.setMinimumSize(new Dimension(50, 50));
        JSplitPane jSplitPane = new JSplitPane(0, this.desk, this.context);
        this.split1 = jSplitPane;
        jSplitPane.setOneTouchExpandable(true);
        setResizeWeight(this.split1, 0.66d);
        jPanel.add(this.split1, "Center");
        JLabel jLabel = new JLabel();
        this.statusBar = jLabel;
        jLabel.setText("Thread: ");
        jPanel.add(this.statusBar, "South");
        this.dlg = new JFileChooser();
        this.dlg.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String name = file.getName();
                int lastIndexOf = name.lastIndexOf(46);
                if (lastIndexOf <= 0 || lastIndexOf >= name.length() - 1 || !name.substring(lastIndexOf + 1).toLowerCase().equals("js")) {
                    return false;
                }
                return true;
            }

            public String getDescription() {
                return "JavaScript Files (*.js)";
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                SwingGui.this.exit();
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0018, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000f, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String readFile(java.lang.String r4) {
        /*
            r3 = this;
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ IOException -> 0x0019 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0019 }
            java.lang.String r1 = org.mozilla.javascript.Kit.readReader(r0)     // Catch:{ all -> 0x000d }
            r0.close()     // Catch:{ IOException -> 0x0019 }
            goto L_0x0029
        L_0x000d:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0014 }
            goto L_0x0018
        L_0x0014:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch:{ IOException -> 0x0019 }
        L_0x0018:
            throw r2     // Catch:{ IOException -> 0x0019 }
        L_0x0019:
            r0 = move-exception
            java.lang.String r0 = r0.getMessage()
            java.lang.String r1 = "Error reading "
            java.lang.String r4 = defpackage.y2.i(r1, r4)
            r1 = 0
            org.mozilla.javascript.tools.debugger.MessageDialogWrapper.showMessageDialog(r3, r0, r4, r1)
            r1 = 0
        L_0x0029:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.debugger.SwingGui.readFile(java.lang.String):java.lang.String");
    }

    private void setFilePosition(FileWindow fileWindow, int i) {
        FileTextArea fileTextArea = fileWindow.textArea;
        if (i == -1) {
            try {
                fileWindow.setPosition(-1);
                if (this.currentWindow == fileWindow) {
                    this.currentWindow = null;
                }
            } catch (BadLocationException unused) {
            }
        } else {
            int lineStartOffset = fileTextArea.getLineStartOffset(i - 1);
            FileWindow fileWindow2 = this.currentWindow;
            if (!(fileWindow2 == null || fileWindow2 == fileWindow)) {
                fileWindow2.setPosition(-1);
            }
            fileWindow.setPosition(lineStartOffset);
            this.currentWindow = fileWindow;
        }
        if (fileWindow.isIcon()) {
            this.desk.getDesktopManager().deiconifyFrame(fileWindow);
        }
        this.desk.getDesktopManager().activateFrame(fileWindow);
        try {
            fileWindow.show();
            fileWindow.toFront();
            fileWindow.setSelected(true);
        } catch (Exception unused2) {
        }
    }

    public static void setResizeWeight(JSplitPane jSplitPane, double d) {
        Class<JSplitPane> cls = JSplitPane.class;
        try {
            cls.getMethod("setResizeWeight", new Class[]{Double.TYPE}).invoke(jSplitPane, new Object[]{new Double(d)});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
    }

    private void updateEnabled(boolean z) {
        boolean z2;
        getJMenuBar().updateEnabled(z);
        int componentCount = this.toolBar.getComponentCount();
        for (int i = 0; i < componentCount; i++) {
            if (i == 0) {
                z2 = !z;
            } else {
                z2 = z;
            }
            this.toolBar.getComponent(i).setEnabled(z2);
        }
        if (z) {
            this.toolBar.setEnabled(true);
            if (getExtendedState() == 1) {
                setExtendedState(0);
            }
            toFront();
            this.context.setEnabled(true);
            return;
        }
        FileWindow fileWindow = this.currentWindow;
        if (fileWindow != null) {
            fileWindow.setPosition(-1);
        }
        this.context.setEnabled(false);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x023d  */
    /* JADX WARNING: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void actionPerformed(java.awt.event.ActionEvent r20) {
        /*
            r19 = this;
            r7 = r19
            java.lang.String r0 = r20.getActionCommand()
            java.lang.String r1 = "Cut"
            boolean r2 = r0.equals(r1)
            r8 = 0
            r9 = -1
            if (r2 != 0) goto L_0x0229
            java.lang.String r2 = "Copy"
            boolean r3 = r0.equals(r2)
            if (r3 != 0) goto L_0x0229
            java.lang.String r3 = "Paste"
            boolean r4 = r0.equals(r3)
            if (r4 == 0) goto L_0x0022
            goto L_0x0229
        L_0x0022:
            java.lang.String r4 = "Step Over"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x002d
            r5 = 0
            goto L_0x023b
        L_0x002d:
            java.lang.String r4 = "Step Into"
            boolean r4 = r0.equals(r4)
            r5 = 1
            if (r4 == 0) goto L_0x0038
            goto L_0x023b
        L_0x0038:
            java.lang.String r4 = "Step Out"
            boolean r4 = r0.equals(r4)
            r6 = 2
            if (r4 == 0) goto L_0x0044
            r5 = 2
            goto L_0x023b
        L_0x0044:
            java.lang.String r4 = "Go"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x004f
            r5 = 3
            goto L_0x023b
        L_0x004f:
            java.lang.String r4 = "Break"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x005e
            org.mozilla.javascript.tools.debugger.Dim r0 = r7.dim
            r0.setBreak()
            goto L_0x023a
        L_0x005e:
            java.lang.String r4 = "Exit"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x006b
            r19.exit()
            goto L_0x023a
        L_0x006b:
            java.lang.String r4 = "Open"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0094
            java.lang.String r0 = "Select a file to compile"
            java.lang.String r0 = r7.chooseFile(r0)
            if (r0 == 0) goto L_0x023a
            java.lang.String r1 = r7.readFile(r0)
            if (r1 == 0) goto L_0x023a
            org.mozilla.javascript.tools.debugger.RunProxy r2 = new org.mozilla.javascript.tools.debugger.RunProxy
            r2.<init>(r7, r5)
            r2.fileName = r0
            r2.text = r1
            java.lang.Thread r0 = new java.lang.Thread
            r0.<init>(r2)
            r0.start()
            goto L_0x023a
        L_0x0094:
            java.lang.String r4 = "Load"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x00bd
            java.lang.String r0 = "Select a file to execute"
            java.lang.String r0 = r7.chooseFile(r0)
            if (r0 == 0) goto L_0x023a
            java.lang.String r1 = r7.readFile(r0)
            if (r1 == 0) goto L_0x023a
            org.mozilla.javascript.tools.debugger.RunProxy r2 = new org.mozilla.javascript.tools.debugger.RunProxy
            r2.<init>(r7, r6)
            r2.fileName = r0
            r2.text = r1
            java.lang.Thread r0 = new java.lang.Thread
            r0.<init>(r2)
            r0.start()
            goto L_0x023a
        L_0x00bd:
            java.lang.String r4 = "More Windows..."
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x00d5
            org.mozilla.javascript.tools.debugger.MoreWindows r0 = new org.mozilla.javascript.tools.debugger.MoreWindows
            java.util.Map<java.lang.String, org.mozilla.javascript.tools.debugger.FileWindow> r1 = r7.fileWindows
            java.lang.String r2 = "Window"
            java.lang.String r3 = "Files"
            r0.<init>(r7, r1, r2, r3)
            r0.showDialog(r7)
            goto L_0x023a
        L_0x00d5:
            java.lang.String r4 = "Console"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0109
            org.mozilla.javascript.tools.debugger.JSInternalConsole r0 = r7.console
            boolean r0 = r0.isIcon()
            if (r0 == 0) goto L_0x00f0
            javax.swing.JDesktopPane r0 = r7.desk
            javax.swing.DesktopManager r0 = r0.getDesktopManager()
            org.mozilla.javascript.tools.debugger.JSInternalConsole r1 = r7.console
            r0.deiconifyFrame(r1)
        L_0x00f0:
            org.mozilla.javascript.tools.debugger.JSInternalConsole r0 = r7.console
            r0.show()
            javax.swing.JDesktopPane r0 = r7.desk
            javax.swing.DesktopManager r0 = r0.getDesktopManager()
            org.mozilla.javascript.tools.debugger.JSInternalConsole r1 = r7.console
            r0.activateFrame(r1)
            org.mozilla.javascript.tools.debugger.JSInternalConsole r0 = r7.console
            org.mozilla.javascript.tools.shell.ConsoleTextArea r0 = r0.consoleTextArea
            r0.requestFocus()
            goto L_0x023a
        L_0x0109:
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0111
            goto L_0x023a
        L_0x0111:
            boolean r1 = r0.equals(r2)
            if (r1 == 0) goto L_0x0119
            goto L_0x023a
        L_0x0119:
            boolean r1 = r0.equals(r3)
            if (r1 == 0) goto L_0x0121
            goto L_0x023a
        L_0x0121:
            java.lang.String r1 = "Go to function..."
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0137
            org.mozilla.javascript.tools.debugger.FindFunction r0 = new org.mozilla.javascript.tools.debugger.FindFunction
            java.lang.String r1 = "Go to function"
            java.lang.String r2 = "Function"
            r0.<init>(r7, r1, r2)
            r0.showDialog(r7)
            goto L_0x023a
        L_0x0137:
            java.lang.String r1 = "Go to line..."
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x016a
            java.lang.String r1 = "Line number"
            java.lang.String r2 = "Go to line..."
            r3 = 3
            r4 = 0
            r5 = 0
            r6 = 0
            r0 = r19
            java.lang.Object r0 = javax.swing.JOptionPane.showInputDialog(r0, r1, r2, r3, r4, r5, r6)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x0169
            java.lang.String r1 = r0.trim()
            int r1 = r1.length()
            if (r1 != 0) goto L_0x015c
            goto L_0x0169
        L_0x015c:
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0166 }
            r1 = 0
            r7.showFileWindow(r1, r0)     // Catch:{ NumberFormatException -> 0x0166 }
            goto L_0x023a
        L_0x0166:
            goto L_0x023a
        L_0x0169:
            return
        L_0x016a:
            java.lang.String r1 = "Tile"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x01cf
            javax.swing.JDesktopPane r0 = r7.desk
            javax.swing.JInternalFrame[] r0 = r0.getAllFrames()
            int r1 = r0.length
            double r2 = (double) r1
            double r2 = java.lang.Math.sqrt(r2)
            int r2 = (int) r2
            int r3 = r2 * r2
            if (r3 >= r1) goto L_0x0191
            int r3 = r2 + 1
            int r4 = r2 * r3
            if (r4 >= r1) goto L_0x018b
            r2 = r3
            goto L_0x0192
        L_0x018b:
            r18 = r3
            r3 = r2
            r2 = r18
            goto L_0x0192
        L_0x0191:
            r3 = r2
        L_0x0192:
            javax.swing.JDesktopPane r1 = r7.desk
            java.awt.Dimension r1 = r1.getSize()
            int r4 = r1.width
            int r4 = r4 / r2
            int r1 = r1.height
            int r1 = r1 / r3
            r5 = 0
            r6 = 0
        L_0x01a0:
            if (r5 >= r3) goto L_0x023a
            r15 = 0
            r16 = 0
        L_0x01a5:
            if (r15 >= r2) goto L_0x01cb
            int r10 = r5 * r2
            int r10 = r10 + r15
            int r11 = r0.length
            if (r10 < r11) goto L_0x01ae
            goto L_0x01cb
        L_0x01ae:
            r11 = r0[r10]
            r11.setIcon(r8)     // Catch:{ Exception -> 0x01b6 }
            r11.setMaximum(r8)     // Catch:{ Exception -> 0x01b6 }
        L_0x01b6:
            javax.swing.JDesktopPane r10 = r7.desk
            javax.swing.DesktopManager r10 = r10.getDesktopManager()
            r12 = r16
            r13 = r6
            r14 = r4
            r17 = r15
            r15 = r1
            r10.setBoundsForFrame(r11, r12, r13, r14, r15)
            int r16 = r16 + r4
            int r15 = r17 + 1
            goto L_0x01a5
        L_0x01cb:
            int r6 = r6 + r1
            int r5 = r5 + 1
            goto L_0x01a0
        L_0x01cf:
            java.lang.String r1 = "Cascade"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0210
            javax.swing.JDesktopPane r0 = r7.desk
            javax.swing.JInternalFrame[] r0 = r0.getAllFrames()
            int r1 = r0.length
            javax.swing.JDesktopPane r2 = r7.desk
            int r2 = r2.getHeight()
            int r2 = r2 / r1
            r3 = 30
            if (r2 <= r3) goto L_0x01eb
            r2 = 30
        L_0x01eb:
            int r1 = r1 - r5
            r3 = 0
            r4 = 0
        L_0x01ee:
            if (r1 < 0) goto L_0x023a
            r11 = r0[r1]
            r11.setIcon(r8)     // Catch:{ Exception -> 0x01f8 }
            r11.setMaximum(r8)     // Catch:{ Exception -> 0x01f8 }
        L_0x01f8:
            java.awt.Dimension r5 = r11.getPreferredSize()
            int r14 = r5.width
            int r15 = r5.height
            javax.swing.JDesktopPane r5 = r7.desk
            javax.swing.DesktopManager r10 = r5.getDesktopManager()
            r12 = r3
            r13 = r4
            r10.setBoundsForFrame(r11, r12, r13, r14, r15)
            int r1 = r1 + -1
            int r3 = r3 + r2
            int r4 = r4 + r2
            goto L_0x01ee
        L_0x0210:
            org.mozilla.javascript.tools.debugger.FileWindow r0 = r7.getFileWindow(r0)
            if (r0 == 0) goto L_0x023a
            boolean r1 = r0.isIcon()     // Catch:{  }
            if (r1 == 0) goto L_0x021f
            r0.setIcon(r8)     // Catch:{  }
        L_0x021f:
            r0.setVisible(r5)     // Catch:{  }
            r0.moveToFront()     // Catch:{  }
            r0.setSelected(r5)     // Catch:{  }
            goto L_0x023a
        L_0x0229:
            javax.swing.JInternalFrame r0 = r19.getSelectedFrame()
            if (r0 == 0) goto L_0x023a
            boolean r1 = r0 instanceof java.awt.event.ActionListener
            if (r1 == 0) goto L_0x023a
            java.awt.event.ActionListener r0 = (java.awt.event.ActionListener) r0
            r1 = r20
            r0.actionPerformed(r1)
        L_0x023a:
            r5 = -1
        L_0x023b:
            if (r5 == r9) goto L_0x0245
            r7.updateEnabled(r8)
            org.mozilla.javascript.tools.debugger.Dim r0 = r7.dim
            r0.setReturnValue(r5)
        L_0x0245:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.debugger.SwingGui.actionPerformed(java.awt.event.ActionEvent):void");
    }

    public void addTopLevel(String str, JFrame jFrame) {
        if (jFrame != this) {
            this.toplevels.put(str, jFrame);
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0024 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void createFileWindow(org.mozilla.javascript.tools.debugger.Dim.SourceInfo r5, int r6) {
        /*
            r4 = this;
            java.lang.String r0 = r5.url()
            org.mozilla.javascript.tools.debugger.FileWindow r1 = new org.mozilla.javascript.tools.debugger.FileWindow
            r1.<init>(r4, r5)
            java.util.Map<java.lang.String, org.mozilla.javascript.tools.debugger.FileWindow> r5 = r4.fileWindows
            r5.put(r0, r1)
            r5 = -1
            if (r6 == r5) goto L_0x0032
            org.mozilla.javascript.tools.debugger.FileWindow r2 = r4.currentWindow
            if (r2 == 0) goto L_0x0018
            r2.setPosition(r5)
        L_0x0018:
            org.mozilla.javascript.tools.debugger.FileTextArea r2 = r1.textArea     // Catch:{ BadLocationException -> 0x0024 }
            int r3 = r6 + -1
            int r2 = r2.getLineStartOffset(r3)     // Catch:{ BadLocationException -> 0x0024 }
            r1.setPosition(r2)     // Catch:{ BadLocationException -> 0x0024 }
            goto L_0x0032
        L_0x0024:
            org.mozilla.javascript.tools.debugger.FileTextArea r2 = r1.textArea     // Catch:{ BadLocationException -> 0x002f }
            r3 = 0
            int r2 = r2.getLineStartOffset(r3)     // Catch:{ BadLocationException -> 0x002f }
            r1.setPosition(r2)     // Catch:{ BadLocationException -> 0x002f }
            goto L_0x0032
        L_0x002f:
            r1.setPosition(r5)
        L_0x0032:
            javax.swing.JDesktopPane r2 = r4.desk
            r2.add(r1)
            if (r6 == r5) goto L_0x003b
            r4.currentWindow = r1
        L_0x003b:
            org.mozilla.javascript.tools.debugger.Menubar r5 = r4.menubar
            r5.addFile(r0)
            r5 = 1
            r1.setVisible(r5)
            r1.setMaximum(r5)     // Catch:{ Exception -> 0x004d }
            r1.setSelected(r5)     // Catch:{ Exception -> 0x004d }
            r1.moveToFront()     // Catch:{ Exception -> 0x004d }
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.debugger.SwingGui.createFileWindow(org.mozilla.javascript.tools.debugger.Dim$SourceInfo, int):void");
    }

    public void dispatchNextGuiEvent() throws InterruptedException {
        EventQueue eventQueue = this.awtEventQueue;
        if (eventQueue == null) {
            eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
            this.awtEventQueue = eventQueue;
        }
        ActiveEvent nextEvent = eventQueue.getNextEvent();
        if (nextEvent instanceof ActiveEvent) {
            nextEvent.dispatch();
            return;
        }
        Object source = nextEvent.getSource();
        if (source instanceof Component) {
            ((Component) source).dispatchEvent(nextEvent);
        } else if (source instanceof MenuComponent) {
            ((MenuComponent) source).dispatchEvent(nextEvent);
        }
    }

    public void enterInterrupt(Dim.StackFrame stackFrame, String str, String str2) {
        if (SwingUtilities.isEventDispatchThread()) {
            enterInterruptImpl(stackFrame, str, str2);
            return;
        }
        RunProxy runProxy = new RunProxy(this, 4);
        runProxy.lastFrame = stackFrame;
        runProxy.threadTitle = str;
        runProxy.alertMessage = str2;
        SwingUtilities.invokeLater(runProxy);
    }

    public void enterInterruptImpl(Dim.StackFrame stackFrame, String str, String str2) {
        String str3;
        this.statusBar.setText(y2.i("Thread: ", str));
        showStopLine(stackFrame);
        if (str2 != null) {
            MessageDialogWrapper.showMessageDialog(this, str2, "Exception in Script", 0);
        }
        updateEnabled(true);
        Dim.ContextData contextData = stackFrame.contextData();
        ContextWindow contextWindow = this.context;
        JComboBox<String> jComboBox = contextWindow.context;
        List<String> list = contextWindow.toolTips;
        contextWindow.disableUpdate();
        int frameCount = contextData.frameCount();
        jComboBox.removeAllItems();
        jComboBox.setSelectedItem((Object) null);
        list.clear();
        for (int i = 0; i < frameCount; i++) {
            Dim.StackFrame frame = contextData.getFrame(i);
            String url = frame.getUrl();
            int lineNumber = frame.getLineNumber();
            if (url.length() > 20) {
                str3 = "..." + url.substring(url.length() - 17);
            } else {
                str3 = url;
            }
            jComboBox.insertItemAt("\"" + str3 + "\", line " + lineNumber, i);
            list.add("\"" + url + "\", line " + lineNumber);
        }
        this.context.enableUpdate();
        jComboBox.setSelectedIndex(0);
        jComboBox.setMinimumSize(new Dimension(50, jComboBox.getMinimumSize().height));
    }

    public JSInternalConsole getConsole() {
        return this.console;
    }

    public FileWindow getFileWindow(String str) {
        if (str == null || str.equals("<stdin>")) {
            return null;
        }
        return this.fileWindows.get(str);
    }

    public Menubar getMenubar() {
        return this.menubar;
    }

    public boolean isGuiEventThread() {
        return SwingUtilities.isEventDispatchThread();
    }

    public void removeWindow(FileWindow fileWindow) {
        this.fileWindows.remove(fileWindow.getUrl());
        JMenu windowMenu = getWindowMenu();
        int itemCount = windowMenu.getItemCount();
        int i = itemCount - 1;
        JMenuItem item = windowMenu.getItem(i);
        String shortName = getShortName(fileWindow.getUrl());
        int i2 = 5;
        while (true) {
            if (i2 >= itemCount) {
                break;
            }
            JMenuItem item2 = windowMenu.getItem(i2);
            if (item2 != null) {
                String text = item2.getText();
                if (text.substring(text.indexOf(32) + 1).equals(shortName)) {
                    windowMenu.remove(item2);
                    if (itemCount == 6) {
                        windowMenu.remove(4);
                    } else {
                        int i3 = i2 - 4;
                        while (i2 < i) {
                            JMenuItem item3 = windowMenu.getItem(i2);
                            if (item3 != null) {
                                String text2 = item3.getText();
                                if (text2.equals("More Windows...")) {
                                    break;
                                }
                                int indexOf = text2.indexOf(32);
                                StringBuilder sb = new StringBuilder();
                                int i4 = i3 + 48;
                                sb.append((char) i4);
                                sb.append(" ");
                                sb.append(text2.substring(indexOf + 1));
                                item3.setText(sb.toString());
                                item3.setMnemonic(i4);
                                i3++;
                            }
                            i2++;
                        }
                        if (itemCount - 6 == 0 && item != item2 && item.getText().equals("More Windows...")) {
                            windowMenu.remove(item);
                        }
                    }
                }
            }
            i2++;
        }
        windowMenu.revalidate();
    }

    public void setExitAction(Runnable runnable) {
        this.exitAction = runnable;
    }

    public void setVisible(boolean z) {
        SwingGui.super.setVisible(z);
        if (z) {
            this.console.consoleTextArea.requestFocus();
            this.context.split.setDividerLocation(0.5d);
            try {
                this.console.setMaximum(true);
                this.console.setSelected(true);
                this.console.show();
                this.console.consoleTextArea.requestFocus();
            } catch (Exception unused) {
            }
        }
    }

    public void showFileWindow(String str, int i) {
        FileWindow fileWindow;
        if (str != null) {
            fileWindow = getFileWindow(str);
        } else {
            JInternalFrame selectedFrame = getSelectedFrame();
            if (selectedFrame == null || !(selectedFrame instanceof FileWindow)) {
                fileWindow = this.currentWindow;
            } else {
                fileWindow = (FileWindow) selectedFrame;
            }
        }
        if (fileWindow == null && str != null) {
            createFileWindow(this.dim.sourceInfo(str), -1);
            fileWindow = getFileWindow(str);
        }
        if (fileWindow != null) {
            if (i > -1) {
                int position = fileWindow.getPosition(i - 1);
                int position2 = fileWindow.getPosition(i) - 1;
                if (position > 0) {
                    fileWindow.textArea.select(position);
                    fileWindow.textArea.setCaretPosition(position);
                    fileWindow.textArea.moveCaretPosition(position2);
                } else {
                    return;
                }
            }
            try {
                if (fileWindow.isIcon()) {
                    fileWindow.setIcon(false);
                }
                fileWindow.setVisible(true);
                fileWindow.moveToFront();
                fileWindow.setSelected(true);
                requestFocus();
                fileWindow.requestFocus();
                fileWindow.textArea.requestFocus();
            } catch (Exception unused) {
            }
        }
    }

    public void showStopLine(Dim.StackFrame stackFrame) {
        String url = stackFrame.getUrl();
        if (url != null && !url.equals("<stdin>")) {
            showFileWindow(url, -1);
            int lineNumber = stackFrame.getLineNumber();
            FileWindow fileWindow = getFileWindow(url);
            if (fileWindow != null) {
                setFilePosition(fileWindow, lineNumber);
            }
        } else if (this.console.isVisible()) {
            this.console.show();
        }
    }

    public boolean updateFileWindow(Dim.SourceInfo sourceInfo) {
        FileWindow fileWindow = getFileWindow(sourceInfo.url());
        if (fileWindow == null) {
            return false;
        }
        fileWindow.updateText(sourceInfo);
        fileWindow.show();
        return true;
    }

    public void updateSourceText(Dim.SourceInfo sourceInfo) {
        RunProxy runProxy = new RunProxy(this, 3);
        runProxy.sourceInfo = sourceInfo;
        SwingUtilities.invokeLater(runProxy);
    }
}
