package ngo2024.pages.SpecificsPage;

import java.awt.*;
import javax.swing.*;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Land;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.TabPageBase;

/**
 * Page of a Specific land, containing information of relative land.
 *
 * @author leonb
 */
public final class SpecificLand extends SpecificPageBase {

    private Land land;

    // Individual JTextFields for each attribute
    private JTextField SpakField;
    private JTextField namnField;
    private JTextField ValutaField;
    private JTextField tidszonField;
    private JTextField politikField;
    private JTextField ekonomiField;

    /**
     * Creates a tabbed page containg information about this land.
     *
     * @param title, title of the tab
     * @param landID, the key of the row in the land table
     * @param parent, the JTabbedPane this will be added to.
     */
    public SpecificLand(String title, String landID, JTabbedPane parent) {
        super(parent, title);

        land = new Land(landID);

        addTab();
    }

    /**
     * creates a header for the page
     *
     * @return a JLabel styled as a title with the land name.
     */
    private JLabel createHeaderPanel() {
        JLabel titleLabel = new JLabel(land.getNamn() != null ? land.getNamn() : "N/A");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        return titleLabel;
    }

    /**
     * Creates a footer panel
     *
     * @return a Jpanel containing a edit button
     */
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        footerPanel.add(createAdminOptions());
        return footerPanel;
    }

    @Override
    protected void initiate() {
        // Set main layout
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize GUI components
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Create left container with image and general info
        JPanel container = new JPanel(new GridBagLayout());

        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each land attribute
        namnField = addField(container, gbc, "Namn");
        SpakField = addField(container, gbc, "Språk");
        ValutaField = addField(container, gbc, "Valuta");
        tidszonField = addField(container, gbc, "Tidszon");
        politikField = addField(container, gbc, "Politisk struktur");
        ekonomiField = addField(container, gbc, "Ekonomi");

        setAllFieldValues();
        enableFields(false);

        add(container, BorderLayout.CENTER);

        add(createFooterPanel(), BorderLayout.SOUTH);

    }

    @Override
    protected boolean hasThePrivlege() {
        return Main.user.getBehorig() != AccessLevel.GUEST;
    }

    @Override
    protected void enableFields(boolean state) {
        namnField.setEnabled(state);
        SpakField.setEnabled(state);
        ValutaField.setEnabled(state);
        tidszonField.setEnabled(state);
        politikField.setEnabled(state);
        ekonomiField.setEnabled(state);

    }

    @Override
    protected void saveAllInputs() {
        land.setNamn(namnField.getText());
        land.setSprak(SpakField.getText());
        land.setValuta(ValutaField.getText());
        land.setTidszon(tidszonField.getText());
        land.setPolitiskStruktur(politikField.getText());
        land.setEkonomi(ekonomiField.getText());

    }

    @Override
    protected void setAllFieldValues() {
        namnField.setText(land.getNamn());
        SpakField.setText(land.getSprak());
        ValutaField.setText(land.getValuta());
        tidszonField.setText(land.getTidszon());
        politikField.setText(land.getPolitisk_struktur());
        ekonomiField.setText(land.getEkonomi());
    }

    @Override
    protected boolean allowedToEdit() {
        return true;
    }
}
