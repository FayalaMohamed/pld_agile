package com.hexa.controller;

import java.io.FileInputStream;

import com.hexa.model.XMLfileOpener;
import com.hexa.view.Window;

public class ChargerCarte implements State {

  public void entryAction(Controller c, Window w) {
    //faire un truc comme : XMLParser.serlectfile() -> problème du singleton
    if (xmlFileInputStream != null) {
        //appel de XMLParser avec le flux en paramètre
    } else {
        c.setCurrentState(c.initialState);
    }
  }
}
