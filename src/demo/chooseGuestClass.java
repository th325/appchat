/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.util.LinkedList;
import GUI.*;
/**
 *
 * @author Huynh
 */
public class chooseGuestClass {
    public FrameChat fchat=null;
    public ConnectClass cnn = null;
    public chooseGuestClass choose= null;
    private String[] value=new String [4];
    public LinkedList<InfoClient> ListInfo = new LinkedList<InfoClient>(); 
    public int countClient=0;
    public InfoClient info = null;
    
    
    public void SetDefault(String[] args,StartFrame start) throws Exception{
        choose=this;
        cnn=new ConnectClass();
        boolean isServer=false;
        if (args[2].equals("S"))isServer=true;
        value[0]=args[0];
        value[1]=args[1];
        value[2]=args[2];
        info = new InfoClient(value[2],Integer.parseInt(value[1]),value[0]);
        System.out.println(info.getPort());
        cnn.SetBindForObj(info,choose,start);
    }
}
