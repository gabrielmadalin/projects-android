/*
 * BackgammonPCView.java
 */

package com.josuvladimir.backgammonpc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskMonitor;

import com.josuvladimir.backgammonpc.Game.currentPlay;

/**
 * The application's main frame.
 */

public class BackgammonPCView extends FrameView {
    public static JPanel tablePanel;
    static Game game;
    Icon   turnON,turnOFF;
    public void updateView()
    {
        if(game.turn == currentPlay.playerW)
        {
            WhiteLabel.setIcon(turnON);
            BlackLabel.setIcon(turnOFF);
        }
        else
        {
            WhiteLabel.setIcon(turnOFF);
            BlackLabel.setIcon(turnON);
        }
        PipsWhiteVal.setText(String.valueOf(Game.playerW.pips));
        PipsBlackVal.setText(String.valueOf(Game.playerB.pips));
        PipsWhiteVal.updateUI();
        PipsBlackVal.updateUI();
        PipsWhileLabel.updateUI();
        PipsBlackLabel.updateUI();
        //OkButton.updateUI();
        MesageLabel.updateUI();
        RollButton.updateUI();
        InfoPanel.updateUI();
        tablePanel.updateUI();
        mainPanel.updateUI();
    }
    public void initView(Game.gameType gameType)
    {
        game = new Game(gameType);
        turnON = new ImageIcon(Game.class.getResource("resources/turnON.png"));
        turnOFF = new ImageIcon(Game.class.getResource("resources/turnOFF.png"));
        game.setView(this);
        getFrame().setResizable(false);
        tablePanel = new JPanel(null);
        tablePanel.setBounds(0, 0, 700, 700);
        tablePanel.setLocation(0, 0);
        tablePanel.setVisible(true);
        tablePanel.setOpaque(false);
        mainPanel.add(tablePanel);
        mainPanel.setComponentZOrder(Board, 3);
        mainPanel.setComponentZOrder(tablePanel, 2);
        mainPanel.setComponentZOrder(InfoPanel, 2);
        mainPanel.setComponentZOrder(MesageLabel, 0);
        //mainPanel.setComponentZOrder(OkButton, 1);
        tablePanel.add(Game.playerW.diceA);
        tablePanel.add(Game.playerW.diceB);
        tablePanel.add(Game.playerB.diceA);
        tablePanel.add(Game.playerB.diceB);
        tablePanel.setComponentZOrder(Game.playerW.diceA, 0);
        tablePanel.setComponentZOrder(Game.playerW.diceB, 0);
        tablePanel.setComponentZOrder(Game.playerB.diceA, 0);
        tablePanel.setComponentZOrder(Game.playerB.diceB, 0);
        InfoPanel.setComponentZOrder(PointFinalW, 0);
        InfoPanel.setComponentZOrder(PointFinalB, 0);

        paint(game.table);
        OkButton.setVisible(false);
        MesageLabel.setVisible(false);
        RollButton.setVisible(true);
        RollButton.addMouseListener(new MouseListener() {

            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseReleased(MouseEvent e) {
                if(RollButton.isEnabled())
                {
                    game.setCurrentState(Game.gameState.wasRoll);
                    //game.run();
                    //game.RollDice();
                    //RollButton.setEnabled(false);
                    //game.run();
                    //game.diceA.setVisible(true);
                    //game.diceB.setVisible(true);
                }
                updateView();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        updateView();
        game.start();
    }

    //public void showMesage(String mesage,Game.gameState state)
    public void showMesage(String mesage)
    {
        game.setCurrentState(Game.gameState.showMesage);
        //game.nextState(state);

        MesageLabel.setText(mesage);
        //MesageLabel.setVisible(true);
        //OkButton.setEnabled(true);
        //OkButton.setVisible(true);
        //RollButton.setEnabled(false);
        //RollButton.updateUI();
    }
    public void outToInfoPanel(Piece p)
    {
        InfoPanel.add(p);
        InfoPanel.setComponentZOrder(p, 1);
    }
    public void paint(Table table)
    {
        for (int i = 1; i < table.size(); i++)
        {
            TPoint point = table.elementAt(i);
            if(point != null && point.isEmpty() == false)
            {
                for (int j = 0; j < point.size(); j++) {
                    Piece piece = point.elementAt(j);
                    piece.setVisible(true);
                    tablePanel.add(piece);
                }
            }
        }
        tablePanel.updateUI();
    }

    public BackgammonPCView(SingleFrameApplication app) {
        super(app);
        initComponents();
        //my code
        //*
         //initView(Game.gameType.vsHuman);
         initView(Game.gameType.vsComp);
        /*/
        initView(Game.gameType.comPvsComp);
        //*/
        

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = BackgammonPCApp.getApplication().getMainFrame();
            aboutBox = new BackgammonPCAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        BackgammonPCApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("deprecation")
	private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        OkButton = new javax.swing.JButton();
        MesageLabel = new javax.swing.JLabel();
        Board = new javax.swing.JLabel();
        InfoPanel = new javax.swing.JPanel();
        PointFinalW = new javax.swing.JPanel();
        PointFinalB = new javax.swing.JPanel();
        BlackLabel = new javax.swing.JLabel();
        WhiteLabel = new javax.swing.JLabel();
        PipsBlackLabel = new javax.swing.JLabel();
        PipsWhileLabel = new javax.swing.JLabel();
        RollButton = new javax.swing.JButton();
        PipsBlackVal = new javax.swing.JLabel();
        PipsWhiteVal = new javax.swing.JLabel();
        Point1 = new javax.swing.JPanel();
        Point2 = new javax.swing.JPanel();
        Point3 = new javax.swing.JPanel();
        Point4 = new javax.swing.JPanel();
        Point5 = new javax.swing.JPanel();
        Point6 = new javax.swing.JPanel();
        Point7 = new javax.swing.JPanel();
        Point8 = new javax.swing.JPanel();
        Point9 = new javax.swing.JPanel();
        Point10 = new javax.swing.JPanel();
        Point11 = new javax.swing.JPanel();
        Point12 = new javax.swing.JPanel();
        Point13 = new javax.swing.JPanel();
        Point14 = new javax.swing.JPanel();
        Point15 = new javax.swing.JPanel();
        Point16 = new javax.swing.JPanel();
        Point17 = new javax.swing.JPanel();
        Point18 = new javax.swing.JPanel();
        Point19 = new javax.swing.JPanel();
        Point20 = new javax.swing.JPanel();
        Point21 = new javax.swing.JPanel();
        Point22 = new javax.swing.JPanel();
        Point23 = new javax.swing.JPanel();
        Point24 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        MenuNewGameC = new javax.swing.JMenuItem();
        MenuNewGameH = new javax.swing.JMenuItem();
        MenuUndo = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 700));
        mainPanel.setLayout(null);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(BackgammonPCApp.class).getContext().getResourceMap(BackgammonPCView.class);
        OkButton.setBackground(resourceMap.getColor("OkButton.background")); // NOI18N
        OkButton.setFont(resourceMap.getFont("OkButton.font")); // NOI18N
        OkButton.setForeground(resourceMap.getColor("OkButton.foreground")); // NOI18N
        OkButton.setBorder(new javax.swing.border.MatteBorder(null));
        OkButton.setBorderPainted(false);
        OkButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        OkButton.setLabel("OK"); // NOI18N
        OkButton.setName("OkButton"); // NOI18N
        OkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                OkButtonMouseReleased(evt);
            }
        });
        mainPanel.add(OkButton);
        OkButton.setBounds(320, 370, 60, 30);

        MesageLabel.setBackground(resourceMap.getColor("MesageLabel.background")); // NOI18N
        MesageLabel.setFont(resourceMap.getFont("MesageLabel.font")); // NOI18N
        MesageLabel.setForeground(resourceMap.getColor("MesageLabel.foreground")); // NOI18N
        MesageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MesageLabel.setText(resourceMap.getString("MesageLabel.text")); // NOI18N
        MesageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        MesageLabel.setName("MesageLabel"); // NOI18N
        MesageLabel.setOpaque(true);
        mainPanel.add(MesageLabel);
        MesageLabel.setBounds(230, 330, 240, 30);
        MesageLabel.getAccessibleContext().setAccessibleName(resourceMap.getString("MesageLabel.AccessibleContext.accessibleName")); // NOI18N

        Board.setIcon(resourceMap.getIcon("Table.icon")); // NOI18N
        Board.setText(resourceMap.getString("Table.text")); // NOI18N
        Board.setMaximumSize(new java.awt.Dimension(700, 700));
        Board.setMinimumSize(new java.awt.Dimension(700, 700));
        Board.setName("Table"); // NOI18N
        Board.setPreferredSize(new java.awt.Dimension(700, 700));
        mainPanel.add(Board);
        Board.setBounds(0, 0, 700, 700);

        InfoPanel.setBackground(resourceMap.getColor("InfoPanel.background")); // NOI18N
        InfoPanel.setMinimumSize(new java.awt.Dimension(100, 700));
        InfoPanel.setName("InfoPanel"); // NOI18N
        InfoPanel.setPreferredSize(new java.awt.Dimension(100, 700));

        PointFinalW.setName("PointFinalW"); // NOI18N
        PointFinalW.setOpaque(false);
        PointFinalW.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PointFinalWMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout PointFinalWLayout = new javax.swing.GroupLayout(PointFinalW);
        PointFinalW.setLayout(PointFinalWLayout);
        PointFinalWLayout.setHorizontalGroup(
            PointFinalWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        PointFinalWLayout.setVerticalGroup(
            PointFinalWLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        PointFinalB.setName("PointFinalB"); // NOI18N
        PointFinalB.setOpaque(false);
        PointFinalB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PointFinalBMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout PointFinalBLayout = new javax.swing.GroupLayout(PointFinalB);
        PointFinalB.setLayout(PointFinalBLayout);
        PointFinalBLayout.setHorizontalGroup(
            PointFinalBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        PointFinalBLayout.setVerticalGroup(
            PointFinalBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 303, Short.MAX_VALUE)
        );

        BlackLabel.setFont(resourceMap.getFont("BlackLabel.font")); // NOI18N
        BlackLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BlackLabel.setIcon(resourceMap.getIcon("BlackLabel.icon")); // NOI18N
        BlackLabel.setText(resourceMap.getString("BlackLabel.text")); // NOI18N
        BlackLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BlackLabel.setName("BlackLabel"); // NOI18N

        WhiteLabel.setFont(resourceMap.getFont("WhiteLabel.font")); // NOI18N
        WhiteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WhiteLabel.setIcon(resourceMap.getIcon("WhiteLabel.icon")); // NOI18N
        WhiteLabel.setText(resourceMap.getString("WhiteLabel.text")); // NOI18N
        WhiteLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WhiteLabel.setName("WhiteLabel"); // NOI18N

        PipsBlackLabel.setFont(resourceMap.getFont("PipsLabel.font")); // NOI18N
        PipsBlackLabel.setText(resourceMap.getString("PipsLabel.text")); // NOI18N
        PipsBlackLabel.setName("PipsLabel"); // NOI18N

        PipsWhileLabel.setFont(resourceMap.getFont("PipsWhileLabel.font")); // NOI18N
        PipsWhileLabel.setText(resourceMap.getString("PipsWhileLabel.text")); // NOI18N
        PipsWhileLabel.setName("PipsWhileLabel"); // NOI18N

        RollButton.setFont(resourceMap.getFont("RollButton.font")); // NOI18N
        RollButton.setText(resourceMap.getString("RollButton.text")); // NOI18N
        RollButton.setName("RollButton"); // NOI18N
        RollButton.setPreferredSize(new java.awt.Dimension(50, 30));

        PipsBlackVal.setFont(resourceMap.getFont("PipsBlackVal.font")); // NOI18N
        PipsBlackVal.setText(resourceMap.getString("PipsBlackVal.text")); // NOI18N
        PipsBlackVal.setName("PipsBlackVal"); // NOI18N

        PipsWhiteVal.setFont(resourceMap.getFont("PipsWhiteVal.font")); // NOI18N
        PipsWhiteVal.setText(resourceMap.getString("PipsWhiteVal.text")); // NOI18N
        PipsWhiteVal.setName("PipsWhiteVal"); // NOI18N

        javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
        InfoPanel.setLayout(InfoPanelLayout);
        InfoPanelLayout.setHorizontalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addComponent(PipsWhileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PipsWhiteVal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addComponent(PipsBlackLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PipsBlackVal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(WhiteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BlackLabel)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RollButton, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(PointFinalB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PointFinalW, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        InfoPanelLayout.setVerticalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BlackLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addComponent(PipsBlackVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addComponent(PipsBlackLabel))
                .addGap(239, 239, 239)
                .addComponent(RollButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(261, 261, 261)
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PipsWhileLabel)
                    .addComponent(PipsWhiteVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(WhiteLabel)
                .addContainerGap())
            .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PointFinalB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                    .addComponent(PointFinalW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        mainPanel.add(InfoPanel);
        InfoPanel.setBounds(700, 0, 100, 700);

        Point1.setName("Point1"); // NOI18N
        Point1.setOpaque(false);
        Point1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point1Layout = new javax.swing.GroupLayout(Point1);
        Point1.setLayout(Point1Layout);
        Point1Layout.setHorizontalGroup(
            Point1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point1Layout.setVerticalGroup(
            Point1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point1);
        Point1.setBounds(621, 417, 40, 250);

        Point2.setName("Point2"); // NOI18N
        Point2.setOpaque(false);
        Point2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point2MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point2Layout = new javax.swing.GroupLayout(Point2);
        Point2.setLayout(Point2Layout);
        Point2Layout.setHorizontalGroup(
            Point2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point2Layout.setVerticalGroup(
            Point2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point2);
        Point2.setBounds(573, 417, 40, 250);

        Point3.setName("Point3"); // NOI18N
        Point3.setOpaque(false);
        Point3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point3MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point3Layout = new javax.swing.GroupLayout(Point3);
        Point3.setLayout(Point3Layout);
        Point3Layout.setHorizontalGroup(
            Point3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point3Layout.setVerticalGroup(
            Point3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point3);
        Point3.setBounds(527, 417, 40, 250);

        Point4.setName("Point4"); // NOI18N
        Point4.setOpaque(false);
        Point4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point4MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point4Layout = new javax.swing.GroupLayout(Point4);
        Point4.setLayout(Point4Layout);
        Point4Layout.setHorizontalGroup(
            Point4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point4Layout.setVerticalGroup(
            Point4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point4);
        Point4.setBounds(480, 417, 40, 250);

        Point5.setName("Point5"); // NOI18N
        Point5.setOpaque(false);
        Point5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point5MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point5Layout = new javax.swing.GroupLayout(Point5);
        Point5.setLayout(Point5Layout);
        Point5Layout.setHorizontalGroup(
            Point5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point5Layout.setVerticalGroup(
            Point5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point5);
        Point5.setBounds(433, 417, 40, 250);

        Point6.setName("Point6"); // NOI18N
        Point6.setOpaque(false);
        Point6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point6MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point6Layout = new javax.swing.GroupLayout(Point6);
        Point6.setLayout(Point6Layout);
        Point6Layout.setHorizontalGroup(
            Point6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point6Layout.setVerticalGroup(
            Point6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point6);
        Point6.setBounds(387, 417, 40, 250);

        Point7.setName("Point7"); // NOI18N
        Point7.setOpaque(false);
        Point7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point7MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point7Layout = new javax.swing.GroupLayout(Point7);
        Point7.setLayout(Point7Layout);
        Point7Layout.setHorizontalGroup(
            Point7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point7Layout.setVerticalGroup(
            Point7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point7);
        Point7.setBounds(267, 417, 40, 250);

        Point8.setName("Point8"); // NOI18N
        Point8.setOpaque(false);
        Point8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point8MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point8Layout = new javax.swing.GroupLayout(Point8);
        Point8.setLayout(Point8Layout);
        Point8Layout.setHorizontalGroup(
            Point8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point8Layout.setVerticalGroup(
            Point8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point8);
        Point8.setBounds(219, 417, 40, 250);

        Point9.setName("Point9"); // NOI18N
        Point9.setOpaque(false);
        Point9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point9MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point9Layout = new javax.swing.GroupLayout(Point9);
        Point9.setLayout(Point9Layout);
        Point9Layout.setHorizontalGroup(
            Point9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point9Layout.setVerticalGroup(
            Point9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point9);
        Point9.setBounds(173, 417, 40, 250);

        Point10.setName("Point10"); // NOI18N
        Point10.setOpaque(false);
        Point10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point10MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point10Layout = new javax.swing.GroupLayout(Point10);
        Point10.setLayout(Point10Layout);
        Point10Layout.setHorizontalGroup(
            Point10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point10Layout.setVerticalGroup(
            Point10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point10);
        Point10.setBounds(125, 417, 40, 250);

        Point11.setName("Point11"); // NOI18N
        Point11.setOpaque(false);
        Point11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point11MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point11Layout = new javax.swing.GroupLayout(Point11);
        Point11.setLayout(Point11Layout);
        Point11Layout.setHorizontalGroup(
            Point11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point11Layout.setVerticalGroup(
            Point11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point11);
        Point11.setBounds(77, 417, 40, 250);

        Point12.setName("Point12"); // NOI18N
        Point12.setOpaque(false);
        Point12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point12MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point12Layout = new javax.swing.GroupLayout(Point12);
        Point12.setLayout(Point12Layout);
        Point12Layout.setHorizontalGroup(
            Point12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point12Layout.setVerticalGroup(
            Point12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point12);
        Point12.setBounds(30, 417, 40, 250);

        Point13.setName("Point13"); // NOI18N
        Point13.setOpaque(false);
        Point13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point13MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point13Layout = new javax.swing.GroupLayout(Point13);
        Point13.setLayout(Point13Layout);
        Point13Layout.setHorizontalGroup(
            Point13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point13Layout.setVerticalGroup(
            Point13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point13);
        Point13.setBounds(30, 30, 40, 250);

        Point14.setName("Point14"); // NOI18N
        Point14.setOpaque(false);
        Point14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point14MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point14Layout = new javax.swing.GroupLayout(Point14);
        Point14.setLayout(Point14Layout);
        Point14Layout.setHorizontalGroup(
            Point14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point14Layout.setVerticalGroup(
            Point14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point14);
        Point14.setBounds(77, 30, 40, 250);

        Point15.setName("Point15"); // NOI18N
        Point15.setOpaque(false);
        Point15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point15MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point15Layout = new javax.swing.GroupLayout(Point15);
        Point15.setLayout(Point15Layout);
        Point15Layout.setHorizontalGroup(
            Point15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point15Layout.setVerticalGroup(
            Point15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point15);
        Point15.setBounds(125, 30, 40, 250);

        Point16.setName("Point16"); // NOI18N
        Point16.setOpaque(false);
        Point16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point16MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point16Layout = new javax.swing.GroupLayout(Point16);
        Point16.setLayout(Point16Layout);
        Point16Layout.setHorizontalGroup(
            Point16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point16Layout.setVerticalGroup(
            Point16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point16);
        Point16.setBounds(173, 30, 40, 250);

        Point17.setName("Point17"); // NOI18N
        Point17.setOpaque(false);
        Point17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point17MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point17Layout = new javax.swing.GroupLayout(Point17);
        Point17.setLayout(Point17Layout);
        Point17Layout.setHorizontalGroup(
            Point17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point17Layout.setVerticalGroup(
            Point17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point17);
        Point17.setBounds(219, 30, 40, 250);

        Point18.setName("Point18"); // NOI18N
        Point18.setOpaque(false);
        Point18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point18MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point18Layout = new javax.swing.GroupLayout(Point18);
        Point18.setLayout(Point18Layout);
        Point18Layout.setHorizontalGroup(
            Point18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point18Layout.setVerticalGroup(
            Point18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point18);
        Point18.setBounds(267, 30, 40, 250);

        Point19.setName("Point19"); // NOI18N
        Point19.setOpaque(false);
        Point19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point19MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point19Layout = new javax.swing.GroupLayout(Point19);
        Point19.setLayout(Point19Layout);
        Point19Layout.setHorizontalGroup(
            Point19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point19Layout.setVerticalGroup(
            Point19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point19);
        Point19.setBounds(387, 30, 40, 250);

        Point20.setName("Point20"); // NOI18N
        Point20.setOpaque(false);
        Point20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point20MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point20Layout = new javax.swing.GroupLayout(Point20);
        Point20.setLayout(Point20Layout);
        Point20Layout.setHorizontalGroup(
            Point20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point20Layout.setVerticalGroup(
            Point20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point20);
        Point20.setBounds(433, 30, 40, 250);

        Point21.setName("Point21"); // NOI18N
        Point21.setOpaque(false);
        Point21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point21MouseReleased(evt);
            }
        });
        Point21.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Point21KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout Point21Layout = new javax.swing.GroupLayout(Point21);
        Point21.setLayout(Point21Layout);
        Point21Layout.setHorizontalGroup(
            Point21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point21Layout.setVerticalGroup(
            Point21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point21);
        Point21.setBounds(480, 30, 40, 250);

        Point22.setName("Point22"); // NOI18N
        Point22.setOpaque(false);
        Point22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point22MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point22Layout = new javax.swing.GroupLayout(Point22);
        Point22.setLayout(Point22Layout);
        Point22Layout.setHorizontalGroup(
            Point22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point22Layout.setVerticalGroup(
            Point22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point22);
        Point22.setBounds(527, 30, 40, 250);

        Point23.setName("Point23"); // NOI18N
        Point23.setOpaque(false);
        Point23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point23MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point23Layout = new javax.swing.GroupLayout(Point23);
        Point23.setLayout(Point23Layout);
        Point23Layout.setHorizontalGroup(
            Point23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point23Layout.setVerticalGroup(
            Point23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point23);
        Point23.setBounds(573, 30, 40, 250);

        Point24.setName("Point24"); // NOI18N
        Point24.setOpaque(false);
        Point24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Point24MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout Point24Layout = new javax.swing.GroupLayout(Point24);
        Point24.setLayout(Point24Layout);
        Point24Layout.setHorizontalGroup(
            Point24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        Point24Layout.setVerticalGroup(
            Point24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        mainPanel.add(Point24);
        Point24.setBounds(621, 30, 40, 250);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        MenuNewGameC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        MenuNewGameC.setText(resourceMap.getString("MenuNewGameC.text")); // NOI18N
        MenuNewGameC.setName("MenuNewGameC"); // NOI18N
        MenuNewGameC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MenuNewGameCMouseReleased(evt);
            }
        });
        fileMenu.add(MenuNewGameC);

        MenuNewGameH.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        MenuNewGameH.setText(resourceMap.getString("MenuNewGameH.text")); // NOI18N
        MenuNewGameH.setName("MenuNewGameH"); // NOI18N
        MenuNewGameH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MenuNewGameHMouseReleased(evt);
            }
        });
        fileMenu.add(MenuNewGameH);

        MenuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        MenuUndo.setText(resourceMap.getString("MenuUndo.text")); // NOI18N
        MenuUndo.setName("MenuUndo"); // NOI18N
        MenuUndo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                MenuUndoMouseReleased(evt);
            }
        });
        fileMenu.add(MenuUndo);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(BackgammonPCApp.class).getContext().getActionMap(BackgammonPCView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 730, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void Point15MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point15MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(15);
    }//GEN-LAST:event_Point15MouseReleased

    private void Point10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point10MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(10);
    }//GEN-LAST:event_Point10MouseReleased

    private void Point2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point2MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(2);
    }//GEN-LAST:event_Point2MouseReleased

    private void Point1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point1MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(1);
    }//GEN-LAST:event_Point1MouseReleased

    private void Point3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point3MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(3);
    }//GEN-LAST:event_Point3MouseReleased

    private void Point4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point4MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(4);
    }//GEN-LAST:event_Point4MouseReleased

    private void Point5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point5MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(5);
    }//GEN-LAST:event_Point5MouseReleased

    private void Point6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point6MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(6);
    }//GEN-LAST:event_Point6MouseReleased

    private void Point7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point7MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(7);
    }//GEN-LAST:event_Point7MouseReleased

    private void Point8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point8MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(8);
    }//GEN-LAST:event_Point8MouseReleased

    private void Point9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point9MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(9);
    }//GEN-LAST:event_Point9MouseReleased

    private void Point11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point11MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(11);
    }//GEN-LAST:event_Point11MouseReleased

    private void Point12MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point12MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(12);
    }//GEN-LAST:event_Point12MouseReleased

    private void Point13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point13MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(13);
    }//GEN-LAST:event_Point13MouseReleased

    private void Point14MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point14MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(14);
    }//GEN-LAST:event_Point14MouseReleased

    private void Point16MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point16MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(16);
    }//GEN-LAST:event_Point16MouseReleased

    private void Point17MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point17MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(17);
    }//GEN-LAST:event_Point17MouseReleased

    private void Point18MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point18MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(18);
    }//GEN-LAST:event_Point18MouseReleased

    private void Point19MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point19MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(19);
    }//GEN-LAST:event_Point19MouseReleased

    private void Point21KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Point21KeyReleased
        // TODO add your handling code here:
        //Game.instance.table.setSelectedPoint(21);
    }//GEN-LAST:event_Point21KeyReleased

    private void Point21MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point21MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(21);
    }//GEN-LAST:event_Point21MouseReleased

    private void Point20MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point20MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(20);
    }//GEN-LAST:event_Point20MouseReleased

    private void Point22MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point22MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(22);
    }//GEN-LAST:event_Point22MouseReleased

    private void Point23MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point23MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(23);
    }//GEN-LAST:event_Point23MouseReleased

    private void Point24MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Point24MouseReleased
        // TODO add your handling code here:
        Game.instance.table.setSelectedPoint(24);
    }//GEN-LAST:event_Point24MouseReleased

    private void OkButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OkButtonMouseReleased
        // TODO add your handling code here:
        //game.run();
        game.setCurrentState(Game.gameState.hideMesage);
    }//GEN-LAST:event_OkButtonMouseReleased

    private void PointFinalWMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PointFinalWMouseReleased
        // TODO add your handling code here:
        game.table.selectOutPoint(Piece.colorType.white);
    }//GEN-LAST:event_PointFinalWMouseReleased

    private void PointFinalBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PointFinalBMouseReleased
        // TODO add your handling code here:
        game.table.selectOutPoint(Piece.colorType.black);

    }//GEN-LAST:event_PointFinalBMouseReleased

    private void MenuUndoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuUndoMouseReleased
        // TODO add your handling code here:
        game.table.undoLastMove();

    }//GEN-LAST:event_MenuUndoMouseReleased

    private void MenuNewGameCMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuNewGameCMouseReleased
        // TODO add your handling code here:
        //game = new Game(Game.gameType.vsComp);
        game.gameOver = true;
        initView(Game.gameType.vsComp);

    }//GEN-LAST:event_MenuNewGameCMouseReleased

    private void MenuNewGameHMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuNewGameHMouseReleased
        // TODO add your handling code here:
        //game = new Game(Game.gameType.vsComp);
        game.gameOver = true;
        try {
            wait(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BackgammonPCView.class.getName()).log(Level.SEVERE, null, ex);
        }
        initView(Game.gameType.vsHuman);
    }//GEN-LAST:event_MenuNewGameHMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BlackLabel;
    private javax.swing.JLabel Board;
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JMenuItem MenuNewGameC;
    private javax.swing.JMenuItem MenuNewGameH;
    private javax.swing.JMenuItem MenuUndo;
    public javax.swing.JLabel MesageLabel;
    public javax.swing.JButton OkButton;
    private javax.swing.JLabel PipsBlackLabel;
    private javax.swing.JLabel PipsBlackVal;
    private javax.swing.JLabel PipsWhileLabel;
    private javax.swing.JLabel PipsWhiteVal;
    private javax.swing.JPanel Point1;
    private javax.swing.JPanel Point10;
    private javax.swing.JPanel Point11;
    private javax.swing.JPanel Point12;
    private javax.swing.JPanel Point13;
    private javax.swing.JPanel Point14;
    private javax.swing.JPanel Point15;
    private javax.swing.JPanel Point16;
    private javax.swing.JPanel Point17;
    private javax.swing.JPanel Point18;
    private javax.swing.JPanel Point19;
    private javax.swing.JPanel Point2;
    private javax.swing.JPanel Point20;
    private javax.swing.JPanel Point21;
    private javax.swing.JPanel Point22;
    private javax.swing.JPanel Point23;
    private javax.swing.JPanel Point24;
    private javax.swing.JPanel Point3;
    private javax.swing.JPanel Point4;
    private javax.swing.JPanel Point5;
    private javax.swing.JPanel Point6;
    private javax.swing.JPanel Point7;
    private javax.swing.JPanel Point8;
    private javax.swing.JPanel Point9;
    private javax.swing.JPanel PointFinalB;
    private javax.swing.JPanel PointFinalW;
    public javax.swing.JButton RollButton;
    private javax.swing.JLabel WhiteLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
