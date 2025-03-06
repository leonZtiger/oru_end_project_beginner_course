package ngo2024.pages.SpecificsPage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Avdelning;
import ngo2024.DataContainers.DataContainerCommon;
import ngo2024.DataContainers.HallbarhetsMal;
import ngo2024.DataContainers.Handlaggare;
import ngo2024.DataContainers.Land;
import ngo2024.DataContainers.Partner;
import ngo2024.DataContainers.Project;
import ngo2024.DataContainers.User;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.SpecificsPage.SpecificEmployee;
import ngo2024.pages.SpecificsPage.SpecificEnvGoal;
import ngo2024.pages.SpecificsPage.SpecificPageBase;
import ngo2024.pages.SpecificsPage.SpecificPartner;
import ngo2024.pages.TabPageBase;
import oru.inf.InfException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * A tabbed page showing information of the specified project.
 *
 * @author leonb
 */
public final class SpecificProject extends SpecificPageBase {

    private Project project;

    // Individual JTextFields for each attribute
    private JTextField projektnamnField;
    private JTextField beskrivningField;
    private JTextField startdatumField;
    private JTextField slutdatumField;
    private JTextField kostnadField;

    private JScrollPane hallbarhetsList;
    private JScrollPane partnerList;
    private JScrollPane contributerList;

    /**
     * Creates a project page containing all relevent information.
     *
     * @param name, title of the tab.
     * @param projectID, the key of the project row.
     * @param parent, the JTabbedPane this will be added to.
     */
    public SpecificProject(String name, String projectID, JTabbedPane parent) {
        super(parent, name);

        // Initiate the project object
        project = new Project(projectID);

        addTab();
    }

    @Override
    protected void initiate() {
        setLayout(new BorderLayout(20, 20));

        // Create text container with project name and description
        JPanel textContainer = new JPanel(new BorderLayout(10, 10));
        textContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(project.getProjektnamn());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        textContainer.add(nameLabel, BorderLayout.NORTH);

        textContainer.add(createInformationContainer());

        add(createDescriptionPanel(), BorderLayout.CENTER);
        add(textContainer, BorderLayout.WEST);

        JPanel eastCon = new JPanel();
        eastCon.setLayout(new GridLayout(1, 2));

        eastCon.add(createPartnerList());
        eastCon.add(createEnvGoalList());
        eastCon.add(createContributerList());

        add(eastCon, BorderLayout.CENTER);

        // Add edit button if user has the appropriet level of access
        if (Main.user.getBehorig() == AccessLevel.ADMIN1 || Main.user.getBehorig() == AccessLevel.ADMIN2 || Main.user.getBehorig() == AccessLevel.PROJECT_CHEF) {
            add(createAdminOptions(), BorderLayout.SOUTH);
        }
    }

    private JScrollPane createDescriptionPanel() {
        JTextArea descriptionArea = new JTextArea(project.getBeskrivning());
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        return new JScrollPane(descriptionArea);
    }

    @Override
    protected void enableFields(boolean state) {
        projektnamnField.setEnabled(state);
        beskrivningField.setEnabled(state);
        startdatumField.setEnabled(state);
        slutdatumField.setEnabled(state);
        kostnadField.setEnabled(state);
    }

