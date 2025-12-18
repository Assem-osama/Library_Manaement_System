package com.mycompany.library_manaement_system;

import javax.swing.UIManager;

public class Library_Manaement_System {

    public static void main(String[] args) {

        // Try to use Nimbus look
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            
        }

        new com.mycompany.library_manaement_system.View.RegisterForm().setVisible(true);
    }
}
