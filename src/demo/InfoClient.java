/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.Serializable;

/**
 *
 * @author Huynh
 */
public class InfoClient implements Serializable{
    private int Port = 0;
    private String IP="";
    private String Name="";
    private FrameChat Fchat=null;/*FRAME CHAT SAMPLE CAN REPLACE OTHER*/

    /**
     *
     * @param IP
     * @param Port
     * @param Name
     */
    public InfoClient(String IP,int Port, String Name,FrameChat Fchat){
      this.Port=Port;
      this.IP=IP;
      this.Name=Name;
      this.Fchat=Fchat;
      
    }
    public int getPort(){
        return this.Port;
    }
    public String getIP(){
        return this.IP;
    }
    public String getName(){
        return this.Name;
    }
    public FrameChat getFchat(){
        return this.Fchat;
    }
}
