package main;
import views.auth.LoginView;
import views.hrd.HRDMainView;
import views.product_admin.ProductAdminMainView;

public class Main {

	public Main() {
		new LoginView();
	}

	public static void main(String[] args) {
		new Main();
	}

}