    private JPanel createInformationContainer() {

        // Create  container with general info
        JPanel container = new JPanel(new GridBagLayout());

        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each project attribute
        projektnamnField = addField(container, gbc, "Namn");
        beskrivningField = addField(container, gbc, "Beskrivning");
        startdatumField = addField(container, gbc, "Startdatum");
        slutdatumField = addField(container, gbc, "Slutdatum");
        kostnadField = addField(container, gbc, "Kostnad");

        setAllFieldValues();
        enableFields(false);

        JLabel landLbl = new JLabel(project.getLandObj().getNamn());
        ActionListener onLandEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = GuiUtil.getSelectFromListPupop(Land.getAll(), "Välj nytt land");
                if (id == null) {
                    return;
                }

                project.setLand(id);
                landLbl.setText(project.getLandObj().getNamn());
            }
        };

        // Land edit
        addEditableButton(container, gbc, "Land:", landLbl, onLandEdit);

        JLabel chefLbl = new JLabel(project.getProjektchefObj().getFullname());
        ActionListener onChefEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = GuiUtil.getSelectFromListPupop(Handlaggare.getAll(), "Välj ny projekt chef");
                if (id == null) {
                    return;
                }

                project.setProjektchef(id);
                chefLbl.setText(project.getProjektchefObj().getFullname());
            }
        };

        // Chef edit
        addEditableButton(container, gbc, "Chef:", chefLbl, onChefEdit);

        JLabel statusLbl = new JLabel(project.getStatus());
        ActionListener onStatusEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String status = GuiUtil.getSelectFromStringListPupop(new String[]{"Pågående", "Avslutat", "Planerat"}, "Välj status");
                if (status == null) {
                    return;
                }

                project.setStatus(status);
                statusLbl.setText(project.getStatus());
            }
        };

        // Status edit
        addEditableButton(container, gbc, "Status:", statusLbl, onStatusEdit);

        JLabel prioLbl = new JLabel(project.getPrioritet());
        ActionListener onPrioEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String status = GuiUtil.getSelectFromStringListPupop(new String[]{"Hög", "Medel", "Låg"}, "Välj prioritet");
                if (status == null) {
                    return;
                }

                project.setPrioritet(status);
                prioLbl.setText(project.getPrioritet());
            }
        };

        // Status edit
        addEditableButton(container, gbc, "Prioritet:", prioLbl, onPrioEdit);

        return container;

    }

    @Override
    protected void setAllFieldValues() {
        projektnamnField.setText(project.getProjektnamn());
        beskrivningField.setText(project.getBeskrivning());
        startdatumField.setText(project.getStartDatum());
        slutdatumField.setText(project.getSlutDatum());
        kostnadField.setText(project.getKostnad());
    }

    private JPanel createPartnerList() {
        JPanel con = new JPanel();

        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Partners");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        con.add(title);

        partnerList = new JScrollPane();
        updatePartnerList();
        con.add(partnerList);

        JButton removeBtn = new JButton("Tabort");
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Partner> hList = project.getPartners();
                ArrayList<DataContainerCommon> a = new ArrayList<>();

                for (Partner p : hList) {
                    a.add(p);
                }

                String id = GuiUtil.getSelectFromListPupop(a, "Välj partner att tabort");
                if (id == null) {
                    return;
                }

                project.removePartner(id);
                updatePartnerList();
            }
        });
        con.add(removeBtn);

        JButton addBtn = new JButton("Lägg till");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = GuiUtil.getSelectFromListPupop(Partner.getAll(), "Välj partner att lägga till");
                if (id == null) {
                    return;
                }

                project.addPartner(id);
                updatePartnerList();
            }
        });
        con.add(addBtn);

        return con;
    }

    private JPanel createContributerList() {
        JPanel con = new JPanel();

        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Medverkande");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        con.add(title);

        contributerList = new JScrollPane();
        updateContributerList();
        con.add(contributerList);

        JButton removeBtn = new JButton("Tabort");
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<User> hList = project.getContributers();
                ArrayList<DataContainerCommon> a = new ArrayList<>();

                for (User p : hList) {
                    a.add(p);
                }

                String id = GuiUtil.getSelectFromListPupop(a, "Välj handläggare att tabort");
                if (id == null) {
                    return;
                }

                project.removeContributer(id);
                updateContributerList();
            }
        });
        con.add(removeBtn);

        JButton addBtn = new JButton("Lägg till");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = GuiUtil.getSelectFromListPupop(User.getAll(), "Välj handläggare att lägga till");
                if (id == null) {
                    return;
                }

                project.addContributer(id);
                updateContributerList();
            }
        });
        con.add(addBtn);

        return con;
    }

    private JPanel createEnvGoalList() {

        JPanel con = new JPanel();

        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Hållbarhetsmål");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        con.add(title);

        hallbarhetsList = new JScrollPane();
        updateHallbarhetsList();
        con.add(hallbarhetsList);

        JButton removeBtn = new JButton("Tabort");
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<HallbarhetsMal> hList = project.getHallbarhetsMal();
                ArrayList<DataContainerCommon> a = new ArrayList<>();

                for (HallbarhetsMal h : hList) {
                    a.add(h);
                }

                String id = GuiUtil.getSelectFromListPupop(a, "Välj mål att tabort");
                if (id == null) {
                    return;
                }

                project.removeHallbarhetesMal(id);
                updateHallbarhetsList();
            }
        });
        con.add(removeBtn);

        JButton addBtn = new JButton("Lägg till");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = GuiUtil.getSelectFromListPupop(HallbarhetsMal.getAll(), "Välj mål att lägga till");
                if (id == null) {
                    return;
                }

                project.addHallbarhetesMal(id);
                updateHallbarhetsList();
            }
        });
        con.add(addBtn);

        return con;
    }

    private void updateContributerList() {
        HashMap<String, ActionListener> items = new HashMap<String, ActionListener>();

        for (User p : project.getContributers()) {
            items.put(p.getFullname(),
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SpecificEmployee(p.getFullname(), p.getAid(), parent);
                }
            });
        }

        JPanel p = GuiUtil.createClickableItemList(items);
        contributerList.setViewportView(p);
        contributerList.repaint();
    }

    private void updatePartnerList() {
        HashMap<String, ActionListener> items = new HashMap<String, ActionListener>();

        for (Partner p : project.getPartners()) {
            items.put(p.getNamn(),
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SpecificPartner(p.getNamn(), p.getPid(), parent);
                }
            });
        }

        JPanel p = GuiUtil.createClickableItemList(items);
        partnerList.setViewportView(p);
        partnerList.repaint();
    }

    private void updateHallbarhetsList() {
        HashMap<String, ActionListener> items = new HashMap<String, ActionListener>();

        for (HallbarhetsMal m : project.getHallbarhetsMal()) {

            items.put(m.getNamn(),
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SpecificEnvGoal(m.getNamn(), m.getHid(), parent);
                }
            });
        }

        JPanel p = GuiUtil.createClickableItemList(items);
        hallbarhetsList.setViewportView(p);
        hallbarhetsList.repaint();
    }

    @Override
    protected void saveAllInputs() {
        project.setProjektnamn(projektnamnField.getText());
        project.setBeskrivning(beskrivningField.getText());
        project.setStartDatum(startdatumField.getText());
        project.setSlutDatum(slutdatumField.getText());
        project.setKostnad(kostnadField.getText());
    }

    @Override
    protected boolean hasThePrivlege() {

        // User works on same department, ie has privlege to see
        if (project.getAvdelningIds().contains(Main.user.getAvdelning())) {
            return true;
        }

        // Else if user is a contributer, allow them to visit
        for (User c : project.getContributers()) {
            if (c.getAid().equals(Main.user.getAid())) {
                return true;
            }
        }

        if (Main.user.getAid().equals(project.getProjektchef())) {
            return true;
        }

        // If user is guest
        if (Main.user.getBehorig() == AccessLevel.GUEST) {
            return false;
        }

        return false;
    }

    @Override
    protected boolean allowedToEdit() {
        // Check if user is allowed to edit
        if (Main.user == null || (!Main.user.getAid().equals(project.getProjektchef())) && Main.user.getBehorig() != AccessLevel.ADMIN2 && Main.user.getBehorig() != AccessLevel.ADMIN1) {
            return false;
        }
        return true;
    }
}
