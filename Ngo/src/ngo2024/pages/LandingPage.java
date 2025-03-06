package ngo2024.pages;

import ngo2024.pages.SearchPages.SearchEmployees;
import ngo2024.pages.SearchPages.DepartmentSearch;
import ngo2024.pages.SearchPages.ProjectSearch;
import ngo2024.pages.SearchPages.LandSearch;
import ngo2024.pages.SpecificsPage.AddEmployee;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Project;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.CredentialsPrompt;
import ngo2024.pages.MyProfilePanel;
import ngo2024.pages.SearchPages.SearchEnvGoal;
import ngo2024.pages.SearchPages.SearchEnvGoal;
import ngo2024.pages.SpecificsPage.SpecificProject;
import ngo2024.pages.TabPageBase;
import oru.inf.InfException;

/**
 * The main page of the application. Contains all the neseacry means to navigate
 * information from the database.
 *
 * @author leonb
 */
public class LandingPage extends JFrame {

    public static JTabbedPane content;
    private JLabel footerText;
    public static String email;

    /**
     * *
     * Creates Landing page JFrame and initiates all components
     */
    public LandingPage() {
        initializeFrame();

        createTabbedPane();
        createTopBar();
        createFooter();

        setVisible(true);
    }

    /**
     * Opens login dialog
     */
    private void openLogin() {

        // Opens signin dialog
        new CredentialsPrompt(this);
        footerText.setText("Inloggad som: " + (Main.user.getBehorig() == AccessLevel.GUEST ? "gäst" : Main.user.getEpost()));

    }

    /**
     * Makes this window visible and configures size
     */
    private void initializeFrame() {

        // Set window icon
        try {
            
            Image icon  = ImageIO.read(Main.class.getResourceAsStream("../resources/iconApp.png"));
            setIconImage(icon);
        } catch (IOException ex) {
            Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());
        setTitle("Ngo administration application");
    }

    /**
     * Adds a topbar to the NORTH of the page. The topbar contains, Explore
     * menu, login button, Start button and a my-profile button.
     */
    private void createTopBar() {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createWelcomePage();
            }
        });

        //skapar menybar för JMenu att finnas i. 
        JMenuBar con = new JMenuBar();
        JMenu utforska = new JMenu("Utforska");
        con.add(utforska);

        //Skapar alternativ för drop-down meny
        JMenuItem utfrskProj = new JMenuItem("Projekt");
        JMenuItem utfrskAvd = new JMenuItem("Avdelning");
        JMenuItem utfrskLand = new JMenuItem("Land");
        JMenuItem utfrskMal = new JMenuItem("Hållbarhetsmål");
        JMenuItem utfrskAnst = new JMenuItem("Anställda");

        //lägger till alternativen i drop-down menyn
        utforska.add(utfrskProj);
        utforska.add(utfrskAvd);
        utforska.add(utfrskLand);
        utforska.add(utfrskMal);
        utforska.add(utfrskAnst);

        utfrskProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utfrskProjActionPerformed(evt);
            }
        });
        utfrskAvd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utfrskAvdActionPerformed(evt);
            }
        });
        utfrskLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utfrskLandActionPerformed(evt);
            }
        });
        utfrskMal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new SearchEnvGoal(content);
            }
        });
        utfrskAnst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new SearchEmployees(content, null);
            }
        });
        JButton changeDetailsbtn = new JButton("Ändra mina uppgifter");

        changeDetailsbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChangeDetails();
            }
        });

        JButton hanteraAnstBtn = new JButton("Lägg till anställd");
        hanteraAnstBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployee(content);
            }
        });

        JButton statBtn = new JButton("Se din statistik");
        statBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<String> history = new ArrayList<String>();
                try {

                    final int[] totCost = {0};

                    Main.infDB.fetchColumn("SELECT pid FROM projekt WHERE projektchef = " + Main.user.getAid()).forEach(pid -> {
                        Project p = new Project(pid);
                        history.add(p.getProjektnamn() + " kostnad: " + p.getKostnad());
                        totCost[0] += Double.parseDouble(p.getKostnad());

                    });
                    history.add("Totala kostand på projekt: " + totCost[0]);
                    String[] strArr = new String[history.size()];
                    String picked = GuiUtil.getSelectFromStringListPupop(history.toArray(strArr), "Din projekt historik");

                    /*   if (picked != null) {
                        String id = Main.infDB.fetchSingle("SELECT pid FROM projekt WHERE projektnamn = '" + picked + "'");
                        new SpecificProject(picked, new Project(id).getPid(), content);
                    }*/
                } catch (InfException ex) {
                    Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton loginBtn = new JButton(Main.user.getBehorig() == AccessLevel.GUEST ? "Logga in" : "Byt användare");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogin();

                loginBtn.setText(Main.user.getBehorig() == AccessLevel.GUEST ? "Logga in" : "Byt användare");

            }
        });

        topBar.add(start);
        topBar.add(con);
        topBar.add(hanteraAnstBtn);
        topBar.add(changeDetailsbtn);
        topBar.add(statBtn);
        topBar.add(loginBtn);

        add(topBar, BorderLayout.NORTH);
    }

    private void openChangeDetails() {

        if (Main.user.getBehorig() != AccessLevel.GUEST) {
            new MyProfilePanel(content);
        } else {
            openLogin();

        }

    }

    /**
     * Opens a project search page.
     *
     * @param evt, the event passed from the action
     */
    public void utfrskProjActionPerformed(java.awt.event.ActionEvent evt) {

        new ProjectSearch(content);
        repaint();
        revalidate();
    }

    /**
     * Opens a department search page.
     *
     * @param evt, the event passed from the action
     */
    public void utfrskAvdActionPerformed(java.awt.event.ActionEvent evt) {

        new DepartmentSearch(content);
        repaint();
        revalidate();
    }

    /**
     * Opens a country search page.
     *
     * @param evt, the event passed from the action
     */
    public void utfrskLandActionPerformed(java.awt.event.ActionEvent evt) {

        new LandSearch(content);
        repaint();
        revalidate();
    }

    /**
     * Adds a tab container to the Center of the window
     */
    private void createTabbedPane() {
        content = new JTabbedPane();
        createWelcomePage();
        add(content, BorderLayout.CENTER);
    }

    /**
     * Creates a basic tab page with a welcome message.
     *
     */
    private void createWelcomePage() {
        new TabPageBase(content, "Välkommen") {
            @Override
            protected void initiate() {
                add(new JLabel("Hej och välkommen till Ngo2024 administriva applikation för administration och handläggning."));
            }

            @Override
            protected boolean hasThePrivlege() {
                return true;
            }
        }.addTab();

    }

    /**
     * Adds a footer to the window
     */
    private void createFooter() {

        String userEmail = "gäst";
        if (Main.user.getBehorig() != AccessLevel.GUEST) {
            userEmail = Main.user.getEpost();
        }

        JPanel footer = new JPanel();
        footerText = new JLabel("Inloggad som: " + userEmail);
        footer.add(footerText);
        add(footer, BorderLayout.SOUTH);
    }
}
