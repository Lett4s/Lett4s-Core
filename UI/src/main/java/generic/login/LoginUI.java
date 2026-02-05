package generic.login;

import generic.Client;
import generic.component.*;
import generic.themes.ColorPalette;
import generic.utility.ChildUIComponent;
import generic.utility.HighlightType;
import generic.utility.MainUIComponent;
import paint.animation.AnimationVisualizer;
import paint.animation.impl.impl.SpinningAnimation;
import util.settings.ClientSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created: 06/08/2023 13:10
 * Author: Twitter @hawolt
 **/

public class LoginUI extends MainUIComponent implements ActionListener {

    private final CardLayout cardLayout = new CardLayout();
    private final ChildUIComponent parent = new ChildUIComponent(cardLayout);
    private final AnimationVisualizer animationVisualizer;
    private final LHintPasswordTextField passwordRegister;
    private final LHintTextField usernameRegister;
    private final LHintPasswordTextField password;
    private final LHintTextField emailRegister;
    private final LHintTextField username;
    private final ILoginCallback callback;
    private final JCheckBox rememberMe;
    private final LHintTextField tag;
    private final JButton register;
    private final JButton login;

    private LoginUI(Client client, ILoginCallback callback) {
        super(client.getRoot());
        this.setLayout(new BorderLayout());
        client.getRoot().setUndecorated(true);
        ClientSettings settings = client.getSettingService().getClientSettings();

        SwiftFrameDecoration control = new SwiftFrameDecoration(
                client.getRoot()
        );
        control.getButtonPanel().setBackground(ColorPalette.accentColor);
        this.add(control, BorderLayout.NORTH);

        this.add(parent, BorderLayout.CENTER);
        SpinningAnimation spinningAnimation = new SpinningAnimation(5, 45);
        this.animationVisualizer = new AnimationVisualizer(spinningAnimation);
        this.parent.add(InternalLoginState.LOADING.name(), animationVisualizer);
        this.animationVisualizer.start();

        ChildUIComponent login = new ChildUIComponent(new GridLayout(0, 1, 0, 5));
        login.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.username = new LHintTextField("username");
        if (settings.isRememberMe()) this.username.setText(settings.getRememberMeUsername());
        this.password = new LHintPasswordTextField("password");
        this.login = new LFlatButton("Login", LTextAlign.CENTER, HighlightType.COMPONENT);
        this.login.setActionCommand(InternalLoginState.LOGIN.name());
        this.rememberMe = new LFlatCheckBox("Remember Me");
        this.rememberMe.setForeground(Color.WHITE);
        this.rememberMe.setFocusPainted(false);
        this.rememberMe.setBackground(ColorPalette.buttonSelectionColor);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);

        login.add(rememberMe);
        login.add(usernameLabel);
        login.add(username);
        login.add(passwordLabel);
        login.add(password);
        login.add(this.login);
        parent.add(InternalLoginState.LOGIN.name(), login);

        ChildUIComponent userTagComponent = new ChildUIComponent(new GridLayout(0, 3, 0, 5));
        ChildUIComponent register = new ChildUIComponent(new GridLayout(0, 1, 0, 5));
        userTagComponent.setBorder(new EmptyBorder(5, 5, 5, 5));
        register.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.register = new LFlatButton("Register", LTextAlign.CENTER, HighlightType.COMPONENT);
        this.register.setActionCommand(InternalLoginState.REGISTER.name());
        this.passwordRegister = new LHintPasswordTextField("password");
        this.usernameRegister = new LHintTextField("username");
        this.tag = new LHintTextField("tag", 5);
        this.emailRegister = new LHintTextField("e-mail");
        JLabel usernameLabelReg = new JLabel("Username");
        usernameLabelReg.setForeground(Color.WHITE);
        JLabel passwordLabelReg = new JLabel("Password");
        passwordLabelReg.setForeground(Color.WHITE);
        JLabel emailLabelReg = new JLabel("E-Mail");
        emailLabelReg.setForeground(Color.WHITE);
        JLabel hashTag = new JLabel("#");
        hashTag.setForeground(Color.WHITE);

        userTagComponent.add(username);
        userTagComponent.add(hashTag);
        userTagComponent.add(tag);
        register.add(usernameLabelReg);
        register.add(userTagComponent);
        register.add(emailLabelReg);
        register.add(emailRegister);
        register.add(passwordLabelReg);
        register.add(password);
        register.add(this.register);
        parent.add(InternalLoginState.REGISTER.name(), register);

        this.setPreferredSize(new Dimension(300, 300));
        this.login.addActionListener(this);
        this.register.addActionListener(this);
        this.container.add(this);

        // Using .setLabelFor() to bind labels to corresponding input fields
        usernameLabel.setLabelFor(username);
        passwordLabel.setLabelFor(password);

        usernameLabelReg.setLabelFor(usernameRegister);
        passwordLabelReg.setLabelFor(passwordRegister);
        emailLabelReg.setLabelFor(emailRegister);

        // Enter hook so users can log in with enter
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) return;
                actionPerformed(null);
            }
        };

        username.addKeyListener(enterKeyAdapter);
        password.addKeyListener(enterKeyAdapter);

        this.callback = callback;
        this.init();
    }

    public static LoginUI create(Client window, ILoginCallback callback) {
        return new LoginUI(window, callback);
    }

    public void toggle(InternalLoginState state) {
        cardLayout.show(parent, state.name());
    }

    public JCheckBox getRememberMe() {
        return rememberMe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pass = new String(password.getPassword());
        if (pass.isEmpty()) {
            Client.showMessageDialog("Password can not be blank");
        } else {
            String user = username.getText();
            if (e.getActionCommand().equals(InternalLoginState.LOGIN.name())) {
                this.toggle(InternalLoginState.LOADING);
                Client.service.execute(() -> callback.onLogin(rememberMe.isSelected(), user, pass));
            } else if (e.getActionCommand().equals(InternalLoginState.REGISTER.name())) {
                this.toggle(InternalLoginState.REGISTER);
                String email = this.emailRegister.getText();
                String tag = this.tag.getText();
                Client.service.execute(() -> callback.onRegister(email, user, tag, pass));
            }
        }
    }

    public AnimationVisualizer getAnimationVisualizer() {
        return animationVisualizer;
    }

}