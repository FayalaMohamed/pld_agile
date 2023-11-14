package com.hexa.view.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.hexa.controller.Controller;

public class KeyboardListener extends KeyAdapter {
  Controller controller;

  public KeyboardListener(Controller c) {
    controller = c;
  }

  public void keyPressed(KeyEvent e) {
    if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & java.awt.event.KeyEvent.CTRL_DOWN_MASK) != 0)) {
      controller.undo();
    } else if ((e.getKeyCode() == KeyEvent.VK_R)
        && ((e.getModifiersEx() & java.awt.event.KeyEvent.CTRL_DOWN_MASK) != 0)) {
      controller.redo();
    }
  }
}
