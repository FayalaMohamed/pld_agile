package com.hexa;

import com.hexa.controller.Controller;
import com.hexa.view.Window;

public class App {

	public static void main(String[] args) throws Exception {

		Controller controller = new Controller();
		Window window = new Window();

		window.initialiser(controller);
		controller.initialiser(window);

	}

}
