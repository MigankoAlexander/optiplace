/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optiplace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.Caret;

/**
 *
 * @author Miganko
 */
public class WorkFrame extends JFrame {

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("Файл");
    private JMenuItem randomInc = new JMenuItem("Случайный набор");
    private JMenuItem clean = new JMenuItem("Очистить окно");
    private JMenuItem exit = new JMenuItem("Выход");
    private JMenu DopMenu = new JMenu("Дополнительно");
    private JMenuItem params = new JMenuItem("Параметры");
    private WorkPanel mapPanel = new WorkPanel();
    private JPanel conrolPanel = new JPanel(new BorderLayout());
    GridLayout gridInputLayout = new GridLayout(8, 2, 1, 1);
    private JPanel inputPanel = new JPanel(gridInputLayout);
    private TextField pField = new TextField("5");
    private TextField sField = new TextField("100");
    private TextField tField = new TextField("2");
    private List<Point> emergences = new ArrayList<>();
    private JButton button = new JButton("Размещение");
    Label descr = new Label(" Укажите места ЧС и запустите алгоритм");
    double minFitnes = Double.POSITIVE_INFINITY;
    Long maximTime = (long)3;
    Integer xxStep = 5;
    Integer yyStep = 5;

    public WorkFrame(int width, int height, String title) {
        JFrame frame = new JFrame(title);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        mapPanel.setBackground(Color.WHITE);
        frame.add(mapPanel, BorderLayout.CENTER);
        frame.add(conrolPanel, BorderLayout.EAST);

        pField.setMaximumSize(new Dimension(100, 30));
        sField.setMaximumSize(new Dimension(100, 30));
        tField.setMaximumSize(new Dimension(100, 30));


        inputPanel.add(new Label("  Число машин в плане размещения, шт"));
        inputPanel.add(pField);
        inputPanel.add(new Label("  Расстояние реагирования одной"));
        //inputPanel.add(new Label("реагирования"));
        inputPanel.add(new Label(" машины, pix"));
        inputPanel.add(sField);
//        inputPanel.add(new Label(" Максимальное"));
//        inputPanel.add(new Label("время"));
//        inputPanel.add(new Label(" расчета, с"));
//        inputPanel.add(tField);
//        inputPanel.add(new Label(""));
//        inputPanel.add(new Label(""));

        conrolPanel.add(inputPanel, BorderLayout.NORTH);




        conrolPanel.add(button, BorderLayout.SOUTH);
        conrolPanel.add(descr, BorderLayout.CENTER);

        menuBar.add(fileMenu);
        fileMenu.add(randomInc);


        randomInc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                button.setText("Размещение");
                emergences = new ArrayList<>();
                mapPanel.cleanFigures();
                Random random = new Random();

                int numOfEnerg = random.nextInt(13);

                for (int i = 0; i < numOfEnerg; i++) {
                    int x = random.nextInt(mapPanel.getWidth() - inputPanel.getWidth());
                    int y = random.nextInt(mapPanel.getHeight());

                    emergences.add(new Point(x, y));
                    mapPanel.addFigure(new Dot(x, y, Color.RED));
                }
            }
        });

        fileMenu.add(clean);
        clean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                button.setText("Размещение");
                emergences = new ArrayList<>();
                mapPanel.cleanFigures();

            }
        });
        fileMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        menuBar.add(DopMenu);

        DopMenu.add(params);
        params.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                
                
                //LogIn.runLogIn();
                final JFrame frame = new JFrame("Авторизация");

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        JLabel login = new JLabel("Password:");
        JLabel password = new JLabel("Login:");
        
        final JTextField loginTextField = new JFormattedTextField();
        JPasswordField passwordTextField = new JPasswordField(10);

        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Отмена");
        JPanel panellogin = new JPanel();
        JPanel panelpassword = new JPanel();

        final String strAdmin = "Admin";
        String strPass = "Admin";
        // String Pass = e.getActionCommand(); 

        frame.add(login, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        frame.add(password, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        frame.add(loginTextField, new GridBagConstraints(1, 0, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        frame.add(passwordTextField, new GridBagConstraints(1, 1, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        frame.add(ok, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        frame.add(cancel, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        frame.add(password, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                
            }
        });

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String str1 = loginTextField.getText();
                if (str1.equals(strAdmin)) {
                    
                    final JFrame dopFrame = new JFrame("Дополнительно");
                dopFrame.setBounds(100, 100, 400, 250);
                dopFrame.setVisible(true);

                GridBagLayout gbl = new GridBagLayout();
                dopFrame.setLayout(gbl);
                GridBagConstraints c = new GridBagConstraints();
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridheight = 1;
                c.gridwidth = GridBagConstraints.REMAINDER;
                c.gridx = 0;
                c.gridy = GridBagConstraints.RELATIVE;
                c.insets = new Insets(5, 0, 0, 0);
                c.ipadx = 0;
                c.ipady = 0;
                c.weightx = 0.0;
                c.weighty = 0.0;

                JLabel maxTime = new JLabel("Максимальное время работы алгоритма, c");
                maxTime.setLocation(new Point(110, 110));
                JLabel xStep = new JLabel("Шаг по х в сетке размещения машин, pix");
                xStep.setLocation(new Point(120, 120));
                JLabel yStep = new JLabel("Шаг по y в сетке размещения машин, pix");
                yStep.setLocation(new Point(130, 130));

                final JTextField timeField = new JTextField(maximTime.toString());
                final JTextField xField = new JTextField(xxStep.toString());
                final JTextField yField = new JTextField(yyStep.toString());
                
                JButton ok = new JButton("Ok");
                JButton cancel = new JButton("Отмена");

                
                gbl.setConstraints(maxTime, c);
                dopFrame.add(maxTime);
                
                
                gbl.setConstraints(timeField, c);
                dopFrame.add(timeField);
                
                gbl.setConstraints(xStep, c);
                dopFrame.add(xStep);
                
                gbl.setConstraints(xField, c);
                dopFrame.add(xField);
                
                gbl.setConstraints(yStep, c);
                dopFrame.add(yStep);
                
                
                gbl.setConstraints(yField, c);
                dopFrame.add(yField);
                               
                gbl.setConstraints(ok, c);
                dopFrame.add(ok);
                
                gbl.setConstraints(cancel, c);
                dopFrame.add(cancel);
                
                ok.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        maximTime = Long.parseLong(timeField.getText());
                        
                        xxStep = Integer.parseInt(xField.getText());
                        
                        yyStep = Integer.parseInt(yField.getText());
                        
                        dopFrame.dispose();
                    }
                });
                cancel.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        dopFrame.dispose();
                    }
                });
                frame.dispose();
                }
            }
        });

        //char[] input = passwordTextField.get();
        //   if (str1.equals(strAdmin)){if (input.equals(Pass)){System.out.println("Р’С‹ 651!");}
        //                               else {System.out.println("Р’С‹ 2!");
        //                                           System.out.println("4" + str1);
        //                                           System.out.println("5" + input);};}
        //   else {System.out.println("Р’С‹ 1!"); System.out.println("1" + str1);
        //   System.out.println("2" + Pass);};
        // });
        frame.setVisible(true);
        frame.pack();

    }

            
        });
        frame.setJMenuBar(menuBar);



        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (!emergences.isEmpty()) {


                    long startTime = System.currentTimeMillis();
                    button.setText("Другой вариант");

                    mapPanel.cleanFigures();

                    for (int i = 0; i < emergences.size(); i++) {
                        mapPanel.addFigure(new Dot(emergences.get(i).x, emergences.get(i).y, Color.RED));
                    }

                    PlacementAlgorithm algorithm = new PlacementAlgorithm(
                            mapPanel.getWidth(),
                            mapPanel.getHeight(),
                            xxStep,
                            yyStep,
                            Integer.parseInt(pField.getText()),
                            25, //optimal
                            Integer.parseInt(sField.getText()),
                            emergences);

                    List<List<Point>> sets = algorithm.getInitSets();
                    List<Integer> uncoveredList = algorithm.getUncoveredEmerg(sets);
                    List<Double> chances = algorithm.getChances(sets);
                    long currentTime = System.currentTimeMillis();
                    while (algorithm.getMin(uncoveredList) > 0 && (currentTime - startTime) / 1000 < maximTime) {
                        List<Point> pairs = algorithm.getPairs(chances);
                        List<List<Point>> nextGen = algorithm.getNextGen(sets, pairs);
                        double nextGenFit = algorithm.getFitness(nextGen);
                        currentTime = System.currentTimeMillis();
                        while (nextGenFit > minFitnes && (currentTime - startTime) / 1000 < maximTime) {
                            nextGen = algorithm.mutation(nextGen);
                            for (int i = 0; i < nextGen.size(); i++) {
                                algorithm.optimaze(nextGen.get(i));
                            }
                            nextGenFit = algorithm.getFitness(nextGen);
                            currentTime = System.currentTimeMillis();
                        }
                        minFitnes = nextGenFit;
                        List<Double> newChances = algorithm.getChances(nextGen);
                        chances.addAll(newChances);
                        sets.addAll(nextGen);
                        uncoveredList = algorithm.getUncoveredEmerg(sets);
                        currentTime = System.currentTimeMillis();
                    }
                    if ((currentTime - startTime) / 1000 > maximTime) {
                        descr.setText("Оптимальное решение не найдено");
                    }
                    int numOfMax = algorithm.getNumOfMax(chances);
                    algorithm.optimaze(sets.get(numOfMax));
                    List<List<Boolean>> ins1 = new ArrayList<>();

                    for (int i = 0; i < sets.get(numOfMax).size(); i++) {
                        mapPanel.addFigure(new Circle(sets.get(numOfMax).get(i).x,
                                sets.get(numOfMax).get(i).y,
                                Integer.parseInt(sField.getText()),
                                Color.BLACK));

                        mapPanel.addFigure(new Dot(sets.get(numOfMax).get(i).x,
                                sets.get(numOfMax).get(i).y, Color.GREEN));

                        ins1.add(new ArrayList<Boolean>());
                        for (int j = 0; j < emergences.size(); j++) {
                            ins1.get(i).add(algorithm.inCircle(
                                    emergences.get(j).x,
                                    emergences.get(j).y,
                                    sets.get(numOfMax).get(i).x,
                                    sets.get(numOfMax).get(i).y,
                                    Integer.parseInt(sField.getText())));
                        }
                    }
                    minFitnes = Double.POSITIVE_INFINITY;
                }
            }
        });
        frame.setVisible(true);
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mapPanel.addFigure(new Dot(e.getX(), e.getY(), Color.RED));
                emergences.add(new Point(e.getX(), e.getY()));
            }
        });
    }
}
