package generic.login;

/**
 * Created: 07/08/2023 18:30
 * Author: Twitter @hawolt
 **/

public interface ILoginCallback {
    void onLogin(boolean rememberMe, String username, String password);
}
