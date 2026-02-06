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
    private final JButton cancel;
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
        this.login.setActionCommand("login");
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

        ChildUIComponent userTagComponent = new ChildUIComponent(new GridLayout(0, 2, 5, 0));
        ChildUIComponent buttonComponent = new ChildUIComponent(new GridLayout(0, 2, 5, 0));
        ChildUIComponent register = new ChildUIComponent(new GridLayout(0, 1, 0, 5));
        register.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.register = new LFlatButton("Register", LTextAlign.CENTER, HighlightType.COMPONENT);
        this.register.setActionCommand("register");
        this.cancel = new LFlatButton("Cancel", LTextAlign.CENTER, HighlightType.COMPONENT);
        this.cancel.setActionCommand("cancel");
        this.passwordRegister = new LHintPasswordTextField("password");
        this.usernameRegister = new LHintTextField("username");
        this.tag = new LHintTextField("tag", 7, 2, true);
        this.emailRegister = new LHintTextField("e-mail (optional)");
        JLabel usernameLabelReg = new JLabel("Username");
        usernameLabelReg.setForeground(Color.WHITE);
        JLabel passwordLabelReg = new JLabel("Password");
        passwordLabelReg.setForeground(Color.WHITE);
        JLabel emailLabelReg = new JLabel("E-Mail");
        emailLabelReg.setForeground(Color.WHITE);

        userTagComponent.add(usernameRegister);
        userTagComponent.add(tag);
        register.add(usernameLabelReg);
        register.add(userTagComponent);
        register.add(emailLabelReg);
        register.add(emailRegister);
        register.add(passwordLabelReg);
        register.add(passwordRegister);
        buttonComponent.add(this.register);
        buttonComponent.add(this.cancel);
        register.add(buttonComponent);
        parent.add(InternalLoginState.REGISTER.name(), register);

        this.setPreferredSize(new Dimension(350, 350));
        this.login.addActionListener(this);
        this.cancel.addActionListener(this);
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
                actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "login"));
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
        String pass;
        String user;
        String tag;
        String email;
        switch (e.getActionCommand()) {
            case "cancel":
                toggle(InternalLoginState.LOGIN);
                break;
            case "login":
                pass = new String(password.getPassword());
                if (pass.isEmpty()) {
                    Client.showMessageDialog("Password can not be blank");
                } else {
                    user = username.getText();
                    this.toggle(InternalLoginState.LOADING);
                    Client.service.execute(() -> callback.onLogin(rememberMe.isSelected(), user, pass));
                }
                break;
            case "register":
                tag = this.tag.getText();
                user = usernameRegister.getText();
                pass = new String(passwordRegister.getPassword());
                if (tag.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    Client.showMessageDialog("All mandatory fields must be filled");
                } else {
                    email = this.emailRegister.getText();
                    this.toggle(InternalLoginState.LOADING);
                    Client.service.execute(() -> callback.onRegister(email, user, tag, pass));
                }
                break;
        }
    }

    public AnimationVisualizer getAnimationVisualizer() {
        return animationVisualizer;
    }

}